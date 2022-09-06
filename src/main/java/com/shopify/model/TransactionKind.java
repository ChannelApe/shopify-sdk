package com.shopify.model;

public enum TransactionKind  {
    AUTHORIZATION("authorization"),
    CAPTURE("capture"),
    SALE("sale"),
    VOID("void"),
    REFUND("refund");

    static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for %s";
    private final String value;

    private TransactionKind(final String value) {
        this.value = value;
    }

    public static TransactionKind toEnum(String value) {
        if (AUTHORIZATION.toString().equals(value)) {
            return TransactionKind.AUTHORIZATION;
        } else if (CAPTURE.toString().equals(value)) {
            return TransactionKind.CAPTURE;
        } else if (SALE.toString().equals(value)) {
            return TransactionKind.SALE;
        } else if (VOID.toString().equals(value)) {
            return TransactionKind.VOID;
        } else if (REFUND.toString().equals(value)) {
            return TransactionKind.REFUND;
        }
        throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

    @Override
    public String toString() {
        return value;
    }
}
