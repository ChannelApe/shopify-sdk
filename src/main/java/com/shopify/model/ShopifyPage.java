package com.shopify.model;

import java.util.ArrayList;

public class ShopifyPage<T> extends ArrayList<T> {

	private static final long serialVersionUID = 7202410951814178409L;

	private String nextPageInfo;
	private String previousPageInfo;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nextPageInfo == null) ? 0 : nextPageInfo.hashCode());
		result = prime * result + ((previousPageInfo == null) ? 0 : previousPageInfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		return super.equals(obj);
	}

}
