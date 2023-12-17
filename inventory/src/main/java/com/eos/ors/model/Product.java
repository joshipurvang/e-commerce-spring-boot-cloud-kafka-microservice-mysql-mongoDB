package com.eos.ors.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {


	private Long id;
	private String name;
	private int qty;
	private double	price;
	private String details;
}
