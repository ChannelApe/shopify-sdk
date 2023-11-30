package com.shopify.model;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class ShopifyInventoryLevelRootTest {

	@Test
	void givenSomeValuesWhenCreatingShopifyInventoryLevelUpdateRequestThenExpectCorrectValues() {
		final ShopifyInventoryLevel shopifyInventoryLevel = new ShopifyInventoryLevel();
		shopifyInventoryLevel.setAvailable(2L);
		shopifyInventoryLevel.setInventoryItemId("12387734");
		shopifyInventoryLevel.setLocationId("983482934");

		final ShopifyInventoryLevelRoot shopifyInventoryLevelRoot = new ShopifyInventoryLevelRoot();
		shopifyInventoryLevelRoot.setInventoryLevel(shopifyInventoryLevel);
		assertSame(shopifyInventoryLevelRoot.getInventoryLevel(), shopifyInventoryLevel);
	}

}
