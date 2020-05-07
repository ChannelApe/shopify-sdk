package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.Currency;

@XmlRootElement
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
    private Object paymentDetails;
    @XmlElement(name = "payment_method")
    private String paymentMethod;
    @XmlElement(name = "processed_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime processedAt;

    public String getId() {
        return id;
    }
    public String getOrderId() {
        return orderId;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public Currency getCurrency() {
        return currency;
    }
    public String getUserId() {
        return userId;
    }
    public boolean getTest() {
        return test;
    }
    public String getRemoteReference() {
        return remoteReference;
    }
    public Object getPaymentDetails() {
        return paymentDetails;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public DateTime getProcessedAt() {
        return processedAt;
    }
    public void setId(final String id) {
        this.id = id;
    }
    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }
    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }
    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }
    public void setUserId(final String userId) {
        this.userId = userId;
    }
    public void setTest(final boolean test) {
        this.test = test;
    }
    public void setRemoteReference(final String remoteReference) {
        this.remoteReference = remoteReference;
    }
    public void setPaymentDetails(final Object paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
    public void setPaymentMethod(final String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public void setProcessedAt(final DateTime processedAt) {
        this.processedAt = processedAt;
    }
}
