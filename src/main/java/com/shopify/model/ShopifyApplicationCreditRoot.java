package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyApplicationCreditRoot {

    @XmlElement(name = "application_credits")
    private List<ShopifyApplicationCredit> applicationCredits = new LinkedList<>();

    public List<ShopifyApplicationCredit> getApplicationCredits() {
        return applicationCredits;
    }

    public void setApplicationCredits(List<ShopifyApplicationCredit> applicationCredits) {
        this.applicationCredits = applicationCredits;
    }
}
