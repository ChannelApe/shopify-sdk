package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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