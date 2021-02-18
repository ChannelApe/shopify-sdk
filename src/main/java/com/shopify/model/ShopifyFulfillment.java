package com.shopify.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.joda.time.DateTime;

import com.shopify.model.adapters.DateTimeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
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
	@XmlElement(name = "tracking_numbers")
	private List<String> trackingNumbers;
	@XmlElement(name = "notify_customer")
	private boolean notifyCustomer;
	@XmlElement(name = "line_items")
	private List<ShopifyLineItem> lineItems = new LinkedList<>();
	@XmlElement(name = "tracking_urls")
	private List<String> trackingUrls = new LinkedList<>();
	@XmlElement(name = "location_id")
	private String locationId;
	private String name;
	private Object receipt;
	private String service;
	@XmlElement(name = "shipment_status")
	private String shipmentStatus;
	@XmlElement(name = "variant_inventory_management")
	private String variantInventoryManagement;

}
