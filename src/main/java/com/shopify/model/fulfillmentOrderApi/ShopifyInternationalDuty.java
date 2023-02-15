package com.shopify.model.fulfillmentOrderApi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyInternationalDuty {

	public enum Inconterm {
		// Delivered at place.
		DAP("dap"),
		// Delivered duty paid
		DDP("ddp");

		static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for status: %s";
		private final String value;

		private Inconterm(final String value) {
			this.value = value;
		}

		public static Inconterm toEnum(final String value) {
			if (DAP.toString().equals(value)) {
				return Inconterm.DAP;
			} else if (DDP.toString().equals(value)) {
				return Inconterm.DDP;
			}

			throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
		}

		@Override
		public String toString() {
			return value;
		}
	}

	private String incoterm;

	public String getIncoterm() {
		return incoterm;
	}

	public void setIncoterm(String incoterm) {
		this.incoterm = incoterm;
	}

}
