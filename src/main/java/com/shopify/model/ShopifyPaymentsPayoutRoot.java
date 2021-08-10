package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyPaymentsPayoutRoot {

    private List<ShopifyPaymentsPayout> payouts = new LinkedList<>();

    public List<ShopifyPaymentsPayout> getPayouts() {
        return payouts;
    }

    public void setPayouts(List<ShopifyPaymentsPayout> payouts) {
        this.payouts = payouts;
    }
}
