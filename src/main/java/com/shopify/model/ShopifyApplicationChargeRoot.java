package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyApplicationChargeRoot {

    @XmlElement(name = "application_charges")
    private List<ShopifyApplicationCharge> applicationCharges = new LinkedList<>();

    public List<ShopifyApplicationCharge> getApplicationCharges() {
        return applicationCharges;
    }

    public void setApplicationCharges(List<ShopifyApplicationCharge> applicationCharges) {
        this.applicationCharges = applicationCharges;
    }
}
