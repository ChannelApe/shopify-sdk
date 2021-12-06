package com.shopify.model.adapters;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.shopify.model.MetafieldValueType;

public class MetafieldValueTypeAdapterTest {

	private MetafieldValueTypeAdapter metafieldValueTypeAdapter;

	@Before
	public void setUp() {
		metafieldValueTypeAdapter = new MetafieldValueTypeAdapter();
	}

	@Test
	public void giveSomeMetafieldValueTypeWhenMarshallingThenReturnMarshalledString() throws Exception {
		final String actualMarshalledString = metafieldValueTypeAdapter
				.marshal(MetafieldValueType.SINGLE_LINE_TEXT_FIELD);
		assertEquals(MetafieldValueType.SINGLE_LINE_TEXT_FIELD.toString(), actualMarshalledString);
	}

	@Test
	public void givenSomeStringWhenUnmarshallingThenReturnMetafieldValueType() throws Exception {
		final MetafieldValueType actualMetafieldValueType = metafieldValueTypeAdapter
				.unmarshal(MetafieldValueType.NUMBER_INTEGER.toString());
		assertEquals(MetafieldValueType.NUMBER_INTEGER, actualMetafieldValueType);
	}

}
