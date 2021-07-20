package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifySmartCollectionRoot {

	@XmlElement(name = "smart_collection")
	private ShopifySmartCollection smartCollection;

	public ShopifySmartCollection getSmartCollection() {
		return smartCollection;
	}

	public void setSmartCollection(ShopifySmartCollection smartCollection) {
		this.smartCollection = smartCollection;
	}
}
