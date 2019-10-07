package com.shopify.model;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopifyGetCustomersRequestTest {
    @Test
    public void givenSomeValuesWhenShopifyCustomersGetRequestThenExpectCorrectValues() {
        DateTime minimumCreationDate = new DateTime();
        DateTime maximumCreationDate = minimumCreationDate.plusDays(1);
        List<String> ids = new ArrayList<>();
        ids.add("some-id");
        final ShopifyGetCustomersRequest shopifyGetCustomersRequest = ShopifyGetCustomersRequest
                .newBuilder()
                .withPage(1)
                .withLimit(50)
                .withSinceId("since-id")
                .withIds(ids)
                .withCreatedAtMin(minimumCreationDate)
                .withCreatedAtMax(maximumCreationDate)
                .build();
        assertEquals(1, shopifyGetCustomersRequest.getPage());
        assertEquals(50, shopifyGetCustomersRequest.getLimit());
        assertEquals("since-id", shopifyGetCustomersRequest.getSinceId());
        assertEquals("some-id", shopifyGetCustomersRequest.getIds().get(0));
        assertEquals(minimumCreationDate, shopifyGetCustomersRequest.getCreatedAtMin());
        assertEquals(maximumCreationDate, shopifyGetCustomersRequest.getCreatedAtMax());
    }
}
