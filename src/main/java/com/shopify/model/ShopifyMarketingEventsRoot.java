package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyMarketingEventsRoot {

	@XmlElement(name = "marketing_events")
	private List<ShopifyMarketingEvent> marketingEvents = new LinkedList<>();

	public List<ShopifyMarketingEvent> getMarketingEvents() {
		return marketingEvents;
	}

	public void setMarketingEvents(final List<ShopifyMarketingEvent> marketingEvents) {
		this.marketingEvents = marketingEvents;
	}

}
