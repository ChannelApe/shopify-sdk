package com.shopify.model;

public enum MetafieldValueType {

	STRING("string"), INTEGER("integer");

	static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for %s";
	private final String value;

	private MetafieldValueType(final String value) {
		this.value = value;
	}

	public static MetafieldValueType toEnum(final String value) {
		if (STRING.toString().equals(value)) {
			return MetafieldValueType.STRING;
		} else if (INTEGER.toString().equals(value)) {
			return MetafieldValueType.INTEGER;
		}
		throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
	}

	@Override
	public String toString() {
		return value;
	}

}
