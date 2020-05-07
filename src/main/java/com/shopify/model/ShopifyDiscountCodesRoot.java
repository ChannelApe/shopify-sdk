package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyDiscountCodesRoot {
    @XmlElement(name = "discount_codes")
    public List<ShopifyDiscountCode> getDiscountCodes() {
        return discountCodes;
    }

    public void setDiscountCodes(List<ShopifyDiscountCode> discountCodes) {
        this.discountCodes = discountCodes;
    }

    private List<ShopifyDiscountCode> discountCodes = new LinkedList<>();

}
