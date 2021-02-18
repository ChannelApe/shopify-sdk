package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.DateTimeAdapter;
import lombok.Data;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyFulfillmentEvent {
    private String address1;
    private String city;
    private String country;
    @XmlElement(name = "created_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime createdAt;
    @XmlElement(name = "estimated_delivery_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime estimatedDeliveryAt;
    @XmlElement(name = "fulfillment_id")
    private String fulfillmentId;
    @XmlElement(name = "happened_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime happenedAt;
    private String id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String message;
    @XmlElement(name = "order_id")
    private String orderId;
    private String province;
    @XmlElement(name = "shop_id")
    private String shopId;
    private String status;
    @XmlElement(name = "updated_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime updatedAt;
    private String zip;
}
