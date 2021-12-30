package com.shopify.model.discount;

import com.shopify.model.adapters.DateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DiscountCode {

    private String code;
    private String id;
    @XmlElement(name = "created_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private String createdAt;
    @XmlElement(name = "updated_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private String updatedAt;
    @XmlElement(name = "price_rule_id")
    private Long priceRuleId;
    @XmlElement(name = "usage_count")
    private Long usageCount;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getPriceRuleId() {
        return priceRuleId;
    }

    public void setPriceRuleId(final Long priceRuleId) {
        this.priceRuleId = priceRuleId;
    }

    public Long getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(final Long usageCount) {
        this.usageCount = usageCount;
    }
}
