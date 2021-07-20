package com.shopify.exceptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.shopify.exceptions.ShopifyErrorCode.Type;

public class ShopifyErrorResponseExceptionTest {

	@Test
	public void givenResponseWith422StatusCodeAndSomeResponseBodyAndSomeResponsHeadersWhenCreatingShopifyErroResponseExceptionThenCreateExceptionWithExpectedMessageAndException() {
		final Response response = mock(Response.class);
		final int expectedStatusCode = 422;
		when(response.getStatus()).thenReturn(expectedStatusCode);

		final MultivaluedMap<String, String> responseHeaders = new MultivaluedHashMap<>();
		responseHeaders.put("Content-Type", Arrays.asList("application/json", "charset=utf-8"));
		responseHeaders.put("Transfer-Encoding", Arrays.asList("chunked"));
		responseHeaders.put("X-Request-Id", Arrays.asList("ce729803-84ce-4063-a672-816f03c2c9a2"));
		responseHeaders.put("X-Shopify-API-Deprecated-Reason",
				Arrays.asList("https://help.shopify.com/api/guides/inventory-migration-guide"));
		when(response.getStringHeaders()).thenReturn(responseHeaders);

		final String expectedResponseBodyString = "{\"error\": \"something went wrong.\"}";
		final InputStream expectedResponseStream = new ByteArrayInputStream(
				expectedResponseBodyString.getBytes(StandardCharsets.UTF_8));
		when(response.getEntity()).thenReturn(expectedResponseStream);

		final ShopifyErrorResponseException actualShopifyErrorResponseException = new ShopifyErrorResponseException(
				response);

		final String expectedResponseHeaders = "{Transfer-Encoding=[chunked], X-Request-Id=[ce729803-84ce-4063-a672-816f03c2c9a2], X-Shopify-API-Deprecated-Reason=[https://help.shopify.com/api/guides/inventory-migration-guide], Content-Type=[application/json, charset=utf-8]}";

		assertEquals(String.format(ShopifyErrorResponseException.MESSAGE, expectedStatusCode, expectedResponseHeaders,
				expectedResponseBodyString), actualShopifyErrorResponseException.getMessage());
		assertTrue(actualShopifyErrorResponseException instanceof RuntimeException);
		assertEquals(1, actualShopifyErrorResponseException.getShopifyErrorCodes().size());
		assertEquals(Type.UNKNOWN, actualShopifyErrorResponseException.getShopifyErrorCodes().get(0).getType());
		assertEquals(expectedResponseBodyString,
				actualShopifyErrorResponseException.getShopifyErrorCodes().get(0).getMessage());
		assertEquals(expectedStatusCode, actualShopifyErrorResponseException.getStatusCode());
		assertEquals(expectedResponseBodyString, actualShopifyErrorResponseException.getResponseBody());
	}

	@Test
	public void givenResponseWith422StatusCodeAndSomeResponseBodyAndSomeResponsHeadersWhenCreatingShopifyErroResponseExceptionWithUnparseableBodyThenCreateExceptionWithExpectedMessageAndException() {
		final Response response = mock(Response.class);
		final int expectedStatusCode = 422;
		when(response.getStatus()).thenReturn(expectedStatusCode);

		final MultivaluedMap<String, String> responseHeaders = new MultivaluedHashMap<>();
		responseHeaders.put("Content-Type", Arrays.asList("application/json", "charset=utf-8"));
		responseHeaders.put("Transfer-Encoding", Arrays.asList("chunked"));
		responseHeaders.put("X-Request-Id", Arrays.asList("ce729803-84ce-4063-a672-816f03c2c9a2"));
		responseHeaders.put("X-Shopify-API-Deprecated-Reason",
				Arrays.asList("https://help.shopify.com/api/guides/inventory-migration-guide"));
		when(response.getStringHeaders()).thenReturn(responseHeaders);

		final String expectedResponseBodyString = "Some unaprseable error";
		final InputStream expectedResponseStream = new ByteArrayInputStream(
				expectedResponseBodyString.getBytes(StandardCharsets.UTF_8));
		when(response.getEntity()).thenReturn(expectedResponseStream);
		final ShopifyErrorResponseException actualShopifyErrorResponseException = new ShopifyErrorResponseException(
				response);

		final String expectedResponseHeaders = "{Transfer-Encoding=[chunked], X-Request-Id=[ce729803-84ce-4063-a672-816f03c2c9a2], X-Shopify-API-Deprecated-Reason=[https://help.shopify.com/api/guides/inventory-migration-guide], Content-Type=[application/json, charset=utf-8]}";

		assertEquals(String.format(ShopifyErrorResponseException.MESSAGE, expectedStatusCode, expectedResponseHeaders,
				expectedResponseBodyString), actualShopifyErrorResponseException.getMessage());
		assertTrue(actualShopifyErrorResponseException instanceof RuntimeException);
		assertEquals(1, actualShopifyErrorResponseException.getShopifyErrorCodes().size());
		assertEquals(Type.UNKNOWN, actualShopifyErrorResponseException.getShopifyErrorCodes().get(0).getType());
		assertEquals(expectedResponseBodyString,
				actualShopifyErrorResponseException.getShopifyErrorCodes().get(0).getMessage());
		assertEquals(expectedStatusCode, actualShopifyErrorResponseException.getStatusCode());
		assertEquals(expectedResponseBodyString, actualShopifyErrorResponseException.getResponseBody());
	}

