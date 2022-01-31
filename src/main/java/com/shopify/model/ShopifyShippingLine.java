package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyShippingLine {

	private String id;
	private String title;
	private BigDecimal price;
	@XmlElement(name = "price_set")
	private PriceSet priceSet;
	@XmlElement(name = "discounted_price")
	private BigDecimal discountedPrice;
	@XmlElement(name = "discounted_price_set")
	private PriceSet discountedPriceSet;

	private String code;
	private String source;
	@XmlElement(name = "carrier_identifier")
	private String carrierIdentifier;
	@XmlElement(name = "requested_fulfillment_service_id")
	private String requestedFulfillmentServiceId;
	@XmlElement(name = "tax_lines")
	private List<ShopifyTaxLine> taxLines;

	private String phone;
	@XmlElement(name = "original_shop_price")
	private BigDecimal originalShopPrice;
	@XmlElement(name = "original_shop_markup")
	private BigDecimal originalShopMarkup;
	@XmlElement(name = "presentment_title")
	private String presentmentTitle;
	@XmlElement(name = "validation_context")
	private String validationContext;
	@XmlElement(name = "custom_tax_lines")
	private String customTaxLines;
	private BigDecimal markup;
	@XmlElement(name = "delivery_category")
	private String deliveryCategory;
	@XmlElement(name = "carrier_service_id")
	private String carrierServiceId;
	@XmlElement(name = "api_client_id")
	private String apiClientId;
	@XmlElement(name = "applied_discounts")
	private List<Map<String, Object>> appliedDiscounts;
	@XmlElement(name = "delivery_option_group")
	private Map<String, Object> deliveryOptionGroup;
	@XmlElement(name = "delivery_expectation_range")
	private List<Integer> deliveryExpectationRange;
	@XmlElement(name = "delivery_expectation_type")
	private String deliveryExpectationType;
	@XmlElement(name = "discount_allocations")
	private List<DiscountAllocation> discountAllocations;


}
