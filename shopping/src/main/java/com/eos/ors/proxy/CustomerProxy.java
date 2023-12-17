package com.eos.ors.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eos.ors.model.Customer;

@FeignClient(name = "customer")
public interface CustomerProxy {
	
	@PostMapping("/api/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer);
	@GetMapping("/api/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id);

}
