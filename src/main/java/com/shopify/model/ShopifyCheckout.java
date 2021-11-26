package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.CurrencyAdapter;
import com.shopify.model.adapters.DateTimeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyCheckout {
	@XmlElement(name = "abandoned_checkout_url")
	private String abandonedCheckoutUrl;
	@XmlElement(name = "billing_address")
	private ShopifyAddress billingAddress;
	@XmlElement(name = "buyer_accepts_marketing")
	private boolean buyerAcceptsMarketing;
	@XmlElement(name = "cart_token")
	private String cartToken;
	@XmlElement(name = "closed_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime closedAt;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "completed_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime completedAt;
	@XmlJavaTypeAdapter(CurrencyAdapter.class)
	private Currency currency;
	private ShopifyCustomer customer;
	@XmlElement(name = "customer_locale")
	private String customerLocale;
	@XmlElement(name = "device_id")
	private String deviceId;
	private String email;
	private String gateway;
	private String id;
	@XmlElement(name = "landing_site")
	private String landingSite;
	@XmlElement(name = "line_items")
	private List<ShopifyLineItem> lineItems;
	@XmlElement(name = "location_id")
	private String locationId;
	private String note;
	private String phone;
	@XmlJavaTypeAdapter(CurrencyAdapter.class)
	@XmlElement(name = "presentment_currency")
	private Currency presentmentCurrency;
	@XmlElement(name = "referring_site")
	private String referringSite;
	@XmlElement(name = "shipping_address")
	private ShopifyAddress shippingAddress;
	@XmlElement(name = "shipping_lines")
	private List<ShopifyShippingLine> shippingLines;
	@XmlElement(name = "discount_codes")
	private List<ShopifyDiscountCode> shopifyDiscountCodes;
	@XmlElement(name = "source_name")
	private String sourceName;
	@XmlElement(name = "subtotal_price")
	private BigDecimal subtotalPrice;
	@XmlElement(name = "tax_lines")
	private List<ShopifyTaxLine> taxLines;
	@XmlElement(name = "taxes_included")
	private boolean taxesIncluded;
	private String token;
	@XmlElement(name = "total_discounts")
	private BigDecimal totalDiscounts;
	@XmlElement(name = "total_duties")
	private BigDecimal totalDuties;
	@XmlElement(name = "total_line_items_price")
	private BigDecimal totalLineItemsPrice;
	@XmlElement(name = "total_price")
	private BigDecimal totalPrice;
	@XmlElement(name = "total_tax")
	private BigDecimal totalTax;
	@XmlElement(name = "total_weight")
	private long totalWeight;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	@XmlElement(name = "user_id")
	private String userId;

	@XmlElement(name = "note_attributes")
	private List<Map<String, Object>> noteAttributes;
	@XmlElement(name = "source_url")
	private String sourceUrl;
	@XmlElement(name = "source_identifier")
	private String sourceIdentifier;
	private String name;
	private String source;
	@XmlElement(name = "sms_marketing_phone")
	private String smsMarketingPhone;
	@XmlElement(name = "buyer_accepts_sms_marketing")
	private Boolean buyerAcceptsSmsMarketing;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getAbandonedCheckoutUrl() {
		return abandonedCheckoutUrl;
	}

	public void setAbandonedCheckoutUrl(final String abandonedCheckoutUrl) {
		this.abandonedCheckoutUrl = abandonedCheckoutUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getCustomerLocale() {
		return customerLocale;
	}

	public void setCustomerLocale(final String customerLocale) {
		this.customerLocale = customerLocale;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(final String deviceId) {
		this.deviceId = deviceId;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(final String gateway) {
		this.gateway = gateway;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public DateTime getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(final DateTime closedAt) {
		this.closedAt = closedAt;
	}

	public DateTime getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(final DateTime completedAt) {
		this.completedAt = completedAt;
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

	public Currency getPresentmentCurrency() {
		return presentmentCurrency;
	}

	public void setPresentmentCurrency(final Currency presentmentCurrency) {
		this.presentmentCurrency = presentmentCurrency;
	}

	public BigDecimal getTotalDiscounts() {
		return totalDiscounts;
	}

	public void setTotalDiscounts(final BigDecimal totalDiscounts) {
		this.totalDiscounts = totalDiscounts;
	}

	public BigDecimal getTotalDuties() {
		return totalDuties;
	}

	public void setTotalDuties(final BigDecimal totalDuties) {
		this.totalDuties = totalDuties;
	}

	public BigDecimal getTotalLineItemsPrice() {
		return totalLineItemsPrice;
	}

	public void setTotalLineItemsPrice(final BigDecimal totalLineItemsPrice) {
		this.totalLineItemsPrice = totalLineItemsPrice;
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

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(final String sourceName) {
		this.sourceName = sourceName;
	}

	public List<ShopifyLineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(final List<ShopifyLineItem> lineItems) {
		this.lineItems = lineItems;
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

	public List<ShopifyDiscountCode> getShopifyDiscountCodes() {
		return shopifyDiscountCodes;
	}

	public void setShopifyDiscountCodes(List<ShopifyDiscountCode> shopifyDiscountCodes) {
		this.shopifyDiscountCodes = shopifyDiscountCodes;
	}

	public List<ShopifyTaxLine> getTaxLines() {
		return taxLines;
	}

	public void setTaxLines(final List<ShopifyTaxLine> taxLines) {
		this.taxLines = taxLines;
	}

	public List<Map<String, Object>> getNoteAttributes() {
		return noteAttributes;
	}

	public void setNoteAttributes(List<Map<String, Object>> noteAttributes) {
		this.noteAttributes = noteAttributes;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getSourceIdentifier() {
		return sourceIdentifier;
	}

	public void setSourceIdentifier(String sourceIdentifier) {
		this.sourceIdentifier = sourceIdentifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSmsMarketingPhone() {
		return smsMarketingPhone;
	}

	public void setSmsMarketingPhone(String smsMarketingPhone) {
		this.smsMarketingPhone = smsMarketingPhone;
	}

	public Boolean getBuyerAcceptsSmsMarketing() {
		return buyerAcceptsSmsMarketing;
	}

	public void setBuyerAcceptsSmsMarketing(Boolean buyerAcceptsSmsMarketing) {
		this.buyerAcceptsSmsMarketing = buyerAcceptsSmsMarketing;
	}
}
