package com.shopify.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

public class ShopifyOrderTest {

	private static final String SOME_USER_ID = "123123";
	private static final int SOME_TOTAL_WEIGHT = 123;
	private static final BigDecimal SOME_TOTAL_TAX = new BigDecimal(42.03);
	private static final BigDecimal SOME_TOTAL_PRICE = new BigDecimal(12.00);
	private static final BigDecimal SOME_TOTA_LINE_ITEMS_PRICE = new BigDecimal(41.00);
	private static final BigDecimal SOME_TOTAL_DISCOUNTS = new BigDecimal(42.00);
	private static final String SOME_TOKEN = "token";
	private static final List<ShopifyTaxLine> SOME_TAX_LINES = Collections.emptyList();
	private static final String SOME_TAGS = "some Tags";
	private static final String HTTP_REFERRED_SITE = "http://referred-site";
	private static final String SOME_PROCESSING_METHOD = "SomeProcessingMethod";
	private static final String SOME_STATUS = "OPEN";
	private static final String SOMEORDER_NUMBER = "someorderNumber";
	private static final List<ShopifyAttribute> SOME_NOTE_ATTRIBUTES = Collections.emptyList();
	private static final String SOME_NOTES = "SomeNOtes";
	private static final String SOME_ORDER_NAME = "some order name";
	private static final String SOME_LOCATION_ID = "123";
	private static final List<ShopifyLineItem> SOME_LINE_ITEMS = Collections.emptyList();
	private static final String SOME_LANDING_SITE = "some landing site";
	private static final String SOME_ID = "someId";
	private static final String SOME_FULFILLMENT_STATUS = "Fulfilled";
	private static final List<ShopifyFulfillment> SOME_FULFILLMENTS = Collections.emptyList();
	private static final String SOME_FINANCIAL_STATUS = "some financail status";
	private static final String SOME_EMAIL = "rkazokas@channelape.com";
	private static final Currency SOME_CURRENCY = Currency.getInstance("USD");
	private static final DateTime SOME_DATE = new DateTime();
	private static final String SOME_CART_TOKEN = "someCartToken";
	private static final String SOME_CANCELLED_REASON = "SomeCancelledReason";
	private static final ShopifyAddress SOME_BILLING_ADDRESS = new ShopifyAddress();
	private static final String SOME_BROWSER_IP = "SomeBrowserIP";

