package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SMSMarketingConsent {

    @XmlElement(name = "marketing_state")
    private String marketingState;
    @XmlElement(name = "marketing_opt_in_level")
    private String marketingOptInLevel;

    public String getMarketingState() {
        return marketingState;
    }

    public void setMarketingState(final String marketingState) {
        this.marketingState = marketingState;
    }

    public String getMarketingOptInLevel() {
        return marketingOptInLevel;
    }

    public void setMarketingOptInLevel(final String marketingOptInLevel) {
        this.marketingOptInLevel = marketingOptInLevel;
    }
}
