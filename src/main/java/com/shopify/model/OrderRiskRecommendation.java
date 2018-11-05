package com.shopify.model;

public enum OrderRiskRecommendation {

	ACCEPT("accept"), INVESTIGATE("investigate"), CANCEL("cancel");

	static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for %s";
	private final String value;

	private OrderRiskRecommendation(final String value) {
		this.value = value;
	}

	public static OrderRiskRecommendation toEnum(String value) {
		if (ACCEPT.toString().equals(value)) {
			return OrderRiskRecommendation.ACCEPT;
		} else if (INVESTIGATE.toString().equals(value)) {
			return OrderRiskRecommendation.INVESTIGATE;
		} else if (CANCEL.toString().equals(value)) {
			return OrderRiskRecommendation.CANCEL;
		}
		throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
	}

	@Override
	public String toString() {
		return value;
	}

}