	@Test
	public void givenSomeValuesWhenSettingOrderValuesWhenCreatingShopifyOrderThenExpectCorrectValues()
			throws Exception {
		final ShopifyOrder shopifyOrder = new ShopifyOrder();
		shopifyOrder.setBrowserIp(SOME_BROWSER_IP);
		shopifyOrder.setBillingAddress(SOME_BILLING_ADDRESS);
		shopifyOrder.setBuyerAcceptsMarketing(true);
		shopifyOrder.setCancelledAt(SOME_DATE);
		shopifyOrder.setCancelReason(SOME_CANCELLED_REASON);
		shopifyOrder.setCartToken(SOME_CART_TOKEN);
		shopifyOrder.setClosedAt(SOME_DATE);
		shopifyOrder.setCreatedAt(SOME_DATE);
		shopifyOrder.setCurrency(SOME_CURRENCY);
		shopifyOrder.setEmail(SOME_EMAIL);
		shopifyOrder.setFinancialStatus(SOME_FINANCIAL_STATUS);
		shopifyOrder.setFulfillments(SOME_FULFILLMENTS);
		shopifyOrder.setFulfillmentStatus(SOME_FULFILLMENT_STATUS);
		shopifyOrder.setId(SOME_ID);
		shopifyOrder.setLandingSite(SOME_LANDING_SITE);
		shopifyOrder.setLineItems(SOME_LINE_ITEMS);
		shopifyOrder.setLocationId(SOME_LOCATION_ID);
		shopifyOrder.setName(SOME_ORDER_NAME);
		shopifyOrder.setNote(SOME_NOTES);
		shopifyOrder.setNoteAttributes(SOME_NOTE_ATTRIBUTES);
		shopifyOrder.setOrderNumber(SOMEORDER_NUMBER);
		shopifyOrder.setOrderStatusUrl(SOME_STATUS);
		shopifyOrder.setProcessedAt(SOME_DATE);
		shopifyOrder.setProcessingMethod(SOME_PROCESSING_METHOD);
		shopifyOrder.setReferringSite(HTTP_REFERRED_SITE);
		shopifyOrder.setTags(SOME_TAGS);
		shopifyOrder.setTaxesIncluded(true);
		shopifyOrder.setTaxLines(SOME_TAX_LINES);
		shopifyOrder.setToken(SOME_TOKEN);
		shopifyOrder.setTotalDiscounts(SOME_TOTAL_DISCOUNTS);
		shopifyOrder.setTotaLineItemsPrice(SOME_TOTA_LINE_ITEMS_PRICE);
		shopifyOrder.setTotalPrice(SOME_TOTAL_PRICE);
		shopifyOrder.setTotalTax(SOME_TOTAL_TAX);
		shopifyOrder.setTotalWeight(SOME_TOTAL_WEIGHT);
		shopifyOrder.setUpdatedAt(SOME_DATE);
		shopifyOrder.setUserId(SOME_USER_ID);

		assertEquals(SOME_BROWSER_IP, shopifyOrder.getBrowserIp());
		assertEquals(SOME_BILLING_ADDRESS, shopifyOrder.getBillingAddress());
		assertEquals(true, shopifyOrder.isBuyerAcceptsMarketing());
		assertEquals(SOME_DATE, shopifyOrder.getCancelledAt());
		assertEquals(SOME_CANCELLED_REASON, shopifyOrder.getCancelReason());
		assertEquals(SOME_CART_TOKEN, shopifyOrder.getCartToken());
		assertEquals(SOME_DATE, shopifyOrder.getClosedAt());
		assertEquals(SOME_DATE, shopifyOrder.getCreatedAt());
		assertEquals(SOME_CURRENCY, shopifyOrder.getCurrency());
		assertEquals(SOME_EMAIL, shopifyOrder.getEmail());
		assertEquals(SOME_FINANCIAL_STATUS, shopifyOrder.getFinancialStatus());
		assertEquals(SOME_FULFILLMENTS, shopifyOrder.getFulfillments());
		assertEquals(SOME_FULFILLMENT_STATUS, shopifyOrder.getFulfillmentStatus());
		assertEquals(SOME_ID, shopifyOrder.getId());
		assertEquals(SOME_LANDING_SITE, shopifyOrder.getLandingSite());
		assertEquals(SOME_LINE_ITEMS, shopifyOrder.getLineItems());
		assertEquals(SOME_LOCATION_ID, shopifyOrder.getLocationId());
		assertEquals(SOME_ORDER_NAME, shopifyOrder.getName());
		assertEquals(SOME_NOTES, shopifyOrder.getNote());
		assertEquals(SOME_NOTE_ATTRIBUTES, shopifyOrder.getNoteAttributes());
		assertEquals(SOMEORDER_NUMBER, shopifyOrder.getOrderNumber());
		assertEquals(SOME_STATUS, shopifyOrder.getOrderStatusUrl());
		assertEquals(SOME_DATE, shopifyOrder.getProcessedAt());
		assertEquals(HTTP_REFERRED_SITE, shopifyOrder.getReferringSite());
		assertEquals(SOME_PROCESSING_METHOD, shopifyOrder.getProcessingMethod());
		assertEquals(SOME_TAGS, shopifyOrder.getTags());
		assertEquals(true, shopifyOrder.isTaxesIncluded());
		assertEquals(SOME_TAX_LINES, shopifyOrder.getTaxLines());
		assertEquals(SOME_TOKEN, shopifyOrder.getToken());
		assertEquals(SOME_TOTAL_DISCOUNTS, shopifyOrder.getTotalDiscounts());
		assertEquals(SOME_TOTA_LINE_ITEMS_PRICE, shopifyOrder.getTotaLineItemsPrice());
		assertEquals(SOME_TOTAL_PRICE, shopifyOrder.getTotalPrice());
		assertEquals(SOME_TOTAL_TAX, shopifyOrder.getTotalTax());
		assertEquals(SOME_TOTAL_WEIGHT, shopifyOrder.getTotalWeight());
		assertEquals(SOME_DATE, shopifyOrder.getUpdatedAt());
		assertEquals(SOME_USER_ID, shopifyOrder.getUserId());

	}

}
