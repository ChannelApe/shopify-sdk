package com.shopify.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ShopifyCustomerUpdateRequestTest {

	@Test
	void givenSomeValuesWhenShopifyCustomerUpdateRequestThenExpectCorrectValues() {

		final ShopifyCustomerUpdateRequest shopifyCustomerUpdateRequest = ShopifyCustomerUpdateRequest.newBuilder()
				.withId("some-id").withFirstName("Ryan").withLastName("Kazokas").withEmail("rkazokas@channelape.com")
				.withPhone("209378429734").build();

		assertEquals("some-id", shopifyCustomerUpdateRequest.getId());
		assertEquals("Ryan", shopifyCustomerUpdateRequest.getFirstName());
		assertEquals("Kazokas", shopifyCustomerUpdateRequest.getLastname());
		assertEquals("rkazokas@channelape.com", shopifyCustomerUpdateRequest.getEmail());
	}

}
