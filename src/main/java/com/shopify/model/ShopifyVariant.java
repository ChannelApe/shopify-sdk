package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyVariant {

	private String id;
	@JsonProperty("product_id")
	private String productId;
	private String title;
	private BigDecimal price;
	@JsonProperty("compare_at_price")
	private BigDecimal compareAtPrice;
	private String sku;
	private String barcode;
	private int position;
	private long grams;
	private Long inventoryQuantity;
	@JsonProperty("image_id")
	private String imageId;
	@JsonProperty("inventory_policy")
	private InventoryPolicy inventoryPolicy;
	@JsonProperty("inventory_management")
	private String inventoryManagement;
	private String option1;
	private String option2;
	private String option3;
	@JsonProperty("fulfillment_service")
	private String fulfillmentService;
	@JsonProperty("requires_shipping")
	private boolean requiresShipping;
	private boolean taxable;

	@JsonProperty("inventory_item_id")
	private String inventoryItemId;

	private long available;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(final String productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getCompareAtPrice() {
		return compareAtPrice;
	}

	public void setCompareAtPrice(final BigDecimal compareAtPrice) {
		this.compareAtPrice = compareAtPrice;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(final String sku) {
		this.sku = sku;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(final String barcode) {
		this.barcode = barcode;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(final int position) {
		this.position = position;
	}

	public long getGrams() {
		return grams;
	}

	public void setGrams(final long grams) {
		this.grams = grams;
	}

	public Long getInventoryQuantity() {
		return inventoryQuantity;
	}

	@JsonProperty("inventory_quantity")
	public void setInventoryQuantity(final Long inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(final String imageId) {
		this.imageId = imageId;
	}

	public InventoryPolicy getInventoryPolicy() {
		return inventoryPolicy;
	}

	public void setInventoryPolicy(final InventoryPolicy inventoryPolicy) {
		this.inventoryPolicy = inventoryPolicy;
	}

	public String getInventoryManagement() {
		return inventoryManagement;
	}

	public void setInventoryManagement(final String inventoryManagement) {
		this.inventoryManagement = inventoryManagement;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(final String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(final String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(final String option3) {
		this.option3 = option3;
	}

	public String getFulfillmentService() {
		return fulfillmentService;
	}

	public void setFulfillmentService(final String fulfillmentService) {
		this.fulfillmentService = fulfillmentService;
	}

	public boolean isRequiresShipping() {
		return requiresShipping;
	}

	public void setRequiresShipping(final boolean requiresShipping) {
		this.requiresShipping = requiresShipping;
	}

	public boolean isTaxable() {
		return taxable;
	}

	public void setTaxable(final boolean taxable) {
		this.taxable = taxable;
	}

	public String getInventoryItemId() {
		return inventoryItemId;
	}

	public void setInventoryItemId(final String inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}

	public long getAvailable() {
		return available;
	}

	public void setAvailable(final long available) {
		this.available = available;
	}

}
