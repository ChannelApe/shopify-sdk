package com.shopify.model.price;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PriceSet {

    @XmlElement(name = "shop_money")
    private Money shopMoney;
    @XmlElement(name = "presentment_money")
    private Money presentmentMoney;

    public Money getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(final Money shopMoney) {
        this.shopMoney = shopMoney;
    }

    public Money getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(final Money presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }
}
