package com.shopify.model;

import com.shopify.mappers.TransactionStatusAdapter;
import com.shopify.model.adapters.CurrencyAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.Currency;

@XmlRootElement
public class CurrencyExchangeAdjustment {
    private String id;
    private BigDecimal adjustment;
    @XmlElement(name = "original_amount")
    private BigDecimal originalAmount;
    @XmlElement(name = "final_amount")
    private BigDecimal finalAmount;
    @XmlJavaTypeAdapter(CurrencyAdapter.class)
    private Currency currency;

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public BigDecimal getAdjustment() {
        return adjustment;
    }

    public String getId() {
        return id;
    }
}
