package com.shopify.model.discount;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DiscountApplication {

    private String code;
    @XmlElement(name = "target_type")
    private String targetType;
    private String type;
    private BigDecimal value;
    @XmlElement(name = "value_type")
    private String valueType;
    @XmlElement(name = "allocation_method")
    private String allocationMethod;
    @XmlElement(name = "target_selection")
    private String targetSelection;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(final String targetType) {
        this.targetType = targetType;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(final BigDecimal value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(final String valueType) {
        this.valueType = valueType;
    }

    public String getAllocationMethod() {
        return allocationMethod;
    }

    public void setAllocationMethod(final String allocationMethod) {
        this.allocationMethod = allocationMethod;
    }

    public String getTargetSelection() {
        return targetSelection;
    }

    public void setTargetSelection(final String targetSelection) {
        this.targetSelection = targetSelection;
    }
}
