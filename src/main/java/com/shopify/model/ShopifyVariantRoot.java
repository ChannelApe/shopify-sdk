package com.shopify.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyVariantRoot {

	private ShopifyVariant variant;

	public ShopifyVariant getVariant() {
		return variant;
	}

	public void setVariant(ShopifyVariant variant) {
		this.variant = variant;
	}
}
