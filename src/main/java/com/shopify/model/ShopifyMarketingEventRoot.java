package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyMarketingEventRoot {

	@XmlElement(name = "marketing_event")
	private ShopifyMarketingEvent marketingEvent;

	public ShopifyMarketingEvent getMarketingEvent() {
		return marketingEvent;
	}

	public void setMarketingEvent(final ShopifyMarketingEvent marketingEvent) {
		this.marketingEvent = marketingEvent;
	}

}
