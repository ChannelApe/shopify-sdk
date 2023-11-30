package com.shopify.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ShopifyInventoryLevelTest {

	@Test
	void givenSomeValuesWhenCreatingShopifyInventoryLevelThenExpectCorrectValues() {
		final ShopifyInventoryLevel shopifyInventoryLevel = new ShopifyInventoryLevel();
		shopifyInventoryLevel.setAvailable(2L);
		shopifyInventoryLevel.setInventoryItemId("12387734");
		shopifyInventoryLevel.setLocationId("983482934");

		assertEquals(2L, shopifyInventoryLevel.getAvailable());
		assertEquals("12387734", shopifyInventoryLevel.getInventoryItemId());
		assertEquals("983482934", shopifyInventoryLevel.getLocationId());
	}

}
