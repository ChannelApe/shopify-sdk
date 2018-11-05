package com.shopify;

import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.clientdriver.capture.JsonBodyCapture;
import com.github.restdriver.clientdriver.capture.StringBodyCapture;
import com.shopify.exceptions.ShopifyClientException;
import com.shopify.exceptions.ShopifyErrorResponseException;
import com.shopify.model.Metafield;
import com.shopify.model.MetafieldValueType;
import com.shopify.model.MetafieldsRoot;
import com.shopify.model.Shop;
import com.shopify.model.ShopifyAccessTokenRoot;
import com.shopify.model.ShopifyAddress;
import com.shopify.model.ShopifyCustomer;
import com.shopify.model.ShopifyFulfillment;
import com.shopify.model.ShopifyFulfillmentRoot;
import com.shopify.model.ShopifyInventoryLevel;
import com.shopify.model.ShopifyInventoryLevelRoot;
import com.shopify.model.ShopifyLineItem;
import com.shopify.model.ShopifyLocation;
import com.shopify.model.ShopifyLocationsRoot;
import com.shopify.model.ShopifyOrder;
import com.shopify.model.ShopifyOrderCreationRequest;
import com.shopify.model.ShopifyOrderRoot;
import com.shopify.model.ShopifyOrdersRoot;
import com.shopify.model.ShopifyRefund;
import com.shopify.model.ShopifyRefundCreationRequest;
import com.shopify.model.ShopifyRefundLineItem;
import com.shopify.model.ShopifyRefundRoot;
import com.shopify.model.ShopifyRefundShippingDetails;
import com.shopify.model.ShopifyShippingLine;
import com.shopify.model.ShopifyShop;
import com.shopify.model.ShopifyTransaction;
import com.shopify.model.ShopifyVariant;
import com.shopify.model.ShopifyVariantRoot;
import com.shopify.model.ShopifyVariantUpdateRequest;

@RunWith(MockitoJUnitRunner.class)
public class ShopifySdkTest {

	private static final DateTime SOME_DATE_TIME = new DateTime();
	private static final ShopifyCustomer SOME_CUSTOMER = new ShopifyCustomer();
	private static final String FORWARD_SLASH = "/";
	private final String accessToken = "09382489782734897289374829374";

	private ShopifySdk shopifySdk;

	@Rule
	public ClientDriverRule driver = new ClientDriverRule();

	@Before
	public void setUp() throws JAXBException {
		final String subdomainUrl = driver.getBaseUrl();

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.SHOP).toString();

		final ShopifyShop shopifyShop = new ShopifyShop();

