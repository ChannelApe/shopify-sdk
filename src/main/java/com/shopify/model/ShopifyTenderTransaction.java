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
import java.util.Currency;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyTenderTransaction {
    private String id;
    @XmlElement(name = "order_id")
    private String orderId;
    private BigDecimal amount;
    private Currency currency;
    @XmlElement(name = "user_id")
    private String userId;
    private boolean test;
    @XmlElement(name = "remote_reference")
    private String remoteReference;
    @XmlElement(name = "payment_details")
    private PaymentDetails paymentDetails;
    @XmlElement(name = "payment_method")
    private String paymentMethod;
    @XmlElement(name = "processed_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime processedAt;
}
