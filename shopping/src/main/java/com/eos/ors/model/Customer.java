package com.eos.ors.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
	
	
	private long id;
	private String name;
	private String email;
	private Address billingAddress;
	private Address shippingAddress;

}
