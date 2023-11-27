package com.shopify.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

import com.shopify.model.adapters.DateTimeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyGiftCard {

	private String id;
	private String note;
	@XmlElement(name = "api_client_id")
	private String apiClientId;
	private BigDecimal balance;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "initial_value")
	private BigDecimal initialValue;
	private String currency;
	@XmlElement(name = "customer_id")
	private String customerId;
	private String code;
	@XmlElement(name = "disabled_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime disabledAt;
	@XmlElement(name = "expires_on")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime expiresOn;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	@XmlElement(name = "last_characters")
	private String lastCharacters;
	@XmlElement(name = "line_item_id")
	private String lineItemId;
	@XmlElement(name = "user_id")
	private String userId;
	@XmlElement(name = "template_suffix")
	private String templateSuffix;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(final String note) {
		this.note = note;
	}

	public String getApiClientId() {
		return apiClientId;
	}

	public void setApiClientId(final String apiClientId) {
		this.apiClientId = apiClientId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(final BigDecimal balance) {
		this.balance = balance;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final DateTime createdAt) {
		this.createdAt = createdAt;
	}

	public BigDecimal getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(final BigDecimal initialValue) {
		this.initialValue = initialValue;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(final String customerId) {
		this.customerId = customerId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public DateTime getDisabledAt() {
		return disabledAt;
	}

	public void setDisabledAt(final DateTime disabledAt) {
		this.disabledAt = disabledAt;
	}

	public DateTime getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(final DateTime expiresOn) {
		this.expiresOn = expiresOn;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(final DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getLastCharacters() {
		return lastCharacters;
	}

	public void setLastCharacters(final String lastCharacters) {
		this.lastCharacters = lastCharacters;
	}

	public String getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(final String lineItemId) {
		this.lineItemId = lineItemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public String getTemplateSuffix() {
		return templateSuffix;
	}

	public void setTemplateSuffix(final String templateSuffix) {
		this.templateSuffix = templateSuffix;
	}

}
