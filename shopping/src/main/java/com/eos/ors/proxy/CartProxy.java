package com.eos.ors.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.eos.ors.model.Cart;

//@FeignClient(name = "cart", url = "http://localhost:7082")
@FeignClient(name = "cart")
public interface CartProxy {
	
	@PostMapping("/api/cart")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart);
	
	@PutMapping("/api/cart/customer/{customerId}")
	public ResponseEntity<Cart> updateCartByCustomerId(@PathVariable Long customerId, @RequestBody Cart cart);
	
	@GetMapping("/api/cart/customer/{customerId}")
    public ResponseEntity<Cart> getCartByCustomerId(@PathVariable Long customerId);
	
	@DeleteMapping("/api/cart/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartById(@PathVariable Long id);
	
	 @DeleteMapping("/api/cart/lineitem/{id}")
	 @ResponseStatus(HttpStatus.NO_CONTENT)
	 public void deleteLineItemId(@PathVariable Long id);

}
