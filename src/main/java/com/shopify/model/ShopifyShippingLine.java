package com.shopify.model;

import com.shopify.model.discount.DiscountAllocation;
import com.shopify.model.price.PriceSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyShippingLine {

	private String id;
	private String title;
	private BigDecimal price;
	@XmlElement(name = "price_set")
	private PriceSet price_set;
	private String code;
	private String source;
	@XmlElement(name = "delivery_category")
	private String deliveryCategory;
	@XmlElement(name = "carrier_identifier")
	private String carrierIdentifier;
	@XmlElement(name = "discounted_price")
	private BigDecimal discountedPrice;
	@XmlElement(name = "discounted_price_set")
	private PriceSet discountedPriceSet;
	private String phone;
	@XmlElement(name = "requested_fulfillment_service_id")
	private String requestedFulfillmentServiceId;
	@XmlElement(name = "tax_lines")
	private List<ShopifyTaxLine> taxLines;
	@XmlElement(name = "discount_allocations")
	private List<DiscountAllocation> discountAllocations;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
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

	public PriceSet getPrice_set() {
		return price_set;
	}

	public void setPrice_set(final PriceSet price_set) {
		this.price_set = price_set;
	}

	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getSource() {
		return source;
	}

	public void setSource(final String source) {
		this.source = source;
	}

	public String getCarrierIdentifier() {
		return carrierIdentifier;
	}

	public void setCarrierIdentifier(final String carrierIdentifier) {
		this.carrierIdentifier = carrierIdentifier;
	}

	public String getDeliveryCategory() {
		return deliveryCategory;
	}

	public void setDeliveryCategory(final String deliveryCategory) {
		this.deliveryCategory = deliveryCategory;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getRequestedFulfillmentServiceId() {
		return requestedFulfillmentServiceId;
	}

	public void setRequestedFulfillmentServiceId(final String requestedFulfillmentServiceId) {
		this.requestedFulfillmentServiceId = requestedFulfillmentServiceId;
	}

	public List<ShopifyTaxLine> getTaxLines() {
		return taxLines;
	}

	public void setTaxLines(final List<ShopifyTaxLine> taxLines) {
		this.taxLines = taxLines;
	}

	public List<DiscountAllocation> getDiscountAllocations() {
		return discountAllocations;
	}

	public void setDiscountAllocations(final List<DiscountAllocation> discountAllocations) {
		this.discountAllocations = discountAllocations;
	}
}
