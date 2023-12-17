package com.eos.ors.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
	
	private Long id;
	private long customerId;
	private List<LineItem> lineItem;
	
	

}
