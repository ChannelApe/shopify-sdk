package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@XmlRootElement
public class Shop {

	private String id;
	private String name;
	private String address1;
	private String address2;
	@XmlElement(name = "checkout_api_supported")
	private boolean checkoutApiSupported;
	private String city;
	private String country;
	@XmlElement(name = "country_code")
	private String countryCode;
	@XmlElement(name = "country_name")
	private String countryName;
	@XmlElement(name = "country_taxes")
	private Boolean countryTaxes;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	@XmlElement(name = "customer_email")
	private String customerEmail;
	private Currency currency;
	private String domain;
	@XmlElement(name = "enabled_presentment_currencies")
	private List<String> enabledPresentmentCurrencies;
	@XmlElement(name = "eligible_for_card_reader_giveaway")
	private boolean eligibleForCardReaderGiveaway;
	@XmlElement(name = "eligible_for_payments")
	private boolean eligibleForPayments;
	private String email;
	@XmlElement(name = "force_ssl")
	private boolean forceSsl;
	@XmlElement(name = "google_apps_domain")
	private String googleAppsDomain;
	@XmlElement(name = "google_apps_login_enabled")
	private Boolean googleAppsLoginEnabled;
	@XmlElement(name = "has_discounts")
	private boolean hasDiscounts;
	@XmlElement(name = "has_gift_cards")
	private boolean hasGiftCards;
	@XmlElement(name = "has_storefront")
	private boolean hasStorefront;
	@XmlElement(name = "iana_timezone")
	private String ianaTimezone;
	private BigDecimal latitude;
	private BigDecimal longitude;
	@XmlElement(name = "money_format")
	private String moneyFormat;
	@XmlElement(name = "money_in_emails_format")
	private String moneyInEmailsFormat;
	@XmlElement(name = "money_with_currency_format")
	private String moneyWithCurrencyFormat;
	@XmlElement(name = "money_with_currency_in_emails_format")
	private String moneyWithCurrencyInEmailsFormat;
	@XmlElement(name = "multi_location_enabled")
	private boolean multiLocationEnabled;
	@XmlElement(name = "myshopify_domain")
	private String myshopifyDomain;
	@XmlElement(name = "password_enabled")
	private boolean passwordEnabled;
	private String phone;
	@XmlElement(name = "plan_display_name")
	private String planDisplayName;
	@XmlElement(name = "pre_launch_enabled")
	private boolean preLaunchEnabled;
	@XmlElement(name = "cookie_consent_level")
	private String cookieConsentLevel;
	@XmlElement(name = "plan_name")
	private String planName;
	@XmlElement(name = "primary_locale")
	private String primaryLocale;
	private String province;
	@XmlElement(name = "province_code")
	private String provinceCode;
	@XmlElement(name = "requires_extra_payments_agreement")
	private boolean requiresExtraPaymentsAgreement;
	@XmlElement(name = "setup_required")
	private boolean setupRequired;
	@XmlElement(name = "shop_owner")
	private String shopOwner;
	private String source;
	@XmlElement(name = "taxes_included")
	private Boolean taxesIncluded;
	@XmlElement(name = "tax_shipping")
	private Boolean taxShipping;
	@XmlElement(name = "timezone")
	private String timezone;
	@XmlElement(name = "weight_unit")
	private String weightUnit;
	private String zip;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress1() {
		return address1;
	}
	public String getAddress2() {
		return address2;
	}
	public boolean getCheckoutApiSupported() {
		return checkoutApiSupported;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public Boolean getCountryTaxes() {
		return countryTaxes;
	}
	public DateTime getCreatedAt() {
		return createdAt;
	}
	public DateTime getUpdatedAt() {
		return updatedAt;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public Currency getCurrency() {
		return currency;
	}
	public String getDomain() {
		return domain;
	}
	public List<String> enabledPresentmentCurrencies() {
		return enabledPresentmentCurrencies;
	}
	public boolean getEligibleForCardReaderGiveaway() {
		return eligibleForCardReaderGiveaway;
	}
	public boolean getEligibleForPayments() {
		return eligibleForPayments;
	}
	public String getEmail() {
		return email;
	}
	public boolean getForceSsl() {
		return forceSsl;
	}
	public String getGoogleAppsDomain() {
		return googleAppsDomain;
	}
	public Boolean getGoogleAppsLoginEnabled() {
		return googleAppsLoginEnabled;
	}
	public boolean getHasDiscounts() {
		return hasDiscounts;
	}
	public boolean getHasGiftCards() {
		return hasGiftCards;
	}
	public boolean getHasStorefront() {
		return hasStorefront;
	}
	public String getIanaTimezone() {
		return ianaTimezone;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public String getMoneyFormat() {
		return moneyFormat;
	}
	public String getMoneyInEmailsFormat() {
		return moneyInEmailsFormat;
	}
	public String getMoneyWithCurrencyFormat() {
		return moneyWithCurrencyFormat;
	}
	public String getMoneyWithCurrencyInEmailsFormat() {
		return moneyWithCurrencyInEmailsFormat;
	}
	public boolean getMultiLocationEnabled() {
		return multiLocationEnabled;
	}
	public String getMyshopifyDomain() {
		return myshopifyDomain;
	}
	public boolean getPasswordEnabled() {
		return passwordEnabled;
	}
	public String getPhone() {
		return phone;
	}
	public String getPlanDisplayName() {
		return planDisplayName;
	}
	public boolean getPreLaunchEnabled() {
		return preLaunchEnabled;
	}
	public String getCookieConsentLevel() {
		return cookieConsentLevel;
	}
	public String getPlanName() {
		return planName;
	}
	public String getPrimaryLocale() {
		return primaryLocale;
	}
	public String getProvince() {
		return province;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public boolean getRequiresExtraPaymentsAgreement() {
		return requiresExtraPaymentsAgreement;
	}
	public boolean getSetupRequired() {
		return setupRequired;
	}
	public String getShopOwner() {
		return shopOwner;
	}
	public String getSource() {
		return source;
	}
	public Boolean getTaxesIncluded() {
		return taxesIncluded;
	}
	public Boolean getTaxShipping() {
		return taxShipping;
	}
	public String getTimezone() {
		return timezone;
	}
	public String getWeightUnit() {
		return weightUnit;
	}
	public String getZip() {
		return zip;
	}

	public void setAddress1(final String address1) {
		this.address1 = address1;
	}
	public void setAddress2(final String address2) {
		this.address2 = address2;
	}
	public void setCheckoutApiSupported(final boolean checkoutApiSupported) {
		this.checkoutApiSupported = checkoutApiSupported;
	}
	public void setCity(final String city) {
		this.city = city;
	}
	public void setCountry(final String country) {
		this.country = country;
	}
	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}
	public void setCountryName(final String countryName) {
		this.countryName = countryName;
	}
	public void setCountryTaxes(final Boolean countryTaxes) {
		this.countryTaxes = countryTaxes;
	}
	public void setCreatedAt(final DateTime createdAt) {
		this.createdAt = createdAt;
	}
	public void setUpdatedAt(final DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public void setCustomerEmail(final String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public void setCurrency(final Currency currency) {
		this.currency = currency;
	}
	public void setDomain(final String domain) {
		this.domain = domain;
	}
	public void setEnabledPresentmentCurrencies(final List<String> enabledPresentmentCurrencies) {
		this.enabledPresentmentCurrencies = enabledPresentmentCurrencies;
	}
	public void setEligibleForCardReaderGiveaway(final boolean eligibleForCardReaderGiveaway) {
		this.eligibleForCardReaderGiveaway = eligibleForCardReaderGiveaway;
	}
	public void setEligibleForPayments(final boolean eligibleForPayments) {
		this.eligibleForPayments = eligibleForPayments;
	}
	public void setEmail(final String email) {
		this.email = email;
	}
	public void setForceSsl(final boolean forceSsl) {
		this.forceSsl = forceSsl;
	}
	public void setGoogleAppsDomain(final String googleAppsDomain) {
		this.googleAppsDomain = googleAppsDomain;
	}
	public void setGoogleAppsLoginEnabled(final Boolean googleAppsLoginEnabled) {
		this.googleAppsLoginEnabled = googleAppsLoginEnabled;
	}
	public void setHasDiscounts(final boolean hasDiscounts) {
		this.hasDiscounts = hasDiscounts;
	}
	public void setHasGiftCards(final boolean hasGiftCards) {
		this.hasGiftCards = hasGiftCards;
	}
	public void setHasStorefront(final boolean hasStorefront) {
		this.hasStorefront = hasStorefront;
	}
	public void setIanaTimezone(final String ianaTimezone) {
		this.ianaTimezone = ianaTimezone;
	}
	public void setLatitude(final BigDecimal latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(final BigDecimal longitude) {
		this.longitude = longitude;
	}
	public void setMoneyFormat(final String moneyFormat) {
		this.moneyFormat = moneyFormat;
	}
	public void setMoneyInEmailsFormat(final String moneyInEmailsFormat) {
		this.moneyInEmailsFormat = moneyInEmailsFormat;
	}
	public void setMoneyWithCurrencyFormat(final String moneyWithCurrencyFormat) {
		this.moneyWithCurrencyFormat = moneyWithCurrencyFormat;
	}
	public void setMoneyWithCurrencyInEmailsFormat(final String moneyWithCurrencyInEmailsFormat) {
		this.moneyWithCurrencyInEmailsFormat = moneyWithCurrencyInEmailsFormat;
	}
	public void setMultiLocationEnabled(final boolean multiLocationEnabled) {
		this.multiLocationEnabled = multiLocationEnabled;
	}
	public void setMyshopifyDomain(final String myshopifyDomain) {
		this.myshopifyDomain = myshopifyDomain;
	}
	public void setPasswordEnabled(final boolean passwordEnabled) {
		this.passwordEnabled = passwordEnabled;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}
	public void setPlanDisplayName(final String planDisplayName) {
		this.planDisplayName = planDisplayName;
	}
	public void setPreLaunchEnabled(final boolean preLaunchEnabled) {
		this.preLaunchEnabled = preLaunchEnabled;
	}
	public void setCookieConsentLevel(final String cookieConsentLevel) {
		this.cookieConsentLevel = cookieConsentLevel;
	}
	public void setPlanName(final String planName) {
		this.planName = planName;
	}
	public void setPrimaryLocale(final String primaryLocale) {
		this.primaryLocale = primaryLocale;
	}
	public void setProvince(final String province) {
		this.province = province;
	}
	public void setProvinceCode(final String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public void setRequiresExtraPaymentsAgreement(final boolean requiresExtraPaymentsAgreement) {
		this.requiresExtraPaymentsAgreement = requiresExtraPaymentsAgreement;
	}
	public void setSetupRequired(final boolean setupRequired) {
		this.setupRequired = setupRequired;
	}
	public void setShopOwner(final String shopOwner) {
		this.shopOwner = shopOwner;
	}
	public void setSource(final String source) {
		this.source = source;
	}
	public void setTaxesIncluded(final Boolean taxesIncluded) {
		this.taxesIncluded = taxesIncluded;
	}
	public void setTaxShipping(final Boolean taxShipping) {
		this.taxShipping = taxShipping;
	}
	public void setTimezone(final String timezone) {
		this.timezone = timezone;
	}
	public void setWeightUnit(final String weightUnit) {
		this.weightUnit = weightUnit;
	}
	public void setZip(final String zip) {
		this.zip = zip;
	}
}
