package com.shopify.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class ShopifyVariantUpdateRequestTest {

	private static final String SOME_CURRENT_SKU = "10004";
	private static final String SOME_CURRENT_BARCODE = "144141141";
	private static final BigDecimal SOME_CURRENT_PRICE_AMOUNT = new BigDecimal("14.99");
	private static final BigDecimal SOME_CURRENT_COMPARE_AT_PRICE_AMOUNT = new BigDecimal("24.99");
	private static final BigDecimal SOME_CURRENT_GRAMS_AMOUNT = new BigDecimal("405.252");
	private static final long SOME_CURRENT_QUANTITY = 25;
	private static final String SOME_CURRENT_FIRST_OPTION = "24 ea";
	private static final String SOME_CURRENT_SECOND_OPTION = "Lemon";
	private static final String SOME_CURRENT_THIRD_OPTION = "Left";
	private static final String SOME_CURRENT_IMAGE_ID = "1414141";

	private static final String SOME_NEW_SKU = "10005";
	private static final String SOME_NEW_BARCODE = "144141142";
	private static final BigDecimal SOME_NEW_PRICE_AMOUNT = new BigDecimal("15.99");
	private static final BigDecimal SOME_NEW_COMPARE_AT_PRICE_AMOUNT = new BigDecimal("25.99");
	private static final BigDecimal SOME_NEW_GRAMS_AMOUNT = new BigDecimal("406.252");
	private static final long SOME_NEW_QUANTITY = 26;
	private static final String SOME_NEW_FIRST_OPTION = "25 ea";
	private static final String SOME_NEW_SECOND_OPTION = "Lime";
	private static final String SOME_NEW_THIRD_OPTION = "Right";
	private static final String SOME_NEW_IMAGE_SOURCE = "image1";
	private static final String SOME_NEW_INVENTORY_MANAGEMENT = "blank";
	private static final InventoryPolicy SOME_NEW_INVENTORY_POLICY = InventoryPolicy.CONTINUE;
	private static final String SOME_NEW_FULFILLMENT_SERVICE = "garage";

	@Test
	public void givenNoNewValuesSetWhenBuildingShopifyVariantUpdateRequestThenExpectCorrectValues() {
		final ShopifyVariant currentShopifyVariant = buildCurrentShopifyVariant();

		final ShopifyVariantUpdateRequest actualShopifyVariantUpdateRequest = ShopifyVariantUpdateRequest.newBuilder()
				.withCurrentShopifyVariant(currentShopifyVariant).withSamePrice().withSameCompareAtPrice().withSameSku()
				.withSameBarcode().withSameWeight().withAvailable(SOME_CURRENT_QUANTITY).withSameFirstOption()
				.withSameSecondOption().withSameThirdOption().withSameImage().withSameInventoryManagement()
				.withSameInventoryPolicy().withSameFulfillmentService().withSameRequiresShipping().withSameTaxable()
				.withSameInventoryItemId().build();

		assertNull(actualShopifyVariantUpdateRequest.getImageSource());
		assertFalse(actualShopifyVariantUpdateRequest.hasImageSource());
		assertFalse(actualShopifyVariantUpdateRequest.hasChanged());
		final ShopifyVariant actualShopifyVariant = actualShopifyVariantUpdateRequest.getRequest();
		assertEquals(SOME_CURRENT_SKU, actualShopifyVariant.getSku());
		assertEquals(SOME_CURRENT_BARCODE, actualShopifyVariant.getBarcode());
		assertEquals(SOME_CURRENT_PRICE_AMOUNT.toPlainString(), actualShopifyVariant.getPrice().toPlainString());
		assertEquals(SOME_CURRENT_COMPARE_AT_PRICE_AMOUNT.toPlainString(),
				actualShopifyVariant.getCompareAtPrice().toPlainString());
		assertEquals(405, actualShopifyVariant.getGrams());
		assertEquals(SOME_CURRENT_QUANTITY, actualShopifyVariant.getAvailable());
		assertEquals(SOME_CURRENT_FIRST_OPTION, actualShopifyVariant.getOption1());
		assertEquals(SOME_CURRENT_SECOND_OPTION, actualShopifyVariant.getOption2());
		assertEquals(SOME_CURRENT_THIRD_OPTION, actualShopifyVariant.getOption3());
		assertEquals(SOME_CURRENT_IMAGE_ID, actualShopifyVariant.getImageId());
		assertEquals("shopify", actualShopifyVariant.getInventoryManagement());
		assertEquals(InventoryPolicy.DENY, actualShopifyVariant.getInventoryPolicy());
		assertEquals(FulfillmentService.MANUAL.toString(), actualShopifyVariant.getFulfillmentService());
		assertTrue(actualShopifyVariant.isRequiresShipping());
		assertTrue(actualShopifyVariant.isTaxable());
	}

	@Test
	public void givenAllNewValuesSetWhenBuildingShopifyVariantUpdateRequestThenExpectCorrectValues() {
		final ShopifyVariant currentShopifyVariant = buildCurrentShopifyVariant();

		final ShopifyVariantUpdateRequest actualShopifyVariantUpdateRequest = ShopifyVariantUpdateRequest.newBuilder()
				.withCurrentShopifyVariant(currentShopifyVariant).withPrice(SOME_NEW_PRICE_AMOUNT)
				.withCompareAtPrice(SOME_NEW_COMPARE_AT_PRICE_AMOUNT).withSku(SOME_NEW_SKU)
				.withBarcode(SOME_NEW_BARCODE).withWeight(SOME_NEW_GRAMS_AMOUNT).withAvailable(SOME_NEW_QUANTITY)
				.withFirstOption(SOME_NEW_FIRST_OPTION).withSecondOption(SOME_NEW_SECOND_OPTION)
				.withThirdOption(SOME_NEW_THIRD_OPTION).withImageSource(SOME_NEW_IMAGE_SOURCE)
				.withInventoryManagement(SOME_NEW_INVENTORY_MANAGEMENT).withInventoryPolicy(SOME_NEW_INVENTORY_POLICY)
				.withFulfillmentService(SOME_NEW_FULFILLMENT_SERVICE).withRequiresShipping(false).withTaxable(false)
				.withSameInventoryItemId().build();

		assertEquals(SOME_NEW_IMAGE_SOURCE, actualShopifyVariantUpdateRequest.getImageSource());
		assertTrue(actualShopifyVariantUpdateRequest.hasImageSource());
		assertTrue(actualShopifyVariantUpdateRequest.hasChanged());
		final ShopifyVariant actualShopifyVariant = actualShopifyVariantUpdateRequest.getRequest();
		assertNull(actualShopifyVariant.getImageId());
		assertEquals(SOME_NEW_SKU, actualShopifyVariant.getSku());
		assertEquals(SOME_NEW_BARCODE, actualShopifyVariant.getBarcode());
		assertEquals(SOME_NEW_PRICE_AMOUNT.toPlainString(), actualShopifyVariant.getPrice().toPlainString());
		assertEquals(SOME_NEW_COMPARE_AT_PRICE_AMOUNT.toPlainString(),
				actualShopifyVariant.getCompareAtPrice().toPlainString());
		assertEquals(406, actualShopifyVariant.getGrams());
		assertEquals(SOME_NEW_QUANTITY, actualShopifyVariant.getAvailable());
		assertEquals(SOME_NEW_FIRST_OPTION, actualShopifyVariant.getOption1());
		assertEquals(SOME_NEW_SECOND_OPTION, actualShopifyVariant.getOption2());
		assertEquals(SOME_NEW_THIRD_OPTION, actualShopifyVariant.getOption3());
		assertEquals(SOME_NEW_INVENTORY_MANAGEMENT, actualShopifyVariant.getInventoryManagement());
		assertEquals(SOME_NEW_INVENTORY_POLICY, actualShopifyVariant.getInventoryPolicy());
		assertEquals(SOME_NEW_FULFILLMENT_SERVICE, actualShopifyVariant.getFulfillmentService());
		assertFalse(actualShopifyVariant.isRequiresShipping());
		assertFalse(actualShopifyVariant.isTaxable());
	}

	@Test
	public void givenAllNewValuesAndNoOptionsAndNoImageSetWhenBuildingShopifyVariantUpdateRequestWithNullPriceAndNullCompareAtPriceThenExpectCorrectValues() {
		final ShopifyVariant currentShopifyVariant = buildCurrentShopifyVariant();
		currentShopifyVariant.setPrice(null);
		currentShopifyVariant.setCompareAtPrice(null);

		final ShopifyVariantUpdateRequest actualShopifyVariantUpdateRequest = ShopifyVariantUpdateRequest.newBuilder()
				.withCurrentShopifyVariant(currentShopifyVariant).withPrice(SOME_NEW_PRICE_AMOUNT)
				.withCompareAtPrice(SOME_NEW_COMPARE_AT_PRICE_AMOUNT).withSku(SOME_NEW_SKU)
				.withBarcode(SOME_NEW_BARCODE).withWeight(SOME_NEW_GRAMS_AMOUNT).withAvailable(SOME_NEW_QUANTITY)
				.noFirstOption().noSecondOption().noThirdOption().noImage()
				.withInventoryManagement(SOME_NEW_INVENTORY_MANAGEMENT).withInventoryPolicy(SOME_NEW_INVENTORY_POLICY)
				.withFulfillmentService(SOME_NEW_FULFILLMENT_SERVICE).withRequiresShipping(false).withTaxable(false)
				.withSameInventoryItemId().build();

		assertNull(actualShopifyVariantUpdateRequest.getImageSource());
		assertFalse(actualShopifyVariantUpdateRequest.hasImageSource());
		assertTrue(actualShopifyVariantUpdateRequest.hasChanged());
		final ShopifyVariant actualShopifyVariant = actualShopifyVariantUpdateRequest.getRequest();
		assertNull(actualShopifyVariant.getImageId());
		assertEquals(SOME_NEW_SKU, actualShopifyVariant.getSku());
		assertEquals(SOME_NEW_BARCODE, actualShopifyVariant.getBarcode());
		assertEquals(SOME_NEW_PRICE_AMOUNT.toPlainString(), actualShopifyVariant.getPrice().toPlainString());
		assertEquals(SOME_NEW_COMPARE_AT_PRICE_AMOUNT.toPlainString(),
				actualShopifyVariant.getCompareAtPrice().toPlainString());
		assertEquals(406, actualShopifyVariant.getGrams());
		assertEquals(SOME_NEW_QUANTITY, actualShopifyVariant.getAvailable());
		assertNull(actualShopifyVariant.getOption1());
		assertNull(actualShopifyVariant.getOption2());
		assertNull(actualShopifyVariant.getOption3());
		assertEquals(SOME_NEW_INVENTORY_MANAGEMENT, actualShopifyVariant.getInventoryManagement());
		assertEquals(SOME_NEW_INVENTORY_POLICY, actualShopifyVariant.getInventoryPolicy());
		assertEquals(SOME_NEW_FULFILLMENT_SERVICE, actualShopifyVariant.getFulfillmentService());
		assertFalse(actualShopifyVariant.isRequiresShipping());
		assertFalse(actualShopifyVariant.isTaxable());
	}

	private ShopifyVariant buildCurrentShopifyVariant() {
		final ShopifyVariant currentShopifyVariant = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(SOME_CURRENT_PRICE_AMOUNT).withCompareAtPrice(SOME_CURRENT_COMPARE_AT_PRICE_AMOUNT)
				.withSku(SOME_CURRENT_SKU).withBarcode(SOME_CURRENT_BARCODE).withWeight(SOME_CURRENT_GRAMS_AMOUNT)
				.withAvailable(SOME_CURRENT_QUANTITY).withFirstOption(SOME_CURRENT_FIRST_OPTION)
				.withSecondOption(SOME_CURRENT_SECOND_OPTION).withThirdOption(SOME_CURRENT_THIRD_OPTION)
				.withImageSource(SOME_NEW_IMAGE_SOURCE).withDefaultInventoryManagement().withDefaultInventoryPolicy()
				.withDefaultFulfillmentService().withRequiresShippingDefault().withTaxableDefault().build()
				.getRequest();
		currentShopifyVariant.setImageId(SOME_CURRENT_IMAGE_ID);
		return currentShopifyVariant;
	}

}
