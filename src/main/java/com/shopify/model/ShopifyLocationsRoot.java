package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyLocationsRoot {

	private List<ShopifyLocation> locations = new LinkedList<>();

	public List<ShopifyLocation> getLocations() {
		return locations;
	}

	public void setLocations(final List<ShopifyLocation> locations) {
		this.locations = locations;
	}

}
