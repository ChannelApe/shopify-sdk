package com.shopify.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.shopify.model.adapters.OrderRiskRecommendationAdapter;

@XmlRootElement
public class ShopifyOrderRisk {

	private String id;
	@XmlElement(name = "order_id")
	private String orderId;
	@XmlElement(name = "checkout_id")
	private String checkoutId;
	private String source;
	private BigDecimal score;
	@XmlJavaTypeAdapter(OrderRiskRecommendationAdapter.class)
	private OrderRiskRecommendation recommendation;
	private boolean display;
	@XmlElement(name = "cause_cancel")
	private boolean causeCancel;
	private String message;
	@XmlElement(name = "merchant_message")
	private String merchantMessage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCheckoutId() {
		return checkoutId;
	}

	public void setCheckoutId(String checkoutId) {
		this.checkoutId = checkoutId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public OrderRiskRecommendation getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(OrderRiskRecommendation recommendation) {
		this.recommendation = recommendation;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public boolean isCauseCancel() {
		return causeCancel;
	}

	public void setCauseCancel(boolean causeCancel) {
		this.causeCancel = causeCancel;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMerchantMessage() {
		return merchantMessage;
	}

	public void setMerchantMessage(String merchantMessage) {
		this.merchantMessage = merchantMessage;
	}

}
