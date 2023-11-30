package com.shopify.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyFulfillmentHold {

	private String reason;
	@XmlElement(name = "reason_notes")
	private String reasonNotes;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReasonNotes() {
		return reasonNotes;
	}

	public void setReasonNotes(String reasonNotes) {
		this.reasonNotes = reasonNotes;
	}

}
