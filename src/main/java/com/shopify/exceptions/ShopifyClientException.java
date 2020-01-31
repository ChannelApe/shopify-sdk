package com.shopify.exceptions;

public class ShopifyClientException extends RuntimeException {

	public ShopifyClientException(final Throwable throwable) {
		super(throwable);
	}

	public ShopifyClientException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
