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
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyCarrierService {

    String id;
    String name;
    Boolean active;
    @XmlElement(name = "callback_url")
    String callbackUrl;
    @XmlElement(name = "carrier_service_type")
    String carrierServiceType;
    String format;
    @XmlElement(name = "service_discovery")
    Boolean serviceDiscovery;
    @XmlElement(name = "admin_graphql_api_id")
    String adminGraphqlApiId;

}
