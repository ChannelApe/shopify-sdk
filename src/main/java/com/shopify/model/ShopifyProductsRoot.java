package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyProductsRoot {

	private List<ShopifyProduct> products = new LinkedList<ShopifyProduct>();

	public List<ShopifyProduct> getProducts() {
		return products;
	}

	public void setProducts(List<ShopifyProduct> products) {
		this.products = products;
	}
}
