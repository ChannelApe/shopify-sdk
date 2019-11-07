
package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyCollect {

	@XmlElement(name = "collection_id")
	private String collectionId;

	private Boolean featured;

	@XmlElement(name = "product_id")
	private String productId;

	public String getCollectionId() {
		return collectionId;
	}

	public Boolean getFeatured() {
		return featured;
	}

	public String getProductId() {
		return productId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	public void setFeatured(Boolean featured) {
		this.featured = featured;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
}
