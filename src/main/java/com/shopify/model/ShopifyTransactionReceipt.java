package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyTransactionReceipt {

	@XmlElement(name = "apple_pay")
	private boolean applePay;

	public boolean isApplePay() {
		return applePay;
	}

	public void setApplePay(final boolean applePay) {
		this.applePay = applePay;
	}

}
