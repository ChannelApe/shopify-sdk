package com.shopify.model;

import java.math.BigDecimal;
import java.util.Currency;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.shopify.model.adapters.CurrencyAdapter;
import com.shopify.model.adapters.DateTimeAdapter;
import org.joda.time.DateTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyTransaction {

	@XmlElement(name = "order_id")
	private String orderId;
	private String kind;
	private String gateway;
	@XmlElement(name = "parent_id")
	private String parentId;
	private BigDecimal amount;
	@XmlJavaTypeAdapter(CurrencyAdapter.class)
	private Currency currency;
	@XmlElement(name = "maximum_refundable")
	private BigDecimal maximumRefundable;
	private ShopifyTransactionReceipt receipt;
	@XmlElement(name = "location_id")
	private String locationId;
	@XmlElement(name = "device_id")
	private String deviceId;
	private String message;
	@XmlElement(name = "payment_details")
	private Object paymentDetails;
	@XmlElement(name = "source_name")
	private String sourceName;
	@XmlElement(name = "processed_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime processedAt;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	private String id;

	public String getDeviceId() {
		return deviceId;
	}
	public String getId() {
		return id;
	}
	public String getMessage() {
		return message;
	}

	public Object getPaymentDetails() {
		return paymentDetails;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}
	public void setId(final String id) {
		this.id = id;
	}

	public void setDeviceId(final String deviceId) {
		this.deviceId = deviceId;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPaymentDetails(Object paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
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

	public DateTime getProcessedAt() {
		return processedAt;
	}

	public void setProcessedAt(final DateTime processedAt) {
		this.processedAt = processedAt;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final DateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(final String locationId) {
		this.locationId = locationId;
	}


}
