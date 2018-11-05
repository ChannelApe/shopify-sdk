package com.shopify.model;

import java.math.BigDecimal;

public class ShopifyRecurringApplicationChargeCreationRequest {

	private final ShopifyRecurringApplicationCharge request;

	public static interface NameStep {
		public TermsStep withName(final String name);
	}

	public static interface TermsStep {
		public PriceStep withTerms(final String terms);
	}

	public static interface PriceStep {
		public CappedAmountStep withPrice(final BigDecimal price);
	}

	public static interface CappedAmountStep {
		public ReturnUrlStep withCappedAmount(final BigDecimal cappedAmount);
	}

	public static interface ReturnUrlStep {
		public TrialDaysStep withReturnUrl(final String returnUrl);
	}

	public static interface TrialDaysStep {
		public TestStep withTrialDays(final int trialDays);
	}

	public static interface TestStep {
		public BuildStep withTest(final boolean test);
	}

	public static interface BuildStep {
		public ShopifyRecurringApplicationChargeCreationRequest build();
	}

	public static NameStep newBuilder() {
		return new Steps();
	}

	public ShopifyRecurringApplicationCharge getRequest() {
		return request;
	}

	private ShopifyRecurringApplicationChargeCreationRequest(
			final ShopifyRecurringApplicationCharge shopifyRecurringApplicationCharge) {
		this.request = shopifyRecurringApplicationCharge;
	}

	private static class Steps
			implements NameStep, TermsStep, PriceStep, CappedAmountStep, ReturnUrlStep, TrialDaysStep, TestStep, BuildStep {

		private ShopifyRecurringApplicationCharge shopifyRecurringApplicationCharge = new ShopifyRecurringApplicationCharge();

		@Override
		public ShopifyRecurringApplicationChargeCreationRequest build() {
			return new ShopifyRecurringApplicationChargeCreationRequest(shopifyRecurringApplicationCharge);
		}

		@Override
		public TestStep withTrialDays(int trialDays) {
			shopifyRecurringApplicationCharge.setTrialDays(trialDays);
			return this;
		}

		@Override
		public TrialDaysStep withReturnUrl(String returnUrl) {
			shopifyRecurringApplicationCharge.setReturnUrl(returnUrl);
			return this;
		}

		@Override
		public ReturnUrlStep withCappedAmount(BigDecimal cappedAmount) {
			shopifyRecurringApplicationCharge.setCappedAmount(cappedAmount);
			return this;
		}

		@Override
		public CappedAmountStep withPrice(BigDecimal price) {
			shopifyRecurringApplicationCharge.setPrice(price);
			return this;
		}

		@Override
		public TermsStep withName(String name) {
			shopifyRecurringApplicationCharge.setName(name);
			return this;
		}

		@Override
		public PriceStep withTerms(String terms) {
			shopifyRecurringApplicationCharge.setTerms(terms);
			return this;
		}

		@Override
		public BuildStep withTest(boolean test) {
			shopifyRecurringApplicationCharge.setTest(test);
			return this;
		}

	}
}
