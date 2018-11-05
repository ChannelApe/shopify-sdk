package com.shopify.model.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.shopify.model.OrderRiskRecommendation;

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
