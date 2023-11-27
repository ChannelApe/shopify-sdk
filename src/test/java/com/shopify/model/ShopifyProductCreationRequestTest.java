package com.shopify.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

public class ShopifyProductCreationRequestTest {

	private static final String SOME_SKU = "10004";
	private static final String SOME_BARCODE = "144141141";
	private static final String SOME_TITLE = "Some Title";
	private static final String SOME_METAFIELDS_GLOBAL_TITLE_TAG = "SEO Title";
	private static final String SOME_METAFIELDS_GLOBAL_DESCRIPTION_TAG = "SEO Description";
	private static final long SOME_QUANTITY = 25;
	private static final String SOME_FIRST_OPTION_NAME = "Color";
	private static final String SOME_SECOND_OPTION_NAME = "Flavor";
	private static final String SOME_THIRD_OPTION_NAME = "Size";
	private static final String SOME_PRODUCT_TYPE = "Energy";
	private static final String SOME_BODY_HTML = "Some description";
	private static final String SOME_VENDOR = "Clif";
	private static final Set<String> SOME_TAGS = new HashSet<>(Arrays.asList("EuropaSports"));
	private static final String SOME_SECOND_IMAGE_SOURCE = "image9191919";
	private static final String SOME_FIRST_IMAGE_SOURCE = "image2133";

	@Test
	public void givenValuesSetAndPublishedWhenBuildingShopifyProductCreationRequestThenExpectCorrectValues() {
		final String firstVariantFirstOptionValue = "Red";
		final String firstVariantSecondOptionValue = "Lemon";
		final String firstVariantThirdOptionValue = "24 ea";
		final ShopifyVariantCreationRequest firstVariantCreationRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.TEN).withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(BigDecimal.ZERO).withAvailable(SOME_QUANTITY)
				.withFirstOption(firstVariantFirstOptionValue).withSecondOption(firstVariantSecondOptionValue)
				.withThirdOption(firstVariantThirdOptionValue).withImageSource(SOME_FIRST_IMAGE_SOURCE)
				.withDefaultInventoryManagement().withDefaultInventoryPolicy().withDefaultFulfillmentService()
				.withRequiresShippingDefault().withTaxableDefault().build();

