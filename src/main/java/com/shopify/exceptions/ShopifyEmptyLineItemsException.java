package com.shopify.exceptions;

public class ShopifyEmptyLineItemsException extends Exception {
	private static final long serialVersionUID = -6803895255030397292L;

	public ShopifyEmptyLineItemsException() {
		super("LineItems are required when creating a new fulfillment from a fulfillmentOrder");
	}

}
