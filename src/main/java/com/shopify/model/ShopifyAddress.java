package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyAddress {

	private Long id;
	private Long customer_id;
	@XmlElement(name = "first_name")
	private String firstName;
	@XmlElement(name = "last_name")
	private String lastname;
	private String name;
	private String company;
	private String address1;
	private String address2;
	private String city;
	private String zip;
	private String province;
	private String country;
	@XmlElement(name = "province_code")
	private String provinceCode;
	@XmlElement(name = "country_code")
	private String countryCode;
	@XmlElement(name = "country_name")
	private String countryName;
	private String phone;
	private BigDecimal latitude;
	private BigDecimal longitude;
	@XmlElement(name = "default")
	private Boolean addressDefault;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(final Long customer_id) {
		this.customer_id = customer_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(final String company) {
		this.company = company;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(final String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(final String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(final String zip) {
		this.zip = zip;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(final String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(final String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(final String countryName) {
		this.countryName = countryName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(final BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(final BigDecimal longitude) {
		this.longitude = longitude;
	}

	public Boolean getAddressDefault() {
		return addressDefault;
	}

	public void setAddressDefault(final Boolean addressDefault) {
		this.addressDefault = addressDefault;
	}
}
