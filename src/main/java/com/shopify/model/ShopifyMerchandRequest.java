package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyMerchandRequest {

	public enum Kind {
		FULFILLMENT_REQUEST("fulfillment_request"),
		CANCELLATION_REQUEST("cancellation_request"),
		LEGACY_FULFILL_REQUEST("legacy_fulfill_request");

		static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for status: %s";
		private final String value;

		private Kind(final String value) {
			this.value = value;
		}

		public static Kind toEnum(final String value) {
			if (FULFILLMENT_REQUEST.toString().equals(value)) {
				return Kind.FULFILLMENT_REQUEST;
			} else if (CANCELLATION_REQUEST.toString().equals(value)) {
				return Kind.CANCELLATION_REQUEST;
			} else if (LEGACY_FULFILL_REQUEST.toString().equals(value)) {
				return Kind.LEGACY_FULFILL_REQUEST;
			}

			throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
		}

		@Override
		public String toString() {
			return value;
		}
	}

	private String message;
	@XmlElement(name = "request_options")
	private String requestOptions;
	private String kind;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestOptions() {
		return requestOptions;
	}

	public void setRequestOptions(String requestOptions) {
		this.requestOptions = requestOptions;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

}