		final String secondVariantFirstOptionValue = "Green";
		final String secondVariantSecondOptionValue = "Strawberry";
		final String secondVariantThirdOptionValue = "11-t5";
		final ShopifyVariantCreationRequest secondVariantCreationRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.TEN).withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(BigDecimal.ZERO).withAvailable(SOME_QUANTITY)
				.withFirstOption(secondVariantFirstOptionValue).withSecondOption(secondVariantSecondOptionValue)
				.withThirdOption(secondVariantThirdOptionValue).noImageSource().withDefaultInventoryManagement()
				.withDefaultInventoryPolicy().withDefaultFulfillmentService().withRequiresShippingDefault()
				.withTaxableDefault().build();

		final String thirdVariantFirstOptionValue = "Pink";
		final String thirdVariantSecondOptionValue = "Watermelon";
		final String thirdVariantThirdOptionValue = "40 count";
		final ShopifyVariantCreationRequest thirdVariantCreationRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.TEN).withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(BigDecimal.ZERO).withAvailable(SOME_QUANTITY)
				.withFirstOption(thirdVariantFirstOptionValue).withSecondOption(thirdVariantSecondOptionValue)
				.withThirdOption(thirdVariantThirdOptionValue).withImageSource("imageNotFound444")
				.withDefaultInventoryManagement().withDefaultInventoryPolicy().withDefaultFulfillmentService()
				.withRequiresShippingDefault().withTaxableDefault().build();

		final ShopifyProductCreationRequest actualShopifyProductCreationRequest = ShopifyProductCreationRequest
				.newBuilder().withTitle(SOME_TITLE).withMetafieldsGlobalTitleTag(SOME_METAFIELDS_GLOBAL_TITLE_TAG)
				.withProductType(SOME_PRODUCT_TYPE).withBodyHtml(SOME_BODY_HTML)
				.withMetafieldsGlobalDescriptionTag(SOME_METAFIELDS_GLOBAL_DESCRIPTION_TAG).withVendor(SOME_VENDOR)
				.withTags(SOME_TAGS)
				.withSortedOptionNames(
						Arrays.asList(SOME_FIRST_OPTION_NAME, SOME_SECOND_OPTION_NAME, SOME_THIRD_OPTION_NAME))
				.withImageSources(Arrays.asList(SOME_FIRST_IMAGE_SOURCE, SOME_SECOND_IMAGE_SOURCE))
				.withVariantCreationRequests(Arrays.asList(firstVariantCreationRequest, secondVariantCreationRequest,
						thirdVariantCreationRequest))
				.withPublished(true).build();

		final ShopifyProduct actualShopifyProduct = actualShopifyProductCreationRequest.getRequest();
		assertEquals(SOME_TITLE, actualShopifyProduct.getTitle());
		assertEquals(SOME_METAFIELDS_GLOBAL_TITLE_TAG, actualShopifyProduct.getMetafieldsGlobalTitleTag());
		assertEquals(SOME_METAFIELDS_GLOBAL_DESCRIPTION_TAG, actualShopifyProduct.getMetafieldsGlobalDescriptionTag());
		assertEquals(SOME_BODY_HTML, actualShopifyProduct.getBodyHtml());
		assertEquals(SOME_VENDOR, actualShopifyProduct.getVendor());
		assertEquals(SOME_TAGS, actualShopifyProduct.getTags());
		assertEquals(SOME_PRODUCT_TYPE, actualShopifyProduct.getProductType());
		final DateTime actualPublishedAt = DateTime.parse(actualShopifyProduct.getPublishedAt());
		assertTrue(DateTime.now(DateTimeZone.UTC).compareTo(actualPublishedAt) >= 0);
		assertTrue(actualShopifyProduct.isPublished());

		final List<Option> actualOptions = actualShopifyProduct.getOptions();
		assertEquals(3, actualOptions.size());
		assertEquals(SOME_FIRST_OPTION_NAME, actualOptions.get(0).getName());
		assertEquals(1, actualOptions.get(0).getPosition());
		assertEquals(SOME_SECOND_OPTION_NAME, actualOptions.get(1).getName());
		assertEquals(2, actualOptions.get(1).getPosition());
		assertEquals(SOME_THIRD_OPTION_NAME, actualOptions.get(2).getName());
		assertEquals(3, actualOptions.get(2).getPosition());

		final List<Image> actualImages = actualShopifyProduct.getImages();
		assertEquals(2, actualImages.size());
		assertEquals(SOME_FIRST_IMAGE_SOURCE, actualImages.get(0).getSource());
		final List<Metafield> actualFirstImageMetafields = actualImages.get(0).getMetafields();
		assertEquals(1, actualFirstImageMetafields.size());
		assertEquals(SOME_TITLE, actualFirstImageMetafields.get(0).getValue());
		assertEquals(1, actualImages.get(0).getPosition());
		assertEquals(SOME_SECOND_IMAGE_SOURCE, actualImages.get(1).getSource());
		assertEquals(2, actualImages.get(1).getPosition());

		final List<ShopifyVariant> actualShopifyVariants = actualShopifyProduct.getVariants();
		assertEquals(3, actualShopifyVariants.size());
		assertEquals(1, actualShopifyVariants.get(0).getPosition());
		assertSame(secondVariantCreationRequest.getRequest(), actualShopifyVariants.get(0));
		assertEquals(2, actualShopifyVariants.get(1).getPosition());
		assertSame(thirdVariantCreationRequest.getRequest(), actualShopifyVariants.get(1));
		assertEquals(3, actualShopifyVariants.get(2).getPosition());
		assertSame(firstVariantCreationRequest.getRequest(), actualShopifyVariants.get(2));

		assertFalse(actualShopifyProductCreationRequest.hasVariantImagePosition(1));
		assertTrue(actualShopifyProductCreationRequest.hasVariantImagePosition(3));
		final int actualVariantImagePosition = actualShopifyProductCreationRequest.getVariantImagePosition(3);
		assertTrue(actualVariantImagePosition == 1 || actualVariantImagePosition == 2);

		assertTrue(actualShopifyProductCreationRequest.hasChanged());
	}

	@Test
	public void givenValuesSetAndUnpublishedWhenBuildingShopifyProductCreationRequestThenExpectCorrectValues() {
		final String firstVariantFirstOptionValue = "Red";
		final String firstVariantSecondOptionValue = "Lemon";
		final String firstVariantThirdOptionValue = "24 ea";
		final ShopifyVariantCreationRequest firstVariantCreationRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.TEN).withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(BigDecimal.ZERO).withAvailable(SOME_QUANTITY)
				.withFirstOption(firstVariantFirstOptionValue).withSecondOption(firstVariantSecondOptionValue)
				.withThirdOption(firstVariantThirdOptionValue).withImageSource(SOME_FIRST_IMAGE_SOURCE)
				.withDefaultInventoryManagement().withDefaultInventoryPolicy().withDefaultFulfillmentService()
				.withRequiresShippingDefault().withTaxableDefault().build();

		final String secondVariantFirstOptionValue = "Green";
		final String secondVariantSecondOptionValue = "Strawberry";
		final String secondVariantThirdOptionValue = "11-t5";
		final ShopifyVariantCreationRequest secondVariantCreationRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.TEN).withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(BigDecimal.ZERO).withAvailable(SOME_QUANTITY)
				.withFirstOption(secondVariantFirstOptionValue).withSecondOption(secondVariantSecondOptionValue)
				.withThirdOption(secondVariantThirdOptionValue).noImageSource().withDefaultInventoryManagement()
				.withDefaultInventoryPolicy().withDefaultFulfillmentService().withRequiresShippingDefault()
				.withTaxableDefault().build();

		final String thirdVariantFirstOptionValue = "Pink";
		final String thirdVariantSecondOptionValue = "Watermelon";
		final String thirdVariantThirdOptionValue = "40 count";
		final ShopifyVariantCreationRequest thirdVariantCreationRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.TEN).withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(BigDecimal.ZERO).withAvailable(SOME_QUANTITY)
				.withFirstOption(thirdVariantFirstOptionValue).withSecondOption(thirdVariantSecondOptionValue)
				.withThirdOption(thirdVariantThirdOptionValue).withImageSource("imageNotFound444")
				.withDefaultInventoryManagement().withDefaultInventoryPolicy().withDefaultFulfillmentService()
				.withRequiresShippingDefault().withTaxableDefault().build();

		final ShopifyProductCreationRequest actualShopifyProductCreationRequest = ShopifyProductCreationRequest
				.newBuilder().withTitle(SOME_TITLE).withMetafieldsGlobalTitleTag(SOME_METAFIELDS_GLOBAL_TITLE_TAG)
				.withProductType(SOME_PRODUCT_TYPE).withBodyHtml(SOME_BODY_HTML)
				.withMetafieldsGlobalDescriptionTag(SOME_METAFIELDS_GLOBAL_DESCRIPTION_TAG).withVendor(SOME_VENDOR)
				.withTags(SOME_TAGS)
				.withSortedOptionNames(
						Arrays.asList(SOME_FIRST_OPTION_NAME, SOME_SECOND_OPTION_NAME, SOME_THIRD_OPTION_NAME))
				.withImageSources(Arrays.asList(SOME_FIRST_IMAGE_SOURCE, SOME_SECOND_IMAGE_SOURCE))
				.withVariantCreationRequests(Arrays.asList(firstVariantCreationRequest, secondVariantCreationRequest,
						thirdVariantCreationRequest))
				.withPublished(false).build();

		final ShopifyProduct actualShopifyProduct = actualShopifyProductCreationRequest.getRequest();
		assertEquals(SOME_TITLE, actualShopifyProduct.getTitle());
		assertEquals(SOME_METAFIELDS_GLOBAL_TITLE_TAG, actualShopifyProduct.getMetafieldsGlobalTitleTag());
		assertEquals(SOME_METAFIELDS_GLOBAL_DESCRIPTION_TAG, actualShopifyProduct.getMetafieldsGlobalDescriptionTag());
		assertEquals(SOME_BODY_HTML, actualShopifyProduct.getBodyHtml());
		assertEquals(SOME_VENDOR, actualShopifyProduct.getVendor());
		assertEquals(SOME_TAGS, actualShopifyProduct.getTags());
		assertEquals(SOME_PRODUCT_TYPE, actualShopifyProduct.getProductType());
		assertNull(actualShopifyProduct.getPublishedAt());
		assertFalse(actualShopifyProduct.isPublished());

		final List<Option> actualOptions = actualShopifyProduct.getOptions();
		assertEquals(3, actualOptions.size());
		assertEquals(SOME_FIRST_OPTION_NAME, actualOptions.get(0).getName());
		assertEquals(1, actualOptions.get(0).getPosition());
		assertEquals(SOME_SECOND_OPTION_NAME, actualOptions.get(1).getName());
		assertEquals(2, actualOptions.get(1).getPosition());
		assertEquals(SOME_THIRD_OPTION_NAME, actualOptions.get(2).getName());
		assertEquals(3, actualOptions.get(2).getPosition());

		final List<Image> actualImages = actualShopifyProduct.getImages();
		assertEquals(2, actualImages.size());
		assertEquals(SOME_FIRST_IMAGE_SOURCE, actualImages.get(0).getSource());
		final List<Metafield> actualFirstImageMetafields = actualImages.get(0).getMetafields();
		assertEquals(1, actualFirstImageMetafields.size());
		assertEquals(SOME_TITLE, actualFirstImageMetafields.get(0).getValue());
		assertEquals(1, actualImages.get(0).getPosition());
		assertEquals(SOME_SECOND_IMAGE_SOURCE, actualImages.get(1).getSource());
		assertEquals(2, actualImages.get(1).getPosition());

		final List<ShopifyVariant> actualShopifyVariants = actualShopifyProduct.getVariants();
		assertEquals(3, actualShopifyVariants.size());
		assertEquals(1, actualShopifyVariants.get(0).getPosition());
		assertSame(secondVariantCreationRequest.getRequest(), actualShopifyVariants.get(0));
		assertEquals(2, actualShopifyVariants.get(1).getPosition());
		assertSame(thirdVariantCreationRequest.getRequest(), actualShopifyVariants.get(1));
		assertEquals(3, actualShopifyVariants.get(2).getPosition());
		assertSame(firstVariantCreationRequest.getRequest(), actualShopifyVariants.get(2));

		assertFalse(actualShopifyProductCreationRequest.hasVariantImagePosition(1));
		assertTrue(actualShopifyProductCreationRequest.hasVariantImagePosition(3));
		final int actualVariantImagePosition = actualShopifyProductCreationRequest.getVariantImagePosition(3);
		assertTrue(actualVariantImagePosition == 1 || actualVariantImagePosition == 2);

		assertTrue(actualShopifyProductCreationRequest.hasChanged());
	}
}
