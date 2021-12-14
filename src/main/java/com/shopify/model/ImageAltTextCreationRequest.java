package com.shopify.model;

import java.util.Arrays;
import java.util.List;

public class ImageAltTextCreationRequest {

	static final String KEY = "alt";
	static final String NAMESPACE = "tags";
	static final MetafieldType TYPE = MetafieldType.SINGLE_LINE_TEXT;

	public static interface ImageAltTextStep {
		public BuildStep withImageAltText(final String imageAltText);
	}

	public static interface BuildStep {
		public List<Metafield> build();
	}

	public static ImageAltTextStep newBuilder() {
		return new Steps();
	}

	private static class Steps implements ImageAltTextStep, BuildStep {

		private Metafield imageAltTextMetafield;

		@Override
		public List<Metafield> build() {
			return Arrays.asList(imageAltTextMetafield);
		}

		@Override
		public BuildStep withImageAltText(String imageAltText) {
			this.imageAltTextMetafield = new Metafield();
			imageAltTextMetafield.setKey(KEY);
			imageAltTextMetafield.setValue(imageAltText);
			imageAltTextMetafield.setNamespace(NAMESPACE);
			imageAltTextMetafield.setType(TYPE);
			return this;
		}

	}

}
