package com.shopify.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopifyProducts {

	private final Map<String, ShopifyProduct> productIdToShopifyProduct;

	public ShopifyProducts(final List<ShopifyProduct> shopifyProducts) {
		productIdToShopifyProduct = new HashMap<>(shopifyProducts.size());
		shopifyProducts.stream().forEach(shopifyProduct -> {
			productIdToShopifyProduct.put(shopifyProduct.getId(), shopifyProduct);
		});
	}

	public ShopifyProduct get(final String productId) {
		return productIdToShopifyProduct.get(productId);
	}

	public List<ShopifyProduct> values() {
		return new ArrayList<>(productIdToShopifyProduct.values());
	}

	public List<ShopifyVariant> getVariants() {
		final Collection<ShopifyProduct> shopifyProducts = productIdToShopifyProduct.values();
		final List<ShopifyVariant> shopifyVariants = new ArrayList<>(shopifyProducts.size());
		for (ShopifyProduct shopifyProduct : shopifyProducts) {
			shopifyVariants.addAll(shopifyProduct.getVariants());
		}
		return shopifyVariants;
	}

	public int size() {
		return productIdToShopifyProduct.size();
	}

	public boolean containsKey(final String productId) {
		return productIdToShopifyProduct.containsKey(productId);
	}

}
