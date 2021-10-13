package com.shopify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverResponse;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.clientdriver.capture.StringBodyCapture;
import com.shopify.exceptions.ShopifyClientException;
import com.shopify.mappers.ShopifySdkObjectMapper;
import com.shopify.model.*;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ShopifySdkTest {

	private static final String SOME_API_VERSION = "2020-10";
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
				.withConnectionTimeout(500, TimeUnit.MILLISECONDS).build();

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
}
