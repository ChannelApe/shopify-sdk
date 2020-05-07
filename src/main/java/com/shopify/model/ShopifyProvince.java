package com.shopify.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyProvince {
    private String code;
    private String countryId;
    private String id;
    private String name;
    @XmlElement(name = "shipping_zone_id")
    private String shippingZoneId;
    private BigDecimal tax;
    @XmlElement(name = "tax_name")
    private String taxName;
    @XmlElement(name = "tax_type")
    private String taxType;
    @XmlElement(name = "tax_percentage")
    private BigDecimal taxPercentage;

    public String getCode() {
        return code;
    }
    public String getCountryId() {
        return countryId;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getShippingZoneId() {
        return shippingZoneId;
    }
    public BigDecimal getTax() {
        return tax;
    }
    public String getTaxName() {
        return taxName;
    }
    public String getTaxType() {
        return taxType;
    }
    public BigDecimal getTaxPercentage() {
        return taxPercentage;
    }
    public void setCode(final String code) {
        this.code = code;
    }
    public void setCountryId(final String countryId) {
        this.countryId = countryId;
    }
    public void setId(final String id) {
        this.id = id;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public void setShippingZoneId(final String shippingZoneId) {
        this.shippingZoneId = shippingZoneId;
    }
    public void setTax(final BigDecimal tax) {
        this.tax = tax;
    }
    public void setTaxName(final String taxName) {
        this.taxName = taxName;
    }
    public void setTaxType(final String taxType) {
        this.taxType = taxType;
    }
    public void setTaxPercentage(final BigDecimal taxPercentage) {
        this.taxPercentage = taxPercentage;
    }
}
