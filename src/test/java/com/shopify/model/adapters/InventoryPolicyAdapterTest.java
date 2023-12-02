package com.shopify.model.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shopify.model.InventoryPolicy;

class InventoryPolicyAdapterTest {

	private InventoryPolicyAdapter inventoryPolicyAdapter;

	@BeforeEach
	void setUp() {
		inventoryPolicyAdapter = new InventoryPolicyAdapter();
	}

	@Test
	void giveSomeInventoryPolicyWhenMarshallingThenReturnMarshalledString() throws Exception {
		final String actualMarshalledString = inventoryPolicyAdapter.marshal(InventoryPolicy.CONTINUE);
		assertEquals(InventoryPolicy.CONTINUE.toString(), actualMarshalledString);
	}

	@Test
	void givenSomeStringWhenUnmarshallingThenReturnInventoryPolicy() throws Exception {
		final InventoryPolicy actualInventoryPolicy = inventoryPolicyAdapter
				.unmarshal(InventoryPolicy.CONTINUE.toString());
		assertEquals(InventoryPolicy.CONTINUE, actualInventoryPolicy);
	}

}
