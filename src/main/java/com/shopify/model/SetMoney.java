package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SetMoney {
    @XmlElement(name = "shop_money")
    private Money shopMoney;
    @XmlElement(name = "presentment_money")
    private Money presentmentMoney;
}
