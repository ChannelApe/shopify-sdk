package com.shopify.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;
import org.joda.time.DateTime;

import com.shopify.model.adapters.CurrencyAdapter;
import com.shopify.model.adapters.DateTimeAdapter;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyOrder {

	private String id;
	private String email;
	@XmlElement(name = "closed_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime closedAt;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	private int number;
	private String note;
	private String token;
	@XmlElement(name = "total_price")
	private BigDecimal totalPrice;
	@XmlElement(name = "subtotal_price")
	private BigDecimal subtotalPrice;
	@XmlElement(name = "total_weight")
	private long totalWeight;
	@XmlElement(name = "total_tax")
	private BigDecimal totalTax;
	@XmlElement(name = "taxes_included")
	private boolean taxesIncluded;
	@XmlJavaTypeAdapter(CurrencyAdapter.class)
	private Currency currency;
	@XmlElement(name = "financial_status")
	private String financialStatus;
	@XmlElement(name = "total_discounts")
	private BigDecimal totalDiscounts;
	@XmlElement(name = "total_line_items_price")
	private BigDecimal totaLineItemsPrice;
	@XmlElement(name = "cart_token")
	private String cartToken;
	@XmlElement(name = "buyer_accepts_marketing")
	private boolean buyerAcceptsMarketing;
	private String name;
	@XmlElement(name = "referring_site")
	private String referringSite;
	@XmlElement(name = "landing_site")
	private String landingSite;
	@XmlElement(name = "cancelled_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime cancelledAt;
	@XmlElement(name = "cancel_reason")
	private String cancelReason;
	@XmlElement(name = "user_id")
	private String userId;
	@XmlElement(name = "location_id")
	private String locationId;
	@XmlElement(name = "processed_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime processedAt;
	@XmlElement(name = "browser_ip")
	private String browserIp;
	@XmlElement(name = "order_number")
	private String orderNumber;
	@XmlElement(name = "processing_method")
	private String processingMethod;
	@XmlElement(name = "source_name")
	private String sourceName;
	@XmlElement(name = "fulfillment_status")
	private String fulfillmentStatus;
	@XmlElement(name = "tags")
	private String tags;
	@XmlElement(name = "order_status_url")
	private String orderStatusUrl;
	@XmlElement(name = "line_items")
	private List<ShopifyLineItem> lineItems = new LinkedList<>();
	private List<ShopifyFulfillment> fulfillments = new LinkedList<>();
	@XmlElement(name = "billing_address")
	private ShopifyAddress billingAddress = new ShopifyAddress();
	@XmlElement(name = "shipping_address")
	private ShopifyAddress shippingAddress = new ShopifyAddress();
	private ShopifyCustomer customer = new ShopifyCustomer();
	@XmlElement(name = "shipping_lines")
	private List<ShopifyShippingLine> shippingLines = new LinkedList<>();
	@XmlElement(name = "tax_lines")
	private List<ShopifyTaxLine> taxLines = new LinkedList<>();

	@XmlElement(name = "note_attributes")
	private List<ShopifyAttribute> noteAttributes = new LinkedList<>();
	private List<ShopifyRefund> refunds = new LinkedList<>();
	private List<Metafield> metafields = new LinkedList<>();

}
