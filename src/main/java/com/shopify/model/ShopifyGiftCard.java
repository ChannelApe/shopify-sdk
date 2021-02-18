package com.shopify.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.joda.time.DateTime;

import com.shopify.model.adapters.DateTimeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
	@XmlElement(name = "order_id")
	private String orderId;

}
