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
public class DiscountAllocation {
    private BigDecimal amount;
    @XmlElement(name = "discount_application_index")
    private int discountApplicationIndex;
    @XmlElement(name = "amount_set")
    private PriceSet amountSet;
}
