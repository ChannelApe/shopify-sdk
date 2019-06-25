package com.shopify.model;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShopifyGetCustomersRequestTest {
    @Test
    public void givenSomeValuesWhenShopifyCustomersGetRequestThenExpectCorrectValues() {
        DateTime minimumCreationDate = new DateTime();
        DateTime maximumCreationDate = minimumCreationDate.plusDays(1);
        final ShopifyGetCustomersRequest shopifyGetCustomersRequest = ShopifyGetCustomersRequest
                .newBuilder()
                .withPage(1)
                .withLimit(50)
                .withSinceId("since-id")
                .withCreatedAtMin(minimumCreationDate)
                .withCreatedAtMax(maximumCreationDate)
                .build();
        assertEquals(shopifyGetCustomersRequest.getPage(), 1);
        assertEquals(shopifyGetCustomersRequest.getLimit(), 50);
        assertEquals(shopifyGetCustomersRequest.getSinceId(), "since-id");
    }
}
