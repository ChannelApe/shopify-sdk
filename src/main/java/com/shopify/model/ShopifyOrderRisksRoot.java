package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyOrderRisksRoot {

	private List<ShopifyOrderRisk> risks = new LinkedList<>();

	public List<ShopifyOrderRisk> getRisks() {
		return risks;
	}

	public void setRisks(List<ShopifyOrderRisk> risks) {
		this.risks = risks;
	}

}
