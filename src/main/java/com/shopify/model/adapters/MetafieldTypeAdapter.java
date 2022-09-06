package com.shopify.model.adapters;

import com.shopify.model.MetafieldType;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MetafieldTypeAdapter extends XmlAdapter<String, MetafieldType> {

	@Override
	public MetafieldType unmarshal(final String metafieldType) throws Exception {
		return MetafieldType.toEnum(metafieldType);
	}

	@Override
	public String marshal(final MetafieldType metafieldType) throws Exception {
		return metafieldType.toString();
	}

}
