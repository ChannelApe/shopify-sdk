package com.shopify.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import com.shopify.model.adapters.EscapedStringAdapter;
import com.shopify.model.adapters.TagsAdapter;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyProduct {

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
	private String publishedAt;
	private Boolean published;

	public Boolean isPublished() {
		return (published == null) ? StringUtils.isNotBlank(publishedAt) : published;
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
}
