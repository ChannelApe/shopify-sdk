package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
