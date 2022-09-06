package com.shopify.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Currency;

public class ShopifyTransactionCreationRequest {
    private final ShopifyTransaction request;

    public static ShopifyTransactionCreationRequest.SetOrderIdStep newBuilder() {
        return new ShopifyTransactionCreationRequest.Steps();
    }

    public ShopifyTransaction getRequest() {
        return request;
    }

    public interface SetOrderIdStep {
        SetIdStep withOrderId(final String orderId);
    }

    public interface SetIdStep {
        SetAmountStep withId(final String id);
        SetAmountStep withNoId();
    }

    public interface SetAmountStep {
        SetAuthorizationStep withAmount(final BigDecimal amount);
        SetAuthorizationStep withDefaultOrderAmount();
    }

    public interface SetAuthorizationStep {
        SetAuthorizationExpiresAtStep withAuthorization(final String authorization);
        SetAuthorizationExpiresAtStep withNoAuthorization();
    }

    public interface SetAuthorizationExpiresAtStep {
        SetExtendedAuthorizationAttributesStep withAuthorizationExpiresAt(final DateTime authorizationExpiresAt);
        SetExtendedAuthorizationAttributesStep withNoAuthorizationExpiresAt();
    }

    public interface SetExtendedAuthorizationAttributesStep {
        SetStatusStep withExtendedAuthorizationAttributes(final ExtendedAuthorizationAttributes extendedAuthorizationAttributes);
        SetStatusStep withNoExtendedAuthorizationAttributes();
    }

    public interface SetStatusStep {
        SetKindStep withStatus(final TransactionStatus status);
        SetKindStep withNoStatus();
    }

    public interface SetKindStep {
        SetGatewayStep withKind(final TransactionKind kind);
    }

    public interface SetGatewayStep {
        SetCurrencyStep withGateway(final String gateway);
    }

    public interface SetCurrencyStep {
        SetMaximumRefundableStep withCurrency(final Currency currency);
    }

    public interface SetMaximumRefundableStep {
        SetMaximumRefundableStep withMaximumRefundable(final BigDecimal maximumRefundable);
        SetMaximumRefundableStep withNoMaximumRefundable();

        ShopifyTransactionCreationRequest build();
    }

    private ShopifyTransactionCreationRequest(final ShopifyTransaction request) {
        this.request = request;
    }

    private static class Steps implements SetOrderIdStep, SetIdStep, SetAmountStep, SetAuthorizationStep, SetAuthorizationExpiresAtStep,
            SetExtendedAuthorizationAttributesStep, SetStatusStep, SetKindStep, SetGatewayStep, SetCurrencyStep, SetMaximumRefundableStep {
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
        public SetAmountStep withNoId() {
            return this;
        }

        @Override
        public SetAuthorizationStep withAmount(BigDecimal amount) {
            request.setAmount(amount);
            return this;
        }

        @Override
        public SetAuthorizationStep withDefaultOrderAmount() {
            return this;
        }

        @Override
        public SetKindStep withStatus(TransactionStatus status) {
            request.setStatus(status);
            return this;
        }

        @Override
        public SetKindStep withNoStatus() {
            return this;
        }

        @Override
        public SetGatewayStep withKind(TransactionKind kind) {
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
        public SetMaximumRefundableStep withNoMaximumRefundable() {
            return this;
        }

        @Override
        public ShopifyTransactionCreationRequest build() {
            return new ShopifyTransactionCreationRequest(request);
        }

        @Override
        public SetAuthorizationExpiresAtStep withAuthorization(String authorization) {
            request.setAuthorization(authorization);
            return this;
        }

        @Override
        public SetAuthorizationExpiresAtStep withNoAuthorization() {
            return this;
        }

        @Override
        public SetExtendedAuthorizationAttributesStep withAuthorizationExpiresAt(DateTime authorizationExpiresAt) {
            request.setAuthorizationExpiresAt(authorizationExpiresAt);
            return this;
        }

        @Override
        public SetExtendedAuthorizationAttributesStep withNoAuthorizationExpiresAt() {
            return this;
        }

        @Override
        public SetStatusStep withExtendedAuthorizationAttributes(ExtendedAuthorizationAttributes extendedAuthorizationAttributes) {
            request.setExtendedAuthorizationAttributes(extendedAuthorizationAttributes);
            return this;
        }

        @Override
        public SetStatusStep withNoExtendedAuthorizationAttributes() {
            return this;
        }
    }
}