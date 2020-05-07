package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyPriceRuleRoot {

	private ShopifyPriceRule priceRule;

	public ShopifyPriceRule getPriceRule() {
		return priceRule;
	}

	public void setPriceRule(ShopifyPriceRule priceRule) {
		this.priceRule = priceRule;
	}

}
