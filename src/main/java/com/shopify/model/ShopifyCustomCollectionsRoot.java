package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyCustomCollectionsRoot {

	@XmlElement(name = "custom_collections")
	private List<ShopifyCustomCollection> customCollections = new LinkedList<ShopifyCustomCollection>();

	public List<ShopifyCustomCollection> getCustomCollections() {
		return customCollections;
	}

	public void setCustomCollections(List<ShopifyCustomCollection> customCollections) {
		this.customCollections = customCollections;
	}
}
