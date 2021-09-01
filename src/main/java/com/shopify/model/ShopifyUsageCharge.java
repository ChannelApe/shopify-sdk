package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.DateTimeAdapter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;

@Getter
@Setter
@XmlRootElement
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyUsageCharge {

    String id;
    String description;
    BigDecimal price;
    @XmlElement(name = "created_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    DateTime createdAt;
    @XmlElement(name = "billing_on")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    DateTime billingOn;
    @XmlElement(name = "balance_used")
    BigDecimal balanceUsed;
    @XmlElement(name = "balance_remaining")
    BigDecimal balanceRemaining;
    @XmlElement(name = "risk_level")
    long riskLevel;
    @XmlElement(name = "updated_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    DateTime updatedAt;

}
