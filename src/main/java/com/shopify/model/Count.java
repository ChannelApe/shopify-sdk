package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Count {

	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
