package com.shopify.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

public class ShopifyProductUpdateRequestTest {

	private static final String SOME_BARCODE = "144141141";
	private static final long SOME_QUANTITY = 25;

	private static final String SOME_CURRENT_TITLE = "Some Title";
	private static final String SOME_CURRENT_METAFIELDS_GLOBAL_TITLE_TAG = "SEO Title";
	private static final String SOME_CURRENT_METAFIELDS_GLOBAL_DESCRIPTION_TAG = "SEO Description";
	private static final String SOME_CURRENT_FIRST_OPTION_NAME = "Color";
	private static final String SOME_CURRENT_SECOND_OPTION_NAME = "Flavor";
	private static final String SOME_CURRENT_THIRD_OPTION_NAME = "Size";
	private static final String SOME_CURRENT_PRODUCT_TYPE = "Energy";
	private static final String SOME_CURRENT_BODY_HTML = "Some description";
	private static final String SOME_CURRENT_VENDOR = "Clif";
	private static final Set<String> SOME_CURRENT_TAGS = new HashSet<>(Arrays.asList("EuropaSports"));
	private static final String SOME_CURRENT_FIRST_IMAGE_ID = "141414141";
	private static final String SOME_CURRENT_SECOND_IMAGE_ID = "41414141414141";

	private static final String SOME_NEW_TITLE = "Some New Title";
	private static final String SOME_NEW_METAFIELDS_GLOBAL_TITLE_TAG = "SEO New Title";
	private static final String SOME_NEW_METAFIELDS_GLOBAL_DESCRIPTION_TAG = "SEO New Description";
	private static final String SOME_NEW_FIRST_OPTION_NAME = "Colour";
	private static final String SOME_NEW_SECOND_OPTION_NAME = "Taste";
	private static final String SOME_NEW_THIRD_OPTION_NAME = "Measure";
	private static final String SOME_NEW_PRODUCT_TYPE = "Chi";
	private static final String SOME_NEW_BODY_HTML = "Some new and improved description";
	private static final String SOME_SKU = "10004";
	private static final String SOME_NEW_VENDOR = "Clifford";
	private static final Set<String> SOME_NEW_TAGS = new HashSet<>(Arrays.asList("NewEuropaSports"));

	private static final String SOME_FIRST_IMAGE_SOURCE = "image9191919";

	@Test
	public void givenNoNewValuesSetAndPublishedWhenBuildingShopifyProductUpdateRequestThenExpectCorrectValues() {
		final String firstVariantFirstOptionValue = "Green";
		final String firstVariantSecondOptionValue = "Lemon";
		final String firstVariantThirdOptionValue = "24 ea";
		final ShopifyVariantCreationRequest firstVariantCreationRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.TEN).withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(BigDecimal.ZERO).withAvailable(SOME_QUANTITY)
				.withFirstOption(firstVariantFirstOptionValue).withSecondOption(firstVariantSecondOptionValue)
				.withThirdOption(firstVariantThirdOptionValue).noImageSource().withDefaultInventoryManagement()
				.withDefaultInventoryPolicy().withDefaultFulfillmentService().withRequiresShippingDefault()
				.withTaxableDefault().build();

