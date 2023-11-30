package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class ShopifyCustomersRoot {
    private List<ShopifyCustomer> customers = new LinkedList<>();

}
