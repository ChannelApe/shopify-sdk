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

@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level= AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyPriceBasedShippingRates {

    String id;
    String name;
    String price;
    @XmlElement(name = "shipping_zone_id")
    String shippingZoneId;
    @XmlElement(name = "min_order_subtotal")
    String minOrderSubtotal;
    @XmlElement(name = "max_order_subtotal")
    String maxOrderSubtotal;

}
