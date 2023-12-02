package com.shopify.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ShopifyCustomCollectionCreationRequestTest {

	private static final String SOME_TITLE = "Basic collection";
	private static final String SOME_HANDLE = "basic-collection";

	@Test
	void givenSomeValuesWhenBuildingProductCreationRequestThenExpectAllValuesToBeCorrect() {
		final ShopifyCustomCollectionCreationRequest customCollectionCreationRequest = ShopifyCustomCollectionCreationRequest
				.newBuilder().withTitle(SOME_TITLE).withHandle(SOME_HANDLE).isPublished(true).build();

		final ShopifyCustomCollection customCollection = customCollectionCreationRequest.getRequest();
		assertEquals(SOME_TITLE, customCollection.getTitle());
		assertEquals(SOME_HANDLE, customCollection.getHandle());
		assertTrue(customCollection.isPublished());
	}

	@Test
	void givenMinimalValuesWhenBuildingShopifyCustomCollectionCreationRequestThenExpectCorrectValues() {
		final ShopifyCustomCollectionCreationRequest customCollectionCreationRequest = ShopifyCustomCollectionCreationRequest
				.newBuilder().withTitle(SOME_TITLE).build();

		final ShopifyCustomCollection customCollection = customCollectionCreationRequest.getRequest();
		assertEquals(SOME_TITLE, customCollection.getTitle());
		assertNull(customCollection.getHandle());
		assertFalse(customCollection.isPublished());
	}
}