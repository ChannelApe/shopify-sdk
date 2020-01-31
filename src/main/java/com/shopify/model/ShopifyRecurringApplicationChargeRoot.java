package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyRecurringApplicationChargeRoot {

	@JsonProperty("recurring_application_charge")
	private ShopifyRecurringApplicationCharge recurringApplicationCharge;

	public ShopifyRecurringApplicationCharge getRecurringApplicationCharge() {
		return recurringApplicationCharge;
	}

	public void setRecurringApplicationCharge(ShopifyRecurringApplicationCharge recurringApplicationCharge) {
		this.recurringApplicationCharge = recurringApplicationCharge;
	}

}
