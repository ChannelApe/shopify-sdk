package com.shopify.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class ShopifyVariantCreationRequestTest {

	private static final String SOME_SKU = "10004";
	private static final String SOME_BARCODE = "144141141";
	private static final BigDecimal SOME_PRICE_AMOUNT = new BigDecimal("14.99");
	private static final BigDecimal SOME_COMPARE_AT_PRICE_AMOUNT = new BigDecimal("14.99");
	private static final BigDecimal SOME_GRAMS_AMOUNT = new BigDecimal("405.252");
	private static final long SOME_QUANTITY = 25;
	private static final String SOME_FIRST_OPTION = "24 ea";
	private static final String SOME_SECOND_OPTION = "Lemon";
	private static final String SOME_THIRD_OPTION = "Left";
	private static final String SOME_IMAGE_SOURCE = "image1";
	private static final String SOME_INVENTORY_MANAGEMENT = "blank";
	private static final InventoryPolicy SOME_INVENTORY_POLICY = InventoryPolicy.CONTINUE;
	private static final String SOME_FULFILLMENT_SERVICE = "garage";

	@Test
	public void givenValuesSetAndNoCompareAtPriceAndNoImageSourceAndDefaultInventoryPolicyAndDefaultFulfillmentSErviceAndDefaultInventoryManagementAndDefaultRequiresShippingAndDefaultTaxableWhenBuildingShopifyVariantCreationRequestThenExpectCorrectValues() {
		final ShopifyVariantCreationRequest actualShopifyVariantCreationRequest = ShopifyVariantCreationRequest
				.newBuilder().withPrice(SOME_PRICE_AMOUNT).noCompareAtPrice().withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(SOME_GRAMS_AMOUNT).withAvailable(SOME_QUANTITY)
				.withFirstOption(SOME_FIRST_OPTION).withSecondOption(SOME_SECOND_OPTION)
				.withThirdOption(SOME_THIRD_OPTION).noImageSource().withDefaultInventoryManagement()
				.withDefaultInventoryPolicy().withDefaultFulfillmentService().withRequiresShippingDefault()
				.withTaxableDefault().build();

		assertTrue(actualShopifyVariantCreationRequest.hasChanged());
		assertNull(actualShopifyVariantCreationRequest.getImageSource());
		final ShopifyVariant actualShopifyVariant = actualShopifyVariantCreationRequest.getRequest();
		assertEquals(SOME_SKU, actualShopifyVariant.getSku());
		assertEquals(SOME_BARCODE, actualShopifyVariant.getBarcode());
		assertEquals(SOME_PRICE_AMOUNT.toPlainString(), actualShopifyVariant.getPrice().toPlainString());
		assertNull(actualShopifyVariant.getCompareAtPrice());
		assertEquals(405, actualShopifyVariant.getGrams());
		assertEquals(SOME_QUANTITY, actualShopifyVariant.getAvailable());
		assertEquals(SOME_FIRST_OPTION, actualShopifyVariant.getOption1());
		assertEquals(SOME_SECOND_OPTION, actualShopifyVariant.getOption2());
		assertEquals(SOME_THIRD_OPTION, actualShopifyVariant.getOption3());
		assertEquals("shopify", actualShopifyVariant.getInventoryManagement());
		assertEquals(InventoryPolicy.DENY, actualShopifyVariant.getInventoryPolicy());
		assertEquals(FulfillmentService.MANUAL.toString(), actualShopifyVariant.getFulfillmentService());
		assertTrue(actualShopifyVariant.isRequiresShipping());
		assertTrue(actualShopifyVariant.isTaxable());
	}

	@Test
	public void givenValuesSetWhenBuildingShopifyVariantCreationRequestThenExpectCorrectValues() {
		final ShopifyVariantCreationRequest actualShopifyVariantCreationRequest = ShopifyVariantCreationRequest
				.newBuilder().withPrice(SOME_PRICE_AMOUNT).withCompareAtPrice(SOME_COMPARE_AT_PRICE_AMOUNT)
				.withSku(SOME_SKU).withBarcode(SOME_BARCODE).withWeight(SOME_GRAMS_AMOUNT).withAvailable(SOME_QUANTITY)
				.withFirstOption(SOME_FIRST_OPTION).withSecondOption(SOME_SECOND_OPTION)
				.withThirdOption(SOME_THIRD_OPTION).withImageSource(SOME_IMAGE_SOURCE)
				.withInventoryManagement(SOME_INVENTORY_MANAGEMENT).withInventoryPolicy(SOME_INVENTORY_POLICY)
				.withFulfillmentService(SOME_FULFILLMENT_SERVICE).withRequiresShipping(false).withTaxable(false)
				.build();

		assertEquals(SOME_IMAGE_SOURCE, actualShopifyVariantCreationRequest.getImageSource());
		assertTrue(actualShopifyVariantCreationRequest.hasChanged());
		final ShopifyVariant actualShopifyVariant = actualShopifyVariantCreationRequest.getRequest();
		assertEquals(SOME_SKU, actualShopifyVariant.getSku());
		assertEquals(SOME_BARCODE, actualShopifyVariant.getBarcode());
		assertEquals(SOME_PRICE_AMOUNT.toPlainString(), actualShopifyVariant.getPrice().toPlainString());
		assertEquals(SOME_COMPARE_AT_PRICE_AMOUNT.toPlainString(),
				actualShopifyVariant.getCompareAtPrice().toPlainString());
		assertEquals(405, actualShopifyVariant.getGrams());
		assertEquals(SOME_QUANTITY, actualShopifyVariant.getAvailable());
		assertEquals(SOME_FIRST_OPTION, actualShopifyVariant.getOption1());
		assertEquals(SOME_SECOND_OPTION, actualShopifyVariant.getOption2());
		assertEquals(SOME_THIRD_OPTION, actualShopifyVariant.getOption3());
		assertEquals(SOME_INVENTORY_MANAGEMENT, actualShopifyVariant.getInventoryManagement());
		assertEquals(SOME_INVENTORY_POLICY, actualShopifyVariant.getInventoryPolicy());
		assertEquals(SOME_FULFILLMENT_SERVICE, actualShopifyVariant.getFulfillmentService());
		assertFalse(actualShopifyVariant.isRequiresShipping());
		assertFalse(actualShopifyVariant.isTaxable());
	}

}
