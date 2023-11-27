package com.shopify.exceptions;

public class ShopifyClientException extends RuntimeException {

	private static final long serialVersionUID = -5992356578452439224L;

	public ShopifyClientException(final Throwable throwable) {
		super(throwable);
	}

	public ShopifyClientException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
