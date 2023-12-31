package com.eos.ors.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
	
	private String doorNo;
	private String streetName;
	private String layout;
	private String city;
	private String pincode;
	

}
