package com.shopify.model;

import com.shopify.mappers.TransactionErrorCodeAdapter;
import com.shopify.mappers.TransactionKindAdapter;
import com.shopify.mappers.TransactionStatusAdapter;
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

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyTransaction {
	private String id;
	@XmlElement(name = "order_id")
	private String orderId;
	@XmlJavaTypeAdapter(TransactionKindAdapter.class)
	private TransactionKind kind;
	private String gateway;
	private String authorization;
	@XmlElement(name = "authorization_expires_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private String authorizationExpiresAt;
	@XmlElement(name = "extended_authorization_attributes")
	private ExtendedAuthorizationAttributes extendedAuthorizationAttributes;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "location_id")
	private String locationId;
	@XmlElement(name = "payment_details")
	private PaymentDetails paymentDetails;

	@XmlElement(name = "payments_refund_attributes")
	private PaymentsRefundAttributes paymentsRefundAttributes;
	@XmlElement(name = "processed_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime processedAt;

	@XmlElement(name = "source_name")
	private String sourceName;

	@XmlElement(name = "user_id")
	private String userId;
	@XmlElement(name = "currency_exchange_adjustment")
	private CurrencyExchangeAdjustment currencyExchangeAdjustment;
	@XmlElement(name = "parent_id")
	private String parentId;
	@XmlElement(name = "device_id")
	private String deviceId;
	@XmlJavaTypeAdapter(TransactionStatusAdapter.class)
	private TransactionStatus status;
	@XmlElement(name = "error_code")
	@XmlJavaTypeAdapter(TransactionErrorCodeAdapter.class)
	private TransactionErrorCode errorCode;
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

	public TransactionKind getKind() {
		return kind;
	}

	public void setKind(final TransactionKind kind) {
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

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(final TransactionStatus status) {
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

	public TransactionErrorCode getErrorCode() {
		return errorCode;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public String getAuthorizationExpiresAt() {
		return authorizationExpiresAt;
	}

	public void setAuthorizationExpiresAt(String authorizationExpiresAt) {
		this.authorizationExpiresAt = authorizationExpiresAt;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public ExtendedAuthorizationAttributes getExtendedAuthorizationAttributes() {
		return extendedAuthorizationAttributes;
	}

	public void setExtendedAuthorizationAttributes(ExtendedAuthorizationAttributes extendedAuthorizationAttributes) {
		this.extendedAuthorizationAttributes = extendedAuthorizationAttributes;
	}

	public String getLocationId() {
		return locationId;
	}

	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	public PaymentsRefundAttributes getPaymentsRefundAttributes() {
		return paymentsRefundAttributes;
	}

	public DateTime getProcessedAt() {
		return processedAt;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getUserId() {
		return userId;
	}

	public CurrencyExchangeAdjustment getCurrencyExchangeAdjustment() {
		return currencyExchangeAdjustment;
	}
}
