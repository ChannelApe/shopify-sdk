package com.shopify;

import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.clientdriver.capture.JsonBodyCapture;
import com.github.restdriver.clientdriver.capture.StringBodyCapture;
import com.shopify.exceptions.ShopifyClientException;
import com.shopify.exceptions.ShopifyErrorResponseException;
import com.shopify.jaxbproviders.ShopifySdkJsonProvider;
import com.shopify.model.Count;
import com.shopify.model.Image;
import com.shopify.model.ImageAltTextCreationRequest;
import com.shopify.model.Metafield;
import com.shopify.model.MetafieldRoot;
import com.shopify.model.MetafieldValueType;
import com.shopify.model.MetafieldsRoot;
import com.shopify.model.OrderRiskRecommendation;
import com.shopify.model.Shop;
import com.shopify.model.ShopifyAccessTokenRoot;
import com.shopify.model.ShopifyAddress;
import com.shopify.model.ShopifyCustomer;
import com.shopify.model.ShopifyFulfillment;
import com.shopify.model.ShopifyFulfillmentCreationRequest;
import com.shopify.model.ShopifyFulfillmentRoot;
import com.shopify.model.ShopifyFulfillmentUpdateRequest;
import com.shopify.model.ShopifyGiftCard;
import com.shopify.model.ShopifyGiftCardCreationRequest;
import com.shopify.model.ShopifyGiftCardRoot;
import com.shopify.model.ShopifyImageRoot;
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
import com.shopify.model.ShopifyOrdersRoot;
import com.shopify.model.ShopifyProduct;
import com.shopify.model.ShopifyProductCreationRequest;
import com.shopify.model.ShopifyProductMetafieldCreationRequest;
import com.shopify.model.ShopifyProductRoot;
import com.shopify.model.ShopifyProductUpdateRequest;
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

	private static final DateTime SOME_DATE_TIME = new DateTime();
	private static final ShopifyCustomer SOME_CUSTOMER = new ShopifyCustomer();
	private static final String FORWARD_SLASH = "/";
	private final String accessToken = "09382489782734897289374829374";

	private ShopifySdk shopifySdk;

	@Rule
	public ClientDriverRule driver = new ClientDriverRule();

	@Before
	public void setUp() throws JsonProcessingException {
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
				.withClientSecret("some-client-secret").withAuthorizationToken("3892742738482").build();

		assertNull(shopifySdk.getAccessToken());
		shopifySdk.getShop();

	}

	@Test
	public void givenSomeShopifyFulfillmenmtCreationRequestWhenCreatingShopifyFulfillmentThenCreateAndReturnFulfillment()
			throws JsonProcessingException {

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId("some_line_item_id");
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS).append("/1234/")
				.append(ShopifySdk.FULFILLMENTS).toString();
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

		final ShopifyFulfillmentCreationRequest request = ShopifyFulfillmentCreationRequest.newBuilder()
				.withOrderId("1234").withTrackingCompany("USPS").withTrackingNumber("12341234").withNotifyCustomer(true)
				.withLineItems(Arrays.asList(lineItem)).withLocationId("1")
				.withTrackingUrls(Arrays.asList("tracking_url1", "tracking_url2")).build();

		final ShopifyFulfillment actualShopifyFulfillment = shopifySdk.createFulfillment(request);

		assertValidFulfillment(currentFulfillment, actualShopifyFulfillment);

	}

	@Test
	public void givenSomeShopifyFulfillmenmtUpdateRequestWhenUpdatingShopifyFulfillmentThenUpdateAndReturnFulfillment()
			throws JsonProcessingException {

		final ShopifyLineItem lineItem = new ShopifyLineItem();
		lineItem.setId("some_line_item_id");
		lineItem.setSku("some_sku");
		lineItem.setQuantity(5L);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS).append("/1234/")
				.append(ShopifySdk.FULFILLMENTS).append("/4567").toString();
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
	public void givenSomePageAndCreatedAtMinAndCreatedAtMaxOrdersWhenRetrievingOrdersThenRetrieveOrdersWithCorrectValues()
			throws JsonProcessingException {
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
			throws JsonProcessingException {
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
	public void givenSomeOrderIdWhenClosingOrderThenCloseAndReturnOrder() throws JsonProcessingException {
		final String someOrderId = "1234";

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
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

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
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
			throws JsonProcessingException {

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
			throws JsonProcessingException {

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
			throws JsonProcessingException {

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
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestAndUpdatingVariantThenUpdateAndReturnVariant()
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
		shopifyVariant.setFulfillmentService("manual");
		shopifyVariant.setInventoryItemId("123");
		shopifyVariant.setProductId("123");
		shopifyVariantRoot.setVariant(shopifyVariant);

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.VARIANTS)
				.append(FORWARD_SLASH).append("999").toString();

		final String expectedResponseBodyString = getJsonString(ShopifyVariantRoot.class, shopifyVariantRoot);

		final ShopifyImageRoot shopifyImageRoot = new ShopifyImageRoot();
		final Image shopifyImage = new Image();
		shopifyImage.setId("47382748");
		final List<Metafield> metafields = ImageAltTextCreationRequest.newBuilder()
				.withImageAltText(shopifyVariant.getTitle()).build();
		shopifyImage.setMetafields(metafields);
		shopifyImage.setVariantIds(Arrays.asList("999"));
		shopifyImage.setSource("https://channelape.com/1.png");
		shopifyImageRoot.setImage(shopifyImage);

		final String expectedImageResponseBodyString = getJsonString(ShopifyImageRoot.class, shopifyImageRoot);

		final String expectedImagePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
				.append(FORWARD_SLASH).append("123").append(FORWARD_SLASH).append(ShopifySdk.IMAGES).toString();

		final Status expectedImageStatus = Status.OK;
		final int expectedImageStatusCode = expectedImageStatus.getStatusCode();
		final JsonBodyCapture actualImageRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedImagePath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
						.withMethod(Method.POST).capturingBodyIn(actualImageRequestBody),
				giveResponse(expectedImageResponseBodyString, MediaType.APPLICATION_JSON)
						.withStatus(expectedImageStatusCode));

		final Status expectedStatus = Status.OK;
		final int expectedStatusCode = expectedStatus.getStatusCode();
		final JsonBodyCapture actualRequestBody = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken).withMethod(Method.PUT)
						.capturingBodyIn(actualRequestBody),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final ShopifyVariantUpdateRequest shopifyVariantUpdateRequest = ShopifyVariantUpdateRequest.newBuilder()
				.withCurrentShopifyVariant(shopifyVariant).withSamePrice().withSameCompareAtPrice().withSameSku()
				.withSameBarcode().withSameWeight().withAvailable(3).withSameFirstOption().withSameSecondOption()
				.withSameThirdOption().withImageSource("test").withSameInventoryManagement().withSameInventoryPolicy()
				.withSameFulfillmentService().withSameRequiresShipping().withSameTaxable().withSameInventoryItemId()
				.build();

		final ShopifyVariant actualShopifyVariant = shopifySdk.updateVariant(shopifyVariantUpdateRequest);

		assertNotNull(actualShopifyVariant);
		assertEquals(shopifyVariant.getId(), actualRequestBody.getContent().get("variant").get("id").asText());
		assertEquals(shopifyVariant.getBarcode(),
				actualRequestBody.getContent().get("variant").get("barcode").asText());
		assertEquals(shopifyVariant.getSku(), actualRequestBody.getContent().get("variant").get("sku").asText());
		assertEquals(shopifyVariant.getFulfillmentService(),
				actualRequestBody.getContent().get("variant").get("fulfillment_service").asText());
		assertEquals(shopifyVariant.getInventoryItemId(),
				actualRequestBody.getContent().get("variant").get("inventory_item_id").asText());
		assertEquals(shopifyVariant.getImageId(),
				actualRequestBody.getContent().get("variant").get("image_id").asText());
		assertEquals(shopifyVariant.getOption1(),
				actualRequestBody.getContent().get("variant").get("option1").asText());
		assertEquals(shopifyVariant.getOption2(),
				actualRequestBody.getContent().get("variant").get("option2").asText());
		assertEquals(shopifyVariant.getOption3(),
				actualRequestBody.getContent().get("variant").get("option3").asText());
		assertEquals(shopifyVariant.getPrice(),
				actualRequestBody.getContent().get("variant").get("price").decimalValue());
		assertEquals(shopifyVariant.isRequiresShipping(),
				actualRequestBody.getContent().get("variant").get("requires_shipping").asBoolean());
		assertEquals(shopifyVariant.getProductId(),
				actualRequestBody.getContent().get("variant").get("product_id").asText());

		assertEquals(shopifyVariant.getId(), actualShopifyVariant.getId());
		assertEquals(shopifyVariant.getBarcode(), actualShopifyVariant.getBarcode());
		assertEquals(shopifyVariant.getSku(), actualShopifyVariant.getSku());
		assertEquals(shopifyVariant.getFulfillmentService(), actualShopifyVariant.getFulfillmentService());
		assertEquals(shopifyVariant.getInventoryItemId(), actualShopifyVariant.getInventoryItemId());
		assertEquals("1", actualShopifyVariant.getImageId());
		assertEquals(shopifyVariant.getOption1(), actualShopifyVariant.getOption1());
		assertEquals(shopifyVariant.getOption2(), actualShopifyVariant.getOption2());
		assertEquals(shopifyVariant.getOption3(), actualShopifyVariant.getOption3());
		assertEquals(shopifyVariant.getPrice(), actualShopifyVariant.getPrice());
		assertEquals(shopifyVariant.isRequiresShipping(), actualShopifyVariant.isRequiresShipping());
		assertEquals(shopifyVariant.getProductId(), actualShopifyVariant.getProductId());

	}

	@Test
	public void givenSomeProductCreationRequestWhenCreatingProductThenCreateAndReturnProduct()
			throws JsonProcessingException {
		final String expectedCreationPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
				.toString();
		final String expectedUpdatePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
				.append(FORWARD_SLASH).append("123").toString();
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
		final String expectedCreationPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
				.append(FORWARD_SLASH).append("123").toString();
		final String expectedUpdatePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
				.append(FORWARD_SLASH).append("123").toString();
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
			throws JsonProcessingException {

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

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
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

		final String expectedImagePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
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
		assertEquals(metafield1.getValueType(), productMetafields.get(0).getValueType());

		assertEquals(metafield2.getId(), productMetafields.get(1).getId());
		assertEquals(0, metafield2.getCreatedAt().compareTo(productMetafields.get(1).getCreatedAt()));
		assertEquals(metafield2.getKey(), productMetafields.get(1).getKey());
		assertEquals(metafield2.getNamespace(), productMetafields.get(1).getNamespace());
		assertEquals(metafield2.getOwnerId(), productMetafields.get(1).getOwnerId());
		assertEquals(metafield2.getOwnerResource(), productMetafields.get(1).getOwnerResource());
		assertEquals(metafield2.getUpdatedAt(), productMetafields.get(1).getUpdatedAt());
		assertEquals(metafield2.getValue(), productMetafields.get(1).getValue());
		assertEquals(metafield2.getValueType(), productMetafields.get(1).getValueType());
	}

	@Test
	public void givenSomeOrderIdWhenRetrievingOrderThenRetrieveOrder() throws JsonProcessingException {
		final ShopifyOrderRoot shopifyOrderRoot = new ShopifyOrderRoot();

		final ShopifyOrder shopifyOrder = new ShopifyOrder();
		shopifyOrder.setId("123");
		shopifyOrderRoot.setOrder(shopifyOrder);
		final String expectedImageResponseBodyString = getJsonString(ShopifyOrderRoot.class, shopifyOrderRoot);

		final String expectedImagePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
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
	public void givenSomePageWhenRetrievingOrdersThenRetrieveOrdersWithCorrectValues() throws JsonProcessingException {
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
		driver.addExpectation(onRequestTo(expectedPath).withHeader(ShopifySdk.ACCESS_TOKEN_HEADER, accessToken)
				.withParam("status", "any").withParam("limit", 250).withParam("page", "1").withMethod(Method.GET),
				giveResponse(expectedResponseBodyString, MediaType.APPLICATION_JSON).withStatus(expectedStatusCode));

		final List<ShopifyOrder> shopifyOrders = shopifySdk.getOrders(1);

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

		final String expectedImagePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
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

		final String expectedImagePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
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

		final String expectedImagePath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
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

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.VARIANTS)
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
			throws JsonProcessingException {
		final ShopifyVariant currentShopifyVariant = new ShopifyVariant();
		currentShopifyVariant.setId("98746868985974");
		currentShopifyVariant.setTitle("UK 8");
		currentShopifyVariant.setPrice(new BigDecimal("10.00"));

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
		assertEquals(Long.valueOf(1337), actualShopifyVariant.getInventoryQuantity());
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
		assertEquals("suggested_refund",
				calculateRequestBody.getContent().get("refund").get("transactions").path(0).get("kind").asText());
		assertEquals("123123",
				calculateRequestBody.getContent().get("refund").get("transactions").path(0).get("order_id").asText());
		assertEquals("44444",
				calculateRequestBody.getContent().get("refund").get("transactions").path(0).get("parent_id").asText());

		assertEquals("999999", refundRequestBody.getContent().get("refund").get("id").asText());
		assertEquals("123123", refundRequestBody.getContent().get("refund").get("order_id").asText());
		assertEquals(false, refundRequestBody.getContent().get("refund").get("restock").asBoolean());
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

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.GIFT_CARDS).toString();
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

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.PRODUCTS)
				.append(FORWARD_SLASH).append("123").append(FORWARD_SLASH).append(ShopifySdk.METAFIELDS).toString();

		final Metafield metafield = new Metafield();
		metafield.setCreatedAt(new DateTime());
		metafield.setUpdatedAt(new DateTime());
		metafield.setValueType(MetafieldValueType.STRING);
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
				.withValue("38728743").withValueType(MetafieldValueType.STRING).build();
		final Metafield actualMetafield = shopifySdk.createProductMetafield(shopifyProductMetafieldCreationRequest);

		assertEquals(shopifyProductMetafieldCreationRequest.getRequest().getKey().toString(),
				actualRequestBody.getContent().get("metafield").get("key").asText());
		assertEquals(shopifyProductMetafieldCreationRequest.getRequest().getValue(),
				actualRequestBody.getContent().get("metafield").get("value").asText());

		assertEquals(shopifyProductMetafieldCreationRequest.getRequest().getNamespace(),
				actualRequestBody.getContent().get("metafield").get("namespace").asText());
		assertEquals(shopifyProductMetafieldCreationRequest.getRequest().getValueType().toString(),
				actualRequestBody.getContent().get("metafield").get("value_type").asText());
		assertNotNull(actualMetafield);
		assertEquals(metafield.getId(), actualMetafield.getId());
		assertEquals(0, metafield.getCreatedAt().compareTo(actualMetafield.getCreatedAt()));
		assertEquals(metafield.getKey(), actualMetafield.getKey());
		assertEquals(metafield.getNamespace(), actualMetafield.getNamespace());
		assertEquals(metafield.getOwnerId(), actualMetafield.getOwnerId());
		assertEquals(metafield.getOwnerResource(), actualMetafield.getOwnerResource());
		assertEquals(0, metafield.getUpdatedAt().compareTo(actualMetafield.getUpdatedAt()));
		assertEquals(metafield.getValue(), actualMetafield.getValue());
		assertEquals(metafield.getValueType(), actualMetafield.getValueType());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndSomeVariantMetafieldWhenCreatingVariantMetafieldThenCreateVariantMetafield()
			throws Exception {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.VARIANTS)
				.append(FORWARD_SLASH).append("123").append(FORWARD_SLASH).append(ShopifySdk.METAFIELDS).toString();

		final Metafield metafield = new Metafield();
		metafield.setCreatedAt(new DateTime());
		metafield.setUpdatedAt(new DateTime());
		metafield.setValueType(MetafieldValueType.STRING);
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
				.withValue("38728743").withValueType(MetafieldValueType.STRING).build();
		final Metafield actualMetafield = shopifySdk.createVariantMetafield(shopifyVariantMetafieldCreationRequest);

		assertEquals(shopifyVariantMetafieldCreationRequest.getRequest().getKey().toString(),
				actualRequestBody.getContent().get("metafield").get("key").asText());
		assertEquals(shopifyVariantMetafieldCreationRequest.getRequest().getValue(),
				actualRequestBody.getContent().get("metafield").get("value").asText());

		assertEquals(shopifyVariantMetafieldCreationRequest.getRequest().getNamespace(),
				actualRequestBody.getContent().get("metafield").get("namespace").asText());
		assertEquals(shopifyVariantMetafieldCreationRequest.getRequest().getValueType().toString(),
				actualRequestBody.getContent().get("metafield").get("value_type").asText());
		assertNotNull(actualMetafield);
		assertEquals(metafield.getId(), actualMetafield.getId());
		assertEquals(0, metafield.getCreatedAt().compareTo(actualMetafield.getCreatedAt()));
		assertEquals(metafield.getKey(), actualMetafield.getKey());
		assertEquals(metafield.getNamespace(), actualMetafield.getNamespace());
		assertEquals(metafield.getOwnerId(), actualMetafield.getOwnerId());
		assertEquals(metafield.getOwnerResource(), actualMetafield.getOwnerResource());
		assertEquals(0, metafield.getUpdatedAt().compareTo(actualMetafield.getUpdatedAt()));
		assertEquals(metafield.getValue(), actualMetafield.getValue());
		assertEquals(metafield.getValueType(), actualMetafield.getValueType());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndValidRequestWhenRetrievingVariantMetafieldsThenReturnVariantMetafields()
			throws JsonProcessingException {

		final Metafield metafield = new Metafield();
		metafield.setKey("channelape_variant_id");
		metafield.setValue("8fb0fb40-ab18-439e-bc6e-394b63ff1819");
		metafield.setNamespace("channelape");
		metafield.setOwnerId("1234");
		metafield.setValueType(MetafieldValueType.STRING);
		metafield.setOwnerResource("variant");

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.VARIANTS)
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
		assertEquals(metafield.getValueType(), actualMetafields.get(0).getValueType());
		assertEquals(metafield.getNamespace(), actualMetafields.get(0).getNamespace());
		assertEquals(metafield.getOwnerId(), actualMetafields.get(0).getOwnerId());
		assertEquals(metafield.getOwnerResource(), actualMetafields.get(0).getOwnerResource());
	}

	@Test
	public void givenSomeValidAccessTokenAndSubdomainAndSomeRecurringApplicationChargeCreationRequestWhenCreatingRecurringApplicationChargeThenCreateRecurringApplicationCharge()
			throws Exception {

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH)
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

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH)
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

		final String expectedGetPath = new StringBuilder().append(FORWARD_SLASH)
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
				.append(ShopifySdk.RECURRING_APPLICATION_CHARGES).append(FORWARD_SLASH).append("Some-Charge_id")
				.append(FORWARD_SLASH).append(ShopifySdk.ACTIVATE).toString();

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

		final String expectedPath = new StringBuilder().append(FORWARD_SLASH).append(ShopifySdk.ORDERS)
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

	private <T> String getJsonString(final Class<T> clazz, final T object) throws JsonProcessingException {
		final JacksonJsonProvider jacksonJaxbJsonProvider = new ShopifySdkJsonProvider();

		final ObjectMapper objectMapper = jacksonJaxbJsonProvider.locateMapper(clazz, MediaType.APPLICATION_JSON_TYPE);

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

}
