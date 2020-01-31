package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyOrderUpdateRoot {

	private ShopifyOrderShippingAddressUpdateRequest order;

	public ShopifyOrderShippingAddressUpdateRequest getOrder() {
		return order;
	}

	public void setOrder(final ShopifyOrderShippingAddressUpdateRequest order) {
		this.order = order;
	}

}
