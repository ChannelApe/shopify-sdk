package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class ShopifyCustomCollectionsRoot {

	@XmlElement(name = "custom_collections")
	private List<ShopifyCustomCollection> customCollections = new LinkedList<ShopifyCustomCollection>();
}
