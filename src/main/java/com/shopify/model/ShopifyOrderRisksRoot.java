package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyOrderRisksRoot {

	private List<ShopifyOrderRisk> risks = new LinkedList<>();

	public List<ShopifyOrderRisk> getRisks() {
		return risks;
	}

	public void setRisks(List<ShopifyOrderRisk> risks) {
		this.risks = risks;
	}

}
