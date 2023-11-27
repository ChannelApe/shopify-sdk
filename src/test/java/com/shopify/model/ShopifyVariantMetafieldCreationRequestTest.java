package com.shopify.model;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

public class ShopifyVariantMetafieldCreationRequestTest {

	private static final String SOME_VARIANT_ID = UUID.randomUUID().toString();
	private static final String SOME_NAMESPACE = "channelape";
	private static final String SOME_KEY = "length";
	private static final String SOME_VALUE = "36";
	private static final MetafieldType SOME_VALUE_TYPE = MetafieldType.NUMBER_INTEGER;

	@Test
	public void givenSomeValuesWhenCreatingShopifyVariantMetafieldCreationRequestThenReturnValues() {
		final ShopifyVariantMetafieldCreationRequest actualShopifyVariantMetafieldCreationRequest = ShopifyVariantMetafieldCreationRequest
				.newBuilder().withVariantId(SOME_VARIANT_ID).withNamespace(SOME_NAMESPACE).withKey(SOME_KEY)
				.withValue(SOME_VALUE).withValueType(SOME_VALUE_TYPE).build();

		assertEquals(SOME_VARIANT_ID, actualShopifyVariantMetafieldCreationRequest.getVariantId());
		assertEquals(SOME_NAMESPACE, actualShopifyVariantMetafieldCreationRequest.getRequest().getNamespace());
		assertEquals(SOME_KEY, actualShopifyVariantMetafieldCreationRequest.getRequest().getKey());
		assertEquals(SOME_VALUE, actualShopifyVariantMetafieldCreationRequest.getRequest().getValue());
		assertEquals(SOME_VALUE_TYPE, actualShopifyVariantMetafieldCreationRequest.getRequest().getType());
	}

}
