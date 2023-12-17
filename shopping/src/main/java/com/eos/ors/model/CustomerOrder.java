package com.eos.ors.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerOrder {
	
	private Customer customer;
	private List<Order> orders;	

}
