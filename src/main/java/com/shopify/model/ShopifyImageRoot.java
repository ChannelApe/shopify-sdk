package com.shopify.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyImageRoot {

	private Image image;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
