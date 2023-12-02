package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyInventoryLevelRoot {

	@XmlElement(name = "inventory_level")
	private ShopifyInventoryLevel inventoryLevel;

	public ShopifyInventoryLevel getInventoryLevel() {
		return inventoryLevel;
	}

	public void setInventoryLevel(final ShopifyInventoryLevel inventoryLevel) {
		this.inventoryLevel = inventoryLevel;
	}

}
