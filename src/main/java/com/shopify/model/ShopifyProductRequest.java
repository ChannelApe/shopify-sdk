package com.shopify.model;

public interface ShopifyProductRequest {

	public ShopifyProduct getRequest();

	public int getVariantImagePosition(final int variantPosition);

	public boolean hasVariantImagePosition(final int variantPosition);

	public boolean hasChanged();

}
