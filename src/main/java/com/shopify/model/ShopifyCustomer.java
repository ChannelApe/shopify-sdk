package com.shopify.model;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.shopify.model.adapters.DateTimeAdapter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyCustomer {

	private String id;
	private String email;
	@XmlElement(name = "accepts_marketing")
	private boolean acceptsMarketing;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	@XmlElement(name = "first_name")
	private String firstName;
	@XmlElement(name = "last_name")
	private String lastname;
	private String phone;
	@XmlElement(name = "orders_count")
	private long ordersCount;
	private String state;
	@XmlElement(name = "total_spent")
	private BigDecimal totalSpent;
	private String note;

}
