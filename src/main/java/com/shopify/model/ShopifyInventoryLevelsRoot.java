package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyInventoryLevelsRoot {

	@XmlElement(name = "inventory_levels")
	private List<ShopifyInventoryLevel> inventoryLevels = new LinkedList<>();

	public List<ShopifyInventoryLevel> getInventoryLevels() {
		return inventoryLevels;
	}

	public void setInventoryLevels(List<ShopifyInventoryLevel> inventoryLevels) {
		this.inventoryLevels = inventoryLevels;
	}

}
