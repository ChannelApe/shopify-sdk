package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyCountry {
    private String code;
    private String id;
    private String name;
    private List<ShopifyProvince> provinces;
    private BigDecimal tax;

    @XmlElement(name = "shipping_zone_id")
    private String shippingZoneId;
    @XmlElement(name = "tax_name")
    private String taxName;

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

    public String getShippingZoneId() {
        return shippingZoneId;
    }

    public void setShippingZoneId(String shippingZoneId) {
        this.shippingZoneId = shippingZoneId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }
}
