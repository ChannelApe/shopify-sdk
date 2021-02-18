package com.shopify.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.EscapedStringAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyRecurringApplicationCharge {

	private String id;
	@XmlElement(name = "api_client_id")
	private String apiClientId;
	private String name;
	private String terms;
	private BigDecimal price;
	@XmlElement(name = "capped_amount")
	private BigDecimal cappedAmount;
	private String status;
	@XmlElement(name = "return_url")
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String returnUrl;
	@XmlElement(name = "confirmation_url")
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String confirmationUrl;
	@XmlElement(name = "trial_days")
	private int trialDays;
	@XmlElement(name = "trial_end_on")
	private String trialEndsOn;
	@XmlElement(name = "activated_on")
	private String activatedOn;
	@XmlElement(name = "billing_on")
	private String billingOn;
	@XmlElement(name = "cancelled_on")
	private String cancelledOn;
	@XmlElement(name = "created_at")
	private String createdAt;
	@XmlElement(name = "updated_on")
	private String updatedOn;
	private Boolean test;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApiClientId() {
		return apiClientId;
	}

	public void setApiClientId(String apiClientId) {
		this.apiClientId = apiClientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getCappedAmount() {
		return cappedAmount;
	}

	public void setCappedAmount(BigDecimal cappedAmount) {
		this.cappedAmount = cappedAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getConfirmationUrl() {
		return confirmationUrl;
	}

	public void setConfirmationUrl(String confirmationUrl) {
		this.confirmationUrl = confirmationUrl;
	}

	public int getTrialDays() {
		return trialDays;
	}

	public void setTrialDays(int trialDays) {
		this.trialDays = trialDays;
	}

	public String getTrialEndsOn() {
		return trialEndsOn;
	}

	public void setTrialEndsOn(String trialEndsOn) {
		this.trialEndsOn = trialEndsOn;
	}

	public String getActivatedOn() {
		return activatedOn;
	}

	public void setActivatedOn(String activatedOn) {
		this.activatedOn = activatedOn;
	}

	public String getBillingOn() {
		return billingOn;
	}

	public void setBillingOn(String billingOn) {
		this.billingOn = billingOn;
	}

	public String getCancelledOn() {
		return cancelledOn;
	}

	public void setCancelledOn(String cancelledOn) {
		this.cancelledOn = cancelledOn;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Boolean isTest() {
		return test;
	}

	public void setTest(Boolean test) {
		this.test = test;
	}
}
