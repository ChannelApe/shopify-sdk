package com.shopify.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyFulfillmentRoot {

	private ShopifyFulfillment fulfillment;

	public ShopifyFulfillment getFulfillment() {
		return fulfillment;
	}

	public void setFulfillment(ShopifyFulfillment fulfillment) {
		this.fulfillment = fulfillment;
	}

}
