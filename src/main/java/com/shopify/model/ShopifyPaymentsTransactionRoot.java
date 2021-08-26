package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyPaymentsTransactionRoot {

    private List<ShopifyPaymentsTransaction> transactions = new LinkedList<>();

    public List<ShopifyPaymentsTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<ShopifyPaymentsTransaction> transactions) {
        this.transactions = transactions;
    }
}
