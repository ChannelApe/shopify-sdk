package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import java.math.BigDecimal;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.shopify.model.adapters.EscapedStringAdapter;
import com.shopify.model.adapters.InventoryPolicyAdapter;
import org.joda.time.DateTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyWebhook {

	private String id;
	private String address;
	private String topic;
	private String api_version;
	private String created_at;
	private List<String> fields;
	private String format;
	@XmlElement(name = "metafield_namespaces")
	private List<String> metafieldNamespaces;
	@XmlElement(name = "private_metafield_namespaces")
	private List<String> privateMetafieldNamespaces;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getApi_version() {
		return api_version;
	}

	public void setApi_version(String api_version) {
		this.api_version = api_version;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<String> getMetafieldNamespaces() {
		return metafieldNamespaces;
	}

	public void setMetafieldNamespaces(List<String> metafieldNamespaces) {
		this.metafieldNamespaces = metafieldNamespaces;
	}

	public List<String> getPrivateMetafieldNamespaces() {
		return privateMetafieldNamespaces;
	}

	public void setPrivateMetafieldNamespaces(List<String> privateMetafieldNamespaces) {
		this.privateMetafieldNamespaces = privateMetafieldNamespaces;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
