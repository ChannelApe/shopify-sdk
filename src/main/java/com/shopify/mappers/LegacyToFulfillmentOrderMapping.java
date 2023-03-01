package com.shopify.mappers;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopify.ShopifySdk;
import com.shopify.exceptions.ShopifyUnsupportedActionException;
import com.shopify.model.ShopifyFulfillment;
import com.shopify.model.ShopifyFulfillmentOrder;
import com.shopify.model.ShopifyFulfillmentOrder.SupportedActions;
import com.shopify.model.ShopifyFulfillmentOrderLineItem;
import com.shopify.model.ShopifyFulfillmentOrderMoveLocationPayload;
import com.shopify.model.ShopifyFulfillmentOrderMoveRequestRoot;
import com.shopify.model.ShopifyFulfillmentOrderPayloadLineItem;
import com.shopify.model.ShopifyFulfillmentPayload;
import com.shopify.model.ShopifyFulfillmentPayloadRoot;
import com.shopify.model.ShopifyLineItem;
import com.shopify.model.ShopifyLineItemsByFulfillmentOrder;
import com.shopify.model.ShopifyTrackingInfo;
import com.shopify.model.ShopifyUpdateFulfillmentPayload;
import com.shopify.model.ShopifyUpdateFulfillmentPayloadRoot;

public class LegacyToFulfillmentOrderMapping {

	private static final Logger LOGGER = LoggerFactory.getLogger(LegacyToFulfillmentOrderMapping.class);

	/**
	 * the idea here is to create a payload similar to what <a href=
	 * "https://shopify.dev/docs/api/admin-rest/2023-04/resources/fulfillmentorder#post-fulfillment-orders-fulfillment-order-id-move">we
	 * have here</a>, the resulting payload will be sent to shopify via the
	 * active ShopifySdk instance
	 * 
	 * @see ShopifySdk
	 * @see ShopifyFulfillmentOrderMoveRequestRoot
	 * 
	 * @param newLocation
	 *            the new location that the fulfillment order will be moved to
	 * @param fulfillmentOrder
	 *            the fulfillment order to be moved
	 * @return an ShopifyFulfillmentOrderMoveRequestRoot instance to be sent to
	 *         shopify
	 */
	public static ShopifyFulfillmentOrderMoveRequestRoot toShopifyMoveFulfillmentOrder(final String newLocation,
			ShopifyFulfillmentOrder fulfillmentOrder) {
		try {
			final ShopifyFulfillmentOrderMoveLocationPayload payload = new ShopifyFulfillmentOrderMoveLocationPayload();
			payload.setNewLocationId(newLocation);
			for (final ShopifyFulfillmentOrderLineItem fulfillmentOrderLineItem : fulfillmentOrder.getLineItems()) {
				payload.getFulfillmentOrderLineItems().add(new ShopifyFulfillmentOrderPayloadLineItem(
						fulfillmentOrderLineItem.getId(), fulfillmentOrderLineItem.getQuantity()));
			}

			ShopifyFulfillmentOrderMoveRequestRoot root = new ShopifyFulfillmentOrderMoveRequestRoot();
			root.setFulfillmentOrder(payload);
			return root;
		} catch (final Exception e) {
			LOGGER.error(
					"There was an error parsing fulfillmentOrder into a ShopifyFulfillmentOrder moveRequest payload",
					e);
			throw e;
		}
	}

