package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@XmlRootElement
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyUsageCharge {

    String id;
    String description;
    String price;
    @XmlElement(name = "created_at")
    String createdAt;
    @XmlElement(name = "billing_on")
    String billingOn;
    @XmlElement(name = "balance_used")
    BigDecimal balanceUsed;
    @XmlElement(name = "balance_remaining")
    BigDecimal balanceRemaining;
    @XmlElement(name = "risk_level")
    int riskLevel;
    @XmlElement(name = "updated_at")
    String updatedAt;

}
