package com.shopify.model;

import java.util.Comparator;

public class ShopifyVariantRequestPositionComparator implements Comparator<ShopifyVariantRequest>

{
	@Override
	public int compare(final ShopifyVariantRequest shopifyVariantRequest,
			final ShopifyVariantRequest otherShopifyVariantRequest) {
		final int position1 = shopifyVariantRequest.getRequest().getPosition();
		final int position2 = otherShopifyVariantRequest.getRequest().getPosition();
		return position1 - position2;

	}
}
