package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyFulfillmentOrder {
    @XmlElement(name = "assigned_location_id")
    private String assignedLocationId;
    private ShopifyAddress destination;
    private String id;
    @XmlElement(name = "line_items")
    private List<ShopifyFulfillmentLineItem> lineItems;
    @XmlElement(name = "order_id")
    private String orderId;
    @XmlElement(name = "request_status")
    private String requestStatus;
    @XmlElement(name = "shop_id")
    private String shopId;
    private String status;
    @XmlElement(name = "supported_actions")
    private List<String> supportedActions;
    @XmlElement(name = "assigned_location")
    private ShopifyAddress assignedLocation;
    @XmlElement(name = "merchant_requests")
    private List<MerchantRequest> merchantRequests;
}
