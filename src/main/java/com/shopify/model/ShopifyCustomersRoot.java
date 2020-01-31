package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyCustomersRoot {
    public List<ShopifyCustomer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<ShopifyCustomer> customers) {
        this.customers = customers;
    }

    private List<ShopifyCustomer> customers = new LinkedList<>();

}
