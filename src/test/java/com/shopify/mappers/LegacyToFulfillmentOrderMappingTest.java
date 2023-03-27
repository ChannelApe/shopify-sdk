package com.shopify.mappers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import com.shopify.exceptions.ShopifyEmptyLineItemsException;
import com.shopify.model.ShopifyFulfillment;
import com.shopify.model.ShopifyFulfillmentCreationRequest;
import com.shopify.model.ShopifyFulfillmentOrder;
import com.shopify.model.ShopifyFulfillmentOrderLineItem;
import com.shopify.model.ShopifyFulfillmentPayloadRoot;
import com.shopify.model.ShopifyLineItem;
import com.shopify.model.ShopifyUpdateFulfillmentPayloadRoot;

public class LegacyToFulfillmentOrderMappingTest {
	private static final DateTime SOME_DATE_TIME = new DateTime();

	private ShopifyFulfillment buildShopifyFulfillment(final ShopifyLineItem lineItem) {
		final ShopifyFulfillment currentFulfillment = new ShopifyFulfillment();
		currentFulfillment.setId("4567");
		currentFulfillment.setOrderId("1234");
		currentFulfillment.setCreatedAt(SOME_DATE_TIME);
		currentFulfillment.setLineItems(Arrays.asList(lineItem));
		currentFulfillment.setLocationId("1");
		currentFulfillment.setNotifyCustomer(true);
		currentFulfillment.setStatus("cancelled");
		currentFulfillment.setTrackingCompany("USPS");
		currentFulfillment.setTrackingNumber("12341234");
		currentFulfillment.setUpdatedAt(SOME_DATE_TIME);
		return currentFulfillment;
	}

	@Test
	public void whenMappingToFulfillmentOrderWithAnArrayOfTrackingUrlsTheTrackingUrlShouldMatchTheArraysFirstItem()
			throws Exception {
		final String lineItemId = "987";
		final String fulfillmentOrderId = "1234";

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId(lineItemId);
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		List<ShopifyFulfillmentOrderLineItem> fulfillmentOrderLineItems = new LinkedList<>();
		ShopifyFulfillmentOrderLineItem fulfillmentOrderLineItem = new ShopifyFulfillmentOrderLineItem();
		fulfillmentOrderLineItem.setQuantity(1);
		fulfillmentOrderLineItem.setLineItemId(lineItemId);
		fulfillmentOrderLineItem.setFulfillableQuantity(1);
		fulfillmentOrderLineItem.setFulfillmentOrderId(fulfillmentOrderId);
		fulfillmentOrderLineItems.add(fulfillmentOrderLineItem);

		final List<String> supportedActions = new LinkedList<>();
		supportedActions.add("move");
		supportedActions.add("create_fulfillment");

		final ShopifyFulfillmentOrder fulfillmentOrder = new ShopifyFulfillmentOrder();
		fulfillmentOrder.setId(fulfillmentOrderId);
		fulfillmentOrder.setLineItems(fulfillmentOrderLineItems);
		fulfillmentOrder.setSupportedActions(supportedActions);
		fulfillmentOrder.setAssignedLocationId("5678");
		final List<ShopifyFulfillmentOrder> fulfillmentOrders = new LinkedList<>();
		fulfillmentOrders.add(fulfillmentOrder);

		final ShopifyFulfillmentCreationRequest request = ShopifyFulfillmentCreationRequest.newBuilder()
				.withOrderId("1234").withTrackingCompany("USPS").withTrackingNumber("12341234").withNotifyCustomer(true)
				.withLineItems(Arrays.asList(lineItem)).withLocationId("1").withTrackingUrls(Arrays.asList()).build();

		request.getRequest().setTrackingUrl("tracking_url");
		request.getRequest().setTrackingUrls(Arrays.asList("tracking_url1", "tracking_url2"));
		final ShopifyFulfillmentPayloadRoot payload = LegacyToFulfillmentOrderMapping
				.toShopifyFulfillmentPayloadRoot(request.getRequest(), fulfillmentOrders);

		assertEquals(payload.getFulfillment().getTrackingInfo().getUrl(), "tracking_url1");
	}

