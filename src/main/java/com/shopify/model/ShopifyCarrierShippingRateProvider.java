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
import java.util.Map;

@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level= AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyCarrierShippingRateProvider {

    String id;
    @XmlElement(name = "carrier_service_id")
    String carrierServiceId;
    @XmlElement(name = "flat_modifier")
    String flatModifier;
    @XmlElement(name = "percent_modifier")
    String percentModifier;
    @XmlElement(name = "shipping_zone_id")
    String shippingZoneId;
    @XmlElement(name = "service_filter")
    Map<String, Object> serviceFilter;

}
