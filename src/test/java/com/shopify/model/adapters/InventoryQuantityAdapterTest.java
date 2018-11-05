package com.shopify.model.adapters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.shopify.model.adapters.InventoryQuantityAdapter;

public class InventoryQuantityAdapterTest {

	@InjectMocks
	private InventoryQuantityAdapter inventoryQuantityAdapter;

	@Before
	public void setUp() {
		inventoryQuantityAdapter = new InventoryQuantityAdapter();
	}

	@Test
	public void givenSomeInventoryQuantityWhenMarshallingThenReturnNullString() throws Exception {
		final String actualMarshalledString = inventoryQuantityAdapter.marshal(5L);
		assertNull(actualMarshalledString);
	}

	@Test
	public void givenSomeStringWhenUnmarshallingThenReturnInventoryQuantity() throws Exception {
		final Long actualInventoryQuantity = inventoryQuantityAdapter.unmarshal("5");
		assertEquals(5L, actualInventoryQuantity.longValue());
	}

}
