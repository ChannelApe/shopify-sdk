package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.util.Currency;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyRefund {

	private String id;
	@JsonProperty("order_id")
	private String orderId;
	@JsonProperty("created_at")
	private DateTime createdAt;
	private String note;
	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("processed_at")
	private DateTime processedAt;
	@JsonProperty("refund_line_items")
	private List<ShopifyRefundLineItem> refundLineItems;
	private ShopifyRefundShippingDetails shipping;
	private List<ShopifyTransaction> transactions;
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

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(final Currency currency) {
		this.currency = currency;
	}

}
