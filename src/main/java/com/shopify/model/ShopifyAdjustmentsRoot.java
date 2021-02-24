package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyAdjustmentsRoot {

	private List<ShopifyAdjustment> adjustments = new LinkedList<>();

	public List<ShopifyAdjustment> getAdjustments() {
		return adjustments;
	}

	public void setAdjustments(final List<ShopifyAdjustment> adjustments) {
		this.adjustments = adjustments;
	}
}
