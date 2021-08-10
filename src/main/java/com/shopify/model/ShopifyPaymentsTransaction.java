package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyPaymentsTransaction {

    String id;
    String type;
    Boolean test;
    @XmlElement(name = "payout_id")
    String payoutId;
    @XmlElement(name = "payout_status")
    String payoutStatus;
    String currency;
    String amount;
    String fee;
    String net;
    @XmlElement(name = "source_id")
    String sourceId;
    @XmlElement(name = "source_type")
    String sourceType;
    @XmlElement(name = "source_order_transaction_id")
    String sourceOrderTransactionId;
    @XmlElement(name = "source_order_id")
    String sourceOrderId;
    @XmlElement(name = "processed_at")
    String processedAt;

}
