package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

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
