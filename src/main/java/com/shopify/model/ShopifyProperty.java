package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shopify.model.adapters.ShopifyPropertyValueDeserializer;
import com.shopify.model.adapters.ShopifyPropertyValueSerializer;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class ShopifyProperty {

	private String name;

	@JsonSerialize(using = ShopifyPropertyValueSerializer.class)
	@JsonDeserialize(using = ShopifyPropertyValueDeserializer.class)
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
