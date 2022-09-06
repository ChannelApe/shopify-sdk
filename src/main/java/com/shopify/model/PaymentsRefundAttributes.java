package com.shopify.model;

import com.shopify.mappers.TransactionStatusAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentsRefundAttributes {

    @XmlJavaTypeAdapter(TransactionStatusAdapter.class)
    private TransactionStatus status;

    @XmlElement(name = "acquirer_reference_number")
    private String acquirerReferenceNumber;

    public TransactionStatus getStatus() {
        return status;
    }

    public String getAcquirerReferenceNumber() {
        return acquirerReferenceNumber;
    }
}
