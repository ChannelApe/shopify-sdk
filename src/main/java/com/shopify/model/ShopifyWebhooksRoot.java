package com.shopify.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyWebhooksRoot {

	private List<ShopifyWebhook> webhooks;

	public List<ShopifyWebhook> getWebhooks() {
		return webhooks;
	}

	public void setWebhooks(List<ShopifyWebhook> webhooks) {
		this.webhooks = webhooks;
	}
}
