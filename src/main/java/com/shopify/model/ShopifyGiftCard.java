package com.shopify.model;

import java.math.BigDecimal;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;
import org.joda.time.DateTime;

import com.shopify.model.adapters.DateTimeAdapter;

@Data
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

}
