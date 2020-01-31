package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyTransactionReceipt {

	@JsonProperty("apple_pay")
	private boolean applePay;

	public boolean isApplePay() {
		return applePay;
	}

	public void setApplePay(final boolean applePay) {
		this.applePay = applePay;
	}

}
