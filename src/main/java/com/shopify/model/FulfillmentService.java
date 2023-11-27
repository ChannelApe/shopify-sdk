package com.shopify.model;

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
