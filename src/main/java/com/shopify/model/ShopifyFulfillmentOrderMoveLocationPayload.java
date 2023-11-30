package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyFulfillmentOrderMoveLocationPayload {

	@XmlElement(name = "new_location_id")
	private String newLocationId;
	@XmlElement(name = "fulfillment_order_line_items")
	private List<ShopifyFulfillmentOrderPayloadLineItem> fulfillmentOrderLineItems = new LinkedList<>();

	public String getNewLocationId() {
		return newLocationId;
	}

	public void setNewLocationId(String newLocationId) {
		this.newLocationId = newLocationId;
	}

	public List<ShopifyFulfillmentOrderPayloadLineItem> getFulfillmentOrderLineItems() {
		return fulfillmentOrderLineItems;
	}

	public void setFulfillmentOrderLineItems(List<ShopifyFulfillmentOrderPayloadLineItem> fulfillmentOrderLineItems) {
		this.fulfillmentOrderLineItems = fulfillmentOrderLineItems;
	}

}