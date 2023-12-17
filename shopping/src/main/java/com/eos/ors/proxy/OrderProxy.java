package com.eos.ors.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eos.ors.model.Order;

//@FeignClient(name = "order", url = "http://localhost:7084")
@FeignClient(name = "order")
public interface OrderProxy {
	
	@PostMapping("/api/order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order);
	
	@GetMapping("/api/order/customer/{id}")
    public ResponseEntity<List<Order>> getOrderByCustomerId(@PathVariable Long id);
}
