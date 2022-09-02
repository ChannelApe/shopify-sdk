package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyTransactionRoot {

    @XmlElement(name = "transaction")
    private ShopifyTransaction shopifyTransaction;

    public ShopifyTransaction getTransaction() {
        return shopifyTransaction;
    }

    public void setTransaction(ShopifyTransaction shopifyTransaction) {
        this.shopifyTransaction = shopifyTransaction;
    }
}