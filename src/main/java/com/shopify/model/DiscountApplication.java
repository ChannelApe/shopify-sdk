package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountApplication {
    private String type;
    private String title;
    private String description;
    private BigDecimal value;
    @XmlElement(name = "value_type")
    private String valueType;
    @XmlElement(name = "allocation_method")
    private String allocationMethod;
    @XmlElement(name = "target_selection")
    private String targetSelection;
    @XmlElement(name = "target_type")
    private String targetType;
}
