package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyErrorsRoot {

	private ShopifyErrors errors = new ShopifyErrors();

	public ShopifyErrors getErrors() {
		return errors;
	}

	public void setErrors(ShopifyErrors errors) {
		this.errors = errors;
	}

}
