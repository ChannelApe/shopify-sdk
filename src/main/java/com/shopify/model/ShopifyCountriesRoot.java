package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyCountriesRoot {

	private List<ShopifyCountry> countries = new LinkedList<ShopifyCountry>();

	public List<ShopifyCountry> getCountries() {
		return countries;
	}

	public void setCountries(List<ShopifyCountry> countries) {
		this.countries = countries;
	}
}
