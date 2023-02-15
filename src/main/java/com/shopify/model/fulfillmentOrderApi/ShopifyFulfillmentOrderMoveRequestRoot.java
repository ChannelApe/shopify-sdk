package com.shopify.model.fulfillmentOrderApi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyFulfillmentOrderMoveRequestRoot {

	@XmlElement(name = "fulfillment_order")
	private ShopifyFulfillmentOrderPayload fulfillmentOrder;

	public ShopifyFulfillmentOrderPayload getFulfillmentOrder() {
		return fulfillmentOrder;
	}

	public void setFulfillmentOrder(ShopifyFulfillmentOrderPayload fulfillmentOrder) {
		this.fulfillmentOrder = fulfillmentOrder;
	}

}