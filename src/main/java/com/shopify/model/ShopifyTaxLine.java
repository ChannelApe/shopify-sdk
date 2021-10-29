package com.shopify.model;

import com.shopify.model.price.PriceSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
public class ShopifyTaxLine {

	private String title;
	private BigDecimal price;
	private BigDecimal rate;
	@XmlElement(name = "price_set")
	private PriceSet priceSet;
	@XmlElement(name = "channel_liable")
	private Boolean channelLiable;

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(final BigDecimal rate) {
		this.rate = rate;
	}

	public PriceSet getPriceSet() {
		return priceSet;
	}

	public void setPriceSet(final PriceSet priceSet) {
		this.priceSet = priceSet;
	}

	public Boolean getChannelLiable() {
		return channelLiable;
	}

	public void setChannelLiable(final Boolean channelLiable) {
		this.channelLiable = channelLiable;
	}
}
