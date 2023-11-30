package com.shopify.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyCustomCollectionRoot {

	@XmlElement(name = "custom_collection")
	private ShopifyCustomCollection customCollection;

	public ShopifyCustomCollection getCustomCollection() {
		return customCollection;
	}

	public void setCustomCollection(ShopifyCustomCollection customCollection) {
		this.customCollection = customCollection;
	}
}
