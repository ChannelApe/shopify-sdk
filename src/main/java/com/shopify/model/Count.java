package com.shopify.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Count {

	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
