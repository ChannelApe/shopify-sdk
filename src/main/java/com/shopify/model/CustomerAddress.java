package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerAddress {
    private String address1;
    private String address2;
    private String city;
    private String country;
    private String province;
    private String zip;
    private String phone;
    @XmlElement(name = "province_code")
    private String provinceCode;
    @XmlElement(name = "country_code")
    private String countryCode;
    @XmlElement(name = "country_name")
    private String countryName;
    @XmlElement(name = "customer_id")
    private String customerId;
    @XmlElement(name = "first_name")
    private String firstName;
    @XmlElement(name = "last_name")
    private String lastName;
    private String company;
    private String id;

    private String name;
    @XmlElement(name = "default")
    private boolean defaultAddress;

    public String getAddress1() {
        return address1;
    }

    public String getId() {
        return id;
    }

    public String getAddress2() {
        return address2;
    }
    public String getCity() {
        return city;
    }
    public String getCountry() {
        return country;
    }
    public String getProvince() {
        return province;
    }
    public String getZip() {
        return zip;
    }
    public String getPhone() {
        return phone;
    }
    public String getProvinceCode() {
        return provinceCode;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public String getCountryName() {
        return countryName;
    }
    public String getCustomerId() {
        return customerId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getCompany() {
        return company;
    }
    public void setAddress1(final String address1) {
        this.address1 = address1;
    }
    public void setId(final String id) {
        this.id = id;
    }
    public void setAddress2(final String address2) {
        this.address2 = address2;
    }
    public void setCity(final String city) {
        this.city = city;
    }
    public void setCountry(final String country) {
        this.country = country;
    }
    public void setProvince(final String province) {
        this.province = province;
    }
    public void setZip(final String zip) {
        this.zip = zip;
    }
    public void setPhone(final String phone) {
        this.phone = phone;
    }
    public void setProvinceCode(final String provinceCode) {
        this.provinceCode = provinceCode;
    }
    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }
    public void setCountryName(final String countryName) {
        this.countryName = countryName;
    }
    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    public void setCompany(final String company) {
        this.company = company;
    }
}
