package com.shopify.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyAttribute {

	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
