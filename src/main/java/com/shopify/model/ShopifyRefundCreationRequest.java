package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyRefundCreationRequest {

	private ShopifyRefund request;

	public ShopifyRefund getRequest() {
		return request;
	}

	public void setRequest(final ShopifyRefund request) {
		this.request = request;
	}

}
