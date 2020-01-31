package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum FulfillmentService {

	MANUAL("manual");

	private final String value;

	private FulfillmentService(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}
