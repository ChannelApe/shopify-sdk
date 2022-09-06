package com.shopify.model;

public class ShopifyProductMetafieldUpdateRequest {

    private final String productId;
    private final String metafieldId;
    private final Metafield request;

    public static interface ProductIdStep {
        MetafieldIdStep withProductId(final String customerId);
    }

    public static interface MetafieldIdStep {
        ValueStep withMetafieldId(final String metafieldId);
    }


    public static interface ValueStep {
        ValueTypeStep withValue(final String value);

        ValueTypeStep withSameValue();
    }

    public static interface ValueTypeStep {
        BuildStep withValueType(final MetafieldType valueType);

        BuildStep withSameValueType();
    }

    public static interface BuildStep {
        ShopifyProductMetafieldUpdateRequest build();
    }

    public static ProductIdStep newBuilder() {
        return new Steps();
    }

    public String getProductId() {
        return productId;
    }

    public String getMetafieldId() {
        return metafieldId;
    }

    public Metafield getRequest() {
        return request;
    }

    private ShopifyProductMetafieldUpdateRequest(final Steps steps) {
        this.metafieldId = steps.metafieldId;
        this.productId = steps.productId;
        this.request = steps.request;
    }

    private static class Steps implements ProductIdStep, MetafieldIdStep, ValueStep, ValueTypeStep, BuildStep {

        private String productId;

        private String metafieldId;
        private Metafield request = new Metafield();

        @Override
        public ShopifyProductMetafieldUpdateRequest build() {
            return new ShopifyProductMetafieldUpdateRequest(this);
        }

        @Override
        public MetafieldIdStep withProductId(final String productId) {
            this.productId = productId;
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
        public ValueTypeStep withSameValue() {
            return this;
        }

        @Override
        public BuildStep withValueType(final MetafieldType type) {
            this.request.setType(type);
            return this;
        }

        @Override
        public BuildStep withSameValueType() {
            return this;
        }
    }
}
