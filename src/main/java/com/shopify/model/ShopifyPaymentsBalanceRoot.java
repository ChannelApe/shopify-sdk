package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyPaymentsBalanceRoot {

    private List<ShopifyPaymentsBalance> balance = new LinkedList<>();

    public List<ShopifyPaymentsBalance> getBalance() {
        return balance;
    }

    public void setBalance(List<ShopifyPaymentsBalance> balance) {
        this.balance = balance;
    }
}
