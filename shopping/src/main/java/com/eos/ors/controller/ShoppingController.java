package com.eos.ors.controller;

import java.util.List;

import com.eos.ors.exception.DataNotFoundException;
import com.eos.ors.model.CustomerOrder;
import com.eos.ors.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eos.ors.model.Cart;
import com.eos.ors.model.Customer;
import com.eos.ors.model.Order;
import com.eos.ors.model.Product;
import com.eos.ors.util.AppConstant;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(AppConstant.BASE_CONTEXT)
public class ShoppingController {
	
	@Autowired
    ShoppingService shoppingService;
	
	@PostMapping("/products")
	@Operation(summary = "Api to create Product and update inventory")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = shoppingService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
	
	@GetMapping("/products")
	@Operation(summary = "Api to get all Products")
	public ResponseEntity<List<Product>> findAllProduct() {
        return shoppingService.findAllProduct();
    }
	

	
	@PostMapping("/customer")
	@Operation(summary = "Api to create Customer and customr specific cart")
    public ResponseEntity<Cart> createCustomer(@RequestBody Customer customer) {
		Cart createdCart = shoppingService.createCustomer(customer);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }
	
	@PutMapping("/customer/{customerId}/cart")
	@Operation(summary = "Api to create Customer and customr specific cart")
    public ResponseEntity<Cart> updateCartByCustomerId(@PathVariable Long customerId, @RequestBody Cart cart) {
		Cart createdCart = shoppingService.updateCartByCustomerId(customerId,cart);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }
	@PostMapping("/customer/{customerId}/order")
	@Operation(summary = "Api to create Order for specific customer")
    public ResponseEntity<Order> createOrder(@PathVariable Long customerId) {
		Order createdOrder = shoppingService.createOrder(customerId);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
	
	@GetMapping("/customer/{customerId}/orders")
    @Operation(summary ="Get Order by Customer ID")
    public ResponseEntity<CustomerOrder> getOrderByCustomerId(@PathVariable Long customerId) {
		CustomerOrder customerOrder = shoppingService.findCustomerOrderById(customerId);
        if (customerOrder != null) {
            return new ResponseEntity<>(customerOrder, HttpStatus.OK);
        }
        throw new DataNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
    }

}
