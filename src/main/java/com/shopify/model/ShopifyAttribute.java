package com.shopify.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class ShopifyAttribute {

	private String name;
	private String value;

}
