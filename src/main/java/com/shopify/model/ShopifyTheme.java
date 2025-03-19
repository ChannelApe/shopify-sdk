package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.joda.time.DateTime;

@XmlRootElement
public class ShopifyTheme {
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	private String id;
	private String name;
	private boolean previewable;
	private boolean processing;
	private String role;
	private String src;
	@XmlElement(name = "theme_store_id")
	private String themeStoreId;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	@XmlElement(name = "admin_graphql_api_id")
	private String adminGraphqlApiId;

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}

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

	public boolean isPreviewable() {
		return previewable;
	}

	public void setPreviewable(boolean previewable) {
		this.previewable = previewable;
	}

	public boolean isProcessing() {
		return processing;
	}

	public void setProcessing(boolean processing) {
		this.processing = processing;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getThemeStoreId() {
		return themeStoreId;
	}

	public void setThemeStoreId(String themeStoreId) {
		this.themeStoreId = themeStoreId;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getAdminGraphqlApiId() {
		return adminGraphqlApiId;
	}

	public void setAdminGraphqlApiId(String adminGraphqlApiId) {
		this.adminGraphqlApiId = adminGraphqlApiId;
	}
}
