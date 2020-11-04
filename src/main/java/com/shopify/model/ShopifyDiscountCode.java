package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.DateTimeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyDiscountCode {
    private String code;
    private String id;
    @XmlElement(name = "price_rule_id")
    private String priceRuleId;
    @XmlElement(name = "usage_count")
    private int usageCount;
    @XmlElement(name = "created_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime createdAt;
    @XmlElement(name = "updated_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime updatedAt;

    public DateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(final DateTime createdAt) {
        this.createdAt = createdAt;
    }
    public DateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(final DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getPriceRuleId() {
        return priceRuleId;
    }
    public void setPriceRuleId(String priceRuleId) {
        this.priceRuleId = priceRuleId;
    }
    public int getUsageCount() {
        return usageCount;
    }
    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }
}
