package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyCustomerObjectUpdateRequestRoot {
    private ShopifyCustomer customer;

    public ShopifyCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(ShopifyCustomer customer) {
        this.customer = customer;
    }
}
