package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyLocationsRoot {

	private List<ShopifyLocation> locations = new LinkedList<>();

	public List<ShopifyLocation> getLocations() {
		return locations;
	}

	public void setLocations(final List<ShopifyLocation> locations) {
		this.locations = locations;
	}

}
