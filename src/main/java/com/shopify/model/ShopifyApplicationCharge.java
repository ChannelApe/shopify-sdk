package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyApplicationCharge {

    @XmlElement(name = "confirmation_url")
    String confirmationUrl;
    @XmlElement(name = "created_at")
    String createdAt;
    String id;
    String name;
    BigDecimal price;
    @XmlElement(name = "return_url")
    String returnUrl;
    String status;
    @XmlElement(name = "updated_at")
    String updatedAt;
    Boolean test;
    @XmlElement(name = "charge_type")
    String chargeType;
    @XmlElement(name = "decorated_return_url")
    String decoratedReturnUrl;

}
