package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyInventoryItem {

	private String id;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;

	private BigDecimal cost;
	@XmlElement(name = "country_code_of_origin")
	private String countryCodeOfOrigin;
	@XmlElement(name = "country_harmonized_system_codes")
	private List<Object> countryHarmonizedSystemCodes;
	@XmlElement(name = "harmonized_system_code")
	private String harmonizedSystemCode;
	@XmlElement(name = "province_code_of_origin")
	private String provinceCodeOfOrigin;
	private String sku;
	private boolean tracked;
	@XmlElement(name = "requires_shipping")
	private boolean requiresShipping;

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(final DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final DateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public String getCountryCodeOfOrigin() {
		return countryCodeOfOrigin;
	}
	public List<Object> getCountryHarmonizedSystemCodes() {
		return countryHarmonizedSystemCodes;
	}
	public String getHarmonizedSystemCode() {
		return harmonizedSystemCode;
	}
	public String getProvinceCodeOfOrigin() {
		return provinceCodeOfOrigin;
	}
	public String getSku() {
		return sku;
	}
	public boolean getTracked() {
		return tracked;
	}
	public boolean getRequiresShipping() {
		return requiresShipping;
	}
	public void setCost(final BigDecimal cost) {
		this.cost = cost;
	}
	public void setCountryCodeOfOrigin(final String countryCodeOfOrigin) {
		this.countryCodeOfOrigin = countryCodeOfOrigin;
	}
	public void setCountryHarmonizedSystemCodes(final List<Object> countryHarmonizedSystemCodes) {
		this.countryHarmonizedSystemCodes = countryHarmonizedSystemCodes;
	}
	public void setHarmonizedSystemCode(final String harmonizedSystemCode) {
		this.harmonizedSystemCode = harmonizedSystemCode;
	}
	public void setProvinceCodeOfOrigin(final String provinceCodeOfOrigin) {
		this.provinceCodeOfOrigin = provinceCodeOfOrigin;
	}
	public void setSku(final String sku) {
		this.sku = sku;
	}
	public void setTracked(final boolean tracked) {
		this.tracked = tracked;
	}
	public void setRequiresShipping(final boolean requiresShipping) {
		this.requiresShipping = requiresShipping;
	}
}
