package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyFulfillmentOrderMoveResponseRoot {

	@XmlElement(name = "original_fulfillment_order")
	private ShopifyFulfillmentOrder originalFulfillmentOrder;

	public ShopifyFulfillmentOrder getOriginalFulfillmentOrder() {
		return originalFulfillmentOrder;
	}

	public void setOriginalFulfillmentOrder(ShopifyFulfillmentOrder originalFulfillmentOrder) {
		this.originalFulfillmentOrder = originalFulfillmentOrder;
	}

}