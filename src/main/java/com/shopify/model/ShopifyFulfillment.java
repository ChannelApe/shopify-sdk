package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;
import org.joda.time.DateTime;

import com.shopify.model.adapters.DateTimeAdapter;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyFulfillment {

	public enum Status {

		PENDING("pending"), OPEN("open"), SUCCESS("success"), CANCELLED("cancelled"), ERROR("error"),
		FAILURE("failure");

		static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for status: %s";
		private final String value;

		private Status(final String value) {
			this.value = value;
		}

		public static Status toEnum(final String value) {
			if (PENDING.toString().equals(value)) {
				return Status.PENDING;
			} else if (OPEN.toString().equals(value)) {
				return Status.OPEN;
			} else if (SUCCESS.toString().equals(value)) {
				return Status.SUCCESS;
			} else if (CANCELLED.toString().equals(value)) {
				return Status.CANCELLED;
			} else if (ERROR.toString().equals(value)) {
				return Status.ERROR;
			} else if (FAILURE.toString().equals(value)) {
				return Status.FAILURE;
			}

			throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
		}

		@Override
		public String toString() {
			return value;
		}
	}

	private String id;
	@XmlElement(name = "order_id")
	private String orderId;
	private String status;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	@XmlElement(name = "tracking_company")
	private String trackingCompany;
	@XmlElement(name = "tracking_number")
	private String trackingNumber;
	@XmlElement(name = "notify_customer")
	private boolean notifyCustomer;
	@XmlElement(name = "line_items")
	private List<ShopifyLineItem> lineItems = new LinkedList<>();
	@XmlElement(name = "tracking_url")
	private String trackingUrl;
	@XmlElement(name = "tracking_urls")
	private List<String> trackingUrls = new LinkedList<>();
	@XmlElement(name = "location_id")
	private String locationId;

}
