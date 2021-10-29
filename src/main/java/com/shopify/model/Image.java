package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import com.shopify.model.adapters.EscapedStringAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Image extends AbstractModel {

	private String id;
	@XmlElement(name = "product_id")
	private String productId;
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String name;
	private int position;
	@XmlElement(name = "src")
	private String source;
	@XmlElement(name = "variant_ids")
	private List<String> variantIds = new LinkedList<>();
	private List<Metafield> metafields = new LinkedList<>();
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private String createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private String updated_at;
	private String alt;
	private Long width;
	private Long height;

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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(final String updated_at) {
		this.updated_at = updated_at;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(final String alt) {
		this.alt = alt;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(final Long width) {
		this.width = width;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(final Long height) {
		this.height = height;
	}
}
