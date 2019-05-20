package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyOrderShippingAddressUpdateRequest {

	private String id;
	@XmlElement(name = "shipping_address")
	private ShopifyAddressUpdateRequest shippingAddress;

	public static interface OptionalsStep {
		OptionalsStep withShippingAddress(final ShopifyAddressUpdateRequest shippingAddress);

		ShopifyOrderShippingAddressUpdateRequest build();
	}

	public static interface IdStep {
		OptionalsStep withId(final String id);
	}

	public static IdStep newBuilder() {
		return new Steps();
	}

	private ShopifyOrderShippingAddressUpdateRequest(final Steps steps) {
		this.id = steps.id;
		this.shippingAddress = steps.shippingAddress;
	}

	private static class Steps implements IdStep, OptionalsStep {
		private String id;
		private ShopifyAddressUpdateRequest shippingAddress;

		@Override
		public OptionalsStep withShippingAddress(final ShopifyAddressUpdateRequest shippingAddress) {
			this.shippingAddress = shippingAddress;
			return this;
		}

		@Override
		public ShopifyOrderShippingAddressUpdateRequest build() {
			return new ShopifyOrderShippingAddressUpdateRequest(this);
		}

		@Override
		public OptionalsStep withId(final String id) {
			this.id = id;
			return this;
		}

	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public ShopifyAddressUpdateRequest getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(final ShopifyAddressUpdateRequest shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

}
