package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.joda.time.DateTime;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyGetCustomersRequest {
    private int page;
    private int limit;
    private List<String> ids;
    private String sinceId;
    private DateTime createdAtMin;
    private DateTime createdAtMax;
    public static interface OptionalsStep {
        OptionalsStep withPage(int page);

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
        if (steps != null) {
            this.page = steps.page;
            this.limit = steps.limit;
            this.ids = steps.ids;
            this.sinceId = steps.sinceId;
            this.createdAtMin = steps.createdAtMin;
            this.createdAtMax = steps.createdAtMax;
        }
    }

    protected static class Steps implements OptionalsStep {
        private int page;
        private int limit;
        private List<String> ids;
        private String sinceId;
        private DateTime createdAtMin;
        private DateTime createdAtMax;

        @Override
        public ShopifyGetCustomersRequest build() {
            return new ShopifyGetCustomersRequest(this);
        }
        @Override
        public OptionalsStep withPage(int page) {
            this.page = page;
            return this;
        }
        @Override
        public OptionalsStep withLimit(int limit) {
            this.limit = limit;
            return this;
        }
        @Override
        public OptionalsStep withIds(List<String> ids) {
            this.ids = ids;
            return this;
        }
        @Override
        public OptionalsStep withSinceId(String sinceId) {
            this.sinceId = sinceId;
            return this;
        }
        @Override
        public OptionalsStep withCreatedAtMin(DateTime createdAtMin) {
            this.createdAtMin = createdAtMin;
            return this;
        }
        @Override
        public OptionalsStep withCreatedAtMax(DateTime createdAtMax) {
            this.createdAtMax = createdAtMax;
            return this;
        }
    }
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getSinceId() {
        return sinceId;
    }

    public void setSinceId(String sinceId) {
        this.sinceId = sinceId;
    }

    public DateTime getCreatedAtMin() {
        return createdAtMin;
    }

    public void setCreatedAtMin(DateTime createdAtMin) {
        this.createdAtMin = createdAtMin;
    }

    public DateTime getCreatedAtMax() {
        return createdAtMax;
    }

    public void setCreatedAtMax(DateTime createdAtMax) {
        this.createdAtMax = createdAtMax;
    }
}
