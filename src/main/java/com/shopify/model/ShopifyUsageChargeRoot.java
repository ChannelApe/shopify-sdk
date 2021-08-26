package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyUsageChargeRoot {

    @XmlElement(name = "usage_charges")
    private List<ShopifyUsageCharge> usageCharges = new LinkedList<>();

    public List<ShopifyUsageCharge> getUsageCharges() {
        return usageCharges;
    }

    public void setUsageCharges(List<ShopifyUsageCharge> usageCharges) {
        this.usageCharges = usageCharges;
    }
}
