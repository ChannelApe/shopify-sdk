package com.shopify.model;

public class ShopifyCustomCollectionCreationRequest {



	private final ShopifyCustomCollection request;

	public static interface OptionalsStep {
		OptionalsStep withBodyHtml(final String bodyHtml);
		OptionalsStep withHandle(final String handle);
		OptionalsStep isPublished(final boolean published);
		OptionalsStep withSortOrder(final String sortOrder);
		OptionalsStep withTemplateSuffix(final String templateSuffix);
		OptionalsStep withPublishedScope(final String publishedScope);

		ShopifyCustomCollectionCreationRequest build();
	}


	public static interface MandatoryStep {
		OptionalsStep withTitle(final String title);
	}




	public static MandatoryStep newBuilder() {
		return new Steps();
	}

	public ShopifyCustomCollection getRequest() {
		return request;
	}

	private ShopifyCustomCollectionCreationRequest(final ShopifyCustomCollection request) {
		this.request = request;
	}

	private static class Steps implements MandatoryStep, OptionalsStep {

		private final ShopifyCustomCollection request = new ShopifyCustomCollection();

		@Override
		public ShopifyCustomCollectionCreationRequest build() {
			return new ShopifyCustomCollectionCreationRequest(request);
		}

		@Override
		public OptionalsStep withTitle(final String title) {
			this.request.setTitle(title);
			return this;
		}

		@Override
		public OptionalsStep withBodyHtml(String bodyHtml) {
			request.setBodyHtml(bodyHtml);
			return this;
		}

		@Override
		public OptionalsStep withHandle(String handle) {
			request.setHandle(handle);
			return this;
		}

		@Override
		public OptionalsStep isPublished(boolean published) {
			request.setPublished(published);
			return this;
		}

		@Override
		public OptionalsStep withSortOrder(String sortOrder) {
			request.setSortOrder(sortOrder);
			return this;
		}

		@Override
		public OptionalsStep withTemplateSuffix(String templateSuffix) {
			request.setTemplateSuffix(templateSuffix);
			return this;
		}

		@Override
		public OptionalsStep withPublishedScope(String publishedScope) {
			request.setPublishedScope(publishedScope);
			return this;
		}
	}
}
