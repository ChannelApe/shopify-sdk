package com.shopify.model;

public class ShopifyOrderUpdateRoot {

	private ShopifyOrderShippingAddressUpdateRequest order;

	public ShopifyOrderShippingAddressUpdateRequest getOrder() {
		return order;
	}

	public void setOrder(final ShopifyOrderShippingAddressUpdateRequest order) {
		this.order = order;
	}

}
