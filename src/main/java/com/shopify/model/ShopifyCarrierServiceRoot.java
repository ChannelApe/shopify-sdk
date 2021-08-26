package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyCarrierServiceRoot {

    @XmlElement(name = "carrier_services")
    private List<ShopifyCarrierService> carrierServices = new LinkedList<>();

    public List<ShopifyCarrierService> getCarrierServices() {
        return carrierServices;
    }

    public void setCarrierServices(List<ShopifyCarrierService> carrierServices) {
        this.carrierServices = carrierServices;
    }
}
