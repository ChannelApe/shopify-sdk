package com.shopify.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class ShopifyVariantRequestOption1ComparatorTest {

	private static final int ZERO = 0;

	@Test
	public void givenShopifyVariantRequestWithNullFirstOptionAndOtherShopifyVariantRequestWithNullFirstOptionWhenComparingThenReturnZero() {
		final ShopifyVariantRequestOption1Comparator shopifyVariantRequestOption1Comparator = new ShopifyVariantRequestOption1Comparator();
		final ShopifyVariantRequest shopifyVariantRequest = buildShopifyVariantRequest(null);
		final ShopifyVariantRequest otherShopifyVariantRequest = buildShopifyVariantRequest(null);

		final int comparison = shopifyVariantRequestOption1Comparator.compare(shopifyVariantRequest,
				otherShopifyVariantRequest);

		assertEquals(ZERO, comparison);
	}

	@Test
	public void givenShopifyVariantRequestWithNullFirstOptionAndOtherShopifyVariantRequestWithYellowFirstOptionWhenComparingThenReturnANegativeNumber() {
		final ShopifyVariantRequestOption1Comparator shopifyVariantRequestOption1Comparator = new ShopifyVariantRequestOption1Comparator();
		final ShopifyVariantRequest shopifyVariantRequest = buildShopifyVariantRequest(null);
		final ShopifyVariantRequest otherShopifyVariantRequest = buildShopifyVariantRequest("Yellow");

		final int comparison = shopifyVariantRequestOption1Comparator.compare(shopifyVariantRequest,
				otherShopifyVariantRequest);

		assertTrue(comparison < 0);
	}

	@Test
	public void givenShopifyVariantRequestWithYellowFirstOptionAndOtherShopifyVariantRequestWithNullFirstOptionWhenComparingThenReturnAPositiveNumber() {
		final ShopifyVariantRequestOption1Comparator shopifyVariantRequestOption1Comparator = new ShopifyVariantRequestOption1Comparator();
		final ShopifyVariantRequest shopifyVariantRequest = buildShopifyVariantRequest("Yellow");
		final ShopifyVariantRequest otherShopifyVariantRequest = buildShopifyVariantRequest(null);

		final int comparison = shopifyVariantRequestOption1Comparator.compare(shopifyVariantRequest,
				otherShopifyVariantRequest);

		assertTrue(comparison > 0);
	}

	@Test
	public void givenShopifyVariantRequestWithYellowFirstOptionAndOtherShopifyVariantRequestWithYellowFirstOptionWhenComparingThenReturnZero() {
		final ShopifyVariantRequestOption1Comparator shopifyVariantRequestOption1Comparator = new ShopifyVariantRequestOption1Comparator();
		final ShopifyVariantRequest shopifyVariantRequest = buildShopifyVariantRequest("Yellow");
		final ShopifyVariantRequest otherShopifyVariantRequest = buildShopifyVariantRequest("Yellow");

		final int comparison = shopifyVariantRequestOption1Comparator.compare(shopifyVariantRequest,
				otherShopifyVariantRequest);

		assertEquals(ZERO, comparison);
	}

	@Test
	public void givenShopifyVariantRequestWithyellowFirstOptionAndOtherShopifyVariantRequestWithYellowFirstOptionWhenComparingThenReturnZero() {
		final ShopifyVariantRequestOption1Comparator shopifyVariantRequestOption1Comparator = new ShopifyVariantRequestOption1Comparator();
		final ShopifyVariantRequest shopifyVariantRequest = buildShopifyVariantRequest("yellow");
		final ShopifyVariantRequest otherShopifyVariantRequest = buildShopifyVariantRequest("Yellow");

		final int comparison = shopifyVariantRequestOption1Comparator.compare(shopifyVariantRequest,
				otherShopifyVariantRequest);

		assertEquals(ZERO, comparison);
	}

	@Test
	public void givenShopifyVariantRequestWithRedFirstOptionAndOtherShopifyVariantRequestWithYellowFirstOptionWhenComparingThenReturnANegativeNumber() {
		final ShopifyVariantRequestOption1Comparator shopifyVariantRequestOption1Comparator = new ShopifyVariantRequestOption1Comparator();
		final ShopifyVariantRequest shopifyVariantRequest = buildShopifyVariantRequest("Red");
		final ShopifyVariantRequest otherShopifyVariantRequest = buildShopifyVariantRequest("Yellow");

		final int comparison = shopifyVariantRequestOption1Comparator.compare(shopifyVariantRequest,
				otherShopifyVariantRequest);

		assertTrue(comparison < 0);
	}

	public ShopifyVariantCreationRequest buildShopifyVariantRequest(final String firstOption) {
		return ShopifyVariantCreationRequest.newBuilder().withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.ONE)
				.withSku("1").withBarcode("11").withWeight(BigDecimal.ZERO).withAvailable(4)
				.withFirstOption(firstOption).noSecondOption().noThirdOption().noImageSource()
				.withDefaultInventoryManagement().withDefaultInventoryPolicy().withDefaultFulfillmentService()
				.withRequiresShippingDefault().withTaxableDefault().build();
	}

}
