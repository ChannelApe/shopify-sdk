package com.shopify.model;

import java.util.Currency;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.joda.time.DateTime;

import com.shopify.model.adapters.CurrencyAdapter;
import com.shopify.model.adapters.DateTimeAdapter;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyRefund {

	private String id;
	@XmlElement(name = "order_id")
	private String orderId;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	private String note;
	@XmlElement(name = "user_id")
	private String userId;
	@XmlElement(name = "processed_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime processedAt;
	@XmlElement(name = "refund_line_items")
	private List<ShopifyRefundLineItem> refundLineItems;
	private ShopifyRefundShippingDetails shipping;
	private List<ShopifyTransaction> transactions;
	@XmlJavaTypeAdapter(CurrencyAdapter.class)
	private Currency currency;
	private List<ShopifyDuty> duties;
	@XmlElement(name = "order_adjustments")
	private List<OrderAdjustment> orderAdjustments;
	@XmlElement(name = "refund_duties")
	private List<RefundDuty> refundDuties;

	@XmlElement(name = "admin_graphql_api_id")
	private String adminGraphqlApiId;
	private Boolean restock;
	@XmlElement(name = "total_duties_set")
	private PriceSet totalDutiesSet;

}
