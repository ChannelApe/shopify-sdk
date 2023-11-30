package com.shopify.model.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EscapedStringsAdapterTest {

	private static final List<String> UNESCAPED_STRINGS = new ArrayList<>(
			Arrays.asList("& & & I love this product & & &", "& one more"));
	private static final List<String> ESCAPED_STRINGS = new ArrayList<>(
			Arrays.asList("&amp; &amp; &amp; I love this product &amp; &amp; &amp;", "&amp; one more"));

	private EscapedStringsAdapter escapedStringsAdapter;

	@BeforeEach
	void setUp() {
		escapedStringsAdapter = new EscapedStringsAdapter();
	}

	@Test
	void giveSomeUnescapedStringsWhenMarshallingThenReturnUnescapedStrings() throws Exception {
		assertEquals(UNESCAPED_STRINGS, escapedStringsAdapter.marshal(UNESCAPED_STRINGS));
	}

	@Test
	void giveNullWhenMarshallingThenReturnNull() throws Exception {
		assertNull(escapedStringsAdapter.marshal(null));
	}

	@Test
	void giveSomeEscapedStringsWhenUnmarshallingThenReturnUnescapedStrings() throws Exception {
		assertEquals(UNESCAPED_STRINGS, escapedStringsAdapter.unmarshal(ESCAPED_STRINGS));
	}

	@Test
	void giveNullWhenUnmarshallingThenReturnNull() throws Exception {
		assertNull(escapedStringsAdapter.unmarshal(null));
	}
}
