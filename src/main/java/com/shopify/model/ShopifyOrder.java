package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyOrder {

	private String id;
	private String email;
	@JsonProperty("closed_at")
	private DateTime closedAt;
	@JsonProperty("created_at")
	private DateTime createdAt;
	@JsonProperty("updated_at")
	private DateTime updatedAt;
	private int number;
	private String note;
	private String token;
	@JsonProperty("total_price")
	private BigDecimal totalPrice;
	@JsonProperty("subtotal_price")
	private BigDecimal subtotalPrice;
	@JsonProperty("total_weight")
	private long totalWeight;
	@JsonProperty("total_tax")
	private BigDecimal totalTax;
	@JsonProperty("taxes_included")
	private boolean taxesIncluded;
	private Currency currency;
	@JsonProperty("financial_status")
	private String financialStatus;
	@JsonProperty("total_discounts")
	private BigDecimal totalDiscounts;
	@JsonProperty("total_line_items_price")
	private BigDecimal totaLineItemsPrice;
	@JsonProperty("cart_token")
	private String cartToken;
	@JsonProperty("buyer_accepts_marketing")
	private boolean buyerAcceptsMarketing;
	private String name;
	@JsonProperty("referring_site")
	private String referringSite;
	@JsonProperty("landing_site")
	private String landingSite;
	@JsonProperty("cancelled_at")
	private DateTime cancelledAt;
	@JsonProperty("cancel_reason")
	private String cancelReason;
	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("location_id")
	private String locationId;
	@JsonProperty("processed_at")
	private DateTime processedAt;
	@JsonProperty("browser_ip")
	private String browserIp;
	@JsonProperty("order_number")
	private String orderNumber;
	@JsonProperty("processing_method")
	private String processingMethod;
	@JsonProperty("source_name")
	private String sourceName;
	@JsonProperty("fulfillment_status")
	private String fulfillmentStatus;
	@JsonProperty("tags")
	private String tags;
	@JsonProperty("order_status_url")
	private String orderStatusUrl;
	@JsonProperty("line_items")
	private List<ShopifyLineItem> lineItems = new LinkedList<>();
	private List<ShopifyFulfillment> fulfillments = new LinkedList<>();
	@JsonProperty("billing_address")
	private ShopifyAddress billingAddress = new ShopifyAddress();
	@JsonProperty("shipping_address")
	private ShopifyAddress shippingAddress = new ShopifyAddress();
	private ShopifyCustomer customer = new ShopifyCustomer();
	@JsonProperty("shipping_lines")
	private List<ShopifyShippingLine> shippingLines = new LinkedList<>();
	@JsonProperty("tax_lines")
	private List<ShopifyTaxLine> taxLines = new LinkedList<>();
	@JsonProperty("note_attributes")
	private List<ShopifyAttribute> noteAttributes = new LinkedList<>();
	private List<ShopifyRefund> refunds = new LinkedList<>();
	private List<Metafield> metafields = new LinkedList<>();

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public DateTime getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(final DateTime closedAt) {
		this.closedAt = closedAt;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final DateTime createdAt) {
		this.createdAt = createdAt;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(final DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	public String getNote() {
		return note;
	}

	public void setNote(final String note) {
		this.note = note;
	}

	public String getToken() {
		return token;
	}

	public void setToken(final String token) {
		this.token = token;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(final BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getSubtotalPrice() {
		return subtotalPrice;
	}

	public void setSubtotalPrice(final BigDecimal subtotalPrice) {
		this.subtotalPrice = subtotalPrice;
	}

	public long getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(final long totalWeight) {
		this.totalWeight = totalWeight;
	}

	public BigDecimal getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(final BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public boolean isTaxesIncluded() {
		return taxesIncluded;
	}

	public void setTaxesIncluded(final boolean taxesIncluded) {
		this.taxesIncluded = taxesIncluded;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(final Currency currency) {
		this.currency = currency;
	}

	public String getFinancialStatus() {
		return financialStatus;
	}

	public void setFinancialStatus(final String financialStatus) {
		this.financialStatus = financialStatus;
	}

	public BigDecimal getTotalDiscounts() {
		return totalDiscounts;
	}

	public void setTotalDiscounts(final BigDecimal totalDiscounts) {
		this.totalDiscounts = totalDiscounts;
	}

	public BigDecimal getTotaLineItemsPrice() {
		return totaLineItemsPrice;
	}

	public void setTotaLineItemsPrice(final BigDecimal totaLineItemsPrice) {
		this.totaLineItemsPrice = totaLineItemsPrice;
	}

	public String getCartToken() {
		return cartToken;
	}

	public void setCartToken(final String cartToken) {
		this.cartToken = cartToken;
	}

	public boolean isBuyerAcceptsMarketing() {
		return buyerAcceptsMarketing;
	}

	public void setBuyerAcceptsMarketing(final boolean buyerAcceptsMarketing) {
		this.buyerAcceptsMarketing = buyerAcceptsMarketing;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getReferringSite() {
		return referringSite;
	}

	public void setReferringSite(final String referringSite) {
		this.referringSite = referringSite;
	}

	public String getLandingSite() {
		return landingSite;
	}

	public void setLandingSite(final String landingSite) {
		this.landingSite = landingSite;
	}

	public DateTime getCancelledAt() {
		return cancelledAt;
	}

	public void setCancelledAt(final DateTime cancelledAt) {
		this.cancelledAt = cancelledAt;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(final String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(final String locationId) {
		this.locationId = locationId;
	}

	public DateTime getProcessedAt() {
		return processedAt;
	}

	public void setProcessedAt(final DateTime processedAt) {
		this.processedAt = processedAt;
	}

	public String getBrowserIp() {
		return browserIp;
	}

	public void setBrowserIp(final String browserIp) {
		this.browserIp = browserIp;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(final String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getProcessingMethod() {
		return processingMethod;
	}

	public void setProcessingMethod(final String processingMethod) {
		this.processingMethod = processingMethod;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(final String sourceName) {
		this.sourceName = sourceName;
	}

	public String getFulfillmentStatus() {
		return fulfillmentStatus;
	}

	public void setFulfillmentStatus(final String fulfillmentStatus) {
		this.fulfillmentStatus = fulfillmentStatus;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}

	public String getOrderStatusUrl() {
		return orderStatusUrl;
	}

	public void setOrderStatusUrl(final String orderStatusUrl) {
		this.orderStatusUrl = orderStatusUrl;
	}

	public List<ShopifyLineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(final List<ShopifyLineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public List<ShopifyFulfillment> getFulfillments() {
		return fulfillments;
	}

	public void setFulfillments(final List<ShopifyFulfillment> fulfillments) {
		this.fulfillments = fulfillments;
	}

	public ShopifyAddress getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(final ShopifyAddress billingAddress) {
		this.billingAddress = billingAddress;
	}

	public ShopifyAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(final ShopifyAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public ShopifyCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(final ShopifyCustomer customer) {
		this.customer = customer;
	}

	public List<ShopifyShippingLine> getShippingLines() {
		return shippingLines;
	}

	public void setShippingLines(final List<ShopifyShippingLine> shippingLines) {
		this.shippingLines = shippingLines;
	}

	public List<ShopifyTaxLine> getTaxLines() {
		return taxLines;
	}

	public void setTaxLines(final List<ShopifyTaxLine> taxLines) {
		this.taxLines = taxLines;
	}

	public List<ShopifyAttribute> getNoteAttributes() {
		return noteAttributes;
	}

	public void setNoteAttributes(final List<ShopifyAttribute> noteAttributes) {
		this.noteAttributes = noteAttributes;
	}

	public List<Metafield> getMetafields() {
		return metafields;
	}

	public void setMetafields(final List<Metafield> metafields) {
		this.metafields = metafields;
	}

	public List<ShopifyRefund> getRefunds() {
		return refunds;
	}

	public void setRefunds(final List<ShopifyRefund> refunds) {
		this.refunds = refunds;
	}
}
