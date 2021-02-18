package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyRefundShippingDetails {

	private BigDecimal amount;
	private BigDecimal tax;
	@XmlElement(name = "maximum_refundable")
	private BigDecimal maximumRefundable;
	@XmlElement(name = "full_refund")
	private boolean fullRefund;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(final BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getMaximumRefundable() {
		return maximumRefundable;
	}

	public void setMaximumRefundable(final BigDecimal maximumRefundable) {
		this.maximumRefundable = maximumRefundable;
	}

	public boolean isFullRefund() {
		return fullRefund;
	}

	public void setFullRefund(final boolean fullRefund) {
		this.fullRefund = fullRefund;
	}

}
