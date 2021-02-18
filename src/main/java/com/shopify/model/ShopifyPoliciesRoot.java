package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyPoliciesRoot {

	private List<ShopifyPolicy> policies = new LinkedList<ShopifyPolicy>();

	public List<ShopifyPolicy> getPolicies() {
		return policies;
	}

	public void setPolicies(List<ShopifyPolicy> policies) {
		this.policies = policies;
	}
}
