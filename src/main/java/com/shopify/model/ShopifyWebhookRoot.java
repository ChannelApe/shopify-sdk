package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyWebhookRoot {

	private ShopifyWebhook webhook;

	public ShopifyWebhook getWebhook() {
		return webhook;
	}

	public void setWebhook(ShopifyWebhook webhook) {
		this.webhook = webhook;
	}
}
