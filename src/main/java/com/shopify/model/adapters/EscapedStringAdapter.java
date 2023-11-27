package com.shopify.model.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang3.StringEscapeUtils;

public class EscapedStringAdapter extends XmlAdapter<String, String> {

	@Override
	public String unmarshal(final String escapedString) throws Exception {
		return StringEscapeUtils.unescapeHtml4(escapedString);
	}

	@Override
	public String marshal(final String unescapedString) throws Exception {
		return unescapedString;
	}

}
