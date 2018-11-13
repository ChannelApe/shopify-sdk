package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyGiftCardRoot {
	@XmlElement(name = "gift_card")
	private ShopifyGiftCard giftCard;

	public ShopifyGiftCard getGiftCard() {
		return giftCard;
	}

	public void setGiftCard(final ShopifyGiftCard giftCard) {
		this.giftCard = giftCard;
	}

}
