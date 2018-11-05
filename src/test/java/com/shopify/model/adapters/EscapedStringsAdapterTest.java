package com.shopify.model.adapters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.shopify.model.adapters.EscapedStringsAdapter;

public class EscapedStringsAdapterTest {

	private static final Set<String> UNESCAPED_STRINGS = new HashSet<>(
			Arrays.asList("& & & I love this product & & &", "& one more"));
	private static final Set<String> ESCAPED_STRINGS = new HashSet<>(
			Arrays.asList("&amp; &amp; &amp; I love this product &amp; &amp; &amp;", "&amp; one more"));

	private EscapedStringsAdapter escapedStringsAdapter;

	@Before
	public void setUp() {
		escapedStringsAdapter = new EscapedStringsAdapter();
	}

	@Test
	public void giveSomeUnescapedStringsWhenMarshallingThenReturnUnescapedStrings() throws Exception {
		assertEquals(UNESCAPED_STRINGS, escapedStringsAdapter.marshal(UNESCAPED_STRINGS));
	}

	@Test
	public void giveNullWhenMarshallingThenReturnNull() throws Exception {
		assertNull(escapedStringsAdapter.marshal(null));
	}

	@Test
	public void giveSomeEscapedStringsWhenUnmarshallingThenReturnUnescapedStrings() throws Exception {
		assertEquals(UNESCAPED_STRINGS, escapedStringsAdapter.unmarshal(ESCAPED_STRINGS));
	}

	@Test
	public void giveNullWhenUnmarshallingThenReturnNull() throws Exception {
		assertNull(escapedStringsAdapter.unmarshal(null));
	}
}
