package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetafieldRoot {

	private Metafield metafield;

	public Metafield getMetafield() {
		return metafield;
	}

	public void setMetafield(Metafield metafield) {
		this.metafield = metafield;
	}

}
