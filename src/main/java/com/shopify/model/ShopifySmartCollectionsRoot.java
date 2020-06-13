package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifySmartCollectionsRoot {

	@XmlElement(name = "smart_collections")
	private List<ShopifySmartCollection> smartCollections = new LinkedList<>();

	public List<ShopifySmartCollection> getSmartCollections() {
		return smartCollections;
	}

	public void setSmartCollections(List<ShopifySmartCollection> smartCollections) {
		this.smartCollections = smartCollections;
	}
}
