package com.eos.ors.order;

import java.util.List;

import com.eos.ors.util.AppConstant;
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

import com.eos.ors.exception.OrderNotFoundException;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping(value = AppConstant.BASE_CONTEXT)
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	
	@PostMapping
	@Operation(summary = "Api to create Order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary ="Update Order")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        if (updatedOrder != null) {
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @Operation(summary ="Get Order by ID")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        throw new OrderNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
    }
    
    @GetMapping("/customer/{id}")
    @Operation(summary ="Get Order by Customer ID")
    public ResponseEntity<List<Order>> getOrderByCustomerId(@PathVariable Long id) {
        List<Order> orders = orderService.getOrderByCustomerId(id);
        if (orders != null) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
        throw new OrderNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
    }

    @GetMapping
    @Operation(summary ="Get All Order")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            throw new OrderNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary ="Delete Order by Id")
    public void deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
    }
    

}
