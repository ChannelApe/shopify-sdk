package com.shopify.model.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public class DateTimeAdapter extends XmlAdapter<String, DateTime> {

	@Override
	public DateTime unmarshal(final String timestamp) throws Exception {
		if (StringUtils.isBlank(timestamp)) {
			return null;
		}
		return DateTime.parse(timestamp);
	}

	@Override
	public String marshal(final DateTime dateTime) throws Exception {
		if (dateTime == null) {
			return null;
		}
		return dateTime.toString();
	}

}
