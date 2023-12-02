package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyFulfillmentOrderRoot {

	@XmlElement(name = "fulfillment_order")
	private ShopifyFulfillmentOrder fulfillmentOrder;

	public ShopifyFulfillmentOrder getFulfillmentOrder() {
		return fulfillmentOrder;
	}

	public void setFulfillmentOrder(ShopifyFulfillmentOrder fulfillmentOrder) {
		this.fulfillmentOrder = fulfillmentOrder;
	}
}
