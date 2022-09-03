package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ExtendedAuthorizationAttributes {

    @XmlElement(name = "standard_authorization_expires_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime standardAuthorizationExpiresAt;

    @XmlElement(name = "extended_authorization_expires_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime extendedAuthorizationExpiresAt;

    public DateTime getExtendedAuthorizationExpiresAt() {
        return extendedAuthorizationExpiresAt;
    }

    public DateTime getStandardAuthorizationExpiresAt() {
        return standardAuthorizationExpiresAt;
    }
}
