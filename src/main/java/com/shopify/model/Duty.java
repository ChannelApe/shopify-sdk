package com.shopify.model;

import com.shopify.model.price.Money;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Duty extends AbstractModel {

    private String id;
    @XmlElement(name = "tax_lines")
    private List<ShopifyTaxLine> taxLines;
    @XmlElement(name = "shop_money")
    private Money shopMoney;
    @XmlElement(name = "presentment_money")
    private Money presentmentMoney;
    @XmlElement(name = "country_code_of_origin")
    private String countryCodeOfOrigin;
    @XmlElement(name = "harmonized_system_code")
    private String harmonizedSystemCode;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public List<ShopifyTaxLine> getTaxLines() {
        return taxLines;
    }

    public void setTaxLines(final List<ShopifyTaxLine> taxLines) {
        this.taxLines = taxLines;
    }

    public Money getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(final Money shopMoney) {
        this.shopMoney = shopMoney;
    }

    public Money getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(final Money presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

    public String getCountryCodeOfOrigin() {
        return countryCodeOfOrigin;
    }

    public void setCountryCodeOfOrigin(final String countryCodeOfOrigin) {
        this.countryCodeOfOrigin = countryCodeOfOrigin;
    }

    public String getHarmonizedSystemCode() {
        return harmonizedSystemCode;
    }

    public void setHarmonizedSystemCode(final String harmonizedSystemCode) {
        this.harmonizedSystemCode = harmonizedSystemCode;
    }
}

