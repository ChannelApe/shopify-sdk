package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
