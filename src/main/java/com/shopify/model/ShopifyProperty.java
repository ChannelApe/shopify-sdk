package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyProperty {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
