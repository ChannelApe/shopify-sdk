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
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyDraftOrder {

	private String id;
	@XmlElement(name = "order_id")
	private String orderId;
	private String email;
	@XmlElement(name = "completed_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime completedAt;
	@XmlElement(name = "invoice_sent_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime invoiceSentAt;
	@XmlElement(name = "invoice_url")
	private String invoiceUrl;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	private String note;
	@XmlElement(name = "total_price")
	private BigDecimal totalPrice;
	@XmlElement(name = "subtotal_price")
	private BigDecimal subtotalPrice;
	@XmlElement(name = "total_tax")
	private BigDecimal totalTax;
	@XmlElement(name = "taxes_included")
	private boolean taxesIncluded;
	@XmlJavaTypeAdapter(CurrencyAdapter.class)
	private Currency currency;
	private String status;
	private String name;
	@XmlElement(name = "tags")
	private String tags;
	@XmlElement(name = "tax_exempt")
	private boolean taxExempt;
	@XmlElement(name = "tax_exemptions")
	private List<String> taxExemptions = new LinkedList<>();
	@XmlElement(name = "line_items")
	private List<ShopifyLineItem> lineItems;
	@XmlElement(name = "billing_address")
	private ShopifyAddress billingAddress = new ShopifyAddress();
	@XmlElement(name = "shipping_address")
	private ShopifyAddress shippingAddress = new ShopifyAddress();
	private ShopifyCustomer customer = new ShopifyCustomer();
	@XmlElement(name = "shipping_line")
	private ShopifyShippingLine shippingLine;
	@XmlElement(name = "tax_lines")
	private List<ShopifyTaxLine> taxLines = new LinkedList<>();
	@XmlElement(name = "note_attributes")
	private List<ShopifyAttribute> noteAttributes = new LinkedList<>();
	@XmlElement(name = "applied_discount_code")
	private AppliedDiscountCode appliedDiscountCode;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public DateTime getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(final DateTime completedAt) {
		this.completedAt = completedAt;
	}

	public DateTime getInvoiceSentAt() {
		return invoiceSentAt;
	}

	public void setInvoiceSentAt(final DateTime invoiceSentAt) {
		this.invoiceSentAt = invoiceSentAt;
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

	public boolean isTaxExempt() {
		return taxExempt;
	}

	public void setTaxExempt(final boolean taxExempt) {
		this.taxExempt = taxExempt;
	}

	public AppliedDiscountCode getAppliedDiscountCode() {
		return appliedDiscountCode;
	}

	public void setAppliedDiscountCode(final AppliedDiscountCode appliedDiscountCode) {
		this.appliedDiscountCode = appliedDiscountCode;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(final Currency currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}

	public String getInvoiceUrl() {
		return invoiceUrl;
	}

	public void setInvoiceUrl(final String invoiceUrl) {
		this.invoiceUrl = invoiceUrl;
	}

	public List<ShopifyLineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(final List<ShopifyLineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public List<String> getTaxExemptions() {
		return taxExemptions;
	}

	public void setTaxExemptions(final List<String> taxExemptions) {
		this.taxExemptions = taxExemptions;
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

	public ShopifyShippingLine getShippingLine() {
		return shippingLine;
	}

	public void setShippingLine(final ShopifyShippingLine shippingLine) {
		this.shippingLine = shippingLine;
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


}
