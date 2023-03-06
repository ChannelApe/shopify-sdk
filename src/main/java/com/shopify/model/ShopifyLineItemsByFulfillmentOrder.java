package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyLineItemsByFulfillmentOrder {

	@XmlElement(name = "fulfillment_order_id")
	private String fulfillmentOrderId;
	@XmlElement(name = "fulfillment_order_line_items")
	private List<ShopifyFulfillmentOrderPayloadLineItem> fulfillmentOrderLineItems = new LinkedList<>();

	public String getFulfillmentOrderId() {
		return fulfillmentOrderId;
	}

	public void setFulfillmentOrderId(String fulfillmentOrderId) {
		this.fulfillmentOrderId = fulfillmentOrderId;
	}

	public List<ShopifyFulfillmentOrderPayloadLineItem> getFulfillmentOrderLineItems() {
		return fulfillmentOrderLineItems;
	}

	public void setFulfillmentOrderLineItems(List<ShopifyFulfillmentOrderPayloadLineItem> fulfillmentOrderLineItems) {
		this.fulfillmentOrderLineItems = fulfillmentOrderLineItems;
	}
}
