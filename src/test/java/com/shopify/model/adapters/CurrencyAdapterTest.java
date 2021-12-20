package com.shopify.model.adapters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

public class CurrencyAdapterTest {

	private CurrencyAdapter currencyAdapter;

	@Before
	public void setUp() {
		currencyAdapter = new CurrencyAdapter();
	}

	@Test
	public void giveSomeCurrencyWhenMarshallingThenReturnCurrencyCode() throws Exception {
		final Currency currency = Currency.getInstance("USD");
		final String actualCurrencyCode = currencyAdapter.marshal(currency);
		assertEquals(currency.getCurrencyCode(), actualCurrencyCode);
	}

	@Test
	public void giveNullCurrencyWhenMarshallingThenReturnNullString() throws Exception {
		final Currency currency = null;
		final String actualCurrencyCode = currencyAdapter.marshal(currency);
		assertNull(actualCurrencyCode);
	}

	@Test
	public void givenEmptyCurrencyCodeWhenUnmarshallingThenReturnNullCurrency() throws Exception {
		final String currencyCode = "";
		final Currency actualCurrency = currencyAdapter.unmarshal(currencyCode);
		assertNull(actualCurrency);
	}

	@Test
	public void givenSomeCurrencyCodeWhenUnmarshallingThenReturnCurrency() throws Exception {
		final String currencyCode = "USD";
		final Currency actualCurrency = currencyAdapter.unmarshal(currencyCode);
		assertEquals(currencyCode, actualCurrency.getCurrencyCode());
	}

}
