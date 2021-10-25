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
import java.util.List;

@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@FieldDefaults(level= AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyShippingZone {

    String id;
    String name;
    @XmlElement(name = "profile_id")
    String profileId;
    @XmlElement(name = "location_group_id")
    String locationGroupId;
    List<ShopifyCountry> countries;
    @XmlElement(name = "carrier_shipping_rate_providers")
    List<ShopifyCarrierShippingRateProvider> carrierShippingRateProviders;
    @XmlElement(name = "weight_based_shipping_rates")
    List<ShopifyWeightBasedShippingRates> weightBasedShippingRates;
    @XmlElement(name = "price_based_shipping_rates")
    List<ShopifyPriceBasedShippingRates> priceBasedShippingRates;
    @XmlElement(name = "admin_graphql_api_id")
    String adminGraphqlApiId;

}
