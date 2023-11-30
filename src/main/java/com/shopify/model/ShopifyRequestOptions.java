package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

import com.shopify.model.adapters.DateTimeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyRequestOptions {

	@XmlElement(name = "shipping_method")
	private String shippingMethod;
	private String note;
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime date;

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

}
