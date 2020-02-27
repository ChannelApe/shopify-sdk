package com.shopify.model;

import java.util.List;

public class ShopifyPage<T> {

	private List<T> items;
	private String nextPageInfo;
	private String previousPageInfo;

	public List<T> getItems() {
		return items;
	}

	public void setItems(final List<T> items) {
		this.items = items;
	}

	public String getNextPageInfo() {
		return nextPageInfo;
	}

	public void setNextPageInfo(final String nextPageInfo) {
		this.nextPageInfo = nextPageInfo;
	}

	public String getPreviousPageInfo() {
		return previousPageInfo;
	}

	public void setPreviousPageInfo(final String previousPageInfo) {
		this.previousPageInfo = previousPageInfo;
	}

}
