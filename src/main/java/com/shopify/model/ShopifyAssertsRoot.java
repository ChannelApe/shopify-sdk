package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyAssertsRoot {

	private List<ShopifyAsset> assets = new LinkedList<>();

	public List<ShopifyAsset> getAssets() {
		return assets;
	}

	public void setAssets(List<ShopifyAsset> assets) {
		this.assets = assets;
	}
}
