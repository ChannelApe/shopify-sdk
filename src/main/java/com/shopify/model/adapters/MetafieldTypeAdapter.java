package com.shopify.model.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.shopify.model.MetafieldType;

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
