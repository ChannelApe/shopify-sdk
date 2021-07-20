package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyRefundLineItem {

	private String id;
	private long quantity;
	@XmlElement(name = "line_item_id")
	private String lineItemId;
	@XmlElement(name = "location_id")
	private String locationId;
	@XmlElement(name = "restock_type")
	private String restockType;
	private BigDecimal subtotal;
	@XmlElement(name = "total_tax")
	private BigDecimal totalTax;
	@XmlElement(name = "line_item")
	private ShopifyLineItem lineItem;
	@XmlElement(name = "sub_total_set")
	private PriceSet subTotalSet;
	@XmlElement(name = "total_tax_set")
	private PriceSet totalTaxSet;
}
