package com.shopify.model.adapters;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.shopify.model.OrderRiskRecommendation;

public class OrderRiskRecommendationAdapterTest {

	private OrderRiskRecommendationAdapter orderRiskRecommendationAdapter;

	@Before
	public void setUp() {
		orderRiskRecommendationAdapter = new OrderRiskRecommendationAdapter();
	}

	@Test
	public void giveAcceptOrderRiskRecommendationWhenMarshallingThenReturnMarshalledString() throws Exception {
		final String actualMarshalledString = orderRiskRecommendationAdapter.marshal(OrderRiskRecommendation.ACCEPT);
		assertEquals(OrderRiskRecommendation.ACCEPT.toString(), actualMarshalledString);
	}

	@Test
	public void givenAcceptStringWhenUnmarshallingThenReturnOrderRiskRecommendation() throws Exception {
		final OrderRiskRecommendation actualOrderRiskRecommendation = orderRiskRecommendationAdapter
				.unmarshal(OrderRiskRecommendation.ACCEPT.toString());
		assertEquals(OrderRiskRecommendation.ACCEPT, actualOrderRiskRecommendation);
	}

	@Test
	public void giveInvestigateOrderRiskRecommendationWhenMarshallingThenReturnMarshalledString() throws Exception {
		final String actualMarshalledString = orderRiskRecommendationAdapter
				.marshal(OrderRiskRecommendation.INVESTIGATE);
		assertEquals(OrderRiskRecommendation.INVESTIGATE.toString(), actualMarshalledString);
	}

	@Test
	public void givenInvestigateStringWhenUnmarshallingThenReturnOrderRiskRecommendation() throws Exception {
		final OrderRiskRecommendation actualOrderRiskRecommendation = orderRiskRecommendationAdapter
				.unmarshal(OrderRiskRecommendation.INVESTIGATE.toString());
		assertEquals(OrderRiskRecommendation.INVESTIGATE, actualOrderRiskRecommendation);
	}

	@Test
	public void giveCancelOrderRiskRecommendationWhenMarshallingThenReturnMarshalledString() throws Exception {
		final String actualMarshalledString = orderRiskRecommendationAdapter.marshal(OrderRiskRecommendation.CANCEL);
		assertEquals(OrderRiskRecommendation.CANCEL.toString(), actualMarshalledString);
	}

	@Test
	public void givenCancelStringWhenUnmarshallingThenReturnOrderRiskRecommendation() throws Exception {
		final OrderRiskRecommendation actualOrderRiskRecommendation = orderRiskRecommendationAdapter
				.unmarshal(OrderRiskRecommendation.CANCEL.toString());
		assertEquals(OrderRiskRecommendation.CANCEL, actualOrderRiskRecommendation);
	}

	@Test(expected = IllegalArgumentException.class)
	public void givenMaybeStringWhenUnmarshallingThenThrowNewIllegalArgumentException() throws Exception {
		orderRiskRecommendationAdapter.unmarshal("maybe");
	}

}
