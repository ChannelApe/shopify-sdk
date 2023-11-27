package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;

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
