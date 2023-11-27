package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyShop {

	private Shop shop;

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

}
