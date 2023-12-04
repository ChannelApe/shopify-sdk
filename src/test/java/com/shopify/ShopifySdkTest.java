package com.shopify;

import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.shopify.model.ShopifySmartCollection;
import com.shopify.model.ShopifySmartCollectionsRoot;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverResponse;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.clientdriver.capture.JsonBodyCapture;
import com.github.restdriver.clientdriver.capture.StringBodyCapture;
import com.shopify.exceptions.ShopifyClientException;
import com.shopify.exceptions.ShopifyEmptyLineItemsException;
import com.shopify.exceptions.ShopifyErrorResponseException;
import com.shopify.mappers.LegacyToFulfillmentOrderMapping;
import com.shopify.mappers.ShopifySdkObjectMapper;
import com.shopify.model.Count;
import com.shopify.model.Image;
import com.shopify.model.Metafield;
import com.shopify.model.MetafieldRoot;
import com.shopify.model.MetafieldType;
import com.shopify.model.MetafieldsRoot;
import com.shopify.model.OrderRiskRecommendation;
import com.shopify.model.Shop;
import com.shopify.model.ShopifyAccessTokenRoot;
import com.shopify.model.ShopifyAddress;
import com.shopify.model.ShopifyAdjustment;
import com.shopify.model.ShopifyAttribute;
import com.shopify.model.ShopifyCustomCollection;
import com.shopify.model.ShopifyCustomCollectionCreationRequest;
import com.shopify.model.ShopifyCustomCollectionRoot;
import com.shopify.model.ShopifyCustomCollectionsRoot;
import com.shopify.model.ShopifyCustomer;
import com.shopify.model.ShopifyCustomerRoot;
import com.shopify.model.ShopifyCustomerUpdateRequest;
import com.shopify.model.ShopifyCustomersRoot;
import com.shopify.model.ShopifyFulfillment;
import com.shopify.model.ShopifyFulfillmentCreationRequest;
import com.shopify.model.ShopifyFulfillmentOrder;
import com.shopify.model.ShopifyFulfillmentOrderLineItem;
import com.shopify.model.ShopifyFulfillmentPayloadRoot;
import com.shopify.model.ShopifyFulfillmentRoot;
import com.shopify.model.ShopifyFulfillmentUpdateRequest;
import com.shopify.model.ShopifyGetCustomersRequest;
import com.shopify.model.ShopifyGiftCard;
import com.shopify.model.ShopifyGiftCardCreationRequest;
import com.shopify.model.ShopifyGiftCardRoot;
import com.shopify.model.ShopifyInventoryLevel;
import com.shopify.model.ShopifyInventoryLevelRoot;
import com.shopify.model.ShopifyLineItem;
import com.shopify.model.ShopifyLocation;
import com.shopify.model.ShopifyLocationsRoot;
import com.shopify.model.ShopifyOrder;
import com.shopify.model.ShopifyOrderCreationRequest;
import com.shopify.model.ShopifyOrderRisk;
import com.shopify.model.ShopifyOrderRisksRoot;
import com.shopify.model.ShopifyOrderRoot;
import com.shopify.model.ShopifyOrderShippingAddressUpdateRequest;
import com.shopify.model.ShopifyOrdersRoot;
import com.shopify.model.ShopifyPage;
import com.shopify.model.ShopifyProduct;
import com.shopify.model.ShopifyProductCreationRequest;
import com.shopify.model.ShopifyProductMetafieldCreationRequest;
import com.shopify.model.ShopifyProductRoot;
import com.shopify.model.ShopifyProductUpdateRequest;
import com.shopify.model.ShopifyProducts;
import com.shopify.model.ShopifyProductsRoot;
import com.shopify.model.ShopifyProperty;
import com.shopify.model.ShopifyRecurringApplicationCharge;
import com.shopify.model.ShopifyRecurringApplicationChargeCreationRequest;
import com.shopify.model.ShopifyRecurringApplicationChargeRoot;
import com.shopify.model.ShopifyRefund;
import com.shopify.model.ShopifyRefundCreationRequest;
import com.shopify.model.ShopifyRefundLineItem;
import com.shopify.model.ShopifyRefundRoot;
import com.shopify.model.ShopifyRefundShippingDetails;
import com.shopify.model.ShopifyShippingLine;
import com.shopify.model.ShopifyShop;
import com.shopify.model.ShopifyTaxLine;
import com.shopify.model.ShopifyTransaction;
import com.shopify.model.ShopifyTransactionReceipt;
import com.shopify.model.ShopifyTransactionsRoot;
import com.shopify.model.ShopifyVariant;
import com.shopify.model.ShopifyVariantCreationRequest;
import com.shopify.model.ShopifyVariantMetafieldCreationRequest;
import com.shopify.model.ShopifyVariantRoot;
import com.shopify.model.ShopifyVariantUpdateRequest;

@RunWith(MockitoJUnitRunner.class)
public class ShopifySdkTest {

	private static final String SOME_API_VERSION = "2020-01";
	private static final DateTime SOME_DATE_TIME = new DateTime();
	private static final ShopifyCustomer SOME_CUSTOMER = new ShopifyCustomer();
	private static final String FORWARD_SLASH = "/";
	private final String accessToken = "09382489782734897289374829374";

	private ShopifySdk shopifySdk;

	@Rule
	public ClientDriverRule driver = new ClientDriverRule();

