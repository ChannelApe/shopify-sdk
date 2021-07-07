package com.shopify.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ShopifyAddressUpdateRequestTest {

	private static final String SOME_PROVINCE_CODE = "PA";
	private static final String SOME_PROVINCE = "Pennsylvania";
	private static final String SOME_PHONE = "3874928734234";
	private static final BigDecimal SOME_LONGITUDE = new BigDecimal(485.8323);
	private static final BigDecimal SOME_LATITUDE = new BigDecimal(41.489);
	private static final String SOME_LAST_NAME = "Kazokas";
	private static final String SOME_FIRST_NAME = "Ryan";
	private static final String SOME_COUNTRY_CODE = "US";
	private static final String SOME_COUNTRY = "United States";
	private static final String SOME_COMPANY = "ChannelApe";
	private static final String SOME_CITY = "Scranton";
	private static final String SOME_ADDRESS_2 = "Suite 100";
	private static final String SOME_ADDRESS_1 = "224 Wyoming Ave";

	@Test
	public void givenSomeValuesWhenBuildingShopifyAddressUpdateRequestThenExpectCorrectValues() {

		final ShopifyAddressUpdateRequest shopifyAddressUpdateRequest = new ShopifyAddressUpdateRequest();
		shopifyAddressUpdateRequest.setAddress1(SOME_ADDRESS_1);
		shopifyAddressUpdateRequest.setAddress2(SOME_ADDRESS_2);
		shopifyAddressUpdateRequest.setCity(SOME_CITY);
		shopifyAddressUpdateRequest.setCompany(SOME_COMPANY);
		shopifyAddressUpdateRequest.setCountry(SOME_COUNTRY);
		shopifyAddressUpdateRequest.setCountryCode(SOME_COUNTRY_CODE);
		shopifyAddressUpdateRequest.setFirstName(SOME_FIRST_NAME);
		shopifyAddressUpdateRequest.setLastname(SOME_LAST_NAME);
		shopifyAddressUpdateRequest.setLatitude(SOME_LATITUDE);
		shopifyAddressUpdateRequest.setLongitude(SOME_LONGITUDE);
		shopifyAddressUpdateRequest.setPhone(SOME_PHONE);
		shopifyAddressUpdateRequest.setProvince(SOME_PROVINCE);
		shopifyAddressUpdateRequest.setProvinceCode(SOME_PROVINCE_CODE);

		assertEquals(SOME_ADDRESS_1, shopifyAddressUpdateRequest.getAddress1());
		assertEquals(SOME_ADDRESS_2, shopifyAddressUpdateRequest.getAddress2());
		assertEquals(SOME_CITY, shopifyAddressUpdateRequest.getCity());
		assertEquals(SOME_COMPANY, shopifyAddressUpdateRequest.getCompany());
		assertEquals(SOME_COUNTRY, shopifyAddressUpdateRequest.getCountry());
		assertEquals(SOME_COUNTRY_CODE, shopifyAddressUpdateRequest.getCountryCode());
		assertEquals(SOME_FIRST_NAME, shopifyAddressUpdateRequest.getFirstName());
		assertEquals(SOME_LAST_NAME, shopifyAddressUpdateRequest.getLastname());
		assertEquals(SOME_LATITUDE, shopifyAddressUpdateRequest.getLatitude());
		assertEquals(SOME_LONGITUDE, shopifyAddressUpdateRequest.getLongitude());
		assertEquals(SOME_PHONE, shopifyAddressUpdateRequest.getPhone());
		assertEquals(SOME_PROVINCE, shopifyAddressUpdateRequest.getProvince());
		assertEquals(SOME_PROVINCE_CODE, shopifyAddressUpdateRequest.getProvinceCode());

	}
}
