package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyCustomerSavedSearchRoot {

	@XmlElement(name = "customer_saved_search")
	private ShopifyCustomerSavedSearch customerSavedSearch;

	public ShopifyCustomerSavedSearch getCustomerSavedSearch() {
		return customerSavedSearch;
	}

	public void setCustomerSavedSearch(final ShopifyCustomerSavedSearch customerSavedSearch) {
		this.customerSavedSearch = customerSavedSearch;
	}

}
