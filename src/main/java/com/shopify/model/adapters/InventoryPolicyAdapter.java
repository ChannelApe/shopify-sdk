package com.shopify.model.adapters;

import com.shopify.model.InventoryPolicy;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class InventoryPolicyAdapter extends XmlAdapter<String, InventoryPolicy> {

	@Override
	public InventoryPolicy unmarshal(final String inventoryPolicy) throws Exception {
		return InventoryPolicy.toEnum(inventoryPolicy);
	}

	@Override
	public String marshal(final InventoryPolicy inventoryManagement) throws Exception {
		return inventoryManagement.toString();
	}

}
