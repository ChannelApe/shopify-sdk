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
}
