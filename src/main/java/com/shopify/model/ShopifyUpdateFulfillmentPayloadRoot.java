package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
