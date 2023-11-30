package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyFulfillmentOrdersRoot {

	@XmlElement(name = "fulfillment_orders")
	private List<ShopifyFulfillmentOrder> fulfillmentOrders = new LinkedList<>();

	public List<ShopifyFulfillmentOrder> getFulfillmentOrders() {
		return fulfillmentOrders;
	}

	public void setFulfillmentOrders(List<ShopifyFulfillmentOrder> fulfillmentOrders) {
		this.fulfillmentOrders = fulfillmentOrders;
	}
}
