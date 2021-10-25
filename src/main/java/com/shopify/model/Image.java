package com.shopify.model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.DateTimeAdapter;
import com.shopify.model.adapters.EscapedStringAdapter;
import org.joda.time.DateTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {

	private String id;
	@XmlElement(name = "product_id")
	private String productId;
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String name;
	private String alt;
	private int position;
	@XmlElement(name = "src")
	private String source;
	@XmlElement(name = "variant_ids")
	private List<String> variantIds = new LinkedList<>();
	private List<Metafield> metafields = new LinkedList<>();

	private BigDecimal width;
	private BigDecimal height;

	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;

	@XmlElement(name = "admin_graphql_api_id")
	private String adminGraphqlApiId;

	public DateTime getCreatedAt() {
		return createdAt;
	}
	public DateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setCreatedAt(final DateTime createdAt) {
		this.createdAt = createdAt;
	}
	public void setUpdatedAt(final DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<String> getVariantIds() {
		return variantIds;
	}

	public void setVariantIds(List<String> variantIds) {
		this.variantIds = variantIds;
	}

	public List<Metafield> getMetafields() {
		return metafields;
	}

	public void setMetafields(List<Metafield> metafields) {
		this.metafields = metafields;
	}

	public String getAdminGraphqlApiId() {
		return adminGraphqlApiId;
	}

	public void setAdminGraphqlApiId(String adminGraphqlApiId) {
		this.adminGraphqlApiId = adminGraphqlApiId;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}
}
