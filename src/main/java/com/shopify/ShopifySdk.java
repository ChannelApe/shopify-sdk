package com.shopify;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.RetryListener;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.shopify.exceptions.ShopifyClientException;
import com.shopify.exceptions.ShopifyEmptyLineItemsException;
import com.shopify.exceptions.ShopifyErrorResponseException;
import com.shopify.exceptions.ShopifyIncompatibleApiException;
import com.shopify.mappers.LegacyToFulfillmentOrderMapping;
import com.shopify.mappers.ResponseEntityToStringMapper;
import com.shopify.mappers.ShopifySdkObjectMapper;
import com.shopify.model.Count;
import com.shopify.model.Image;
import com.shopify.model.ImageAltTextCreationRequest;
import com.shopify.model.Metafield;
import com.shopify.model.MetafieldRoot;
import com.shopify.model.MetafieldsRoot;
import com.shopify.model.Shop;
import com.shopify.model.ShopifyAccessTokenRoot;
import com.shopify.model.ShopifyCancelOrderRequest;
import com.shopify.model.ShopifyCustomCollection;
import com.shopify.model.ShopifyCustomCollectionCreationRequest;
import com.shopify.model.ShopifyCustomCollectionRoot;
import com.shopify.model.ShopifyCustomCollectionsRoot;
import com.shopify.model.ShopifyCustomer;
import com.shopify.model.ShopifyCustomerRoot;
import com.shopify.model.ShopifyCustomerUpdateRequest;
import com.shopify.model.ShopifyCustomerUpdateRoot;
import com.shopify.model.ShopifyCustomersRoot;
import com.shopify.model.ShopifyFulfillment;
import com.shopify.model.ShopifyFulfillmentCreationRequest;
import com.shopify.model.ShopifyFulfillmentOrder;
import com.shopify.model.ShopifyFulfillmentOrderMoveRequestRoot;
import com.shopify.model.ShopifyFulfillmentOrderMoveResponseRoot;
import com.shopify.model.ShopifyFulfillmentOrdersRoot;
import com.shopify.model.ShopifyFulfillmentPayloadRoot;
import com.shopify.model.ShopifyFulfillmentRoot;
import com.shopify.model.ShopifyFulfillmentUpdateRequest;
import com.shopify.model.ShopifyGetCustomersRequest;
import com.shopify.model.ShopifyGiftCard;
import com.shopify.model.ShopifyGiftCardCreationRequest;
import com.shopify.model.ShopifyGiftCardRoot;
import com.shopify.model.ShopifyImageRoot;
import com.shopify.model.ShopifyInventoryLevel;
import com.shopify.model.ShopifyInventoryLevelRoot;
import com.shopify.model.ShopifyLocation;
import com.shopify.model.ShopifyLocationsRoot;
import com.shopify.model.ShopifyOrder;
import com.shopify.model.ShopifyOrderCreationRequest;
import com.shopify.model.ShopifyOrderRisk;
import com.shopify.model.ShopifyOrderRisksRoot;
import com.shopify.model.ShopifyOrderRoot;
import com.shopify.model.ShopifyOrderShippingAddressUpdateRequest;
import com.shopify.model.ShopifyOrderUpdateRoot;
import com.shopify.model.ShopifyOrdersRoot;
import com.shopify.model.ShopifyPage;
import com.shopify.model.ShopifyProduct;
import com.shopify.model.ShopifyProductCreationRequest;
import com.shopify.model.ShopifyProductMetafieldCreationRequest;
import com.shopify.model.ShopifyProductRequest;
import com.shopify.model.ShopifyProductRoot;
import com.shopify.model.ShopifyProductUpdateRequest;
import com.shopify.model.ShopifyProducts;
import com.shopify.model.ShopifyProductsRoot;
import com.shopify.model.ShopifyRecurringApplicationCharge;
import com.shopify.model.ShopifyRecurringApplicationChargeCreationRequest;
import com.shopify.model.ShopifyRecurringApplicationChargeRoot;
import com.shopify.model.ShopifyRefund;
import com.shopify.model.ShopifyRefundCreationRequest;
import com.shopify.model.ShopifyRefundRoot;
import com.shopify.model.ShopifyShop;
import com.shopify.model.ShopifySmartCollection;
import com.shopify.model.ShopifySmartCollectionRoot;
import com.shopify.model.ShopifySmartCollectionsRoot;
import com.shopify.model.ShopifyTransaction;
import com.shopify.model.ShopifyTransactionsRoot;
import com.shopify.model.ShopifyUpdateFulfillmentPayloadRoot;
import com.shopify.model.ShopifyVariant;
import com.shopify.model.ShopifyVariantMetafieldCreationRequest;
import com.shopify.model.ShopifyVariantRoot;
import com.shopify.model.ShopifyVariantUpdateRequest;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShopifySdk {

	static final String API_VERSION_PREFIX = "api";
	private static final long TWO_HUNDRED_MILLISECONDS = 200L;
	private static final String EQUALS = "=";
	private static final String AMPERSAND = "&";
	private static final String REL_PREVIOUS_HEADER_KEY = "previous";
	private static final String REL_NEXT_HEADER_KEY = "next";
	private static final String EMPTY_STRING = "";
	static final String RETRY_AFTER_HEADER = "Retry-After";
	private static final String MINIMUM_REQUEST_RETRY_DELAY_CANNOT_BE_LARGER_THAN_MAXIMUM_REQUEST_RETRY_DELAY_MESSAGE = "Maximum request retry delay must be larger than minimum request retry delay.";
	private static final String INVALID_MINIMUM_REQUEST_RETRY_DELAY_MESSAGE = "Minimum request retry delay cannot be set lower than 200 milliseconds.";

	private static final Logger LOGGER = LoggerFactory.getLogger(ShopifySdk.class);

	private static final String HTTPS = "https://";
	private static final String API_TARGET = ".myshopify.com/admin";
	static final String ACCESS_TOKEN_HEADER = "X-Shopify-Access-Token";
	static final String DEPRECATED_REASON_HEADER = "X-Shopify-API-Deprecated-Reason";
	static final String OAUTH = "oauth";
	static final String REVOKE = "revoke";
	static final String ACCESS_TOKEN = "access_token";
	static final String PRODUCTS = "products";
	static final String COLLECTIONS = "collections";
	static final String VARIANTS = "variants";
	static final String CUSTOM_COLLECTIONS = "custom_collections";
	static final String SMART_COLLECTIONS = "smart_collections";
	static final String RECURRING_APPLICATION_CHARGES = "recurring_application_charges";
	static final String ORDERS = "orders";
	static final String FULFILLMENTS = "fulfillments";
	static final String FULFILLMENT_ORDERS = "fulfillment_orders";
	static final String ACTIVATE = "activate";
	static final String IMAGES = "images";
	static final String SHOP = "shop";
	static final String COUNT = "count";
	static final String CLOSE = "close";
	static final String CANCEL = "cancel";
	static final String MOVE = "move";
	static final String UPDATE_TRACKING = "update_tracking";
	static final String METAFIELDS = "metafields";
	static final String RISKS = "risks";
	static final String LOCATIONS = "locations";
	static final String INVENTORY_LEVELS = "inventory_levels";
	static final String JSON = ".json";
	static final String LIMIT_QUERY_PARAMETER = "limit";
	static final String PAGE_INFO_QUERY_PARAMETER = "page_info";
	static final String STATUS_QUERY_PARAMETER = "status";
	static final String ANY_STATUSES = "any";
	static final String CREATED_AT_MIN_QUERY_PARAMETER = "created_at_min";
	static final String CREATED_AT_MAX_QUERY_PARAMETER = "created_at_max";
	static final String UPDATED_AT_MIN_QUERY_PARAMETER = "updated_at_min";
	static final String UPDATED_AT_MAX_QUERY_PARAMETER = "updated_at_max";
	static final String ATTRIBUTION_APP_ID_QUERY_PARAMETER = "attribution_app_id";
	static final String IDS_QUERY_PARAMETER = "ids";
	static final String SINCE_ID_QUERY_PARAMETER = "since_id";
	static final String QUERY_QUERY_PARAMETER = "query";
	static final String CALCULATE = "calculate";
	static final String REFUNDS = "refunds";
	static final String TRANSACTIONS = "transactions";
	static final String GIFT_CARDS = "gift_cards";
	static final String REFUND_KIND = "refund";
	static final String SET = "set";
	private static final String CLIENT_ID = "client_id";
	private static final String CLIENT_SECRET = "client_secret";
	private static final String AUTHORIZATION_CODE = "code";

	private static final int DEFAULT_REQUEST_LIMIT = 50;
	private static final int TOO_MANY_REQUESTS_STATUS_CODE = 429;
	private static final int UNPROCESSABLE_ENTITY_STATUS_CODE = 422;
	private static final int LOCKED_STATUS_CODE = 423;

	private static final String SHOP_RETRIEVED_MESSAGE = "Starting to make calls for Shopify store with ID of {} and name of {}";
	private static final String COULD_NOT_BE_SAVED_SHOPIFY_ERROR_MESSAGE = "could not successfully be saved";
	private static final String RETRY_FAILED_MESSAGE = "Request retry has failed.";
	private static final String DEPRECATED_SHOPIFY_CALL_ERROR_MESSAGE = "Shopify call is deprecated. Please take note of the X-Shopify-API-Deprecated-Reason and correct the call.\nRequest Location of {}\nResponse Status Code of {}\nResponse Headers of:\n{}";
	static final String GENERAL_ACCESS_TOKEN_EXCEPTION_MESSAGE = "There was a problem generating access token using shop subdomain of %s and authorization code of %s.";

	private static final Long DEFAULT_MAXIMUM_REQUEST_RETRY_TIMEOUT_IN_MILLISECONDS = 180000L;
	private static final Long DEFAULT_MAXIMUM_REQUEST_RETRY_RANDOM_DELAY_IN_MILLISECONDS = 5000L;
	private static final Long DEFAULT_MINIMUM_REQUEST_RETRY_RANDOM_DELAY_IN_MILLISECONDS = 1000L;
	private static final long DEFAULT_READ_TIMEOUT_IN_MILLISECONDS = 15000L;
	private static final long DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS = 60000L;

	private String shopSubdomain;
	private String apiUrl;
	private String apiVersion;
	private String clientId;
	private String clientSecret;
	private String authorizationToken;
	private WebTarget webTarget;
	private String accessToken;
	private long minimumRequestRetryRandomDelayMilliseconds;
	private long maximumRequestRetryRandomDelayMilliseconds;
	private long maximumRequestRetryTimeoutMilliseconds;

	private static final Client CLIENT = buildClient();

	private static final String CUSTOMERS = "customers";
	private static final String SEARCH = "search";

	public static interface OptionalsStep {

		/**
		 * The Shopify SDK uses random waits in between retry attempts. Minimum
		 * duration time to wait before retrying a failed request. Value must
		 * also be less than
		 * {@link #withMaximumRequestRetryRandomDelay(int, TimeUnit) Maximum
		 * Request Retry Random Delay}.<br>
		 * Default value is: 1 second.
		 *
		 * @param duration of the minimum retry delay
		 * @param timeUnit the time unit to be used (miliseconds, seconds, etc...)
		 * @return {@link OptionalsStep} the OptionalsStep interface
		 */
		OptionalsStep withMinimumRequestRetryRandomDelay(int duration, TimeUnit timeUnit);

		/**
		 * The Shopify SDK uses random waits in between retry attempts. Maximum
		 * duration time to wait before retrying a failed request. Value must
		 * also be more than
		 * {@link #withMinimumRequestRetryRandomDelay(int, TimeUnit) Minimum
		 * Request Retry Random Delay}.<br>
		 * Default value is: 5 seconds.
		 *
		 * @param duration of the maximum retry delay
		 * @param timeUnit the time unit to be used (miliseconds, seconds, etc...)
		 * @return {@link OptionalsStep} the OptionalsStep interface
		 */
		OptionalsStep withMaximumRequestRetryRandomDelay(int duration, TimeUnit timeUnit);

		/**
		 * Maximum duration time to keep attempting requests <br>
		 * Default value is: 3 minutes.
		 *
		 * @param duration of the request timeout
		 * @param timeUnit the time unit to be used (miliseconds, seconds, etc...)
		 * @return {@link OptionalsStep} the OptionalsStep interface
		 */
		OptionalsStep withMaximumRequestRetryTimeout(int duration, TimeUnit timeUnit);

		/**
		 * The duration to wait when connecting to Shopify's API. <br>
		 * Default value is: 1 minute.
		 *
		 * @param duration of the connection timeout
		 * @param timeUnit the time unit to be used (miliseconds, seconds, etc...)
		 * @return {@link OptionalsStep} the OptionalsStep interface
		 */
		OptionalsStep withConnectionTimeout(int duration, TimeUnit timeUnit);

		/**
		 * The duration to attempt to read a response from Shopify's API. <br>
		 * Default value is: 15 seconds.
		 *
		 * @param duration of the duration to be considered
		 * @param timeUnit the time unit to be used (miliseconds, seconds, etc...)
		 * @return {@link OptionalsStep} the OptionalsStep interface
		 */
		OptionalsStep withReadTimeout(int duration, TimeUnit timeUnit);

		/**
		 * String representation of the version you want to use. If not
		 * populated, this will use shopify oldest stable version. Although this
		 * is not recommended so you can target a set of shopify features. Ex:
		 * '2020-10' '2020-07' '2020-04'. If you are specifying the API URL
		 * ensure you leave off the version if you are using this.
		 *
		 * @param apiVersion current apiVersion value
		 * @return {@link OptionalsStep} the OptionalsStep interface
		 */
		OptionalsStep withApiVersion(final String apiVersion);

		ShopifySdk build();

	}

	public static interface AuthorizationTokenStep {
		OptionalsStep withAuthorizationToken(final String authorizationToken);

	}

	public static interface ClientSecretStep {
		AuthorizationTokenStep withClientSecret(final String clientSecret);

	}

	public static interface AccessTokenStep {
		OptionalsStep withAccessToken(final String accessToken);

		ClientSecretStep withClientId(final String clientId);
	}

	public static interface SubdomainStep {
		AccessTokenStep withSubdomain(final String subdomain);

		AccessTokenStep withApiUrl(final String apiUrl);
	}

	public static SubdomainStep newBuilder() {
		return new Steps();
	}

	protected ShopifySdk(final Steps steps) {
		if (steps != null) {
			this.shopSubdomain = steps.subdomain;
			this.accessToken = steps.accessToken;
			this.clientId = steps.clientId;
			this.clientSecret = steps.clientSecret;
			this.authorizationToken = steps.authorizationToken;
			this.apiUrl = steps.apiUrl;
			this.apiVersion = steps.apiVersion;
			this.minimumRequestRetryRandomDelayMilliseconds = steps.minimumRequestRetryRandomDelayMilliseconds;
			this.maximumRequestRetryRandomDelayMilliseconds = steps.maximumRequestRetryRandomDelayMilliseconds;
			this.maximumRequestRetryTimeoutMilliseconds = steps.maximumRequestRetryTimeoutMilliseconds;

			CLIENT.property(ClientProperties.CONNECT_TIMEOUT, Math.toIntExact(steps.connectionTimeoutMilliseconds));
			CLIENT.property(ClientProperties.READ_TIMEOUT, Math.toIntExact(steps.readTimeoutMilliseconds));
			validateConstructionOfShopifySdk();
		}

	}

	private void validateConstructionOfShopifySdk() {
		if (this.minimumRequestRetryRandomDelayMilliseconds < TWO_HUNDRED_MILLISECONDS) {
			throw new IllegalArgumentException(INVALID_MINIMUM_REQUEST_RETRY_DELAY_MESSAGE);
		}
		if (minimumRequestRetryRandomDelayMilliseconds > maximumRequestRetryRandomDelayMilliseconds) {
			throw new IllegalArgumentException(
					MINIMUM_REQUEST_RETRY_DELAY_CANNOT_BE_LARGER_THAN_MAXIMUM_REQUEST_RETRY_DELAY_MESSAGE);
		}
	}

	protected static class Steps
			implements SubdomainStep, ClientSecretStep, AuthorizationTokenStep, AccessTokenStep, OptionalsStep {

		private String subdomain;
		private String accessToken;
		private String clientId;
		private String clientSecret;
		private String authorizationToken;
		private String apiUrl;
		private String apiVersion;
		private long minimumRequestRetryRandomDelayMilliseconds = DEFAULT_MINIMUM_REQUEST_RETRY_RANDOM_DELAY_IN_MILLISECONDS;
		private long maximumRequestRetryRandomDelayMilliseconds = DEFAULT_MAXIMUM_REQUEST_RETRY_RANDOM_DELAY_IN_MILLISECONDS;
		private long maximumRequestRetryTimeoutMilliseconds = DEFAULT_MAXIMUM_REQUEST_RETRY_TIMEOUT_IN_MILLISECONDS;
		private long connectionTimeoutMilliseconds = DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS;
		private long readTimeoutMilliseconds = DEFAULT_READ_TIMEOUT_IN_MILLISECONDS;

		@Override
		public ShopifySdk build() {
			return new ShopifySdk(this);
		}

		@Override
		public OptionalsStep withAccessToken(final String accessToken) {
			this.accessToken = accessToken;
			return this;
		}

		@Override
		public AccessTokenStep withSubdomain(final String subdomain) {
			this.subdomain = subdomain;
			return this;
		}

		@Override
		public AccessTokenStep withApiUrl(final String apiUrl) {
			this.apiUrl = apiUrl;
			return this;
		}

		@Override
		public ClientSecretStep withClientId(final String clientId) {
			this.clientId = clientId;
			return this;
		}

		@Override
		public OptionalsStep withAuthorizationToken(final String authorizationToken) {
			this.authorizationToken = authorizationToken;
			return this;
		}

		@Override
		public AuthorizationTokenStep withClientSecret(final String clientSecret) {
			this.clientSecret = clientSecret;
			return this;
		}

		@Override
		public OptionalsStep withMinimumRequestRetryRandomDelay(final int duration, final TimeUnit timeUnit) {
			this.minimumRequestRetryRandomDelayMilliseconds = timeUnit.toMillis(duration);
			return this;
		}

		@Override
		public OptionalsStep withMaximumRequestRetryRandomDelay(final int duration, final TimeUnit timeUnit) {
			this.maximumRequestRetryRandomDelayMilliseconds = timeUnit.toMillis(duration);
			return this;
		}

		@Override
		public OptionalsStep withMaximumRequestRetryTimeout(final int duration, final TimeUnit timeUnit) {
			this.maximumRequestRetryTimeoutMilliseconds = timeUnit.toMillis(duration);
			return this;
		}

		@Override
		public OptionalsStep withConnectionTimeout(final int duration, final TimeUnit timeUnit) {
			this.connectionTimeoutMilliseconds = timeUnit.toMillis(duration);
			return this;
		}

		@Override
		public OptionalsStep withReadTimeout(final int duration, final TimeUnit timeUnit) {
			this.readTimeoutMilliseconds = timeUnit.toMillis(duration);
			return this;
		}

		@Override
		public OptionalsStep withApiVersion(final String apiVersion) {
			this.apiVersion = apiVersion;
			return this;
		}

	}

	public boolean revokeOAuthToken() {
		try {
			final Response response = delete(getUnversionedWebTarget().path(OAUTH).path(REVOKE));
			return Status.OK.getStatusCode() == response.getStatus();
		} catch (final ShopifyErrorResponseException e) {
			return false;
		}
	}

	public ShopifyProduct getProduct(final String productId) {
		final Response response = get(getWebTarget().path(PRODUCTS).path(productId));
		final ShopifyProductRoot shopifyProductRootResponse = response.readEntity(ShopifyProductRoot.class);
		return shopifyProductRootResponse.getProduct();
	}

	public ShopifyVariant getVariant(final String variantId) {
		final Response response = get(getWebTarget().path(VARIANTS).path(variantId));
		final ShopifyVariantRoot shopifyVariantRootResponse = response.readEntity(ShopifyVariantRoot.class);
		return shopifyVariantRootResponse.getVariant();
	}

	public ShopifyPage<ShopifyProduct> getProducts(final int pageSize) {
		return this.getProducts(null, pageSize);
	}

	public ShopifyPage<ShopifyProduct> getProducts(final String pageInfo, final int pageSize) {
		final Response response = get(getWebTarget().path(PRODUCTS).queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo));
		final ShopifyProductsRoot shopifyProductsRoot = response.readEntity(ShopifyProductsRoot.class);
		return mapPagedResponse(shopifyProductsRoot.getProducts(), response);
	}

	public ShopifyProducts getProducts() {
		final List<ShopifyProduct> shopifyProducts = new LinkedList<>();

		ShopifyPage<ShopifyProduct> shopifyProductsPage = getProducts(DEFAULT_REQUEST_LIMIT);
		LOGGER.info("Retrieved {} products from first page", shopifyProductsPage.size());
		shopifyProducts.addAll(shopifyProductsPage);
		while (shopifyProductsPage.getNextPageInfo() != null) {
			shopifyProductsPage = getProducts(shopifyProductsPage.getNextPageInfo(), DEFAULT_REQUEST_LIMIT);
			LOGGER.info("Retrieved {} products from page {}", shopifyProductsPage.size(),
					shopifyProductsPage.getNextPageInfo());
			shopifyProducts.addAll(shopifyProductsPage);
		}
		return new ShopifyProducts(shopifyProducts);
	}

	public ShopifyPage<ShopifyProduct> getCollectionProducts(final String collectionId, final int pageSize) {
		return this.getCollectionProducts(collectionId, null, pageSize);
	}

	public ShopifyPage<ShopifyProduct> getCollectionProducts(final String collectionId, final String pageInfo, final int pageSize) {
		final String productsJson = new StringBuilder().append(PRODUCTS).append(JSON).toString();
		final Response response = get(getWebTarget().path(COLLECTIONS).path(collectionId).path(productsJson)
			.queryParam(LIMIT_QUERY_PARAMETER, pageSize)
			.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo));
		final ShopifyProductsRoot shopifyProductsRoot = response.readEntity(ShopifyProductsRoot.class);
		return mapPagedResponse(shopifyProductsRoot.getProducts(), response);
	}

	public ShopifyProducts getCollectionProducts(final String collectionId) {
		final List<ShopifyProduct> shopifyProducts = new LinkedList<>();

		ShopifyPage<ShopifyProduct> shopifyProductsPage = getCollectionProducts(collectionId, DEFAULT_REQUEST_LIMIT);
		LOGGER.info("Retrieved {} products for collection {} from first page", shopifyProductsPage.size(), collectionId);
		shopifyProducts.addAll(shopifyProductsPage);
		while (shopifyProductsPage.getNextPageInfo() != null) {
			shopifyProductsPage = getCollectionProducts(collectionId, shopifyProductsPage.getNextPageInfo(), DEFAULT_REQUEST_LIMIT);
			LOGGER.info("Retrieved {} products for collection {} from page {}", shopifyProductsPage.size(),
					collectionId, shopifyProductsPage.getNextPageInfo());
			shopifyProducts.addAll(shopifyProductsPage);
		}
		return new ShopifyProducts(shopifyProducts);
	}

	public int getProductCount() {
		final Response response = get(getWebTarget().path(PRODUCTS).path(COUNT));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
	}

	public ShopifyCustomCollection getCustomCollection(final String collectionId) {
		final Response response = get(getWebTarget().path(CUSTOM_COLLECTIONS).path(collectionId));
		final ShopifyCustomCollectionRoot shopifyCsutomCollectionRootResponse = response.readEntity(ShopifyCustomCollectionRoot.class);
		return shopifyCsutomCollectionRootResponse.getCustomCollection();
	}

	public ShopifyPage<ShopifyCustomCollection> getCustomCollections(final int pageSize) {
		final Response response = get(
				getWebTarget().path(CUSTOM_COLLECTIONS).queryParam(LIMIT_QUERY_PARAMETER, pageSize));
		final ShopifyCustomCollectionsRoot shopifyCustomCollectionsRoot = response
				.readEntity(ShopifyCustomCollectionsRoot.class);
		return mapPagedResponse(shopifyCustomCollectionsRoot.getCustomCollections(), response);
	}

	public ShopifyPage<ShopifyCustomCollection> getCustomCollections(final String pageInfo, final int pageSize) {
		final Response response = get(getWebTarget().path(CUSTOM_COLLECTIONS)
				.queryParam(LIMIT_QUERY_PARAMETER, pageSize).queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo));
		final ShopifyCustomCollectionsRoot shopifyCustomCollectionsRoot = response
				.readEntity(ShopifyCustomCollectionsRoot.class);
		return mapPagedResponse(shopifyCustomCollectionsRoot.getCustomCollections(), response);
	}

	public List<ShopifyCustomCollection> getCustomCollections() {
		final List<ShopifyCustomCollection> shopifyCustomCollections = new LinkedList<>();

		ShopifyPage<ShopifyCustomCollection> customCollectionsPage = getCustomCollections(DEFAULT_REQUEST_LIMIT);
		LOGGER.info("Retrieved {} custom collections from first page", customCollectionsPage.size());
		shopifyCustomCollections.addAll(customCollectionsPage);

		while (customCollectionsPage.getNextPageInfo() != null) {
			customCollectionsPage = getCustomCollections(customCollectionsPage.getNextPageInfo(),
					DEFAULT_REQUEST_LIMIT);
			LOGGER.info("Retrieved {} custom collections from page info {}", customCollectionsPage.size(),
					customCollectionsPage.getNextPageInfo());
			shopifyCustomCollections.addAll(customCollectionsPage);
		}

		return shopifyCustomCollections;
	}

	public ShopifyCustomCollection createCustomCollection(
			final ShopifyCustomCollectionCreationRequest shopifyCustomCollectionCreationRequest) {
		final ShopifyCustomCollectionRoot shopifyCustomCollectionRootRequest = new ShopifyCustomCollectionRoot();
		final ShopifyCustomCollection shopifyCustomCollection = shopifyCustomCollectionCreationRequest.getRequest();
		shopifyCustomCollectionRootRequest.setCustomCollection(shopifyCustomCollection);
		final Response response = post(getWebTarget().path(CUSTOM_COLLECTIONS), shopifyCustomCollectionRootRequest);
		final ShopifyCustomCollectionRoot shopifyCustomCollectionRootResponse = response
				.readEntity(ShopifyCustomCollectionRoot.class);
		return shopifyCustomCollectionRootResponse.getCustomCollection();
	}

	public ShopifySmartCollection getSmartCollection(final String collectionId) {
		final Response response = get(getWebTarget().path(SMART_COLLECTIONS).path(collectionId));
		final ShopifySmartCollectionRoot shopifySmartCollectionRootResponse = response.readEntity(ShopifySmartCollectionRoot.class);
		return shopifySmartCollectionRootResponse.getSmartCollection();
	}

	public ShopifyPage<ShopifySmartCollection> getSmartCollections(final int pageSize) {
		final Response response = get(
			getWebTarget().path(SMART_COLLECTIONS).queryParam(LIMIT_QUERY_PARAMETER, pageSize));
		final ShopifySmartCollectionsRoot shopifySmartCollectionsRoot = response
			.readEntity(ShopifySmartCollectionsRoot.class);
		return mapPagedResponse(shopifySmartCollectionsRoot.getSmartCollections(), response);
	}

	public ShopifyPage<ShopifySmartCollection> getSmartCollections(final String pageInfo, final int pageSize) {
		final Response response = get(getWebTarget().path(SMART_COLLECTIONS)
			.queryParam(LIMIT_QUERY_PARAMETER, pageSize).queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo));
		final ShopifySmartCollectionsRoot shopifySmartCollectionsRoot = response
			.readEntity(ShopifySmartCollectionsRoot.class);
		return mapPagedResponse(shopifySmartCollectionsRoot.getSmartCollections(), response);
	}

	public List<ShopifySmartCollection> getSmartCollections() {
		final List<ShopifySmartCollection> shopifySmartCollections = new LinkedList<>();

		ShopifyPage<ShopifySmartCollection> smartCollectionsPage = getSmartCollections(DEFAULT_REQUEST_LIMIT);
		LOGGER.info("Retrieved {} custom collections from first page", smartCollectionsPage.size());
		shopifySmartCollections.addAll(smartCollectionsPage);

		while (smartCollectionsPage.getNextPageInfo() != null) {
			smartCollectionsPage = getSmartCollections(smartCollectionsPage.getNextPageInfo(),
				DEFAULT_REQUEST_LIMIT);
			LOGGER.info("Retrieved {} smart collections from page info {}", smartCollectionsPage.size(),
				smartCollectionsPage.getNextPageInfo());
			shopifySmartCollections.addAll(smartCollectionsPage);
		}

		return shopifySmartCollections;
	}

	public ShopifyShop getShop() {
		final Response response = get(getWebTarget().path(SHOP));
		return response.readEntity(ShopifyShop.class);
	}

	public ShopifyProduct createProduct(final ShopifyProductCreationRequest shopifyProductCreationRequest) {
		final ShopifyProductRoot shopifyProductRootRequest = new ShopifyProductRoot();
		final ShopifyProduct shopifyProduct = shopifyProductCreationRequest.getRequest();
		shopifyProductRootRequest.setProduct(shopifyProduct);
		final Response response = post(getWebTarget().path(PRODUCTS), shopifyProductRootRequest);
		final ShopifyProductRoot shopifyProductRootResponse = response.readEntity(ShopifyProductRoot.class);
		final ShopifyProduct createdShopifyProduct = shopifyProductRootResponse.getProduct();
		return updateProductImages(shopifyProductCreationRequest, createdShopifyProduct);
	}

	public ShopifyProduct updateProduct(final ShopifyProductUpdateRequest shopifyProductUpdateRequest) {
		final ShopifyProductRoot shopifyProductRootRequest = new ShopifyProductRoot();
		final ShopifyProduct shopifyProduct = shopifyProductUpdateRequest.getRequest();
		shopifyProductRootRequest.setProduct(shopifyProduct);
		final Response response = put(getWebTarget().path(PRODUCTS).path(shopifyProduct.getId()),
				shopifyProductRootRequest);
		final ShopifyProductRoot shopifyProductRootResponse = response.readEntity(ShopifyProductRoot.class);
		final ShopifyProduct updatedShopifyProduct = shopifyProductRootResponse.getProduct();
		return updateProductImages(shopifyProductUpdateRequest, updatedShopifyProduct);
	}

	public ShopifyVariant updateVariant(final ShopifyVariantUpdateRequest shopifyVariantUpdateRequest) {
		final ShopifyVariant shopifyVariant = shopifyVariantUpdateRequest.getRequest();
		final String shopifyVariantId = shopifyVariant.getId();
		if (StringUtils.isNotBlank(shopifyVariantUpdateRequest.getImageSource())) {
			final ShopifyImageRoot shopifyImageRootRequest = new ShopifyImageRoot();
			final Image imageRequest = new Image();
			imageRequest.setSource(shopifyVariantUpdateRequest.getImageSource());
			final List<Metafield> metafields = ImageAltTextCreationRequest.newBuilder()
					.withImageAltText(shopifyVariant.getTitle()).build();
			imageRequest.setMetafields(metafields);
			imageRequest.setVariantIds(Arrays.asList(shopifyVariantId));
			shopifyImageRootRequest.setImage(imageRequest);
			final String productId = shopifyVariant.getProductId();
			final Response response = post(getWebTarget().path(PRODUCTS).path(productId).path(IMAGES),
					shopifyImageRootRequest);
			final ShopifyImageRoot shopifyImageRootResponse = response.readEntity(ShopifyImageRoot.class);
			final Image createdImage = shopifyImageRootResponse.getImage();
			shopifyVariant.setImageId(createdImage.getId());
		}

		final ShopifyVariantRoot shopifyVariantRootRequest = new ShopifyVariantRoot();
		shopifyVariantRootRequest.setVariant(shopifyVariant);
		final Response response = put(getWebTarget().path(VARIANTS).path(shopifyVariantId), shopifyVariantRootRequest);
		final ShopifyVariantRoot shopifyVariantRootResponse = response.readEntity(ShopifyVariantRoot.class);
		return shopifyVariantRootResponse.getVariant();
	}

	public boolean deleteProduct(final String productId) {
		final Response response = delete(getWebTarget().path(PRODUCTS).path(productId));
		return Status.OK.getStatusCode() == response.getStatus();
	}

	public ShopifyRecurringApplicationCharge createRecurringApplicationCharge(
			final ShopifyRecurringApplicationChargeCreationRequest shopifyRecurringApplicationChargeCreationRequest) {
		final ShopifyRecurringApplicationChargeRoot shopifyRecurringApplicationChargeRootRequest = new ShopifyRecurringApplicationChargeRoot();
		final ShopifyRecurringApplicationCharge shopifyRecurringApplicationChargeRequest = shopifyRecurringApplicationChargeCreationRequest
				.getRequest();
		shopifyRecurringApplicationChargeRootRequest
				.setRecurringApplicationCharge(shopifyRecurringApplicationChargeRequest);

		final Response response = post(getWebTarget().path(RECURRING_APPLICATION_CHARGES),
				shopifyRecurringApplicationChargeRootRequest);
		final ShopifyRecurringApplicationChargeRoot shopifyRecurringApplicationChargeRootResponse = response
				.readEntity(ShopifyRecurringApplicationChargeRoot.class);
		return shopifyRecurringApplicationChargeRootResponse.getRecurringApplicationCharge();
	}

	public ShopifyRecurringApplicationCharge getRecurringApplicationCharge(final String chargeId) {
		final Response response = get(getWebTarget().path(RECURRING_APPLICATION_CHARGES).path(chargeId));
		final ShopifyRecurringApplicationChargeRoot shopifyRecurringApplicationChargeRootResponse = response
				.readEntity(ShopifyRecurringApplicationChargeRoot.class);
		return shopifyRecurringApplicationChargeRootResponse.getRecurringApplicationCharge();
	}

	public ShopifyRecurringApplicationCharge activateRecurringApplicationCharge(final String chargeId) {
		final ShopifyRecurringApplicationCharge shopifyRecurringApplicationChargeRequest = getRecurringApplicationCharge(
				chargeId);
		final Response response = post(getWebTarget().path(RECURRING_APPLICATION_CHARGES).path(chargeId).path(ACTIVATE),
				shopifyRecurringApplicationChargeRequest);
		final ShopifyRecurringApplicationChargeRoot shopifyRecurringApplicationChargeRootResponse = response
				.readEntity(ShopifyRecurringApplicationChargeRoot.class);
		return shopifyRecurringApplicationChargeRootResponse.getRecurringApplicationCharge();
	}

	public ShopifyOrder getOrder(final String orderId) {
		final Response response = get(buildOrdersEndpoint().path(orderId));
		final ShopifyOrderRoot shopifyOrderRootResponse = response.readEntity(ShopifyOrderRoot.class);
		return shopifyOrderRootResponse.getOrder();
	}

	public List<ShopifyTransaction> getOrderTransactions(final String orderId) {
		final Response response = get(buildOrdersEndpoint().path(orderId).path(TRANSACTIONS));

		final ShopifyTransactionsRoot shopifyTransactionsRootResponse = response
				.readEntity(ShopifyTransactionsRoot.class);
		return shopifyTransactionsRootResponse.getTransactions();
	}

	public ShopifyPage<ShopifyOrder> getOrders() {
		return getOrders(DEFAULT_REQUEST_LIMIT);
	}

	public ShopifyPage<ShopifyOrder> getOrders(final int pageSize) {
		final Response response = get(buildOrdersEndpoint().queryParam(STATUS_QUERY_PARAMETER, ANY_STATUSES)
				.queryParam(LIMIT_QUERY_PARAMETER, pageSize));
		return getOrders(response);
	}

	public ShopifyPage<ShopifyOrder> getOrders(final DateTime mininumCreationDate) {
		return getOrders(mininumCreationDate, DEFAULT_REQUEST_LIMIT);
	}

	public ShopifyPage<ShopifyOrder> getOrders(final DateTime mininumCreationDate, final int pageSize) {
		final Response response = get(buildOrdersEndpoint().queryParam(STATUS_QUERY_PARAMETER, ANY_STATUSES)
				.queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(CREATED_AT_MIN_QUERY_PARAMETER, mininumCreationDate.toString()));
		return getOrders(response);
	}

	public ShopifyPage<ShopifyOrder> getOrders(final DateTime mininumCreationDate, final DateTime maximumCreationDate) {
		return getOrders(mininumCreationDate, maximumCreationDate, DEFAULT_REQUEST_LIMIT);
	}

	public ShopifyPage<ShopifyOrder> getUpdatedOrdersCreatedBefore(final DateTime minimumUpdatedAtDate,
			final DateTime maximumUpdatedAtDate, final DateTime maximumCreatedAtDate, final int pageSize) {
		final Response response = get(buildOrdersEndpoint().queryParam(STATUS_QUERY_PARAMETER, ANY_STATUSES)
				.queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
				.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString())
				.queryParam(CREATED_AT_MAX_QUERY_PARAMETER, maximumCreatedAtDate.toString()));
		return getOrders(response);
	}

	public ShopifyPage<ShopifyOrder> getOrders(final DateTime mininumCreationDate, final DateTime maximumCreationDate,
			final int pageSize) {
		final Response response = get(buildOrdersEndpoint().queryParam(STATUS_QUERY_PARAMETER, ANY_STATUSES)
				.queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(CREATED_AT_MIN_QUERY_PARAMETER, mininumCreationDate.toString())
				.queryParam(CREATED_AT_MAX_QUERY_PARAMETER, maximumCreationDate.toString()));
		return getOrders(response);
	}

	public ShopifyPage<ShopifyOrder> getOrders(final DateTime mininumCreationDate, final DateTime maximumCreationDate,
			final String appId) {
		return getOrders(mininumCreationDate, maximumCreationDate, appId, DEFAULT_REQUEST_LIMIT);
	}

	public ShopifyPage<ShopifyOrder> getOrders(final DateTime mininumCreationDate, final DateTime maximumCreationDate,
			final String appId, final int pageSize) {
		final Response response = get(buildOrdersEndpoint().queryParam(STATUS_QUERY_PARAMETER, ANY_STATUSES)
				.queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(CREATED_AT_MIN_QUERY_PARAMETER, mininumCreationDate.toString())
				.queryParam(CREATED_AT_MAX_QUERY_PARAMETER, maximumCreationDate.toString())
				.queryParam(ATTRIBUTION_APP_ID_QUERY_PARAMETER, appId));
		return getOrders(response);
	}

	public ShopifyPage<ShopifyOrder> getOrders(final String pageInfo, final int pageSize) {
		final Response response = get(buildOrdersEndpoint().queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo));
		return getOrders(response);
	}

	/**
	 * Creates a fulfillment in shopify
	 * 
	 * @deprecated starting in March 01, 2023 this method will be obsolete
	 * @param shopifyFulfillmentCreationRequest the fulfillment creaton request
	 * @return the newly created fulfillment
	 */
	@Deprecated
	public ShopifyFulfillment createFulfillment(
			final ShopifyFulfillmentCreationRequest shopifyFulfillmentCreationRequest) {
		return this.createFulfillmentWithLegacyApi(shopifyFulfillmentCreationRequest);
	}

	/**
	 * Creates a fulfillment in shopify starting with api 2023-04
	 * 
	 * @param shopifyFulfillmentCreationRequest the fulfillment creaton request
	 * 
	 * @param fulfillmentOrders                 the fulfillment orders list to
	 *                                          create the new fulfillment
	 * @return the newly created fulfillment
	 * @throws ShopifyEmptyLineItemsException in case we have a fulfillment
	 *                                        associated with a fulfillmentOrder
	 *                                        without supported action
	 */
	public ShopifyFulfillment createFulfillment(
			final ShopifyFulfillmentCreationRequest shopifyFulfillmentCreationRequest,
			List<ShopifyFulfillmentOrder> fulfillmentOrders) throws ShopifyEmptyLineItemsException {
		return this.createFulfillmentWithFulfillmentOrderApi(shopifyFulfillmentCreationRequest, fulfillmentOrders);
	}

	/**
	 * Updates a fulfillment in shopify
	 * 
	 * @deprecated starting in March 01, 2023 this method will be obsolete
	 * @param shopifyFulfillmentUpdateRequest the fulfillment update request
	 * @return the newly updated fulfillment
	 */
	@Deprecated
	public ShopifyFulfillment updateFulfillment(final ShopifyFulfillmentUpdateRequest shopifyFulfillmentUpdateRequest) {
		final ShopifyFulfillmentRoot shopifyFulfillmentRoot = new ShopifyFulfillmentRoot();
		final ShopifyFulfillment shopifyFulfillment = shopifyFulfillmentUpdateRequest.getRequest();
		shopifyFulfillmentRoot.setFulfillment(shopifyFulfillment);
		final Response response = put(buildOrdersEndpoint().path(shopifyFulfillment.getOrderId()).path(FULFILLMENTS)
				.path(shopifyFulfillment.getId()), shopifyFulfillmentRoot);
		final ShopifyFulfillmentRoot shopifyFulfillmentRootResponse = response.readEntity(ShopifyFulfillmentRoot.class);
		return shopifyFulfillmentRootResponse.getFulfillment();
	}

	/**
	 * Based on the api starting from 2023-04 it only updates tracking information
	 * 
	 * @param shopifyFulfillmentUpdateRequest the information needed to update the
	 *                                        fulfillment's tracking info
	 * @return the updated version of the shopify fulfillment
	 */
	public ShopifyFulfillment updateFulfillmentTrackingInfo(
			final ShopifyFulfillmentUpdateRequest shopifyFulfillmentUpdateRequest) {
		final ShopifyFulfillment shopifyFulfillment = shopifyFulfillmentUpdateRequest.getRequest();
		final ShopifyUpdateFulfillmentPayloadRoot payload = LegacyToFulfillmentOrderMapping
				.toUpdateShopifyFulfillmentPayloadRoot(shopifyFulfillment);

		final Response response = post(
				getWebTarget().path(FULFILLMENTS).path(shopifyFulfillment.getId()).path(UPDATE_TRACKING), payload);
		final ShopifyFulfillmentRoot shopifyFulfillmentRootResponse = response.readEntity(ShopifyFulfillmentRoot.class);
		return shopifyFulfillmentRootResponse.getFulfillment();
	}

	public ShopifyOrder createOrder(final ShopifyOrderCreationRequest shopifyOrderCreationRequest) {
		final ShopifyOrderRoot shopifyOrderRoot = new ShopifyOrderRoot();
		final ShopifyOrder shopifyOrder = shopifyOrderCreationRequest.getRequest();
		shopifyOrderRoot.setOrder(shopifyOrder);
		final Response response = post(buildOrdersEndpoint(), shopifyOrderRoot);
		final ShopifyOrderRoot shopifyOrderRootResponse = response.readEntity(ShopifyOrderRoot.class);
		return shopifyOrderRootResponse.getOrder();
	}

	public ShopifyOrder updateOrderShippingAddress(
			final ShopifyOrderShippingAddressUpdateRequest shopifyOrderUpdateRequest) {
		final ShopifyOrderUpdateRoot shopifyOrderRoot = new ShopifyOrderUpdateRoot();
		shopifyOrderRoot.setOrder(shopifyOrderUpdateRequest);
		final Response response = put(buildOrdersEndpoint().path(shopifyOrderUpdateRequest.getId()), shopifyOrderRoot);
		final ShopifyOrderRoot shopifyOrderRootResponse = response.readEntity(ShopifyOrderRoot.class);
		return shopifyOrderRootResponse.getOrder();
	}

	public ShopifyCustomer updateCustomer(final ShopifyCustomerUpdateRequest shopifyCustomerUpdateRequest) {
		final ShopifyCustomerUpdateRoot shopifyCustomerUpdateRequestRoot = new ShopifyCustomerUpdateRoot();
		shopifyCustomerUpdateRequestRoot.setCustomer(shopifyCustomerUpdateRequest);
		final Response response = put(getWebTarget().path(CUSTOMERS).path(shopifyCustomerUpdateRequest.getId()),
				shopifyCustomerUpdateRequestRoot);
		final ShopifyCustomerRoot shopifyCustomerRootResponse = response.readEntity(ShopifyCustomerRoot.class);
		return shopifyCustomerRootResponse.getCustomer();
	}

	public ShopifyCustomer getCustomer(final String customerId) {
		final Response response = get(getWebTarget().path(CUSTOMERS).path(customerId));
		final ShopifyCustomerRoot shopifyCustomerRootResponse = response.readEntity(ShopifyCustomerRoot.class);
		return shopifyCustomerRootResponse.getCustomer();
	}

	public ShopifyPage<ShopifyCustomer> getCustomers(final ShopifyGetCustomersRequest shopifyGetCustomersRequest) {
		WebTarget target = getWebTarget().path(CUSTOMERS);
		if (shopifyGetCustomersRequest.getPageInfo() != null) {
			target = target.queryParam(PAGE_INFO_QUERY_PARAMETER, shopifyGetCustomersRequest.getPageInfo());
		}
		if (shopifyGetCustomersRequest.getLimit() != 0) {
			target = target.queryParam(LIMIT_QUERY_PARAMETER, shopifyGetCustomersRequest.getLimit());
		} else {
			target = target.queryParam(LIMIT_QUERY_PARAMETER, DEFAULT_REQUEST_LIMIT);
		}
		if (shopifyGetCustomersRequest.getIds() != null) {
			target = target.queryParam(IDS_QUERY_PARAMETER, String.join(",", shopifyGetCustomersRequest.getIds()));
		}
		if (shopifyGetCustomersRequest.getSinceId() != null) {
			target = target.queryParam(SINCE_ID_QUERY_PARAMETER, shopifyGetCustomersRequest.getSinceId());
		}
		if (shopifyGetCustomersRequest.getCreatedAtMin() != null) {
			target = target.queryParam(CREATED_AT_MIN_QUERY_PARAMETER, shopifyGetCustomersRequest.getCreatedAtMin());
		}
		if (shopifyGetCustomersRequest.getCreatedAtMax() != null) {
			target = target.queryParam(CREATED_AT_MAX_QUERY_PARAMETER, shopifyGetCustomersRequest.getCreatedAtMax());
		}
		final Response response = get(target);
		return getCustomers(response);
	}

	public ShopifyPage<ShopifyCustomer> searchCustomers(final String query) {
		final Response response = get(getWebTarget().path(CUSTOMERS).path(SEARCH)
				.queryParam(QUERY_QUERY_PARAMETER, query).queryParam(LIMIT_QUERY_PARAMETER, DEFAULT_REQUEST_LIMIT));
		return getCustomers(response);
	}

	public ShopifyFulfillment cancelFulfillment(final String orderId, final String fulfillmentId) {
		final WebTarget buildOrdersEndpoint = buildOrdersEndpoint();
		final Response response = post(
				buildOrdersEndpoint.path(orderId).path(FULFILLMENTS).path(fulfillmentId).path(CANCEL),
				new ShopifyFulfillment());
		final ShopifyFulfillmentRoot shopifyFulfillmentRootResponse = response.readEntity(ShopifyFulfillmentRoot.class);
		return shopifyFulfillmentRootResponse.getFulfillment();
	}

	public ShopifyOrder closeOrder(final String orderId) {
		final Response response = post(buildOrdersEndpoint().path(orderId).path(CLOSE), new ShopifyOrder());
		final ShopifyOrderRoot shopifyOrderRootResponse = response.readEntity(ShopifyOrderRoot.class);
		return shopifyOrderRootResponse.getOrder();
	}

	public ShopifyOrder cancelOrder(final String orderId, final String reason) {
		final ShopifyCancelOrderRequest shopifyCancelOrderRequest = new ShopifyCancelOrderRequest();
		shopifyCancelOrderRequest.setReason(reason);
		final Response response = post(buildOrdersEndpoint().path(orderId).path(CANCEL), shopifyCancelOrderRequest);
		final ShopifyOrderRoot shopifyOrderRootResponse = response.readEntity(ShopifyOrderRoot.class);
		return shopifyOrderRootResponse.getOrder();
	}

	public Metafield createVariantMetafield(
			final ShopifyVariantMetafieldCreationRequest shopifyVariantMetafieldCreationRequest) {
		final MetafieldRoot metafieldRoot = new MetafieldRoot();
		metafieldRoot.setMetafield(shopifyVariantMetafieldCreationRequest.getRequest());
		final Response response = post(getWebTarget().path(VARIANTS)
				.path(shopifyVariantMetafieldCreationRequest.getVariantId()).path(METAFIELDS), metafieldRoot);
		final MetafieldRoot metafieldRootResponse = response.readEntity(MetafieldRoot.class);
		return metafieldRootResponse.getMetafield();
	}

	public List<Metafield> getVariantMetafields(final String variantId) {
		final Response response = get(getWebTarget().path(VARIANTS).path(variantId).path(METAFIELDS));
		final MetafieldsRoot metafieldsRootResponse = response.readEntity(MetafieldsRoot.class);
		return metafieldsRootResponse.getMetafields();
	}

	public Metafield createProductMetafield(
			final ShopifyProductMetafieldCreationRequest shopifyProductMetafieldCreationRequest) {
		final MetafieldRoot metafieldRoot = new MetafieldRoot();
		metafieldRoot.setMetafield(shopifyProductMetafieldCreationRequest.getRequest());
		final Response response = post(getWebTarget().path(PRODUCTS)
				.path(shopifyProductMetafieldCreationRequest.getProductId()).path(METAFIELDS), metafieldRoot);
		final MetafieldRoot metafieldRootResponse = response.readEntity(MetafieldRoot.class);
		return metafieldRootResponse.getMetafield();
	}

	public List<Metafield> getProductMetafields(final String productId) {
		final Response response = get(getWebTarget().path(PRODUCTS).path(productId).path(METAFIELDS));
		final MetafieldsRoot metafieldsRootResponse = response.readEntity(MetafieldsRoot.class);
		return metafieldsRootResponse.getMetafields();
	}

	public List<ShopifyOrderRisk> getOrderRisks(final String orderId) {
		final Response response = get(buildOrdersEndpoint().path(orderId).path(RISKS));
		final ShopifyOrderRisksRoot shopifyOrderRisksRootResponse = response.readEntity(ShopifyOrderRisksRoot.class);
		return shopifyOrderRisksRootResponse.getRisks();
	}

	public List<ShopifyLocation> getLocations() {
		final String locationsEndpoint = new StringBuilder().append(LOCATIONS).append(JSON).toString();
		final Response response = get(getWebTarget().path(locationsEndpoint));
		final ShopifyLocationsRoot shopifyLocationRootResponse = response.readEntity(ShopifyLocationsRoot.class);
		return shopifyLocationRootResponse.getLocations();
	}

	public ShopifyInventoryLevel updateInventoryLevel(final String inventoryItemId, final String locationId,
			final long quantity) {
		final ShopifyInventoryLevel shopifyInventoryLevel = new ShopifyInventoryLevel();
		shopifyInventoryLevel.setAvailable(quantity);
		shopifyInventoryLevel.setLocationId(locationId);
		shopifyInventoryLevel.setInventoryItemId(inventoryItemId);
		final Response response = post(getWebTarget().path(INVENTORY_LEVELS).path(SET), shopifyInventoryLevel);
		final ShopifyInventoryLevelRoot shopifyInventoryLevelRootResponse = response
				.readEntity(ShopifyInventoryLevelRoot.class);
		return shopifyInventoryLevelRootResponse.getInventoryLevel();
	}

	public List<Metafield> getOrderMetafields(final String orderId) {
		final Response response = get(buildOrdersEndpoint().path(orderId).path(METAFIELDS));
		final MetafieldsRoot metafieldsRootResponse = response.readEntity(MetafieldsRoot.class);
		return metafieldsRootResponse.getMetafields();
	}

	public ShopifyRefund refund(final ShopifyRefundCreationRequest shopifyRefundCreationRequest) {

		final ShopifyRefund calculatedShopifyRefund = calculateRefund(shopifyRefundCreationRequest);
		calculatedShopifyRefund.getTransactions().forEach(transaction -> transaction.setKind(REFUND_KIND));

		final WebTarget path = buildOrdersEndpoint().path(shopifyRefundCreationRequest.getRequest().getOrderId())
				.path(REFUNDS);
		final ShopifyRefundRoot shopifyRefundRoot = new ShopifyRefundRoot();
		shopifyRefundRoot.setRefund(calculatedShopifyRefund);
		final Response response = post(path, shopifyRefundRoot);
		final ShopifyRefundRoot shopifyRefundRootResponse = response.readEntity(ShopifyRefundRoot.class);
		return shopifyRefundRootResponse.getRefund();

	}

	public ShopifyGiftCard createGiftCard(final ShopifyGiftCardCreationRequest shopifyGiftCardCreationRequest) {
		final ShopifyGiftCardRoot shopifyGiftCardRoot = new ShopifyGiftCardRoot();
		final ShopifyGiftCard shopifyGiftCard = shopifyGiftCardCreationRequest.getRequest();
		shopifyGiftCardRoot.setGiftCard(shopifyGiftCard);
		final Response response = post(getWebTarget().path(GIFT_CARDS), shopifyGiftCardRoot);
		final ShopifyGiftCardRoot shopifyOrderRootResponse = response.readEntity(ShopifyGiftCardRoot.class);
		return shopifyOrderRootResponse.getGiftCard();
	}

	public String getAccessToken() {
		return accessToken;
	}

	private ShopifyPage<ShopifyCustomer> getCustomers(final Response response) {
		final ShopifyCustomersRoot shopifyCustomersRootResponse = response.readEntity(ShopifyCustomersRoot.class);
		return mapPagedResponse(shopifyCustomersRootResponse.getCustomers(), response);
	}

	private ShopifyRefund calculateRefund(final ShopifyRefundCreationRequest shopifyRefundCreationRequest) {
		final ShopifyRefundRoot shopifyRefundRoot = new ShopifyRefundRoot();

		shopifyRefundRoot.setRefund(shopifyRefundCreationRequest.getRequest());

		final WebTarget path = buildOrdersEndpoint().path(shopifyRefundCreationRequest.getRequest().getOrderId())
				.path(REFUNDS).path(CALCULATE);
		final Response response = post(path, shopifyRefundRoot);
		final ShopifyRefundRoot shopifyRefundRootResponse = response.readEntity(ShopifyRefundRoot.class);
		return shopifyRefundRootResponse.getRefund();

	}

	private ShopifyProduct updateProductImages(final ShopifyProductRequest shopifyProductRequest,
			final ShopifyProduct shopifyProduct) {
		setVariantImageIds(shopifyProductRequest, shopifyProduct);
		final ShopifyProductRoot shopifyProductRootRequest = new ShopifyProductRoot();
		shopifyProductRootRequest.setProduct(shopifyProduct);
		final Response response = put(getWebTarget().path(PRODUCTS).path(shopifyProduct.getId()),
				shopifyProductRootRequest);
		final ShopifyProductRoot shopifyProductRootResponse = response.readEntity(ShopifyProductRoot.class);
		return shopifyProductRootResponse.getProduct();
	}

	private void setVariantImageIds(final ShopifyProductRequest shopifyProductRequest,
			final ShopifyProduct shopifyProduct) {
		shopifyProduct.getVariants().stream().forEach(variant -> {
			final int variantPosition = variant.getPosition();
			if (shopifyProductRequest.hasVariantImagePosition(variantPosition)) {
				final int imagePosition = shopifyProductRequest.getVariantImagePosition(variantPosition);
				shopifyProduct.getImages().stream().filter(image -> image.getPosition() == imagePosition).findFirst()
						.ifPresent(variantImage -> variant.setImageId(variantImage.getId()));
			}
		});
	}

	private ShopifyPage<ShopifyOrder> getOrders(final Response response) {
		final ShopifyOrdersRoot shopifyOrderRootResponse = response.readEntity(ShopifyOrdersRoot.class);
		return mapPagedResponse(shopifyOrderRootResponse.getOrders(), response);
	}

	private Response get(final WebTarget webTarget) {
		final Callable<Response> responseCallable = () -> webTarget.request(MediaType.APPLICATION_JSON)
				.header(ACCESS_TOKEN_HEADER, accessToken).get();
		final Response response = invokeResponseCallable(responseCallable);
		return handleResponse(response, Status.OK);
	}

	private Response delete(final WebTarget webTarget) {
		final Callable<Response> responseCallable = () -> webTarget.request(MediaType.APPLICATION_JSON)
				.header(ACCESS_TOKEN_HEADER, accessToken).delete();
		final Response response = invokeResponseCallable(responseCallable);
		return handleResponse(response, Status.OK);
	}

	private <T> Response post(final WebTarget webTarget, final T object) {
		final Callable<Response> responseCallable = () -> {
			final Entity<T> entity = Entity.entity(object, MediaType.APPLICATION_JSON);
			return webTarget.request(MediaType.APPLICATION_JSON).header(ACCESS_TOKEN_HEADER, accessToken).post(entity);
		};
		final Response response = invokeResponseCallable(responseCallable);
		return handleResponse(response, Status.CREATED, Status.OK);
	}

	private <T> Response put(final WebTarget webTarget, final T object) {
		final Callable<Response> responseCallable = () -> {
			final Entity<T> entity = Entity.entity(object, MediaType.APPLICATION_JSON);
			return webTarget.request(MediaType.APPLICATION_JSON).header(ACCESS_TOKEN_HEADER, accessToken).put(entity);
		};
		final Response response = invokeResponseCallable(responseCallable);
		return handleResponse(response, Status.OK);
	}

	private Response handleResponse(final Response response, final Status... expectedStatus) {

		if ((response.getHeaders() != null) && response.getHeaders().containsKey(DEPRECATED_REASON_HEADER)) {
			LOGGER.error(DEPRECATED_SHOPIFY_CALL_ERROR_MESSAGE, response.getLocation(), response.getStatus(),
					response.getStringHeaders());
		}

		final List<Integer> expectedStatusCodes = getExpectedStatusCodes(expectedStatus);
		if (expectedStatusCodes.contains(response.getStatus())) {
			return response;
		}

		throw new ShopifyErrorResponseException(response);
	}

	private List<Integer> getExpectedStatusCodes(final Status... expectedStatus) {
		return Arrays.asList(expectedStatus).stream().map(Status::getStatusCode).collect(Collectors.toList());
	}

	private Response invokeResponseCallable(final Callable<Response> responseCallable) {
		final Retryer<Response> retryer = buildResponseRetyer();
		try {
			return retryer.call(responseCallable);
		} catch (ExecutionException | RetryException e) {
			throw new ShopifyClientException(RETRY_FAILED_MESSAGE, e);
		}
	}

	private Retryer<Response> buildResponseRetyer() {
		return RetryerBuilder.<Response> newBuilder().retryIfResult(ShopifySdk::shouldRetryResponse).retryIfException()
				.withWaitStrategy(WaitStrategies.randomWait(minimumRequestRetryRandomDelayMilliseconds,
						TimeUnit.MILLISECONDS, maximumRequestRetryRandomDelayMilliseconds, TimeUnit.MILLISECONDS))
				.withStopStrategy(
						StopStrategies.stopAfterDelay(maximumRequestRetryTimeoutMilliseconds, TimeUnit.MILLISECONDS))
				.withRetryListener(new ShopifySdkRetryListener()).build();
	}

	private static boolean shouldRetryResponse(final Response response) {
		return isServerError(response) || hasExceededRateLimit(response) || hasNotBeenSaved(response);
	}

	private static boolean hasExceededRateLimit(final Response response) {
		return TOO_MANY_REQUESTS_STATUS_CODE == response.getStatus()
				&& response.getHeaders().containsKey(RETRY_AFTER_HEADER);
	}

	private static boolean isServerError(final Response response) {
		return (Status.Family.SERVER_ERROR == Status.Family.familyOf(response.getStatus()))
				|| (LOCKED_STATUS_CODE == response.getStatus());
	}

	private static boolean hasNotBeenSaved(final Response response) {
		if ((UNPROCESSABLE_ENTITY_STATUS_CODE == response.getStatus()) && response.hasEntity()) {
			final String shopifyErrorResponse = ResponseEntityToStringMapper.map(response);
			LOGGER.debug(shopifyErrorResponse);
			return shopifyErrorResponse.contains(COULD_NOT_BE_SAVED_SHOPIFY_ERROR_MESSAGE);
		}
		return false;
	}

	public String generateToken() {
		try {

			final Entity<String> entity = Entity.entity(EMPTY_STRING, MediaType.APPLICATION_JSON);
			final Response response = this.getUnversionedWebTarget().path(OAUTH).path(ACCESS_TOKEN)
					.queryParam(CLIENT_ID, this.clientId).queryParam(CLIENT_SECRET, this.clientSecret)
					.queryParam(AUTHORIZATION_CODE, this.authorizationToken).request(MediaType.APPLICATION_JSON)
					.post(entity);

			final int status = response.getStatus();

			if (Status.OK.getStatusCode() == status) {
				final ShopifyAccessTokenRoot shopifyResponse = response.readEntity(ShopifyAccessTokenRoot.class);
				return shopifyResponse.getAccessToken();
			}
			throw new ShopifyErrorResponseException(response);
		} catch (final Exception e) {
			throw new ShopifyClientException(
					String.format(GENERAL_ACCESS_TOKEN_EXCEPTION_MESSAGE, shopSubdomain, this.authorizationToken), e);
		}
	}

	private WebTarget getUnversionedWebTarget() {
		if (StringUtils.isNotBlank(this.shopSubdomain)) {
			return CLIENT
					.target(new StringBuilder().append(HTTPS).append(this.shopSubdomain).append(API_TARGET).toString());
		}
		return CLIENT.target(this.apiUrl);
	}

	private WebTarget getWebTarget() {
		if (this.webTarget == null) {

			if (StringUtils.isNotBlank(this.shopSubdomain)) {
				this.webTarget = CLIENT.target(
						new StringBuilder().append(HTTPS).append(this.shopSubdomain).append(API_TARGET).toString());

			} else {
				this.webTarget = CLIENT.target(this.apiUrl);
			}
			if (StringUtils.isNotBlank(this.apiVersion)) {
				this.webTarget = this.webTarget.path(API_VERSION_PREFIX).path(this.apiVersion);
			}
			if (this.accessToken == null) {
				this.accessToken = generateToken();
			}

			final Shop shop = this.getShop().getShop();
			LOGGER.info(SHOP_RETRIEVED_MESSAGE, shop.getId(), shop.getName());
		}
		return webTarget;
	}

	private static Client buildClient() {
		final ObjectMapper mapper = ShopifySdkObjectMapper.buildMapper();
		final JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(mapper);

		return ClientBuilder.newClient().register(JacksonFeature.class).register(provider);
	}

	public class ShopifySdkRetryListener implements RetryListener {

		private static final String RETRY_EXCEPTION_ATTEMPT_MESSAGE = "An exception occurred while making an API call to shopify: {} on attempt number {} and {} seconds since first attempt";
		private static final String RETRY_INVALID_RESPONSE_ATTEMPT_MESSAGE = "Waited {} seconds since first retry attempt. This is attempt {}. Please review the following failed request information.\nRequest Location of {}\nResponse Status Code of {}\nResponse Headers of:\n{}\nResponse Body of:\n{}";

		@Override
		public <V> void onRetry(final Attempt<V> attempt) {
			if (attempt.hasResult()) {
				final Response response = (Response) attempt.getResult();

				final String responseBody = ResponseEntityToStringMapper.map(response);

				if (LOGGER.isWarnEnabled() && !hasExceededRateLimit(response) && shouldRetryResponse(response)) {

					final long delaySinceFirstAttemptInSeconds = convertMillisecondsToSeconds(
							attempt.getDelaySinceFirstAttempt());
					LOGGER.warn(RETRY_INVALID_RESPONSE_ATTEMPT_MESSAGE, delaySinceFirstAttemptInSeconds,
							attempt.getAttemptNumber(), response.getLocation(), response.getStatus(),
							response.getStringHeaders(), responseBody);

				}

			} else if (LOGGER.isWarnEnabled() && attempt.hasException()) {

				final long delaySinceFirstAttemptInSeconds = convertMillisecondsToSeconds(
						attempt.getDelaySinceFirstAttempt());
				LOGGER.warn(RETRY_EXCEPTION_ATTEMPT_MESSAGE, attempt.getAttemptNumber(),
						delaySinceFirstAttemptInSeconds, attempt.getExceptionCause());
			}
		}

		private long convertMillisecondsToSeconds(final long milliiseconds) {
			return TimeUnit.SECONDS.convert(milliiseconds, TimeUnit.MILLISECONDS);
		}

	}

	private <T> ShopifyPage<T> mapPagedResponse(final List<T> items, final Response response) {

		final ShopifyPage<T> shopifyPage = new ShopifyPage<>();
		shopifyPage.addAll(items);

		final Set<Link> links = response.getLinks();
		final String nextLink = links.stream().filter(link -> link.getRel().equals(REL_NEXT_HEADER_KEY))
				.map(link -> getQueryParam(link.getUri(), PAGE_INFO_QUERY_PARAMETER)).findFirst().orElse(null);
		final String previousLink = links.stream().filter(link -> link.getRel().equals(REL_PREVIOUS_HEADER_KEY))
				.map(link -> getQueryParam(link.getUri(), PAGE_INFO_QUERY_PARAMETER)).findFirst().orElse(null);
		shopifyPage.setNextPageInfo(nextLink);
		shopifyPage.setPreviousPageInfo(previousLink);

		return shopifyPage;
	}

	private String getQueryParam(final URI uri, final String key) {
		final String[] params = uri.getQuery().split(AMPERSAND);
		for (final String param : params) {
			final String name = param.split(EQUALS)[0];
			final String value = param.split(EQUALS)[1];
			if (name.equals(key)) {
				return value;
			}
		}
		return null;
	}

	private WebTarget buildOrdersEndpoint() {
		return getWebTarget().path(ORDERS);
	}

	public List<ShopifyFulfillmentOrder> getFulfillmentOrdersFromOrder(final String shopifyOrderId) {
		final List<ShopifyFulfillmentOrder> fulfillmentOrders = new LinkedList<>();
		final Response response = get(buildOrdersEndpoint().path(shopifyOrderId).path(FULFILLMENT_ORDERS));
		final ShopifyFulfillmentOrdersRoot shopifyFulfillmentOrdersRoot = response
				.readEntity(ShopifyFulfillmentOrdersRoot.class);

		fulfillmentOrders.addAll(shopifyFulfillmentOrdersRoot.getFulfillmentOrders());

		return fulfillmentOrders;
	}

	public ShopifyFulfillmentOrder moveFulfillmentOrder(final String newLocationId,
			final ShopifyFulfillmentOrder shopifyFulfillmentOrder) throws ShopifyIncompatibleApiException {
		final ShopifyFulfillmentOrderMoveRequestRoot payload = LegacyToFulfillmentOrderMapping
				.toShopifyMoveFulfillmentOrder(newLocationId, shopifyFulfillmentOrder);

		final Response response = post(
				getWebTarget().path(FULFILLMENT_ORDERS).path(shopifyFulfillmentOrder.getId()).path(MOVE), payload);
		final ShopifyFulfillmentOrderMoveResponseRoot shopifyFulfillmentRootResponse = response
				.readEntity(ShopifyFulfillmentOrderMoveResponseRoot.class);
		return shopifyFulfillmentRootResponse.getOriginalFulfillmentOrder();
	}

	private ShopifyFulfillment createFulfillmentWithLegacyApi(
			final ShopifyFulfillmentCreationRequest shopifyFulfillmentCreationRequest) {
		final ShopifyFulfillmentRoot shopifyFulfillmentRoot = new ShopifyFulfillmentRoot();
		final ShopifyFulfillment shopifyFulfillment = shopifyFulfillmentCreationRequest.getRequest();
		shopifyFulfillmentRoot.setFulfillment(shopifyFulfillment);
		final Response response = post(buildOrdersEndpoint().path(shopifyFulfillment.getOrderId()).path(FULFILLMENTS),
				shopifyFulfillmentRoot);
		final ShopifyFulfillmentRoot shopifyFulfillmentRootResponse = response.readEntity(ShopifyFulfillmentRoot.class);
		return shopifyFulfillmentRootResponse.getFulfillment();
	}

	private ShopifyFulfillment createFulfillmentWithFulfillmentOrderApi(
			final ShopifyFulfillmentCreationRequest shopifyFulfillmentCreationRequest,
			final List<ShopifyFulfillmentOrder> fulfillmentOrders) throws ShopifyEmptyLineItemsException {
		final ShopifyFulfillmentPayloadRoot payload = LegacyToFulfillmentOrderMapping
				.toShopifyFulfillmentPayloadRoot(shopifyFulfillmentCreationRequest.getRequest(), fulfillmentOrders);

		final Response response = post(getWebTarget().path(FULFILLMENTS), payload);
		final ShopifyFulfillmentRoot shopifyFulfillmentRootResponse = response.readEntity(ShopifyFulfillmentRoot.class);

		return shopifyFulfillmentRootResponse.getFulfillment();
	}
}
