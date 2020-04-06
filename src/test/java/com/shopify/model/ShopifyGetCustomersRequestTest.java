package com.shopify.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

public class ShopifyGetCustomersRequestTest {
	@Test
	public void givenSomeValuesWhenShopifyCustomersGetRequestThenExpectCorrectValues() {
		final DateTime minimumCreationDate = new DateTime();
		final DateTime maximumCreationDate = minimumCreationDate.plusDays(1);
		final List<String> ids = new ArrayList<>();
		ids.add("some-id");
		final ShopifyGetCustomersRequest shopifyGetCustomersRequest = ShopifyGetCustomersRequest.newBuilder()
				.withPageInfo("some-page-info").withLimit(50).withSinceId("since-id").withIds(ids)
				.withCreatedAtMin(minimumCreationDate).withCreatedAtMax(maximumCreationDate).build();
		assertEquals("some-page-info", shopifyGetCustomersRequest.getPageInfo());
		assertEquals(50, shopifyGetCustomersRequest.getLimit());
		assertEquals("since-id", shopifyGetCustomersRequest.getSinceId());
		assertEquals("some-id", shopifyGetCustomersRequest.getIds().get(0));
		assertEquals(minimumCreationDate, shopifyGetCustomersRequest.getCreatedAtMin());
		assertEquals(maximumCreationDate, shopifyGetCustomersRequest.getCreatedAtMax());
	}
}
