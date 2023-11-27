package com.shopify.model;

public enum InventoryPolicy {

	DENY("deny"), CONTINUE("continue");

	static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for %s";
	private final String value;

	private InventoryPolicy(final String value) {
		this.value = value;
	}

	public static InventoryPolicy toEnum(String value) {
		if (DENY.toString().equals(value)) {
			return InventoryPolicy.DENY;
		} else if (CONTINUE.toString().equals(value)) {
			return InventoryPolicy.CONTINUE;
		}
		throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
	}

	@Override
	public String toString() {
		return value;
	}

}