	@Test
	public void givenResponseWith422StatusCodeAndSomeResponseBodyAndSomeResponsHeadersWhenCreatingShopifyErroResponseExceptionWithParsableBodyThenCreateExceptionWithExpectedMessageAndException() {
		final Response response = mock(Response.class);
		final int expectedStatusCode = 422;
		when(response.getStatus()).thenReturn(expectedStatusCode);

		final MultivaluedMap<String, String> responseHeaders = new MultivaluedHashMap<>();
		responseHeaders.put("Content-Type", Arrays.asList("application/json", "charset=utf-8"));
		responseHeaders.put("Transfer-Encoding", Arrays.asList("chunked"));
		responseHeaders.put("X-Request-Id", Arrays.asList("ce729803-84ce-4063-a672-816f03c2c9a2"));
		responseHeaders.put("X-Shopify-API-Deprecated-Reason",
				Arrays.asList("https://help.shopify.com/api/guides/inventory-migration-guide"));
		when(response.getStringHeaders()).thenReturn(responseHeaders);

		final String expectedResponseBodyString = "{\n" + "    \"errors\": {\n" + "        \"shipping_address\": [\n"
				+ "            \"address1 can't be blank, zip is not valid for united states, and city can't be blank\"\n"
				+ "        ]\n" + "    }\n" + "}";

		final InputStream expectedResponseStream = new ByteArrayInputStream(
				expectedResponseBodyString.getBytes(StandardCharsets.UTF_8));
		when(response.getEntity()).thenReturn(expectedResponseStream);
		final ShopifyErrorResponseException actualShopifyErrorResponseException = new ShopifyErrorResponseException(
				response);

		final String expectedResponseHeaders = "{Transfer-Encoding=[chunked], X-Request-Id=[ce729803-84ce-4063-a672-816f03c2c9a2], X-Shopify-API-Deprecated-Reason=[https://help.shopify.com/api/guides/inventory-migration-guide], Content-Type=[application/json, charset=utf-8]}";

		assertEquals(String.format(ShopifyErrorResponseException.MESSAGE, expectedStatusCode, expectedResponseHeaders,
				expectedResponseBodyString), actualShopifyErrorResponseException.getMessage());
		assertTrue(actualShopifyErrorResponseException instanceof RuntimeException);
		assertEquals(1, actualShopifyErrorResponseException.getShopifyErrorCodes().size());
		assertEquals(Type.SHIPPING_ADDRESS,
				actualShopifyErrorResponseException.getShopifyErrorCodes().get(0).getType());
		assertEquals("address1 can't be blank, zip is not valid for united states, and city can't be blank",
				actualShopifyErrorResponseException.getShopifyErrorCodes().get(0).getMessage());
		assertEquals(expectedStatusCode, actualShopifyErrorResponseException.getStatusCode());
		assertEquals(expectedResponseBodyString, actualShopifyErrorResponseException.getResponseBody());
	}

}
