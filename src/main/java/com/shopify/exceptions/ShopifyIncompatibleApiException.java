package com.shopify.exceptions;

public class ShopifyIncompatibleApiException extends RuntimeException {

	private static final String defaultMessage = "This action cannot be done in the current ShopifyApi version";
	private static final long serialVersionUID = 147838161361971621L;

	public ShopifyIncompatibleApiException() {
		super(defaultMessage);
	}

	public ShopifyIncompatibleApiException(final Throwable throwable) {
		super(defaultMessage, throwable);
	}

}
