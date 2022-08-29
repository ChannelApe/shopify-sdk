package com.shopify.model;

public class ShopifyCustomerMetafieldUpdateRequest {

    private final String customerId;
    private final String metafieldId;
    private final Metafield request;

    public static interface CustomerIdStep {
        MetafieldIdStep withCustomerId(final String customerId);
    }

    public static interface MetafieldIdStep {
        ValueStep withMetafieldId(final String metafieldId);
    }


    public static interface ValueStep {
        ValueTypeStep withValue(final String value);
    }

    public static interface ValueTypeStep {
        BuildStep withValueType(final MetafieldType valueType);
    }

    public static interface BuildStep {
        ShopifyCustomerMetafieldUpdateRequest build();
    }

    public static CustomerIdStep newBuilder() {
        return new Steps();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getMetafieldId() {
        return metafieldId;
    }

    public Metafield getRequest() {
        return request;
    }

    private ShopifyCustomerMetafieldUpdateRequest(final Steps steps) {
        this.metafieldId = steps.metafieldId;
        this.customerId = steps.customerId;
        this.request = steps.request;
    }

    private static class Steps implements CustomerIdStep, MetafieldIdStep, ValueStep, ValueTypeStep, BuildStep {

        private String customerId;

        private String metafieldId;
        private Metafield request = new Metafield();

        @Override
        public ShopifyCustomerMetafieldUpdateRequest build() {
            return new ShopifyCustomerMetafieldUpdateRequest(this);
        }

        @Override
        public MetafieldIdStep withCustomerId(final String customerId) {
            this.customerId = customerId;
            return this;
        }


        @Override
        public ValueStep withMetafieldId(String metafieldId) {
            this.metafieldId = metafieldId;
            return this;
        }

        @Override
        public ValueTypeStep withValue(final String value) {
            this.request.setValue(value);
            return this;
        }

        @Override
        public BuildStep withValueType(final MetafieldType type) {
            this.request.setType(type);
            return this;
        }
    }
}
