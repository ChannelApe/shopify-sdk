package com.shopify.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.shopify.model.adapters.DateTimeAdapter;
import org.joda.time.DateTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifySmartCollection {
	private String id;
	private String title;
	private String handle;

	@XmlElement(name = "body_html")
	private String bodyHtml;

	@XmlElement(name = "published_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime publishedAt;

	@XmlElement(name = "published_scope")
	private String publishedScope;

	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;

	private List<ShopifySmartCollectionRule> rules;
	private boolean disjunctive;

	@XmlElement(name = "sort_order")
	private String sortOrder;

	@XmlElement(name = "template_suffix")
	private String templateSuffix;

	public String getId() {
		return id;
	}

	public ShopifySmartCollection setId(String id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public ShopifySmartCollection setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getHandle() {
		return handle;
	}

	public ShopifySmartCollection setHandle(String handle) {
		this.handle = handle;
		return this;
	}

	public String getBodyHtml() {
		return bodyHtml;
	}

	public ShopifySmartCollection setBodyHtml(String bodyHtml) {
		this.bodyHtml = bodyHtml;
		return this;
	}

	public DateTime getPublishedAt() {
		return publishedAt;
	}

	public ShopifySmartCollection setPublishedAt(DateTime publishedAt) {
		this.publishedAt = publishedAt;
		return this;
	}

	public String getPublishedScope() {
		return publishedScope;
	}

	public ShopifySmartCollection setPublishedScope(String publishedScope) {
		this.publishedScope = publishedScope;
		return this;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public ShopifySmartCollection setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}

	public List<ShopifySmartCollectionRule> getRules() {
		return rules;
	}

	public ShopifySmartCollection setRules(List<ShopifySmartCollectionRule> rules) {
		this.rules = rules;
		return this;
	}

	public boolean isDisjunctive() {
		return disjunctive;
	}

	public ShopifySmartCollection setDisjunctive(boolean disjunctive) {
		this.disjunctive = disjunctive;
		return this;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public ShopifySmartCollection setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
		return this;
	}

	public String getTemplateSuffix() {
		return templateSuffix;
	}

	public ShopifySmartCollection setTemplateSuffix(String templateSuffix) {
		this.templateSuffix = templateSuffix;
		return this;
	}
}
