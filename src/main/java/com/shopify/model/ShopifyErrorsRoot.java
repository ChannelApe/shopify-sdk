package com.shopify.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class ShopifyErrorsRoot {

	private ShopifyErrors errors = new ShopifyErrors();

}
