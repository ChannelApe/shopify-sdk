package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;

public class ShopifyDraftOrderRoot {

	@XmlElement(name = "draft_order")
	private ShopifyDraftOrder draftOrder;

	public ShopifyDraftOrder getDraftOrder() {
		return draftOrder;
	}

	public void setDraftOrder(final ShopifyDraftOrder customer) {
		this.draftOrder = draftOrder;
	}

}