	@BeforeClass
	public static void beforeClass() {
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "OFF");
	}

	@Before
	public void setUp() throws JsonProcessingException {
		MockitoAnnotations.initMocks(this);
		final String subdomainUrl = driver.getBaseUrl();

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.SHOP)
				.toString();

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

		shopifySdk = ShopifySdk.newBuilder().withApiUrl(subdomainUrl).withAccessToken(accessToken)
				.withMinimumRequestRetryRandomDelay(200, TimeUnit.MILLISECONDS)
				.withMaximumRequestRetryTimeout(225, TimeUnit.MILLISECONDS)
				.withConnectionTimeout(500, TimeUnit.MILLISECONDS).withApiVersion(SOME_API_VERSION).build();

	}

	@Test(expected = IllegalArgumentException.class)
	public void givenMinimumDelayIsLargerThanMaximumDelayWhenCreatingShopifySdkThenExpectIllegalArgumentException() {
		ShopifySdk.newBuilder().withApiUrl("").withAccessToken(accessToken)
				.withMinimumRequestRetryRandomDelay(10, TimeUnit.DAYS)
				.withMaximumRequestRetryRandomDelay(5, TimeUnit.SECONDS).withConnectionTimeout(2, TimeUnit.MINUTES)
				.withReadTimeout(3, TimeUnit.MINUTES).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void givenInvalidMiniumRetryDelayWhenCreatingShopifySdkThenExpectIllegalArgumentException() {
		ShopifySdk.newBuilder().withApiUrl("").withAccessToken(accessToken)
				.withMinimumRequestRetryRandomDelay(1, TimeUnit.MICROSECONDS).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void givenInvalidMaximumRetryDelayWhenCreatingShopifySdkThenExpectIllegalArgumentException() {
		ShopifySdk.newBuilder().withApiUrl("").withAccessToken(accessToken)
				.withMaximumRequestRetryRandomDelay(1, TimeUnit.MICROSECONDS).build();
	}

	@Test
	public void givenSomeClientCredentialsWhenCallinglToTheShopifyApiThenExpectAccessTokenToBeGeneratedAfterCallIsMade()
			throws JsonProcessingException {

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
				.withClientSecret("some-client-secret").withAuthorizationToken("3892742738482")
				.withMaximumRequestRetryTimeout(2, TimeUnit.SECONDS).build();

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
			throws JsonProcessingException {

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
				.withClientSecret("some-client-secret").withAuthorizationToken("3892742738482")
				.withMaximumRequestRetryTimeout(2, TimeUnit.SECONDS).build();

		assertNull(shopifySdk.getAccessToken());
		shopifySdk.getShop();

	}

	@Test
	public void givenSomeShopifyFulfillmentCreationRequestWhenCreatingShopifyFulfillmentThenCreateAndReturnFulfillmentWithLegacyApi()
			throws JsonProcessingException, ConnectException {

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId("some_line_item_id");
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.append("/1234/").append(ShopifySdk.FULFILLMENTS).toString();
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
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode)
						.withHeader(ShopifySdk.DEPRECATED_REASON_HEADER, "Call to be removed from API.")
						.withHeader("Location", new StringBuilder().append("https://test.myshopify.com/admin")
								.append(expectedPath).toString()));

		final ShopifyFulfillmentCreationRequest request = ShopifyFulfillmentCreationRequest.newBuilder()
				.withOrderId("1234").withTrackingCompany("USPS").withTrackingNumber("12341234").withNotifyCustomer(true)
				.withLineItems(Arrays.asList(lineItem)).withLocationId("1")
				.withTrackingUrls(Arrays.asList("tracking_url1", "tracking_url2")).build();

		final ShopifyFulfillment actualShopifyFulfillment = shopifySdk.createFulfillment(request);

		assertValidFulfillment(currentFulfillment, actualShopifyFulfillment);

	}

	@Test(expected = ShopifyEmptyLineItemsException.class)
	public void givenSomeShopifyFulfillmentOrderWithNoCreateFulfillmentSupportedActionWhenCreatingShopifyFulfillmentThenGetAnUnsupportedActionException()
			throws JsonProcessingException, ConnectException, ShopifyEmptyLineItemsException {
		final String lineItemId = "987";
		final String fulfillmentOrderId = "1234";

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId(lineItemId);
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		List<ShopifyFulfillmentOrderLineItem> fulfillmentOrderLineItems = new LinkedList<>();
		ShopifyFulfillmentOrderLineItem fulfillmentOrderLineItem = new ShopifyFulfillmentOrderLineItem();
		fulfillmentOrderLineItem.setQuantity(1);
		fulfillmentOrderLineItem.setLineItemId(lineItemId);
		fulfillmentOrderLineItem.setFulfillableQuantity(1);
		fulfillmentOrderLineItem.setFulfillmentOrderId(fulfillmentOrderId);
		fulfillmentOrderLineItems.add(fulfillmentOrderLineItem);

		final List<String> supportedActions = new LinkedList<>();
		supportedActions.add("move");

		final ShopifyFulfillmentOrder fulfillmentOrder = new ShopifyFulfillmentOrder();
		fulfillmentOrder.setId(fulfillmentOrderId);
		fulfillmentOrder.setLineItems(fulfillmentOrderLineItems);
		fulfillmentOrder.setSupportedActions(supportedActions);
		fulfillmentOrder.setAssignedLocationId("5678");
		final List<ShopifyFulfillmentOrder> fulfillmentOrders = new LinkedList<>();
		fulfillmentOrders.add(fulfillmentOrder);

		final ShopifyFulfillment currentFulfillment = buildShopifyFulfillment(lineItem);
		final ShopifyFulfillmentRoot shopifyFulfillmentRoot = new ShopifyFulfillmentRoot();
		shopifyFulfillmentRoot.setFulfillment(currentFulfillment);

		final ShopifyFulfillmentCreationRequest request = ShopifyFulfillmentCreationRequest.newBuilder()
				.withOrderId("1234").withTrackingCompany("USPS").withTrackingNumber("12341234").withNotifyCustomer(true)
				.withLineItems(Arrays.asList(lineItem)).withLocationId("1")
				.withTrackingUrls(Arrays.asList("tracking_url1", "tracking_url2")).build();

		shopifySdk.createFulfillment(request, fulfillmentOrders);
	}

	@Test(expected = ShopifyEmptyLineItemsException.class)
	public void givenSomeShopifyFulfillmentOrderAndFulfillmentWithNoMatchingLineItemsWhenCreatingShopifyFulfillmentThenGetAnUnsupportedActionException()
			throws JsonProcessingException, ConnectException, ShopifyEmptyLineItemsException {
		final String lineItemId = "987";
		final String fulfillmentOrderId = "1234";

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId("lineItemId");
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		List<ShopifyFulfillmentOrderLineItem> fulfillmentOrderLineItems = new LinkedList<>();
		ShopifyFulfillmentOrderLineItem fulfillmentOrderLineItem = new ShopifyFulfillmentOrderLineItem();
		fulfillmentOrderLineItem.setQuantity(1);
		fulfillmentOrderLineItem.setLineItemId(lineItemId);
		fulfillmentOrderLineItem.setFulfillableQuantity(1);
		fulfillmentOrderLineItem.setFulfillmentOrderId(fulfillmentOrderId);
		fulfillmentOrderLineItems.add(fulfillmentOrderLineItem);

		final List<String> supportedActions = new LinkedList<>();
		supportedActions.add("move");
		supportedActions.add("create_fulfillment");

		final ShopifyFulfillmentOrder fulfillmentOrder = new ShopifyFulfillmentOrder();
		fulfillmentOrder.setId(fulfillmentOrderId);
		fulfillmentOrder.setLineItems(fulfillmentOrderLineItems);
		fulfillmentOrder.setSupportedActions(supportedActions);
		fulfillmentOrder.setAssignedLocationId("5678");
		final List<ShopifyFulfillmentOrder> fulfillmentOrders = new LinkedList<>();
		fulfillmentOrders.add(fulfillmentOrder);

		final ShopifyFulfillment currentFulfillment = buildShopifyFulfillment(lineItem);
		final ShopifyFulfillmentRoot shopifyFulfillmentRoot = new ShopifyFulfillmentRoot();
		shopifyFulfillmentRoot.setFulfillment(currentFulfillment);

		final ShopifyFulfillmentCreationRequest request = ShopifyFulfillmentCreationRequest.newBuilder()
				.withOrderId("1234").withTrackingCompany("USPS").withTrackingNumber("12341234").withNotifyCustomer(true)
				.withLineItems(Arrays.asList(lineItem)).withLocationId("1")
				.withTrackingUrls(Arrays.asList("tracking_url1", "tracking_url2")).build();

		shopifySdk.createFulfillment(request, fulfillmentOrders);
	}

	@Test
	public void givenSomeShopifyFulfillmentCreationRequestWhenCreatingShopifyFulfillmentThenCreateAndReturnFulfillmentWithFulfillmentOrderApi()
			throws JsonProcessingException, ConnectException, ShopifyEmptyLineItemsException {
		final String lineItemId = "987";
		final String fulfillmentOrderId = "1234";

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId(lineItemId);
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		List<ShopifyFulfillmentOrderLineItem> fulfillmentOrderLineItems = new LinkedList<>();
		ShopifyFulfillmentOrderLineItem fulfillmentOrderLineItem = new ShopifyFulfillmentOrderLineItem();
		fulfillmentOrderLineItem.setQuantity(1);
		fulfillmentOrderLineItem.setLineItemId(lineItemId);
		fulfillmentOrderLineItem.setFulfillableQuantity(1);
		fulfillmentOrderLineItem.setFulfillmentOrderId(fulfillmentOrderId);
		fulfillmentOrderLineItems.add(fulfillmentOrderLineItem);

		final List<String> supportedActions = new LinkedList<>();
		supportedActions.add("move");
		supportedActions.add("create_fulfillment");

		final ShopifyFulfillmentOrder fulfillmentOrder = new ShopifyFulfillmentOrder();
		fulfillmentOrder.setId(fulfillmentOrderId);
		fulfillmentOrder.setLineItems(fulfillmentOrderLineItems);
		fulfillmentOrder.setSupportedActions(supportedActions);
		fulfillmentOrder.setAssignedLocationId("5678");
		final List<ShopifyFulfillmentOrder> fulfillmentOrders = new LinkedList<>();
		fulfillmentOrders.add(fulfillmentOrder);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.FULFILLMENTS)
				.toString();
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
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode)
						.withHeader(ShopifySdk.DEPRECATED_REASON_HEADER, "Call to be removed from API.")
						.withHeader("Location", new StringBuilder().append("https://test.myshopify.com/admin")
								.append(expectedPath).toString()));

		final ShopifyFulfillmentCreationRequest request = ShopifyFulfillmentCreationRequest.newBuilder()
				.withOrderId("1234").withTrackingCompany("USPS").withTrackingNumber("12341234").withNotifyCustomer(true)
				.withLineItems(Arrays.asList(lineItem)).withLocationId("1")
				.withTrackingUrls(Arrays.asList("tracking_url1", "tracking_url2")).build();

		final ShopifyFulfillment actualShopifyFulfillment = shopifySdk.createFulfillment(request, fulfillmentOrders);

		// making sure the tracking url mapping is correct
		final ShopifyFulfillmentPayloadRoot payload = LegacyToFulfillmentOrderMapping
				.toShopifyFulfillmentPayloadRoot(request.getRequest(), fulfillmentOrders);

		assertEquals(payload.getFulfillment().getTrackingInfo().getUrl(), "tracking_url1");
		assertValidFulfillment(currentFulfillment, actualShopifyFulfillment);
	}

	@Test
	public void givenSomeShopifyFulfillmentCreationRequestWhenCreatingShopifyFulfillmentThenCreateAndReturnFulfillmentWithFulfillmentOrderApiWithoutTrackingUrlsArray()
			throws JsonProcessingException, ConnectException, ShopifyEmptyLineItemsException {
		final String lineItemId = "987";
		final String fulfillmentOrderId = "1234";

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId(lineItemId);
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		List<ShopifyFulfillmentOrderLineItem> fulfillmentOrderLineItems = new LinkedList<>();
		ShopifyFulfillmentOrderLineItem fulfillmentOrderLineItem = new ShopifyFulfillmentOrderLineItem();
		fulfillmentOrderLineItem.setQuantity(1);
		fulfillmentOrderLineItem.setLineItemId(lineItemId);
		fulfillmentOrderLineItem.setFulfillableQuantity(1);
		fulfillmentOrderLineItem.setFulfillmentOrderId(fulfillmentOrderId);
		fulfillmentOrderLineItems.add(fulfillmentOrderLineItem);

		final List<String> supportedActions = new LinkedList<>();
		supportedActions.add("move");
		supportedActions.add("create_fulfillment");

		final ShopifyFulfillmentOrder fulfillmentOrder = new ShopifyFulfillmentOrder();
		fulfillmentOrder.setId(fulfillmentOrderId);
		fulfillmentOrder.setLineItems(fulfillmentOrderLineItems);
		fulfillmentOrder.setSupportedActions(supportedActions);
		fulfillmentOrder.setAssignedLocationId("5678");
		final List<ShopifyFulfillmentOrder> fulfillmentOrders = new LinkedList<>();
		fulfillmentOrders.add(fulfillmentOrder);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.FULFILLMENTS)
				.toString();
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
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode)
						.withHeader(ShopifySdk.DEPRECATED_REASON_HEADER, "Call to be removed from API.")
						.withHeader("Location", new StringBuilder().append("https://test.myshopify.com/admin")
								.append(expectedPath).toString()));

		final ShopifyFulfillmentCreationRequest request = ShopifyFulfillmentCreationRequest.newBuilder()
				.withOrderId("1234").withTrackingCompany("USPS").withTrackingNumber("12341234").withNotifyCustomer(true)
				.withLineItems(Arrays.asList(lineItem)).withLocationId("1").withTrackingUrls(Arrays.asList()).build();

		request.getRequest().setTrackingUrl("tracking_url");
		final ShopifyFulfillment actualShopifyFulfillment = shopifySdk.createFulfillment(request, fulfillmentOrders);

		// making sure the tracking url mapping is correct
		final ShopifyFulfillmentPayloadRoot payload = LegacyToFulfillmentOrderMapping
				.toShopifyFulfillmentPayloadRoot(request.getRequest(), fulfillmentOrders);

		assertEquals(payload.getFulfillment().getTrackingInfo().getUrl(), "tracking_url");
		assertValidFulfillment(currentFulfillment, actualShopifyFulfillment);
	}

	@Test
	public void givenSomeShopifyFulfillmentCreationRequestWhenCreatingShopifyFulfillmentThenCreateAndReturnFulfillmentWithFulfillmentOrderApiAndFulfillmentToADifferentShopLocation()
			throws JsonProcessingException, ConnectException, ShopifyEmptyLineItemsException {
		final String lineItemId = "987";
		final String fulfillmentOrderId = "1234";

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId(lineItemId);
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		List<ShopifyFulfillmentOrderLineItem> fulfillmentOrderLineItems = new LinkedList<>();
		ShopifyFulfillmentOrderLineItem fulfillmentOrderLineItem = new ShopifyFulfillmentOrderLineItem();
		fulfillmentOrderLineItem.setQuantity(1);
		fulfillmentOrderLineItem.setLineItemId(lineItemId);
		fulfillmentOrderLineItem.setFulfillableQuantity(1);
		fulfillmentOrderLineItem.setFulfillmentOrderId(fulfillmentOrderId);
		fulfillmentOrderLineItems.add(fulfillmentOrderLineItem);

		final List<String> supportedActions = new LinkedList<>();
		supportedActions.add("move");
		supportedActions.add("create_fulfillment");

		final ShopifyFulfillmentOrder fulfillmentOrder = new ShopifyFulfillmentOrder();
		fulfillmentOrder.setId(fulfillmentOrderId);
		fulfillmentOrder.setLineItems(fulfillmentOrderLineItems);
		fulfillmentOrder.setSupportedActions(supportedActions);
		fulfillmentOrder.setAssignedLocationId("5678");
		final List<ShopifyFulfillmentOrder> fulfillmentOrders = new LinkedList<>();
		fulfillmentOrders.add(fulfillmentOrder);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.FULFILLMENTS)
				.toString();
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
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode)
						.withHeader(ShopifySdk.DEPRECATED_REASON_HEADER, "Call to be removed from API.")
						.withHeader("Location", new StringBuilder().append("https://test.myshopify.com/admin")
								.append(expectedPath).toString()));

		final ShopifyFulfillmentCreationRequest request = ShopifyFulfillmentCreationRequest.newBuilder()
				.withOrderId("1234").withTrackingCompany("USPS").withTrackingNumber("12341234").withNotifyCustomer(true)
				.withLineItems(Arrays.asList(lineItem)).withLocationId("1")
				.withTrackingUrls(Arrays.asList("tracking_url1", "tracking_url2")).build();

		final ShopifyFulfillment actualShopifyFulfillment = shopifySdk.createFulfillment(request, fulfillmentOrders);

		assertValidFulfillment(currentFulfillment, actualShopifyFulfillment);

	}

	@Test(expected = ShopifyClientException.class)
	public void givenSomeClientCredentialsAndRateLimitedWhenCallinglToTheShopifyApiThenExpectShopifyClientException()
			throws JsonProcessingException {

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId("some_line_item_id");
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.append("/1234/").append(ShopifySdk.FULFILLMENTS).toString();
		final ShopifyFulfillment currentFulfillment = buildShopifyFulfillment(lineItem);
		final ShopifyFulfillmentRoot shopifyFulfillmentRoot = new ShopifyFulfillmentRoot();
		shopifyFulfillmentRoot.setFulfillment(currentFulfillment);

		final String expectedResponseBodyString = "{ \"errors\": \"You have been rate limited!\" }";

		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.POST).capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(429)
						.withHeader(ShopifySdk.RETRY_AFTER_HEADER, "2.0"))
				.anyTimes();

		final ShopifyFulfillmentCreationRequest request = ShopifyFulfillmentCreationRequest.newBuilder()
				.withOrderId("1234").withTrackingCompany("USPS").withTrackingNumber("12341234").withNotifyCustomer(true)
				.withLineItems(Arrays.asList(lineItem)).withLocationId("1")
				.withTrackingUrls(Arrays.asList("tracking_url1", "tracking_url2")).build();

		shopifySdk.createFulfillment(request);
	}

	@Test
	public void givenSomeShopifyFulfillmentUpdateRequestWhenUpdatingShopifyFulfillmentThenUpdateAndReturnFulfillmentWithLegacyApi()
			throws JsonProcessingException {

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId("some_line_item_id");
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.append("/1234/").append(ShopifySdk.FULFILLMENTS).append("/4567").toString();
		final ShopifyFulfillment currentFulfillment = buildShopifyFulfillment(lineItem);
		final ShopifyFulfillmentRoot shopifyFulfillmentRoot = new ShopifyFulfillmentRoot();
		shopifyFulfillmentRoot.setFulfillment(currentFulfillment);

		final String expectedResponseBodyString = getJsonString(ShopifyFulfillmentRoot.class, shopifyFulfillmentRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken).withMethod(Method.PUT)
						.capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyFulfillmentUpdateRequest request = ShopifyFulfillmentUpdateRequest.newBuilder()
				.withCurrentShopifyFulfillment(currentFulfillment).withTrackingCompany("USPS")
				.withTrackingNumber("12341234").withNotifyCustomer(true).withLineItems(Arrays.asList(lineItem))
				.withLocationId("1").withTrackingUrls(Arrays.asList("tracking_url1", "tracking_url2")).build();

		final ShopifyFulfillment actualShopifyFulfillment = shopifySdk.updateFulfillment(request);

		assertValidFulfillment(currentFulfillment, actualShopifyFulfillment);

	}

	@Test
	public void givenSomeShopifyFulfillmentUpdateRequestWhenUpdatingShopifyFulfillmentThenUpdateAndReturnFulfillmentWithFulfillmentOrderApi()
			throws JsonProcessingException {

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId("some_line_item_id");
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.FULFILLMENTS)
				.append("/4567").append(FORWARD_SLASH).append(ShopifySdk.UPDATE_TRACKING).toString();
		final ShopifyFulfillment currentFulfillment = buildShopifyFulfillment(lineItem);
		final ShopifyFulfillmentRoot shopifyFulfillmentRoot = new ShopifyFulfillmentRoot();
		shopifyFulfillmentRoot.setFulfillment(currentFulfillment);

		final String expectedResponseBodyString = getJsonString(ShopifyFulfillmentRoot.class, shopifyFulfillmentRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.POST).capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyFulfillmentUpdateRequest request = ShopifyFulfillmentUpdateRequest.newBuilder()
				.withCurrentShopifyFulfillment(currentFulfillment).withTrackingCompany("USPS")
				.withTrackingNumber("12341234").withNotifyCustomer(true).withLineItems(Arrays.asList(lineItem))
				.withLocationId("1").withTrackingUrls(Arrays.asList("tracking_url1", "tracking_url2")).build();

		final ShopifyFulfillment actualShopifyFulfillment = shopifySdk.updateFulfillmentTrackingInfo(request);
		assertValidFulfillment(currentFulfillment, actualShopifyFulfillment);
	}

	@Test
	public void givenSomePageAndCreatedAtMinAndCreatedAtMaxOrdersWhenRetrievingOrdersThenRetrieveOrdersWithCorrectValues()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.toString();
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

		final ShopifyTaxLine shopifyTaxLine1LineItem1 = buildTaxLine(new BigDecimal(41.22), new BigDecimal(46.3),
				"Some Tax Line1");
		final ShopifyTaxLine shopifyTaxLine1LineItem2 = buildTaxLine(new BigDecimal(54.22), new BigDecimal(26.3),
				"Some Tax Line2");
		shopifyLineItem1
				.setTaxLines(new LinkedList<>(Arrays.asList(shopifyTaxLine1LineItem1, shopifyTaxLine1LineItem2)));

		final ShopifyProperty shopifyProperty1 = buildShopifyProperty("message", "Some new message");
		final ShopifyProperty shopifyProperty2 = buildShopifyProperty("from", "From family");
		final ShopifyProperty shopifyProperty3 = buildShopifyProperty("to", "To family member");
		shopifyLineItem1
				.setProperties(new LinkedList<>(Arrays.asList(shopifyProperty1, shopifyProperty2, shopifyProperty3)));
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

		final ShopifyTransaction shopifyTransaction1 = new ShopifyTransaction();
		shopifyTransaction1.setId("123");
		shopifyTransaction1.setMessage("Refunded 12.72 from manual gateway");
		shopifyTransaction1.setAmount(new BigDecimal(12.72));
		shopifyTransaction1.setStatus("SUCCESS");
		shopifyTransaction1.setKind("refund_discrepancy");
		shopifyTransaction1.setGateway("manual");
		shopifyTransaction1.setCurrency(Currency.getInstance("USD"));
		shopifyTransaction1.setMaximumRefundable(new BigDecimal(15.99));

		final ShopifyTransaction shopifyTransaction2 = new ShopifyTransaction();
		shopifyTransaction2.setId("456");
		shopifyTransaction2.setAmount(new BigDecimal("10.50"));
		shopifyTransaction2.setStatus("FAILURE");
		shopifyTransaction2.setKind("refund_discrepancy");
		shopifyTransaction2.setGateway("manual");
		shopifyTransaction2.setCurrency(Currency.getInstance("USD"));
		shopifyTransaction2.setMaximumRefundable(new BigDecimal(15.99));

		shopifyRefund1.setTransactions(Arrays.asList(shopifyTransaction1, shopifyTransaction2));

		final ShopifyAdjustment shopifyAdjustment1 = new ShopifyAdjustment();
		shopifyAdjustment1.setId("1230");
		shopifyAdjustment1.setAmount(new BigDecimal("-12.00"));
		shopifyAdjustment1.setTaxAmount(new BigDecimal("-0.72"));
		shopifyAdjustment1.setReason("Shipping Refund");

		final ShopifyAdjustment shopifyAdjustment2 = new ShopifyAdjustment();
		shopifyAdjustment2.setId("4560");
		shopifyAdjustment2.setAmount(new BigDecimal("-10.00"));

		shopifyRefund1.setAdjustments(Arrays.asList(shopifyAdjustment1, shopifyAdjustment2));

		shopifyOrder1.setRefunds(Arrays.asList(shopifyRefund1));
		shopifyOrdersRoot.setOrders(Arrays.asList(shopifyOrder1));

		final String expectedResponseBodyString = getJsonString(ShopifyOrdersRoot.class, shopifyOrdersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final DateTime minimumCreationDateTime = SOME_DATE_TIME;

		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withParam(ShopifySdk.STATUS_QUERY_PARAMETER, ShopifySdk.ANY_STATUSES)
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, 50)
						.withParam(ShopifySdk.CREATED_AT_MIN_QUERY_PARAMETER, minimumCreationDateTime.toString())
						.withParam(ShopifySdk.CREATED_AT_MAX_QUERY_PARAMETER, maximumCreationDate.toString())
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withHeader("Link",
						"<https://some-store.myshopify.com/admin/api/2019-10/orders?page_info=123>; rel=\"previous\", <https://humdingers-business-of-the-americas.myshopify.com/admin/api/2019-10/orders?page_info=456>; rel=\"next\"")
						.withStatus(expectedStatusCode));

		final ShopifyPage<ShopifyOrder> shopifyOrders = shopifySdk.getOrders(minimumCreationDateTime,
				maximumCreationDate);

		assertEquals(shopifyOrder1.getId(), shopifyOrders.get(0).getId());
		assertEquals(shopifyOrder1.getEmail(), shopifyOrders.get(0).getEmail());

		assertEquals(2, shopifyOrders.get(0).getLineItems().get(0).getTaxLines().size());
		assertEquals(shopifyTaxLine1LineItem1.getRate(),
				shopifyOrders.get(0).getLineItems().get(0).getTaxLines().get(0).getRate());
		assertEquals(shopifyTaxLine1LineItem1.getPrice(),
				shopifyOrders.get(0).getLineItems().get(0).getTaxLines().get(0).getPrice());
		assertEquals(shopifyTaxLine1LineItem1.getTitle(),
				shopifyOrders.get(0).getLineItems().get(0).getTaxLines().get(0).getTitle());
		assertEquals(shopifyTaxLine1LineItem2.getRate(),
				shopifyOrders.get(0).getLineItems().get(0).getTaxLines().get(1).getRate());
		assertEquals(shopifyTaxLine1LineItem2.getPrice(),
				shopifyOrders.get(0).getLineItems().get(0).getTaxLines().get(1).getPrice());
		assertEquals(shopifyTaxLine1LineItem2.getTitle(),
				shopifyOrders.get(0).getLineItems().get(0).getTaxLines().get(1).getTitle());
		assertEquals(shopifyProperty1.getName(),
				shopifyOrders.get(0).getLineItems().get(0).getProperties().get(0).getName());
		assertEquals(shopifyProperty1.getValue(),
				shopifyOrders.get(0).getLineItems().get(0).getProperties().get(0).getValue());
		assertEquals(shopifyProperty2.getName(),
				shopifyOrders.get(0).getLineItems().get(0).getProperties().get(1).getName());
		assertEquals(shopifyProperty2.getValue(),
				shopifyOrders.get(0).getLineItems().get(0).getProperties().get(1).getValue());
		assertEquals(shopifyProperty3.getName(),
				shopifyOrders.get(0).getLineItems().get(0).getProperties().get(2).getName());
		assertEquals(shopifyProperty3.getValue(),
				shopifyOrders.get(0).getLineItems().get(0).getProperties().get(2).getValue());

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

		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(0).getId(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(0).getId());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(0).getAmount(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(0).getAmount());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(0).getMessage(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(0).getMessage());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(0).getStatus(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(0).getStatus());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(0).getKind(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(0).getKind());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(0).getGateway(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(0).getGateway());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(0).getCurrency(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(0).getCurrency());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(0).getMaximumRefundable(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(0).getMaximumRefundable());

		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(1).getId(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(1).getId());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(1).getAmount(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(1).getAmount());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(1).getMessage(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(1).getMessage());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(1).getStatus(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(1).getStatus());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(1).getKind(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(1).getKind());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(1).getGateway(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(1).getGateway());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(1).getCurrency(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(1).getCurrency());
		assertEquals(shopifyOrder1.getRefunds().get(0).getTransactions().get(1).getMaximumRefundable(),
				shopifyOrders.get(0).getRefunds().get(0).getTransactions().get(1).getMaximumRefundable());

		assertEquals(shopifyOrder1.getRefunds().get(0).getAdjustments().get(0).getId(),
				shopifyOrders.get(0).getRefunds().get(0).getAdjustments().get(0).getId());
		assertEquals(shopifyOrder1.getRefunds().get(0).getAdjustments().get(0).getAmount(),
				shopifyOrders.get(0).getRefunds().get(0).getAdjustments().get(0).getAmount());
		assertEquals(shopifyOrder1.getRefunds().get(0).getAdjustments().get(0).getTaxAmount(),
				shopifyOrders.get(0).getRefunds().get(0).getAdjustments().get(0).getTaxAmount());
		assertEquals(shopifyOrder1.getRefunds().get(0).getAdjustments().get(0).getReason(),
				shopifyOrders.get(0).getRefunds().get(0).getAdjustments().get(0).getReason());
		assertEquals(shopifyOrder1.getRefunds().get(0).getAdjustments().get(1).getId(),
				shopifyOrders.get(0).getRefunds().get(0).getAdjustments().get(1).getId());
		assertEquals(shopifyOrder1.getRefunds().get(0).getAdjustments().get(1).getAmount(),
				shopifyOrders.get(0).getRefunds().get(0).getAdjustments().get(1).getAmount());
		assertEquals(shopifyOrder1.getRefunds().get(0).getAdjustments().get(1).getTaxAmount(),
				shopifyOrders.get(0).getRefunds().get(0).getAdjustments().get(1).getTaxAmount());
		assertEquals(shopifyOrder1.getRefunds().get(0).getAdjustments().get(1).getReason(),
				shopifyOrders.get(0).getRefunds().get(0).getAdjustments().get(1).getReason());

		assertEquals(shopifyLineItem1.getSku(),
				shopifyOrders.get(0).getRefunds().get(0).getRefundLineItems().get(0).getLineItem().getSku());
		assertEquals("456", shopifyOrders.getNextPageInfo());
		assertEquals("123", shopifyOrders.getPreviousPageInfo());

	}

	@Test
	public void givenSomePageAndUpdatedAtMinOrdersWhenRetrievingUpdatedOrdersThenRetrieveUpdatedOrdersWithCorrectValues()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.toString();
		final ShopifyOrdersRoot shopifyOrdersRoot = new ShopifyOrdersRoot();
		final DateTime maximumUpdatedAtDate = DateTime.now(DateTimeZone.UTC);
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
		final DateTime minimumUpdatedAtDateTime = SOME_DATE_TIME;
		final DateTime maximumCreatedAtDateTime = SOME_DATE_TIME;
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withParam(ShopifySdk.STATUS_QUERY_PARAMETER, ShopifySdk.ANY_STATUSES)
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, 250)
						.withParam(ShopifySdk.UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDateTime.toString())
						.withParam(ShopifySdk.UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString())
						.withParam(ShopifySdk.CREATED_AT_MAX_QUERY_PARAMETER, maximumCreatedAtDateTime.toString())
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withHeader("Link",
						"<https://some-store.myshopify.com/admin/api/2019-10/orders?page_info=123>; rel=\"previous\", <https://humdingers-business-of-the-americas.myshopify.com/admin/api/2019-10/orders?page_info=456>; rel=\"next\"")
						.withStatus(expectedStatusCode));

		final ShopifyPage<ShopifyOrder> shopifyOrdersPage = shopifySdk.getUpdatedOrdersCreatedBefore(
				minimumUpdatedAtDateTime, maximumUpdatedAtDate, maximumCreatedAtDateTime, 250);

		final ShopifyOrder shopifyOrder = shopifyOrdersPage.get(0);
		assertEquals(shopifyOrder1.getId(), shopifyOrder.getId());
		assertEquals(shopifyOrder1.getEmail(), shopifyOrder.getEmail());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getId(), shopifyOrder.getFulfillments().get(0).getId());
		assertTrue(shopifyOrder1.getFulfillments().get(0).getCreatedAt()
				.compareTo(shopifyOrder.getFulfillments().get(0).getCreatedAt()) == 0);
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrl(),
				shopifyOrder.getFulfillments().get(0).getTrackingUrl());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrls(),
				shopifyOrder.getFulfillments().get(0).getTrackingUrls());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				shopifyOrder.getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				shopifyOrder.getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getSku(),
				shopifyOrder.getFulfillments().get(0).getLineItems().get(0).getSku());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getName(),
				shopifyOrder.getFulfillments().get(0).getLineItems().get(0).getName());

		assertEquals(shopifyOrder1.getRefunds().size(), shopifyOrder.getRefunds().size());
		assertTrue(shopifyOrder1.getRefunds().get(0).getCreatedAt()
				.compareTo(shopifyOrder.getRefunds().get(0).getCreatedAt()) == 0);
		assertEquals(shopifyOrder1.getRefunds().get(0).getId(), shopifyOrder.getRefunds().get(0).getId());
		assertEquals(shopifyOrder1.getRefunds().get(0).getNote(), shopifyOrder.getRefunds().get(0).getNote());
		assertEquals(shopifyOrder1.getRefunds().get(0).getOrderId(), shopifyOrder.getRefunds().get(0).getOrderId());
		assertTrue(shopifyOrder1.getRefunds().get(0).getProcessedAt()
				.compareTo(shopifyOrder.getRefunds().get(0).getProcessedAt()) == 0);

		assertEquals(shopifyOrder1.getRefunds().get(0).getRefundLineItems().get(0).getLineItemId(),
				shopifyOrder.getRefunds().get(0).getRefundLineItems().get(0).getLineItemId());
		assertEquals(shopifyOrder1.getRefunds().get(0).getRefundLineItems().get(0).getLocationId(),
				shopifyOrder.getRefunds().get(0).getRefundLineItems().get(0).getLocationId());
		assertEquals(shopifyOrder1.getRefunds().get(0).getRefundLineItems().get(0).getQuantity(),
				shopifyOrder.getRefunds().get(0).getRefundLineItems().get(0).getQuantity());
		assertEquals(shopifyOrder1.getRefunds().get(0).getRefundLineItems().get(0).getRestockType(),
				shopifyOrder.getRefunds().get(0).getRefundLineItems().get(0).getRestockType());
		assertEquals(shopifyOrder1.getRefunds().get(0).getRefundLineItems().get(0).getSubtotal(),
				shopifyOrder.getRefunds().get(0).getRefundLineItems().get(0).getSubtotal());
		assertEquals(shopifyOrder1.getRefunds().get(0).getRefundLineItems().get(0).getTotalTax(),
				shopifyOrder.getRefunds().get(0).getRefundLineItems().get(0).getTotalTax());

		assertEquals(shopifyLineItem1.getSku(),
				shopifyOrder.getRefunds().get(0).getRefundLineItems().get(0).getLineItem().getSku());
		assertEquals("456", shopifyOrdersPage.getNextPageInfo());
		assertEquals("123", shopifyOrdersPage.getPreviousPageInfo());
	}

	@Test
	public void givenSomePageAndCreatedAtMinAndCreatedAtMaxOrdersAndAppIdWhenRetrievingOrdersThenRetrieveOrdersWithCorrectValues()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.toString();
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
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, 50)
						.withParam(ShopifySdk.CREATED_AT_MIN_QUERY_PARAMETER, minimumCreationDateTime.toString())
						.withParam(ShopifySdk.CREATED_AT_MAX_QUERY_PARAMETER, maximumCreationDate.toString())
						.withParam(ShopifySdk.ATTRIBUTION_APP_ID_QUERY_PARAMETER, "current").withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withHeader("Link",
						"<https://some-store.myshopify.com/admin/api/2019-10/orders?page_info=123>; rel=\"previous\", <https://humdingers-business-of-the-americas.myshopify.com/admin/api/2019-10/orders?page_info=456>; rel=\"next\"")
						.withStatus(expectedStatusCode));

		final ShopifyPage<ShopifyOrder> shopifyOrdersPage = shopifySdk.getOrders(minimumCreationDateTime,
				maximumCreationDate, "current");

		final ShopifyOrder shopifyOrder = shopifyOrdersPage.get(0);
		assertEquals(shopifyOrder1.getId(), shopifyOrder.getId());
		assertEquals(shopifyOrder1.getEmail(), shopifyOrder.getEmail());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getId(), shopifyOrder.getFulfillments().get(0).getId());
		assertTrue(shopifyOrder1.getFulfillments().get(0).getCreatedAt()
				.compareTo(shopifyOrder.getFulfillments().get(0).getCreatedAt()) == 0);
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrl(),
				shopifyOrder.getFulfillments().get(0).getTrackingUrl());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrls(),
				shopifyOrder.getFulfillments().get(0).getTrackingUrls());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				shopifyOrder.getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				shopifyOrder.getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getSku(),
				shopifyOrder.getFulfillments().get(0).getLineItems().get(0).getSku());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getName(),
				shopifyOrder.getFulfillments().get(0).getLineItems().get(0).getName());
		assertEquals("456", shopifyOrdersPage.getNextPageInfo());
		assertEquals("123", shopifyOrdersPage.getPreviousPageInfo());
	}

	@Test
	public void givenSomeOrderIdWhenClosingOrderThenCloseAndReturnOrder() throws JsonProcessingException {
		final String someOrderId = "1234";

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.append(FORWARD_SLASH).append(someOrderId).append(FORWARD_SLASH).append(ShopifySdk.CLOSE).toString();

		final ShopifyOrderRoot shopifyOrderRoot = new ShopifyOrderRoot();
		final ShopifyOrder shopifyOrder = new ShopifyOrder();
		shopifyOrder.setId(someOrderId);
		shopifyOrderRoot.setOrder(shopifyOrder);
		final String expectedResponseBodyString = getJsonString(ShopifyOrderRoot.class, shopifyOrderRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final StringBodyCapture actualRequestBody = new StringBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.POST).capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyOrder actualShopifyOrder = shopifySdk.closeOrder(someOrderId);

		assertEquals(
				"{\"number\":0,\"total_weight\":0,\"taxes_included\":false,\"buyer_accepts_marketing\":false,\"line_items\":[],\"fulfillments\":[],\"billing_address\":{},\"shipping_address\":{},\"customer\":{\"accepts_marketing\":false,\"orders_count\":0},\"shipping_lines\":[],\"tax_lines\":[],\"note_attributes\":[],\"refunds\":[],\"metafields\":[]}",
				actualRequestBody.getContent());
		assertNotNull(actualShopifyOrder);
		assertEquals(someOrderId, actualShopifyOrder.getId());
	}

	@Test
	public void givenSomeOrderIdAndReasonWhenCancelingOrderThenCancelAndReturnOrder() throws JsonProcessingException {
		final String someOrderId = "1234";
		final String someCanceledReason = "Customer didn't like the quality of the product";

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.append(FORWARD_SLASH).append(someOrderId).append(FORWARD_SLASH).append(ShopifySdk.CANCEL).toString();

		final ShopifyOrderRoot shopifyOrderRoot = new ShopifyOrderRoot();
		final ShopifyOrder shopifyOrder = new ShopifyOrder();
		shopifyOrder.setId(someOrderId);
		shopifyOrderRoot.setOrder(shopifyOrder);
		final String expectedResponseBodyString = getJsonString(ShopifyOrderRoot.class, shopifyOrderRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.POST).capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyOrder actualShopifyOrder = shopifySdk.cancelOrder(someOrderId, someCanceledReason);

		assertEquals(someCanceledReason, actualRequestBody.getContent().get("reason").asText());
		assertNotNull(actualShopifyOrder);
		assertEquals(someOrderId, actualShopifyOrder.getId());
	}

	@Test
	public void givenSomePageAndCreatedAtMinOrdersWhenRetrievingOrdersThenRetrieveOrdersWithCorrectValues()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.toString();

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
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, 50)
						.withParam(ShopifySdk.CREATED_AT_MIN_QUERY_PARAMETER, minimumCreationDateTime.toString())
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withHeader("Link",
						"<https://some-store.myshopify.com/admin/api/2019-10/orders?page_info=123>; rel=\"previous\", <https://humdingers-business-of-the-americas.myshopify.com/admin/api/2019-10/orders?page_info=456>; rel=\"next\"")
						.withStatus(expectedStatusCode));

		final ShopifyPage<ShopifyOrder> shopifyOrdersPage = shopifySdk.getOrders(minimumCreationDateTime);

		final ShopifyOrder actualShopifyOrder1 = shopifyOrdersPage.get(0);
		assertEquals(shopifyOrder1.getId(), actualShopifyOrder1.getId());
		assertEquals(shopifyOrder1.getEmail(), actualShopifyOrder1.getEmail());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getId(),
				actualShopifyOrder1.getFulfillments().get(0).getId());
		assertTrue(shopifyOrder1.getFulfillments().get(0).getCreatedAt()
				.compareTo(actualShopifyOrder1.getFulfillments().get(0).getCreatedAt()) == 0);
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrl(),
				actualShopifyOrder1.getFulfillments().get(0).getTrackingUrl());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrls(),
				actualShopifyOrder1.getFulfillments().get(0).getTrackingUrls());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				actualShopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				actualShopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getSku(),
				actualShopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getSku());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getName(),
				actualShopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getName());
		assertEquals("456", shopifyOrdersPage.getNextPageInfo());
		assertEquals("123", shopifyOrdersPage.getPreviousPageInfo());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainWhenGettingShopifyLocationThenReturnShopifyLocations()
			throws JsonProcessingException {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.LOCATIONS)
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

	@Test(expected = ShopifyClientException.class)
	public void givenSomeValidAccessTokenAndSubdomainAndRequestFailsWhenGettingShopifyLocationThenReturnShopifyLocations()
			throws JsonProcessingException {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.LOCATIONS)
				.append(ShopifySdk.JSON).toString();
		final ShopifyLocationsRoot shopifyLocationsRoot = new ShopifyLocationsRoot();
		final ShopifyLocation shopifyLocation1 = buildShopifyLocation("Some address1", "Some address2", "78237482374",
				"Warehouse 1");
		final ShopifyLocation shopifyLocation2 = buildShopifyLocation("Some address3", "Some address4", "987897984",
				"Warehouse 2");
		final List<ShopifyLocation> shopifyLocations = Arrays.asList(shopifyLocation1, shopifyLocation2);
		shopifyLocationsRoot.setLocations(shopifyLocations);

		final String expectedResponseBodyString = getJsonString(ShopifyLocationsRoot.class, shopifyLocationsRoot);

		final Status expectedStatus = Status.INTERNAL_SERVER_ERROR;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode))
				.anyTimes();

		shopifySdk.getLocations();

	}

	@Test(expected = ShopifyErrorResponseException.class)
	public void givenSomeExceptionIsThrownWhenGettingShopifyLocationsThenExpectShopifyClientException()
			throws JsonProcessingException {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.LOCATIONS)
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
			throws JsonProcessingException {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH)
				.append(ShopifySdk.INVENTORY_LEVELS).append("/").append(ShopifySdk.SET).toString();
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
	public void givenSomeProductCreationRequestWhenCreatingProductThenCreateAndReturnProduct()
			throws JsonProcessingException {
		final String expectedCreationPath = new StringBuilder().append(FORWARD_SLASH)
				.append(ShopifySdk.API_VERSION_PREFIX).append(FORWARD_SLASH).append(SOME_API_VERSION)
				.append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS).toString();
		final String expectedUpdatePath = new StringBuilder().append(FORWARD_SLASH)
				.append(ShopifySdk.API_VERSION_PREFIX).append(FORWARD_SLASH).append(SOME_API_VERSION)
				.append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS).append(FORWARD_SLASH).append("123").toString();
		final ShopifyProductRoot shopifyProductRoot = new ShopifyProductRoot();
		final ShopifyProduct shopifyProduct = new ShopifyProduct();
		shopifyProduct.setId("123");

		final Image image = new Image();
		image.setName("Some image 1");
		image.setPosition(0);
		image.setProductId("123");
		image.setSource("http://channelape.com/1.png");
		shopifyProduct.setImages(Arrays.asList(image));
		shopifyProduct.setProductType("Shoes");
		shopifyProduct.setBodyHtml("Some Description");
		shopifyProduct.setTags(new HashSet<>(Arrays.asList("Shoes", "Apparel")));
		shopifyProduct.setPublished(true);
		shopifyProduct.setTitle("Some Title");
		shopifyProduct.setVendor("Some Vendor");
		shopifyProduct.setPublishedAt("2018-01-01T00:00:00");

		final ShopifyVariant shopifyVariant = new ShopifyVariant();
		shopifyVariant.setId("999");
		shopifyProduct.setVariants(Arrays.asList(shopifyVariant));
		shopifyProductRoot.setProduct(shopifyProduct);

		final String expectedResponseBodyString = getJsonString(ShopifyProductRoot.class, shopifyProductRoot);

		final Status expectedCreationStatus = Status.CREATED;
		final int expectedCreationStatusCode = expectedCreationStatus.getStatusCode();

		final JsonBodyCapture actualCreateRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedCreationPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.POST).capturingBodyIn(actualCreateRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(expectedCreationStatusCode));

		final Status expectedUpdatedStatus = Status.OK;
		final int expectedUpdateStatusCode = expectedUpdatedStatus.getStatusCode();
		final JsonBodyCapture actualUpdateRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedUpdatePath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.PUT).capturingBodyIn(actualUpdateRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(expectedUpdateStatusCode));

		final BigDecimal somePrice = BigDecimal.valueOf(42.11);
		final ShopifyVariantCreationRequest shopifyVariantCreationRequest = ShopifyVariantCreationRequest.newBuilder()
				.withPrice(somePrice).withCompareAtPrice(somePrice).withSku("ABC-123").withBarcode("XYZ-123")
				.withWeight(somePrice).withAvailable(13).withFirstOption("Shoes").withSecondOption("Red")
				.withThirdOption("Green").withImageSource("http://channelape.com/1.png")
				.withDefaultInventoryManagement().withDefaultInventoryPolicy().withDefaultFulfillmentService()
				.withRequiresShipping(true).withTaxable(true).build();
		final ShopifyProductCreationRequest shopifyProductCreationRequest = ShopifyProductCreationRequest.newBuilder()
				.withTitle("Some Product Title").withMetafieldsGlobalTitleTag("Some Metafields Global Title Tag")
				.withProductType("Shoes").withBodyHtml("Some Description")
				.withMetafieldsGlobalDescriptionTag("Some Metafields Tag").withVendor("Some Vendor")
				.withTags(Collections.emptySet()).withSortedOptionNames(Collections.emptyList())
				.withImageSources(Arrays.asList("http://channelape.com/1.png", "http://channelape.com/2.png"))
				.withVariantCreationRequests(Arrays.asList(shopifyVariantCreationRequest)).withPublished(true).build();

		final ShopifyProduct actualShopifyProduct = shopifySdk.createProduct(shopifyProductCreationRequest);

		assertEquals(shopifyProductCreationRequest.getRequest().getVendor(),
				actualCreateRequestBody.getContent().get("product").get("vendor").asText());
		assertEquals(shopifyProductCreationRequest.getRequest().getMetafieldsGlobalTitleTag(),
				actualCreateRequestBody.getContent().get("product").get("metafields_global_title_tag").asText());
		assertEquals(shopifyProductCreationRequest.getRequest().getMetafieldsGlobalDescriptionTag(),
				actualCreateRequestBody.getContent().get("product").get("metafields_global_description_tag").asText());
		assertEquals(shopifyProductCreationRequest.getRequest().getProductType(),
				actualCreateRequestBody.getContent().get("product").get("product_type").asText());
		assertEquals(shopifyProductCreationRequest.getRequest().getPublishedAt(),
				actualCreateRequestBody.getContent().get("product").get("published_at").asText());
		assertEquals(shopifyProductCreationRequest.getRequest().getTitle(),
				actualCreateRequestBody.getContent().get("product").get("title").asText());
		assertEquals(shopifyProductCreationRequest.getRequest().getImages().get(0).getName(),
				actualCreateRequestBody.getContent().get("product").get("images").get(0).get("name"));
		assertEquals(shopifyProductCreationRequest.getRequest().getImages().get(0).getPosition(),
				actualCreateRequestBody.getContent().get("product").get("images").get(0).get("position").asInt());
		assertEquals(shopifyVariantCreationRequest.getRequest().getBarcode(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("barcode").asText());
		assertEquals(shopifyVariantCreationRequest.getRequest().getSku(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("sku").asText());
		assertEquals(somePrice,
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("price").decimalValue());
		assertEquals(shopifyVariantCreationRequest.getRequest().getGrams(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("grams").asLong());
		assertNull(actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("available"));
		assertEquals(shopifyVariantCreationRequest.getRequest().getOption1(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("option1").asText());
		assertEquals(shopifyVariantCreationRequest.getRequest().getOption2(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("option2").asText());
		assertEquals(shopifyVariantCreationRequest.getRequest().getOption3(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("option3").asText());
		assertEquals(shopifyVariantCreationRequest.getRequest().isRequiresShipping(), actualCreateRequestBody
				.getContent().get("product").get("variants").get(0).get("requires_shipping").asBoolean());
		assertEquals(shopifyVariantCreationRequest.getRequest().isTaxable(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("taxable").asBoolean());

		assertNotNull(actualShopifyProduct);
		assertEquals(shopifyProduct.getId(), actualShopifyProduct.getId());
		assertEquals(shopifyProduct.getBodyHtml(), actualShopifyProduct.getBodyHtml());
		assertEquals(shopifyProduct.getImage(), actualShopifyProduct.getImage());
		assertEquals(shopifyProduct.getImages().get(0).getId(), actualShopifyProduct.getImages().get(0).getId());
		assertEquals(shopifyProduct.getImages().get(0).getName(), actualShopifyProduct.getImages().get(0).getName());
		assertEquals(shopifyProduct.getImages().get(0).getPosition(),
				actualShopifyProduct.getImages().get(0).getPosition());
		assertEquals(shopifyProduct.getImages().get(0).getSource(),
				actualShopifyProduct.getImages().get(0).getSource());
		assertEquals(shopifyProduct.getMetafieldsGlobalDescriptionTag(),
				actualShopifyProduct.getMetafieldsGlobalDescriptionTag());
		assertEquals(shopifyProduct.getProductType(), actualShopifyProduct.getProductType());
		assertEquals(shopifyProduct.getPublishedAt(), actualShopifyProduct.getPublishedAt());
		assertTrue(shopifyProduct.getTags().containsAll(actualShopifyProduct.getTags()));
		assertEquals(shopifyProduct.getTitle(), actualShopifyProduct.getTitle());
		assertEquals(shopifyProduct.getVariants().get(0).getId(), actualShopifyProduct.getVariants().get(0).getId());
		assertEquals(shopifyProduct.getVariants().get(0).getAvailable(),
				actualShopifyProduct.getVariants().get(0).getAvailable());
		assertEquals(shopifyProduct.getVariants().get(0).getOption1(),
				actualShopifyProduct.getVariants().get(0).getOption1());
		assertEquals(shopifyProduct.getVariants().get(0).getOption2(),
				actualShopifyProduct.getVariants().get(0).getOption2());
		assertEquals(shopifyProduct.getVariants().get(0).getOption3(),
				actualShopifyProduct.getVariants().get(0).getOption3());
	}

	@Test
	public void givenSomeProductUpdateRequestWhenUpdatingProductThenUpdateAndReturnProduct()
			throws JsonProcessingException {
		final String expectedCreationPath = new StringBuilder().append(FORWARD_SLASH)
				.append(ShopifySdk.API_VERSION_PREFIX).append(FORWARD_SLASH).append(SOME_API_VERSION)
				.append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS).append(FORWARD_SLASH).append("123").toString();
		final String expectedUpdatePath = new StringBuilder().append(FORWARD_SLASH)
				.append(ShopifySdk.API_VERSION_PREFIX).append(FORWARD_SLASH).append(SOME_API_VERSION)
				.append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS).append(FORWARD_SLASH).append("123").toString();
		final ShopifyProductRoot shopifyProductRoot = new ShopifyProductRoot();
		final ShopifyProduct shopifyProduct = new ShopifyProduct();
		shopifyProduct.setId("123");

		final Image image = new Image();
		image.setName("Some image 1");
		image.setPosition(0);
		image.setProductId("123");
		image.setSource("http://channelape.com/1.png");
		shopifyProduct.setImages(Arrays.asList(image));
		shopifyProduct.setProductType("Shoes");
		shopifyProduct.setBodyHtml("Some Description");
		shopifyProduct.setTags(new HashSet<>(Arrays.asList("Shoes", "Apparel")));
		shopifyProduct.setPublished(true);
		shopifyProduct.setTitle("Some Title");
		shopifyProduct.setVendor("Some Vendor");
		shopifyProduct.setPublishedAt("2018-01-01T00:00:00");
		shopifyProduct.setMetafieldsGlobalDescriptionTag("Some tags");
		shopifyProduct.setMetafieldsGlobalTitleTag("some title tags");

		final ShopifyVariant shopifyVariant = new ShopifyVariant();
		shopifyVariant.setId("999");
		shopifyVariant.setBarcode("XYZ-123");
		shopifyVariant.setSku("ABC-123");
		shopifyVariant.setImageId("1");
		shopifyVariant.setPrice(BigDecimal.valueOf(42.11));
		shopifyVariant.setGrams(12);
		shopifyVariant.setAvailable(3L);
		shopifyVariant.setRequiresShipping(true);
		shopifyVariant.setTaxable(true);
		shopifyVariant.setOption1("Red");
		shopifyVariant.setOption2("Blue");
		shopifyVariant.setOption3("GREEN");
		shopifyProduct.setVariants(Arrays.asList(shopifyVariant));
		shopifyProductRoot.setProduct(shopifyProduct);

		final String expectedResponseBodyString = getJsonString(ShopifyProductRoot.class, shopifyProductRoot);

		final Status expectedCreationStatus = Status.OK;
		final int expectedCreationStatusCode = expectedCreationStatus.getStatusCode();

		final JsonBodyCapture actualCreateRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedCreationPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.PUT).capturingBodyIn(actualCreateRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(expectedCreationStatusCode));

		final Status expectedUpdatedStatus = Status.OK;
		final int expectedUpdateStatusCode = expectedUpdatedStatus.getStatusCode();
		final JsonBodyCapture actualUpdateRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedUpdatePath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.PUT).capturingBodyIn(actualUpdateRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(expectedUpdateStatusCode));

		final ShopifyProductUpdateRequest shopifyProductUpdateRequest = ShopifyProductUpdateRequest.newBuilder()
				.withCurrentShopifyProduct(shopifyProduct).withSameTitle().withSameMetafieldsGlobalTitleTag()
				.withSameProductType().withSameBodyHtml().withSameMetafieldsGlobalDescriptionTag().withSameVendor()
				.withSameTags().withSameOptions().withSameImages().withSameVariants().withPublished(true).build();

		final ShopifyProduct actualShopifyProduct = shopifySdk.updateProduct(shopifyProductUpdateRequest);

		assertEquals(shopifyProduct.getVendor(),
				actualCreateRequestBody.getContent().get("product").get("vendor").asText());
		assertEquals(shopifyProduct.getMetafieldsGlobalTitleTag(),
				actualCreateRequestBody.getContent().get("product").get("metafields_global_title_tag").asText());
		assertEquals(shopifyProduct.getMetafieldsGlobalDescriptionTag(),
				actualCreateRequestBody.getContent().get("product").get("metafields_global_description_tag").asText());
		assertEquals(shopifyProduct.getProductType(),
				actualCreateRequestBody.getContent().get("product").get("product_type").asText());
		assertEquals(shopifyProduct.getPublishedAt(),
				actualCreateRequestBody.getContent().get("product").get("published_at").asText());
		assertEquals(shopifyProduct.getTitle(),
				actualCreateRequestBody.getContent().get("product").get("title").asText());
		assertEquals(shopifyProduct.getImages().get(0).getName(),
				actualCreateRequestBody.getContent().get("product").get("images").get(0).get("name").asText());
		assertEquals(shopifyProduct.getImages().get(0).getPosition(),
				actualCreateRequestBody.getContent().get("product").get("images").get(0).get("position").asInt());
		assertEquals(shopifyProduct.getVariants().get(0).getBarcode(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("barcode").asText());
		assertEquals(shopifyProduct.getVariants().get(0).getSku(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("sku").asText());
		assertEquals(shopifyProduct.getVariants().get(0).getPrice(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("price").decimalValue());
		assertEquals(shopifyProduct.getVariants().get(0).getGrams(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("grams").asLong());
		assertNull(actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("available"));
		assertEquals(shopifyProduct.getVariants().get(0).getOption1(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("option1").asText());
		assertEquals(shopifyProduct.getVariants().get(0).getOption2(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("option2").asText());
		assertEquals(shopifyProduct.getVariants().get(0).getOption3(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("option3").asText());
		assertEquals(shopifyProduct.getVariants().get(0).isRequiresShipping(), actualCreateRequestBody.getContent()
				.get("product").get("variants").get(0).get("requires_shipping").asBoolean());
		assertEquals(shopifyProduct.getVariants().get(0).isTaxable(),
				actualCreateRequestBody.getContent().get("product").get("variants").get(0).get("taxable").asBoolean());

		assertNotNull(actualShopifyProduct);
		assertEquals(shopifyProduct.getId(), actualShopifyProduct.getId());
		assertEquals(shopifyProduct.getBodyHtml(), actualShopifyProduct.getBodyHtml());
		assertEquals(shopifyProduct.getImage(), actualShopifyProduct.getImage());
		assertEquals(shopifyProduct.getImages().get(0).getId(), actualShopifyProduct.getImages().get(0).getId());
		assertEquals(shopifyProduct.getImages().get(0).getName(), actualShopifyProduct.getImages().get(0).getName());
		assertEquals(shopifyProduct.getImages().get(0).getPosition(),
				actualShopifyProduct.getImages().get(0).getPosition());
		assertEquals(shopifyProduct.getImages().get(0).getSource(),
				actualShopifyProduct.getImages().get(0).getSource());
		assertEquals(shopifyProduct.getMetafieldsGlobalDescriptionTag(),
				actualShopifyProduct.getMetafieldsGlobalDescriptionTag());
		assertEquals(shopifyProduct.getProductType(), actualShopifyProduct.getProductType());
		assertEquals(shopifyProduct.getPublishedAt(), actualShopifyProduct.getPublishedAt());
		assertTrue(shopifyProduct.getTags().containsAll(actualShopifyProduct.getTags()));
		assertEquals(shopifyProduct.getTitle(), actualShopifyProduct.getTitle());
		assertEquals(shopifyProduct.getVariants().get(0).getId(), actualShopifyProduct.getVariants().get(0).getId());

		assertEquals(shopifyProduct.getVariants().get(0).getOption1(),
				actualShopifyProduct.getVariants().get(0).getOption1());
		assertEquals(shopifyProduct.getVariants().get(0).getOption2(),
				actualShopifyProduct.getVariants().get(0).getOption2());
		assertEquals(shopifyProduct.getVariants().get(0).getOption3(),
				actualShopifyProduct.getVariants().get(0).getOption3());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestWhenRetrievingOrderMetafieldsThenReturnOrderMetafields()
			throws JsonProcessingException {

		final Metafield metafield = new Metafield();
		metafield.setKey("channelape_order_id");
		metafield.setValue("8fb0fb40-ab18-439e-bc6e-394b63ff1819");
		metafield.setNamespace("channelape");
		metafield.setOwnerId("1234");
		metafield.setType(MetafieldType.SINGLE_LINE_TEXT);
		metafield.setOwnerResource("order");

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.append("/1234/").append(ShopifySdk.METAFIELDS).toString();
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
		assertEquals(metafield.getType(), actualMetafields.get(0).getType());
		assertEquals(metafield.getNamespace(), actualMetafields.get(0).getNamespace());
		assertEquals(metafield.getOwnerId(), actualMetafields.get(0).getOwnerId());
		assertEquals(metafield.getOwnerResource(), actualMetafields.get(0).getOwnerResource());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestWhenCancelingFulfillmentsThenCancelFulfillments()
			throws JsonProcessingException {

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId("some_line_item_id");
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.append("/1234/").append(ShopifySdk.FULFILLMENTS).append("/4567/").append(ShopifySdk.CANCEL).toString();
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

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndProductIdWhenGettingProductThenGetProduct()
			throws JsonProcessingException {

		final ShopifyProductRoot shopifyProductRoot = new ShopifyProductRoot();
		final ShopifyProduct shopifyProduct = new ShopifyProduct();
		shopifyProduct.setId("123");

		final Image image = new Image();
		image.setName("Some image 1");
		image.setPosition(0);
		image.setProductId("123");
		image.setSource("http://channelape.com/1.png");
		shopifyProduct.setImages(Arrays.asList(image));
		shopifyProduct.setProductType("Shoes");
		shopifyProduct.setBodyHtml("Some Description");
		shopifyProduct.setTags(new HashSet<>(Arrays.asList("Shoes", "Apparel")));
		shopifyProduct.setPublished(true);
		shopifyProduct.setTitle("Some Title");
		shopifyProduct.setVendor("Some Vendor");
		shopifyProduct.setPublishedAt("2018-01-01T00:00:00");
		shopifyProduct.setMetafieldsGlobalDescriptionTag("Some tags");
		shopifyProduct.setMetafieldsGlobalTitleTag("some title tags");

		final ShopifyVariant shopifyVariant = new ShopifyVariant();
		shopifyVariant.setId("999");
		shopifyVariant.setBarcode("XYZ-123");
		shopifyVariant.setSku("ABC-123");
		shopifyVariant.setImageId("1");
		shopifyVariant.setPrice(BigDecimal.valueOf(42.11));
		shopifyVariant.setGrams(12);
		shopifyVariant.setAvailable(3L);
		shopifyVariant.setRequiresShipping(true);
		shopifyVariant.setTaxable(true);
		shopifyVariant.setOption1("Red");
		shopifyVariant.setOption2("Blue");
		shopifyVariant.setOption3("GREEN");
		shopifyProduct.setVariants(Arrays.asList(shopifyVariant));
		shopifyProductRoot.setProduct(shopifyProduct);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
				.append(FORWARD_SLASH).append("123").toString();

		final String expectedResponseBodyString = getJsonString(ShopifyProductRoot.class, shopifyProductRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyProduct actualShopifyProduct = shopifySdk.getProduct("123");
		assertNotNull(actualShopifyProduct);
		assertEquals(shopifyProduct.getId(), actualShopifyProduct.getId());
		assertEquals(shopifyProduct.getBodyHtml(), actualShopifyProduct.getBodyHtml());
		assertEquals(shopifyProduct.getImage(), actualShopifyProduct.getImage());
		assertEquals(shopifyProduct.getImages().get(0).getId(), actualShopifyProduct.getImages().get(0).getId());
		assertEquals(shopifyProduct.getImages().get(0).getName(), actualShopifyProduct.getImages().get(0).getName());
		assertEquals(shopifyProduct.getImages().get(0).getPosition(),
				actualShopifyProduct.getImages().get(0).getPosition());
		assertEquals(shopifyProduct.getImages().get(0).getSource(),
				actualShopifyProduct.getImages().get(0).getSource());
		assertEquals(shopifyProduct.getMetafieldsGlobalDescriptionTag(),
				actualShopifyProduct.getMetafieldsGlobalDescriptionTag());
		assertEquals(shopifyProduct.getProductType(), actualShopifyProduct.getProductType());
		assertEquals(shopifyProduct.getPublishedAt(), actualShopifyProduct.getPublishedAt());
		assertTrue(shopifyProduct.getTags().containsAll(actualShopifyProduct.getTags()));
		assertEquals(shopifyProduct.getTitle(), actualShopifyProduct.getTitle());
		assertEquals(shopifyProduct.getVariants().get(0).getId(), actualShopifyProduct.getVariants().get(0).getId());

		assertEquals(shopifyProduct.getVariants().get(0).getOption1(),
				actualShopifyProduct.getVariants().get(0).getOption1());
		assertEquals(shopifyProduct.getVariants().get(0).getOption2(),
				actualShopifyProduct.getVariants().get(0).getOption2());
		assertEquals(shopifyProduct.getVariants().get(0).getOption3(),
				actualShopifyProduct.getVariants().get(0).getOption3());

	}

	@Test
	public void givenSomeProductIdWhenRetrievingProductMetaFieldsThenRetrieveProductMetafields()
			throws JsonProcessingException {
		final MetafieldsRoot metafieldsRoot = new MetafieldsRoot();
		final Metafield metafield1 = new Metafield();
		metafield1.setCreatedAt(new DateTime());
		metafield1.setId("123");
		metafield1.setKey("channelape_product_id");
		metafield1.setNamespace("channelape");
		metafield1.setOwnerId("123");
		metafield1.setOwnerResource("product");
		metafield1.setValue("38728743");

		final Metafield metafield2 = new Metafield();
		metafield2.setCreatedAt(new DateTime());
		metafield2.setId("123");
		metafield2.setKey("channelape_variant_id");
		metafield2.setNamespace("channelape");
		metafield2.setOwnerId("123");
		metafield2.setOwnerResource("product");
		metafield2.setValue("87987456");

		metafieldsRoot.setMetafields(Arrays.asList(metafield1, metafield2));
		final String expectedImageResponseBodyString = getJsonString(MetafieldsRoot.class, metafieldsRoot);

		final String expectedImagePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
				.append(FORWARD_SLASH).append("123").append(FORWARD_SLASH).append(ShopifySdk.METAFIELDS).toString();

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedImagePath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET),
				giveResponse(expectedImageResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(expectedStatusCode));

		final List<Metafield> productMetafields = shopifySdk.getProductMetafields("123");
		assertNotNull(productMetafields);
		assertEquals(metafield1.getId(), productMetafields.get(0).getId());
		assertEquals(0, metafield1.getCreatedAt().compareTo(productMetafields.get(0).getCreatedAt()));
		assertEquals(metafield1.getKey(), productMetafields.get(0).getKey());
		assertEquals(metafield1.getNamespace(), productMetafields.get(0).getNamespace());
		assertEquals(metafield1.getOwnerId(), productMetafields.get(0).getOwnerId());
		assertEquals(metafield1.getOwnerResource(), productMetafields.get(0).getOwnerResource());
		assertEquals(metafield1.getUpdatedAt(), productMetafields.get(0).getUpdatedAt());
		assertEquals(metafield1.getValue(), productMetafields.get(0).getValue());
		assertEquals(metafield1.getType(), productMetafields.get(0).getType());

		assertEquals(metafield2.getId(), productMetafields.get(1).getId());
		assertEquals(0, metafield2.getCreatedAt().compareTo(productMetafields.get(1).getCreatedAt()));
		assertEquals(metafield2.getKey(), productMetafields.get(1).getKey());
		assertEquals(metafield2.getNamespace(), productMetafields.get(1).getNamespace());
		assertEquals(metafield2.getOwnerId(), productMetafields.get(1).getOwnerId());
		assertEquals(metafield2.getOwnerResource(), productMetafields.get(1).getOwnerResource());
		assertEquals(metafield2.getUpdatedAt(), productMetafields.get(1).getUpdatedAt());
		assertEquals(metafield2.getValue(), productMetafields.get(1).getValue());
		assertEquals(metafield2.getType(), productMetafields.get(1).getType());
	}

	@Test
	public void givenSomeOrderIdWhenRetrievingOrderThenRetrieveOrder() throws JsonProcessingException {
		final ShopifyOrderRoot shopifyOrderRoot = new ShopifyOrderRoot();

		final ShopifyOrder shopifyOrder = new ShopifyOrder();
		shopifyOrder.setId("123");
		shopifyOrderRoot.setOrder(shopifyOrder);
		final String expectedImageResponseBodyString = getJsonString(ShopifyOrderRoot.class, shopifyOrderRoot);

		final String expectedImagePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.append(FORWARD_SLASH).append("123").toString();

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedImagePath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET),
				giveResponse(expectedImageResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(expectedStatusCode));

		final ShopifyOrder actualShopifyOrder = shopifySdk.getOrder("123");
		assertEquals("123", actualShopifyOrder.getId());

	}

	@Test
	public void givenSomePageSizeWhenRetrievingOrdersThenRetrieveOrdersWithCorrectValues()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.toString();

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
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withParam(ShopifySdk.STATUS_QUERY_PARAMETER, ShopifySdk.ANY_STATUSES)
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, 50).withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withHeader("Link",
						"<https://some-store.myshopify.com/admin/api/2019-10/orders?page_info=123>; rel=\"previous\", <https://humdingers-business-of-the-americas.myshopify.com/admin/api/2019-10/orders?page_info=456>; rel=\"next\"")
						.withStatus(expectedStatusCode));

		final ShopifyPage<ShopifyOrder> shopifyOrdersPage = shopifySdk.getOrders();

		final ShopifyOrder shopifyOrder = shopifyOrdersPage.get(0);
		assertEquals(shopifyOrder1.getId(), shopifyOrder.getId());
		assertEquals(shopifyOrder1.getEmail(), shopifyOrder.getEmail());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getId(), shopifyOrder.getFulfillments().get(0).getId());
		assertTrue(shopifyOrder1.getFulfillments().get(0).getCreatedAt()
				.compareTo(shopifyOrder.getFulfillments().get(0).getCreatedAt()) == 0);
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrl(),
				shopifyOrder.getFulfillments().get(0).getTrackingUrl());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getTrackingUrls(),
				shopifyOrder.getFulfillments().get(0).getTrackingUrls());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				shopifyOrder.getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getId(),
				shopifyOrder.getFulfillments().get(0).getLineItems().get(0).getId());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getSku(),
				shopifyOrder.getFulfillments().get(0).getLineItems().get(0).getSku());
		assertEquals(shopifyOrder1.getFulfillments().get(0).getLineItems().get(0).getName(),
				shopifyOrder.getFulfillments().get(0).getLineItems().get(0).getName());

		assertEquals("456", shopifyOrdersPage.getNextPageInfo());
		assertEquals("123", shopifyOrdersPage.getPreviousPageInfo());
	}

	@Test
	public void givenShopWithNoOrdersAndPage1And197PageSizeWhenRetrievingOrdersThenReturnNoOrders()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.toString();

		final ShopifyOrdersRoot shopifyOrdersRoot = new ShopifyOrdersRoot();

		final String expectedResponseBodyString = getJsonString(ShopifyOrdersRoot.class, shopifyOrdersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final int pageSize = 197;
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withParam(ShopifySdk.STATUS_QUERY_PARAMETER, ShopifySdk.ANY_STATUSES)
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, pageSize).withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyPage<ShopifyOrder> actualShopifyOrdersPage = shopifySdk.getOrders(pageSize);

		assertEquals(0, actualShopifyOrdersPage.size());
	}

	@Test
	public void givenShopWithNoOrdersAndSomeMininumCreationDateAndPage1And80PageSizeWhenRetrievingOrdersThenReturnNoOrders()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.toString();

		final ShopifyOrdersRoot shopifyOrdersRoot = new ShopifyOrdersRoot();

		final String expectedResponseBodyString = getJsonString(ShopifyOrdersRoot.class, shopifyOrdersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final int pageSize = 80;
		final DateTime minimumCreationDateTime = SOME_DATE_TIME;
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withParam(ShopifySdk.STATUS_QUERY_PARAMETER, ShopifySdk.ANY_STATUSES)
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, pageSize)
						.withParam(ShopifySdk.CREATED_AT_MIN_QUERY_PARAMETER, minimumCreationDateTime.toString())
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyPage<ShopifyOrder> actualShopifyOrdersPage = shopifySdk.getOrders(minimumCreationDateTime,
				pageSize);

		assertEquals(0, actualShopifyOrdersPage.size());
	}

	@Test
	public void givenShopWithNoOrdersAndSomeMininumCreationDateAndSomeMaximumCreationDateAndPage1And70PageSizeWhenRetrievingOrdersThenReturnNoOrders()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.toString();

		final ShopifyOrdersRoot shopifyOrdersRoot = new ShopifyOrdersRoot();

		final String expectedResponseBodyString = getJsonString(ShopifyOrdersRoot.class, shopifyOrdersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final int pageSize = 70;
		final DateTime minimumCreationDateTime = SOME_DATE_TIME.minusDays(4);
		final DateTime maximumCreationDateTime = SOME_DATE_TIME;
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withParam(ShopifySdk.STATUS_QUERY_PARAMETER, ShopifySdk.ANY_STATUSES)
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, pageSize)
						.withParam(ShopifySdk.CREATED_AT_MIN_QUERY_PARAMETER, minimumCreationDateTime.toString())
						.withParam(ShopifySdk.CREATED_AT_MAX_QUERY_PARAMETER, maximumCreationDateTime.toString())
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyPage<ShopifyOrder> actualShopifyOrdersPage = shopifySdk.getOrders(minimumCreationDateTime,
				maximumCreationDateTime, pageSize);

		assertEquals(0, actualShopifyOrdersPage.size());
	}

	@Test
	public void givenShopWithNoOrdersAndSomeMininumCreationDateAndSomeMaximumCreationDateAndPage1And51PageSizeAndSomeAppIdWhenRetrievingOrdersThenReturnNoOrders()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.toString();

		final ShopifyOrdersRoot shopifyOrdersRoot = new ShopifyOrdersRoot();

		final String expectedResponseBodyString = getJsonString(ShopifyOrdersRoot.class, shopifyOrdersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final int pageSize = 51;
		final String appId = "current";
		final DateTime minimumCreationDateTime = SOME_DATE_TIME.minusDays(4);
		final DateTime maximumCreationDateTime = SOME_DATE_TIME;
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withParam(ShopifySdk.STATUS_QUERY_PARAMETER, ShopifySdk.ANY_STATUSES)
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, pageSize)
						.withParam(ShopifySdk.CREATED_AT_MIN_QUERY_PARAMETER, minimumCreationDateTime.toString())
						.withParam(ShopifySdk.CREATED_AT_MAX_QUERY_PARAMETER, maximumCreationDateTime.toString())
						.withParam(ShopifySdk.ATTRIBUTION_APP_ID_QUERY_PARAMETER, appId).withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyPage<ShopifyOrder> actualShopifyOrdersPage = shopifySdk.getOrders(minimumCreationDateTime,
				maximumCreationDateTime, appId, pageSize);

		assertEquals(0, actualShopifyOrdersPage.size());
	}

	@Test
	public void givenSomeOrderIdWhenRetrievingOrderRisksThenRetrieveOrderRisks() throws JsonProcessingException {
		final ShopifyOrderRisksRoot shopifyOrderRisksRoot = new ShopifyOrderRisksRoot();
		final ShopifyOrderRisk shopifyOrderRisk1 = new ShopifyOrderRisk();
		shopifyOrderRisk1.setCauseCancel(true);
		shopifyOrderRisk1.setCheckoutId("93284932");
		shopifyOrderRisk1.setDisplay(true);
		shopifyOrderRisk1.setId("9872347234");
		shopifyOrderRisk1.setMerchantMessage("Some merch message");
		shopifyOrderRisk1.setOrderId("123");
		shopifyOrderRisk1.setRecommendation(OrderRiskRecommendation.ACCEPT);
		shopifyOrderRisk1.setScore(new BigDecimal(11.11));
		shopifyOrderRisk1.setSource("some source");

		final ShopifyOrderRisk shopifyOrderRisk2 = new ShopifyOrderRisk();
		shopifyOrderRisk2.setCauseCancel(true);
		shopifyOrderRisk2.setCheckoutId("284932");
		shopifyOrderRisk2.setDisplay(true);
		shopifyOrderRisk2.setId("987427234");
		shopifyOrderRisk2.setMerchantMessage("Some merch message2");
		shopifyOrderRisk2.setOrderId("123");
		shopifyOrderRisk2.setRecommendation(OrderRiskRecommendation.CANCEL);
		shopifyOrderRisk2.setScore(new BigDecimal(12.11));
		shopifyOrderRisk2.setSource("some source2");

		shopifyOrderRisksRoot.setRisks(Arrays.asList(shopifyOrderRisk1, shopifyOrderRisk2));
		final String expectedImageResponseBodyString = getJsonString(ShopifyOrderRisksRoot.class,
				shopifyOrderRisksRoot);

		final String expectedImagePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.append(FORWARD_SLASH).append("123").append(FORWARD_SLASH).append(ShopifySdk.RISKS).toString();

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedImagePath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET),
				giveResponse(expectedImageResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(expectedStatusCode));

		final List<ShopifyOrderRisk> actualShopifyOrderRisks = shopifySdk.getOrderRisks("123");

		assertEquals(shopifyOrderRisk1.getCheckoutId(), actualShopifyOrderRisks.get(0).getCheckoutId());
		assertEquals(shopifyOrderRisk1.getId(), actualShopifyOrderRisks.get(0).getId());
		assertEquals(shopifyOrderRisk1.getMerchantMessage(), actualShopifyOrderRisks.get(0).getMerchantMessage());
		assertEquals(shopifyOrderRisk1.getMessage(), actualShopifyOrderRisks.get(0).getMessage());
		assertEquals(shopifyOrderRisk1.getOrderId(), actualShopifyOrderRisks.get(0).getOrderId());
		assertEquals(shopifyOrderRisk1.getRecommendation(), actualShopifyOrderRisks.get(0).getRecommendation());
		assertEquals(shopifyOrderRisk1.getScore(), actualShopifyOrderRisks.get(0).getScore());
		assertEquals(shopifyOrderRisk1.getSource(), actualShopifyOrderRisks.get(0).getSource());

		assertEquals(shopifyOrderRisk2.getCheckoutId(), actualShopifyOrderRisks.get(1).getCheckoutId());
		assertEquals(shopifyOrderRisk2.getId(), actualShopifyOrderRisks.get(1).getId());
		assertEquals(shopifyOrderRisk2.getMerchantMessage(), actualShopifyOrderRisks.get(1).getMerchantMessage());
		assertEquals(shopifyOrderRisk2.getMessage(), actualShopifyOrderRisks.get(1).getMessage());
		assertEquals(shopifyOrderRisk2.getOrderId(), actualShopifyOrderRisks.get(1).getOrderId());
		assertEquals(shopifyOrderRisk2.getRecommendation(), actualShopifyOrderRisks.get(1).getRecommendation());
		assertEquals(shopifyOrderRisk2.getScore(), actualShopifyOrderRisks.get(1).getScore());
		assertEquals(shopifyOrderRisk2.getSource(), actualShopifyOrderRisks.get(1).getSource());

	}

	@Test
	public void givenSomeValidRequestWhenGettingProductCount() throws JsonProcessingException {
		final Count count = new Count();
		count.setCount(231);
		final String expectedResponseBodyString = getJsonString(Count.class, count);

		final String expectedImagePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
				.append(FORWARD_SLASH).append(ShopifySdk.COUNT).toString();

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedImagePath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final int actualProductCount = shopifySdk.getProductCount();
		assertEquals(231, actualProductCount);

	}

	@Test
	public void givenSomeProductIdWhenDeletingProductThenDeleteProductAndReturnTrue() throws JsonProcessingException {

		final String expectedImagePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
				.append(FORWARD_SLASH).append("123").toString();

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(onRequestTo(expectedImagePath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
				.withMethod(Method.DELETE),
				giveResponse("{}", MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final boolean isProductDeleted = shopifySdk.deleteProduct("123");
		assertEquals(true, isProductDeleted);

	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndVariantIdWhenGettinVariantThenGetVariant()
			throws JsonProcessingException {

		final ShopifyVariantRoot shopifyVariantRoot = new ShopifyVariantRoot();

		final ShopifyVariant shopifyVariant = new ShopifyVariant();
		shopifyVariant.setId("999");
		shopifyVariant.setBarcode("XYZ-123");
		shopifyVariant.setSku("ABC-123");
		shopifyVariant.setImageId("1");
		shopifyVariant.setPrice(BigDecimal.valueOf(42.11));
		shopifyVariant.setGrams(12);
		shopifyVariant.setAvailable(3L);
		shopifyVariant.setRequiresShipping(true);
		shopifyVariant.setTaxable(true);
		shopifyVariant.setOption1("Red");
		shopifyVariant.setOption2("Blue");
		shopifyVariant.setOption3("GREEN");
		shopifyVariantRoot.setVariant(shopifyVariant);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.VARIANTS)
				.append(FORWARD_SLASH).append("999").toString();

		final String expectedResponseBodyString = getJsonString(ShopifyVariantRoot.class, shopifyVariantRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyVariant actualShopifyVariant = shopifySdk.getVariant("999");
		assertNotNull(actualShopifyVariant);

		assertEquals(shopifyVariant.getId(), actualShopifyVariant.getId());
		assertEquals(shopifyVariant.getPrice(), actualShopifyVariant.getPrice());
		assertEquals(shopifyVariant.getGrams(), actualShopifyVariant.getGrams());
		assertEquals(shopifyVariant.getImageId(), actualShopifyVariant.getImageId());
		assertEquals(shopifyVariant.getInventoryPolicy(), actualShopifyVariant.getInventoryPolicy());
		assertEquals(shopifyVariant.getSku(), actualShopifyVariant.getSku());
		assertEquals(shopifyVariant.getPosition(), actualShopifyVariant.getPosition());
		assertEquals(shopifyVariant.getProductId(), actualShopifyVariant.getProductId());
		assertEquals(shopifyVariant.getTitle(), actualShopifyVariant.getTitle());
		assertEquals(shopifyVariant.getCompareAtPrice(), actualShopifyVariant.getCompareAtPrice());
		assertEquals(shopifyVariant.getBarcode(), actualShopifyVariant.getBarcode());
		assertEquals(shopifyVariant.getFulfillmentService(), actualShopifyVariant.getFulfillmentService());
		assertEquals(shopifyVariant.getOption1(), actualShopifyVariant.getOption1());
		assertEquals(shopifyVariant.getOption2(), actualShopifyVariant.getOption2());
		assertEquals(shopifyVariant.getOption3(), actualShopifyVariant.getOption3());

	}

	@Test(expected = ShopifyErrorResponseException.class)
	public void givenSomeInvalidStatusWhenUpdatingInventoryLevelThenExpectShopifyErrorResponseException()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH)
				.append(ShopifySdk.INVENTORY_LEVELS).append("/").append(ShopifySdk.SET).toString();
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
			throws JsonProcessingException, IllegalAccessException {
		final ShopifyVariant currentShopifyVariant = new ShopifyVariant();
		currentShopifyVariant.setId("98746868985974");
		currentShopifyVariant.setTitle("UK 8");
		currentShopifyVariant.setPrice(new BigDecimal("10.00"));
		currentShopifyVariant.setInventoryQuantity(1337L);

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

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.VARIANTS)
				.append(FORWARD_SLASH).append(shopifyVariantUpdateRequest.getRequest().getId()).toString();
		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final StringBodyCapture stringBodyCapture = new StringBodyCapture();

		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken).withMethod(Method.PUT)
						.capturingBodyIn(stringBodyCapture),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withHeader("Location",
						new StringBuilder().append("https://test.myshopify.com/admin/products/2180984635510")
								.append(expectedPath).toString())
						.withStatus(expectedStatusCode));

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
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestWithPropertiesAndCreatingOrderThenCreateAndReturn()
			throws Exception {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append("orders")
				.append(FORWARD_SLASH).append("123").toString();
		final ShopifyOrderRoot shopifyOrderRoot = new ShopifyOrderRoot();
		final ShopifyOrder shopifyOrder = new ShopifyOrder();

		final String expectedResponseBodyString = "{ \"order\": { \"line_items\": [{ \"id\": \"1\", \"properties\": [ { \"name\": \"metafields\", \"value\": { \"test\": \"hello\" }},{ \"name\": \"some-basic-string\", \"value\": \"some-basic-string-value\"} ] }] } }";

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyOrder actualShopifyOrder = shopifySdk.getOrder("123");
		assertEquals(1, actualShopifyOrder.getLineItems().size());
		assertEquals("metafields", actualShopifyOrder.getLineItems().get(0).getProperties().get(0).getName());
		assertEquals("{\"test\":\"hello\"}",
				actualShopifyOrder.getLineItems().get(0).getProperties().get(0).getValue());
		assertEquals("some-basic-string", actualShopifyOrder.getLineItems().get(0).getProperties().get(1).getName());
		assertEquals("some-basic-string-value",
				actualShopifyOrder.getLineItems().get(0).getProperties().get(1).getValue());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestAndCreatingOrderThenCreateAndReturn()
			throws Exception {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append("orders").toString();
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
		shopifyOrder.setFinancialStatus("pending");
		shopifyOrder.setNote("Some note");

		final ShopifyAttribute shopifyAttribute1 = new ShopifyAttribute();
		shopifyAttribute1.setName("some-name1");
		shopifyAttribute1.setValue("some-value1");
		final ShopifyAttribute shopifyAttribute2 = new ShopifyAttribute();
		shopifyAttribute2.setName("some-name2");
		shopifyAttribute2.setValue("some-value2");
		final ShopifyAttribute shopifyAttribute3 = new ShopifyAttribute();
		shopifyAttribute3.setName("some-name3");
		shopifyAttribute3.setValue("some-value3");
		final List<ShopifyAttribute> someNoteAttributes = Arrays.asList(shopifyAttribute1, shopifyAttribute2,
				shopifyAttribute3);

		shopifyOrder.setNoteAttributes(someNoteAttributes);
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
				.withMetafields(Collections.emptyList()).withShippingLines(Arrays.asList(shippingLine1))
				.withFinancialStatus("pending").withNote("Some note").withNoteAttributes(someNoteAttributes).build();
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

		assertEquals("pending", actualRequestBody.getContent().get("order").get("financial_status").asText());
		assertEquals("Some note", actualRequestBody.getContent().get("order").get("note").asText());
		assertEquals(3, actualRequestBody.getContent().get("order").get("note_attributes").size());
		assertEquals(someNoteAttributes.get(0).getName(),
				actualRequestBody.getContent().get("order").get("note_attributes").path(0).get("name").asText());
		assertEquals(someNoteAttributes.get(0).getValue(),
				actualRequestBody.getContent().get("order").get("note_attributes").path(0).get("value").asText());
		assertEquals(someNoteAttributes.get(1).getName(),
				actualRequestBody.getContent().get("order").get("note_attributes").path(1).get("name").asText());
		assertEquals(someNoteAttributes.get(1).getValue(),
				actualRequestBody.getContent().get("order").get("note_attributes").path(1).get("value").asText());
		assertEquals(someNoteAttributes.get(2).getName(),
				actualRequestBody.getContent().get("order").get("note_attributes").path(2).get("name").asText());
		assertEquals(someNoteAttributes.get(2).getValue(),
				actualRequestBody.getContent().get("order").get("note_attributes").path(2).get("value").asText());

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
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestWhenUpdatingOrderShippingAddressThenUpdateAndReturnOrder()
			throws JsonProcessingException {

		final String someShopifyOrderId = "99999";
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append("orders")
				.append(FORWARD_SLASH).append(someShopifyOrderId).toString();
		final ShopifyOrderRoot shopifyOrderRoot = new ShopifyOrderRoot();
		final ShopifyOrder shopifyOrder = new ShopifyOrder();
		shopifyOrder.setId(someShopifyOrderId);
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

		final ShopifyOrderShippingAddressUpdateRequest shopifyAddressRequest = ShopifyOrderShippingAddressUpdateRequest
				.newBuilder().withId(someShopifyOrderId).withAddress1("224 Wyoming Ave").withAddress2("Suite 100")
				.withCity("Scranton").withProvince("PEnnsylvania").withProvinceCode("PA").withZip("18503")
				.withCountry("USA").withCountryCode("US").withPhone("123").withFirstName("Ryan").withLastName("Kazokas")
				.withCompany("ChannelApe").withLatitude(new BigDecimal(410.44444))
				.withLongitude(new BigDecimal(123.442)).build();

		final String expectedResponseBodyString = getJsonString(ShopifyOrderRoot.class, shopifyOrderRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.PUT)
						.capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyOrder actualShopifyOrder = shopifySdk.updateOrderShippingAddress(shopifyAddressRequest);

		assertEquals("224 Wyoming Ave",
				actualRequestBody.getContent().get("order").get("shipping_address").get("address1").asText());
		assertEquals("Suite 100",
				actualRequestBody.getContent().get("order").get("shipping_address").get("address2").asText());
		assertEquals("PEnnsylvania",
				actualRequestBody.getContent().get("order").get("shipping_address").get("province").asText());
		assertEquals("18503", actualRequestBody.getContent().get("order").get("shipping_address").get("zip").asText());

		assertEquals("Ryan",
				actualRequestBody.getContent().get("order").get("shipping_address").get("first_name").asText());
		assertEquals("Kazokas",
				actualRequestBody.getContent().get("order").get("shipping_address").get("last_name").asText());

		assertEquals("USA",
				actualRequestBody.getContent().get("order").get("shipping_address").get("country").asText());
		assertEquals("US",
				actualRequestBody.getContent().get("order").get("shipping_address").get("country_code").asText());
		assertEquals("PA",
				actualRequestBody.getContent().get("order").get("shipping_address").get("province_code").asText());
		assertEquals("123", actualRequestBody.getContent().get("order").get("shipping_address").get("phone").asText());
		assertEquals("ChannelApe",
				actualRequestBody.getContent().get("order").get("shipping_address").get("company").asText());
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
	public void givenSomeUpdateCustomerRequestWhenUpdatingCustomerThenUpdateAndReturnCustomer()
			throws JsonProcessingException {
		final String someCustomerId = "some-id";
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append("customers")
				.append(FORWARD_SLASH).append(someCustomerId).toString();
		final ShopifyCustomerUpdateRequest shopifyCustomerUpdateRequest = ShopifyCustomerUpdateRequest.newBuilder()
				.withId(someCustomerId).withFirstName("Ryan").withLastName("Kazokas")
				.withEmail("rkazokas@channelape.com").withPhone("57087482349").build();
		final ShopifyCustomerRoot shopifyCustomerRoot = new ShopifyCustomerRoot();
		final ShopifyCustomer shopifyCustomer = new ShopifyCustomer();
		shopifyCustomer.setFirstName("Ryan");
		shopifyCustomer.setLastname("Kazokas");
		shopifyCustomer.setEmail("rkazokas@channelape.com");
		shopifyCustomer.setNote("Some NOtes");
		shopifyCustomer.setOrdersCount(3);
		shopifyCustomer.setState("some-state");
		shopifyCustomer.setPhone("57087482349");
		shopifyCustomer.setTotalSpent(new BigDecimal(32.12));
		shopifyCustomerRoot.setCustomer(shopifyCustomer);
		final String expectedResponseBodyString = getJsonString(ShopifyCustomerRoot.class, shopifyCustomerRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.PUT)
						.capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyCustomer updatedCustomer = shopifySdk.updateCustomer(shopifyCustomerUpdateRequest);

		assertEquals("rkazokas@channelape.com", updatedCustomer.getEmail());
		assertEquals("Ryan", updatedCustomer.getFirstName());
		assertEquals("Kazokas", updatedCustomer.getLastname());
		assertEquals("Some NOtes", updatedCustomer.getNote());
		assertEquals(3, updatedCustomer.getOrdersCount());
		assertEquals("57087482349", updatedCustomer.getPhone());
		assertEquals("some-state", updatedCustomer.getState());
		assertEquals(new BigDecimal(32.12), updatedCustomer.getTotalSpent());

		assertEquals("rkazokas@channelape.com", actualRequestBody.getContent().get("customer").get("email").asText());
		assertEquals("Ryan", actualRequestBody.getContent().get("customer").get("first_name").asText());
		assertEquals("Kazokas", actualRequestBody.getContent().get("customer").get("last_name").asText());
		assertEquals("57087482349", actualRequestBody.getContent().get("customer").get("phone").asText());
	}

	@Test
	public void givenAValidCustomerIdWhenRetrievingACustomerThenReturnACustomer() throws JsonProcessingException {
		final ShopifyCustomer shopifyCustomer = buildShopifyCustomer();
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append("customers")
				.append(FORWARD_SLASH).append(shopifyCustomer.getId()).toString();
		final ShopifyCustomerRoot shopifyCustomerRoot = new ShopifyCustomerRoot();
		shopifyCustomerRoot.setCustomer(shopifyCustomer);

		final String expectedResponseBodyString = getJsonString(ShopifyCustomerRoot.class, shopifyCustomerRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyCustomer actualCustomer = shopifySdk.getCustomer(shopifyCustomer.getId());

		assertCustomer(actualCustomer);
	}

	@Test
	public void givenAValidRequestWhenRetrievingAListOfCustomersWithPaginationParamsThenRetrieveThoseCustomers()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append("customers").toString();
		final ShopifyCustomer shopifyCustomer = buildShopifyCustomer();
		final List<ShopifyCustomer> shopifyCustomers = new LinkedList<>();
		shopifyCustomers.add(shopifyCustomer);
		final ShopifyCustomersRoot shopifyCustomersRoot = new ShopifyCustomersRoot();
		shopifyCustomersRoot.setCustomers(shopifyCustomers);
		final String expectedResponseBodyString = getJsonString(ShopifyCustomersRoot.class, shopifyCustomersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();

		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.GET)
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, 50),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));
		final ShopifyGetCustomersRequest shopifyGetCustomersRequest = ShopifyGetCustomersRequest.newBuilder().build();

		final ShopifyPage<ShopifyCustomer> actualCustomersPage = shopifySdk.getCustomers(shopifyGetCustomersRequest);

		assertCustomers(actualCustomersPage);
	}

	@Test
	public void givenAListOfIdsWhenRetrievingCustomersThenRetrieveJustThoseCustomers() throws JsonProcessingException {
		final String someOtherCustomerId = "some-other-id";
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append("customers").toString();
		final ShopifyCustomer shopifyCustomer = buildShopifyCustomer();
		final List<ShopifyCustomer> shopifyCustomers = new LinkedList<>();
		final List<String> ids = new ArrayList<>();
		ids.add(shopifyCustomer.getId());
		ids.add(someOtherCustomerId);
		shopifyCustomers.add(shopifyCustomer);
		final ShopifyCustomersRoot shopifyCustomersRoot = new ShopifyCustomersRoot();
		shopifyCustomersRoot.setCustomers(shopifyCustomers);
		final String expectedResponseBodyString = getJsonString(ShopifyCustomersRoot.class, shopifyCustomersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();

		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.GET)
						.withParam(ShopifySdk.IDS_QUERY_PARAMETER, StringUtils.join(ids, ","))
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, 50),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyGetCustomersRequest shopifyGetCustomersRequest = ShopifyGetCustomersRequest.newBuilder()
				.withIds(ids).build();

		final ShopifyPage<ShopifyCustomer> actualCustomersPage = shopifySdk.getCustomers(shopifyGetCustomersRequest);

		assertCustomers(actualCustomersPage);
	}

	@Test
	public void givenASinceIdWhenRetrievingCustomersThenRetrieveJustThoseCustomers() throws JsonProcessingException {
		final String sinceId = "since-id";
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append("customers").toString();
		final ShopifyCustomer shopifyCustomer = buildShopifyCustomer();
		final List<ShopifyCustomer> shopifyCustomers = new LinkedList<>();
		shopifyCustomers.add(shopifyCustomer);
		final ShopifyCustomersRoot shopifyCustomersRoot = new ShopifyCustomersRoot();
		shopifyCustomersRoot.setCustomers(shopifyCustomers);
		final String expectedResponseBodyString = getJsonString(ShopifyCustomersRoot.class, shopifyCustomersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();

		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.GET)
						.withParam(ShopifySdk.SINCE_ID_QUERY_PARAMETER, sinceId)
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, 50),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyGetCustomersRequest shopifyGetCustomersRequest = ShopifyGetCustomersRequest.newBuilder()
				.withSinceId(sinceId).build();

		final ShopifyPage<ShopifyCustomer> actualCustomersPage = shopifySdk.getCustomers(shopifyGetCustomersRequest);

		assertCustomers(actualCustomersPage);
	}

	@Test
	public void givenMinimumCreationDateAndPaginationParamsWhenRetrievingCustomersThenRetrieveJustThoseCustomers()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append("customers").toString();
		final ShopifyCustomer shopifyCustomer = buildShopifyCustomer();
		final List<ShopifyCustomer> shopifyCustomers = new LinkedList<>();
		shopifyCustomers.add(shopifyCustomer);
		final ShopifyCustomersRoot shopifyCustomersRoot = new ShopifyCustomersRoot();
		shopifyCustomersRoot.setCustomers(shopifyCustomers);
		final String expectedResponseBodyString = getJsonString(ShopifyCustomersRoot.class, shopifyCustomersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();

		final DateTime minimumCreationTime = new DateTime();

		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.GET)
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, 50)
						.withParam(ShopifySdk.CREATED_AT_MIN_QUERY_PARAMETER, minimumCreationTime),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyGetCustomersRequest shopifyGetCustomersRequest = ShopifyGetCustomersRequest.newBuilder()
				.withCreatedAtMin(minimumCreationTime).build();

		final ShopifyPage<ShopifyCustomer> actualCustomersPage = shopifySdk.getCustomers(shopifyGetCustomersRequest);

		assertCustomers(actualCustomersPage);
	}

	@Test
	public void givenAMinimumAndMaximumCreationDateAndPageParamWhenRetrievingCustomersThenRetrieveJustThoseCustomers()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append("customers").toString();
		final ShopifyCustomer shopifyCustomer = buildShopifyCustomer();
		final List<ShopifyCustomer> shopifyCustomers = new LinkedList<>();
		shopifyCustomers.add(shopifyCustomer);
		final ShopifyCustomersRoot shopifyCustomersRoot = new ShopifyCustomersRoot();
		shopifyCustomersRoot.setCustomers(shopifyCustomers);
		final String expectedResponseBodyString = getJsonString(ShopifyCustomersRoot.class, shopifyCustomersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();

		final DateTime minimumCreationTime = new DateTime();
		final DateTime maximumCreationTime = minimumCreationTime.plusDays(1);

		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.GET)
						.withParam(ShopifySdk.CREATED_AT_MIN_QUERY_PARAMETER, minimumCreationTime)
						.withParam(ShopifySdk.CREATED_AT_MAX_QUERY_PARAMETER, maximumCreationTime)
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, 50),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyGetCustomersRequest shopifyGetCustomersRequest = ShopifyGetCustomersRequest.newBuilder()
				.withCreatedAtMin(minimumCreationTime).withCreatedAtMax(maximumCreationTime).build();

		final ShopifyPage<ShopifyCustomer> actualCustomersPage = shopifySdk.getCustomers(shopifyGetCustomersRequest);

		assertCustomers(actualCustomersPage);
	}

	@Test
	public void givenAValidQueryWhenRetrievingCustomersThenRetrieveJustThoseCustomersViaTheSearchAPI()
			throws JsonProcessingException {
		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append("customers")
				.append(FORWARD_SLASH).append("search").toString();
		final ShopifyCustomer shopifyCustomer = buildShopifyCustomer();
		final List<ShopifyCustomer> shopifyCustomers = new LinkedList<>();
		shopifyCustomers.add(shopifyCustomer);
		final ShopifyCustomersRoot shopifyCustomersRoot = new ShopifyCustomersRoot();
		shopifyCustomersRoot.setCustomers(shopifyCustomers);
		final String expectedResponseBodyString = getJsonString(ShopifyCustomersRoot.class, shopifyCustomersRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();

		final String query = "Austin";

		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.GET)
						.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, 50)
						.withParam(ShopifySdk.QUERY_QUERY_PARAMETER, query),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyPage<ShopifyCustomer> actualCustomersPage = shopifySdk.searchCustomers(query);

		assertCustomers(actualCustomersPage);
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestAndCreatingRefundThenCalculateAndCreateRefundAndReturn()
			throws Exception {

		final String expectedCalculatePath = new StringBuilder().append(FORWARD_SLASH)
				.append(ShopifySdk.API_VERSION_PREFIX).append(FORWARD_SLASH).append(SOME_API_VERSION)
				.append(FORWARD_SLASH).append(ShopifySdk.ORDERS).append(FORWARD_SLASH).append("123123")
				.append(FORWARD_SLASH).append(ShopifySdk.REFUNDS).append(FORWARD_SLASH).append(ShopifySdk.CALCULATE)
				.toString();

		final String expectedRefundPath = new StringBuilder().append(FORWARD_SLASH)
				.append(ShopifySdk.API_VERSION_PREFIX).append(FORWARD_SLASH).append(SOME_API_VERSION)
				.append(FORWARD_SLASH).append(ShopifySdk.ORDERS).append(FORWARD_SLASH).append("123123")
				.append(FORWARD_SLASH).append(ShopifySdk.REFUNDS).toString();

		final ShopifyRefundRoot shopifyRefundRoot = new ShopifyRefundRoot();
		final ShopifyRefund shopifyRefund = new ShopifyRefund();

		shopifyRefund.setCreatedAt(SOME_DATE_TIME);
		shopifyRefund.setProcessedAt(SOME_DATE_TIME);
		shopifyRefund.setCurrency(Currency.getInstance("USD"));
		shopifyRefund.setId("999999");
		shopifyRefund.setOrderId("123123");

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
		shopifyTransaction.setKind("suggested_refund");
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
		assertEquals("suggested_refund",
				calculateRequestBody.getContent().get("refund").get("transactions").path(0).get("kind").asText());
		assertEquals("123123",
				calculateRequestBody.getContent().get("refund").get("transactions").path(0).get("order_id").asText());
		assertEquals("44444",
				calculateRequestBody.getContent().get("refund").get("transactions").path(0).get("parent_id").asText());

		assertEquals("999999", refundRequestBody.getContent().get("refund").get("id").asText());
		assertEquals("123123", refundRequestBody.getContent().get("refund").get("order_id").asText());
		assertEquals("USD", refundRequestBody.getContent().get("refund").get("currency").asText());
		assertEquals(SOME_DATE_TIME.toString(),
				refundRequestBody.getContent().get("refund").get("created_at").asText());
		assertEquals(SOME_DATE_TIME.toString(),
				refundRequestBody.getContent().get("refund").get("processed_at").asText());

		assertEquals(true, refundRequestBody.getContent().get("refund").get("shipping").get("full_refund").asBoolean());
		assertEquals(BigDecimal.valueOf(99.11),
				refundRequestBody.getContent().get("refund").get("shipping").get("amount").decimalValue());
		assertEquals(BigDecimal.valueOf(92.11),
				refundRequestBody.getContent().get("refund").get("shipping").get("maximum_refundable").decimalValue());
		assertEquals(BigDecimal.valueOf(11.44),
				refundRequestBody.getContent().get("refund").get("shipping").get("tax").decimalValue());

		assertEquals(1, refundRequestBody.getContent().get("refund").get("refund_line_items").size());
		assertEquals("718273812", refundRequestBody.getContent().get("refund").get("refund_line_items").path(0)
				.get("line_item_id").asText());
		assertEquals("no_restock", refundRequestBody.getContent().get("refund").get("refund_line_items").path(0)
				.get("restock_type").asText());
		assertEquals(3,
				refundRequestBody.getContent().get("refund").get("refund_line_items").path(0).get("quantity").asInt());

		assertEquals(1, refundRequestBody.getContent().get("refund").get("transactions").size());
		assertEquals(BigDecimal.valueOf(11.23),
				refundRequestBody.getContent().get("refund").get("transactions").path(0).get("amount").decimalValue());
		assertEquals(BigDecimal.valueOf(11.23), refundRequestBody.getContent().get("refund").get("transactions").path(0)
				.get("maximum_refundable").decimalValue());
		assertEquals("USD",
				refundRequestBody.getContent().get("refund").get("transactions").path(0).get("currency").asText());
		assertEquals("bogus",
				refundRequestBody.getContent().get("refund").get("transactions").path(0).get("gateway").asText());
		assertEquals("refund",
				refundRequestBody.getContent().get("refund").get("transactions").path(0).get("kind").asText());
		assertEquals("123123",
				refundRequestBody.getContent().get("refund").get("transactions").path(0).get("order_id").asText());
		assertEquals("44444",
				refundRequestBody.getContent().get("refund").get("transactions").path(0).get("parent_id").asText());

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

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestWhenCreatingGiftCardThenCreateAndReturn()
			throws Exception {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.GIFT_CARDS)
				.toString();
		final ShopifyGiftCardRoot shopifyGiftCardRoot = new ShopifyGiftCardRoot();
		final ShopifyGiftCard shopifyGiftCard = new ShopifyGiftCard();

		shopifyGiftCard.setInitialValue(new BigDecimal(41.21));
		shopifyGiftCard.setCode("ABCDEFGHIJKLMNOP");
		shopifyGiftCard.setBalance(new BigDecimal(41.21));
		final DateTime someDateTime = new DateTime();
		shopifyGiftCard.setExpiresOn(someDateTime);
		shopifyGiftCard.setCreatedAt(someDateTime);
		shopifyGiftCard.setCurrency("USD");
		shopifyGiftCard.setLastCharacters("MNOP");
		shopifyGiftCard.setId("1");
		shopifyGiftCard.setNote("Happy Birthday!");

		shopifyGiftCardRoot.setGiftCard(shopifyGiftCard);

		final String expectedResponseBodyString = getJsonString(ShopifyGiftCardRoot.class, shopifyGiftCardRoot);

		final Status expectedStatus = Status.CREATED;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.POST)
						.capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyGiftCardCreationRequest shopifyGiftCardCreationRequest = ShopifyGiftCardCreationRequest
				.newBuilder().withInitialValue(new BigDecimal(42.21)).withCode("ABCDEFGHIJKLMNOP").withCurrency("USD")
				.build();
		final ShopifyGiftCard actualShopifyGiftCard = shopifySdk.createGiftCard(shopifyGiftCardCreationRequest);

		assertEquals("ABCDEFGHIJKLMNOP", actualRequestBody.getContent().get("gift_card").get("code").asText());
		assertEquals("USD", actualRequestBody.getContent().get("gift_card").get("currency").asText());
		assertEquals(BigDecimal.valueOf(42.21),
				actualRequestBody.getContent().get("gift_card").get("initial_value").decimalValue());

		assertEquals(shopifyGiftCard.getId(), actualShopifyGiftCard.getId());
		assertEquals(shopifyGiftCard.getApiClientId(), actualShopifyGiftCard.getApiClientId());
		assertEquals(shopifyGiftCard.getInitialValue(), actualShopifyGiftCard.getInitialValue());
		assertEquals(0, shopifyGiftCard.getCreatedAt().compareTo(actualShopifyGiftCard.getCreatedAt()));
		assertEquals(shopifyGiftCard.getBalance(), actualShopifyGiftCard.getBalance());
		assertEquals(shopifyGiftCard.getCode(), actualShopifyGiftCard.getCode());
		assertEquals(0, shopifyGiftCard.getExpiresOn().compareTo(actualShopifyGiftCard.getExpiresOn()));
		assertNull(actualShopifyGiftCard.getDisabledAt());
		assertEquals(shopifyGiftCard.getLineItemId(), actualShopifyGiftCard.getLineItemId());
		assertEquals(shopifyGiftCard.getNote(), actualShopifyGiftCard.getNote());
		assertEquals(shopifyGiftCard.getLastCharacters(), actualShopifyGiftCard.getLastCharacters());
		assertEquals(shopifyGiftCard.getTemplateSuffix(), actualShopifyGiftCard.getTemplateSuffix());
		assertNull(actualShopifyGiftCard.getUpdatedAt());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndSomeProductMetafieldWhenCreatingProductMetafieldThenCreateAndProductMetafield()
			throws Exception {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
				.append(FORWARD_SLASH).append("123").append(FORWARD_SLASH).append(ShopifySdk.METAFIELDS).toString();

		final Metafield metafield = new Metafield();
		metafield.setCreatedAt(new DateTime());
		metafield.setUpdatedAt(new DateTime());
		metafield.setType(MetafieldType.SINGLE_LINE_TEXT);
		metafield.setId("123");
		metafield.setKey("channelape_product_id");
		metafield.setNamespace("channelape");
		metafield.setOwnerId("123");
		metafield.setOwnerResource("product");
		metafield.setValue("38728743");

		final MetafieldRoot metafieldRoot = new MetafieldRoot();
		metafieldRoot.setMetafield(metafield);

		final String expectedResponseBodyString = getJsonString(MetafieldRoot.class, metafieldRoot);

		final Status expectedStatus = Status.CREATED;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.POST)
						.capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyProductMetafieldCreationRequest shopifyProductMetafieldCreationRequest = ShopifyProductMetafieldCreationRequest
				.newBuilder().withProductId("123").withNamespace("channelape").withKey("channelape_product_id")
				.withValue("38728743").withValueType(MetafieldType.SINGLE_LINE_TEXT).build();
		final Metafield actualMetafield = shopifySdk.createProductMetafield(shopifyProductMetafieldCreationRequest);

		assertEquals(shopifyProductMetafieldCreationRequest.getRequest().getKey().toString(),
				actualRequestBody.getContent().get("metafield").get("key").asText());
		assertEquals(shopifyProductMetafieldCreationRequest.getRequest().getValue(),
				actualRequestBody.getContent().get("metafield").get("value").asText());

		assertEquals(shopifyProductMetafieldCreationRequest.getRequest().getNamespace(),
				actualRequestBody.getContent().get("metafield").get("namespace").asText());
		assertEquals(shopifyProductMetafieldCreationRequest.getRequest().getType().toString(),
				actualRequestBody.getContent().get("metafield").get("type").asText());
		assertNotNull(actualMetafield);
		assertEquals(metafield.getId(), actualMetafield.getId());
		assertEquals(0, metafield.getCreatedAt().compareTo(actualMetafield.getCreatedAt()));
		assertEquals(metafield.getKey(), actualMetafield.getKey());
		assertEquals(metafield.getNamespace(), actualMetafield.getNamespace());
		assertEquals(metafield.getOwnerId(), actualMetafield.getOwnerId());
		assertEquals(metafield.getOwnerResource(), actualMetafield.getOwnerResource());
		assertEquals(0, metafield.getUpdatedAt().compareTo(actualMetafield.getUpdatedAt()));
		assertEquals(metafield.getValue(), actualMetafield.getValue());
		assertEquals(metafield.getType(), actualMetafield.getType());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndSomeVariantMetafieldWhenCreatingVariantMetafieldThenCreateVariantMetafield()
			throws Exception {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.VARIANTS)
				.append(FORWARD_SLASH).append("123").append(FORWARD_SLASH).append(ShopifySdk.METAFIELDS).toString();

		final Metafield metafield = new Metafield();
		metafield.setCreatedAt(new DateTime());
		metafield.setUpdatedAt(new DateTime());
		metafield.setType(MetafieldType.SINGLE_LINE_TEXT);
		metafield.setId("123");
		metafield.setKey("channelape_variant_id");
		metafield.setNamespace("channelape");
		metafield.setOwnerId("123");
		metafield.setOwnerResource("variant");
		metafield.setValue("38728743");

		final MetafieldRoot metafieldRoot = new MetafieldRoot();
		metafieldRoot.setMetafield(metafield);

		final String expectedResponseBodyString = getJsonString(MetafieldRoot.class, metafieldRoot);

		final Status expectedStatus = Status.CREATED;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.POST)
						.capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyVariantMetafieldCreationRequest shopifyVariantMetafieldCreationRequest = ShopifyVariantMetafieldCreationRequest
				.newBuilder().withVariantId("123").withNamespace("channelape").withKey("channelape_variant_id")
				.withValue("38728743").withValueType(MetafieldType.SINGLE_LINE_TEXT).build();
		final Metafield actualMetafield = shopifySdk.createVariantMetafield(shopifyVariantMetafieldCreationRequest);

		assertEquals(shopifyVariantMetafieldCreationRequest.getRequest().getKey().toString(),
				actualRequestBody.getContent().get("metafield").get("key").asText());
		assertEquals(shopifyVariantMetafieldCreationRequest.getRequest().getValue(),
				actualRequestBody.getContent().get("metafield").get("value").asText());

		assertEquals(shopifyVariantMetafieldCreationRequest.getRequest().getNamespace(),
				actualRequestBody.getContent().get("metafield").get("namespace").asText());
		assertEquals(shopifyVariantMetafieldCreationRequest.getRequest().getType().toString(),
				actualRequestBody.getContent().get("metafield").get("type").asText());
		assertNotNull(actualMetafield);
		assertEquals(metafield.getId(), actualMetafield.getId());
		assertEquals(0, metafield.getCreatedAt().compareTo(actualMetafield.getCreatedAt()));
		assertEquals(metafield.getKey(), actualMetafield.getKey());
		assertEquals(metafield.getNamespace(), actualMetafield.getNamespace());
		assertEquals(metafield.getOwnerId(), actualMetafield.getOwnerId());
		assertEquals(metafield.getOwnerResource(), actualMetafield.getOwnerResource());
		assertEquals(0, metafield.getUpdatedAt().compareTo(actualMetafield.getUpdatedAt()));
		assertEquals(metafield.getValue(), actualMetafield.getValue());
		assertEquals(metafield.getType(), actualMetafield.getType());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestWhenRetrievingVariantMetafieldsThenReturnVariantMetafields()
			throws JsonProcessingException {

		final Metafield metafield = new Metafield();
		metafield.setKey("channelape_variant_id");
		metafield.setValue("8fb0fb40-ab18-439e-bc6e-394b63ff1819");
		metafield.setNamespace("channelape");
		metafield.setOwnerId("1234");
		metafield.setType(MetafieldType.SINGLE_LINE_TEXT);
		metafield.setOwnerResource("variant");

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.VARIANTS)
				.append("/1234/").append(ShopifySdk.METAFIELDS).toString();
		final MetafieldsRoot metafieldsRoot = new MetafieldsRoot();
		metafieldsRoot.setMetafields(Arrays.asList(metafield));

		final String expectedResponseBodyString = getJsonString(MetafieldsRoot.class, metafieldsRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();

		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final List<Metafield> actualMetafields = shopifySdk.getVariantMetafields("1234");
		assertNotNull(actualMetafields);
		assertEquals(1, actualMetafields.size());
		assertEquals(metafield.getKey(), actualMetafields.get(0).getKey());
		assertEquals(metafield.getValue(), actualMetafields.get(0).getValue());
		assertEquals(metafield.getType(), actualMetafields.get(0).getType());
		assertEquals(metafield.getNamespace(), actualMetafields.get(0).getNamespace());
		assertEquals(metafield.getOwnerId(), actualMetafields.get(0).getOwnerId());
		assertEquals(metafield.getOwnerResource(), actualMetafields.get(0).getOwnerResource());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndSomeRecurringApplicationChargeCreationRequestWhenCreatingRecurringApplicationChargeThenCreateRecurringApplicationCharge()
			throws Exception {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH)
				.append(ShopifySdk.RECURRING_APPLICATION_CHARGES).toString();

		final ShopifyRecurringApplicationCharge shopifyRecurringApplicationCharge = new ShopifyRecurringApplicationCharge();
		shopifyRecurringApplicationCharge.setActivatedOn("2018-01-01");
		shopifyRecurringApplicationCharge.setBillingOn("2019-01-01");
		shopifyRecurringApplicationCharge.setConfirmationUrl("https://www.google.com/1");
		shopifyRecurringApplicationCharge.setCreatedAt("2018-01-01");
		shopifyRecurringApplicationCharge.setCappedAmount(BigDecimal.valueOf(41.42));
		shopifyRecurringApplicationCharge.setReturnUrl("https://www.google.com/2");
		shopifyRecurringApplicationCharge.setName("Some Name");
		shopifyRecurringApplicationCharge.setPrice(BigDecimal.valueOf(41.42));
		shopifyRecurringApplicationCharge.setApiClientId("787428734234");
		shopifyRecurringApplicationCharge.setTerms("some terms");
		shopifyRecurringApplicationCharge.setTrialDays(720);
		shopifyRecurringApplicationCharge.setTrialEndsOn("2020-01-01");
		shopifyRecurringApplicationCharge.setTest(true);
		shopifyRecurringApplicationCharge.setStatus("active");

		final ShopifyRecurringApplicationChargeRoot shopifyRecurringApplicationChargeRoot = new ShopifyRecurringApplicationChargeRoot();
		shopifyRecurringApplicationChargeRoot.setRecurringApplicationCharge(shopifyRecurringApplicationCharge);
		final String expectedResponseBodyString = getJsonString(ShopifyRecurringApplicationChargeRoot.class,
				shopifyRecurringApplicationChargeRoot);

		final Status expectedStatus = Status.CREATED;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.POST)
						.capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyRecurringApplicationChargeCreationRequest request = ShopifyRecurringApplicationChargeCreationRequest
				.newBuilder().withName("Some Name").withTerms("terms").withPrice(BigDecimal.valueOf(42.11))
				.withCappedAmount(BigDecimal.valueOf(11.11)).withReturnUrl("https://google.com/1").withTrialDays(720)
				.withTest(true).build();
		final ShopifyRecurringApplicationCharge actualShopifyRecurringApplicationCharge = shopifySdk
				.createRecurringApplicationCharge(request);

		assertEquals(request.getRequest().getName(),
				actualRequestBody.getContent().get("recurring_application_charge").get("name").asText());
		assertEquals(request.getRequest().getTerms(),
				actualRequestBody.getContent().get("recurring_application_charge").get("terms").asText());
		assertEquals(0, request.getRequest().getPrice().compareTo(
				actualRequestBody.getContent().get("recurring_application_charge").get("price").decimalValue()));
		assertEquals(0, request.getRequest().getCappedAmount().compareTo(actualRequestBody.getContent()
				.get("recurring_application_charge").get("capped_amount").decimalValue()));
		assertEquals(request.getRequest().getReturnUrl(),
				actualRequestBody.getContent().get("recurring_application_charge").get("return_url").asText());
		assertEquals(request.getRequest().getTrialDays(),
				actualRequestBody.getContent().get("recurring_application_charge").get("trial_days").asInt());
		assertEquals(request.getRequest().isTest(),
				actualRequestBody.getContent().get("recurring_application_charge").get("test").asBoolean());
		assertEquals(shopifyRecurringApplicationCharge.getId(), actualShopifyRecurringApplicationCharge.getId());
		assertEquals(shopifyRecurringApplicationCharge.getActivatedOn(),
				actualShopifyRecurringApplicationCharge.getActivatedOn());
		assertEquals(shopifyRecurringApplicationCharge.getApiClientId(),
				actualShopifyRecurringApplicationCharge.getApiClientId());
		assertEquals(shopifyRecurringApplicationCharge.getBillingOn(),
				actualShopifyRecurringApplicationCharge.getBillingOn());
		assertEquals(shopifyRecurringApplicationCharge.getCancelledOn(),
				actualShopifyRecurringApplicationCharge.getCancelledOn());
		assertEquals(shopifyRecurringApplicationCharge.getCappedAmount(),
				actualShopifyRecurringApplicationCharge.getCappedAmount());
		assertEquals(shopifyRecurringApplicationCharge.getConfirmationUrl(),
				actualShopifyRecurringApplicationCharge.getConfirmationUrl());
		assertEquals(shopifyRecurringApplicationCharge.getCreatedAt(),
				actualShopifyRecurringApplicationCharge.getCreatedAt());
		assertEquals(shopifyRecurringApplicationCharge.getName(), actualShopifyRecurringApplicationCharge.getName());
		assertEquals(shopifyRecurringApplicationCharge.getPrice(), actualShopifyRecurringApplicationCharge.getPrice());
		assertEquals(shopifyRecurringApplicationCharge.getReturnUrl(),
				actualShopifyRecurringApplicationCharge.getReturnUrl());
		assertEquals(shopifyRecurringApplicationCharge.getStatus(),
				actualShopifyRecurringApplicationCharge.getStatus());
		assertEquals(shopifyRecurringApplicationCharge.getTerms(), actualShopifyRecurringApplicationCharge.getTerms());
		assertEquals(shopifyRecurringApplicationCharge.getTrialDays(),
				actualShopifyRecurringApplicationCharge.getTrialDays());
		assertEquals(shopifyRecurringApplicationCharge.getTrialEndsOn(),
				actualShopifyRecurringApplicationCharge.getTrialEndsOn());
		assertEquals(shopifyRecurringApplicationCharge.getUpdatedOn(),
				actualShopifyRecurringApplicationCharge.getUpdatedOn());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndSomeRecurringChargeIdRequestWheGettingRecurringApplicationChargeThenGetRecurringApplicationCharge()
			throws Exception {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH)
				.append(ShopifySdk.RECURRING_APPLICATION_CHARGES).append(FORWARD_SLASH).append("Some-Charge_id")
				.toString();

		final ShopifyRecurringApplicationCharge shopifyRecurringApplicationCharge = new ShopifyRecurringApplicationCharge();
		shopifyRecurringApplicationCharge.setActivatedOn("2018-01-01");
		shopifyRecurringApplicationCharge.setBillingOn("2019-01-01");
		shopifyRecurringApplicationCharge.setConfirmationUrl("https://www.google.com/1");
		shopifyRecurringApplicationCharge.setCreatedAt("2018-01-01");
		shopifyRecurringApplicationCharge.setCappedAmount(BigDecimal.valueOf(41.42));
		shopifyRecurringApplicationCharge.setReturnUrl("https://www.google.com/2");
		shopifyRecurringApplicationCharge.setName("Some Name");
		shopifyRecurringApplicationCharge.setPrice(BigDecimal.valueOf(41.42));
		shopifyRecurringApplicationCharge.setApiClientId("787428734234");
		shopifyRecurringApplicationCharge.setTerms("some terms");
		shopifyRecurringApplicationCharge.setTrialDays(720);
		shopifyRecurringApplicationCharge.setTrialEndsOn("2020-01-01");
		shopifyRecurringApplicationCharge.setTest(true);
		shopifyRecurringApplicationCharge.setStatus("active");

		final ShopifyRecurringApplicationChargeRoot shopifyRecurringApplicationChargeRoot = new ShopifyRecurringApplicationChargeRoot();
		shopifyRecurringApplicationChargeRoot.setRecurringApplicationCharge(shopifyRecurringApplicationCharge);
		final String expectedResponseBodyString = getJsonString(ShopifyRecurringApplicationChargeRoot.class,
				shopifyRecurringApplicationChargeRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyRecurringApplicationCharge actualShopifyRecurringApplicationCharge = shopifySdk
				.getRecurringApplicationCharge("Some-Charge_id");

		assertEquals(shopifyRecurringApplicationCharge.getId(), actualShopifyRecurringApplicationCharge.getId());
		assertEquals(shopifyRecurringApplicationCharge.getActivatedOn(),
				actualShopifyRecurringApplicationCharge.getActivatedOn());
		assertEquals(shopifyRecurringApplicationCharge.getApiClientId(),
				actualShopifyRecurringApplicationCharge.getApiClientId());
		assertEquals(shopifyRecurringApplicationCharge.getBillingOn(),
				actualShopifyRecurringApplicationCharge.getBillingOn());
		assertEquals(shopifyRecurringApplicationCharge.getCancelledOn(),
				actualShopifyRecurringApplicationCharge.getCancelledOn());
		assertEquals(shopifyRecurringApplicationCharge.getCappedAmount(),
				actualShopifyRecurringApplicationCharge.getCappedAmount());
		assertEquals(shopifyRecurringApplicationCharge.getConfirmationUrl(),
				actualShopifyRecurringApplicationCharge.getConfirmationUrl());
		assertEquals(shopifyRecurringApplicationCharge.getCreatedAt(),
				actualShopifyRecurringApplicationCharge.getCreatedAt());
		assertEquals(shopifyRecurringApplicationCharge.getName(), actualShopifyRecurringApplicationCharge.getName());
		assertEquals(shopifyRecurringApplicationCharge.getPrice(), actualShopifyRecurringApplicationCharge.getPrice());
		assertEquals(shopifyRecurringApplicationCharge.getReturnUrl(),
				actualShopifyRecurringApplicationCharge.getReturnUrl());
		assertEquals(shopifyRecurringApplicationCharge.getStatus(),
				actualShopifyRecurringApplicationCharge.getStatus());
		assertEquals(shopifyRecurringApplicationCharge.getTerms(), actualShopifyRecurringApplicationCharge.getTerms());
		assertEquals(shopifyRecurringApplicationCharge.getTrialDays(),
				actualShopifyRecurringApplicationCharge.getTrialDays());
		assertEquals(shopifyRecurringApplicationCharge.getTrialEndsOn(),
				actualShopifyRecurringApplicationCharge.getTrialEndsOn());
		assertEquals(shopifyRecurringApplicationCharge.getUpdatedOn(),
				actualShopifyRecurringApplicationCharge.getUpdatedOn());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndSomeRecurringChargeIdRequestWhenActivatingRecurringApplicationChargeThenActivateRecurringApplicationCharge()
			throws Exception {

		final String expectedGetPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH)
				.append(ShopifySdk.RECURRING_APPLICATION_CHARGES).append(FORWARD_SLASH).append("Some-Charge_id")
				.toString();

		final ShopifyRecurringApplicationCharge shopifyRecurringApplicationCharge = new ShopifyRecurringApplicationCharge();
		shopifyRecurringApplicationCharge.setActivatedOn("2018-01-01");
		shopifyRecurringApplicationCharge.setBillingOn("2019-01-01");
		shopifyRecurringApplicationCharge.setConfirmationUrl("https://www.google.com/1");
		shopifyRecurringApplicationCharge.setCreatedAt("2018-01-01");
		shopifyRecurringApplicationCharge.setCappedAmount(BigDecimal.valueOf(41.42));
		shopifyRecurringApplicationCharge.setReturnUrl("https://www.google.com/2");
		shopifyRecurringApplicationCharge.setName("Some Name");
		shopifyRecurringApplicationCharge.setPrice(BigDecimal.valueOf(41.42));
		shopifyRecurringApplicationCharge.setApiClientId("787428734234");
		shopifyRecurringApplicationCharge.setTerms("some terms");
		shopifyRecurringApplicationCharge.setTrialDays(720);
		shopifyRecurringApplicationCharge.setTrialEndsOn("2020-01-01");
		shopifyRecurringApplicationCharge.setTest(true);
		shopifyRecurringApplicationCharge.setStatus("active");

		final ShopifyRecurringApplicationChargeRoot shopifyRecurringApplicationChargeRoot = new ShopifyRecurringApplicationChargeRoot();
		shopifyRecurringApplicationChargeRoot.setRecurringApplicationCharge(shopifyRecurringApplicationCharge);
		final String expectedResponseBodyString = getJsonString(ShopifyRecurringApplicationChargeRoot.class,
				shopifyRecurringApplicationChargeRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedGetPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final JsonBodyCapture actualActivateRequestBody = new JsonBodyCapture();
		final String expectedActivatePath = new StringBuilder().append(FORWARD_SLASH)
				.append(ShopifySdk.API_VERSION_PREFIX).append(FORWARD_SLASH).append(SOME_API_VERSION)
				.append(FORWARD_SLASH).append(ShopifySdk.RECURRING_APPLICATION_CHARGES).append(FORWARD_SLASH)
				.append("Some-Charge_id").append(FORWARD_SLASH).append(ShopifySdk.ACTIVATE).toString();

		final Status expectedActivateStatus = Status.OK;
		final int expectedActivateStatusCode = expectedActivateStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedActivatePath).withHeader("X-Shopify-Access-Token", accessToken)
						.withMethod(Method.POST).capturingBodyIn(actualActivateRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(expectedActivateStatusCode));

		final ShopifyRecurringApplicationCharge actualShopifyRecurringApplicationCharge = shopifySdk
				.activateRecurringApplicationCharge("Some-Charge_id");

		assertEquals(shopifyRecurringApplicationCharge.getName(),
				actualActivateRequestBody.getContent().get("name").asText());
		assertEquals(shopifyRecurringApplicationCharge.getTerms(),
				actualActivateRequestBody.getContent().get("terms").asText());
		assertEquals(0, shopifyRecurringApplicationCharge.getPrice()
				.compareTo(actualActivateRequestBody.getContent().get("price").decimalValue()));
		assertEquals(0, shopifyRecurringApplicationCharge.getCappedAmount()
				.compareTo(actualActivateRequestBody.getContent().get("capped_amount").decimalValue()));
		assertEquals(shopifyRecurringApplicationCharge.getReturnUrl(),
				actualActivateRequestBody.getContent().get("return_url").asText());
		assertEquals(shopifyRecurringApplicationCharge.getTrialDays(),
				actualActivateRequestBody.getContent().get("trial_days").asInt());
		assertEquals(shopifyRecurringApplicationCharge.isTest(),
				actualActivateRequestBody.getContent().get("test").asBoolean());

		assertEquals(shopifyRecurringApplicationCharge.getId(), actualShopifyRecurringApplicationCharge.getId());
		assertEquals(shopifyRecurringApplicationCharge.getActivatedOn(),
				actualShopifyRecurringApplicationCharge.getActivatedOn());
		assertEquals(shopifyRecurringApplicationCharge.getApiClientId(),
				actualShopifyRecurringApplicationCharge.getApiClientId());
		assertEquals(shopifyRecurringApplicationCharge.getBillingOn(),
				actualShopifyRecurringApplicationCharge.getBillingOn());
		assertEquals(shopifyRecurringApplicationCharge.getCancelledOn(),
				actualShopifyRecurringApplicationCharge.getCancelledOn());
		assertEquals(shopifyRecurringApplicationCharge.getCappedAmount(),
				actualShopifyRecurringApplicationCharge.getCappedAmount());
		assertEquals(shopifyRecurringApplicationCharge.getConfirmationUrl(),
				actualShopifyRecurringApplicationCharge.getConfirmationUrl());
		assertEquals(shopifyRecurringApplicationCharge.getCreatedAt(),
				actualShopifyRecurringApplicationCharge.getCreatedAt());
		assertEquals(shopifyRecurringApplicationCharge.getName(), actualShopifyRecurringApplicationCharge.getName());
		assertEquals(shopifyRecurringApplicationCharge.getPrice(), actualShopifyRecurringApplicationCharge.getPrice());
		assertEquals(shopifyRecurringApplicationCharge.getReturnUrl(),
				actualShopifyRecurringApplicationCharge.getReturnUrl());
		assertEquals(shopifyRecurringApplicationCharge.getStatus(),
				actualShopifyRecurringApplicationCharge.getStatus());
		assertEquals(shopifyRecurringApplicationCharge.getTerms(), actualShopifyRecurringApplicationCharge.getTerms());
		assertEquals(shopifyRecurringApplicationCharge.getTrialDays(),
				actualShopifyRecurringApplicationCharge.getTrialDays());
		assertEquals(shopifyRecurringApplicationCharge.getTrialEndsOn(),
				actualShopifyRecurringApplicationCharge.getTrialEndsOn());
		assertEquals(shopifyRecurringApplicationCharge.getUpdatedOn(),
				actualShopifyRecurringApplicationCharge.getUpdatedOn());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndSomeValidRequestWheRevokingOauthTokenThenReturnTrue()
			throws Exception {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.OAUTH)
				.append(FORWARD_SLASH).append(ShopifySdk.REVOKE).toString();

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.DELETE),
				giveResponse("", MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final boolean revokedOauthToken = shopifySdk.revokeOAuthToken();
		assertTrue(revokedOauthToken);

	}

	@Test
	public void givenSomeOrderIdWhenGettingOrderTransactionsThenRetrieveOrderTransactions() throws Exception {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
				.append(FORWARD_SLASH).append("Some-Order_id").append(FORWARD_SLASH).append(ShopifySdk.TRANSACTIONS)
				.toString();

		final ShopifyTransactionsRoot shopifyTransactionsRoot = new ShopifyTransactionsRoot();
		final ShopifyTransaction shopifyTransaction1 = new ShopifyTransaction();
		shopifyTransaction1.setAmount(new BigDecimal(11.11));
		shopifyTransaction1.setCurrency(Currency.getInstance("USD"));
		shopifyTransaction1.setGateway("bogus");
		shopifyTransaction1.setKind("sale");

		final ShopifyTransactionReceipt shopifyTransactionReceipt1 = new ShopifyTransactionReceipt();
		shopifyTransactionReceipt1.setApplePay(true);
		shopifyTransaction1.setReceipt(shopifyTransactionReceipt1);

		final ShopifyTransaction shopifyTransaction2 = new ShopifyTransaction();
		shopifyTransaction2.setAmount(new BigDecimal(11.11));
		shopifyTransaction2.setCurrency(Currency.getInstance("CAD"));
		shopifyTransaction2.setGateway("bogus2");
		shopifyTransaction2.setKind("sale2");

		final ShopifyTransactionReceipt shopifyTransactionReceipt2 = new ShopifyTransactionReceipt();
		shopifyTransactionReceipt2.setApplePay(false);
		shopifyTransaction2.setReceipt(shopifyTransactionReceipt2);

		shopifyTransactionsRoot.setTransactions(Arrays.asList(shopifyTransaction1, shopifyTransaction2));

		final String expectedResponseBodyString = getJsonString(ShopifyTransactionsRoot.class, shopifyTransactionsRoot);

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader("X-Shopify-Access-Token", accessToken).withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final List<ShopifyTransaction> actualShopifyTransactions = shopifySdk.getOrderTransactions("Some-Order_id");

		assertNotNull(actualShopifyTransactions);
		assertEquals(2, actualShopifyTransactions.size());
		assertEquals(shopifyTransaction1.getCurrency(), actualShopifyTransactions.get(0).getCurrency());
		assertEquals(0, shopifyTransaction1.getAmount().compareTo(actualShopifyTransactions.get(0).getAmount()));
		assertEquals(shopifyTransaction1.getGateway(), actualShopifyTransactions.get(0).getGateway());
		assertEquals(shopifyTransaction1.getKind(), actualShopifyTransactions.get(0).getKind());
		assertEquals(shopifyTransaction1.getOrderId(), actualShopifyTransactions.get(0).getOrderId());
		assertEquals(shopifyTransaction1.getParentId(), actualShopifyTransactions.get(0).getParentId());
		assertEquals(shopifyTransaction1.getReceipt().isApplePay(),
				actualShopifyTransactions.get(0).getReceipt().isApplePay());

		assertEquals(shopifyTransaction2.getCurrency(), actualShopifyTransactions.get(1).getCurrency());
		assertEquals(0, shopifyTransaction2.getAmount().compareTo(actualShopifyTransactions.get(1).getAmount()));
		assertEquals(shopifyTransaction2.getGateway(), actualShopifyTransactions.get(1).getGateway());
		assertEquals(shopifyTransaction2.getKind(), actualShopifyTransactions.get(1).getKind());
		assertEquals(shopifyTransaction2.getOrderId(), actualShopifyTransactions.get(1).getOrderId());
		assertEquals(shopifyTransaction2.getParentId(), actualShopifyTransactions.get(1).getParentId());
		assertEquals(shopifyTransaction2.getReceipt().isApplePay(),
				actualShopifyTransactions.get(1).getReceipt().isApplePay());
	}

	@Test
	public void givenStoreWithNoProductsWhenRetrievingProductsThenReturnEmptyShopifyProducts()
			throws JsonProcessingException {
		addProductsPageDriverExpectation(null, 50, 0, null);

		final ShopifyProducts actualShopifyProducts = shopifySdk.getProducts();

		assertEquals(0, actualShopifyProducts.size());
	}

	@Test
	public void givenStoreWithNoProductsAndPage1And200PageSizeWhenRetrievingProductsThenReturnEmptyShopifyProducts()
			throws JsonProcessingException {
		final int pageSize = 200;
		addProductsPageDriverExpectation("123", 200, 0, null);

		final ShopifyPage<ShopifyProduct> actualShopifyProducts = shopifySdk.getProducts("123", pageSize);

		assertNull(actualShopifyProducts.getNextPageInfo());
		assertNull(actualShopifyProducts.getPreviousPageInfo());
		assertEquals(0, actualShopifyProducts.size());
	}

	@Test
	public void givenStoreWith305ProductsWhenRetrievingProductsThenReturnShopifyProductsWith305Products()
			throws JsonProcessingException {
		addProductsPageDriverExpectation(null, 50, 50, "2");
		addProductsPageDriverExpectation("2", 50, 50, "3");
		addProductsPageDriverExpectation("3", 50, 50, "4");
		addProductsPageDriverExpectation("4", 50, 50, "5");
		addProductsPageDriverExpectation("5", 50, 50, "6");
		addProductsPageDriverExpectation("6", 50, 50, "7");
		addProductsPageDriverExpectation("7", 50, 5, null);

		final ShopifyProducts actualShopifyProducts = shopifySdk.getProducts();

		assertEquals(305, actualShopifyProducts.size());
		for (final ShopifyProduct actualShopifyProduct : actualShopifyProducts.values()) {
			assertNotNull(actualShopifyProduct.getId());
		}
	}

	@Test
	public void givenCollectionWithNoProductsWhenRetrievingProductsByCollectionThenReturnEmptyShopifyProducts()
		throws JsonProcessingException {
		addCollectionsProductsPageDriverExpectation("collection-id", null, 50, 0, null);

		final ShopifyProducts actualShopifyProducts = shopifySdk.getCollectionProducts("collection-id");

		assertEquals(0, actualShopifyProducts.size());
	}

	@Test
	public void givenCollectionWith305ProductsWhenRetrievingCollectionProductsThenReturnShopifyProductsWith305Products()
		throws JsonProcessingException {
		addCollectionsProductsPageDriverExpectation("collection-id", null, 50, 50, "2");
		addCollectionsProductsPageDriverExpectation("collection-id", "2", 50, 50, "3");
		addCollectionsProductsPageDriverExpectation("collection-id", "3", 50, 50, "4");
		addCollectionsProductsPageDriverExpectation("collection-id", "4", 50, 50, "5");
		addCollectionsProductsPageDriverExpectation("collection-id", "5", 50, 50, "6");
		addCollectionsProductsPageDriverExpectation("collection-id", "6", 50, 50, "7");
		addCollectionsProductsPageDriverExpectation("collection-id", "7", 50, 5, null);

		final ShopifyProducts actualShopifyProducts = shopifySdk.getCollectionProducts("collection-id");

		assertEquals(305, actualShopifyProducts.size());
		for (final ShopifyProduct actualShopifyProduct : actualShopifyProducts.values()) {
			assertNotNull(actualShopifyProduct.getId());
		}
	}

	@Test
	public void givenSomeCustomCollectionsCreationRequestCreateAndReturnCustomCollection()
			throws JsonProcessingException {
		final String expectedCreationPath = new StringBuilder().append(FORWARD_SLASH)
				.append(ShopifySdk.API_VERSION_PREFIX).append(FORWARD_SLASH).append(SOME_API_VERSION)
				.append(FORWARD_SLASH).append(ShopifySdk.CUSTOM_COLLECTIONS).toString();
		final ShopifyCustomCollectionRoot shopifyCustomCollectionRoot = new ShopifyCustomCollectionRoot();
		final ShopifyCustomCollection shopifyCustomCollection = new ShopifyCustomCollection();

		shopifyCustomCollection.setId("123");
		shopifyCustomCollection.setTitle("Some Title");
		shopifyCustomCollection.setHandle("handle");
		shopifyCustomCollection.setPublished(true);
		shopifyCustomCollection.setBodyHtml("Some Description");

		shopifyCustomCollection.setTemplateSuffix("some-title");
		shopifyCustomCollection.setPublishedScope("global");
		shopifyCustomCollection.setSortOrder("alpha-asc");

		shopifyCustomCollectionRoot.setCustomCollection(shopifyCustomCollection);

		final String expectedResponseBodyString = getJsonString(ShopifyCustomCollectionRoot.class,
				shopifyCustomCollectionRoot);

		final Status expectedCreationStatus = Status.CREATED;
		final int expectedCreationStatusCode = expectedCreationStatus.getStatusCode();

		final JsonBodyCapture actualCreateRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedCreationPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.POST).capturingBodyIn(actualCreateRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(expectedCreationStatusCode));

		final ShopifyCustomCollectionCreationRequest shopifyCustomCollectionCreationRequest = ShopifyCustomCollectionCreationRequest
				.newBuilder().withTitle("Some Title").withBodyHtml("Some Description").withHandle("handle")
				.withTemplateSuffix("some-title").withPublishedScope("global").withSortOrder("alpha-asc")
				.isPublished(true).build();

		final ShopifyCustomCollection actualShopifyCustomCollection = shopifySdk
				.createCustomCollection(shopifyCustomCollectionCreationRequest);

		assertCustomCollection(shopifyCustomCollection, actualShopifyCustomCollection);

		assertEquals(shopifyCustomCollectionCreationRequest.getRequest().getTitle(),
				actualCreateRequestBody.getContent().get("custom_collection").get("title").asText());
		assertEquals(shopifyCustomCollectionCreationRequest.getRequest().getBodyHtml(),
				actualCreateRequestBody.getContent().get("custom_collection").get("body_html").asText());
		assertEquals(shopifyCustomCollectionCreationRequest.getRequest().getHandle(),
				actualCreateRequestBody.getContent().get("custom_collection").get("handle").asText());
		assertEquals(shopifyCustomCollectionCreationRequest.getRequest().getPublishedScope(),
				actualCreateRequestBody.getContent().get("custom_collection").get("published_scope").asText());
		assertEquals(shopifyCustomCollectionCreationRequest.getRequest().getSortOrder(),
				actualCreateRequestBody.getContent().get("custom_collection").get("sort_order").asText());
		assertEquals(shopifyCustomCollectionCreationRequest.getRequest().isPublished(),
				actualCreateRequestBody.getContent().get("custom_collection").get("published").asBoolean());
		assertNotNull(actualShopifyCustomCollection);
	}

	@Test
	public void givenSomeCustomCollectionsExistOnMultiplePagesWhenRetrievingCustomCollectionsThenExpectAllCustomCollectionsToBeReturned()
			throws JsonProcessingException {
		final String expectedGetPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH)
				.append(ShopifySdk.CUSTOM_COLLECTIONS).toString();

		final ShopifyCustomCollection shopifyCustomCollection1 = new ShopifyCustomCollection();
		shopifyCustomCollection1.setId("123");
		shopifyCustomCollection1.setTitle("Some Title");
		shopifyCustomCollection1.setHandle("handle");
		shopifyCustomCollection1.setPublished(true);
		shopifyCustomCollection1.setBodyHtml("Some Description");
		shopifyCustomCollection1.setTemplateSuffix("some-title");
		shopifyCustomCollection1.setPublishedScope("global");
		shopifyCustomCollection1.setSortOrder("alpha-asc");

		final ShopifyCustomCollection shopifyCustomCollection2 = new ShopifyCustomCollection();
		shopifyCustomCollection2.setId("123");
		shopifyCustomCollection2.setTitle("Some Title");
		shopifyCustomCollection2.setHandle("handle");
		shopifyCustomCollection2.setPublished(true);
		shopifyCustomCollection2.setBodyHtml("Some Description");
		shopifyCustomCollection2.setTemplateSuffix("some-title");
		shopifyCustomCollection2.setPublishedScope("global");
		shopifyCustomCollection2.setSortOrder("alpha-asc");

		final ShopifyCustomCollectionsRoot shopifyCustomCollectionsRootPage1 = new ShopifyCustomCollectionsRoot();
		shopifyCustomCollectionsRootPage1
				.setCustomCollections(Arrays.asList(shopifyCustomCollection1, shopifyCustomCollection2));

		final String expectedResponseBodyString1 = getJsonString(ShopifyCustomCollectionsRoot.class,
				shopifyCustomCollectionsRootPage1);

		final Status expectedCreationStatus = Status.OK;
		final int expectedCreationStatusCode = expectedCreationStatus.getStatusCode();

		driver.addExpectation(
				onRequestTo(expectedGetPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET).withParam("limit", 50),
				giveResponse(expectedResponseBodyString1, MediaType.APPLICATION_JSON)
						.withStatus(expectedCreationStatusCode)
						.withHeader("Link", "<http://localhost?page_info=123>; rel=\"next\""));

		final ShopifyCustomCollection shopifyCustomCollection3 = new ShopifyCustomCollection();
		shopifyCustomCollection3.setId("123");
		shopifyCustomCollection3.setTitle("Some Title");
		shopifyCustomCollection3.setHandle("handle");
		shopifyCustomCollection3.setPublished(true);
		shopifyCustomCollection3.setBodyHtml("Some Description");
		shopifyCustomCollection3.setTemplateSuffix("some-title");
		shopifyCustomCollection3.setPublishedScope("global");
		shopifyCustomCollection3.setSortOrder("alpha-asc");

		final ShopifyCustomCollectionsRoot shopifyCustomCollectionsRootPage2 = new ShopifyCustomCollectionsRoot();
		shopifyCustomCollectionsRootPage2.setCustomCollections(Arrays.asList(shopifyCustomCollection3));
		final String expectedResponseBodyString2 = getJsonString(ShopifyCustomCollectionsRoot.class,
				shopifyCustomCollectionsRootPage2);
		driver.addExpectation(
				onRequestTo(expectedGetPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET).withParam("limit", 50).withParam("page_info", "123"),
				giveResponse(expectedResponseBodyString2, MediaType.APPLICATION_JSON)
						.withStatus(expectedCreationStatusCode));
		final List<ShopifyCustomCollection> actualShopifyCustomCollections = shopifySdk.getCustomCollections();

		assertEquals(3, actualShopifyCustomCollections.size());

		final ShopifyCustomCollection firstActualShopifyCustomCollection = actualShopifyCustomCollections.get(0);
		assertCustomCollection(shopifyCustomCollection1, firstActualShopifyCustomCollection);

		final ShopifyCustomCollection secondActualShopifyCustomCollection = actualShopifyCustomCollections.get(1);
		assertCustomCollection(shopifyCustomCollection2, secondActualShopifyCustomCollection);

		final ShopifyCustomCollection thirdActualShopifyCustomCollection = actualShopifyCustomCollections.get(2);
		assertCustomCollection(shopifyCustomCollection3, thirdActualShopifyCustomCollection);
	}

	private void assertCustomCollection(final ShopifyCustomCollection expectedShopifyCustomCollection,
			final ShopifyCustomCollection actualShopifyCustomCollection) {
		assertEquals(expectedShopifyCustomCollection.getId(), actualShopifyCustomCollection.getId());
		assertEquals(expectedShopifyCustomCollection.getAdminGraphqlApiId(),
				actualShopifyCustomCollection.getAdminGraphqlApiId());
		assertEquals(expectedShopifyCustomCollection.getBodyHtml(), actualShopifyCustomCollection.getBodyHtml());
		assertEquals(expectedShopifyCustomCollection.getHandle(), actualShopifyCustomCollection.getHandle());
		assertEquals(expectedShopifyCustomCollection.getPublishedAt(), actualShopifyCustomCollection.getPublishedAt());
		assertEquals(expectedShopifyCustomCollection.getSortOrder(), actualShopifyCustomCollection.getSortOrder());
		assertEquals(expectedShopifyCustomCollection.getTemplateSuffix(),
				actualShopifyCustomCollection.getTemplateSuffix());
		assertEquals(expectedShopifyCustomCollection.getTitle(), actualShopifyCustomCollection.getTitle());
		assertEquals(expectedShopifyCustomCollection.getUpdatedAt(), actualShopifyCustomCollection.getUpdatedAt());
	}

	@Test
	public void givenSomeSmartCollectionsExistOnMultiplePagesWhenRetrievingSmartCollectionsThenExpectAllSmartCollectionsToBeReturned()
			throws JsonProcessingException {
		final String expectedGetPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH)
				.append(ShopifySdk.SMART_COLLECTIONS).toString();

		final ShopifySmartCollection shopifySmartCollection1 = new ShopifySmartCollection();
		shopifySmartCollection1.setId("123");
		shopifySmartCollection1.setTitle("Some Title");
		shopifySmartCollection1.setHandle("handle");
		shopifySmartCollection1.setBodyHtml("Some Description");
		shopifySmartCollection1.setTemplateSuffix("some-title");
		shopifySmartCollection1.setPublishedScope("global");
		shopifySmartCollection1.setSortOrder("alpha-asc");

		final ShopifySmartCollection shopifySmartCollection2 = new ShopifySmartCollection();
		shopifySmartCollection2.setId("123");
		shopifySmartCollection2.setTitle("Some Title");
		shopifySmartCollection2.setHandle("handle");
		shopifySmartCollection2.setBodyHtml("Some Description");
		shopifySmartCollection2.setTemplateSuffix("some-title");
		shopifySmartCollection2.setPublishedScope("global");
		shopifySmartCollection2.setSortOrder("alpha-asc");

		final ShopifySmartCollectionsRoot shopifySmartCollectionsRootPage1 = new ShopifySmartCollectionsRoot();
		shopifySmartCollectionsRootPage1
				.setSmartCollections(Arrays.asList(shopifySmartCollection1, shopifySmartCollection2));

		final String expectedResponseBodyString1 = getJsonString(ShopifySmartCollectionsRoot.class,
				shopifySmartCollectionsRootPage1);

		final Status expectedCreationStatus = Status.OK;
		final int expectedCreationStatusCode = expectedCreationStatus.getStatusCode();

		driver.addExpectation(
				onRequestTo(expectedGetPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET).withParam("limit", 50),
				giveResponse(expectedResponseBodyString1, MediaType.APPLICATION_JSON)
						.withStatus(expectedCreationStatusCode)
						.withHeader("Link", "<http://localhost?page_info=123>; rel=\"next\""));

		final ShopifySmartCollection shopifySmartCollection3 = new ShopifySmartCollection();
		shopifySmartCollection3.setId("123");
		shopifySmartCollection3.setTitle("Some Title");
		shopifySmartCollection3.setHandle("handle");
		shopifySmartCollection3.setBodyHtml("Some Description");
		shopifySmartCollection3.setTemplateSuffix("some-title");
		shopifySmartCollection3.setPublishedScope("global");
		shopifySmartCollection3.setSortOrder("alpha-asc");

		final ShopifySmartCollectionsRoot shopifySmartCollectionsRootPage2 = new ShopifySmartCollectionsRoot();
		shopifySmartCollectionsRootPage2.setSmartCollections(Arrays.asList(shopifySmartCollection3));
		final String expectedResponseBodyString2 = getJsonString(ShopifySmartCollectionsRoot.class,
				shopifySmartCollectionsRootPage2);
		driver.addExpectation(
				onRequestTo(expectedGetPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.GET).withParam("limit", 50).withParam("page_info", "123"),
				giveResponse(expectedResponseBodyString2, MediaType.APPLICATION_JSON)
						.withStatus(expectedCreationStatusCode));
		final List<ShopifySmartCollection> actualShopifySmartCollections = shopifySdk.getSmartCollections();

		assertEquals(3, actualShopifySmartCollections.size());

		final ShopifySmartCollection firstActualShopifySmartCollection = actualShopifySmartCollections.get(0);
		assertSmartCollection(shopifySmartCollection1, firstActualShopifySmartCollection);

		final ShopifySmartCollection secondActualShopifySmartCollection = actualShopifySmartCollections.get(1);
		assertSmartCollection(shopifySmartCollection2, secondActualShopifySmartCollection);

		final ShopifySmartCollection thirdActualShopifySmartCollection = actualShopifySmartCollections.get(2);
		assertSmartCollection(shopifySmartCollection3, thirdActualShopifySmartCollection);
	}

	private void assertSmartCollection(final ShopifySmartCollection expectedShopifySmartCollection,
			final ShopifySmartCollection actualShopifySmartCollection) {
		assertEquals(expectedShopifySmartCollection.getId(), actualShopifySmartCollection.getId());
		assertEquals(expectedShopifySmartCollection.getBodyHtml(), actualShopifySmartCollection.getBodyHtml());
		assertEquals(expectedShopifySmartCollection.getHandle(), actualShopifySmartCollection.getHandle());
		assertEquals(expectedShopifySmartCollection.getPublishedAt(), actualShopifySmartCollection.getPublishedAt());
		assertEquals(expectedShopifySmartCollection.getSortOrder(), actualShopifySmartCollection.getSortOrder());
		assertEquals(expectedShopifySmartCollection.getTemplateSuffix(),
				actualShopifySmartCollection.getTemplateSuffix());
		assertEquals(expectedShopifySmartCollection.getTitle(), actualShopifySmartCollection.getTitle());
		assertEquals(expectedShopifySmartCollection.getUpdatedAt(), actualShopifySmartCollection.getUpdatedAt());
	}

	private void addProductsPageDriverExpectation(final String pageInfo, final int pageLimit, final int pageSize,
			final String nextPageInfo) throws JsonProcessingException {
		final ShopifyProductsRoot pageShopifyProductsRoot = new ShopifyProductsRoot();
		final List<ShopifyProduct> firstPageShopifyProducts = new ArrayList<>(pageLimit);
		for (int i = 0; i < pageSize; i++) {
			final ShopifyProduct shopifyProduct = new ShopifyProduct();
			shopifyProduct.setId(UUID.randomUUID().toString());
			firstPageShopifyProducts.add(shopifyProduct);
		}
		pageShopifyProductsRoot.setProducts(firstPageShopifyProducts);

		final String responseBodyString = getJsonString(ShopifyProductsRoot.class, pageShopifyProductsRoot);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION).append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
				.toString();
		ClientDriverRequest expectedRequest = onRequestTo(expectedPath)
				.withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
				.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, pageLimit).withMethod(Method.GET);
		if (pageInfo != null) {
			expectedRequest = expectedRequest.withParam(ShopifySdk.PAGE_INFO_QUERY_PARAMETER, pageInfo);
		}
		ClientDriverResponse expectedResponse = giveResponse(responseBodyString, MediaType.APPLICATION_JSON)
				.withStatus(Status.OK.getStatusCode());
		if (nextPageInfo != null) {
			expectedResponse = expectedResponse.withHeader("Link",
					"<http://localhost?page_info=" + nextPageInfo + ">; rel=\"next\"");
		}

		driver.addExpectation(expectedRequest, expectedResponse).anyTimes();

	}

	private void addCollectionsProductsPageDriverExpectation(final String collectionId, final String pageInfo, final int pageLimit, final int pageSize,
			final String nextPageInfo) throws JsonProcessingException {
		final ShopifyProductsRoot pageShopifyProductsRoot = new ShopifyProductsRoot();
		final List<ShopifyProduct> firstPageShopifyProducts = new ArrayList<>(pageLimit);
		for (int i = 0; i < pageSize; i++) {
			final ShopifyProduct shopifyProduct = new ShopifyProduct();
			shopifyProduct.setId(UUID.randomUUID().toString());
			firstPageShopifyProducts.add(shopifyProduct);
		}
		pageShopifyProductsRoot.setProducts(firstPageShopifyProducts);

		final String responseBodyString = getJsonString(ShopifyProductsRoot.class, pageShopifyProductsRoot);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.API_VERSION_PREFIX)
				.append(FORWARD_SLASH).append(SOME_API_VERSION)
				.append(FORWARD_SLASH).append(ShopifySdk.COLLECTIONS)
				.append(FORWARD_SLASH).append(collectionId)
				.append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS).append(ShopifySdk.JSON)
				.toString();
		ClientDriverRequest expectedRequest = onRequestTo(expectedPath)
				.withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
				.withParam(ShopifySdk.LIMIT_QUERY_PARAMETER, pageLimit).withMethod(Method.GET);
		if (pageInfo != null) {
			expectedRequest = expectedRequest.withParam(ShopifySdk.PAGE_INFO_QUERY_PARAMETER, pageInfo);
		}
		ClientDriverResponse expectedResponse = giveResponse(responseBodyString, MediaType.APPLICATION_JSON)
				.withStatus(Status.OK.getStatusCode());
		if (nextPageInfo != null) {
			expectedResponse = expectedResponse.withHeader("Link",
					"<http://localhost?page_info=" + nextPageInfo + ">; rel=\"next\"");
		}

		driver.addExpectation(expectedRequest, expectedResponse).anyTimes();

	}

	private <T> String getJsonString(final Class<T> clazz, final T object) throws JsonProcessingException {

		final ObjectMapper objectMapper = ShopifySdkObjectMapper.buildMapper();

		return objectMapper.writeValueAsString(object);
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

	private ShopifyCustomer buildShopifyCustomer() {
		final ShopifyCustomer shopifyCustomer = new ShopifyCustomer();
		shopifyCustomer.setId("some-customer-id");
		shopifyCustomer.setFirstName("Austin");
		shopifyCustomer.setLastname("Brown");
		shopifyCustomer.setEmail("me@austincbrown.com");
		shopifyCustomer.setNote("A cool dude");
		shopifyCustomer.setOrdersCount(3);
		shopifyCustomer.setState("New York");
		shopifyCustomer.setPhone("7188675309");
		shopifyCustomer.setTotalSpent(new BigDecimal(32.12));
		return shopifyCustomer;
	}

	private void assertCustomers(final List<ShopifyCustomer> actualCustomers) {
		assertEquals(1, actualCustomers.size());
		final ShopifyCustomer actualCustomer = actualCustomers.get(0);
		assertCustomer(actualCustomer);
	}

	private void assertCustomer(final ShopifyCustomer actualCustomer) {
		assertEquals("some-customer-id", actualCustomer.getId());
		assertEquals("Austin", actualCustomer.getFirstName());
		assertEquals("Brown", actualCustomer.getLastname());
		assertEquals("me@austincbrown.com", actualCustomer.getEmail());
		assertEquals("A cool dude", actualCustomer.getNote());
		assertEquals(3, actualCustomer.getOrdersCount());
		assertEquals("New York", actualCustomer.getState());
		assertEquals("7188675309", actualCustomer.getPhone());
		assertEquals(new BigDecimal(32.12), actualCustomer.getTotalSpent());
	}

	private ShopifyTaxLine buildTaxLine(final BigDecimal price, final BigDecimal rate, final String title) {
		final ShopifyTaxLine shopifyTaxLine1LineItem1 = new ShopifyTaxLine();
		shopifyTaxLine1LineItem1.setPrice(price);
		shopifyTaxLine1LineItem1.setRate(rate);
		shopifyTaxLine1LineItem1.setTitle(title);
		return shopifyTaxLine1LineItem1;
	}

	private ShopifyProperty buildShopifyProperty(final String name, final String value) {
		final ShopifyProperty shopifyProperty = new ShopifyProperty();
		shopifyProperty.setName(name);
		shopifyProperty.setValue(value);
		return shopifyProperty;
	}
}
