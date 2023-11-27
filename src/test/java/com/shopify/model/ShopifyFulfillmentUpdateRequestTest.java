package com.shopify.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

public class ShopifyFulfillmentUpdateRequestTest {

	private static final List<String> SOME_TRACKING_URLS = Arrays.asList("123.com");
	private static final String SOME_LOCATION_ID = "93284234";
	private static final String SOME_TRACKING_COMPANY = "USPS";
	private static final String SOME_TRACKING_NUMBER = "404042AD";
	private static final boolean SOME_NOTIFY_CUSTOMER = true;
	private static final List<ShopifyLineItem> SOME_LINE_ITEMS = Arrays.asList(new ShopifyLineItem(),
			new ShopifyLineItem());

	@Test
	public void givenSomeShopifyFulfillmentAndSomeTrackingNumberAndSomeTrackingCompanyAndSomeLineItemsWhenUpdatingFulfillmentUpdateRequestThenReturnCorrectRequest() {
		final ShopifyFulfillment currentShopifyFulfillment = new ShopifyFulfillment();
		currentShopifyFulfillment.setId(UUID.randomUUID().toString());
		currentShopifyFulfillment.setOrderId(UUID.randomUUID().toString());
		currentShopifyFulfillment.setTrackingCompany("FedEx");
		currentShopifyFulfillment.setTrackingNumber("404004104140DDF");
		currentShopifyFulfillment.setLineItems(Arrays.asList(new ShopifyLineItem()));

		final ShopifyFulfillmentUpdateRequest shopifyFulfillmentUpdateRequest = ShopifyFulfillmentUpdateRequest
				.newBuilder().withCurrentShopifyFulfillment(currentShopifyFulfillment)
				.withTrackingCompany(SOME_TRACKING_COMPANY).withTrackingNumber(SOME_TRACKING_NUMBER)
				.withNotifyCustomer(SOME_NOTIFY_CUSTOMER).withLineItems(SOME_LINE_ITEMS)
				.withLocationId(SOME_LOCATION_ID).withTrackingUrls(SOME_TRACKING_URLS).build();

		final ShopifyFulfillment actualRequest = shopifyFulfillmentUpdateRequest.getRequest();

		assertEquals(currentShopifyFulfillment.getId(), actualRequest.getId());
		assertEquals(currentShopifyFulfillment.getOrderId(), actualRequest.getOrderId());
		assertEquals(SOME_TRACKING_NUMBER, actualRequest.getTrackingNumber());
		assertEquals(SOME_TRACKING_COMPANY, actualRequest.getTrackingCompany());
		assertEquals(SOME_NOTIFY_CUSTOMER, actualRequest.isNotifyCustomer());
		assertSame(SOME_LINE_ITEMS, actualRequest.getLineItems());
		assertSame(SOME_LOCATION_ID, actualRequest.getLocationId());
		assertSame(SOME_TRACKING_URLS, actualRequest.getTrackingUrls());
	}

}
