package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
