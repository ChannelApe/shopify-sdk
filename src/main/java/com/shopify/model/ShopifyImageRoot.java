package com.shopify.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class ShopifyImageRoot {

	private Image image;
}
