package com.shopify.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public class ShopifyVariantUpdateRequest implements ShopifyVariantRequest {

	private final ShopifyVariant request;
	private final String imageSource;
	private final boolean changed;

	public static interface CurrentShopifyVariantStep {
		public PriceStep withCurrentShopifyVariant(final ShopifyVariant shopifyVariant);
	}

	public static interface PriceStep {
		public CompareAtPriceStep withPrice(final BigDecimal price);

		public CompareAtPriceStep withSamePrice();
	}

	public static interface CompareAtPriceStep {
		public SkuStep withCompareAtPrice(final BigDecimal compareAtPrice);

		public SkuStep withSameCompareAtPrice();
	}

	public static interface SkuStep {
		public BarcodeStep withSku(final String sku);

		public BarcodeStep withSameSku();
	}

	public static interface BarcodeStep {
		public WeightStep withBarcode(final String barcode);

		public WeightStep withSameBarcode();
	}

	public static interface WeightStep {
		public AvailableStep withWeight(final BigDecimal weight);

		public AvailableStep withSameWeight();
	}

	public static interface AvailableStep {
		public FirstOptionStep withAvailable(final long quantity);
	}

	public static interface FirstOptionStep {
		public SecondOptionStep withFirstOption(final String option);

		public SecondOptionStep withSameFirstOption();

		public SecondOptionStep noFirstOption();
	}

	public static interface SecondOptionStep {
		public ThirdOptionStep withSecondOption(final String option);

		public ThirdOptionStep withSameSecondOption();

		public ThirdOptionStep noSecondOption();
	}

	public static interface ThirdOptionStep {
		public ImageSourceStep withThirdOption(final String option);

		public ImageSourceStep withSameThirdOption();

		public ImageSourceStep noThirdOption();
	}

	public static interface ImageSourceStep {
		public InventoryManagementStep withImageSource(final String imageSource);

		public InventoryManagementStep withSameImage();

		public InventoryManagementStep noImage();
	}

	public static interface InventoryManagementStep {
		public InventoryPolicyStep withInventoryManagement(final String inventoryManagement);

		public InventoryPolicyStep withSameInventoryManagement();
	}

	public static interface InventoryPolicyStep {
		public FulfillmentServiceStep withInventoryPolicy(final InventoryPolicy inventoryPolicy);

		public FulfillmentServiceStep withSameInventoryPolicy();
	}

	public static interface FulfillmentServiceStep {
		public RequiresShippingStep withFulfillmentService(final String fulfillmentService);

		public RequiresShippingStep withSameFulfillmentService();
	}

	public static interface RequiresShippingStep {
		public TaxableStep withRequiresShipping(final boolean requiresShipping);

		public TaxableStep withSameRequiresShipping();
	}

	public static interface TaxableStep {
		public InventoryItemIdStep withTaxable(final boolean taxable);

		public InventoryItemIdStep withSameTaxable();
	}

	public static interface InventoryItemIdStep {
		public BuildStep withInventoryItemId(final String inventoryItemId);

		public BuildStep withSameInventoryItemId();
	}

	public static interface BuildStep {
		public ShopifyVariantUpdateRequest build();
	}

	public static CurrentShopifyVariantStep newBuilder() {
		return new Steps();
	}

	@Override
	public ShopifyVariant getRequest() {
		return request;
	}

	@Override
	public String getImageSource() {
		return imageSource;
	}

	@Override
	public boolean hasImageSource() {
		return imageSource != null;
	}

	@Override
	public boolean hasChanged() {
		return changed;
	}

	private ShopifyVariantUpdateRequest(final ShopifyVariant shopifyVariant, final String imageSource,
			final boolean changed) {
		this.request = shopifyVariant;
		this.imageSource = imageSource;
		this.changed = changed;
	}

	private static class Steps implements CurrentShopifyVariantStep, PriceStep, CompareAtPriceStep, SkuStep,
			BarcodeStep, WeightStep, AvailableStep, FirstOptionStep, SecondOptionStep, ThirdOptionStep, ImageSourceStep,
			InventoryManagementStep, InventoryPolicyStep, FulfillmentServiceStep, RequiresShippingStep, TaxableStep,
			InventoryItemIdStep, BuildStep {
		private static final int ZERO = 0;

		private ShopifyVariant shopifyVariant;
		private String imageSource;
		private boolean changed;

		@Override
		public ShopifyVariantUpdateRequest build() {
			return new ShopifyVariantUpdateRequest(shopifyVariant, imageSource, changed);
		}

		@Override
		public ImageSourceStep withThirdOption(final String option) {
			if (doesNotEqual(shopifyVariant.getOption3(), option)) {
				shopifyVariant.setOption3(option);
				changed = true;
			}
			return this;
		}

		@Override
		public ImageSourceStep noThirdOption() {
			if (isNotNull(shopifyVariant.getOption3())) {
				shopifyVariant.setOption3(null);
				changed = true;
			}
			return this;
		}

		@Override
		public ThirdOptionStep withSecondOption(final String option) {
			if (doesNotEqual(shopifyVariant.getOption2(), option)) {
				shopifyVariant.setOption2(option);
				changed = true;
			}
			return this;
		}

		@Override
		public ThirdOptionStep noSecondOption() {
			if (isNotNull(shopifyVariant.getOption2())) {
				shopifyVariant.setOption2(null);
				changed = true;
			}
			return this;
		}

		@Override
		public SecondOptionStep withFirstOption(final String option) {
			if (doesNotEqual(shopifyVariant.getOption1(), option)) {
				shopifyVariant.setOption1(option);
				changed = true;
			}
			return this;
		}

		@Override
		public SecondOptionStep noFirstOption() {
			if (isNotNull(shopifyVariant.getOption1())) {
				shopifyVariant.setOption1(null);
				changed = true;
			}
			return this;
		}

		@Override
		public FirstOptionStep withAvailable(final long available) {
			shopifyVariant.setAvailable(available);
			return this;
		}

		@Override
		public AvailableStep withWeight(final BigDecimal weight) {
			final long grams = weight.setScale(ZERO, RoundingMode.HALF_UP).longValueExact();
			if (doesNotEqual(shopifyVariant.getGrams(), grams)) {
				shopifyVariant.setGrams(grams);
				changed = true;
			}
			return this;
		}

		@Override
		public WeightStep withBarcode(final String barcode) {
			if (doesNotEqual(shopifyVariant.getBarcode(), barcode)) {
				shopifyVariant.setBarcode(barcode);
				changed = true;
			}
			return this;
		}

		@Override
		public BarcodeStep withSku(final String sku) {
			if (doesNotEqual(shopifyVariant.getSku(), sku)) {
				shopifyVariant.setSku(sku);
				changed = true;
			}
			return this;
		}

		@Override
		public CompareAtPriceStep withPrice(final BigDecimal money) {
			if (shopifyVariant.getPrice() == null || ZERO != money.compareTo(shopifyVariant.getPrice())) {
				shopifyVariant.setPrice(money);
				changed = true;
			}
			return this;
		}

		@Override
		public SkuStep withCompareAtPrice(final BigDecimal money) {
			if (shopifyVariant.getCompareAtPrice() == null
					|| ZERO != money.compareTo(shopifyVariant.getCompareAtPrice())) {
				shopifyVariant.setCompareAtPrice(money);
				changed = true;
			}
			return this;
		}

		@Override
		public InventoryManagementStep withImageSource(final String imageSource) {
			this.imageSource = imageSource;
			shopifyVariant.setImageId(null);
			return this;
		}

		@Override
		public InventoryManagementStep noImage() {
			if (isNotNull(shopifyVariant.getImageId())) {
				shopifyVariant.setImageId(null);
				changed = true;
			}
			return this;
		}

		@Override
		public InventoryItemIdStep withTaxable(final boolean taxable) {
			if (doesNotEqual(shopifyVariant.isTaxable(), taxable)) {
				shopifyVariant.setTaxable(taxable);
				changed = true;
			}
			return this;
		}

		@Override
		public TaxableStep withRequiresShipping(final boolean requiresShipping) {
			if (doesNotEqual(shopifyVariant.isRequiresShipping(), requiresShipping)) {
				shopifyVariant.setRequiresShipping(requiresShipping);
				changed = true;
			}
			return this;
		}

		@Override
		public RequiresShippingStep withFulfillmentService(final String fulfillmentService) {
			if (doesNotEqual(shopifyVariant.getFulfillmentService(), fulfillmentService)) {
				shopifyVariant.setFulfillmentService(fulfillmentService);
				changed = true;
			}
			return this;
		}

		@Override
		public FulfillmentServiceStep withInventoryPolicy(final InventoryPolicy inventoryPolicy) {
			if (shopifyVariant.getInventoryPolicy() != inventoryPolicy) {
				shopifyVariant.setInventoryPolicy(inventoryPolicy);
				changed = true;
			}
			return this;
		}

		@Override
		public InventoryPolicyStep withInventoryManagement(final String inventoryManagement) {
			if (doesNotEqual(shopifyVariant.getInventoryManagement(), inventoryManagement)) {
				shopifyVariant.setInventoryManagement(inventoryManagement);
				changed = true;
			}
			return this;
		}

		@Override
		public InventoryItemIdStep withSameTaxable() {
			return this;
		}

		@Override
		public TaxableStep withSameRequiresShipping() {
			return this;
		}

		@Override
		public RequiresShippingStep withSameFulfillmentService() {
			return this;
		}

		@Override
		public FulfillmentServiceStep withSameInventoryPolicy() {
			return this;
		}

		@Override
		public InventoryPolicyStep withSameInventoryManagement() {
			return this;
		}

		@Override
		public InventoryManagementStep withSameImage() {
			return this;
		}

		@Override
		public ImageSourceStep withSameThirdOption() {
			return this;
		}

		@Override
		public ThirdOptionStep withSameSecondOption() {
			return this;
		}

		@Override
		public SecondOptionStep withSameFirstOption() {
			return this;
		}

		@Override
		public AvailableStep withSameWeight() {
			return this;
		}

		@Override
		public WeightStep withSameBarcode() {
			return this;
		}

		@Override
		public BarcodeStep withSameSku() {
			return this;
		}

		@Override
		public CompareAtPriceStep withSamePrice() {
			return this;
		}

		@Override
		public SkuStep withSameCompareAtPrice() {
			return this;
		}

		@Override
		public PriceStep withCurrentShopifyVariant(final ShopifyVariant shopifyVariant) {
			this.shopifyVariant = shopifyVariant;
			return this;
		}

		private boolean doesNotEqual(final String s1, final String s2) {
			final String unescapedS1 = StringEscapeUtils.unescapeHtml4(StringUtils.trim(s1));
			final String unescapedS2 = StringEscapeUtils.unescapeHtml4(StringUtils.trim(s2));
			return !StringUtils.equals(unescapedS1, unescapedS2);
		}

		private boolean doesNotEqual(final long l1, final long l2) {
			return l1 != l2;
		}

		private boolean doesNotEqual(final boolean b1, final boolean b2) {
			return b1 != b2;
		}

		private boolean isNotNull(final Object object) {
			return object != null;
		}

		@Override
		public BuildStep withInventoryItemId(final String inventoryItemId) {
			this.shopifyVariant.setInventoryItemId(inventoryItemId);
			return this;
		}

		@Override
		public BuildStep withSameInventoryItemId() {
			return this;
		}

	}

}
