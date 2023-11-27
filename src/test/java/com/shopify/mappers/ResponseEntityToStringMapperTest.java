package com.shopify.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.core.Response;

import org.junit.Test;

public class ResponseEntityToStringMapperTest {

	@Test
	public void givenSomeValuesWhenMappingResponseEntityToAStringThenExpectCorrectValues() throws Exception {
		final Response response = mock(Response.class);

		final String expectedResponseBodyString = "{\"error\": \"something went wrong.\"}";
		final InputStream expectedResponseStream = new ByteArrayInputStream(
				expectedResponseBodyString.getBytes(StandardCharsets.UTF_8));
		when(response.getEntity()).thenReturn(expectedResponseStream);
		final String actualResponseBodyString = ResponseEntityToStringMapper.map(response);
		assertEquals(expectedResponseBodyString, actualResponseBodyString);

	}

	@Test
	public void givenSomeExceptionOccursWhenMappingResponseEntityToAStringThenExpectNullValue() throws Exception {
		final Response response = mock(Response.class);

		when(response.getEntity()).thenThrow(new NullPointerException());
		final String actualResponseBodyString = ResponseEntityToStringMapper.map(response);
		assertNull(actualResponseBodyString);

	}

}
