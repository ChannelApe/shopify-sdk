package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyErrors {

	@JsonProperty("shipping_address")
	private List<String> shippingAddressErrors = new LinkedList<>();

	public List<String> getShippingAddressErrors() {
		return shippingAddressErrors;
	}

	public void setShippingAddressErrors(final List<String> shippingAddressErrors) {
		this.shippingAddressErrors = shippingAddressErrors;
	}

}
