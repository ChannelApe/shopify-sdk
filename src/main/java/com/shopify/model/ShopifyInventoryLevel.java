package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyInventoryLevel {

	@XmlElement(name = "inventory_item_id")
	private String inventoryItemId;

	@XmlElement(name = "location_id")
	private String locationId;

	private long available;

}
