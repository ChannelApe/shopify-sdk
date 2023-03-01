package com.shopify.exceptions;

import com.shopify.model.ShopifyFulfillmentOrder;

public class ShopifyUnsupportedActionException extends Exception {
	private static final long serialVersionUID = -6803895255030397292L;

	public ShopifyUnsupportedActionException(final ShopifyFulfillmentOrder.SupportedActions action) {
		super("The action '" + action.toString() + "' is not supported...");
	}

}
