package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyUpdateFulfillmentPayloadRoot {

	private ShopifyUpdateFulfillmentPayload fulfillment;

	public ShopifyUpdateFulfillmentPayload getFulfillment() {
		return fulfillment;
	}

	public void setFulfillment(ShopifyUpdateFulfillmentPayload fulfillment) {
		this.fulfillment = fulfillment;
	}
}
