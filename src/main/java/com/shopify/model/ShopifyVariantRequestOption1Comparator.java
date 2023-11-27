package com.shopify.model;

import java.util.Comparator;

import org.apache.commons.lang3.ObjectUtils;

public class ShopifyVariantRequestOption1Comparator implements Comparator<ShopifyVariantRequest> {
	@Override
	public int compare(final ShopifyVariantRequest shopifyVariantRequest,
			final ShopifyVariantRequest otherShopifyVariantRequest) {
		final String option1 = shopifyVariantRequest.getRequest().getOption1();
		final String otherOption1 = otherShopifyVariantRequest.getRequest().getOption1();
		return (option1 == null || otherOption1 == null) ? ObjectUtils.compare(option1, otherOption1)
				: option1.compareToIgnoreCase(otherOption1);
	}

}
