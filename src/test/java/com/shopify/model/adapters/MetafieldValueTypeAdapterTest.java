package com.shopify.model.adapters;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.shopify.model.MetafieldValueType;
import com.shopify.model.adapters.MetafieldValueTypeAdapter;

public class MetafieldValueTypeAdapterTest {

	private MetafieldValueTypeAdapter metafieldValueTypeAdapter;

	@Before
	public void setUp() {
		metafieldValueTypeAdapter = new MetafieldValueTypeAdapter();
	}

	@Test
	public void giveSomeMetafieldValueTypeWhenMarshallingThenReturnMarshalledString() throws Exception {
		final String actualMarshalledString = metafieldValueTypeAdapter.marshal(MetafieldValueType.STRING);
		assertEquals(MetafieldValueType.STRING.toString(), actualMarshalledString);
	}

	@Test
	public void givenSomeStringWhenUnmarshallingThenReturnMetafieldValueType() throws Exception {
		final MetafieldValueType actualMetafieldValueType = metafieldValueTypeAdapter
				.unmarshal(MetafieldValueType.INTEGER.toString());
		assertEquals(MetafieldValueType.INTEGER, actualMetafieldValueType);
	}

}
