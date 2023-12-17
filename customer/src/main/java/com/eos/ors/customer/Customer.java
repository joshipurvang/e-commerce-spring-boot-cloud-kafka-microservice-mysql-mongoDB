package com.eos.ors.customer;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String email;
	@Embedded
	@AttributeOverrides({
        @AttributeOverride(name = "doorNo", column = @Column(name = "billing_door_no")),
        @AttributeOverride(name = "streetName", column = @Column(name = "billing_street_name")),
        @AttributeOverride(name = "layout", column = @Column(name = "billing_layout")),
        @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
        @AttributeOverride(name = "pincode", column = @Column(name = "billing_pincode"))})
	private Address billingAddress;
	@Embedded
	@AttributeOverrides({
        @AttributeOverride(name = "doorNo", column = @Column(name = "shipping_door_no")),
        @AttributeOverride(name = "streetName", column = @Column(name = "shipping_street_name")),
        @AttributeOverride(name = "layout", column = @Column(name = "shipping_layout")),
        @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
        @AttributeOverride(name = "pincode", column = @Column(name = "shipping_pincode"))})
	private Address shippingAddress;

}
