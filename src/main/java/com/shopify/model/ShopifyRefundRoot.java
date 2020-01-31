package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyRefundRoot {

	private ShopifyRefund refund;

	public ShopifyRefund getRefund() {
		return refund;
	}

	public void setRefund(final ShopifyRefund refund) {
		this.refund = refund;
	}

}
