package com.shopify.model.adapters;

import com.shopify.model.OrderRiskRecommendation;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class OrderRiskRecommendationAdapter extends XmlAdapter<String, OrderRiskRecommendation> {

	@Override
	public OrderRiskRecommendation unmarshal(final String orderRiskRecommendation) throws Exception {
		return OrderRiskRecommendation.toEnum(orderRiskRecommendation);
	}

	@Override
	public String marshal(final OrderRiskRecommendation orderRiskRecommendation) throws Exception {
		return orderRiskRecommendation.toString();
	}

}
