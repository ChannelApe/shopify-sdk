package com.shopify.model;

public enum InventoryManagement {

	SHOPIFY("shopify"), BLANK("blank");

	static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for %s";
	private final String value;

	private InventoryManagement(final String value) {
		this.value = value;
	}

	public static InventoryManagement toEnum(String value) {
		if (SHOPIFY.toString().equals(value)) {
			return InventoryManagement.SHOPIFY;
		} else if (BLANK.toString().equals(value)) {
			return InventoryManagement.BLANK;
		}
		throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
	}

	@Override
	public String toString() {
		return value;
	}

}
