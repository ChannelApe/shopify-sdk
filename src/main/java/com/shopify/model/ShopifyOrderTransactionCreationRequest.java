package com.shopify.model;

import java.math.BigDecimal;
import java.util.Currency;

public class ShopifyOrderTransactionCreationRequest {
    private final ShopifyTransaction request;

    public static ShopifyOrderTransactionCreationRequest.SetIdStep newBuilder() {
        return new ShopifyOrderTransactionCreationRequest.Steps();
    }

    public ShopifyTransaction getRequest() {
        return request;
    }

    public static interface SetOrderIdStep {
        SetIdStep withOrderId(final String orderId);
    }

    public static interface SetIdStep {
        SetAmountStep withId(final String id);
    }

    public static interface SetAmountStep {
        SetStatusStep withAmount(final BigDecimal amount);
    }

    public static interface SetStatusStep {
        SetKindStep withStatus(final String status);
    }

    public static interface SetKindStep {
        SetGatewayStep withKind(final String kind);
    }

    public static interface SetGatewayStep {
        SetCurrencyStep withGateway(final String gateway);
    }

    public static interface SetCurrencyStep {
        SetMaximumRefundableStep withCurrency(final Currency currency);
    }

    public static interface SetMaximumRefundableStep {
        SetMaximumRefundableStep withMaximumRefundable(final BigDecimal maximumRefundable);

        ShopifyOrderTransactionCreationRequest build();
    }

    private ShopifyOrderTransactionCreationRequest(final ShopifyTransaction request) {
        this.request = request;
    }

    private static class Steps implements SetOrderIdStep, SetIdStep, SetAmountStep, SetStatusStep, SetKindStep,
            SetGatewayStep, SetCurrencyStep, SetMaximumRefundableStep {
        private final ShopifyTransaction request = new ShopifyTransaction();

        @Override
        public SetIdStep withOrderId(String orderId) {
            request.setOrderId(orderId);
            return this;
        }

        @Override
        public SetAmountStep withId(String id) {
            request.setId(id);
            return this;
        }

        @Override
        public SetStatusStep withAmount(BigDecimal amount) {
            request.setAmount(amount);
            return this;
        }

        @Override
        public SetKindStep withStatus(String status) {
            request.setStatus(status);
            return this;
        }

        @Override
        public SetGatewayStep withKind(String kind) {
            request.setKind(kind);
            return this;
        }

        @Override
        public SetCurrencyStep withGateway(String gateway) {
            request.setGateway(gateway);
            return this;
        }

        @Override
        public SetMaximumRefundableStep withCurrency(Currency currency) {
            request.setCurrency(currency);
            return this;
        }

        @Override
        public SetMaximumRefundableStep withMaximumRefundable(BigDecimal maximumRefundable) {
            request.setMaximumRefundable(maximumRefundable);
            return this;
        }

        @Override
        public ShopifyOrderTransactionCreationRequest build() {
            return new ShopifyOrderTransactionCreationRequest(request);
        }
    }
}