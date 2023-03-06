package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

import com.shopify.model.adapters.DateTimeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyFulfillmentOrder {

	/**
	 * Lists all acceptable status for a given FulfillmentOrder
	 * 
	 * @see #OPEN
	 * @see #CLOSED
	 * @see #ON_HOLD
	 * @see #CANCELLED
	 * @see #SCHEDULED
	 * @see #INCOMPLETE
	 * @see #IN_PROGRESS
	 */
	public enum Status {
		/**
		 * The fulfillment order is ready for fulfillment
		 */
		OPEN("open"),
		/**
		 * The fulfillment order has been completed and closed
		 */
		CLOSED("closed"),
		/**
		 * The fulfillment order is on hold. The fulfillment process can't be
		 * initiated until the hold on the fulfillment order is released
		 */
		ON_HOLD("on_hold"),
		/**
		 * The fulfillment order has been cancelled by the merchant
		 */
		CANCELLED("cancelled"),
		/**
		 * The fulfillment order is deferred and will be ready for fulfillment
		 * after the datetime specified in fulfill_at
		 */
		SCHEDULED("scheduled"),
		/**
		 * The fulfillment order cannot be completed as requested
		 */
		INCOMPLETE("incomplete"),
		/**
		 * The fulfillment order is being processed
		 */
		IN_PROGRESS("in_progress");

		static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for status: %s";
		private final String value;

		private Status(final String value) {
			this.value = value;
		}

		public static Status toEnum(final String value) {
			if (OPEN.toString().equals(value)) {
				return Status.OPEN;
			} else if (CLOSED.toString().equals(value)) {
				return Status.CLOSED;
			} else if (ON_HOLD.toString().equals(value)) {
				return Status.ON_HOLD;
			} else if (CANCELLED.toString().equals(value)) {
				return Status.CANCELLED;
			} else if (SCHEDULED.toString().equals(value)) {
				return Status.SCHEDULED;
			} else if (INCOMPLETE.toString().equals(value)) {
				return Status.INCOMPLETE;
			} else if (IN_PROGRESS.toString().equals(value)) {
				return Status.IN_PROGRESS;
			}

			throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * The actions that can be performed on this fulfillment order.
	 */
	public enum SupportedActions {
		HOLD("hold"),
		MOVE("move"),
		EXTERNAL("external"),
		MARK_AS_OPEN("mark_as_open"),
		RELEASE_HOLD("release_hold"),
		CREATE_FULFILLMENT("create_fulfillment"),
		REQUEST_FULFILLMENT("request_fulfillment"),
		REQUEST_CANCELLATION("request_cancellation"),
		CANCEL_FULFILLMENT_ORDER("cancel_fulfillment_order");

		static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for supported action: %s";
		private final String value;

		private SupportedActions(final String value) {
			this.value = value;
		}

		public static SupportedActions toEnum(final String value) {
			if (MOVE.toString().equals(value)) {
				return SupportedActions.MOVE;
			} else if (HOLD.toString().equals(value)) {
				return SupportedActions.HOLD;
			} else if (EXTERNAL.toString().equals(value)) {
				return SupportedActions.EXTERNAL;
			} else if (MARK_AS_OPEN.toString().equals(value)) {
				return SupportedActions.MARK_AS_OPEN;
			} else if (RELEASE_HOLD.toString().equals(value)) {
				return SupportedActions.RELEASE_HOLD;
			} else if (CREATE_FULFILLMENT.toString().equals(value)) {
				return SupportedActions.CREATE_FULFILLMENT;
			} else if (REQUEST_FULFILLMENT.toString().equals(value)) {
				return SupportedActions.REQUEST_FULFILLMENT;
			} else if (REQUEST_CANCELLATION.toString().equals(value)) {
				return SupportedActions.REQUEST_CANCELLATION;
			} else if (CANCEL_FULFILLMENT_ORDER.toString().equals(value)) {
				return SupportedActions.CANCEL_FULFILLMENT_ORDER;
			}

			throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * List of all request status of the fulfillment order. Valid values
	 * 
	 * @see #CLOSED
	 * @see #ACCEPTED
	 * @see #REJECTED
	 * @see #SUBMITTED
	 * @see #UNSUBMITTED
	 * @see #CANCELLATION_ACCEPTED
	 * @see #CANCELLATION_REJECTED
	 * @see #CANCELLATION_REQUESTED
	 */
	public enum RequestStatus {
		/**
		 * The fulfillment service closed the fulfillment order without
		 * completing it.
		 */
		CLOSED("closed"),
		/**
		 * The fulfillment service accepted the merchant's fulfillment request
		 */
		ACCEPTED("accepted"),
		/**
		 * The fulfillment service rejected the merchant's fulfillment request
		 */
		REJECTED("rejected"),
		/**
		 * The merchant requested fulfillment for this fulfillment order
		 */
		SUBMITTED("submitted"),
		/**
		 * The initial request status for newly-created fulfillment orders. This
		 * is the only valid request status for fulfillment orders that aren't
		 * assigned to a fulfillment service
		 */
		UNSUBMITTED("unsubmitted"),
		/**
		 * The fulfillment service accepted the merchant's fulfillment
		 * cancellation request
		 */
		CANCELLATION_ACCEPTED("cancellation_accepted"),
		/**
		 * The fulfillment service rejected the merchant's fulfillment
		 * cancellation request
		 */
		CANCELLATION_REJECTED("cancellation_rejected"),
		/**
		 * The merchant requested a cancellation of the fulfillment request for
		 * this fulfillment order
		 */
		CANCELLATION_REQUESTED("cancellation_requested");

		static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for supported action: %s";
		private final String value;

		private RequestStatus(final String value) {
			this.value = value;
		}

		public static RequestStatus toEnum(final String value) {
			if (CLOSED.toString().equals(value)) {
				return RequestStatus.CLOSED;
			} else if (ACCEPTED.toString().equals(value)) {
				return RequestStatus.ACCEPTED;
			} else if (REJECTED.toString().equals(value)) {
				return RequestStatus.REJECTED;
			} else if (SUBMITTED.toString().equals(value)) {
				return RequestStatus.SUBMITTED;
			} else if (UNSUBMITTED.toString().equals(value)) {
				return RequestStatus.UNSUBMITTED;
			} else if (CANCELLATION_ACCEPTED.toString().equals(value)) {
				return RequestStatus.CANCELLATION_ACCEPTED;
			} else if (CANCELLATION_REJECTED.toString().equals(value)) {
				return RequestStatus.CANCELLATION_REJECTED;
			} else if (CANCELLATION_REQUESTED.toString().equals(value)) {
				return RequestStatus.CANCELLATION_REQUESTED;
			}

			throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
		}

		@Override
		public String toString() {
			return value;
		}
	}

	private String id;
	@XmlElement(name = "shop_id")
	private String shopId;
	@XmlElement(name = "order_id")
	private String orderId;
	@XmlElement(name = "assigned_location_id")
	private String assignedLocationId;
	@XmlElement(name = "request_status")
	private String requestStatus;
	private String status;
	@XmlElement(name = "supported_actions")
	private List<String> supportedActions = new LinkedList<>();
	private ShopifyDestination destination;
	@XmlElement(name = "line_items")
	private List<ShopifyFulfillmentOrderLineItem> lineItems = new LinkedList<>();
	@XmlElement(name = "fulfill_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime fulfillAt;
	@XmlElement(name = "fulfill_by")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime fulfillBy;
	@XmlElement(name = "international_duties")
	private ShopifyInternationalDuty internationalDuties;
	@XmlElement(name = "fulfillment_holds")
	private List<ShopifyFulfillmentHold> fulfillmentHolds = new LinkedList<>();
	@XmlElement(name = "delivery_method")
	private ShopifyDeliveryMethod deliveryMethod;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	@XmlElement(name = "assigned_location")
	private ShopifyAssignedLocation assignedLocation;
	@XmlElement(name = "merchant_requests")
	private List<ShopifyMerchandRequest> merchantRequests = new LinkedList<>();

	public String getAssignedLocationId() {
		return assignedLocationId;
	}

	public void setAssignedLocationId(String assignedLocationId) {
		this.assignedLocationId = assignedLocationId;
	}

	public ShopifyDestination getDestination() {
		return destination;
	}

	public void setDestination(ShopifyDestination destination) {
		this.destination = destination;
	}

	public ShopifyDeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(ShopifyDeliveryMethod deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public DateTime getFulfillAt() {
		return fulfillAt;
	}

	public void setFulfillAt(DateTime fulfillAt) {
		this.fulfillAt = fulfillAt;
	}

	public DateTime getFulfillBy() {
		return fulfillBy;
	}

	public void setFulfillBy(DateTime fulfillBy) {
		this.fulfillBy = fulfillBy;
	}

	public List<ShopifyFulfillmentHold> getFulfillmentHolds() {
		return fulfillmentHolds;
	}

	public void setFulfillmentHolds(List<ShopifyFulfillmentHold> fulfillmentHolds) {
		this.fulfillmentHolds = fulfillmentHolds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ShopifyInternationalDuty getInternationalDuties() {
		return internationalDuties;
	}

	public void setInternationalDuties(ShopifyInternationalDuty internationalDuties) {
		this.internationalDuties = internationalDuties;
	}

	public List<ShopifyFulfillmentOrderLineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<ShopifyFulfillmentOrderLineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getSupportedActions() {
		return supportedActions;
	}

	public void setSupportedActions(List<String> supportedActions) {
		this.supportedActions = supportedActions;
	}

	public List<ShopifyMerchandRequest> getMerchantRequests() {
		return merchantRequests;
	}

	public void setMerchantRequests(List<ShopifyMerchandRequest> merchantRequests) {
		this.merchantRequests = merchantRequests;
	}

	public ShopifyAssignedLocation getAssignedLocation() {
		return assignedLocation;
	}

	public void setAssignedLocation(ShopifyAssignedLocation assignedLocation) {
		this.assignedLocation = assignedLocation;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean hasSupportedAction(final SupportedActions action) {
		final String stringAction = action.toString().toLowerCase();
		for (final String supportedAction : this.getSupportedActions()) {
			if (supportedAction.toLowerCase().equals(stringAction)) {
				return true;
			}
		}

		return false;
	}

}
