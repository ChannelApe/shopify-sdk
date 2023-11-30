package com.shopify.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class ShopifyCustomCollectionRoot {

	@XmlElement(name = "custom_collection")
	private ShopifyCustomCollection customCollection;
}
