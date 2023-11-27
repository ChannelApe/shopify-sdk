package com.shopify.model;

import java.util.List;

import org.joda.time.DateTime;

public class ShopifyOrderCreationRequest {

	private final ShopifyOrder request;

	public static interface ProcessedAtStep {
		NameStep withProcessedAt(final DateTime processedAt);
	}

	public static interface NameStep {
		CustomerStep withName(final String name);
	}

	public static interface CustomerStep {
		LineItemsStep withCustomer(final ShopifyCustomer customer);

		LineItemsStep noCustomer();
	}

	public static interface LineItemsStep {
		ShippingAddressStep withLineItems(final List<ShopifyLineItem> lineItems);
	}

	public static interface ShippingAddressStep {
		BillingAddressStep withShippingAddress(final ShopifyAddress shippingAddress);
	}

	public static interface BillingAddressStep {
		MetafieldsStep withBillingAddress(final ShopifyAddress billingAddress);
	}

	public static interface MetafieldsStep {

		ShippingLinesStep withMetafields(List<Metafield> metafields);
	}

	public static interface ShippingLinesStep {
		OptionalsStep withShippingLines(List<ShopifyShippingLine> shippingLines);
	}

	public static interface OptionalsStep {
		OptionalsStep withNoteAttributes(final List<ShopifyAttribute> noteAttributes);

		OptionalsStep withNote(final String note);

		OptionalsStep withFinancialStatus(final String financialStatus);

		ShopifyOrderCreationRequest build();
	}

	public static ProcessedAtStep newBuilder() {
		return new Steps();
	}

	public ShopifyOrder getRequest() {
		return request;
	}

	private ShopifyOrderCreationRequest(final ShopifyOrder request) {
		this.request = request;
	}

	private static class Steps implements ProcessedAtStep, NameStep, CustomerStep, MetafieldsStep, LineItemsStep,
			ShippingAddressStep, BillingAddressStep, ShippingLinesStep, OptionalsStep {

		private final ShopifyOrder request = new ShopifyOrder();

		@Override
		public ShopifyOrderCreationRequest build() {
			return new ShopifyOrderCreationRequest(request);
		}

		@Override
		public ShippingAddressStep withLineItems(final List<ShopifyLineItem> lineItems) {
			request.setLineItems(lineItems);
			return this;
		}

		@Override
		public LineItemsStep withCustomer(final ShopifyCustomer customer) {
			request.setCustomer(customer);
			return this;
		}

		@Override
		public LineItemsStep noCustomer() {
			request.setCustomer(null);
			return this;
		}

		@Override
		public NameStep withProcessedAt(final DateTime processedAt) {
			request.setProcessedAt(processedAt);
			return this;
		}

		@Override
		public MetafieldsStep withBillingAddress(final ShopifyAddress shippingAddress) {
			request.setBillingAddress(shippingAddress);
			return this;
		}

		@Override
		public BillingAddressStep withShippingAddress(final ShopifyAddress billingAddress) {
			request.setShippingAddress(billingAddress);
			return this;
		}

		@Override
		public ShippingLinesStep withMetafields(final List<Metafield> metafields) {
			request.setMetafields(metafields);
			return this;
		}

		@Override
		public CustomerStep withName(final String name) {
			request.setName(name);
			return this;
		}

		@Override
		public OptionalsStep withShippingLines(final List<ShopifyShippingLine> shippingLines) {
			request.setShippingLines(shippingLines);
			return this;
		}

		@Override
		public OptionalsStep withNoteAttributes(final List<ShopifyAttribute> noteAttributes) {
			request.setNoteAttributes(noteAttributes);
			return this;
		}

		@Override
		public OptionalsStep withNote(final String note) {
			request.setNote(note);
			return this;
		}

		@Override
		public OptionalsStep withFinancialStatus(final String financialStatus) {
			request.setFinancialStatus(financialStatus);
			return this;
		}

	}

}
