package com.shopify.model.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateTimeAdapterTest {

	private DateTimeAdapter dateTimeAdapter;

	@BeforeEach
	void setUp() {
		dateTimeAdapter = new DateTimeAdapter();
	}

	@Test
	void giveSomeDateTimeWhenMarshallingThenReturnTimestamp() throws Exception {
		final DateTime dateTime = DateTime.now(DateTimeZone.UTC).minusDays(3);
		final String actualTimestamp = dateTimeAdapter.marshal(dateTime);
		assertEquals(dateTime.toString(), actualTimestamp);
	}

	@Test
	void giveNullDateTimeWhenMarshallingThenReturnNullString() throws Exception {
		final DateTime dateTime = null;
		final String actualTimestamp = dateTimeAdapter.marshal(dateTime);
		assertNull(actualTimestamp);
	}

	@Test
	void givenEmptyTimestampWhenUnmarshallingThenReturnNullDateTime() throws Exception {
		final String timestamp = "";
		final DateTime actualDateTime = dateTimeAdapter.unmarshal(timestamp);
		assertNull(actualDateTime);
	}

	@Test
	void givenSomeTimestampWhenUnmarshallingThenReturnDateTime() throws Exception {
		final String timestamp = DateTime.now(DateTimeZone.UTC).minusWeeks(3).toString();
		final DateTime actualDateTime = dateTimeAdapter.unmarshal(timestamp);
		assertEquals(timestamp, actualDateTime.toString());
	}

}
