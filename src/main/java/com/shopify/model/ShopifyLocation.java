package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.DateTimeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyLocation {

	private String id;
	private String name;
	private String address1;
	private String address2;
	private String city;
	private String zip;
	private String country;
	private String phone;
	private String province;
	private boolean active;
	private boolean legacy;
	@XmlElement(name = "localized_country_name")
	private String localizedCountryName;
	@XmlElement(name = "localized_province_name")
	private String localizedProvinceName;

	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;

	@XmlElement(name = "country_code")
	private String countryCode;

	@XmlElement(name = "country_name")
	private String countryName;

	@XmlElement(name = "province_code")
	private String provinceCode;

	@XmlElement(name = "admin_graphql_api_id")
	private String adminGraphqlApiId;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getCity() {
		return city;
	}

	public String getZip() {
		return zip;
	}

	public String getCountry() {
		return country;
	}

	public String getPhone() {
		return phone;
	}

	public String getProvince() {
		return province;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setAddress1(final String address1) {
		this.address1 = address1;
	}

	public void setAddress2(final String address2) {
		this.address2 = address2;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setZip(final String zip) {
		this.zip = zip;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setProvince(final String province) {
		this.province = province;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	public void setCountryName(final String countryName) {
		this.countryName = countryName;
	}

	public void setProvinceCode(final String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final DateTime createdAt) {
		this.createdAt = createdAt;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(final DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	public boolean getLegacy() {
		return legacy;
	}

	public void setLegacy(final boolean legacy) {
		this.legacy = legacy;
	}

	public String getLocalizedCountryName() {
		return localizedCountryName;
	}

	public String getLocalizedProvinceName() {
		return localizedProvinceName;
	}

	public void setLocalizedCountryName(final String localizedCountryName) {
		this.localizedCountryName = localizedCountryName;
	}

	public void setLocalizedProvinceName(final String localizedProvinceName) {
		this.localizedProvinceName = localizedProvinceName;
	}

	public String getAdminGraphqlApiId() {
		return adminGraphqlApiId;
	}

	public void setAdminGraphqlApiId(String adminGraphqlApiId) {
		this.adminGraphqlApiId = adminGraphqlApiId;
	}
}
