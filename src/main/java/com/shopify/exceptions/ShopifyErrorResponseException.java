package com.shopify.exceptions;

import org.springframework.http.ResponseEntity;

import java.util.List;

public class ShopifyErrorResponseException extends RuntimeException {

	static final String MESSAGE = "Received unexpected Response Status Code of %d\nResponse Headers of:\n%s\nResponse Body of:\n%s";
	private final int statusCode;
	private final Object responseBody;
	private final List<ShopifyErrorCode> shopifyErrorCodes;

	public ShopifyErrorResponseException(ResponseEntity response) {
		super(buildMessage(response));
		this.responseBody = response.getBody() != null ? response.getBody() : null;
		this.shopifyErrorCodes = ShopifyErrorCodeFactory.create(responseBody);
		this.statusCode = response.getStatusCodeValue();
	}

	private static String buildMessage(ResponseEntity response) {
		final String readEntity = response.getBody() != null ? response.getBody().toString() : null;
		return String.format(MESSAGE, response.getStatusCodeValue(), response.getHeaders().toString(), readEntity);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public Object getResponseBody() {
		return responseBody;
	}

	public List<ShopifyErrorCode> getShopifyErrorCodes() {
		return shopifyErrorCodes;
	}

}
