package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyPaymentsDisputeRoot {

    private List<ShopifyPaymentsDispute> disputes = new LinkedList<>();

    public List<ShopifyPaymentsDispute> getDisputes() {
        return disputes;
    }

    public void setDisputes(List<ShopifyPaymentsDispute> disputes) {
        this.disputes = disputes;
    }
}
