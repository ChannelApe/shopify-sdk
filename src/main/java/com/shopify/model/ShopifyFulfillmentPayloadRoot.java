package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyFulfillmentPayloadRoot {

	private ShopifyFulfillmentPayload fulfillment;

	public ShopifyFulfillmentPayload getFulfillment() {
		return fulfillment;
	}

	public void setFulfillment(ShopifyFulfillmentPayload fulfillment) {
		this.fulfillment = fulfillment;
	}
}
