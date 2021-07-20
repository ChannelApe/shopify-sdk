package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyErrors {

	@XmlElement(name = "shipping_address")
	private List<String> shippingAddressErrors = new LinkedList<>();

	public List<String> getShippingAddressErrors() {
		return shippingAddressErrors;
	}

	public void setShippingAddressErrors(final List<String> shippingAddressErrors) {
		this.shippingAddressErrors = shippingAddressErrors;
	}

}
