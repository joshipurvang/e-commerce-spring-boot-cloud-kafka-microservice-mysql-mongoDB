package com.eos.ors.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineItem {
	
	
	private long id;
	private long productId;
	private String name;
	private int qty;
	private double price;

}
