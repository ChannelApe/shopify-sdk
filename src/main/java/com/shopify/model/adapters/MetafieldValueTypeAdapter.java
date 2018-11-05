package com.shopify.model.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.shopify.model.MetafieldValueType;

public class MetafieldValueTypeAdapter extends XmlAdapter<String, MetafieldValueType> {

	@Override
	public MetafieldValueType unmarshal(final String metafieldValueType) throws Exception {
		return MetafieldValueType.toEnum(metafieldValueType);
	}

	@Override
	public String marshal(final MetafieldValueType metafieldValueType) throws Exception {
		return metafieldValueType.toString();
	}

}
