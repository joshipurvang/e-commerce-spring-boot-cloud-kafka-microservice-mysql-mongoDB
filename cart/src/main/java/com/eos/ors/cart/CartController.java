package main.java.com.eos.ors.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import main.java.com.eos.ors.exception.CartNotFoundException;
import main.java.com.eos.ors.exception.CustomerNotFoundException;
import main.java.com.eos.ors.lineitem.LineItemService;
import main.java.com.eos.ors.util.AppConstant;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping(value = AppConstant.BASE_CONTEXT)
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private LineItemService lineItemService;
	
	
	@PostMapping
	@Operation(summary = "Api to create Cart")
    public ResponseEntity<Cart> createCustomer(@RequestBody Cart cart) {
		Cart createdCart = cartService.createCart(cart);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary ="Update Cart")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cart) {
    	Cart updatedCart = cartService.updateCart(id, cart);
        if (updatedCart != null) {
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/customer/{customerId}")
    @Operation(summary ="Update Cart by customer Id")
    public ResponseEntity<Cart> updateCartByCustomerId(@PathVariable Long customerId, @RequestBody Cart cart) {
    	Cart updatedCart = cartService.updateCartByCustomerId(customerId, cart);
        if (updatedCart != null) {
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @Operation(summary ="Get Cart by ID")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
    	Cart cart = cartService.getCartById(id);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        throw new CartNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
    }
    
    @GetMapping("/customer/{customerId}")
    @Operation(summary ="Get Cart by Customer ID")
    @CircuitBreaker(name = "cartService", fallbackMethod = "handleCustomerNotFound")
    public ResponseEntity<Cart> getCartByCustomerId(@PathVariable Long customerId) {
    	Cart cart = cartService.getCartByCustomerId(customerId);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        throw new CartNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
    }
    
    public ResponseEntity<Cart> handleCustomerNotFound(Long id, Exception ex) {
        
        throw new CustomerNotFoundException("Customer does not exist for provided customer Id !!!");
    }

    @GetMapping
    @Operation(summary ="Get All Cart")
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        if (carts.isEmpty()) {
            throw new CartNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
        }
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary ="Delete Cart by Id")
    public void deleteCartById(@PathVariable Long id) {
    	cartService.deleteCartById(id);
    }
    
    @DeleteMapping("/lineitem/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary ="Delete LineItem by Id")
    public void deleteLineItemId(@PathVariable Long id) {
    	lineItemService.deleteLineItemById(id);
    }
    

}
