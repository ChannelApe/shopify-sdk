package com.shopify.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ShopifyVariantCreationRequest implements ShopifyVariantRequest {

	private final ShopifyVariant request;
	private final String imageSource;

	public static interface PriceStep {
		public CompareAtPriceStep withPrice(final BigDecimal price);
	}

	public static interface CompareAtPriceStep {
		public SkuStep withCompareAtPrice(final BigDecimal compareAtPrice);

		public SkuStep noCompareAtPrice();
	}

	public static interface SkuStep {
		public BarcodeStep withSku(final String sku);
	}

	public static interface BarcodeStep {
		public WeightStep withBarcode(final String barcode);
	}

	public static interface WeightStep {
		public AvailableStep withWeight(final BigDecimal weight);
	}

	public static interface AvailableStep {
		public FirstOptionStep withAvailable(final long available);
	}

	public static interface FirstOptionStep {
		public SecondOptionStep withFirstOption(final String option);

		public SecondOptionStep noFirstOption();
	}

	public static interface SecondOptionStep {
		public ThirdOptionStep withSecondOption(final String option);

		public ThirdOptionStep noSecondOption();
	}

	public static interface ThirdOptionStep {
		public ImageSourceStep withThirdOption(final String option);

		public ImageSourceStep noThirdOption();
	}

	public static interface ImageSourceStep {
		public InventoryManagementStep withImageSource(final String imageSource);

		public InventoryManagementStep noImageSource();
	}

	public static interface InventoryManagementStep {
		public InventoryPolicyStep withInventoryManagement(final String inventoryManagement);

		public InventoryPolicyStep withDefaultInventoryManagement();
	}

	public static interface InventoryPolicyStep {
		public FulfillmentServiceStep withInventoryPolicy(final InventoryPolicy inventoryPolicy);

		public FulfillmentServiceStep withDefaultInventoryPolicy();
	}

	public static interface FulfillmentServiceStep {
		public RequiresShippingStep withFulfillmentService(final String fulfillmentService);

		public RequiresShippingStep withDefaultFulfillmentService();
	}

	public static interface RequiresShippingStep {
		public TaxableStep withRequiresShipping(final boolean requiresShipping);

		public TaxableStep withRequiresShippingDefault();
	}

	public static interface TaxableStep {
		public BuildStep withTaxable(final boolean taxable);

		public BuildStep withTaxableDefault();
	}

	public static interface BuildStep {
		public ShopifyVariantCreationRequest build();
	}

	public static PriceStep newBuilder() {
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
		return true;
	}

	private ShopifyVariantCreationRequest(final ShopifyVariant shopifyVariant, final String imageSource) {
		this.request = shopifyVariant;
		this.imageSource = imageSource;
	}

	private static class Steps implements PriceStep, CompareAtPriceStep, SkuStep, BarcodeStep, WeightStep,
			AvailableStep, FirstOptionStep, SecondOptionStep, ThirdOptionStep, ImageSourceStep, InventoryManagementStep,
			InventoryPolicyStep, FulfillmentServiceStep, RequiresShippingStep, TaxableStep, BuildStep {
		private static final String DEFAULT_INVENTORY_MANAGEMENT = "shopify";
		private static final InventoryPolicy DEFAULT_INVENTORY_POLICY = InventoryPolicy.DENY;
		private static final String DEFAULT_FULFILLMENT_SERVICE = FulfillmentService.MANUAL.toString();
		private static final boolean REQUIRES_SHIPPING_DEFAULT = true;
		private static final boolean TAXABLE_DEFAULT = true;
		private static final int ZERO = 0;

		private final ShopifyVariant shopifyVariant = new ShopifyVariant();
		private String imageSource;

		@Override
		public ShopifyVariantCreationRequest build() {
			return new ShopifyVariantCreationRequest(shopifyVariant, imageSource);
		}

		@Override
		public ImageSourceStep withThirdOption(final String option) {
			shopifyVariant.setOption3(option);
			return this;
		}

		@Override
		public ImageSourceStep noThirdOption() {
			return this;
		}

		@Override
		public ThirdOptionStep withSecondOption(final String option) {
			shopifyVariant.setOption2(option);
			return this;
		}

		@Override
		public ThirdOptionStep noSecondOption() {
			return this;
		}

		@Override
		public SecondOptionStep withFirstOption(final String option) {
			shopifyVariant.setOption1(option);
			return this;
		}

		@Override
		public SecondOptionStep noFirstOption() {
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
			shopifyVariant.setGrams(grams);
			return this;
		}

		@Override
		public WeightStep withBarcode(final String barcode) {
			shopifyVariant.setBarcode(barcode);
			return this;
		}

		@Override
		public BarcodeStep withSku(final String sku) {
			shopifyVariant.setSku(sku);
			return this;
		}

		@Override
		public CompareAtPriceStep withPrice(final BigDecimal amount) {
			shopifyVariant.setPrice(amount);
			return this;
		}

		@Override
		public SkuStep withCompareAtPrice(final BigDecimal amount) {
			shopifyVariant.setCompareAtPrice(amount);
			return this;
		}

		@Override
		public InventoryManagementStep withImageSource(final String imageSource) {
			this.imageSource = imageSource;
			return this;
		}

		@Override
		public InventoryManagementStep noImageSource() {
			return this;
		}

		@Override
		public SkuStep noCompareAtPrice() {
			return this;
		}

		@Override
		public BuildStep withTaxable(final boolean taxable) {
			shopifyVariant.setTaxable(taxable);
			return this;
		}

		@Override
		public BuildStep withTaxableDefault() {
			shopifyVariant.setTaxable(TAXABLE_DEFAULT);
			return this;
		}

		@Override
		public TaxableStep withRequiresShipping(final boolean requiresShipping) {
			shopifyVariant.setRequiresShipping(requiresShipping);
			return this;
		}

		@Override
		public TaxableStep withRequiresShippingDefault() {
			shopifyVariant.setRequiresShipping(REQUIRES_SHIPPING_DEFAULT);
			return this;
		}

		@Override
		public RequiresShippingStep withFulfillmentService(final String fulfillmentService) {
			shopifyVariant.setFulfillmentService(fulfillmentService);
			return this;
		}

		@Override
		public RequiresShippingStep withDefaultFulfillmentService() {
			shopifyVariant.setFulfillmentService(DEFAULT_FULFILLMENT_SERVICE);
			return this;
		}

		@Override
		public FulfillmentServiceStep withInventoryPolicy(final InventoryPolicy inventoryPolicy) {
			shopifyVariant.setInventoryPolicy(inventoryPolicy);
			return this;
		}

		@Override
		public FulfillmentServiceStep withDefaultInventoryPolicy() {
			shopifyVariant.setInventoryPolicy(DEFAULT_INVENTORY_POLICY);
			return this;
		}

		@Override
		public InventoryPolicyStep withInventoryManagement(final String inventoryManagement) {
			shopifyVariant.setInventoryManagement(inventoryManagement);
			return this;
		}

		@Override
		public InventoryPolicyStep withDefaultInventoryManagement() {
			shopifyVariant.setInventoryManagement(DEFAULT_INVENTORY_MANAGEMENT);
			return this;
		}
	}

}
