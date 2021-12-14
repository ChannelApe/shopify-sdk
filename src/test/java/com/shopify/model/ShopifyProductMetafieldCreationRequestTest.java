package com.shopify.model;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

public class ShopifyProductMetafieldCreationRequestTest {

	private static final String SOME_PRODUCT_ID = UUID.randomUUID().toString();
	private static final String SOME_NAMESPACE = "channelape";
	private static final String SOME_KEY = "length";
	private static final String SOME_VALUE = "36";
	private static final MetafieldType SOME_VALUE_TYPE = MetafieldType.NUMBER_INTEGER;

	@Test
	public void givenSomeValuesWhenCreatingShopifyProductMetafieldCreationRequestThenReturnValues() {
		final ShopifyProductMetafieldCreationRequest actualShopifyProductMetafieldCreationRequest = ShopifyProductMetafieldCreationRequest
				.newBuilder().withProductId(SOME_PRODUCT_ID).withNamespace(SOME_NAMESPACE).withKey(SOME_KEY)
				.withValue(SOME_VALUE).withValueType(SOME_VALUE_TYPE).build();

		assertEquals(SOME_PRODUCT_ID, actualShopifyProductMetafieldCreationRequest.getProductId());
		assertEquals(SOME_NAMESPACE, actualShopifyProductMetafieldCreationRequest.getRequest().getNamespace());
		assertEquals(SOME_KEY, actualShopifyProductMetafieldCreationRequest.getRequest().getKey());
		assertEquals(SOME_VALUE, actualShopifyProductMetafieldCreationRequest.getRequest().getValue());
		assertEquals(SOME_VALUE_TYPE, actualShopifyProductMetafieldCreationRequest.getRequest().getType());
	}

}
