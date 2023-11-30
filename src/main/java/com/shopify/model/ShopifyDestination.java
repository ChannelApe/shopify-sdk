package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyDestination {

	private String id;
	private String address1;
	private String address2;
	private String city;
	private String company;
	private String country;
	private String email;
	@XmlElement(name = "first_name")
	private String firstName;
	@XmlElement(name = "last_name")
	private String lastName;
	private String phone;
	private String province;
	private String zip;
}
