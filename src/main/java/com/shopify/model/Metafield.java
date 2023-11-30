package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;
import org.joda.time.DateTime;

import com.shopify.model.adapters.DateTimeAdapter;
import com.shopify.model.adapters.MetafieldTypeAdapter;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Metafield {

	private String id;
	private String key;
	private String value;
	@XmlElement(name = "type")
	@XmlJavaTypeAdapter(MetafieldTypeAdapter.class)
	private MetafieldType type;
	private String namespace;
	@XmlElement(name = "owner_id")
	private String ownerId;
	@XmlElement(name = "owner_resource")
	private String ownerResource;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;

}
