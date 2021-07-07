package com.shopify.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class ShopifyRecurringApplicationChargeCreationRequestTest {

	private String SOME_NAME = "ChannelApe";
	private String SOME_TERMS = "Price varies by integrations installed.";
	private BigDecimal SOME_PRICE = new BigDecimal("49.99");
	private BigDecimal SOME_CAPPED_AMOUNT = new BigDecimal("149.99");
	private String SOME_RETURN_URL = "https://app.channelape.com/channel/shopify/integrate";
	private int SOME_TRIAL_DAYS = 14;

	@Test
	public void givenSomeValuesWhenCreatingRequestThenReturnExpectedRequestValues() {
		final ShopifyRecurringApplicationChargeCreationRequest actualShopifyRecurringApplicationChargeCreationRequest = ShopifyRecurringApplicationChargeCreationRequest
				.newBuilder().withName(SOME_NAME).withTerms(SOME_TERMS).withPrice(SOME_PRICE)
				.withCappedAmount(SOME_CAPPED_AMOUNT).withReturnUrl(SOME_RETURN_URL).withTrialDays(SOME_TRIAL_DAYS)
				.withTest(true).build();
		final ShopifyRecurringApplicationCharge actualShopifyRecurringApplicationCharge = actualShopifyRecurringApplicationChargeCreationRequest
				.getRequest();
		assertEquals(SOME_NAME, actualShopifyRecurringApplicationCharge.getName());
		assertEquals(SOME_TERMS, actualShopifyRecurringApplicationCharge.getTerms());
		assertEquals(SOME_PRICE, actualShopifyRecurringApplicationCharge.getPrice());
		assertEquals(SOME_CAPPED_AMOUNT, actualShopifyRecurringApplicationCharge.getCappedAmount());
		assertEquals(SOME_RETURN_URL, actualShopifyRecurringApplicationCharge.getReturnUrl());
		assertEquals(SOME_TRIAL_DAYS, actualShopifyRecurringApplicationCharge.getTrialDays());
		assertTrue(actualShopifyRecurringApplicationCharge.isTest());
	}

	@Test
	public void givenSomeValuesAndNotTestWhenCreatingRequestThenReturnExpectedRequestValues() {
		final ShopifyRecurringApplicationChargeCreationRequest actualShopifyRecurringApplicationChargeCreationRequest = ShopifyRecurringApplicationChargeCreationRequest
				.newBuilder().withName(SOME_NAME).withTerms(SOME_TERMS).withPrice(SOME_PRICE)
				.withCappedAmount(SOME_CAPPED_AMOUNT).withReturnUrl(SOME_RETURN_URL).withTrialDays(SOME_TRIAL_DAYS)
				.withTest(false).build();
		final ShopifyRecurringApplicationCharge actualShopifyRecurringApplicationCharge = actualShopifyRecurringApplicationChargeCreationRequest
				.getRequest();
		assertEquals(SOME_NAME, actualShopifyRecurringApplicationCharge.getName());
		assertEquals(SOME_TERMS, actualShopifyRecurringApplicationCharge.getTerms());
		assertEquals(SOME_PRICE, actualShopifyRecurringApplicationCharge.getPrice());
		assertEquals(SOME_CAPPED_AMOUNT, actualShopifyRecurringApplicationCharge.getCappedAmount());
		assertEquals(SOME_RETURN_URL, actualShopifyRecurringApplicationCharge.getReturnUrl());
		assertEquals(SOME_TRIAL_DAYS, actualShopifyRecurringApplicationCharge.getTrialDays());
		assertFalse(actualShopifyRecurringApplicationCharge.isTest());
	}
}
