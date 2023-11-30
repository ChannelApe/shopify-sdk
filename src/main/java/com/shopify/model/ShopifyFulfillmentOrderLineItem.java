package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyFulfillmentOrderLineItem {

	private String id;
	@XmlElement(name = "shop_id")
	private String shopId;
	@XmlElement(name = "fulfillment_order_id")
	private String fulfillmentOrderId;
	private long quantity;
	@XmlElement(name = "line_item_id")
	private String lineItemId;
	@XmlElement(name = "inventory_item_id")
	private String inventoryItemId;
	@XmlElement(name = "fulfillable_quantity")
	private long fulfillableQuantity;
	@XmlElement(name = "variant_id")
	private String variantId;
}
