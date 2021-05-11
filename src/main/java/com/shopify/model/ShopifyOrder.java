package com.shopify.model;

import java.math.BigDecimal;
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

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyOrder {
	@XmlElement(name = "app_id")
	private String appId;
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
	private BigDecimal totalLineItemsPrice;
	@XmlElement(name = "cart_token")
	private String cartToken;
	@XmlElement(name = "checkout_token")
	private String checkoutToken;
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
	private List<ShopifyLineItem> lineItems;
	private List<ShopifyFulfillment> fulfillments;
	@XmlElement(name = "billing_address")
	private ShopifyAddress billingAddress;
	@XmlElement(name = "shipping_address")
	private ShopifyAddress shippingAddress;
	private ShopifyCustomer customer;
	@XmlElement(name = "shipping_lines")
	private List<ShopifyShippingLine> shippingLines;
	@XmlElement(name = "tax_lines")
	private List<ShopifyTaxLine> taxLines;
	@XmlElement(name = "note_attributes")
	private List<ShopifyAttribute> noteAttributes;
	private List<ShopifyRefund> refunds;
	private List<Metafield> metafields;
	@XmlElement(name = "client_details")
	private ClientDetails clientDetails;
	@XmlElement(name = "current_total_duties_set")
	private PriceSet currentTotalDutiesSet;
	@XmlElement(name = "discount_applications")
	private List<DiscountApplication> discountApplications;
	@XmlElement(name = "discount_codes")
	private List<DiscountCode> discountCodes;
	@XmlElement(name = "original_total_duties_set")
	private PriceSet originalTotalDutiesSet;
	@XmlElement(name = "payment_gateway_names")
	private List<String> paymentGatewayNames;
	private String phone;
	@XmlElement(name = "presentment_currency")
	private String presentmentCurrency;
	@XmlElement(name = "subtotal_price_set")
	private PriceSet subtotalPriceSet;
	private boolean test;
	@XmlElement(name = "total_discounts_set")
	private PriceSet total_discounts_set;
	@XmlElement(name = "total_line_items_price_set")
	private PriceSet totalLineItemsPriceSet;
	@XmlElement(name = "total_price_set")
	private PriceSet totalPriceSet;
	@XmlElement(name = "total_tax_set")
	private PriceSet totalTaxSet;
	@XmlElement(name = "total_tip_received")
	private BigDecimal totalTipReceived;
	@XmlElement(name = "current_total_discounts")
	private BigDecimal currentTotalDiscounts;
	@XmlElement(name = "current_total_discounts_set")
	private PriceSet currentTotalDiscountsSet;
	@XmlElement(name = "current_total_price")
	private BigDecimal currentTotalPrice;
	@XmlElement(name = "current_total_price_set")
	private PriceSet currentTotalPriceSet;
	@XmlElement(name = "current_subtotal_price")
	private BigDecimal currentSubtotalPrice;
	@XmlElement(name = "current_subtotal_price_set")
	private PriceSet currentSubtotalPriceSet;
	@XmlElement(name = "current_total_tax")
	private BigDecimal currentTotalTax;
	@XmlElement(name = "current_total_tax_set")
	private PriceSet currentTotalTaxSet;
	@XmlElement(name = "customer_locale")
	private String customerLocale;
	@XmlElement(name = "total_outstanding")
	private BigDecimal totalOutstanding;
	@XmlElement(name = "total_shipping_price_set")
	private PriceSet totalShippingPriceSet;




}
