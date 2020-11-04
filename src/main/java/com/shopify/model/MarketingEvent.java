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
public class MarketingEvent {
    @XmlElement(name = "utm_campaign")
    private String utmCampaign;
    @XmlElement(name = "utm_source")
    private String utmSource;
    @XmlElement(name = "utm_medium")
    private String utmMedium;
}
