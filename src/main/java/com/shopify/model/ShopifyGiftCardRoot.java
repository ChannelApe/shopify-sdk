package com.shopify.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class ShopifyGiftCardRoot {
	@XmlElement(name = "gift_card")
	private ShopifyGiftCard giftCard;

}
