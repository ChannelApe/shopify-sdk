package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyDuty {
    private String id;
    @XmlElement(name = "harmonized_system_code")
    private String harmonized_system_code;
    @XmlElement(name = "country_code_of_origin")
    private String country_code_of_origin;
    @XmlElement(name = "shop_money")
    private ShopMoney shopMoney;
    @XmlElement(name = "presentment_money")
    private PresentmentMoney presentmentMoney;
    @XmlElement(name = "tax_lines")
    private List<ShopifyTaxLine> taxLines;
    @XmlElement(name = "amount_set")
    private PriceSet amountSet;
}
