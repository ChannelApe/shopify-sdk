package com.shopify.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class ShopifyAccessTokenRoot {

	private String accessToken;
}
