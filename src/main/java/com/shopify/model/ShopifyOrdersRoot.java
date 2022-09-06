package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyOrdersRoot {

	private List<ShopifyOrder> orders = new LinkedList<>();

	public List<ShopifyOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<ShopifyOrder> orders) {
		this.orders = orders;
	}

}
