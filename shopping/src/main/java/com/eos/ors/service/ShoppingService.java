package com.eos.ors.service;

import java.util.Arrays;
import java.util.List;

import com.eos.ors.proxy.CartProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eos.ors.exception.DataNotFoundException;
import com.eos.ors.exception.InvalidDataException;
import com.eos.ors.model.Cart;
import com.eos.ors.model.Customer;
import com.eos.ors.model.CustomerOrder;
import com.eos.ors.model.LineItem;
import com.eos.ors.model.Order;
import com.eos.ors.model.Product;
import com.eos.ors.proxy.CustomerProxy;
import com.eos.ors.proxy.OrderProxy;
import com.eos.ors.proxy.ProductProxy;
import com.eos.ors.util.AppConstant;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ShoppingService {
	
	@Autowired
	ProductProxy productProxy;
	@Autowired
	InventoryProducer producer;
	@Autowired
	CustomerProxy customerProxy;
	@Autowired
    CartProxy cartProxy;
	@Autowired
	OrderProxy orderProxy;
	

	
	public Product createProduct(Product product) {
		
		ResponseEntity<Product> responseEntity= productProxy.createProduct(product); 	
		if(responseEntity!=null) {
			Product createdProduct=responseEntity.getBody();
			createdProduct.setQty(product.getQty());
			producer.sendProductToKafkaTopic(createdProduct);
			return createdProduct;
		}
		throw new InvalidDataException(AppConstant.INVALID_DATA_ERROR_MESSAGE) ;
		
	}

	@CircuitBreaker(name = "productService", fallbackMethod = "fallbackfindAllProduct")
	public ResponseEntity<List<Product>> findAllProduct() {
		return productProxy.getAllProducts();
	}
	public ResponseEntity<List<Product>> fallbackfindAllProduct(Throwable th) {
		Product samsung = Product.builder().id(1l).name("Samsung").price(500).qty(10).build();
		Product nokia = Product.builder().id(1l).name("nokia").price(100).qty(100).build();
		return new ResponseEntity<>(Arrays.asList(samsung, nokia), HttpStatus.OK);

	}
	

	public Cart createCustomer(Customer customer) {
		ResponseEntity<Customer> responseEntity=customerProxy.createCustomer(customer);
		if(responseEntity!=null) {
			Customer createdCustomer=responseEntity.getBody();
			ResponseEntity<Cart> cartResponse=cartProxy.createCart(Cart.builder().customerId(createdCustomer.getId()).build());
			return cartResponse.getBody();
		}
		throw new InvalidDataException(AppConstant.INVALID_DATA_ERROR_MESSAGE) ;
	}
	
	public Cart updateCartByCustomerId(Long customerId, Cart cart) {

		ResponseEntity<Cart> responseEntity = cartProxy.updateCartByCustomerId(customerId, cart);
		if (responseEntity != null) {
			return responseEntity.getBody();
		}
		throw new DataNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
	}
	
	public Order createOrder(Long customerId) {
		ResponseEntity<Cart> cartResponseEntity = cartProxy.getCartByCustomerId(customerId);
		if (cartResponseEntity != null && cartResponseEntity.getBody().getLineItem().size()>0) {
			Cart customerCart = cartResponseEntity.getBody();
			customerCart.getLineItem().stream().forEach(l -> cartProxy.deleteLineItemId(l.getId()));
			List<LineItem> items=customerCart.getLineItem();
			items.stream().forEach(li -> li.setId(0));
			Order order = Order.builder().customerId(customerId).lineItems(items).build();
			ResponseEntity<Order> orderResponseEntity = orderProxy.createOrder(order);
			updateInvenetoryForProduct(orderResponseEntity.getBody());
			
			return orderResponseEntity.getBody();
		}
		throw new DataNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
	}

	private void updateInvenetoryForProduct(Order placedOrder) {
		if (placedOrder != null) {
			placedOrder.getLineItems().stream()
					.forEach(o -> producer.sendProductToKafkaTopic(Product.builder().id(o.getProductId()).qty(-o.getQty()).build()));
		}
	}
	
	
	public CustomerOrder findCustomerOrderById(Long customerId) {
		ResponseEntity<Customer> customerResponseEntity = customerProxy.getCustomerById(customerId);
		ResponseEntity<List<Order>> orderResponseEntity = orderProxy.getOrderByCustomerId(customerId);
		return CustomerOrder.builder().customer(customerResponseEntity.getBody()).orders(orderResponseEntity.getBody())
				.build();

	}

}
