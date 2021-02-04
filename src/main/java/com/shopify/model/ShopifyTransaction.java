package com.shopify.model;

import java.math.BigDecimal;
import java.util.Currency;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.shopify.model.adapters.CurrencyAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyTransaction {

	private String id;
	@XmlElement(name = "order_id")
	private String orderId;
	private String kind;
	private String gateway;
	@XmlElement(name = "parent_id")
	private String parentId;
	private String status;
	private String message;
	private BigDecimal amount;
	@XmlJavaTypeAdapter(CurrencyAdapter.class)
	private Currency currency;
	@XmlElement(name = "maximum_refundable")
	private BigDecimal maximumRefundable;
	private ShopifyTransactionReceipt receipt;

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

	public String getKind() {
		return kind;
	}

	public void setKind(final String kind) {
		this.kind = kind;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(final String gateway) {
		this.gateway = gateway;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(final String parentId) {
		this.parentId = parentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getMaximumRefundable() {
		return maximumRefundable;
	}

	public void setMaximumRefundable(final BigDecimal maximumRefundable) {
		this.maximumRefundable = maximumRefundable;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(final Currency currency) {
		this.currency = currency;
	}

	public ShopifyTransactionReceipt getReceipt() {
		return receipt;
	}

	public void setReceipt(final ShopifyTransactionReceipt receipt) {
		this.receipt = receipt;
	}

}
