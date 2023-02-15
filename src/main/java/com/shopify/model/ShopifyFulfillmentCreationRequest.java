package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import com.shopify.model.fulfillmentOrderApi.ShopifyFulfillmentOrder;

public class ShopifyFulfillmentCreationRequest {

	private final ShopifyFulfillment request;
	private List<ShopifyFulfillmentOrder> fulfillmentOrders = new LinkedList<>();

	public static interface OrderIdStep {
		TrackingCompanyStep withOrderId(final String orderId);
	}

	public static interface TrackingCompanyStep {
		TrackingNumberStep withTrackingCompany(final String trackingCompany);
	}

	public static interface TrackingNumberStep {
		NotifyCustomerStep withTrackingNumber(final String trackingNumber);
	}

	public static interface NotifyCustomerStep {
		LineItemsStep withNotifyCustomer(final boolean notifyCustomer);
	}

	public static interface LineItemsStep {
		LocationIdStep withLineItems(final List<ShopifyLineItem> lineItems);
	}

	public static interface LocationIdStep {
		TrackingUrlsStep withLocationId(final String locationId);
	}

	public static interface TrackingUrlsStep {
		OptionalsStep withTrackingUrls(final List<String> trackingUrls);
	}

	public interface OptionalsStep {

		public OptionalsStep withFulfillmentOrders(final List<ShopifyFulfillmentOrder> fulfillmentOrders);

		public ShopifyFulfillmentCreationRequest build();
	}

	public static interface BuildStep {
		ShopifyFulfillmentCreationRequest build();
	}

	public static OrderIdStep newBuilder() {
		return new Steps();
	}

	public ShopifyFulfillment getRequest() {
		return request;
	}

	private ShopifyFulfillmentCreationRequest(final ShopifyFulfillment request,
			final List<ShopifyFulfillmentOrder> fulfillmentOrders) {
		this.request = request;
		this.fulfillmentOrders = fulfillmentOrders;
	}

	public List<ShopifyFulfillmentOrder> getFulfillmentOrders() {
		return fulfillmentOrders;
	}

	private static class Steps implements OrderIdStep, TrackingCompanyStep, TrackingNumberStep, NotifyCustomerStep,
			LineItemsStep, LocationIdStep, TrackingUrlsStep, OptionalsStep, BuildStep {

		private final ShopifyFulfillment request = new ShopifyFulfillment();
		private List<ShopifyFulfillmentOrder> fulfillmentOrders = new LinkedList<>();

		@Override
		public ShopifyFulfillmentCreationRequest build() {
			return new ShopifyFulfillmentCreationRequest(request, fulfillmentOrders);
		}

		@Override
		public LocationIdStep withLineItems(final List<ShopifyLineItem> lineItems) {
			request.setLineItems(lineItems);
			return this;
		}

		@Override
		public NotifyCustomerStep withTrackingNumber(final String trackingNumber) {
			request.setTrackingNumber(trackingNumber);
			return this;
		}

		@Override
		public TrackingNumberStep withTrackingCompany(final String trackingCompany) {
			request.setTrackingCompany(trackingCompany);
			return this;
		}

		@Override
		public TrackingCompanyStep withOrderId(final String orderId) {
			request.setOrderId(orderId);
			return this;
		}

		@Override
		public LineItemsStep withNotifyCustomer(final boolean notifyCustomer) {
			request.setNotifyCustomer(notifyCustomer);
			return this;
		}

		@Override
		public TrackingUrlsStep withLocationId(final String locationId) {
			request.setLocationId(locationId);
			return this;
		}

		@Override
		public OptionalsStep withTrackingUrls(final List<String> trackingUrls) {
			request.setTrackingUrls(trackingUrls);
			return this;
		}

		@Override
		public OptionalsStep withFulfillmentOrders(final List<ShopifyFulfillmentOrder> fulfillmentOrders) {
			this.fulfillmentOrders = fulfillmentOrders;
			return this;
		}

	}

}
