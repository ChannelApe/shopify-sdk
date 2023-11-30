package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyInternationalDuty {

	/**
	 * The international duties relevant to the fulfillment order
	 * 
	 * @see #DAP
	 * @see #DDP
	 */
	public enum Inconterm {
		/**
		 * Delivered at place.
		 */
		DAP("dap"),
		/**
		 * Delivered duty paid.
		 */
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

			throw new IllegalArgumentException(NO_MATCHING_ENUMS_ERROR_MESSAGE.formatted(value));
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
