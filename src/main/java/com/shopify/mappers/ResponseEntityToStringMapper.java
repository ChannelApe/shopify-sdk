package com.shopify.mappers;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseEntityToStringMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseEntityToStringMapper.class);

	private ResponseEntityToStringMapper() {
	}

	/**
	 * JAXRS Jersey Client implementation closes stream buffers when reading
	 * entity by string. To combat this and be able to read entities via a
	 * string more than once, this deals with the input streams involved and
	 * resets where necessary.
	 *
	 * @param response
	 *            the response gotten from a request
	 * @return the response as a string value
	 */
	public static String map(final Response response) {

		try {
			response.bufferEntity();
			final InputStream entityBytes = (InputStream) response.getEntity();
			final String responseBody = IOUtils.toString(entityBytes, StandardCharsets.UTF_8.toString());
			entityBytes.reset();
			return responseBody;
		} catch (final Exception e) {
			LOGGER.error("There was an error parsing response body on response with status {}, returning null",
					response.getStatus());
			return null;
		}
	}

}