	/**
	 * the idea here is to create a payload similar to what <a href=
	 * "https://shopify.dev/docs/api/admin-rest/2023-04/resources/fulfillment#post-fulfillments">we
	 * have here</a>, the resulting payload will be sent to shopify via the
	 * active ShopifySdk instance
	 * 
	 * @see ShopifySdk
	 * @see ShopifyFulfillmentPayloadRoot
	 * 
	 * @param fulfillment
	 *            the active fulfillment to be created/updated
	 * @param fulfillmentOrders
	 *            a list of fulfillment orders that are contained within the
	 *            order
	 * @return an ShopifyFulfillmentPayloadRoot instance to be sent to shopify
	 */
	public static ShopifyFulfillmentPayloadRoot toShopifyFulfillmentPayloadRoot(final ShopifyFulfillment fulfillment,
			final List<ShopifyFulfillmentOrder> fulfillmentOrders) throws ShopifyUnsupportedActionException {
		try {
			final ShopifyTrackingInfo trackingInfo = new ShopifyTrackingInfo();
			final ShopifyFulfillmentPayload payload = new ShopifyFulfillmentPayload();
			final List<ShopifyLineItemsByFulfillmentOrder> lineItemsByFulfillmentOrder = new LinkedList<>();

			trackingInfo.setUrl(fulfillment.getTrackingUrl());
			trackingInfo.setNumber(fulfillment.getTrackingNumber());
			trackingInfo.setCompany(fulfillment.getTrackingCompany());

			// here we need to iterate through all fulfillmentOrder line items
			// to determine which fulfillment order line items are going to be
			// referenced inside the payload's line_items_by_fulfillment_order
			// section
			for (final ShopifyFulfillmentOrder fulfillmentOrder : fulfillmentOrders) {
				// if a shopify fulfillment is cancelled a new fulfillment order
				// is added the old one gets it's supported actions emptied, so
				// we need to make sure the current fulfillmentOrder supportes
				// fulfillment creation under it
				if (fulfillmentOrder.getSupportedActions().contains(SupportedActions.CREATE_FULFILLMENT.toString())) {
					ShopifyLineItemsByFulfillmentOrder lineItemsByFulfillment = new ShopifyLineItemsByFulfillmentOrder();
					lineItemsByFulfillment.setFulfillmentOrderId(fulfillmentOrder.getId());
					for (final ShopifyFulfillmentOrderLineItem fulfillmentOrderLineItem : fulfillmentOrder
							.getLineItems()) {
						for (final ShopifyLineItem fulfillmentLineItem : fulfillment.getLineItems()) {
							if (fulfillmentOrderLineItem.getLineItemId().equals(fulfillmentLineItem.getId())) {
								ShopifyFulfillmentOrderPayloadLineItem payloadLineItem = new ShopifyFulfillmentOrderPayloadLineItem(
										fulfillmentOrderLineItem.getId(), fulfillmentLineItem.getQuantity());
								lineItemsByFulfillment.getFulfillmentOrderLineItems().add(payloadLineItem);
							}
						}
					}

					if (lineItemsByFulfillment.getFulfillmentOrderLineItems().size() > 0) {
						lineItemsByFulfillmentOrder.add(lineItemsByFulfillment);
					}
				}
			}

			payload.setTrackingInfo(trackingInfo);
			payload.setNotifyCustomer(fulfillment.isNotifyCustomer());
			payload.setLineItemsByFulfillmentOrder(lineItemsByFulfillmentOrder);
			if (lineItemsByFulfillmentOrder.size() < 1)
				throw new ShopifyUnsupportedActionException(SupportedActions.CREATE_FULFILLMENT);

			ShopifyFulfillmentPayloadRoot root = new ShopifyFulfillmentPayloadRoot();
			root.setFulfillment(payload);
			return root;
		} catch (final Exception e) {
			LOGGER.error("There was an error parsing fulfillmentOrder into a ShopifyFulfillment create payload", e);
			throw e;
		}
	}

	/**
	 * the idea here is to create a payload similar to what <a href=
	 * "https://shopify.dev/docs/api/admin-rest/2023-04/resources/fulfillment#post-fulfillments-fulfillment-id-update-tracking">we
	 * have here</a>, the resulting payload will be sent to shopify via the
	 * active ShopifySdk instance
	 * 
	 * @see ShopifySdk
	 * @see ShopifyUpdateFulfillmentPayloadRoot
	 * 
	 * @param fulfillment
	 *            the active fulfillment to have it's tracking information
	 *            updated
	 * @return an ShopifyFulfillmentPayloadRoot instance to be sent to shopify
	 */
	public static ShopifyUpdateFulfillmentPayloadRoot toUpdateShopifyFulfillmentPayloadRoot(
			final ShopifyFulfillment fulfillment) {
		try {
			final ShopifyTrackingInfo trackingInfo = new ShopifyTrackingInfo();
			final ShopifyUpdateFulfillmentPayload payload = new ShopifyUpdateFulfillmentPayload();

			trackingInfo.setUrl(fulfillment.getTrackingUrl());
			trackingInfo.setNumber(fulfillment.getTrackingNumber());
			trackingInfo.setCompany(fulfillment.getTrackingCompany());

			payload.setTrackingInfo(trackingInfo);
			payload.setNotifyCustomer(fulfillment.isNotifyCustomer());

			ShopifyUpdateFulfillmentPayloadRoot root = new ShopifyUpdateFulfillmentPayloadRoot();
			root.setFulfillment(payload);
			return root;
		} catch (final Exception e) {
			LOGGER.error("There was an error parsing fulfillmentOrder into a ShopifyFulfillment update payload", e);
			throw e;
		}
	}

}
