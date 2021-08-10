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
public class ShopifyPaymentsDispute {

    String id;
    @XmlElement(name = "order_id")
    String orderId;
    String type;
    String currency;
    String amount;
    String reason;
    String status;
    @XmlElement(name = "network_reason_code")
    long network_reason_code;
    @XmlElement(name = "evidence_due_by")
    String evidenceDueBy;
    @XmlElement(name = "evidence_sent_on")
    String evidenceSentOn;
    @XmlElement(name = "finalized_on")
    String finalizedOn;
    @XmlElement(name = "initiated_at")
    String initiatedAt;

}
