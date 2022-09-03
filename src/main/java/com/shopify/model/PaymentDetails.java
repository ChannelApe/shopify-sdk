package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PaymentDetails {

    @XmlElement(name = "credit_card_bin")
    private String creditCardBin;
    @XmlElement(name = "avs_result_code")
    private String avsResultCode;
    @XmlElement(name = "cvv_result_code")
    private String cvvResultCode;
    @XmlElement(name = "credit_card_number")
    private String creditCardNumber;
    @XmlElement(name = "credit_card_company")
    private String creditCardCompany;

    public String getCreditCardCompany() {
        return creditCardCompany;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getCvvResultCode() {
        return cvvResultCode;
    }

    public String getAvsResultCode() {
        return avsResultCode;
    }

    public String getCreditCardBin() {
        return creditCardBin;
    }
}
