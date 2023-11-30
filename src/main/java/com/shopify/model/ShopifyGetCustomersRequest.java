package com.shopify.model;

import java.util.List;

import org.joda.time.DateTime;

import lombok.Data;

@Data
public class ShopifyGetCustomersRequest {
	private int limit;
	private List<String> ids;
	private String sinceId;
	private String pageInfo;
	private DateTime createdAtMin;
	private DateTime createdAtMax;

	public static interface OptionalsStep {

		OptionalsStep withPageInfo(final String pageInfo);

		OptionalsStep withLimit(int limit);

		OptionalsStep withIds(List<String> ids);

		OptionalsStep withSinceId(String sinceId);

		OptionalsStep withCreatedAtMin(DateTime createdAtMin);

		OptionalsStep withCreatedAtMax(DateTime createdAtMax);

		ShopifyGetCustomersRequest build();
	}

	public static OptionalsStep newBuilder() {
		return new Steps();
	}

	protected ShopifyGetCustomersRequest(final Steps steps) {
		this.limit = steps.limit;
		this.ids = steps.ids;
		this.sinceId = steps.sinceId;
		this.createdAtMin = steps.createdAtMin;
		this.createdAtMax = steps.createdAtMax;
		this.pageInfo = steps.pageInfo;
	}

	protected static class Steps implements OptionalsStep {
		private int limit;
		private String pageInfo;
		private List<String> ids;
		private String sinceId;
		private DateTime createdAtMin;
		private DateTime createdAtMax;

		@Override
		public ShopifyGetCustomersRequest build() {
			return new ShopifyGetCustomersRequest(this);
		}

		@Override
		public OptionalsStep withLimit(final int limit) {
			this.limit = limit;
			return this;
		}

		@Override
		public OptionalsStep withIds(final List<String> ids) {
			this.ids = ids;
			return this;
		}

		@Override
		public OptionalsStep withSinceId(final String sinceId) {
			this.sinceId = sinceId;
			return this;
		}

		@Override
		public OptionalsStep withCreatedAtMin(final DateTime createdAtMin) {
			this.createdAtMin = createdAtMin;
			return this;
		}

		@Override
		public OptionalsStep withCreatedAtMax(final DateTime createdAtMax) {
			this.createdAtMax = createdAtMax;
			return this;
		}

		@Override
		public OptionalsStep withPageInfo(final String pageInfo) {
			this.pageInfo = pageInfo;
			return this;
		}
	}
}
