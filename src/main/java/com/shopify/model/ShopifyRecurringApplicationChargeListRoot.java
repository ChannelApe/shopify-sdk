package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ShopifyRecurringApplicationChargeListRoot {

	@XmlElement(name = "recurring_application_charges")
	private List<ShopifyRecurringApplicationCharge> recurringApplicationCharges;

	public List<ShopifyRecurringApplicationCharge> getRecurringApplicationCharges() {
		return recurringApplicationCharges;
	}

	public void setRecurringApplicationCharges(List<ShopifyRecurringApplicationCharge> recurringApplicationCharges) {
		this.recurringApplicationCharges = recurringApplicationCharges;
	}

}
