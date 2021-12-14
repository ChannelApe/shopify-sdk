package com.shopify.model;

public enum MetafieldType {

	SINGLE_LINE_TEXT("single_line_text_field"),
	MULTI_LINE_TEXT("multi_line_text_field"),
	PAGE_REFERENCE("page_reference"),
	PRODUCT_REFERENCE("product_reference"),
	FILE_REFERENCE("file_reference"),
	NUMBER_INTEGER("number_integer"),
	DECIMAL("number_decimal"),
	DATE("date"),
	DATE_AND_TIME("date_time"),
	URL("url"),
	JSON_STRING("json"),
	BOOLEAN("boolean"),
	WEIGHT("weight"),
	VOLUME("volume"),
	DIMENSION("dimension"),
	RATING("rating");

	static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for %s";
	private static final String STRING_TYPE = "string";
	private static final String INTEGER_TYPE = "integer";
	private final String type;

	private MetafieldType(final String type) {
		this.type = type;
	}

	public static MetafieldType toEnum(final String type) {
		if (SINGLE_LINE_TEXT.toString().equals(type) || STRING_TYPE.equalsIgnoreCase(type)) {
			return MetafieldType.SINGLE_LINE_TEXT;
		} else if (NUMBER_INTEGER.toString().equals(type) || INTEGER_TYPE.equalsIgnoreCase(type)) {
			return MetafieldType.NUMBER_INTEGER;
		} else if (MULTI_LINE_TEXT.toString().equals(type)) {
			return MetafieldType.MULTI_LINE_TEXT;
		} else if (PAGE_REFERENCE.toString().equals(type)) {
			return MetafieldType.PAGE_REFERENCE;
		} else if (PRODUCT_REFERENCE.toString().equals(type)) {
			return MetafieldType.PRODUCT_REFERENCE;
		} else if (FILE_REFERENCE.toString().equals(type)) {
			return MetafieldType.FILE_REFERENCE;
		} else if (DECIMAL.toString().equals(type)) {
			return MetafieldType.DECIMAL;
		} else if (DATE.toString().equals(type)) {
			return MetafieldType.DATE;
		} else if (DATE_AND_TIME.toString().equals(type)) {
			return MetafieldType.DATE_AND_TIME;
		} else if (URL.toString().equals(type)) {
			return MetafieldType.URL;
		} else if (JSON_STRING.toString().equals(type)) {
			return MetafieldType.JSON_STRING;
		} else if (BOOLEAN.toString().equals(type)) {
			return MetafieldType.BOOLEAN;
		} else if (WEIGHT.toString().equals(type)) {
			return MetafieldType.WEIGHT;
		} else if (VOLUME.toString().equals(type)) {
			return MetafieldType.VOLUME;
		} else if (DIMENSION.toString().equals(type)) {
			return MetafieldType.DIMENSION;
		} else if (RATING.toString().equals(type)) {
			return MetafieldType.RATING;
		}
		throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, type));
	}

	@Override
	public String toString() {
		return type;
	}

}
