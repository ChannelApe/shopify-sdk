package com.shopify.model;

public enum TransactionErrorCode {
    INCORRECT_NUMBER("incorrect_number"),
    INVALID_NUMBER("invalid_number"),
    INVALID_EXPIRY_DATE("invalid_expiry_date"),
    INVALID_CVC("invalid_cvc"),
    EXPIRED_CARD("expired_card"),
    INCORRECT_CVC("incorrect_cvc"),
    INCORRECT_ZIP("incorrect_zip"),
    INCORRECT_ADDRESS("incorrect_address"),
    CARD_DECLINED("card_declined"),
    PROCESSING_ERROR("processing_error"),
    CALL_ISSUER("call_issuer"),
    PICK_UP_CARD("pick_up_card");

    static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for %s";

    private final String value;

    private TransactionErrorCode(final String value) {
        this.value = value;
    }

    public static TransactionErrorCode toEnum(String value) {
        if (INCORRECT_NUMBER.toString().equals(value)) {
            return TransactionErrorCode.INCORRECT_NUMBER;
        } else if (INVALID_NUMBER.toString().equals(value)) {
            return TransactionErrorCode.INVALID_NUMBER;
        } else if (INVALID_CVC.toString().equals(value)) {
            return TransactionErrorCode.INVALID_CVC;
        } else if (EXPIRED_CARD.toString().equals(value)) {
            return TransactionErrorCode.EXPIRED_CARD;
        } else if (INVALID_EXPIRY_DATE.toString().equals(value)) {
            return TransactionErrorCode.INVALID_EXPIRY_DATE;
        } else if (INCORRECT_CVC.toString().equals(value)) {
            return TransactionErrorCode.INCORRECT_CVC;
        } else if (INCORRECT_ZIP.toString().equals(value)) {
            return TransactionErrorCode.INCORRECT_ZIP;
        } else if (INCORRECT_ADDRESS.toString().equals(value)) {
            return TransactionErrorCode.INCORRECT_ADDRESS;
        } else if (CARD_DECLINED.toString().equals(value)) {
            return TransactionErrorCode.CARD_DECLINED;
        } else if (PROCESSING_ERROR.toString().equals(value)) {
            return TransactionErrorCode.PROCESSING_ERROR;
        } else if (CALL_ISSUER.toString().equals(value)) {
            return TransactionErrorCode.CALL_ISSUER;
        } else if (PICK_UP_CARD.toString().equals(value)) {
            return TransactionErrorCode.PICK_UP_CARD;
        }

        throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

    @Override
    public String toString() {
        return value;
    }
}
