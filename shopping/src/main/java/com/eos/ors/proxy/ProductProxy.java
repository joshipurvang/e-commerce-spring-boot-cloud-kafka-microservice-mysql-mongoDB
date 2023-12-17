package com.eos.ors.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eos.ors.model.Product;

@FeignClient(name = "product")
public interface ProductProxy {
	
	@GetMapping("/api/products")
	ResponseEntity<List<Product>> getAllProducts();
	
	@PostMapping("/api/products")
	ResponseEntity<Product> createProduct(@RequestBody Product product);

}
