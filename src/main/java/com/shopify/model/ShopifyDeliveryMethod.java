package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

import com.shopify.model.adapters.DateTimeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyDeliveryMethod {

	/**
	 * The type of method used to transfer a product or service to a customer
	 * 
	 * @see #NONE
	 * @see #LOCAL
	 * @see #CUSTOM
	 * @see #RETAIL
	 * @see #PICK_UP
	 * @see #SHIPPING
	 */
	public enum MethodType {
		/**
		 * No delivery method
		 */
		NONE("none"),
		/**
		 * A delivery to a customer's doorstep.
		 */
		LOCAL("local"),
		/**
		 * A default value for a non matching delivery method
		 */
		CUSTOM("custom"),
		/**
		 * Items delivered immediately in a retail store.
		 */
		RETAIL("retail"),
		/**
		 * A delivery that a customer picks up at your retail store, curbside,
		 * or any location that you choose
		 */
		PICK_UP("pick_up"),
		/**
		 * A delivery to a customer using a shipping carrier
		 */
		SHIPPING("shipping");

		static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for status: %s";
		private final String value;

		private MethodType(final String value) {
			this.value = value;
		}

		public static MethodType toEnum(final String value) {
			if (NONE.toString().equals(value)) {
				return MethodType.NONE;
			} else if (LOCAL.toString().equals(value)) {
				return MethodType.LOCAL;
			} else if (RETAIL.toString().equals(value)) {
				return MethodType.RETAIL;
			} else if (PICK_UP.toString().equals(value)) {
				return MethodType.PICK_UP;
			} else if (SHIPPING.toString().equals(value)) {
				return MethodType.SHIPPING;
			} else {
				return MethodType.CUSTOM;
			}
		}

		@Override
		public String toString() {
			return value;
		}
	}

	private String id;
	@XmlElement(name = "method_type")
	private String methodType;
	@XmlElement(name = "min_delivery_date_time")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime minDeliveryDateTime;
	@XmlElement(name = "max_delivery_date_time")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime maxDeliveryDateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public DateTime getMinDeliveryDateTime() {
		return minDeliveryDateTime;
	}

	public void setMinDeliveryDateTime(DateTime minDeliveryDateTime) {
		this.minDeliveryDateTime = minDeliveryDateTime;
	}

	public DateTime getMaxDeliveryDateTime() {
		return maxDeliveryDateTime;
	}

	public void setMaxDeliveryDateTime(DateTime maxDeliveryDateTime) {
		this.maxDeliveryDateTime = maxDeliveryDateTime;
	}

}
