package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifySummary {

    @XmlElement(name = "adjustments_fee_amount")
    String adjustmentsFeeAmount;
    @XmlElement(name = "adjustments_gross_amount")
    String adjustmentsGrossAmount;
    @XmlElement(name = "charges_fee_amount")
    String chargesFeeAmount;
    @XmlElement(name = "charges_gross_amount")
    String chargesGrossAmount;
    @XmlElement(name = "refunds_fee_amount")
    String refundsFeeAmount;
    @XmlElement(name = "refunds_gross_amount")
    String refundsGrossAmount;
    @XmlElement(name = "reserved_funds_fee_amount")
    String reservedFundsFeeAmount;
    @XmlElement(name = "reserved_funds_gross_amount")
    String reservedFundsGrossAmount;
    @XmlElement(name = "retried_payouts_fee_amount")
    String retriedPayoutsFeeAmount;
    @XmlElement(name = "retried_payouts_gross_amount")
    String retriedPayoutsGrossAmount;

}
