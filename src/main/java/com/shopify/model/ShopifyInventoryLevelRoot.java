package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
