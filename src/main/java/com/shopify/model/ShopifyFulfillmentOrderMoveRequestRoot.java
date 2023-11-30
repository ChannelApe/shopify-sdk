package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyFulfillmentOrderMoveRequestRoot {

	@XmlElement(name = "fulfillment_order")
	private ShopifyFulfillmentOrderMoveLocationPayload fulfillmentOrder;

	public ShopifyFulfillmentOrderMoveLocationPayload getFulfillmentOrder() {
		return fulfillmentOrder;
	}

	public void setFulfillmentOrder(ShopifyFulfillmentOrderMoveLocationPayload fulfillmentOrder) {
		this.fulfillmentOrder = fulfillmentOrder;
	}

}