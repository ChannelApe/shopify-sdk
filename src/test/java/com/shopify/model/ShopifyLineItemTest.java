package com.shopify.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.Test;

public class ShopifyLineItemTest {

	private static final LinkedList<ShopifyTaxLine> SOME_TAX_LINES = new LinkedList<>();
	private static final LinkedList<ShopifyProperty> SOME_PROPERTIES = new LinkedList<>();
	private static final BigDecimal SOME_PRICE = BigDecimal.valueOf(41.55);

	@Test
	public void givenSomeValuesWhenBuildingLineItemsThenExpectLineItemValuesToBeCorrect() throws Exception {
		final ShopifyLineItem shopifyLineItem = new ShopifyLineItem();
		shopifyLineItem.setFulfillableQuantity(1);
		shopifyLineItem.setFulfillmentService("manual");
		shopifyLineItem.setFulfillmentStatus("inProgress");
		shopifyLineItem.setGiftCard(true);
		shopifyLineItem.setGrams(3);
		shopifyLineItem.setId("4");
		shopifyLineItem.setPrice(SOME_PRICE);
		shopifyLineItem.setName("Some_Name");
		shopifyLineItem.setProductId("123");
		shopifyLineItem.setQuantity(4L);
		shopifyLineItem.setRequiresShipping(true);
		shopifyLineItem.setSku("ABC-123");
		shopifyLineItem.setTaxable(true);
		shopifyLineItem.setTitle("Some_Title");
		shopifyLineItem.setTaxLines(SOME_TAX_LINES);
		shopifyLineItem.setProperties(SOME_PROPERTIES);
		shopifyLineItem.setTotalDiscount(SOME_PRICE);
		shopifyLineItem.setVariantId("1234");
		shopifyLineItem.setVariantInventoryManagement("shopify");
		shopifyLineItem.setVariantTitle("some-title");
		shopifyLineItem.setVendor("some-vendor");

		assertEquals(1, shopifyLineItem.getFulfillableQuantity());
		assertEquals("manual", shopifyLineItem.getFulfillmentService());
		assertEquals("inProgress", shopifyLineItem.getFulfillmentStatus());
		assertTrue(shopifyLineItem.isGiftCard());
		assertEquals(3, shopifyLineItem.getGrams());
		assertEquals("4", shopifyLineItem.getId());
		assertEquals("Some_Name", shopifyLineItem.getName());
		assertEquals(SOME_PRICE, shopifyLineItem.getPrice());
		assertEquals("123", shopifyLineItem.getProductId());
		assertTrue(shopifyLineItem.isRequiresShipping());
		assertEquals(4, shopifyLineItem.getQuantity());
		assertEquals("ABC-123", shopifyLineItem.getSku());
		assertEquals("Some_Title", shopifyLineItem.getTitle());
		assertEquals(SOME_PRICE, shopifyLineItem.getTotalDiscount());
		assertEquals("1234", shopifyLineItem.getVariantId());
		assertEquals("shopify", shopifyLineItem.getVariantInventoryManagement());
		assertEquals("some-title", shopifyLineItem.getVariantTitle());
		assertEquals("some-vendor", shopifyLineItem.getVendor());
		assertSame(SOME_PROPERTIES, shopifyLineItem.getProperties());

	}

}
