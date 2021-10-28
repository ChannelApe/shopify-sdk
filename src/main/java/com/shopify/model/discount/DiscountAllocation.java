package com.shopify.model.discount;

import com.shopify.model.price.PriceSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DiscountAllocation {

    private BigDecimal amount;
    @XmlElement(name = "amount_set")
    private PriceSet amountSet;
    @XmlElement(name = "discount_application_index")
    private Long discountApplicationIndex;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public PriceSet getAmountSet() {
        return amountSet;
    }

    public void setAmountSet(final PriceSet amountSet) {
        this.amountSet = amountSet;
    }

    public Long getDiscountApplicationIndex() {
        return discountApplicationIndex;
    }

    public void setDiscountApplicationIndex(final Long discountApplicationIndex) {
        this.discountApplicationIndex = discountApplicationIndex;
    }
}
