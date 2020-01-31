package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyRefundLineItem {

	private String id;
	private long quantity;
	@JsonProperty("line_item_id")
	private String lineItemId;
	@JsonProperty("location_id")
	private String locationId;
	@JsonProperty("restock_type")
	private String restockType;
	private BigDecimal subtotal;
	@JsonProperty("total_tax")
	private BigDecimal totalTax;
	@JsonProperty("line_item")
	private ShopifyLineItem lineItem;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(final long quantity) {
		this.quantity = quantity;
	}

	public String getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(final String lineItemId) {
		this.lineItemId = lineItemId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(final String locationId) {
		this.locationId = locationId;
	}

	public String getRestockType() {
		return restockType;
	}

	public void setRestockType(final String restockType) {
		this.restockType = restockType;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(final BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(final BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public ShopifyLineItem getLineItem() {
		return lineItem;
	}

	public void setLineItem(final ShopifyLineItem lineItem) {
		this.lineItem = lineItem;
	}

}
