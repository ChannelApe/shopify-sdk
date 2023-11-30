package com.shopify.model;

import java.math.BigDecimal;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyAdjustment {

	private String id;
	@XmlElement(name = "order_id")
	private String orderId;
	@XmlElement(name = "refund_id")
	private String refundId;
	private BigDecimal amount;
	@XmlElement(name = "tax_amount")
	private BigDecimal taxAmount;
	private String kind;
	private String reason;

}
