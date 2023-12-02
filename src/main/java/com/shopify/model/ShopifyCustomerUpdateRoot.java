package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyCustomerUpdateRoot {

	private ShopifyCustomerUpdateRequest customer;

	public ShopifyCustomerUpdateRequest getCustomer() {
		return customer;
	}

	public void setCustomer(final ShopifyCustomerUpdateRequest customer) {
		this.customer = customer;
	}

}
