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
public class ClientDetails {
    @XmlElement(name = "accept_language")
    private String acceptLanguage;
    @XmlElement(name = "browser_height")
    private String browserHeight;
    @XmlElement(name = "browser_ip")
    private String browserIp;
    @XmlElement(name = "browser_width")
    private String browserWidth;
    @XmlElement(name = "session_hash")
    private String sessionHash;
    @XmlElement(name = "user_agent")
    private String userAgent;
}
