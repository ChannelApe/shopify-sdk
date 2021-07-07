package com.shopify.model.adapters;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EscapedStringAdapterTest {

	private static final String UNESCAPED_STRING = "& & & I love this product & & &";
	private static final String ESCAPED_STRING = "&amp; &amp; &amp; I love this product &amp; &amp; &amp;";

	private EscapedStringAdapter escapedStringAdapter;

	@Before
	public void setUp() {
		escapedStringAdapter = new EscapedStringAdapter();
	}

	@Test
	public void giveSomeUnescapedStringWhenMarshallingThenReturnUnescapedString() throws Exception {
		assertEquals(UNESCAPED_STRING, escapedStringAdapter.marshal(UNESCAPED_STRING));
	}

	@Test
	public void giveSomeEscapedStringWhenUnmarshallingThenReturnUnescapedString() throws Exception {
		assertEquals(UNESCAPED_STRING, escapedStringAdapter.unmarshal(ESCAPED_STRING));
	}
}
