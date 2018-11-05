package com.shopify.exceptions;

import javax.ws.rs.core.Response;

public class ShopifyErrorResponseException extends RuntimeException {

	static final String MESSAGE = "Received unexpected Response Status Code of %d\nResponse Headers of:\n%s\nResponse Body of:\n%s";

	private static final long serialVersionUID = 5646635633348617058L;

	public ShopifyErrorResponseException(final Response response) {
		super(buildMessage(response));
	}

	private static String buildMessage(final Response response) {
		response.bufferEntity();
		return String.format(MESSAGE, response.getStatus(), response.getStringHeaders(),
				response.readEntity(String.class));
	}

}
