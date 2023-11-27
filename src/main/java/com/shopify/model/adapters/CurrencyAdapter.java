package com.shopify.model.adapters;

import java.util.Currency;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang3.StringUtils;

public class CurrencyAdapter extends XmlAdapter<String, Currency> {

	@Override
	public Currency unmarshal(final String currencyCode) throws Exception {
		if (StringUtils.isBlank(currencyCode)) {
			return null;
		}
		return Currency.getInstance(currencyCode);
	}

	@Override
	public String marshal(final Currency currency) throws Exception {
		if (currency == null) {
			return null;
		}
		return currency.getCurrencyCode();
	}

}
