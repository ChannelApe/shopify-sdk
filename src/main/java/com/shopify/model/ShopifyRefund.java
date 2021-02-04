package com.shopify.model;

import java.util.Currency;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

import com.shopify.model.adapters.CurrencyAdapter;
import com.shopify.model.adapters.DateTimeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyRefund {

	private String id;
	@XmlElement(name = "order_id")
	private String orderId;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	private String note;
	@XmlElement(name = "user_id")
	private String userId;
	@XmlElement(name = "processed_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime processedAt;
	@XmlElement(name = "refund_line_items")
	private List<ShopifyRefundLineItem> refundLineItems;
	private ShopifyRefundShippingDetails shipping;
	private List<ShopifyTransaction> transactions = new LinkedList<>();
	@XmlElement(name = "order_adjustments")
	private List<ShopifyAdjustment> adjustments = new LinkedList<>();
	@XmlJavaTypeAdapter(CurrencyAdapter.class)
	private Currency currency;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public List<ShopifyRefundLineItem> getRefundLineItems() {
		return refundLineItems;
	}

	public void setRefundLineItems(final List<ShopifyRefundLineItem> refundLineItems) {
		this.refundLineItems = refundLineItems;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final DateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getNote() {
		return note;
	}

	public void setNote(final String note) {
		this.note = note;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public DateTime getProcessedAt() {
		return processedAt;
	}

	public void setProcessedAt(final DateTime processedAt) {
		this.processedAt = processedAt;
	}

	public ShopifyRefundShippingDetails getShipping() {
		return shipping;
	}

	public void setShipping(final ShopifyRefundShippingDetails shipping) {
		this.shipping = shipping;
	}

	public List<ShopifyTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(final List<ShopifyTransaction> transactions) {
		this.transactions = transactions;
	}

	public List<ShopifyAdjustment> getAdjustments() {
		return adjustments;
	}

	public void setAdjustments(final List<ShopifyAdjustment> adjustments) {
		this.adjustments = adjustments;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(final Currency currency) {
		this.currency = currency;
	}

}
