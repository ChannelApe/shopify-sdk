package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderAdjustment {
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
    @XmlElement(name = "amount_set")
    private PriceSet amountSet;
    @XmlElement(name = "tax_amount_set")
    private PriceSet taxAmountSet;
}
