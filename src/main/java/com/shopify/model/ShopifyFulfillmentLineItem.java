package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyFulfillmentLineItem {
	private String id;
	@XmlElement(name = "shop_id")
	private String shopId;
	@XmlElement(name = "fulfillment_order_id")
	private String fulfillmentOrderId;
	@XmlElement(name = "line_item_id")
	private String lineItemId;
	@XmlElement(name = "inventory_item_id")
	private String inventoryItemId;
	private long quantity;
	@XmlElement(name = "fulfillable_quantity")
	private long fulfillableQuantity;
	@XmlElement(name = "variant_id")
	private String variantId;
}
