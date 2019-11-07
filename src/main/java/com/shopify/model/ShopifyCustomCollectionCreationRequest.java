package com.shopify.model;

public class ShopifyCustomCollectionCreationRequest {

	private final ShopifyCustomCollection request;


	public static interface InitialValueStep {
		HandleStep withTitle(final String title);
	}

	public static interface HandleStep {
		BuildStep withHandle(final String handle);
	}

	public static interface BuildStep {
		ShopifyCustomCollectionCreationRequest build();
	}


	public static InitialValueStep newBuilder() {
		return new Steps();
	}

	public ShopifyCustomCollection getRequest() {
		return request;
	}

	private ShopifyCustomCollectionCreationRequest(final ShopifyCustomCollection request) {
		this.request = request;
	}

	private static class Steps implements InitialValueStep, HandleStep, BuildStep {

		private final ShopifyCustomCollection request = new ShopifyCustomCollection();

		@Override
		public ShopifyCustomCollectionCreationRequest build() {
			return new ShopifyCustomCollectionCreationRequest(request);
		}

		@Override
		public HandleStep withTitle(final String title) {
			this.request.setTitle(title);
			return this;
		}

		@Override
		public BuildStep withHandle(final String handle) {
			this.request.setHandle(handle);
			return this;
		}
	}
}
