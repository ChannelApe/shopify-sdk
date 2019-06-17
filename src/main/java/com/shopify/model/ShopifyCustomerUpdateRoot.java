package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
