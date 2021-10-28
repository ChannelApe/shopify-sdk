package com.shopify.model;

import com.shopify.model.discount.DiscountAllocation;
import com.shopify.model.price.PriceSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyLineItem extends AbstractModel {

	private String id;
	@XmlElement(name = "variant_id")
	private String variantId;
	private String title;
	private long quantity;
	private BigDecimal price;
	private long grams;
	private String sku;
	@XmlElement(name = "variant_title")
	private String variantTitle;
	private String vendor;
	@XmlElement(name = "product_id")
	private String productId;
	@XmlElement(name = "requires_shipping")
	private boolean requiresShipping;
	private boolean taxable;
	@XmlElement(name = "gift_card")
	private boolean giftCard;
	private String name;
	@XmlElement(name = "variant_inventory_management")
	private String variantInventoryManagement;
	@XmlElement(name = "fulfillable_quantity")
	private long fulfillableQuantity;
	@XmlElement(name = "total_discount")
	private BigDecimal totalDiscount;
	@XmlElement(name = "fulfillment_status")
	private String fulfillmentStatus;
	@XmlElement(name = "fulfillment_service")
	private String fulfillmentService;
	@XmlElement(name = "tax_lines")
	private List<ShopifyTaxLine> taxLines = new LinkedList<>();
	@XmlElement(name = "origin_location")
	private ShopifyAddress originLocation;
	@XmlElement(name = "price_set")
	private PriceSet priceSet;
	@XmlElement(name = "discounted_price")
	private BigDecimal discountedPrice;
	@XmlElement(name = "discounted_price_set")
	private PriceSet discountedPriceSet;
	@XmlElement(name = "carrier_identifier")
	private String carrierIdentifier;
	@XmlElement(name = "requested_fulfillment_service_id")
	private String requestedFulfillmentServiceId;
	@XmlElement(name = "product_exists")
	private Boolean productExists;
	private List<Property> properties;
	@XmlElement(name = "total_discount_set")
	private PriceSet totalDiscountSet;
	private List<Duty> duties;
	@XmlElement(name = "discount_allocations")
	private List<DiscountAllocation> discountAllocations;

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

	public List<ShopifyTaxLine> getTaxLines() {
		return taxLines;
	}

	public void setTaxLines(final List<ShopifyTaxLine> taxLines) {
		this.taxLines = taxLines;
	}

	public ShopifyAddress getOriginLocation() {
		return originLocation;
	}

	public void setOriginLocation(final ShopifyAddress originLocation) {
		this.originLocation = originLocation;
	}

	public PriceSet getPriceSet() {
		return priceSet;
	}

	public void setPriceSet(final PriceSet priceSet) {
		this.priceSet = priceSet;
	}

	public BigDecimal getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(final BigDecimal discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public PriceSet getDiscountedPriceSet() {
		return discountedPriceSet;
	}

	public void setDiscountedPriceSet(final PriceSet discountedPriceSet) {
		this.discountedPriceSet = discountedPriceSet;
	}

	public String getCarrierIdentifier() {
		return carrierIdentifier;
	}

	public void setCarrierIdentifier(final String carrierIdentifier) {
		this.carrierIdentifier = carrierIdentifier;
	}

	public String getRequestedFulfillmentServiceId() {
		return requestedFulfillmentServiceId;
	}

	public void setRequestedFulfillmentServiceId(final String requestedFulfillmentServiceId) {
		this.requestedFulfillmentServiceId = requestedFulfillmentServiceId;
	}

	public Boolean getProductExists() {
		return productExists;
	}

	public void setProductExists(final Boolean productExists) {
		this.productExists = productExists;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(final List<Property> properties) {
		this.properties = properties;
	}

	public PriceSet getTotalDiscountSet() {
		return totalDiscountSet;
	}

	public void setTotalDiscountSet(final PriceSet totalDiscountSet) {
		this.totalDiscountSet = totalDiscountSet;
	}

	public List<Duty> getDuties() {
		return duties;
	}

	public void setDuties(final List<Duty> duties) {
		this.duties = duties;
	}

	public List<DiscountAllocation> getDiscountAllocations() {
		return discountAllocations;
	}

	public void setDiscountAllocations(final List<DiscountAllocation> discountAllocations) {
		this.discountAllocations = discountAllocations;
	}
}
