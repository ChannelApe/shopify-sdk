package com.shopify.model.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrencyAdapterTest {

	private CurrencyAdapter currencyAdapter;

	@BeforeEach
	void setUp() {
		currencyAdapter = new CurrencyAdapter();
	}

	@Test
	void giveSomeCurrencyWhenMarshallingThenReturnCurrencyCode() throws Exception {
		final Currency currency = Currency.getInstance("USD");
		final String actualCurrencyCode = currencyAdapter.marshal(currency);
		assertEquals(currency.getCurrencyCode(), actualCurrencyCode);
	}

	@Test
	void giveNullCurrencyWhenMarshallingThenReturnNullString() throws Exception {
		final Currency currency = null;
		final String actualCurrencyCode = currencyAdapter.marshal(currency);
		assertNull(actualCurrencyCode);
	}

	@Test
	void givenEmptyCurrencyCodeWhenUnmarshallingThenReturnNullCurrency() throws Exception {
		final String currencyCode = "";
		final Currency actualCurrency = currencyAdapter.unmarshal(currencyCode);
		assertNull(actualCurrency);
	}

	@Test
	void givenSomeCurrencyCodeWhenUnmarshallingThenReturnCurrency() throws Exception {
		final String currencyCode = "USD";
		final Currency actualCurrency = currencyAdapter.unmarshal(currencyCode);
		assertEquals(currencyCode, actualCurrency.getCurrencyCode());
	}

}
