package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.DateTimeAdapter;
import com.shopify.model.adapters.EscapedStringAdapter;
import com.shopify.model.adapters.TagsAdapter;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyProduct {

	private String id;
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String title;
	private String status;
	@XmlElement(name = "product_type")
	private String productType;
	@XmlElement(name = "body_html")
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String bodyHtml;
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String vendor;
	@XmlJavaTypeAdapter(TagsAdapter.class)
	@XmlElement(name = "tags")
	private String tags;
	private List<Option> options;
	@XmlElement(name = "metafields_global_title_tag")
	private String metafieldsGlobalTitleTag;
	@XmlElement(name = "metafields_global_description_tag")
	private String metafieldsGlobalDescriptionTag;
	private List<Image> images;
	private Image image;
	private List<ShopifyVariant> variants;
	@XmlElement(name = "published_at")
	private String publishedAt;
	@XmlElement(name = "published_scope")
	private String publishedScope;
	@XmlElement(name = "template_suffix")
	private String templateSuffix;
	private String handle;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;

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

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getBodyHtml() {
		return bodyHtml;
	}

	public void setBodyHtml(String bodyHtml) {
		this.bodyHtml = bodyHtml;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getPublishedScope() {
		return publishedScope;
	}

	public void setPublishedScope(String publishedScope) {
		this.publishedScope = publishedScope;
	}

	public String getTemplateSuffix() {
		return templateSuffix;
	}

	public void setTemplateSuffix(String templateSuffix) {
		this.templateSuffix = templateSuffix;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public String getMetafieldsGlobalTitleTag() {
		return metafieldsGlobalTitleTag;
	}

	public void setMetafieldsGlobalTitleTag(String metafieldsGlobalTitleTag) {
		this.metafieldsGlobalTitleTag = metafieldsGlobalTitleTag;
	}

	public String getMetafieldsGlobalDescriptionTag() {
		return metafieldsGlobalDescriptionTag;
	}

	public void setMetafieldsGlobalDescriptionTag(String metafieldsGlobalDescriptionTag) {
		this.metafieldsGlobalDescriptionTag = metafieldsGlobalDescriptionTag;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<ShopifyVariant> getVariants() {
		return variants;
	}

	public void setVariants(List<ShopifyVariant> variants) {
		this.variants = variants;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public List<String> getSortedOptionNames() {
		final Comparator<Option> optionPositionCompartor = new Comparator<Option>() {
			@Override
			public int compare(final Option o1, final Option o2) {
				return o1.getPosition() - o2.getPosition();
			}
		};
		if (null == options) return null;
		return options.stream().sorted(optionPositionCompartor).map(Option::getName).collect(Collectors.toList());
	}

}
