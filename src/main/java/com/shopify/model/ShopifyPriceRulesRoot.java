package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyPriceRulesRoot {

	@XmlElement(name = "price_rules")
	private List<ShopifyPriceRule> priceRules = new LinkedList<ShopifyPriceRule>();

	public List<ShopifyPriceRule> getPriceRules() {
		return priceRules;
	}

	public void setPriceRules(List<ShopifyPriceRule> priceRules) {
		this.priceRules = priceRules;
	}
}
