package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyLineItem {

	private String id;
	@JsonProperty("variant_id")
	private String variantId;
	private String title;
	private long quantity;
	private BigDecimal price;
	private long grams;
	private String sku;
	@JsonProperty("variant_title")
	private String variantTitle;
	private String vendor;
	@JsonProperty("product_id")
	private String productId;
	@JsonProperty("requires_shipping")
	private boolean requiresShipping;
	private boolean taxable;
	@JsonProperty("gift_card")
	private boolean giftCard;
	private String name;
	@JsonProperty("variant_inventory_management")
	private String variantInventoryManagement;
	@JsonProperty("fulfillable_quantity")
	private long fulfillableQuantity;
	@JsonProperty("total_discount")
	private BigDecimal totalDiscount;
	@JsonProperty("fulfillment_status")
	private String fulfillmentStatus;
	@JsonProperty("fulfillment_service")
	private String fulfillmentService;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getVariantId() {
		return variantId;
	}

	public void setVariantId(final String variantId) {
		this.variantId = variantId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(final long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	public long getGrams() {
		return grams;
	}

	public void setGrams(final long grams) {
		this.grams = grams;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(final String sku) {
		this.sku = sku;
	}

	public String getVariantTitle() {
		return variantTitle;
	}

	public void setVariantTitle(final String variantTitle) {
		this.variantTitle = variantTitle;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(final String vendor) {
		this.vendor = vendor;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(final String productId) {
		this.productId = productId;
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

	public boolean isGiftCard() {
		return giftCard;
	}

	public void setGiftCard(final boolean giftCard) {
		this.giftCard = giftCard;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getVariantInventoryManagement() {
		return variantInventoryManagement;
	}

	public void setVariantInventoryManagement(final String variantInventoryManagement) {
		this.variantInventoryManagement = variantInventoryManagement;
	}

	public long getFulfillableQuantity() {
		return fulfillableQuantity;
	}

	public void setFulfillableQuantity(final long fulfillableQuantity) {
		this.fulfillableQuantity = fulfillableQuantity;
	}

	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(final BigDecimal totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public String getFulfillmentStatus() {
		return fulfillmentStatus;
	}

	public void setFulfillmentStatus(final String fulfillmentStatus) {
		this.fulfillmentStatus = fulfillmentStatus;
	}

	public String getFulfillmentService() {
		return fulfillmentService;
	}

	public void setFulfillmentService(final String fulfillmentService) {
		this.fulfillmentService = fulfillmentService;
	}

}
