package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDetails {
    @XmlElement(name = "avs_result_code")
    private String avsResultCode;
    @XmlElement(name = "credit_card_bin")
    private String creditCardBin;
    @XmlElement(name = "cvv_result_code")
    private String cvvResultCode;
    @XmlElement(name = "credit_card_number")
    private String creditCardNumber;
    @XmlElement(name = "credit_card_company")
    private String creditCardCompany;
}
