package com.shopify.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class ShopifyProductUpdateRequest implements ShopifyProductRequest {

	private final ShopifyProduct request;
	private final Map<Integer, Integer> variantPositionToImagePosition;
	private final boolean changed;

	public static interface CurrentShopifyProductStep {
		public TitleStep withCurrentShopifyProduct(final ShopifyProduct shopifyProduct);
	}

	public static interface TitleStep {
		public MetafieldsGlobalTitleTagStep withTitle(final String title);

		public MetafieldsGlobalTitleTagStep withSameTitle();
	}

	public static interface MetafieldsGlobalTitleTagStep {
		public ProductTypeStep withMetafieldsGlobalTitleTag(final String metafieldsGlobalTitleTag);

		public ProductTypeStep withSameMetafieldsGlobalTitleTag();
	}

	public static interface ProductTypeStep {
		public BodyHtmlStep withProductType(final String productType);

		public BodyHtmlStep withSameProductType();
	}

	public static interface BodyHtmlStep {
		public MetafieldsGlobalDescriptionTagStep withBodyHtml(final String bodyHtml);

		public MetafieldsGlobalDescriptionTagStep withSameBodyHtml();
	}

	public static interface MetafieldsGlobalDescriptionTagStep {
		public VendorStep withMetafieldsGlobalDescriptionTag(final String metafieldsGlobalDescriptionTag);

		public VendorStep withSameMetafieldsGlobalDescriptionTag();
	}

	public static interface VendorStep {
		public TagsStep withVendor(final String vendor);

		public TagsStep withSameVendor();
	}

	public static interface TagsStep {
		public SortedOptionNamesStep withTags(final Set<String> tags);

		public SortedOptionNamesStep withSameTags();
	}

	public static interface SortedOptionNamesStep {
		public ImageSourcesStep withSortedOptionNames(final List<String> sortedOptionNames);

		public ImageSourcesStep withSameOptions();
	}

	public static interface ImageSourcesStep {
		public VariantUpdateRequestsStep withImageSources(final List<String> imageSources);

		public VariantUpdateRequestsStep withSameImages();
	}

	public static interface VariantUpdateRequestsStep {
		public PublishedStep withVariantRequests(final List<ShopifyVariantRequest> variantRequests);

		public PublishedStep withSameVariants();
	}

	public static interface PublishedStep {
		public BuildStep withPublished(final boolean published);
	}

	public static interface BuildStep {
		public ShopifyProductUpdateRequest build();
	}

	public static CurrentShopifyProductStep newBuilder() {
		return new Steps();
	}

	@Override
	public ShopifyProduct getRequest() {
		return request;
	}

	@Override
	public int getVariantImagePosition(final int variantPosition) {
		return variantPositionToImagePosition.get(variantPosition);
	}

	@Override
	public boolean hasVariantImagePosition(final int variantPosition) {
		return variantPositionToImagePosition.containsKey(variantPosition);
	}

	@Override
	public boolean hasChanged() {
		return changed;
	}

	private ShopifyProductUpdateRequest(final ShopifyProduct shopifyProduct,
			final Map<Integer, Integer> variantPositionToImagePosition, final boolean changed) {
		this.request = shopifyProduct;
		this.variantPositionToImagePosition = variantPositionToImagePosition;
		this.changed = changed;
	}

	private static class Steps implements CurrentShopifyProductStep, TitleStep, MetafieldsGlobalTitleTagStep,
			MetafieldsGlobalDescriptionTagStep, ProductTypeStep, BodyHtmlStep, VendorStep, TagsStep,
			SortedOptionNamesStep, ImageSourcesStep, VariantUpdateRequestsStep, PublishedStep, BuildStep {

		private ShopifyProduct shopifyProduct;
		private Map<Integer, Integer> variantPositionToImagePosition = new HashMap<>();
		private boolean changed;

		@Override
		public ShopifyProductUpdateRequest build() {
			final List<Option> options = shopifyProduct.getOptions();
			if (options != null) {
				options.stream().forEach(option -> option.setValues(null));
			}
			return new ShopifyProductUpdateRequest(shopifyProduct, variantPositionToImagePosition, changed);
		}

		@Override
		public PublishedStep withVariantRequests(final List<ShopifyVariantRequest> variantRequests) {
			if (variantRequests.size() != shopifyProduct.getVariants().size()) {
				changed = true;
			}

			final List<ShopifyVariant> shopifyVariants = new ArrayList<>(variantRequests.size());
			final List<Integer> positions = new ArrayList<>(variantRequests.size());

			for (int i = 0; i < variantRequests.size(); i++) {
				final ShopifyVariantRequest shopifyVariantRequestForPosition = variantRequests.get(i);
				positions.add(shopifyVariantRequestForPosition.getRequest().getPosition());

			}

			int maxPosition = variantRequests.stream().map(ShopifyVariantRequest::getRequest)
					.map(ShopifyVariant::getPosition).max(Comparator.naturalOrder()).get();
			Collections.sort(variantRequests, new ShopifyVariantRequestOption1Comparator());
			for (int i = 0; i < variantRequests.size(); i++) {
				final ShopifyVariantRequest shopifyVariantRequest = variantRequests.get(i);
				if (shopifyVariantRequest.hasChanged()) {
					changed = true;
				}

				final ShopifyVariant shopifyVariant = shopifyVariantRequest.getRequest();

				if (shopifyVariant.getPosition() == 0) {

					maxPosition = maxPosition + 1;
					shopifyVariant.setPosition(maxPosition);
				}

				if (shopifyVariantRequest.hasImageSource()) {
					final String imageSource = shopifyVariantRequest.getImageSource();
					shopifyProduct.getImages().stream().filter(image -> image.getSource().equals(imageSource))
							.findFirst().ifPresent(image -> {
								variantPositionToImagePosition.put(shopifyVariant.getPosition(), image.getPosition());
							});
				}

			}

			Collections.sort(variantRequests, new ShopifyVariantRequestPositionComparator());

			for (int i = 0; i < variantRequests.size(); i++) {
				final ShopifyVariantRequest shopifyVariantRequest = variantRequests.get(i);
				if (shopifyVariantRequest.hasChanged()) {
					changed = true;
				}

				final ShopifyVariant shopifyVariant = shopifyVariantRequest.getRequest();
				shopifyVariants.add(shopifyVariant);

			}

			shopifyProduct.setVariants(shopifyVariants);
			return this;
		}

		@Override
		public VariantUpdateRequestsStep withImageSources(final List<String> imageSources) {
			final List<String> currentImageSources = shopifyProduct.getImages().stream()
					.sorted((Image i1, Image i2) -> Integer.compare(i1.getPosition(), i2.getPosition()))
					.map(Image::getSource).collect(Collectors.toList());

			if (currentImageSources.size() != imageSources.size()) {
				changed = true;
			}

			final List<Image> images = new ArrayList<>(imageSources.size());
			int position = 1;
			final Iterator<String> imageSourceIterator = imageSources.iterator();
			while (imageSourceIterator.hasNext()) {
				final String imageSource = imageSourceIterator.next();
				final Image image = new Image();
				image.setPosition(position);
				image.setSource(imageSource);
				final List<Metafield> metafields = ImageAltTextCreationRequest.newBuilder()
						.withImageAltText(shopifyProduct.getTitle()).build();
				image.setMetafields(metafields);
				position++;
				images.add(image);
			}

			shopifyProduct.setImages(images);
			return this;
		}

		@Override
		public ImageSourcesStep withSortedOptionNames(final List<String> sortedOptionNames) {
			if (doesNotEqual(new HashSet<>(sortedOptionNames), new HashSet<>(shopifyProduct.getSortedOptionNames()))) {
				final List<Option> options = new ArrayList<>(sortedOptionNames.size());
				for (int i = 0; i < sortedOptionNames.size(); i++) {
					final String optionKey = sortedOptionNames.get(i);
					final Option option = new Option();
					option.setName(optionKey);
					final int position = i + 1;
					option.setPosition(position);
					options.add(option);
				}
				shopifyProduct.setOptions(options);
				changed = true;
			}
			return this;
		}

		@Override
		public SortedOptionNamesStep withTags(final Set<String> tags) {
			if (doesNotEqual(tags, shopifyProduct.getTags())) {
				shopifyProduct.setTags(tags);
				changed = true;
			}
			return this;
		}

		@Override
		public TagsStep withVendor(final String vendor) {
			if (doesNotEqual(vendor, shopifyProduct.getVendor())) {
				shopifyProduct.setVendor(vendor);
				changed = true;
			}
			return this;
		}

		@Override
		public MetafieldsGlobalDescriptionTagStep withBodyHtml(final String bodyHtml) {
			if (doesNotEqual(bodyHtml, shopifyProduct.getBodyHtml())) {
				shopifyProduct.setBodyHtml(bodyHtml);
				changed = true;
			}
			return this;
		}

		@Override
		public BodyHtmlStep withProductType(final String productType) {
			if (doesNotEqual(productType, shopifyProduct.getProductType())) {
				shopifyProduct.setProductType(productType);
				changed = true;
			}
			return this;
		}

		@Override
		public MetafieldsGlobalTitleTagStep withTitle(final String title) {
			if (doesNotEqual(title, shopifyProduct.getTitle())) {
				shopifyProduct.setTitle(title);
				changed = true;
			}
			return this;
		}

		@Override
		public VendorStep withMetafieldsGlobalDescriptionTag(String metafieldsGlobalDescriptionTag) {
			if (doesNotEqual(metafieldsGlobalDescriptionTag, shopifyProduct.getMetafieldsGlobalDescriptionTag())) {
				shopifyProduct.setMetafieldsGlobalDescriptionTag(metafieldsGlobalDescriptionTag);
				changed = true;
			}
			return this;
		}

		@Override
		public ProductTypeStep withMetafieldsGlobalTitleTag(String metafieldsGlobalTitleTag) {
			if (doesNotEqual(metafieldsGlobalTitleTag, shopifyProduct.getMetafieldsGlobalTitleTag())) {
				shopifyProduct.setMetafieldsGlobalTitleTag(metafieldsGlobalTitleTag);
				changed = true;
			}
			return this;
		}

		@Override
		public BuildStep withPublished(boolean published) {
			if (shopifyProduct.isPublished() != published) {
				final String publishedAt = published ? DateTime.now(DateTimeZone.UTC).toString() : null;
				shopifyProduct.setPublishedAt(publishedAt);
				shopifyProduct.setPublished(published);
				changed = true;
			}
			return this;
		}

		@Override
		public TitleStep withCurrentShopifyProduct(final ShopifyProduct shopifyProduct) {
			this.shopifyProduct = shopifyProduct;
			return this;
		}

		@Override
		public PublishedStep withSameVariants() {
			return this;
		}

		@Override
		public VariantUpdateRequestsStep withSameImages() {
			return this;
		}

		@Override
		public ImageSourcesStep withSameOptions() {
			return this;
		}

		@Override
		public SortedOptionNamesStep withSameTags() {
			return this;
		}

		@Override
		public TagsStep withSameVendor() {
			return this;
		}

		@Override
		public MetafieldsGlobalDescriptionTagStep withSameBodyHtml() {
			return this;
		}

		@Override
		public BodyHtmlStep withSameProductType() {
			return this;
		}

		@Override
		public MetafieldsGlobalTitleTagStep withSameTitle() {
			return this;
		}

		@Override
		public ProductTypeStep withSameMetafieldsGlobalTitleTag() {
			return this;
		}

		@Override
		public VendorStep withSameMetafieldsGlobalDescriptionTag() {
			return this;
		}

		private boolean doesNotEqual(final String s1, final String s2) {
			final String unescapedS1 = StringEscapeUtils.unescapeHtml4(StringUtils.trim(s1));
			final String unescapedS2 = StringEscapeUtils.unescapeHtml4(StringUtils.trim(s2));
			return !StringUtils.equals(unescapedS1, unescapedS2);
		}

		private boolean doesNotEqual(final Set<String> s1, final Set<String> s2) {
			return !s1.equals(s2);
		}

	}
}
