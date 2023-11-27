package com.shopify.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

public class ShopifyFulfillmentCreationRequestTest {

	private static final List<String> SOME_TRACKING_URLS = Arrays.asList("https://ups.com/123", "https://ups.com/456");
	private static final String SOME_LOCATION_ID = "987483724";
	private static final String SOME_ORDER_ID = UUID.randomUUID().toString();
	private static final String SOME_TRACKING_COMPANY = "USPS";
	private static final String SOME_TRACKING_NUMBER = "404042AD";
	private static final boolean SOME_NOTIFY_CUSTOMER = true;
	private static final List<ShopifyLineItem> SOME_LINE_ITEMS = Arrays.asList(new ShopifyLineItem(),
			new ShopifyLineItem());

	@Test
	public void givenSomeOrderIdAndSomeTrackingNumberAndSomeTrackingCompanyAndSomeLineItemsWhenCreatingFulfillmentCreationRequestThenReturnCorrectRequest() {
		final ShopifyFulfillmentCreationRequest shopifyFulfillmentCreationRequest = ShopifyFulfillmentCreationRequest
				.newBuilder().withOrderId(SOME_ORDER_ID).withTrackingCompany(SOME_TRACKING_COMPANY)
				.withTrackingNumber(SOME_TRACKING_NUMBER).withNotifyCustomer(SOME_NOTIFY_CUSTOMER)
				.withLineItems(SOME_LINE_ITEMS).withLocationId(SOME_LOCATION_ID).withTrackingUrls(SOME_TRACKING_URLS)
				.build();

		final ShopifyFulfillment actualRequest = shopifyFulfillmentCreationRequest.getRequest();

		assertEquals(SOME_ORDER_ID, actualRequest.getOrderId());
		assertEquals(SOME_TRACKING_NUMBER, actualRequest.getTrackingNumber());
		assertEquals(SOME_TRACKING_COMPANY, actualRequest.getTrackingCompany());
		assertEquals(SOME_NOTIFY_CUSTOMER, actualRequest.isNotifyCustomer());
		assertSame(SOME_LINE_ITEMS, actualRequest.getLineItems());
		assertSame(SOME_LOCATION_ID, actualRequest.getLocationId());
		assertSame(SOME_TRACKING_URLS, actualRequest.getTrackingUrls());
	}

}
