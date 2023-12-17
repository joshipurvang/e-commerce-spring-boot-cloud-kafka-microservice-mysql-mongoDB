package com.eos.ors.order;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eos.ors.exception.OrderNotFoundException;
import com.eos.ors.util.AppConstant;



@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
	public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
        	updatedOrder.setId(id);
            return orderRepository.save(updatedOrder);
        }
        throw new OrderNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
    
    public List<Order> getOrderByCustomerId(Long id) {
        return orderRepository.findByCustomerId(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

}
