package com.shopify.model.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class InventoryQuantityAdapter extends XmlAdapter<String, Long> {

	@Override
	public Long unmarshal(final String inventoryQuantity) throws Exception {
		return Long.valueOf(inventoryQuantity);
	}

	@Override
	public String marshal(final Long inventoryQuantity) throws Exception {
		return null;
	}

}
