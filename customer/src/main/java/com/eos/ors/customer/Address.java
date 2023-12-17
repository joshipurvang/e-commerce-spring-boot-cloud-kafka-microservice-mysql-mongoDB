package com.eos.ors.customer;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	
	private String doorNo;
	private String streetName;
	private String layout;
	private String city;
	private String pincode;
	

}
