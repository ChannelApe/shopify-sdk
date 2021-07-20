package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyCheckoutsRoot {
    public List<ShopifyCheckout> getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(List<ShopifyCheckout> checkouts) {
        this.checkouts = checkouts;
    }

    private List<ShopifyCheckout> checkouts = new LinkedList<>();

}
