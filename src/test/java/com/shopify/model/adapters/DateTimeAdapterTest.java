package com.shopify.model.adapters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

public class DateTimeAdapterTest {

	private DateTimeAdapter dateTimeAdapter;

	@Before
	public void setUp() {
		dateTimeAdapter = new DateTimeAdapter();
	}

	@Test
	public void giveSomeDateTimeWhenMarshallingThenReturnTimestamp() throws Exception {
		final DateTime dateTime = DateTime.now(DateTimeZone.UTC).minusDays(3);
		final String actualTimestamp = dateTimeAdapter.marshal(dateTime);
		assertEquals(dateTime.toString(), actualTimestamp);
	}

	@Test
	public void giveNullDateTimeWhenMarshallingThenReturnNullString() throws Exception {
		final DateTime dateTime = null;
		final String actualTimestamp = dateTimeAdapter.marshal(dateTime);
		assertNull(actualTimestamp);
	}

	@Test
	public void givenEmptyTimestampWhenUnmarshallingThenReturnNullDateTime() throws Exception {
		final String timestamp = "";
		final DateTime actualDateTime = dateTimeAdapter.unmarshal(timestamp);
		assertNull(actualDateTime);
	}

	@Test
	public void givenSomeTimestampWhenUnmarshallingThenReturnDateTime() throws Exception {
		final String timestamp = DateTime.now(DateTimeZone.UTC).minusWeeks(3).toString();
		final DateTime actualDateTime = dateTimeAdapter.unmarshal(timestamp);
		assertEquals(timestamp, actualDateTime.toString());
	}

}
