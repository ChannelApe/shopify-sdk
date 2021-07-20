package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.EscapedStringAdapter;
import com.shopify.model.adapters.EscapedStringsAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Option {

	private String id;
	@XmlElement(name = "product_id")
	private String productId;
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String name;
	private int position;
	@XmlJavaTypeAdapter(EscapedStringsAdapter.class)
	private List<String> values = new LinkedList<>();

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(final String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(final int position) {
		this.position = position;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(final List<String> values) {
		this.values = values;
	}

}
