package com.shopify.model;

public class ShopifyCustomerMetafieldCreationRequest {

    private final String customerId;
    private final Metafield request;

    public static interface CustomerIdStep {
        NamespaceStep withCustomerId(final String customerId);
    }

    public static interface NamespaceStep {
        KeyStep withNamespace(final String namespace);
    }

    public static interface KeyStep {
        ValueStep withKey(final String key);
    }

    public static interface ValueStep {
        ValueTypeStep withValue(final String value);
    }

    public static interface ValueTypeStep {
        BuildStep withValueType(final MetafieldType valueType);
    }

    public static interface BuildStep {
        ShopifyCustomerMetafieldCreationRequest build();
    }

    public static CustomerIdStep newBuilder() {
        return new Steps();
    }

    public String getCustomerId() {
        return customerId;
    }

    public Metafield getRequest() {
        return request;
    }

    private ShopifyCustomerMetafieldCreationRequest(final Steps steps) {
        this.customerId = steps.customerId;
        this.request = steps.request;
    }

    private static class Steps implements CustomerIdStep, NamespaceStep, KeyStep, ValueStep, ValueTypeStep, BuildStep {

        private String customerId;
        private Metafield request = new Metafield();

        @Override
        public ShopifyCustomerMetafieldCreationRequest build() {
            return new ShopifyCustomerMetafieldCreationRequest(this);
        }

        @Override
        public BuildStep withValueType(final MetafieldType type) {
            this.request.setType(type);
            return this;
        }

        @Override
        public ValueTypeStep withValue(final String value) {
            this.request.setValue(value);
            return this;
        }

        @Override
        public ValueStep withKey(final String key) {
            this.request.setKey(key);
            return this;
        }

        @Override
        public KeyStep withNamespace(final String namespace) {
            this.request.setNamespace(namespace);
            return this;
        }

        @Override
        public NamespaceStep withCustomerId(final String customerId) {
            this.customerId = customerId;
            return this;
        }

    }
}
