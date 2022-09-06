package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyErrors {

	@XmlElement(name = "shipping_address")
	private List<String> shippingAddressErrors = new LinkedList<>();

	public List<String> getShippingAddressErrors() {
		return shippingAddressErrors;
	}

	public void setShippingAddressErrors(final List<String> shippingAddressErrors) {
		this.shippingAddressErrors = shippingAddressErrors;
	}

}
