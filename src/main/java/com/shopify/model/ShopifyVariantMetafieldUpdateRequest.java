package com.shopify.model;

public class ShopifyVariantMetafieldUpdateRequest {

    private final String variantId;
    private final String metafieldId;
    private final Metafield request;

    public static interface VariantIdStep {
        MetafieldIdStep withVariantId(final String variantId);
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
        ShopifyVariantMetafieldUpdateRequest build();
    }

    public static VariantIdStep newBuilder() {
        return new Steps();
    }

    public String getVariantId() {
        return variantId;
    }

    public String getMetafieldId() {
        return metafieldId;
    }

    public Metafield getRequest() {
        return request;
    }

    private ShopifyVariantMetafieldUpdateRequest(final Steps steps) {
        this.metafieldId = steps.metafieldId;
        this.variantId = steps.variantId;
        this.request = steps.request;
    }

    private static class Steps implements VariantIdStep, MetafieldIdStep, ValueStep, ValueTypeStep, BuildStep {

        private String variantId;

        private String metafieldId;
        private Metafield request = new Metafield();

        @Override
        public ShopifyVariantMetafieldUpdateRequest build() {
            return new ShopifyVariantMetafieldUpdateRequest(this);
        }

        @Override
        public MetafieldIdStep withVariantId(final String variantId) {
            this.variantId = variantId;
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
