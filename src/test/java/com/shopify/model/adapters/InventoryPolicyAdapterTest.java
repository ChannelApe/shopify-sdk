package com.shopify.model.adapters;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.shopify.model.InventoryPolicy;

public class InventoryPolicyAdapterTest {

	private InventoryPolicyAdapter inventoryPolicyAdapter;

	@Before
	public void setUp() {
		inventoryPolicyAdapter = new InventoryPolicyAdapter();
	}

	@Test
	public void giveSomeInventoryPolicyWhenMarshallingThenReturnMarshalledString() throws Exception {
		final String actualMarshalledString = inventoryPolicyAdapter.marshal(InventoryPolicy.CONTINUE);
		assertEquals(InventoryPolicy.CONTINUE.toString(), actualMarshalledString);
	}

	@Test
	public void givenSomeStringWhenUnmarshallingThenReturnInventoryPolicy() throws Exception {
		final InventoryPolicy actualInventoryPolicy = inventoryPolicyAdapter
				.unmarshal(InventoryPolicy.CONTINUE.toString());
		assertEquals(InventoryPolicy.CONTINUE, actualInventoryPolicy);
	}

}