		final Shop shop = new Shop();
		shop.setId("1");
		shop.setName("Some Cool Shopify Store");
		shopifyShop.setShop(shop);
		final String expectedResponseBodyString = getJsonString(ShopifyShop.class, shopifyShop);

		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(Status.OK.getStatusCode()))
				.anyTimes();

		shopifySdk = ShopifySdk.newBuilder().withApiUrl(subdomainUrl).withAccessToken(accessToken).build();

	}

	@Test
	public void givenSomeClientCredentialsWhenCallinglToTheShopifyApiThenExpectAccessTokenToBeGeneratedAfterCallIsMade()
			throws JAXBException {

		final String subdomainUrl = driver.getBaseUrl();

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.OAUTH)
				.append(FORWARD_SLASH).append(ShopifySdk.ACCESS_TOKEN).toString();

		final ShopifyAccessTokenRoot shopifyAccessTokenRoot = new ShopifyAccessTokenRoot();
		shopifyAccessTokenRoot.setAccessToken("897123871827381723");
		final String expectedResponseBodyString = getJsonString(ShopifyAccessTokenRoot.class, shopifyAccessTokenRoot);

		final StringBodyCapture actualStringRequestBody = new StringBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withMethod(Method.POST).withAnyParams()
						.capturingBodyIn(actualStringRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(Status.OK.getStatusCode()));

		shopifySdk = ShopifySdk.newBuilder().withApiUrl(subdomainUrl).withClientId("some-client-id")
				.withClientSecret("some-client-secret").withAuthorizationToken("3892742738482").build();

		final String expectedShopPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.SHOP).toString();

		final ShopifyShop shopifyShop = new ShopifyShop();

		final Shop shop = new Shop();
		shop.setId("4");
		shop.setName("Some Generated Access Token Cool Shopify Store");
		shopifyShop.setShop(shop);
		final String expectedShopResponseBodyString = getJsonString(ShopifyShop.class, shopifyShop);

		driver.addExpectation(
				onRequestTo(expectedShopPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, "897123871827381723")
						.withMethod(Method.GET),
				giveResponse(expectedShopResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(Status.OK.getStatusCode()))
				.anyTimes();

		assertNull(shopifySdk.getAccessToken());
		final ShopifyShop actualShop = shopifySdk.getShop();
		assertEquals("897123871827381723", shopifySdk.getAccessToken());
		assertEquals("4", actualShop.getShop().getId());
		assertEquals("Some Generated Access Token Cool Shopify Store", actualShop.getShop().getName());

	}

	@Test(expected = ShopifyClientException.class)
	public void givenSomeClientCredentialsAndUnexpectedStatusWhenCallingToTheShopifyApiThenExpectExpectShopifyClientException()
			throws JAXBException {

		final String subdomainUrl = driver.getBaseUrl();

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.OAUTH)
				.append(FORWARD_SLASH).append(ShopifySdk.ACCESS_TOKEN).toString();

		final ShopifyAccessTokenRoot shopifyAccessTokenRoot = new ShopifyAccessTokenRoot();
		shopifyAccessTokenRoot.setAccessToken("897123871827381723");
		final String expectedResponseBodyString = getJsonString(ShopifyAccessTokenRoot.class, shopifyAccessTokenRoot);

		final StringBodyCapture actualStringRequestBody = new StringBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withMethod(Method.POST).withAnyParams()
						.capturingBodyIn(actualStringRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(Status.UNAUTHORIZED.getStatusCode()));

		shopifySdk = ShopifySdk.newBuilder().withApiUrl(subdomainUrl).withClientId("some-client-id")
				.withClientSecret("some-client-secret").withAuthorizationToken("3892742738482").build();

		assertNull(shopifySdk.getAccessToken());
		shopifySdk.getShop();

	}

	@Test
	public void givenSomePageAndCreatedAtMinAndCreatedAtMaxOrdersWhenRetrievingOrdersThenRetrieveOrdersWithCorrectValues()
			throws JAXBException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS).toString();
		final DateTime maximumCreationDate = new DateTime();
		final ShopifyOrdersRoot shopifyOrdersRoot = new ShopifyOrdersRoot();

		final ShopifyOrder shopifyOrder1 = new ShopifyOrder();
		shopifyOrder1.setId("someId");
		shopifyOrder1.setEmail("ryan.kazokas@gmail.com");
		shopifyOrder1.setCustomer(SOME_CUSTOMER);

		final ShopifyLineItem shopifyLineItem1 = new ShopifyLineItem();
		shopifyLineItem1.setId("1234565");
		shopifyLineItem1.setSku("847289374");
		shopifyLineItem1.setName("Really Cool Product");
		shopifyOrder1.setLineItems(Arrays.asList(shopifyLineItem1));

		final ShopifyFulfillment shopifyFulfillment = new ShopifyFulfillment();
		shopifyFulfillment.setCreatedAt(SOME_DATE_TIME);
		shopifyFulfillment.setId("somelineitemid1");
		shopifyFulfillment.setLineItems(Arrays.asList(shopifyLineItem1));
		shopifyFulfillment.setTrackingUrl(null);
		shopifyFulfillment.setTrackingUrls(new LinkedList<>());
		shopifyOrder1.setFulfillments(Arrays.asList(shopifyFulfillment));

		final ShopifyRefund shopifyRefund1 = new ShopifyRefund();
		shopifyRefund1.setCreatedAt(SOME_DATE_TIME);
		shopifyRefund1.setProcessedAt(SOME_DATE_TIME);
		shopifyRefund1.setId("87128371823");
		shopifyRefund1.setNote("Customer didn't want");
		shopifyRefund1.setOrderId("someId");
		shopifyRefund1.setRestock(false);
		shopifyRefund1.setUserId(null);

		final ShopifyRefundLineItem shopifyRefundedLineItem = new ShopifyRefundLineItem();
		shopifyRefundedLineItem.setId("213881723");
		shopifyRefundedLineItem.setQuantity(3L);
		shopifyRefundedLineItem.setLineItemId("87482734");
		shopifyRefundedLineItem.setRestockType("restock");
		shopifyRefundedLineItem.setSubtotal(new BigDecimal(4772.112));
		shopifyRefundedLineItem.setTotalTax(new BigDecimal(832.11));
		shopifyRefundedLineItem.setLocationId("783487234");

		shopifyRefundedLineItem.setLineItem(shopifyLineItem1);

		shopifyRefund1.setRefundLineItems(Arrays.asList(shopifyRefundedLineItem));

		shopifyOrder1.setRefunds(Arrays.asList(shopifyRefund1));
		shopifyOrdersRoot.setOrders(Arrays.asList(shopifyOrder1));

		final String expectedResponseBodyString = getJsonString(ShopifyOrdersRoot.class, shopifyOrdersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final DateTime minimumCreationDateTime = SOME_DATE_TIME;

		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withParam("status", "any").withParam("created_at_min", minimumCreationDateTime.toString())
						.withParam("created_at_max", maximumCreationDate.toString()).withParam("page", "1")
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final List<ShopifyOrder> shopifyOrders = shopifySdk.getOrders(minimumCreationDateTime, maximumCreationDate, 1);

		assertEquals(shopifyOrder1.getId(), shopifyOrders.get(0).getId());
		assertEquals(shopifyOrder1.getEmail(), shopifyOrders.get(0).getEmail());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getId(),
				shopifyOrders.get(0).getFulfillments().get(0).getId());
		assertTrue(shopifyOrder1.getFulfillments().get(0).getCreatedAt()
				.compareTo(shopifyOrders.get(0).getFulfillments().get(0).getCreatedAt()) == 0);
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrl(),
				shopifyOrders.get(0).getFulfillments().get(0).getTrackingUrl());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrls(),
				shopifyOrders.get(0).getFulfillments().get(0).getTrackingUrls());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				shopifyOrders.get(0).getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				shopifyOrders.get(0).getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getSku(),
				shopifyOrders.get(0).getFulfillments().get(0).getLineItems().get(0).getSku());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getName(),
				shopifyOrders.get(0).getFulfillments().get(0).getLineItems().get(0).getName());

		assertEquals(shopifyOrder1.getRefunds().size(), shopifyOrders.get(0).getRefunds().size());
		assertTrue(shopifyOrder1.getRefunds().get(0).getCreatedAt()
				.compareTo(shopifyOrders.get(0).getRefunds().get(0).getCreatedAt()) == 0);
		assertEquals(shopifyOrder1.getRefunds().get(0).getId(), shopifyOrders.get(0).getRefunds().get(0).getId());
		assertEquals(shopifyOrder1.getRefunds().get(0).getNote(), shopifyOrders.get(0).getRefunds().get(0).getNote());
		assertEquals(shopifyOrder1.getRefunds().get(0).getOrderId(),
				shopifyOrders.get(0).getRefunds().get(0).getOrderId());
		assertTrue(shopifyOrder1.getRefunds().get(0).getProcessedAt()
				.compareTo(shopifyOrders.get(0).getRefunds().get(0).getProcessedAt()) == 0);

		assertEquals(shopifyOrder1.getRefunds().get(0).getRefundLineItems().get(0).getLineItemId(),
				shopifyOrders.get(0).getRefunds().get(0).getRefundLineItems().get(0).getLineItemId());
		assertEquals(shopifyOrder1.getRefunds().get(0).getRefundLineItems().get(0).getLocationId(),
				shopifyOrders.get(0).getRefunds().get(0).getRefundLineItems().get(0).getLocationId());
		assertEquals(shopifyOrder1.getRefunds().get(0).getRefundLineItems().get(0).getQuantity(),
				shopifyOrders.get(0).getRefunds().get(0).getRefundLineItems().get(0).getQuantity());
		assertEquals(shopifyOrder1.getRefunds().get(0).getRefundLineItems().get(0).getRestockType(),
				shopifyOrders.get(0).getRefunds().get(0).getRefundLineItems().get(0).getRestockType());
		assertEquals(shopifyOrder1.getRefunds().get(0).getRefundLineItems().get(0).getSubtotal(),
				shopifyOrders.get(0).getRefunds().get(0).getRefundLineItems().get(0).getSubtotal());
		assertEquals(shopifyOrder1.getRefunds().get(0).getRefundLineItems().get(0).getTotalTax(),
				shopifyOrders.get(0).getRefunds().get(0).getRefundLineItems().get(0).getTotalTax());

		assertEquals(shopifyLineItem1.getSku(),
				shopifyOrders.get(0).getRefunds().get(0).getRefundLineItems().get(0).getLineItem().getSku());

	}

	@Test
	public void givenSomePageAndCreatedAtMinAndCreatedAtMaxOrdersAndAppIdWhenRetrievingOrdersThenRetrieveOrdersWithCorrectValues()
			throws JAXBException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS).toString();
		final DateTime maximumCreationDate = new DateTime();
		final ShopifyOrdersRoot shopifyOrdersRoot = new ShopifyOrdersRoot();

		final ShopifyOrder shopifyOrder1 = new ShopifyOrder();
		shopifyOrder1.setId("someId");
		shopifyOrder1.setEmail("ryan.kazokas@gmail.com");
		shopifyOrder1.setCustomer(SOME_CUSTOMER);

		final ShopifyLineItem shopifyLineItem1 = new ShopifyLineItem();
		shopifyLineItem1.setId("1234565");
		shopifyLineItem1.setSku("847289374");
		shopifyLineItem1.setName("Really Cool Product");
		shopifyOrder1.setLineItems(Arrays.asList(shopifyLineItem1));

		final ShopifyFulfillment shopifyFulfillment = new ShopifyFulfillment();
		shopifyFulfillment.setCreatedAt(SOME_DATE_TIME);
		shopifyFulfillment.setId("somelineitemid1");
		shopifyFulfillment.setLineItems(Arrays.asList(shopifyLineItem1));
		shopifyFulfillment.setTrackingUrl(null);
		shopifyFulfillment.setTrackingUrls(new LinkedList<>());
		shopifyOrder1.setFulfillments(Arrays.asList(shopifyFulfillment));
		shopifyOrdersRoot.setOrders(Arrays.asList(shopifyOrder1));

		final String expectedResponseBodyString = getJsonString(ShopifyOrdersRoot.class, shopifyOrdersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final DateTime minimumCreationDateTime = SOME_DATE_TIME;
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withParam(ShopifySdk.STATUS_QUERY_PARAMETER, ShopifySdk.ANY_STATUSES)
						.withParam(ShopifySdk.CREATED_AT_MIN_QUERY_PARAMETER, minimumCreationDateTime.toString())
						.withParam(ShopifySdk.CREATED_AT_MAX_QUERY_PARAMETER, maximumCreationDate.toString())
						.withParam(ShopifySdk.ATTRIBUTION_APP_ID_QUERY_PARAMETER, "current")
						.withParam(ShopifySdk.PAGE_QUERY_PARAMETER, "1").withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final List<ShopifyOrder> shopifyOrders = shopifySdk.getOrders(minimumCreationDateTime, maximumCreationDate, 1,
				"current");

		assertEquals(shopifyOrder1.getId(), shopifyOrders.get(0).getId());
		assertEquals(shopifyOrder1.getEmail(), shopifyOrders.get(0).getEmail());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getId(),
				shopifyOrders.get(0).getFulfillments().get(0).getId());
		assertTrue(shopifyOrder1.getFulfillments().get(0).getCreatedAt()
				.compareTo(shopifyOrders.get(0).getFulfillments().get(0).getCreatedAt()) == 0);
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrl(),
				shopifyOrders.get(0).getFulfillments().get(0).getTrackingUrl());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrls(),
				shopifyOrders.get(0).getFulfillments().get(0).getTrackingUrls());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				shopifyOrders.get(0).getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				shopifyOrders.get(0).getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getSku(),
				shopifyOrders.get(0).getFulfillments().get(0).getLineItems().get(0).getSku());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getName(),
				shopifyOrders.get(0).getFulfillments().get(0).getLineItems().get(0).getName());
	}

	@Test
	public void givenSomePageAndCreatedAtMinOrdersWhenRetrievingOrdersThenRetrieveOrdersWithCorrectValues()
			throws JAXBException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS).toString();

		final ShopifyOrdersRoot shopifyOrdersRoot = new ShopifyOrdersRoot();

		final ShopifyOrder shopifyOrder1 = new ShopifyOrder();
		shopifyOrder1.setId("someId");
		shopifyOrder1.setEmail("ryan.kazokas@gmail.com");
		shopifyOrder1.setCustomer(SOME_CUSTOMER);

		final ShopifyLineItem shopifyLineItem1 = new ShopifyLineItem();
		shopifyLineItem1.setId("1234565");
		shopifyLineItem1.setSku("847289374");
		shopifyLineItem1.setName("Really Cool Product");
		shopifyOrder1.setLineItems(Arrays.asList(shopifyLineItem1));

		final ShopifyFulfillment shopifyFulfillment = new ShopifyFulfillment();
		shopifyFulfillment.setCreatedAt(SOME_DATE_TIME);
		shopifyFulfillment.setId("somelineitemid1");
		shopifyFulfillment.setLineItems(Arrays.asList(shopifyLineItem1));
		shopifyFulfillment.setTrackingUrl(null);
		shopifyFulfillment.setTrackingUrls(new LinkedList<>());
		shopifyOrder1.setFulfillments(Arrays.asList(shopifyFulfillment));
		shopifyOrdersRoot.setOrders(Arrays.asList(shopifyOrder1));

		final String expectedResponseBodyString = getJsonString(ShopifyOrdersRoot.class, shopifyOrdersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final DateTime minimumCreationDateTime = SOME_DATE_TIME;
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withParam("status", "any").withParam("created_at_min", minimumCreationDateTime.toString())
						.withParam("page", "1").withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final List<ShopifyOrder> shopifyOrders = shopifySdk.getOrders(minimumCreationDateTime, 1);

		assertEquals(shopifyOrder1.getId(), shopifyOrders.get(0).getId());
		assertEquals(shopifyOrder1.getEmail(), shopifyOrders.get(0).getEmail());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getId(),
				shopifyOrders.get(0).getFulfillments().get(0).getId());
		assertTrue(shopifyOrder1.getFulfillments().get(0).getCreatedAt()
				.compareTo(shopifyOrders.get(0).getFulfillments().get(0).getCreatedAt()) == 0);
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrl(),
				shopifyOrders.get(0).getFulfillments().get(0).getTrackingUrl());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrls(),
				shopifyOrders.get(0).getFulfillments().get(0).getTrackingUrls());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				shopifyOrders.get(0).getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				shopifyOrders.get(0).getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getSku(),
				shopifyOrders.get(0).getFulfillments().get(0).getLineItems().get(0).getSku());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getName(),
				shopifyOrders.get(0).getFulfillments().get(0).getLineItems().get(0).getName());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainWhenGettingShopifyLocationThenReturnShopifyLocations()
			throws JAXBException {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.LOCATIONS)
				.append(ShopifySdk.JSON).toString();
		final ShopifyLocationsRoot shopifyLocationsRoot = new ShopifyLocationsRoot();
		final ShopifyLocation shopifyLocation1 = buildShopifyLocation("Some address1", "Some address2", "78237482374",
				"Warehouse 1");
		final ShopifyLocation shopifyLocation2 = buildShopifyLocation("Some address3", "Some address4", "987897984",
				"Warehouse 2");
		final List<ShopifyLocation> shopifyLocations = Arrays.asList(shopifyLocation1, shopifyLocation2);
		shopifyLocationsRoot.setLocations(shopifyLocations);

		final String expectedResponseBodyString = getJsonString(ShopifyLocationsRoot.class, shopifyLocationsRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final List<ShopifyLocation> actualShopifyLocations = shopifySdk.getLocations();

		assertEquals(2, actualShopifyLocations.size());
		assertEquals(shopifyLocation1.getAddress1(), actualShopifyLocations.get(0).getAddress1());
		assertEquals(shopifyLocation1.getAddress2(), actualShopifyLocations.get(0).getAddress2());
		assertEquals(shopifyLocation1.getCity(), actualShopifyLocations.get(0).getCity());
		assertEquals(shopifyLocation1.getProvince(), actualShopifyLocations.get(0).getProvince());
		assertEquals(shopifyLocation1.getZip(), actualShopifyLocations.get(0).getZip());
		assertEquals(shopifyLocation1.getCountry(), actualShopifyLocations.get(0).getCountry());
		assertEquals(shopifyLocation1.getCountryCode(), actualShopifyLocations.get(0).getCountryCode());
		assertEquals(shopifyLocation1.getCountryName(), actualShopifyLocations.get(0).getCountryName());
		assertEquals(shopifyLocation1.getProvinceCode(), actualShopifyLocations.get(0).getProvinceCode());

		assertEquals(shopifyLocation2.getAddress1(), actualShopifyLocations.get(1).getAddress1());
		assertEquals(shopifyLocation2.getAddress2(), actualShopifyLocations.get(1).getAddress2());
		assertEquals(shopifyLocation2.getCity(), actualShopifyLocations.get(1).getCity());
		assertEquals(shopifyLocation2.getProvince(), actualShopifyLocations.get(1).getProvince());
		assertEquals(shopifyLocation2.getZip(), actualShopifyLocations.get(1).getZip());
		assertEquals(shopifyLocation2.getCountry(), actualShopifyLocations.get(1).getCountry());
		assertEquals(shopifyLocation2.getCountryCode(), actualShopifyLocations.get(1).getCountryCode());
		assertEquals(shopifyLocation2.getCountryName(), actualShopifyLocations.get(1).getCountryName());
		assertEquals(shopifyLocation2.getProvinceCode(), actualShopifyLocations.get(1).getProvinceCode());
	}

	@Test(expected = ShopifyErrorResponseException.class)
	public void givenSomeExceptionIsThrownWhenGettingShopifyLocationsThenExpectShopifyClientException()
			throws JAXBException {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.LOCATIONS)
				.append(ShopifySdk.JSON).toString();
		final ShopifyLocationsRoot shopifyLocationsRoot = new ShopifyLocationsRoot();
		final ShopifyLocation shopifyLocation1 = buildShopifyLocation("Some address1", "Some address2", "78237482374",
				"Warehouse 1");
		final ShopifyLocation shopifyLocation2 = buildShopifyLocation("Some address3", "Some address4", "987897984",
				"Warehouse 2");
		final List<ShopifyLocation> shopifyLocations = Arrays.asList(shopifyLocation1, shopifyLocation2);
		shopifyLocationsRoot.setLocations(shopifyLocations);

		final String expectedResponseBodyString = getJsonString(ShopifyLocationsRoot.class, shopifyLocationsRoot);

		final Status expectedStatus = Status.BAD_REQUEST;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		shopifySdk.getLocations();
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestAndUpdatingInventoryLevelThenUpdateAndReturnInventoryLevel()
			throws JAXBException {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.INVENTORY_LEVELS)
				.append("/").append(ShopifySdk.SET).toString();
		final ShopifyInventoryLevelRoot shopifyInventoryLevelRoot = new ShopifyInventoryLevelRoot();
		final ShopifyInventoryLevel shopifyInventoryLevel = new ShopifyInventoryLevel();
		shopifyInventoryLevel.setAvailable(123L);
		shopifyInventoryLevel.setInventoryItemId("123123");
		shopifyInventoryLevel.setLocationId("736472634");
		shopifyInventoryLevelRoot.setInventoryLevel(shopifyInventoryLevel);

		final String expectedResponseBodyString = getJsonString(ShopifyInventoryLevelRoot.class,
				shopifyInventoryLevelRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();

		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.POST).capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyInventoryLevel actualShopifyInventoryLevel = shopifySdk.updateInventoryLevel("123123", "736472634",
				123L);
		assertEquals(123L, actualRequestBody.getContent().get("available").asLong());
		assertEquals("123123", actualRequestBody.getContent().get("inventory_item_id").asText());
		assertEquals("736472634", actualRequestBody.getContent().get("location_id").asText());

		assertEquals(shopifyInventoryLevel.getAvailable(), actualShopifyInventoryLevel.getAvailable());
		assertEquals(shopifyInventoryLevel.getLocationId(), actualShopifyInventoryLevel.getLocationId());
		assertEquals(shopifyInventoryLevel.getInventoryItemId(), actualShopifyInventoryLevel.getInventoryItemId());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestWhenRetrievingOrderMetafieldsThenReturnOrderMetafields()
			throws JAXBException {

		final Metafield metafield = new Metafield();
		metafield.setKey("channelape_order_id");
		metafield.setValue("8fb0fb40-ab18-439e-bc6e-394b63ff1819");
		metafield.setNamespace("channelape");
		metafield.setOwnerId("1234");
		metafield.setValueType(MetafieldValueType.STRING);
		metafield.setOwnerResource("order");

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS).append("/1234/")
				.append(ShopifySdk.METAFIELDS).toString();
		final MetafieldsRoot metafieldsRoot = new MetafieldsRoot();
		metafieldsRoot.setMetafields(Arrays.asList(metafield));

		final String expectedResponseBodyString = getJsonString(MetafieldsRoot.class, metafieldsRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();

		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final List<Metafield> actualMetafields = shopifySdk.getOrderMetafields("1234");
		assertNotNull(actualMetafields);
		assertEquals(1, actualMetafields.size());
		assertEquals(metafield.getKey(), actualMetafields.get(0).getKey());
		assertEquals(metafield.getValue(), actualMetafields.get(0).getValue());
		assertEquals(metafield.getValueType(), actualMetafields.get(0).getValueType());
		assertEquals(metafield.getNamespace(), actualMetafields.get(0).getNamespace());
		assertEquals(metafield.getOwnerId(), actualMetafields.get(0).getOwnerId());
		assertEquals(metafield.getOwnerResource(), actualMetafields.get(0).getOwnerResource());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestWhenCancelingFulfillmentsThenCancelFulfillments()
			throws JAXBException {

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId("some_line_item_id");
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS).append("/1234/")
				.append(ShopifySdk.FULFILLMENTS).append("/4567/").append(ShopifySdk.CANCEL).toString();
		final ShopifyFulfillment currentFulfillment = buildShopifyFulfillment(lineItem);
		final ShopifyFulfillmentRoot shopifyFulfillmentRoot = new ShopifyFulfillmentRoot();
		shopifyFulfillmentRoot.setFulfillment(currentFulfillment);

		final String expectedResponseBodyString = getJsonString(ShopifyFulfillmentRoot.class, shopifyFulfillmentRoot);

		final Status expectedStatus = Status.CREATED;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.POST).capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyFulfillment actualShopifyFulfillment = shopifySdk.cancelFulfillment("1234", "4567");

		assertEquals(false, actualRequestBody.getContent().get("notify_customer").asBoolean());
		assertEquals(0, actualRequestBody.getContent().get("line_items").size());
		assertEquals(0, actualRequestBody.getContent().get("tracking_urls").size());

		assertValidFulfillment(currentFulfillment, actualShopifyFulfillment);
	}

	@Test(expected = ShopifyErrorResponseException.class)
	public void givenSomeInvalidStatusWhenUpdatingInventoryLevelThenExpectShopifyErrorResponseException()
			throws JAXBException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.INVENTORY_LEVELS)
				.append("/").append(ShopifySdk.SET).toString();
		final ShopifyInventoryLevelRoot shopifyInventoryLevelRoot = new ShopifyInventoryLevelRoot();
		final ShopifyInventoryLevel shopifyInventoryLevel = new ShopifyInventoryLevel();
		shopifyInventoryLevel.setAvailable(123L);
		shopifyInventoryLevel.setInventoryItemId("736472634");
		shopifyInventoryLevel.setLocationId("123123");
		shopifyInventoryLevelRoot.setInventoryLevel(shopifyInventoryLevel);

		final String expectedResponseBodyString = getJsonString(ShopifyInventoryLevelRoot.class,
				shopifyInventoryLevelRoot);

		final Status expectedStatus = Status.BAD_REQUEST;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.POST),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		shopifySdk.updateInventoryLevel("123123", "736472634", 123L);
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestWhenUpdatingVariantThenUpdateAndReturnVariant()
			throws JAXBException {
		final ShopifyVariant currentShopifyVariant = new ShopifyVariant();
		currentShopifyVariant.setId("98746868985974");
		currentShopifyVariant.setTitle("UK 8");
		currentShopifyVariant.setPrice(new BigDecimal("10.00"));
		currentShopifyVariant.setInventoryQuantity(1337);
		currentShopifyVariant.setBarcode("897563254789");

		final String newBarcode = "459876235897";
		final ShopifyVariantUpdateRequest shopifyVariantUpdateRequest = ShopifyVariantUpdateRequest.newBuilder()
				.withCurrentShopifyVariant(currentShopifyVariant).withSamePrice().withSameCompareAtPrice().withSameSku()
				.withBarcode(newBarcode).withSameWeight().withAvailable(20).withSameFirstOption().withSameSecondOption()
				.withSameThirdOption().withSameImage().withSameInventoryManagement().withSameInventoryPolicy()
				.withSameFulfillmentService().withSameRequiresShipping().withSameTaxable().withSameInventoryItemId()
				.build();

		final ShopifyVariantRoot shopifyVariantRoot = new ShopifyVariantRoot();
		shopifyVariantRoot.setVariant(shopifyVariantUpdateRequest.getRequest());

		final String expectedResponseBodyString = "{\r\n" + "    \"variant\": {\r\n"
				+ "        \"id\": 98746868985974,\r\n" + "        \"product_id\": 2180984635510,\r\n"
				+ "        \"title\": \"UK 8\",\r\n" + "        \"price\": \"10.00\",\r\n"
				+ "        \"sku\": \"TL1MKCC090\",\r\n" + "        \"position\": 2,\r\n"
				+ "        \"inventory_policy\": \"deny\",\r\n" + "        \"compare_at_price\": null,\r\n"
				+ "        \"fulfillment_service\": \"manual\",\r\n"
				+ "        \"inventory_management\": \"shopify\",\r\n" + "        \"option1\": \"UK 8\",\r\n"
				+ "        \"option2\": null,\r\n" + "        \"option3\": null,\r\n"
				+ "        \"created_at\": \"2018-09-28T22:41:26+01:00\",\r\n"
				+ "        \"updated_at\": \"2018-10-09T19:55:53+01:00\",\r\n" + "        \"taxable\": true,\r\n"
				+ "        \"barcode\": \"459876235897\",\r\n" + "        \"grams\": 0,\r\n"
				+ "        \"image_id\": null,\r\n" + "        \"inventory_quantity\": 1337,\r\n"
				+ "        \"weight\": 0,\r\n" + "        \"weight_unit\": \"lb\",\r\n"
				+ "        \"inventory_item_id\": 20119411261558,\r\n" + "        \"tax_code\": \"\",\r\n"
				+ "        \"old_inventory_quantity\": 2,\r\n" + "        \"requires_shipping\": true,\r\n"
				+ "        \"admin_graphql_api_id\": \"gid://shopify/ProductVariant/19746868985974\"\r\n" + "    }\r\n"
				+ "}";

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.VARIANTS)
				.append(FORWARD_SLASH).append(shopifyVariantUpdateRequest.getRequest().getId()).toString();
		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final StringBodyCapture stringBodyCapture = new StringBodyCapture();

		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken).withMethod(Method.PUT)
						.capturingBodyIn(stringBodyCapture),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyVariant actualShopifyVariant = shopifySdk.updateVariant(shopifyVariantUpdateRequest);

		final String expectedRequestBodyString = "{\"variant\":{\"id\":\"98746868985974\",\"title\":\"UK 8\",\"price\":10.00,\"barcode\":\"459876235897\",\"position\":0,\"grams\":0,\"requires_shipping\":false,\"taxable\":false}}";
		assertEquals(expectedRequestBodyString, stringBodyCapture.getContent());

		assertEquals(shopifyVariantUpdateRequest.getRequest().getId(), actualShopifyVariant.getId());
		assertEquals(shopifyVariantUpdateRequest.getRequest().getTitle(), actualShopifyVariant.getTitle());
		assertEquals(shopifyVariantUpdateRequest.getRequest().getPrice(), actualShopifyVariant.getPrice());
		assertEquals(shopifyVariantUpdateRequest.getRequest().getInventoryQuantity(),
				actualShopifyVariant.getInventoryQuantity());
		assertEquals(shopifyVariantUpdateRequest.getRequest().getBarcode(), actualShopifyVariant.getBarcode());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestAndCreatingOrderThenCreateAndReturn()
			throws Exception {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append("orders").toString();
		final ShopifyOrderRoot shopifyOrderRoot = new ShopifyOrderRoot();
		final ShopifyOrder shopifyOrder = new ShopifyOrder();
		final ShopifyCustomer shopifyCustomer = new ShopifyCustomer();
		shopifyCustomer.setId("Customer-Id");
		shopifyCustomer.setFirstName("Ryan");
		shopifyCustomer.setLastname("Kazokas");

		final ShopifyAddress address = new ShopifyAddress();
		address.setAddress1("224 Wyoming Ave");
		address.setAddress2("Suite 100");
		address.setCompany("channelape");
		address.setFirstName("Ryan Kazokas");
		address.setLastname("Kazokas");
		address.setProvince("PEnnsylvania");
		address.setProvinceCode("PA");
		address.setZip("18503");
		address.setCountry("US");
		address.setCountryCode("USA");
		shopifyCustomer.setEmail("rkazokas@channelape.com");
		shopifyCustomer.setPhone("87234287384723");
		shopifyCustomer.setFirstName("Ryan");
		shopifyCustomer.setLastname("Kazokas");

		final ShopifyLineItem shopifyLineItem1 = new ShopifyLineItem();
		shopifyLineItem1.setQuantity(3);
		shopifyLineItem1.setVariantId("4123123");
		final ShopifyLineItem shopifyLineItem2 = new ShopifyLineItem();
		shopifyLineItem2.setQuantity(4);
		shopifyLineItem2.setVariantId("5123123");
		final List<ShopifyLineItem> shopifyLineItems = Arrays.asList(shopifyLineItem1, shopifyLineItem2);
		final DateTime processedAt = new DateTime(DateTimeZone.UTC);

		shopifyOrder.setName("123456");
		shopifyOrder.setId("123");
		shopifyOrder.setCustomer(shopifyCustomer);
		shopifyOrder.setLineItems(shopifyLineItems);
		shopifyOrder.setProcessedAt(processedAt);
		shopifyOrder.setShippingAddress(address);
		shopifyOrder.setBillingAddress(address);
		final ShopifyShippingLine shippingLine1 = new ShopifyShippingLine();
		shippingLine1.setCode("Test");
		shippingLine1.setSource("Testing Source");
		shippingLine1.setTitle("Testing Title");
		shippingLine1.setPrice(new BigDecimal(4.33));
		shopifyOrder.setShippingLines(Arrays.asList(shippingLine1));

		shopifyOrderRoot.setOrder(shopifyOrder);

		final String expectedResponseBodyString = getJsonString(ShopifyOrderRoot.class, shopifyOrderRoot);

		final Status expectedStatus = Status.CREATED;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.POST)
						.capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyOrderCreationRequest shopifyOrderRequest = ShopifyOrderCreationRequest.newBuilder()
				.withProcessedAt(processedAt).withName("123456").withCustomer(shopifyCustomer)
				.withLineItems(shopifyLineItems).withShippingAddress(address).withBillingAddress(address)
				.withMetafields(Collections.emptyList()).withShippingLines(Arrays.asList(shippingLine1)).build();
		final ShopifyOrder actualShopifyOrder = shopifySdk.createOrder(shopifyOrderRequest);

		assertEquals("123456", actualRequestBody.getContent().get("order").get("name").asText());
		assertEquals(0, actualRequestBody.getContent().get("order").get("number").asInt());
		assertEquals(false, actualRequestBody.getContent().get("order").get("taxes_included").asBoolean());
		assertEquals(false, actualRequestBody.getContent().get("order").get("buyer_accepts_marketing").asBoolean());
		assertEquals("4123123",
				actualRequestBody.getContent().get("order").get("line_items").path(0).get("variant_id").asText());
		assertEquals(3, actualRequestBody.getContent().get("order").get("line_items").path(0).get("quantity").asInt());
		assertEquals(false, actualRequestBody.getContent().get("order").get("line_items").path(0)
				.get("requires_shipping").asBoolean());
		assertEquals(0, actualRequestBody.getContent().get("order").get("line_items").path(0).get("grams").asInt());
		assertEquals(false,
				actualRequestBody.getContent().get("order").get("line_items").path(0).get("taxable").asBoolean());
		assertEquals(false,
				actualRequestBody.getContent().get("order").get("line_items").path(0).get("gift_card").asBoolean());
		assertEquals(0, actualRequestBody.getContent().get("order").get("line_items").path(0)
				.get("fulfillable_quantity").asInt());

		assertEquals("5123123",
				actualRequestBody.getContent().get("order").get("line_items").path(1).get("variant_id").asText());
		assertEquals(4, actualRequestBody.getContent().get("order").get("line_items").path(1).get("quantity").asInt());
		assertEquals(0, actualRequestBody.getContent().get("order").get("line_items").path(1).get("grams").asInt());
		assertEquals(false, actualRequestBody.getContent().get("order").get("line_items").path(1)
				.get("requires_shipping").asBoolean());
		assertEquals(false,
				actualRequestBody.getContent().get("order").get("line_items").path(1).get("taxable").asBoolean());
		assertEquals(false,
				actualRequestBody.getContent().get("order").get("line_items").path(1).get("gift_card").asBoolean());
		assertEquals(0, actualRequestBody.getContent().get("order").get("line_items").path(1)
				.get("fulfillable_quantity").asInt());

		assertEquals("Customer-Id", actualRequestBody.getContent().get("order").get("customer").get("id").asText());
		assertEquals("Ryan", actualRequestBody.getContent().get("order").get("customer").get("first_name").asText());
		assertEquals("Kazokas", actualRequestBody.getContent().get("order").get("customer").get("last_name").asText());
		assertEquals("rkazokas@channelape.com",
				actualRequestBody.getContent().get("order").get("customer").get("email").asText());
		assertEquals(false,
				actualRequestBody.getContent().get("order").get("customer").get("accepts_marketing").asBoolean());

		assertEquals("224 Wyoming Ave",
				actualRequestBody.getContent().get("order").get("shipping_address").get("address1").asText());
		assertEquals("Suite 100",
				actualRequestBody.getContent().get("order").get("shipping_address").get("address2").asText());
		assertEquals("PEnnsylvania",
				actualRequestBody.getContent().get("order").get("shipping_address").get("province").asText());
		assertEquals("18503", actualRequestBody.getContent().get("order").get("shipping_address").get("zip").asText());
		assertEquals("channelape",
				actualRequestBody.getContent().get("order").get("shipping_address").get("company").asText());
		assertEquals("Ryan Kazokas",
				actualRequestBody.getContent().get("order").get("shipping_address").get("first_name").asText());
		assertEquals("Kazokas",
				actualRequestBody.getContent().get("order").get("shipping_address").get("last_name").asText());
		assertEquals("US", actualRequestBody.getContent().get("order").get("shipping_address").get("country").asText());
		assertEquals("USA",
				actualRequestBody.getContent().get("order").get("shipping_address").get("country_code").asText());
		assertEquals("PA",
				actualRequestBody.getContent().get("order").get("shipping_address").get("province_code").asText());

		assertEquals("224 Wyoming Ave",
				actualRequestBody.getContent().get("order").get("billing_address").get("address1").asText());
		assertEquals("Suite 100",
				actualRequestBody.getContent().get("order").get("billing_address").get("address2").asText());
		assertEquals("PEnnsylvania",
				actualRequestBody.getContent().get("order").get("billing_address").get("province").asText());
		assertEquals("18503", actualRequestBody.getContent().get("order").get("shipping_address").get("zip").asText());
		assertEquals("channelape",
				actualRequestBody.getContent().get("order").get("billing_address").get("company").asText());
		assertEquals("Ryan Kazokas",
				actualRequestBody.getContent().get("order").get("billing_address").get("first_name").asText());
		assertEquals("Kazokas",
				actualRequestBody.getContent().get("order").get("billing_address").get("last_name").asText());
		assertEquals("US", actualRequestBody.getContent().get("order").get("billing_address").get("country").asText());
		assertEquals("USA",
				actualRequestBody.getContent().get("order").get("billing_address").get("country_code").asText());
		assertEquals("PA",
				actualRequestBody.getContent().get("order").get("billing_address").get("province_code").asText());

		assertEquals(1, actualRequestBody.getContent().get("order").get("shipping_lines").size());
		assertEquals("Test",
				actualRequestBody.getContent().get("order").get("shipping_lines").path(0).get("code").asText());
		assertEquals("Testing Source",
				actualRequestBody.getContent().get("order").get("shipping_lines").path(0).get("source").asText());
		assertEquals("Testing Title",
				actualRequestBody.getContent().get("order").get("shipping_lines").path(0).get("title").asText());
		assertEquals(BigDecimal.valueOf(4.33),
				actualRequestBody.getContent().get("order").get("shipping_lines").path(0).get("price").decimalValue());

		assertEquals(shopifyOrder.getId(), actualShopifyOrder.getId());
		assertEquals(shopifyOrder.getName(), actualShopifyOrder.getName());
		assertEquals(shopifyCustomer.getEmail(), actualShopifyOrder.getCustomer().getEmail());
		assertEquals(shopifyCustomer.getFirstName(), actualShopifyOrder.getCustomer().getFirstName());
		assertEquals(shopifyCustomer.getLastname(), actualShopifyOrder.getCustomer().getLastname());
		assertEquals(shopifyCustomer.getPhone(), actualShopifyOrder.getCustomer().getPhone());
		assertEquals(2, actualShopifyOrder.getLineItems().size());
		assertEquals(shopifyOrder.getLineItems().get(0).getVariantId(),
				actualShopifyOrder.getLineItems().get(0).getVariantId());
		assertEquals(shopifyOrder.getLineItems().get(0).getQuantity(),
				actualShopifyOrder.getLineItems().get(0).getQuantity());
		assertEquals(shopifyOrder.getLineItems().get(1).getVariantId(),
				actualShopifyOrder.getLineItems().get(1).getVariantId());
		assertEquals(shopifyOrder.getLineItems().get(1).getQuantity(),
				actualShopifyOrder.getLineItems().get(1).getQuantity());

		assertEquals(processedAt, actualShopifyOrder.getProcessedAt());
		assertEquals(address.getAddress1(), actualShopifyOrder.getBillingAddress().getAddress1());
		assertEquals(address.getAddress2(), actualShopifyOrder.getBillingAddress().getAddress2());
		assertEquals(address.getCountry(), actualShopifyOrder.getBillingAddress().getCountry());
		assertEquals(address.getCountryCode(), actualShopifyOrder.getBillingAddress().getCountryCode());
		assertEquals(address.getFirstName(), actualShopifyOrder.getBillingAddress().getFirstName());
		assertEquals(address.getLastname(), actualShopifyOrder.getBillingAddress().getLastname());
		assertEquals(address.getName(), actualShopifyOrder.getBillingAddress().getName());
		assertEquals(address.getPhone(), actualShopifyOrder.getBillingAddress().getPhone());
		assertEquals(address.getProvince(), actualShopifyOrder.getBillingAddress().getProvince());
		assertEquals(address.getProvinceCode(), actualShopifyOrder.getBillingAddress().getProvinceCode());
		assertEquals(address.getCompany(), actualShopifyOrder.getBillingAddress().getCompany());

		assertEquals(address.getAddress1(), actualShopifyOrder.getShippingAddress().getAddress1());
		assertEquals(address.getAddress2(), actualShopifyOrder.getShippingAddress().getAddress2());
		assertEquals(address.getCountry(), actualShopifyOrder.getShippingAddress().getCountry());
		assertEquals(address.getCountryCode(), actualShopifyOrder.getShippingAddress().getCountryCode());
		assertEquals(address.getFirstName(), actualShopifyOrder.getShippingAddress().getFirstName());
		assertEquals(address.getLastname(), actualShopifyOrder.getShippingAddress().getLastname());
		assertEquals(address.getName(), actualShopifyOrder.getShippingAddress().getName());
		assertEquals(address.getPhone(), actualShopifyOrder.getShippingAddress().getPhone());
		assertEquals(address.getProvince(), actualShopifyOrder.getShippingAddress().getProvince());
		assertEquals(address.getProvinceCode(), actualShopifyOrder.getShippingAddress().getProvinceCode());
		assertEquals(address.getCompany(), actualShopifyOrder.getShippingAddress().getCompany());

		assertEquals(1, actualShopifyOrder.getShippingLines().size());
		assertEquals(shippingLine1.getCode(), actualShopifyOrder.getShippingLines().get(0).getCode());
		assertEquals(shippingLine1.getPrice(), actualShopifyOrder.getShippingLines().get(0).getPrice());
		assertEquals(shippingLine1.getSource(), actualShopifyOrder.getShippingLines().get(0).getSource());
		assertEquals(shippingLine1.getTitle(), actualShopifyOrder.getShippingLines().get(0).getTitle());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestAndCreatingRefundThenCalculateAndCreateRefundAndReturn()
			throws Exception {

		final String expectedCalculatePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.append(FORWARD_SLASH).append("123123").append(FORWARD_SLASH).append(ShopifySdk.REFUNDS)
				.append(FORWARD_SLASH).append(ShopifySdk.CALCULATE).toString();

		final String expectedRefundPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.append(FORWARD_SLASH).append("123123").append(FORWARD_SLASH).append(ShopifySdk.REFUNDS).toString();

		final ShopifyRefundRoot shopifyRefundRoot = new ShopifyRefundRoot();
		final ShopifyRefund shopifyRefund = new ShopifyRefund();

		shopifyRefund.setCreatedAt(SOME_DATE_TIME);
		shopifyRefund.setProcessedAt(SOME_DATE_TIME);
		shopifyRefund.setCurrency(Currency.getInstance("USD"));
		shopifyRefund.setId("999999");
		shopifyRefund.setOrderId("123123");
		shopifyRefund.setRestock(false);

		final ShopifyRefundShippingDetails shipping = new ShopifyRefundShippingDetails();
		shipping.setAmount(new BigDecimal(99.11));
		shipping.setFullRefund(true);
		shipping.setMaximumRefundable(new BigDecimal(92.11));
		shipping.setTax(new BigDecimal(11.44));

		shopifyRefund.setShipping(shipping);

		final ShopifyRefundLineItem shopifyRefundLineItem = new ShopifyRefundLineItem();
		shopifyRefundLineItem.setLineItemId("718273812");
		shopifyRefundLineItem.setQuantity(3);
		shopifyRefundLineItem.setRestockType("no_restock");

		shopifyRefund.setRefundLineItems(Arrays.asList(shopifyRefundLineItem));

		final ShopifyTransaction shopifyTransaction = new ShopifyTransaction();
		shopifyTransaction.setAmount(new BigDecimal(11.23));
		shopifyTransaction.setCurrency(Currency.getInstance("USD"));
		shopifyTransaction.setGateway("bogus");
		shopifyTransaction.setKind("full_refund");
		shopifyTransaction.setMaximumRefundable(new BigDecimal(11.23));
		shopifyTransaction.setOrderId("123123");
		shopifyTransaction.setParentId("44444");

		shopifyRefund.setTransactions(Arrays.asList(shopifyTransaction));

		shopifyRefundRoot.setRefund(shopifyRefund);

		final String expectedResponseBodyString = getJsonString(ShopifyRefundRoot.class, shopifyRefundRoot);

		final Status expectedStatus = Status.CREATED;
		final int expectedStatusCode = expectedStatus.getStatusCode();

		final JsonBodyCapture calculateRequestBody = new JsonBodyCapture();
		final JsonBodyCapture refundRequestBody = new JsonBodyCapture();

		driver.addExpectation(
				onRequestTo(expectedCalculatePath).withHeader("X-Shopify-Access-Token", accessToken)
						.withMethod(Method.POST).capturingBodyIn(calculateRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));
		driver.addExpectation(
				onRequestTo(expectedRefundPath).withHeader("X-Shopify-Access-Token", accessToken)
						.withMethod(Method.POST).capturingBodyIn(refundRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyRefundCreationRequest request = new ShopifyRefundCreationRequest();
		request.setRequest(shopifyRefund);
		final ShopifyRefund actualShopifyRefund = shopifySdk.refund(request);

		assertEquals("999999", calculateRequestBody.getContent().get("refund").get("id").asText());
		assertEquals("123123", calculateRequestBody.getContent().get("refund").get("order_id").asText());
		assertEquals(false, calculateRequestBody.getContent().get("refund").get("restock").asBoolean());
		assertEquals("USD", calculateRequestBody.getContent().get("refund").get("currency").asText());
		assertEquals(SOME_DATE_TIME.toString(),
				calculateRequestBody.getContent().get("refund").get("created_at").asText());
		assertEquals(SOME_DATE_TIME.toString(),
				calculateRequestBody.getContent().get("refund").get("processed_at").asText());

		assertEquals(true,
				calculateRequestBody.getContent().get("refund").get("shipping").get("full_refund").asBoolean());
		assertEquals(BigDecimal.valueOf(99.11),
				calculateRequestBody.getContent().get("refund").get("shipping").get("amount").decimalValue());
		assertEquals(BigDecimal.valueOf(92.11), calculateRequestBody.getContent().get("refund").get("shipping")
				.get("maximum_refundable").decimalValue());
		assertEquals(BigDecimal.valueOf(11.44),
				calculateRequestBody.getContent().get("refund").get("shipping").get("tax").decimalValue());

		assertEquals(1, calculateRequestBody.getContent().get("refund").get("refund_line_items").size());
		assertEquals("718273812", calculateRequestBody.getContent().get("refund").get("refund_line_items").path(0)
				.get("line_item_id").asText());
		assertEquals("no_restock", calculateRequestBody.getContent().get("refund").get("refund_line_items").path(0)
				.get("restock_type").asText());
		assertEquals(3, calculateRequestBody.getContent().get("refund").get("refund_line_items").path(0).get("quantity")
				.asInt());

		assertEquals(1, calculateRequestBody.getContent().get("refund").get("transactions").size());
		assertEquals(BigDecimal.valueOf(11.23), calculateRequestBody.getContent().get("refund").get("transactions")
				.path(0).get("amount").decimalValue());
		assertEquals(BigDecimal.valueOf(11.23), calculateRequestBody.getContent().get("refund").get("transactions")
				.path(0).get("maximum_refundable").decimalValue());
		assertEquals("USD",
				calculateRequestBody.getContent().get("refund").get("transactions").path(0).get("currency").asText());
		assertEquals("bogus",
				calculateRequestBody.getContent().get("refund").get("transactions").path(0).get("gateway").asText());
		assertEquals("full_refund",
				calculateRequestBody.getContent().get("refund").get("transactions").path(0).get("kind").asText());
		assertEquals("123123",
				calculateRequestBody.getContent().get("refund").get("transactions").path(0).get("order_id").asText());
		assertEquals("44444",
				calculateRequestBody.getContent().get("refund").get("transactions").path(0).get("parent_id").asText());

		assertNotNull(actualShopifyRefund);
		assertEquals(shopifyRefund.getId(), actualShopifyRefund.getId());
		assertTrue(SOME_DATE_TIME.compareTo(actualShopifyRefund.getCreatedAt()) == 0);
		assertTrue(SOME_DATE_TIME.compareTo(actualShopifyRefund.getProcessedAt()) == 0);
		assertEquals(shopifyRefund.getCurrency(), actualShopifyRefund.getCurrency());
		assertEquals(shopifyRefund.getOrderId(), actualShopifyRefund.getOrderId());
		assertEquals(shopifyRefund.getRefundLineItems().get(0).getLineItemId(),
				actualShopifyRefund.getRefundLineItems().get(0).getLineItemId());
		assertEquals(shopifyRefund.getRefundLineItems().get(0).getRestockType(),
				actualShopifyRefund.getRefundLineItems().get(0).getRestockType());
		assertEquals(shopifyRefund.getRefundLineItems().get(0).getQuantity(),
				actualShopifyRefund.getRefundLineItems().get(0).getQuantity());
		assertEquals(shopifyRefund.getRefundLineItems().get(0).getSubtotal(),
				actualShopifyRefund.getRefundLineItems().get(0).getSubtotal());
		assertEquals(shopifyRefund.getRefundLineItems().get(0).getLocationId(),
				actualShopifyRefund.getRefundLineItems().get(0).getLocationId());
		assertEquals(shopifyRefund.getRefundLineItems().get(0).getTotalTax(),
				actualShopifyRefund.getRefundLineItems().get(0).getTotalTax());
		assertEquals(shopifyRefund.getShipping().getAmount(), actualShopifyRefund.getShipping().getAmount());
		assertEquals(shopifyRefund.getShipping().getMaximumRefundable(),
				actualShopifyRefund.getShipping().getMaximumRefundable());
		assertEquals(shopifyRefund.getShipping().getTax(), actualShopifyRefund.getShipping().getTax());
		assertEquals(shopifyRefund.getShipping().isFullRefund(), actualShopifyRefund.getShipping().isFullRefund());
		assertEquals(shopifyRefund.getTransactions().get(0).getAmount(),
				actualShopifyRefund.getTransactions().get(0).getAmount());
		assertEquals(shopifyRefund.getTransactions().get(0).getCurrency(),
				actualShopifyRefund.getTransactions().get(0).getCurrency());
		assertEquals(shopifyRefund.getTransactions().get(0).getGateway(),
				actualShopifyRefund.getTransactions().get(0).getGateway());
		assertEquals(shopifyRefund.getTransactions().get(0).getKind(),
				actualShopifyRefund.getTransactions().get(0).getKind());
		assertEquals(shopifyRefund.getTransactions().get(0).getOrderId(),
				actualShopifyRefund.getTransactions().get(0).getOrderId());
		assertEquals(shopifyRefund.getTransactions().get(0).getMaximumRefundable(),
				actualShopifyRefund.getTransactions().get(0).getMaximumRefundable());
		assertEquals(shopifyRefund.getTransactions().get(0).getParentId(),
				actualShopifyRefund.getTransactions().get(0).getParentId());
	}

	private <T> String getJsonString(final Class<T> clazz, final T object) throws JAXBException {
		final JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[] { clazz }, null);
		final Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
		marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);

		final StringWriter stringWriter = new StringWriter();
		marshaller.marshal(object, stringWriter);
		return stringWriter.toString();
	}

	private void assertValidFulfillment(final ShopifyFulfillment expectedShopifyFulfillment,
			final ShopifyFulfillment actualShopifyFulfillment) {
		assertEquals(expectedShopifyFulfillment.getId(), actualShopifyFulfillment.getId());
		assertEquals(expectedShopifyFulfillment.getOrderId(), actualShopifyFulfillment.getOrderId());
		assertTrue(expectedShopifyFulfillment.getCreatedAt().compareTo(actualShopifyFulfillment.getCreatedAt()) == 0);
		assertEquals(expectedShopifyFulfillment.getLineItems().get(0).getId(),
				actualShopifyFulfillment.getLineItems().get(0).getId());
		assertEquals(expectedShopifyFulfillment.getLineItems().get(0).getSku(),
				actualShopifyFulfillment.getLineItems().get(0).getSku());
		assertEquals(expectedShopifyFulfillment.getLineItems().get(0).getQuantity(),
				actualShopifyFulfillment.getLineItems().get(0).getQuantity());
		assertEquals(expectedShopifyFulfillment.getLocationId(), actualShopifyFulfillment.getLocationId());
		assertEquals(expectedShopifyFulfillment.isNotifyCustomer(), actualShopifyFulfillment.isNotifyCustomer());
		assertEquals(expectedShopifyFulfillment.getStatus(), actualShopifyFulfillment.getStatus());
		assertEquals(expectedShopifyFulfillment.getTrackingCompany(), actualShopifyFulfillment.getTrackingCompany());
		assertEquals(expectedShopifyFulfillment.getTrackingNumber(), actualShopifyFulfillment.getTrackingNumber());
		assertEquals(expectedShopifyFulfillment.getTrackingUrl(), actualShopifyFulfillment.getTrackingUrl());
		assertEquals(expectedShopifyFulfillment.getTrackingUrls(), actualShopifyFulfillment.getTrackingUrls());
		assertTrue(expectedShopifyFulfillment.getUpdatedAt().compareTo(actualShopifyFulfillment.getUpdatedAt()) == 0);
	}

	private ShopifyFulfillment buildShopifyFulfillment(final ShopifyLineItem lineItem) {
		final ShopifyFulfillment currentFulfillment = new ShopifyFulfillment();
		currentFulfillment.setId("4567");
		currentFulfillment.setOrderId("1234");
		currentFulfillment.setCreatedAt(SOME_DATE_TIME);
		currentFulfillment.setLineItems(Arrays.asList(lineItem));
		currentFulfillment.setLocationId("1");
		currentFulfillment.setNotifyCustomer(true);
		currentFulfillment.setStatus("cancelled");
		currentFulfillment.setTrackingCompany("USPS");
		currentFulfillment.setTrackingNumber("12341234");
		currentFulfillment.setTrackingUrl("tracking_url");
		currentFulfillment.setTrackingUrls(Arrays.asList("tracking_url1", "tracking_url2"));
		currentFulfillment.setUpdatedAt(SOME_DATE_TIME);
		return currentFulfillment;
	}

	private ShopifyLocation buildShopifyLocation(final String address1, final String address2, final String id,
			final String name) {
		final ShopifyLocation shopifyLocation = new ShopifyLocation();
		shopifyLocation.setAddress1(address1);
		shopifyLocation.setAddress1(address2);
		shopifyLocation.setCity("Scranton");
		shopifyLocation.setCountry("USA");
		shopifyLocation.setCountryCode("US");
		shopifyLocation.setId(id);
		shopifyLocation.setName(name);
		shopifyLocation.setProvince("PEnnsylvania");
		shopifyLocation.setProvinceCode("PA");
		shopifyLocation.setCountryName("United States");
		return shopifyLocation;
	}

}