	@Test
	public void whenMappingToFulfillmentOrderWithASingleTrackingUrlTheTrackingUrlShouldMatchIt() throws Exception {
		final String lineItemId = "987";
		final String fulfillmentOrderId = "1234";

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId(lineItemId);
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		List<ShopifyFulfillmentOrderLineItem> fulfillmentOrderLineItems = new LinkedList<>();
		ShopifyFulfillmentOrderLineItem fulfillmentOrderLineItem = new ShopifyFulfillmentOrderLineItem();
		fulfillmentOrderLineItem.setQuantity(1);
		fulfillmentOrderLineItem.setLineItemId(lineItemId);
		fulfillmentOrderLineItem.setFulfillableQuantity(1);
		fulfillmentOrderLineItem.setFulfillmentOrderId(fulfillmentOrderId);
		fulfillmentOrderLineItems.add(fulfillmentOrderLineItem);

		final List<String> supportedActions = new LinkedList<>();
		supportedActions.add("move");
		supportedActions.add("create_fulfillment");

		final ShopifyFulfillmentOrder fulfillmentOrder = new ShopifyFulfillmentOrder();
		fulfillmentOrder.setId(fulfillmentOrderId);
		fulfillmentOrder.setLineItems(fulfillmentOrderLineItems);
		fulfillmentOrder.setSupportedActions(supportedActions);
		fulfillmentOrder.setAssignedLocationId("5678");
		final List<ShopifyFulfillmentOrder> fulfillmentOrders = new LinkedList<>();
		fulfillmentOrders.add(fulfillmentOrder);

		final ShopifyFulfillmentCreationRequest request = ShopifyFulfillmentCreationRequest.newBuilder()
				.withOrderId("1234").withTrackingCompany("USPS").withTrackingNumber("12341234").withNotifyCustomer(true)
				.withLineItems(Arrays.asList(lineItem)).withLocationId("1").withTrackingUrls(Arrays.asList()).build();

		request.getRequest().setTrackingUrl("tracking_url");
		final ShopifyFulfillmentPayloadRoot payload = LegacyToFulfillmentOrderMapping
				.toShopifyFulfillmentPayloadRoot(request.getRequest(), fulfillmentOrders);

		assertEquals(payload.getFulfillment().getTrackingInfo().getUrl(), "tracking_url");
	}

	@Test(expected = ShopifyEmptyLineItemsException.class)
	public void whenMappingToFulfillmentOrderWithoutMatchingFulfillmentOrder() throws Exception {
		final String lineItemId = "987";
		final String fulfillmentOrderId = "1234";

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId(lineItemId);
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		List<ShopifyFulfillmentOrderLineItem> fulfillmentOrderLineItems = new LinkedList<>();
		ShopifyFulfillmentOrderLineItem fulfillmentOrderLineItem = new ShopifyFulfillmentOrderLineItem();
		fulfillmentOrderLineItem.setQuantity(1);
		fulfillmentOrderLineItem.setLineItemId("002");
		fulfillmentOrderLineItem.setFulfillableQuantity(1);
		fulfillmentOrderLineItem.setFulfillmentOrderId(fulfillmentOrderId);
		fulfillmentOrderLineItems.add(fulfillmentOrderLineItem);

		final List<String> supportedActions = new LinkedList<>();
		supportedActions.add("move");
		supportedActions.add("create_fulfillment");

		final ShopifyFulfillmentOrder fulfillmentOrder = new ShopifyFulfillmentOrder();
		fulfillmentOrder.setId(fulfillmentOrderId);
		fulfillmentOrder.setLineItems(fulfillmentOrderLineItems);
		fulfillmentOrder.setSupportedActions(supportedActions);
		fulfillmentOrder.setAssignedLocationId("5678");
		final List<ShopifyFulfillmentOrder> fulfillmentOrders = new LinkedList<>();
		fulfillmentOrders.add(fulfillmentOrder);

		final ShopifyFulfillmentCreationRequest request = ShopifyFulfillmentCreationRequest.newBuilder()
				.withOrderId("1234").withTrackingCompany("USPS").withTrackingNumber("12341234").withNotifyCustomer(true)
				.withLineItems(Arrays.asList(lineItem)).withLocationId("1").withTrackingUrls(Arrays.asList()).build();

		request.getRequest().setTrackingUrl("tracking_url");
		final ShopifyFulfillmentPayloadRoot payload = LegacyToFulfillmentOrderMapping
				.toShopifyFulfillmentPayloadRoot(request.getRequest(), fulfillmentOrders);

		assertEquals(payload.getFulfillment().getTrackingInfo().getUrl(), "tracking_url");
	}

	@Test
	public void whenUpdatingAFulfillmentOrderWithAnArrayOfTrackingUrlsTheTrackingUrlShouldMatchTheArraysFirstItem()
			throws Exception {
		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId("some_line_item_id");
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		final ShopifyFulfillment currentFulfillment = buildShopifyFulfillment(lineItem);

		currentFulfillment.setTrackingUrl("tracking_url");
		currentFulfillment.setTrackingUrls(Arrays.asList("tracking_url1", "tracking_url2"));

		final ShopifyUpdateFulfillmentPayloadRoot payload = LegacyToFulfillmentOrderMapping
				.toUpdateShopifyFulfillmentPayloadRoot(currentFulfillment);

		assertEquals(payload.getFulfillment().getTrackingInfo().getUrl(), "tracking_url1");
	}

	@Test
	public void whenUpdatingAFulfillmentOrderWithASingleTrackingUrlTheTrackingUrlShouldMatchIt() throws Exception {
		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId("some_line_item_id");
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		final ShopifyFulfillment currentFulfillment = buildShopifyFulfillment(lineItem);

		currentFulfillment.setTrackingUrl("tracking_url");
		currentFulfillment.setTrackingUrls(Arrays.asList());

		final ShopifyUpdateFulfillmentPayloadRoot payload = LegacyToFulfillmentOrderMapping
				.toUpdateShopifyFulfillmentPayloadRoot(currentFulfillment);

		assertEquals(payload.getFulfillment().getTrackingInfo().getUrl(), "tracking_url");
	}
}
