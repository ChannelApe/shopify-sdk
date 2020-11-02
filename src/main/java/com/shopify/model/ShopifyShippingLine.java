package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

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
}
