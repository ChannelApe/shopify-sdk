package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyAssignedLocation {

	private String address1;
	private String address2;
	private String city;
	@XmlElement(name = "country_code")
	private String countryCode;
	@XmlElement(name = "location_id")
	private String locationId;
	private String name;
	private String phone;
	private String province;
	private String zip;
}
