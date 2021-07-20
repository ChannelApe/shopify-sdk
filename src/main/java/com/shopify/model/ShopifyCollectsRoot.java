package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyCollectsRoot {
    public List<ShopifyCollect> getCollects() {
        return collects;
    }

    public void setCollects(List<ShopifyCollect> collects) {
        this.collects = collects;
    }

    private List<ShopifyCollect> collects = new LinkedList<>();

}
