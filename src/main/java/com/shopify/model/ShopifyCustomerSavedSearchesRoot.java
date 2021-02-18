package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyCustomerSavedSearchesRoot {

	@XmlElement(name = "customer_saved_searches")
	private List<ShopifyCustomerSavedSearch> customerSavedSearches = new LinkedList<ShopifyCustomerSavedSearch>();

	public List<ShopifyCustomerSavedSearch> getCustomerSavedSearches() {
		return customerSavedSearches;
	}

	public void setCustomerSavedSearches(List<ShopifyCustomerSavedSearch> customerSavedSearches) {
		this.customerSavedSearches = customerSavedSearches;
	}
}
