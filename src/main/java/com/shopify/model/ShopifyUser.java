package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyUser {
    @XmlElement(name = "account_owner")
    private boolean accountOwner;
    private String bio;
    private String email;
    @XmlElement(name = "first_name")
    private String firstName;
    private String id;
    private String im;
    @XmlElement(name = "last_name")
    private String lastName;
    private List<String> permissions;
    private String phone;
    @XmlElement(name = "receive_announcements")
    private int receiveAnnouncements;
    private String url;
    private String locale;
    @XmlElement(name = "user_type")
    private String userType;
}
