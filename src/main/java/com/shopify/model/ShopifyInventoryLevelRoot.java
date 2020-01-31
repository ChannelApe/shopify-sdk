package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyInventoryLevelRoot {

	@JsonProperty("inventory_level")
	private ShopifyInventoryLevel inventoryLevel;

	public ShopifyInventoryLevel getInventoryLevel() {
		return inventoryLevel;
	}

	public void setInventoryLevel(final ShopifyInventoryLevel inventoryLevel) {
		this.inventoryLevel = inventoryLevel;
	}

}
