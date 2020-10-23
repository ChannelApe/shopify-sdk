package com.shopify;

import com.shopify.model.*;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

public class PrivateAppTest {
    private ShopifySdk shopifySdk;

    @Before
    public void initShopifySdk() {
        shopifySdk = ShopifySdk.newBuilder()
                .withSubdomain("star-soup")
                .withApiKey("")
                .withPassword("")
                .build();
    }

    public void createOrder() {
        ShopifyCustomer customer = new ShopifyCustomer();
        customer.setFirstName("Vantis");
        customer.setLastname("Zhang");
        ShopifyAddress shopifyAddress = new ShopifyAddress();
        shopifyAddress.setFirstName("Vantis");
        shopifyAddress.setLastname("Zhang");
        shopifyAddress.setAddress1("Test");
        shopifyAddress.setAddress2("Test2");
        shopifyAddress.setCity("Shanghai");
        shopifyAddress.setProvince("Shanghai");
        shopifyAddress.setCountry("China");
        ShopifyLineItem item = new ShopifyLineItem();
        item.setVariantId("31953388044330");
        item.setQuantity(1);
        ShopifyOrderCreationRequest shopifyOrderCreationRequest = ShopifyOrderCreationRequest.newBuilder()
                .withProcessedAt(DateTime.now())
                .withName("Vantis Zhang")
                .noCustomer()
                .withLineItems(Arrays.asList(
                        item
                ))
                .withShippingAddress(shopifyAddress)
                .withBillingAddress(shopifyAddress)
                .withMetafields(Arrays.asList(
                ))
                .withShippingLines(Collections.emptyList())
                .withNote("Created by O5 Test")
                .build();
        ShopifyOrder order = shopifySdk.createOrder(shopifyOrderCreationRequest);
        System.out.println(order);
    }

    @Test
    public void testEncoder() {
        String s = Base64.getEncoder().encodeToString(("" + ":" + "").getBytes());
        System.out.println(s);
    }
}
