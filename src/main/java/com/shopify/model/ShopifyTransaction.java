package com.shopify.model;

import java.math.BigDecimal;
import java.util.Currency;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.CurrencyAdapter;
import com.shopify.model.adapters.DateTimeAdapter;
import lombok.Data;
import org.joda.time.DateTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyTransaction {
	@XmlElement(name = "order_id")
	private String orderId;
	private String kind;
	private String gateway;
	@XmlElement(name = "parent_id")
	private String parentId;
	private String status;
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
	private PaymentDetails paymentDetails;
	@XmlElement(name = "source_name")
	private String sourceName;
	@XmlElement(name = "processed_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime processedAt;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	private String id;
	@XmlElement(name = "admin_graphql_api_id")
	private String adminGraphqlApiId;
	private String authorization;
	@XmlElement(name = "currency_exchange_adjustment")
	private String currencyExchangeAdjustment;
	@XmlElement(name = "error_code")
	private String errorCode;
	private Boolean test;
	@XmlElement(name = "user_id")
	private String userId;

}
