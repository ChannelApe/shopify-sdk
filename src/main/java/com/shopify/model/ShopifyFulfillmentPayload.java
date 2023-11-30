package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyFulfillmentPayload {

	private String message;
	@XmlElement(name = "notify_customer")
	private boolean notifyCustomer;
	@XmlElement(name = "tracking_info")
	private ShopifyTrackingInfo trackingInfo;
	@XmlElement(name = "line_items_by_fulfillment_order")
	private List<ShopifyLineItemsByFulfillmentOrder> lineItemsByFulfillmentOrder = new LinkedList<>();
}
