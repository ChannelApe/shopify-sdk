package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyInventoryItemsRoot {

	@XmlElement(name = "inventory_items")
	private List<ShopifyInventoryItem> inventoryItems = new LinkedList<>();

	public List<ShopifyInventoryItem> getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(List<ShopifyInventoryItem> inventoryItems) {
		this.inventoryItems = inventoryItems;
	}

}
