package com.shopify.exceptions;

import java.util.List;

import javax.ws.rs.core.Response;

public class ShopifyErrorResponseException extends RuntimeException {

	static final String MESSAGE = "Received unexpected Response Status Code of %d\nResponse Headers of:\n%s\nResponse Body of:\n%s";
	private final int statusCode;
	private static final long serialVersionUID = 5646635633348617058L;
	private final String responseBody;
	private final List<ShopifyErrorCode> shopifyErrorCodes;

	public ShopifyErrorResponseException(final Response response) {
		super(buildMessage(response));
		response.bufferEntity();
		this.responseBody = response.readEntity(String.class);
		this.shopifyErrorCodes = ShopifyErrorCodeFactory.create(responseBody);
		this.statusCode = response.getStatus();
	}

	public ShopifyErrorResponseException(final Response response, final String responseBody) {
		super(buildMessage(response, responseBody));
		response.bufferEntity();

		this.responseBody = responseBody;
		this.shopifyErrorCodes = ShopifyErrorCodeFactory.create(responseBody);
		this.statusCode = response.getStatus();
	}

	private static String buildMessage(final Response response, final String responseBody) {
		response.bufferEntity();
		return String.format(MESSAGE, response.getStatus(), response.getStringHeaders(), responseBody);
	}

	private static String buildMessage(final Response response) {
		response.bufferEntity();
		return String.format(MESSAGE, response.getStatus(), response.getStringHeaders(),
				response.readEntity(String.class));
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public List<ShopifyErrorCode> getShopifyErrorCodes() {
		return shopifyErrorCodes;
	}

}
