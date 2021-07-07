package com.shopify.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class ShopifyPageTest {

	private static final ShopifyOrder SHOPIFY_ORDER_1 = new ShopifyOrder();
	private static final ShopifyOrder SHOPIFY_ORDER_2 = new ShopifyOrder();

	@Test
	public void givenSomeValuesWhenBuildingShopifyPageThenExpectCorrectValues() throws Exception {
		final ShopifyPage<ShopifyOrder> shopifyPage = new ShopifyPage<>();
		shopifyPage.add(SHOPIFY_ORDER_1);
		shopifyPage.add(SHOPIFY_ORDER_2);
		shopifyPage.setNextPageInfo("123");
		shopifyPage.setPreviousPageInfo("456");

		assertSame(SHOPIFY_ORDER_1, shopifyPage.get(0));
		assertSame(SHOPIFY_ORDER_2, shopifyPage.get(1));

		assertEquals("123", shopifyPage.getNextPageInfo());
		assertEquals("456", shopifyPage.getPreviousPageInfo());
	}

	@Test
	public void givenTwoDifferentShopifyPagesWhenComparingHashCodesExpectThemToBeDifferent() throws Exception {
		final ShopifyPage<ShopifyOrder> shopifyPage1 = new ShopifyPage<>();
		shopifyPage1.add(SHOPIFY_ORDER_1);
		shopifyPage1.add(SHOPIFY_ORDER_2);
		shopifyPage1.setNextPageInfo("123");
		shopifyPage1.setPreviousPageInfo("456");

		final ShopifyPage<ShopifyOrder> shopifyPage2 = new ShopifyPage<>();
		shopifyPage1.add(SHOPIFY_ORDER_1);
		shopifyPage1.add(SHOPIFY_ORDER_2);
		shopifyPage1.setNextPageInfo("4124124");
		shopifyPage1.setPreviousPageInfo("54345");

		assertNotEquals(shopifyPage1.hashCode(), shopifyPage2.hashCode());
	}

	@Test
	public void givenTwoSameShopifyPagesWhenComparingHashCodesExpectThemToBeSame() throws Exception {
		final ShopifyPage<ShopifyOrder> shopifyPage1 = new ShopifyPage<>();
		shopifyPage1.add(SHOPIFY_ORDER_1);
		shopifyPage1.add(SHOPIFY_ORDER_2);
		shopifyPage1.setNextPageInfo("123");
		shopifyPage1.setPreviousPageInfo("456");

		assertEquals(shopifyPage1.hashCode(), shopifyPage1.hashCode());
	}

	@Test
	public void givenTwoDifferentShopifyPagesWithSameValuesWhenComparingHashCodesExpectThemToBeSame() throws Exception {
		final ShopifyPage<ShopifyOrder> shopifyPage1 = new ShopifyPage<>();
		shopifyPage1.add(SHOPIFY_ORDER_1);
		shopifyPage1.add(SHOPIFY_ORDER_2);
		shopifyPage1.setNextPageInfo("123");
		shopifyPage1.setPreviousPageInfo("456");

		final ShopifyPage<ShopifyOrder> shopifyPage2 = new ShopifyPage<>();
		shopifyPage2.add(SHOPIFY_ORDER_1);
		shopifyPage2.add(SHOPIFY_ORDER_2);
		shopifyPage2.setNextPageInfo("123");
		shopifyPage2.setPreviousPageInfo("456");
		assertEquals(shopifyPage1.hashCode(), shopifyPage2.hashCode());
	}

	@Test
	public void givenTwoDifferentShopifyPagesWithSameValuesWhenComparingEqualsExpectThemToBeSame() throws Exception {
		final ShopifyPage<ShopifyOrder> shopifyPage1 = new ShopifyPage<>();
		shopifyPage1.add(SHOPIFY_ORDER_1);
		shopifyPage1.add(SHOPIFY_ORDER_2);
		shopifyPage1.setNextPageInfo("123");
		shopifyPage1.setPreviousPageInfo("456");

		final ShopifyPage<ShopifyOrder> shopifyPage2 = new ShopifyPage<>();
		shopifyPage2.add(SHOPIFY_ORDER_1);
		shopifyPage2.add(SHOPIFY_ORDER_2);
		shopifyPage2.setNextPageInfo("123");
		shopifyPage2.setPreviousPageInfo("456");
		assertEquals(shopifyPage1, shopifyPage2);
	}

}
