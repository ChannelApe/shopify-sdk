package com.shopify.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ShopifyAddressTest {

	private static final String SOME_PROVINCE_CODE = "PA";
	private static final String SOME_PROVINCE = "Pennsylvania";
	private static final String SOME_PHONE = "3874928734234";
	private static final BigDecimal SOME_LONGITUDE = new BigDecimal(485.8323);
	private static final BigDecimal SOME_LATITUDE = new BigDecimal(41.489);
	private static final String SOME_NAME = "Ryan Kazokas";
	private static final String SOME_LAST_NAME = "Kazokas";
	private static final String SOME_FIRST_NAME = "Ryan";
	private static final String SOME_COUNTRY_CODE = "US";
	private static final String SOME_COUNTRY = "United States";
	private static final String SOME_COMPANY = "ChannelApe";
	private static final String SOME_CITY = "Scranton";
	private static final String SOME_ADDRESS_2 = "Suite 100";
	private static final String SOME_ADDRESS_1 = "224 Wyoming Ave";

	@Test
	public void givenSomeValuesWhenBuildingShopifyAddressThenExpectCorrectValues() {

		final ShopifyAddress shopifyAddress = new ShopifyAddress();
		shopifyAddress.setAddress1(SOME_ADDRESS_1);
		shopifyAddress.setAddress2(SOME_ADDRESS_2);
		shopifyAddress.setCity(SOME_CITY);
		shopifyAddress.setCompany(SOME_COMPANY);
		shopifyAddress.setCountry(SOME_COUNTRY);
		shopifyAddress.setCountryCode(SOME_COUNTRY_CODE);
		shopifyAddress.setFirstName(SOME_FIRST_NAME);
		shopifyAddress.setLastname(SOME_LAST_NAME);
		shopifyAddress.setName(SOME_NAME);
		shopifyAddress.setLatitude(SOME_LATITUDE);
		shopifyAddress.setLongitude(SOME_LONGITUDE);
		shopifyAddress.setPhone(SOME_PHONE);
		shopifyAddress.setProvince(SOME_PROVINCE);
		shopifyAddress.setProvinceCode(SOME_PROVINCE_CODE);

		assertEquals(SOME_ADDRESS_1, shopifyAddress.getAddress1());
		assertEquals(SOME_ADDRESS_2, shopifyAddress.getAddress2());
		assertEquals(SOME_CITY, shopifyAddress.getCity());
		assertEquals(SOME_COMPANY, shopifyAddress.getCompany());
		assertEquals(SOME_COUNTRY, shopifyAddress.getCountry());
		assertEquals(SOME_COUNTRY_CODE, shopifyAddress.getCountryCode());
		assertEquals(SOME_FIRST_NAME, shopifyAddress.getFirstName());
		assertEquals(SOME_LAST_NAME, shopifyAddress.getLastname());
		assertEquals(SOME_LATITUDE, shopifyAddress.getLatitude());
		assertEquals(SOME_LONGITUDE, shopifyAddress.getLongitude());
		assertEquals(SOME_PHONE, shopifyAddress.getPhone());
		assertEquals(SOME_PROVINCE, shopifyAddress.getProvince());
		assertEquals(SOME_PROVINCE_CODE, shopifyAddress.getProvinceCode());

	}

}
