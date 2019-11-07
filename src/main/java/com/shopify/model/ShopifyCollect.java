
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

	public static class Builder {

		private String collectionId;
		private Boolean featured;
		private String productId;

		public ShopifyCollect.Builder withCollectionId(String collectionId) {
			this.collectionId = collectionId;
			return this;
		}

		public ShopifyCollect.Builder withFeatured(Boolean featured) {
			this.featured = featured;
			return this;
		}

		public ShopifyCollect.Builder withProductId(String productId) {
			this.productId = productId;
			return this;
		}

		public ShopifyCollect build() {
			ShopifyCollect shopifyCollect = new ShopifyCollect();
			shopifyCollect.collectionId = collectionId;
			shopifyCollect.featured = featured;
			shopifyCollect.productId = productId;
			return shopifyCollect;
		}

	}

}
