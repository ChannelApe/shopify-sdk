package com.shopify.model.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shopify.model.OrderRiskRecommendation;

class OrderRiskRecommendationAdapterTest {

	private OrderRiskRecommendationAdapter orderRiskRecommendationAdapter;

	@BeforeEach
	void setUp() {
		orderRiskRecommendationAdapter = new OrderRiskRecommendationAdapter();
	}

	@Test
	void giveAcceptOrderRiskRecommendationWhenMarshallingThenReturnMarshalledString() throws Exception {
		final String actualMarshalledString = orderRiskRecommendationAdapter.marshal(OrderRiskRecommendation.ACCEPT);
		assertEquals(OrderRiskRecommendation.ACCEPT.toString(), actualMarshalledString);
	}

	@Test
	void givenAcceptStringWhenUnmarshallingThenReturnOrderRiskRecommendation() throws Exception {
		final OrderRiskRecommendation actualOrderRiskRecommendation = orderRiskRecommendationAdapter
				.unmarshal(OrderRiskRecommendation.ACCEPT.toString());
		assertEquals(OrderRiskRecommendation.ACCEPT, actualOrderRiskRecommendation);
	}

	@Test
	void giveInvestigateOrderRiskRecommendationWhenMarshallingThenReturnMarshalledString() throws Exception {
		final String actualMarshalledString = orderRiskRecommendationAdapter
				.marshal(OrderRiskRecommendation.INVESTIGATE);
		assertEquals(OrderRiskRecommendation.INVESTIGATE.toString(), actualMarshalledString);
	}

	@Test
	void givenInvestigateStringWhenUnmarshallingThenReturnOrderRiskRecommendation() throws Exception {
		final OrderRiskRecommendation actualOrderRiskRecommendation = orderRiskRecommendationAdapter
				.unmarshal(OrderRiskRecommendation.INVESTIGATE.toString());
		assertEquals(OrderRiskRecommendation.INVESTIGATE, actualOrderRiskRecommendation);
	}

	@Test
	void giveCancelOrderRiskRecommendationWhenMarshallingThenReturnMarshalledString() throws Exception {
		final String actualMarshalledString = orderRiskRecommendationAdapter.marshal(OrderRiskRecommendation.CANCEL);
		assertEquals(OrderRiskRecommendation.CANCEL.toString(), actualMarshalledString);
	}

	@Test
	void givenCancelStringWhenUnmarshallingThenReturnOrderRiskRecommendation() throws Exception {
		final OrderRiskRecommendation actualOrderRiskRecommendation = orderRiskRecommendationAdapter
				.unmarshal(OrderRiskRecommendation.CANCEL.toString());
		assertEquals(OrderRiskRecommendation.CANCEL, actualOrderRiskRecommendation);
	}

	@Test
	void givenMaybeStringWhenUnmarshallingThenThrowNewIllegalArgumentException() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			orderRiskRecommendationAdapter.unmarshal("maybe");
		});
	}

}
