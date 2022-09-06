package com.shopify.model;

public enum TransactionStatus {
    PENDING("pending"),
    FAILURE("failure"),
    SUCCESS("success"),
    ERROR("error");

    static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for %s";
    private final String value;

    private TransactionStatus(final String value) {
        this.value = value;
    }

    public static TransactionStatus toEnum(String value) {
        if (PENDING.toString().equals(value)) {
            return TransactionStatus.PENDING;
        } else if (FAILURE.toString().equals(value)) {
            return TransactionStatus.FAILURE;
        } else if (SUCCESS.toString().equals(value)) {
            return TransactionStatus.SUCCESS;
        } else if (ERROR.toString().equals(value)) {
            return TransactionStatus.ERROR;
        }

        throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

    @Override
    public String toString() {
        return value;
    }
}
