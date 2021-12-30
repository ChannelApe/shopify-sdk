package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
    @XmlElement(name = "credit_card_name")
    private String creditCardName;
    @XmlElement(name = "credit_card_wallet")
    private String creditCardWallet;
    @XmlElement(name = "credit_card_expiration_month")
    private String creditCardExpirationMonth;
    @XmlElement(name = "credit_card_expiration_year")
    private String creditCardExpirationYear;

    public String getCreditCardBin() {
        return creditCardBin;
    }

    public void setCreditCardBin(final String creditCardBin) {
        this.creditCardBin = creditCardBin;
    }

    public String getAvsResultCode() {
        return avsResultCode;
    }

    public void setAvsResultCode(final String avsResultCode) {
        this.avsResultCode = avsResultCode;
    }

    public String getCvvResultCode() {
        return cvvResultCode;
    }

    public void setCvvResultCode(final String cvvResultCode) {
        this.cvvResultCode = cvvResultCode;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(final String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardCompany() {
        return creditCardCompany;
    }

    public void setCreditCardCompany(final String creditCardCompany) {
        this.creditCardCompany = creditCardCompany;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setCreditCardName(final String creditCardName) {
        this.creditCardName = creditCardName;
    }

    public String getCreditCardWallet() {
        return creditCardWallet;
    }

    public void setCreditCardWallet(final String creditCardWallet) {
        this.creditCardWallet = creditCardWallet;
    }

    public String getCreditCardExpirationMonth() {
        return creditCardExpirationMonth;
    }

    public void setCreditCardExpirationMonth(final String creditCardExpirationMonth) {
        this.creditCardExpirationMonth = creditCardExpirationMonth;
    }

    public String getCreditCardExpirationYear() {
        return creditCardExpirationYear;
    }

    public void setCreditCardExpirationYear(final String creditCardExpirationYear) {
        this.creditCardExpirationYear = creditCardExpirationYear;
    }
}
