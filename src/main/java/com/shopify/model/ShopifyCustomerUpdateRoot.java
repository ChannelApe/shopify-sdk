package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyCustomerUpdateRoot {

	private ShopifyCustomerUpdateRequest customer;

	public ShopifyCustomerUpdateRequest getCustomer() {
		return customer;
	}

	public void setCustomer(final ShopifyCustomerUpdateRequest customer) {
		this.customer = customer;
	}

}
