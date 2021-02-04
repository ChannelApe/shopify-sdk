package com.shopify.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyAdjustment {

	private String id;
	@XmlElement(name = "order_id")
	private String orderId;
	@XmlElement(name = "refund_id")
	private String refundId;
	private BigDecimal amount;
	@XmlElement(name = "tax_amount")
	private BigDecimal taxAmount;
	private String kind;
	private String reason;

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

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(final String refundId) {
		this.refundId = refundId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(final BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(final String kind) {
		this.kind = kind;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

}
