package com.shopify.model;

import java.math.BigDecimal;

public class ShopifyGiftCardCreationRequest {

	private final ShopifyGiftCard request;

	public static interface InitialValueStep {
		CodeStep withInitialValue(final BigDecimal initialValue);
	}

	public static interface CodeStep {
		CurrencyStep withCode(final String code);

		CurrencyStep withGeneratedCode();
	}

	public static interface CurrencyStep {
		BuildStep withCurrency(final String currency);
	}

	public static interface BuildStep {
		ShopifyGiftCardCreationRequest build();
	}

	public static InitialValueStep newBuilder() {
		return new Steps();
	}

	public ShopifyGiftCard getRequest() {
		return request;
	}

	private ShopifyGiftCardCreationRequest(final ShopifyGiftCard request) {
		this.request = request;
	}

	private static class Steps implements InitialValueStep, CodeStep, CurrencyStep, BuildStep {

		private final ShopifyGiftCard request = new ShopifyGiftCard();

		@Override
		public ShopifyGiftCardCreationRequest build() {
			return new ShopifyGiftCardCreationRequest(request);
		}

		@Override
		public CurrencyStep withCode(final String code) {
			this.request.setCode(code);
			return this;
		}

		@Override
		public CurrencyStep withGeneratedCode() {
			return this;
		}

		@Override
		public CodeStep withInitialValue(final BigDecimal initialValue) {
			this.request.setInitialValue(initialValue);
			return this;
		}

		@Override
		public BuildStep withCurrency(final String currency) {
			this.request.setCurrency(currency);
			return this;
		}
	}
}
