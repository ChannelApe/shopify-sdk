package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.CurrencyAdapter;
import com.shopify.model.adapters.DateTimeAdapter;
import com.shopify.model.discount.DiscountApplication;
import com.shopify.model.discount.DiscountCode;
import com.shopify.model.price.PriceSet;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties({"client_details", "payment_terms"})
public class ShopifyOrder extends AbstractModel {

	private String id;
	private String email;
	@XmlElement(name = "closed_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime closedAt;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	private int number;
	private String note;
	private String token;
	@XmlElement(name = "total_price")
	private BigDecimal totalPrice;
	@XmlElement(name = "subtotal_price")
	private BigDecimal subtotalPrice;
	@XmlElement(name = "total_weight")
	private long totalWeight;
	@XmlElement(name = "total_tax")
	private BigDecimal totalTax;
	@XmlElement(name = "taxes_included")
	private boolean taxesIncluded;
	@XmlJavaTypeAdapter(CurrencyAdapter.class)
	private Currency currency;
	@XmlElement(name = "financial_status")
	private String financialStatus;
	@XmlElement(name = "total_discounts")
	private BigDecimal totalDiscounts;
	@XmlElement(name = "total_line_items_price")
	private BigDecimal totaLineItemsPrice;
	@XmlElement(name = "cart_token")
	private String cartToken;
	@XmlElement(name = "buyer_accepts_marketing")
	private boolean buyerAcceptsMarketing;
	private String name;
	@XmlElement(name = "referring_site")
	private String referringSite;
	@XmlElement(name = "landing_site")
	private String landingSite;
	@XmlElement(name = "cancelled_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime cancelledAt;
	@XmlElement(name = "cancel_reason")
	private String cancelReason;
	@XmlElement(name = "user_id")
	private String userId;
	@XmlElement(name = "location_id")
	private String locationId;
	@XmlElement(name = "processed_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime processedAt;
	@XmlElement(name = "browser_ip")
	private String browserIp;
	@XmlElement(name = "order_number")
	private String orderNumber;
	@XmlElement(name = "processing_method")
	private String processingMethod;
	@XmlElement(name = "source_name")
	private String sourceName;
	@XmlElement(name = "fulfillment_status")
	private String fulfillmentStatus;
	@XmlElement(name = "tags")
	private String tags;
	@XmlElement(name = "order_status_url")
	private String orderStatusUrl;
	@XmlElement(name = "line_items")
	private List<ShopifyLineItem> lineItems = new LinkedList<>();
	private List<ShopifyFulfillment> fulfillments = new LinkedList<>();
	@XmlElement(name = "billing_address")
	private ShopifyAddress billingAddress = new ShopifyAddress();
	@XmlElement(name = "shipping_address")
	private ShopifyAddress shippingAddress = new ShopifyAddress();
	private ShopifyCustomer customer = new ShopifyCustomer();
	@XmlElement(name = "shipping_lines")
	private List<ShopifyShippingLine> shippingLines = new LinkedList<>();
	@XmlElement(name = "tax_lines")
	private List<ShopifyTaxLine> taxLines = new LinkedList<>();
	@XmlElement(name = "note_attributes")
	private List<ShopifyAttribute> noteAttributes = new LinkedList<>();
	private List<ShopifyRefund> refunds = new LinkedList<>();
	private List<Metafield> metafields = new LinkedList<>();
	@XmlElement(name = "app_id")
	private Long appId;
	@XmlElement(name = "checkout_id")
	private Long checkoutId;
	@XmlElement(name = "checkout_token")
	private String checkoutToken;
	private Boolean confirmed;
	@XmlElement(name = "contact_email")
	private String contactEmail;
	@XmlElement(name = "landing_site_ref")
	private String landingSiteRef;
	@XmlElement(name = "current_subtotal_price")
	private BigDecimal currentSubtotalPrice;
	@XmlElement(name = "current_subtotal_price_set")
	private PriceSet currentSubtotalPriceSet;
	@XmlElement(name = "current_total_discounts")
	private BigDecimal currentTotalDiscounts;
	@XmlElement(name = "current_total_duties_set")
	private PriceSet currentTotalDutiesSet;
	@XmlElement(name = "current_total_discounts_set")
	private PriceSet currentTotalDiscountsSet;
	@XmlElement(name = "current_total_price")
	private BigDecimal currentTotalPrice;
	@XmlElement(name = "current_total_price_set")
	private PriceSet currentTotalPriceSet;
	@XmlElement(name = "current_total_tax")
	private BigDecimal currentTotalTax;
	@XmlElement(name = "current_total_tax_set")
	private PriceSet currentTotalTaxSet;
	@XmlElement(name = "customer_locale")
	private String customerLocale;
	@XmlElement(name = "device_id")
	private String deviceId;
	@XmlElement(name = "discount_codes")
	private List<DiscountCode> discountCodes;
	@XmlElement(name = "estimated_taxes")
	private Boolean estimatedTaxes;
	private String gateway;
	@XmlElement(name = "original_total_duties_set")
	private PriceSet originalTotalDutiesSet;
	@XmlElement(name = "payment_gateway_names")
	private List<String> paymentGatewayNames;
	private String phone;
	@XmlElement(name = "presentment_currency")
	private String presentmentCurrency;
	@XmlElement(name = "reference")
	private String reference;
	@XmlElement(name = "source_identifier")
	private String sourceIdentifier;
	@XmlElement(name = "source_url")
	private String sourceUrl;
	@XmlElement(name = "subtotal_price_set")
	private PriceSet subtotalPriceSet;
	private Boolean test;
	@XmlElement(name = "total_discounts_set")
	private PriceSet totalDiscountsSet;
	@XmlElement(name = "total_line_items_price_set")
	private PriceSet totalLineItemsPriceSet;
	@XmlElement(name = "total_outstanding")
	private BigDecimal totalOutstanding;
	public String getId() {
		return id;
	}
	@XmlElement(name = "total_price_set")
	private PriceSet totalPriceSet;
	@XmlElement(name = "total_price_usd")
	private BigDecimal totalPriceUsd;
	@XmlElement(name = "total_shipping_price_set")
	private PriceSet totalShippingPriceSet;
	@XmlElement(name = "total_tax_set")
	private PriceSet totalTaxSet;
	@XmlElement(name = "total_tip_received")
	private BigDecimal totalTipReceived;
	@XmlElement(name = "discount_applications")
	private List<DiscountApplication> discountApplications;
	@XmlElement(name = "payment_details")
	private PaymentDetails paymentDetails;

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

	public Long getAppId() {
		return appId;
	}

	public void setAppId(final Long appId) {
		this.appId = appId;
	}

	public Long getCheckoutId() {
		return checkoutId;
	}

	public void setCheckoutId(final Long checkoutId) {
		this.checkoutId = checkoutId;
	}

	public String getCheckoutToken() {
		return checkoutToken;
	}

	public void setCheckoutToken(final String checkoutToken) {
		this.checkoutToken = checkoutToken;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(final Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(final String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getLandingSiteRef() {
		return landingSiteRef;
	}

	public void setLandingSiteRef(final String landingSiteRef) {
		this.landingSiteRef = landingSiteRef;
	}

	public BigDecimal getCurrentSubtotalPrice() {
		return currentSubtotalPrice;
	}

	public void setCurrentSubtotalPrice(final BigDecimal currentSubtotalPrice) {
		this.currentSubtotalPrice = currentSubtotalPrice;
	}

	public PriceSet getCurrentSubtotalPriceSet() {
		return currentSubtotalPriceSet;
	}

	public void setCurrentSubtotalPriceSet(final PriceSet currentSubtotalPriceSet) {
		this.currentSubtotalPriceSet = currentSubtotalPriceSet;
	}

	public BigDecimal getCurrentTotalDiscounts() {
		return currentTotalDiscounts;
	}

	public void setCurrentTotalDiscounts(final BigDecimal currentTotalDiscounts) {
		this.currentTotalDiscounts = currentTotalDiscounts;
	}

	public PriceSet getCurrentTotalDiscountsSet() {
		return currentTotalDiscountsSet;
	}

	public void setCurrentTotalDiscountsSet(final PriceSet currentTotalDiscountsSet) {
		this.currentTotalDiscountsSet = currentTotalDiscountsSet;
	}

	public PriceSet getCurrentTotalDutiesSet() {
		return currentTotalDutiesSet;
	}

	public void setCurrentTotalDutiesSet(final PriceSet currentTotalDutiesSet) {
		this.currentTotalDutiesSet = currentTotalDutiesSet;
	}

	public BigDecimal getCurrentTotalPrice() {
		return currentTotalPrice;
	}

	public void setCurrentTotalPrice(final BigDecimal currentTotalPrice) {
		this.currentTotalPrice = currentTotalPrice;
	}

	public PriceSet getCurrentTotalPriceSet() {
		return currentTotalPriceSet;
	}

	public void setCurrentTotalPriceSet(final PriceSet currentTotalPriceSet) {
		this.currentTotalPriceSet = currentTotalPriceSet;
	}

	public BigDecimal getCurrentTotalTax() {
		return currentTotalTax;
	}

	public void setCurrentTotalTax(final BigDecimal currentTotalTax) {
		this.currentTotalTax = currentTotalTax;
	}

	public PriceSet getCurrentTotalTaxSet() {
		return currentTotalTaxSet;
	}

	public void setCurrentTotalTaxSet(final PriceSet currentTotalTaxSet) {
		this.currentTotalTaxSet = currentTotalTaxSet;
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

	public List<DiscountCode> getDiscountCodes() {
		return discountCodes;
	}

	public void setDiscountCodes(final List<DiscountCode> discountCodes) {
		this.discountCodes = discountCodes;
	}

	public Boolean getEstimatedTaxes() {
		return estimatedTaxes;
	}

	public void setEstimatedTaxes(final Boolean estimatedTaxes) {
		this.estimatedTaxes = estimatedTaxes;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(final String gateway) {
		this.gateway = gateway;
	}

	public PriceSet getOriginalTotalDutiesSet() {
		return originalTotalDutiesSet;
	}

	public void setOriginalTotalDutiesSet(final PriceSet originalTotalDutiesSet) {
		this.originalTotalDutiesSet = originalTotalDutiesSet;
	}

	public List<String> getPaymentGatewayNames() {
		return paymentGatewayNames;
	}

	public void setPaymentGatewayNames(final List<String> paymentGatewayNames) {
		this.paymentGatewayNames = paymentGatewayNames;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getPresentmentCurrency() {
		return presentmentCurrency;
	}

	public void setPresentmentCurrency(final String presentmentCurrency) {
		this.presentmentCurrency = presentmentCurrency;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(final String reference) {
		this.reference = reference;
	}

	public String getSourceIdentifier() {
		return sourceIdentifier;
	}

	public void setSourceIdentifier(final String sourceIdentifier) {
		this.sourceIdentifier = sourceIdentifier;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(final String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public PriceSet getSubtotalPriceSet() {
		return subtotalPriceSet;
	}

	public void setSubtotalPriceSet(final PriceSet subtotalPriceSet) {
		this.subtotalPriceSet = subtotalPriceSet;
	}

	public Boolean getTest() {
		return test;
	}

	public void setTest(final Boolean test) {
		this.test = test;
	}

	public PriceSet getTotalDiscountsSet() {
		return totalDiscountsSet;
	}

	public void setTotalDiscountsSet(final PriceSet totalDiscountsSet) {
		this.totalDiscountsSet = totalDiscountsSet;
	}

	public PriceSet getTotalLineItemsPriceSet() {
		return totalLineItemsPriceSet;
	}

	public void setTotalLineItemsPriceSet(final PriceSet totalLineItemsPriceSet) {
		this.totalLineItemsPriceSet = totalLineItemsPriceSet;
	}

	public BigDecimal getTotalOutstanding() {
		return totalOutstanding;
	}

	public void setTotalOutstanding(final BigDecimal totalOutstanding) {
		this.totalOutstanding = totalOutstanding;
	}

	public PriceSet getTotalPriceSet() {
		return totalPriceSet;
	}

	public void setTotalPriceSet(final PriceSet totalPriceSet) {
		this.totalPriceSet = totalPriceSet;
	}

	public BigDecimal getTotalPriceUsd() {
		return totalPriceUsd;
	}

	public void setTotalPriceUsd(final BigDecimal totalPriceUsd) {
		this.totalPriceUsd = totalPriceUsd;
	}

	public PriceSet getTotalShippingPriceSet() {
		return totalShippingPriceSet;
	}

	public void setTotalShippingPriceSet(final PriceSet totalShippingPriceSet) {
		this.totalShippingPriceSet = totalShippingPriceSet;
	}

	public PriceSet getTotalTaxSet() {
		return totalTaxSet;
	}

	public void setTotalTaxSet(final PriceSet totalTaxSet) {
		this.totalTaxSet = totalTaxSet;
	}

	public BigDecimal getTotalTipReceived() {
		return totalTipReceived;
	}

	public void setTotalTipReceived(final BigDecimal totalTipReceived) {
		this.totalTipReceived = totalTipReceived;
	}

	public List<DiscountApplication> getDiscountApplications() {
		return discountApplications;
	}

	public void setDiscountApplications(final List<DiscountApplication> discountApplications) {
		this.discountApplications = discountApplications;
	}

	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(final PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
}
