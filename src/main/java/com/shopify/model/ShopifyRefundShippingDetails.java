package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyRefundShippingDetails {

	private BigDecimal amount;
	private BigDecimal tax;
	@JsonProperty("maximum_refundable")
	private BigDecimal maximumRefundable;
	@JsonProperty("full_refund")
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
