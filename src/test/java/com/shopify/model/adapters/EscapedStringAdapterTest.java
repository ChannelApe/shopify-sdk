package com.shopify.model.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EscapedStringAdapterTest {

	private static final String UNESCAPED_STRING = "& & & I love this product & & &";
	private static final String ESCAPED_STRING = "&amp; &amp; &amp; I love this product &amp; &amp; &amp;";

	private EscapedStringAdapter escapedStringAdapter;

	@BeforeEach
	void setUp() {
		escapedStringAdapter = new EscapedStringAdapter();
	}

	@Test
	void giveSomeUnescapedStringWhenMarshallingThenReturnUnescapedString() throws Exception {
		assertEquals(UNESCAPED_STRING, escapedStringAdapter.marshal(UNESCAPED_STRING));
	}

	@Test
	void giveSomeEscapedStringWhenUnmarshallingThenReturnUnescapedString() throws Exception {
		assertEquals(UNESCAPED_STRING, escapedStringAdapter.unmarshal(ESCAPED_STRING));
	}
}
