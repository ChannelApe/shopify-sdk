package com.shopify.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyProductRoot {

	private ShopifyProduct product;

	public ShopifyProduct getProduct() {
		return product;
	}

	public void setProduct(ShopifyProduct product) {
		this.product = product;
	}

}
