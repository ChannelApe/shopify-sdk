package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isNotifyCustomer() {
		return notifyCustomer;
	}

	public void setNotifyCustomer(boolean notifyCustomer) {
		this.notifyCustomer = notifyCustomer;
	}

	public ShopifyTrackingInfo getTrackingInfo() {
		return trackingInfo;
	}

	public void setTrackingInfo(ShopifyTrackingInfo trackingInfo) {
		this.trackingInfo = trackingInfo;
	}

	public List<ShopifyLineItemsByFulfillmentOrder> getLineItemsByFulfillmentOrder() {
		return lineItemsByFulfillmentOrder;
	}

	public void setLineItemsByFulfillmentOrder(List<ShopifyLineItemsByFulfillmentOrder> lineItemsByFulfillmentOrder) {
		this.lineItemsByFulfillmentOrder = lineItemsByFulfillmentOrder;
	}
}
