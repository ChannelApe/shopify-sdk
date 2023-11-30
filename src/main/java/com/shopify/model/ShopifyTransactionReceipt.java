package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
