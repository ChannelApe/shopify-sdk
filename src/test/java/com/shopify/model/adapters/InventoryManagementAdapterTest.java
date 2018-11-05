package com.shopify.model.adapters;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.shopify.model.InventoryManagement;
import com.shopify.model.adapters.InventoryManagementAdapter;

public class InventoryManagementAdapterTest {

	private InventoryManagementAdapter inventoryManagementAdapter;

	@Before
	public void setUp() {
		inventoryManagementAdapter = new InventoryManagementAdapter();
	}

	@Test
	public void giveSomeInventoryManagementWhenMarshallingThenReturnMarshalledString() throws Exception {
		final String actualMarshalledString = inventoryManagementAdapter.marshal(InventoryManagement.BLANK);
		assertEquals(InventoryManagement.BLANK.toString(), actualMarshalledString);
	}

	@Test
	public void givenSomeStringWhenUnmarshallingThenReturnInventoryManagement() throws Exception {
		final InventoryManagement actualInventoryManagement = inventoryManagementAdapter
				.unmarshal(InventoryManagement.BLANK.toString());
		assertEquals(InventoryManagement.BLANK, actualInventoryManagement);
	}

}
