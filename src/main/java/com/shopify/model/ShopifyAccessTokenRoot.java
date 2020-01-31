package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyAccessTokenRoot {

	@JsonProperty("access_token")
	public String getAccessToken() {
		return accessToken;
	}

	private String accessToken;

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
