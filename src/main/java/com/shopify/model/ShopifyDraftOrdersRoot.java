package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyDraftOrdersRoot {
    @XmlElement(name = "draft_orders")
    public List<ShopifyDraftOrder> getDraftOrders() {
        return draftOrders;
    }

    public void setDraftOrders(List<ShopifyDraftOrder> draftOrders) {
        this.draftOrders = draftOrders;
    }

    private List<ShopifyDraftOrder> draftOrders = new LinkedList<>();

}
