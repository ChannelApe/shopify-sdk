package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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


	public String getAdminGraphqlApiId() {
		return adminGraphqlApiId;
	}

	public String getBodyHtml() {
		return bodyHtml;
	}

	public String getHandle() {
		return handle;
	}

	public String getId() {
		return id;
	}

	public DateTime getPublishedAt() {
		return publishedAt;
	}

	public String getPublishedScope() {
		return publishedScope;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public String getTemplateSuffix() {
		return templateSuffix;
	}

	public String getTitle() {
		return title;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}


	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public void setBodyHtml(String bodyHtml) {
		this.bodyHtml = bodyHtml;
	}

	public void setPublishedScope(String publishedScope) {
		this.publishedScope = publishedScope;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setTemplateSuffix(String templateSuffix) {
		this.templateSuffix = templateSuffix;
	}

	public void setPublishedAt(DateTime publishedAt) {
		this.publishedAt = publishedAt;
	}

	public void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setAdminGraphqlApiId(String adminGraphqlApiId) {
		this.adminGraphqlApiId = adminGraphqlApiId;
	}
}
