package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.DateTimeAdapter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    DateTime evidenceDueBy;
    @XmlElement(name = "evidence_sent_on")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    DateTime evidenceSentOn;
    @XmlElement(name = "finalized_on")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    DateTime finalizedOn;
    @XmlElement(name = "initiated_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    DateTime initiatedAt;

}
