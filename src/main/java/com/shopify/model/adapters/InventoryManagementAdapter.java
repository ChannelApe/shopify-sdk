package com.shopify.model.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.shopify.model.InventoryManagement;

public class InventoryManagementAdapter extends XmlAdapter<String, InventoryManagement> {

	@Override
	public InventoryManagement unmarshal(final String inventoryManagement) throws Exception {
		return InventoryManagement.toEnum(inventoryManagement);
	}

	@Override
	public String marshal(final InventoryManagement inventoryManagement) throws Exception {
		return inventoryManagement.toString();
	}

}
