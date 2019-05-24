package com.shopify.exceptions;

import java.io.Serializable;

public class ShopifyErrorCode implements Serializable {

	private static final long serialVersionUID = -3870975240510101019L;

	public enum Type implements Serializable {
		SHIPPING_ADDRESS, UNKNOWN
	}

	private final Type type;
	private final String message;

	public ShopifyErrorCode(final Type type, final String message) {
		this.type = type;
		this.message = message;
	}

	public Type getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

}
