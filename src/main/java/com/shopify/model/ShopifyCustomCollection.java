package com.shopify.model;

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
public class ShopifyCustomCollection {

	private String id;
	private String title;
	private String handle;
	private boolean published;

	@XmlElement(name = "body_html")
	private String bodyHtml;

	@XmlElement(name = "published_scope")
	private String publishedScope;

	@XmlElement(name = "sort_order")
	private String sortOrder;

	@XmlElement(name = "template_suffix")
	private String templateSuffix;

	@XmlElement(name = "published_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime publishedAt;

	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;

	@XmlElement(name = "admin_graphql_api_id")
	private String adminGraphqlApiId;
}
