package com.shopify.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(Include.ALWAYS)
public class ShopifyAddressUpdateRequest {

	@XmlElement(name = "first_name")
	private String firstName;
	@XmlElement(name = "last_name")
	private String lastname;
	private String company;
	private String address1;
	private String address2;
	private String city;
	private String zip;
	private String province;
	private String country;
	@XmlElement(name = "province_code")
	private String provinceCode;
	@XmlElement(name = "country_code")
	private String countryCode;
	private String phone;
	private BigDecimal latitude;
	private BigDecimal longitude;

}
