package com.shopify.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShopifyCustomCollectionCreationRequestTest {

	private static final String SOME_TITLE = "Basic collection";
	private static final String SOME_HANDLE = "basic-collection";

	@Test
	public void simpleProductCreationRequest() {
		final ShopifyCustomCollectionCreationRequest customCollectionCreationRequest = ShopifyCustomCollectionCreationRequest.newBuilder()
				.withTitle(SOME_TITLE)
				.withHandle(SOME_HANDLE)
				.build();

		final ShopifyCustomCollection customCollection = customCollectionCreationRequest.getRequest();
		assertEquals(SOME_TITLE, customCollection.getTitle());
		assertEquals(SOME_HANDLE, customCollection.getHandle());
	}
}