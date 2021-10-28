package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import com.shopify.model.adapters.EscapedStringAdapter;
import com.shopify.model.adapters.TagsAdapter;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.*;
import java.util.stream.Collectors;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyProduct extends AbstractModel {

	private String id;
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String title;
	@XmlElement(name = "product_type")
	private String productType;
	@XmlElement(name = "body_html")
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String bodyHtml;
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String vendor;
	@XmlJavaTypeAdapter(TagsAdapter.class)
	@XmlElement(name = "tags")
	private Set<String> tags = new HashSet<>();
	private List<Option> options = new LinkedList<>();
	@XmlElement(name = "metafields_global_title_tag")
	private String metafieldsGlobalTitleTag;
	@XmlElement(name = "metafields_global_description_tag")
	private String metafieldsGlobalDescriptionTag;
	private List<Image> images = new LinkedList<>();
	private Image image;
	private List<ShopifyVariant> variants = new LinkedList<>();
	@XmlElement(name = "published_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private String publishedAt;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private String createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private String updatedAt;
	private Boolean published;
	private String handle;
	@XmlElement(name = "template_suffix")
	private String templateSuffix;
	private String status;
	@XmlElement(name = "published_scope")
	private String publishedScope;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(final String productType) {
		this.productType = productType;
	}

	public String getBodyHtml() {
		return bodyHtml;
	}

	public void setBodyHtml(final String bodyHtml) {
		this.bodyHtml = bodyHtml;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(final String vendor) {
		this.vendor = vendor;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(final Set<String> tags) {
		this.tags = tags;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(final List<Option> options) {
		this.options = options;
	}

	public String getMetafieldsGlobalTitleTag() {
		return metafieldsGlobalTitleTag;
	}

	public void setMetafieldsGlobalTitleTag(final String metafieldsGlobalTitleTag) {
		this.metafieldsGlobalTitleTag = metafieldsGlobalTitleTag;
	}

	public String getMetafieldsGlobalDescriptionTag() {
		return metafieldsGlobalDescriptionTag;
	}

	public void setMetafieldsGlobalDescriptionTag(final String metafieldsGlobalDescriptionTag) {
		this.metafieldsGlobalDescriptionTag = metafieldsGlobalDescriptionTag;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(final List<Image> images) {
		this.images = images;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(final Image image) {
		this.image = image;
	}

	public List<ShopifyVariant> getVariants() {
		return variants;
	}

	public void setVariants(final List<ShopifyVariant> variants) {
		this.variants = variants;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(final String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(final String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean isPublished() {
		return (published == null) ? StringUtils.isNotBlank(publishedAt) : published;
	}

	public void setPublished(final Boolean published) {
		this.published = published;
	}

	public List<String> getSortedOptionNames() {
		final Comparator<Option> optionPositionCompartor = new Comparator<Option>() {
			@Override
			public int compare(final Option o1, final Option o2) {
				return o1.getPosition() - o2.getPosition();
			}
		};
		return options.stream().sorted(optionPositionCompartor).map(Option::getName).collect(Collectors.toList());
	}

	public Boolean getPublished() {
		return published;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(final String handle) {
		this.handle = handle;
	}

	public String getTemplateSuffix() {
		return templateSuffix;
	}

	public void setTemplateSuffix(final String templateSuffix) {
		this.templateSuffix = templateSuffix;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getPublishedScope() {
		return publishedScope;
	}

	public void setPublishedScope(final String publishedScope) {
		this.publishedScope = publishedScope;
	}
}