		final String secondVariantFirstOptionValue = "Pink";
		final String secondVariantSecondOptionValue = "Strawberry";
		final String secondVariantThirdOptionValue = "11-t5";
		final ShopifyVariantCreationRequest secondVariantCreationRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.TEN).withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(BigDecimal.ZERO).withAvailable(SOME_QUANTITY)
				.withFirstOption(secondVariantFirstOptionValue).withSecondOption(secondVariantSecondOptionValue)
				.withThirdOption(secondVariantThirdOptionValue).noImageSource().withDefaultInventoryManagement()
				.withDefaultInventoryPolicy().withDefaultFulfillmentService().withRequiresShippingDefault()
				.withTaxableDefault().build();

		final String thirdVariantFirstOptionValue = "Red";
		final String thirdVariantSecondOptionValue = "Watermelon";
		final String thirdVariantThirdOptionValue = "40 count";
		final ShopifyVariantCreationRequest thirdVariantCreationRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.TEN).withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(BigDecimal.ZERO).withAvailable(SOME_QUANTITY)
				.withFirstOption(thirdVariantFirstOptionValue).withSecondOption(thirdVariantSecondOptionValue)
				.withThirdOption(thirdVariantThirdOptionValue).noImageSource().withDefaultInventoryManagement()
				.withDefaultInventoryPolicy().withDefaultFulfillmentService().withRequiresShippingDefault()
				.withTaxableDefault().build();

		final ShopifyProduct currentShopifyProduct = buildCurrentShopifyProduct(firstVariantCreationRequest,
				secondVariantCreationRequest, thirdVariantCreationRequest);

		final ShopifyProductUpdateRequest actualShopifyProductUpdateRequest = ShopifyProductUpdateRequest.newBuilder()
				.withCurrentShopifyProduct(currentShopifyProduct).withSameTitle().withSameMetafieldsGlobalTitleTag()
				.withSameProductType().withSameBodyHtml().withSameMetafieldsGlobalDescriptionTag().withSameVendor()
				.withSameTags().withSameOptions().withSameImages().withSameVariants().withPublished(true).build();

		final ShopifyProduct actualShopifyProduct = actualShopifyProductUpdateRequest.getRequest();
		assertEquals(SOME_CURRENT_TITLE, actualShopifyProduct.getTitle());
		assertEquals(SOME_CURRENT_METAFIELDS_GLOBAL_TITLE_TAG, actualShopifyProduct.getMetafieldsGlobalTitleTag());
		assertEquals(SOME_CURRENT_METAFIELDS_GLOBAL_DESCRIPTION_TAG,
				actualShopifyProduct.getMetafieldsGlobalDescriptionTag());
		assertEquals(SOME_CURRENT_BODY_HTML, actualShopifyProduct.getBodyHtml());
		assertEquals(SOME_CURRENT_VENDOR, actualShopifyProduct.getVendor());
		assertEquals(SOME_CURRENT_TAGS, actualShopifyProduct.getTags());
		assertEquals(SOME_CURRENT_PRODUCT_TYPE, actualShopifyProduct.getProductType());
		final DateTime actualPublishedAt = DateTime.parse(actualShopifyProduct.getPublishedAt());
		assertTrue(DateTime.now(DateTimeZone.UTC).compareTo(actualPublishedAt) >= 0);
		assertTrue(actualShopifyProduct.isPublished());

		final List<Option> actualOptions = actualShopifyProduct.getOptions();
		assertEquals(3, actualOptions.size());
		assertEquals(SOME_CURRENT_FIRST_OPTION_NAME, actualOptions.get(0).getName());
		assertEquals(1, actualOptions.get(0).getPosition());
		assertEquals(SOME_CURRENT_SECOND_OPTION_NAME, actualOptions.get(1).getName());
		assertEquals(2, actualOptions.get(1).getPosition());
		assertEquals(SOME_CURRENT_THIRD_OPTION_NAME, actualOptions.get(2).getName());
		assertEquals(3, actualOptions.get(2).getPosition());

		final List<Image> actualImages = actualShopifyProduct.getImages();
		assertEquals(2, actualImages.size());
		assertTrue(actualImages.get(0).getId().equals(SOME_CURRENT_FIRST_IMAGE_ID)
				|| actualImages.get(0).getId().equals(SOME_CURRENT_SECOND_IMAGE_ID));
		assertTrue(actualImages.get(1).getId().equals(SOME_CURRENT_FIRST_IMAGE_ID)
				|| actualImages.get(1).getId().equals(SOME_CURRENT_SECOND_IMAGE_ID));

		final List<ShopifyVariant> actualShopifyVariants = actualShopifyProduct.getVariants();
		assertEquals(3, actualShopifyVariants.size());
		assertEquals(1, actualShopifyVariants.get(0).getPosition());
		assertSame(firstVariantCreationRequest.getRequest(), actualShopifyVariants.get(0));
		assertEquals(2, actualShopifyVariants.get(1).getPosition());
		assertSame(secondVariantCreationRequest.getRequest(), actualShopifyVariants.get(1));
		assertEquals(3, actualShopifyVariants.get(2).getPosition());
		assertSame(thirdVariantCreationRequest.getRequest(), actualShopifyVariants.get(2));

		assertFalse(actualShopifyProductUpdateRequest.hasVariantImagePosition(1));
		assertFalse(actualShopifyProductUpdateRequest.hasVariantImagePosition(2));
		assertFalse(actualShopifyProductUpdateRequest.hasVariantImagePosition(3));

		assertFalse(actualShopifyProductUpdateRequest.hasChanged());
	}

	@Test
	public void givenAllNewValuesSetAndUnpublishedWhenBuildingShopifyProductUpdateRequestThenExpectCorrectValues() {
		final ShopifyVariantCreationRequest firstVariantCreationRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.TEN).withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(BigDecimal.ZERO).withAvailable(SOME_QUANTITY)
				.withFirstOption("Red").withSecondOption("Lemon").withThirdOption("24 ea").noImageSource()
				.withDefaultInventoryManagement().withDefaultInventoryPolicy().withDefaultFulfillmentService()
				.withRequiresShippingDefault().withTaxableDefault().build();

		final ShopifyVariantCreationRequest secondVariantCreationRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.TEN).withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(BigDecimal.ZERO).withAvailable(SOME_QUANTITY)
				.withFirstOption("Green").withSecondOption("Strawberry").withThirdOption("11-t5").noImageSource()
				.withDefaultInventoryManagement().withDefaultInventoryPolicy().withDefaultFulfillmentService()
				.withRequiresShippingDefault().withTaxableDefault().build();

		final ShopifyVariantCreationRequest thirdVariantCreationRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.TEN).withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(BigDecimal.ZERO).withAvailable(SOME_QUANTITY)
				.withFirstOption("Pink").withSecondOption("Watermelon").withThirdOption("40 count").noImageSource()
				.withDefaultInventoryManagement().withDefaultInventoryPolicy().withDefaultFulfillmentService()
				.withRequiresShippingDefault().withTaxableDefault().build();

		final ShopifyProduct currentShopifyProduct = buildCurrentShopifyProduct(firstVariantCreationRequest,
				secondVariantCreationRequest, thirdVariantCreationRequest);

		final ShopifyVariantRequest firstNewShopifyVariantRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(BigDecimal.TEN).withCompareAtPrice(BigDecimal.TEN).withSku(SOME_SKU)
				.withBarcode(SOME_BARCODE).withWeight(BigDecimal.ZERO).withAvailable(SOME_QUANTITY)
				.withFirstOption("Yellow").withSecondOption("Peach").withThirdOption("12 ea")
				.withImageSource(SOME_FIRST_IMAGE_SOURCE).withDefaultInventoryManagement().withDefaultInventoryPolicy()
				.withDefaultFulfillmentService().withRequiresShippingDefault().withTaxableDefault().build();

		final ShopifyVariantRequest secondNewShopifyVariantRequest = ShopifyVariantUpdateRequest.newBuilder()
				.withCurrentShopifyVariant(secondVariantCreationRequest.getRequest()).withSamePrice()
				.withSameCompareAtPrice().withSameSku().withSameBarcode().withSameWeight().withAvailable(4L)
				.withSameFirstOption().withSameSecondOption().withSameThirdOption().withImageSource("does not exist")
				.withSameInventoryManagement().withSameInventoryPolicy().withSameFulfillmentService()
				.withSameRequiresShipping().withSameTaxable().withSameInventoryItemId().build();

		final List<ShopifyVariantRequest> variantRequests = Arrays.asList(firstNewShopifyVariantRequest,
				secondNewShopifyVariantRequest);
		final ShopifyProductUpdateRequest actualShopifyProductUpdateRequest = ShopifyProductUpdateRequest.newBuilder()
				.withCurrentShopifyProduct(currentShopifyProduct).withTitle(SOME_NEW_TITLE)
				.withMetafieldsGlobalTitleTag(SOME_NEW_METAFIELDS_GLOBAL_TITLE_TAG)
				.withProductType(SOME_NEW_PRODUCT_TYPE).withBodyHtml(SOME_NEW_BODY_HTML)
				.withMetafieldsGlobalDescriptionTag(SOME_NEW_METAFIELDS_GLOBAL_DESCRIPTION_TAG)
				.withVendor(SOME_NEW_VENDOR).withTags(SOME_NEW_TAGS)
				.withSortedOptionNames(Arrays.asList(SOME_NEW_FIRST_OPTION_NAME, SOME_NEW_SECOND_OPTION_NAME,
						SOME_NEW_THIRD_OPTION_NAME))
				.withImageSources(Arrays.asList(SOME_FIRST_IMAGE_SOURCE)).withVariantRequests(variantRequests)
				.withPublished(false).build();

		final ShopifyProduct actualShopifyProduct = actualShopifyProductUpdateRequest.getRequest();
		assertEquals(SOME_NEW_TITLE, actualShopifyProduct.getTitle());
		assertEquals(SOME_NEW_METAFIELDS_GLOBAL_TITLE_TAG, actualShopifyProduct.getMetafieldsGlobalTitleTag());
		assertEquals(SOME_NEW_METAFIELDS_GLOBAL_DESCRIPTION_TAG,
				actualShopifyProduct.getMetafieldsGlobalDescriptionTag());
		assertEquals(SOME_NEW_BODY_HTML, actualShopifyProduct.getBodyHtml());
		assertEquals(SOME_NEW_VENDOR, actualShopifyProduct.getVendor());
		assertEquals(SOME_NEW_TAGS, actualShopifyProduct.getTags());
		assertEquals(SOME_NEW_PRODUCT_TYPE, actualShopifyProduct.getProductType());
		assertNull(actualShopifyProduct.getPublishedAt());
		assertFalse(actualShopifyProduct.isPublished());

		final List<Option> actualOptions = actualShopifyProduct.getOptions();
		assertEquals(3, actualOptions.size());
		assertEquals(SOME_NEW_FIRST_OPTION_NAME, actualOptions.get(0).getName());
		assertEquals(1, actualOptions.get(0).getPosition());
		assertEquals(SOME_NEW_SECOND_OPTION_NAME, actualOptions.get(1).getName());
		assertEquals(2, actualOptions.get(1).getPosition());
		assertEquals(SOME_NEW_THIRD_OPTION_NAME, actualOptions.get(2).getName());
		assertEquals(3, actualOptions.get(2).getPosition());

		final List<Image> actualImages = actualShopifyProduct.getImages();
		assertEquals(1, actualImages.size());
		assertEquals(SOME_FIRST_IMAGE_SOURCE, actualImages.get(0).getSource());
		assertEquals(SOME_NEW_TITLE, actualImages.get(0).getMetafields().get(0).getValue());
		assertEquals(1, actualImages.get(0).getPosition());

		final List<ShopifyVariant> actualShopifyVariants = actualShopifyProduct.getVariants();
		assertEquals(2, actualShopifyVariants.size());
		assertEquals(1, actualShopifyVariants.get(0).getPosition());
		assertSame(secondNewShopifyVariantRequest.getRequest(), actualShopifyVariants.get(0));
		assertEquals(2, actualShopifyVariants.get(1).getPosition());
		assertSame(firstNewShopifyVariantRequest.getRequest(), actualShopifyVariants.get(1));

		assertTrue(actualShopifyProductUpdateRequest.hasVariantImagePosition(2));
		final int actualVariantImagePosition = actualShopifyProductUpdateRequest.getVariantImagePosition(2);
		assertEquals(1, actualVariantImagePosition);
		assertFalse(actualShopifyProductUpdateRequest.hasVariantImagePosition(1));

		assertTrue(actualShopifyProductUpdateRequest.hasChanged());
	}

	private ShopifyProduct buildCurrentShopifyProduct(final ShopifyVariantCreationRequest firstVariantCreationRequest,
			final ShopifyVariantCreationRequest secondVariantCreationRequest,
			final ShopifyVariantCreationRequest thirdVariantCreationRequest) {
		final ShopifyProduct currentShopifyProduct = ShopifyProductCreationRequest.newBuilder()
				.withTitle(SOME_CURRENT_TITLE).withMetafieldsGlobalTitleTag(SOME_CURRENT_METAFIELDS_GLOBAL_TITLE_TAG)
				.withProductType(SOME_CURRENT_PRODUCT_TYPE).withBodyHtml(SOME_CURRENT_BODY_HTML)
				.withMetafieldsGlobalDescriptionTag(SOME_CURRENT_METAFIELDS_GLOBAL_DESCRIPTION_TAG)
				.withVendor(SOME_CURRENT_VENDOR).withTags(SOME_CURRENT_TAGS)
				.withSortedOptionNames(Arrays.asList(SOME_CURRENT_FIRST_OPTION_NAME, SOME_CURRENT_SECOND_OPTION_NAME,
						SOME_CURRENT_THIRD_OPTION_NAME))
				.withImageSources(Collections.emptyList()).withVariantCreationRequests(Arrays
						.asList(firstVariantCreationRequest, secondVariantCreationRequest, thirdVariantCreationRequest))
				.withPublished(true).build().getRequest();
		final Image firstImage = new Image();
		firstImage.setId(SOME_CURRENT_FIRST_IMAGE_ID);
		final Image secondImage = new Image();
		secondImage.setId(SOME_CURRENT_SECOND_IMAGE_ID);
		currentShopifyProduct.getVariants().get(0).setImageId(SOME_CURRENT_FIRST_IMAGE_ID);
		currentShopifyProduct.getVariants().get(1).setImageId(SOME_CURRENT_SECOND_IMAGE_ID);
		final List<Image> images = Arrays.asList(firstImage, secondImage);
		currentShopifyProduct.setImages(images);
		return currentShopifyProduct;
	}

}