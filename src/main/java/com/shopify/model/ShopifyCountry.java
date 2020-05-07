package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyCountry {
    private String code;
    private String id;
    private String name;
    private List<ShopifyProvince> provinces;
    private BigDecimal tax;

    public String getCode() {
        return code;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<ShopifyProvince> getProvinces() {
        return provinces;
    }
    public BigDecimal getTax() {
        return tax;
    }
    public void setCode(final String code) {
        this.code = code;
    }
    public void setId(final String id) {
        this.id = id;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public void setProvinces(final List<ShopifyProvince> provinces) {
        this.provinces = provinces;
    }
    public void setTax(final BigDecimal tax) {
        this.tax = tax;
    }
}
