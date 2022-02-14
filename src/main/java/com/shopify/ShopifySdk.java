package com.shopify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.github.rholder.retry.*;
import com.google.common.base.Strings;
import com.shopify.exceptions.ShopifyClientException;
import com.shopify.exceptions.ShopifyErrorResponseException;
import com.shopify.mappers.ResponseEntityToStringMapper;
import com.shopify.mappers.ShopifySdkObjectMapper;
import com.shopify.model.*;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientProperties;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
	static final String API_FEATURES_HEADER = "X-Shopify-Api-Features";
	static final String INCLUDE_PRESENTMENT_PRICES = "include-presentment-prices";
	static final String OAUTH = "oauth";
	static final String REVOKE = "revoke";
	static final String ACCESS_TOKEN = "access_token";
	static final String VERSION_2020_04 = "api/2020-04";
	static final String VERSION_2020_10 = "api/2020-10";
	static final String VERSION_2021_10 = "api/2021-10";
	static final String PRODUCTS = "products";
	static final String INVENTORY_LEVELS = "inventory_levels";
	static final String INVENTORY_ITEMS = "inventory_items";
	static final String COLLECTS = "collects";
	static final String VARIANTS = "variants";
	static final String COLLECTIONS = "collections";
	static final String CUSTOM_COLLECTIONS = "custom_collections";
	static final String SMART_COLLECTIONS = "smart_collections";
	static final String RECURRING_APPLICATION_CHARGES = "recurring_application_charges";
	static final String APPLICATION_CHARGES = "application_charges";
	static final String APPLICATION_CREDITS = "application_credits";
	static final String USAGE_CHARGES = "usage_charges";
	static final String SHOPIFY_PAYMENTS = "shopify_payments";
	static final String BALANCE = "balance";
	static final String DISPUTES = "disputes";
	static final String PAYOUTS = "payouts";
	static final String TRANSACTIONS = "transactions";
	static final String ORDERS = "orders";
	static final String TENDER_TRANSACTIONS = "tender_transactions";
	static final String COUNTRIES = "countries";
	static final String POLICIES = "policies";
	static final String SHIPPING_ZONES = "shipping_zones";
	static final String DRAFT_ORDERS = "draft_orders";
	static final String FULFILLMENTS = "fulfillments";
	static final String CARRIER_SERVICES = "carrier_services";
	static final String ACTIVATE = "activate";
	static final String IMAGES = "images";
	static final String SHOP = "shop";
	static final String COUNT = "count";
	static final String CLOSE = "close";
	static final String CANCEL = "cancel";
	static final String METAFIELDS = "metafields";
	static final String PRICE_RULES = "price_rules";
	static final String MARKETING_EVENTS = "marketing_events";
	static final String DISCOUNT_CODES = "discount_codes";
	static final String RISKS = "risks";
	static final String LOCATIONS = "locations";
	static final String JSON = ".json";
	static final String LIMIT_QUERY_PARAMETER = "limit";
	static final String PAGE_INFO_QUERY_PARAMETER = "page_info";
	static final String STATUS_QUERY_PARAMETER = "status";
	static final String ANY_STATUSES = "any";
	static final String CREATED_AT_MIN_QUERY_PARAMETER = "created_at_min";
	static final String CREATED_AT_MAX_QUERY_PARAMETER = "created_at_max";
	static final String UPDATED_AT_MIN_QUERY_PARAMETER = "updated_at_min";
	static final String UPDATED_AT_MAX_QUERY_PARAMETER = "updated_at_max";
	static final String PROCESSED_AT_MIN_QUERY_PARAMETER = "processed_at_min";
	static final String PROCESSED_AT_MAX_QUERY_PARAMETER = "processed_at_max";
	static final String DATE_QUERY_PARAMETER = "date";
	static final String FIELDS = "fields";
	static final String INVENTORY_ITEM_IDS = "inventory_item_ids";
	static final String LOCATION_IDS = "location_ids";
	static final String IDS = "ids";
	static final String ATTRIBUTION_APP_ID_QUERY_PARAMETER = "attribution_app_id";
	static final String IDS_QUERY_PARAMETER = "ids";
	static final String SINCE_ID_QUERY_PARAMETER = "since_id";
	static final String PAYOUT_ID_QUERY_PARAMETER = "payout_id";
	static final String QUERY_QUERY_PARAMETER = "query";
	static final String CALCULATE = "calculate";
	static final String REFUNDS = "refunds";
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
	private static final String CUSTOMER_SAVED_SEARCHES = "customer_saved_searches";
	private static final String SEARCH = "search";
	private static final String CHECKOUTS = "checkouts";

	public static interface OptionalsStep {

		/**
		 * The Shopify SDK uses random waits in between retry attempts. Minimum duration
		 * time to wait before retrying a failed request. Value must also be less than
		 * {@link #withMaximumRequestRetryRandomDelay(int, TimeUnit) Maximum Request
		 * Retry Random Delay}.<br>
		 * Default value is: 1 second.
		 *
		 * @param duration
		 * @param timeUnit
		 * @return {@link OptionalsStep}
		 */
		OptionalsStep withMinimumRequestRetryRandomDelay(int duration, TimeUnit timeUnit);

		/**
		 * The Shopify SDK uses random waits in between retry attempts. Maximum duration
		 * time to wait before retrying a failed request. Value must also be more than
		 * {@link #withMinimumRequestRetryRandomDelay(int, TimeUnit) Minimum Request
		 * Retry Random Delay}.<br>
		 * Default value is: 5 seconds.
		 *
		 * @param duration
		 * @param timeUnit
		 * @return {@link OptionalsStep}
		 */
		OptionalsStep withMaximumRequestRetryRandomDelay(int duration, TimeUnit timeUnit);

		/**
		 * Maximum duration time to keep attempting requests <br>
		 * Default value is: 3 minutes.
		 *
		 * @param duration
		 * @param timeUnit
		 * @return {@link OptionalsStep}
		 */
		OptionalsStep withMaximumRequestRetryTimeout(int duration, TimeUnit timeUnit);

		/**
		 * The duration to wait when connecting to Shopify's API. <br>
		 * Default value is: 1 minute.
		 *
		 * @param duration
		 * @param timeUnit
		 * @return {@link OptionalsStep}
		 */
		OptionalsStep withConnectionTimeout(int duration, TimeUnit timeUnit);

		/**
		 * The duration to attempt to read a response from Shopify's API. <br>
		 * Default value is: 15 seconds.
		 *
		 * @param duration
		 * @param timeUnit
		 * @return {@link OptionalsStep}
		 */
		OptionalsStep withReadTimeout(int duration, TimeUnit timeUnit);

		/**
		 * String representation of the version you want to use. If not populated, this
		 * will use shopify oldest stable version. Although this is not recommended so
		 * you can target a set of shopify features. Ex: '2020-10' '2020-07' '2020-04'.
		 * If you are specifying the API URL ensure you leave off the version if you are
		 * using this.
		 *
		 * @param apiVersion
		 * @return
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
		final WebTarget productWebTarget = getWebTarget().path(PRODUCTS).queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);

		final Callable<Response> responseCallable = () -> productWebTarget.request(MediaType.APPLICATION_JSON)
				.header(ACCESS_TOKEN_HEADER, accessToken)
				.header(API_FEATURES_HEADER, INCLUDE_PRESENTMENT_PRICES)
				.get();
		final Response response = invokeResponseCallable(responseCallable);
		final Response productResponse =  handleResponse(response, Status.OK);

		final ShopifyProductsRoot shopifyProductsRoot = productResponse.readEntity(ShopifyProductsRoot.class);
		return mapPagedResponse(shopifyProductsRoot.getProducts(), response);
	}

	public ShopifyPage<ShopifyProduct> getUpdatedProducts(final DateTime minimumUpdatedAtDate,
													  final DateTime maximumUpdatedAtDate,
													  final int pageSize, final String pageInfo) {
		WebTarget webTarget = buildProductsEndpoint().queryParam(LIMIT_QUERY_PARAMETER, pageSize).queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		if (Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
					.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString());
		}
		final Response response = get(webTarget);
		return getProducts(response);
	}

	public int getUpdatedProductsCount(final DateTime minimumUpdatedAtDate, final DateTime maximumUpdatedAtDate) {
		final Response response = get(getWebTarget().path(PRODUCTS).path(COUNT)
				.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
				.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString()));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
	}

	public ShopifyPage<ShopifyInventoryLevel> getInventoryLevels(
			final int pageSize, final String pageInfo, List<String> locationIds, List<String> inventoryItemIds) {
		WebTarget webTarget = buildInventoryLevelsEndpoint().queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);

		if (Strings.isNullOrEmpty(pageInfo)) {
			if (null != locationIds) {
				webTarget = webTarget.queryParam(LOCATION_IDS, String.join(",", locationIds));
			}
			if (null != inventoryItemIds) {
				webTarget = webTarget.queryParam(INVENTORY_ITEM_IDS, String.join(",", inventoryItemIds));
			}
		}
		final Response response = get(webTarget);
		return getInventoryLevels(response);
	}

	public ShopifyPage<ShopifyInventoryItem> getInventoryItems(final int pageSize, final String pageInfo, List<String> ids) {
		WebTarget webTarget = buildInventoryItemsEndpoint().queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		if (Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(IDS, String.join(",", ids));
		}
		final Response response = get(webTarget);
		return getInventoryItems(response);
	}

	public ShopifyPage<ShopifyProduct> getUpdatedProductsWithFields(final DateTime minimumUpdatedAtDate,
														  final DateTime maximumUpdatedAtDate,
														  final int pageSize, final String pageInfo, List<String> fields) {
		WebTarget webTarget = buildProductsEndpoint().queryParam(LIMIT_QUERY_PARAMETER, pageSize).queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		if (Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
					.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString())
					.queryParam(FIELDS, String.join( ",", fields));
			;
		}
		final Response response = get(webTarget);
		return getProducts(response);
	}

	private ShopifyPage<ShopifyProduct> getProducts(final Response response) {
		final ShopifyProductsRoot shopifyProductsRoot = response.readEntity(ShopifyProductsRoot.class);
		return mapPagedResponse(shopifyProductsRoot.getProducts(), response);
	}

	private ShopifyPage<ShopifyInventoryLevel> getInventoryLevels(final Response response) {
		final ShopifyInventoryLevelsRoot shopifyInventoryLevelsRoot = response.readEntity(ShopifyInventoryLevelsRoot.class);
		return mapPagedResponse(shopifyInventoryLevelsRoot.getInventoryLevels(), response);
	}

	private ShopifyPage<ShopifyInventoryItem> getInventoryItems(final Response response) {
		final ShopifyInventoryItemsRoot shopifyInventoryItemsRoot = response.readEntity(ShopifyInventoryItemsRoot.class);
		return mapPagedResponse(shopifyInventoryItemsRoot.getInventoryItems(), response);
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

	public int getProductCount() {
		final Response response = get(getWebTarget().path(PRODUCTS).path(COUNT));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
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

	public ShopifyPage<ShopifyCustomCollection> getUpdatedCustomCollections(final DateTime minimumUpdatedAtDate,
														  final DateTime maximumUpdatedAtDate,
														  final int pageSize, final String pageInfo) {
		WebTarget webTarget = buildCustomCollectionsEndpoint().queryParam(LIMIT_QUERY_PARAMETER, pageSize).queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		if (Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
					.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString());
		}
		final Response response = get(webTarget);
		return getCustomCollections(response);
	}

	public int getUpdatedCustomCollectionsCount(final DateTime minimumUpdatedAtDate, final DateTime maximumUpdatedAtDate) {
		final Response response = get(getWebTarget().path(CUSTOM_COLLECTIONS).path(COUNT)
				.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
				.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString()));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
	}

	private ShopifyPage<ShopifyCustomCollection> getCustomCollections(Response response) {
		final ShopifyCustomCollectionsRoot shopifyCustomCollectionsRoot = response
				.readEntity(ShopifyCustomCollectionsRoot.class);
		return mapPagedResponse(shopifyCustomCollectionsRoot.getCustomCollections(), response);
	}

	public ShopifyPage<ShopifyCollect> getCollects(final int pageSize, final String pageInfo) {
		WebTarget webTarget = buildCollectsEndpoint().queryParam(LIMIT_QUERY_PARAMETER, pageSize).queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		final Response response = get(webTarget);
		return getCollects(response);
	}

	public int getCollectsCount() {
		final Response response = get(getWebTarget().path(COLLECTS).path(COUNT));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
	}

	private ShopifyPage<ShopifyCollect> getCollects(Response response) {
		final ShopifyCollectsRoot shopifyCollectsRoot = response
				.readEntity(ShopifyCollectsRoot.class);
		return mapPagedResponse(shopifyCollectsRoot.getCollects(), response);
	}

	public ShopifyPage<ShopifySmartCollection> getUpdatedSmartCollections(final DateTime minimumUpdatedAtDate,
																  final DateTime maximumUpdatedAtDate,
																  final int pageSize, final String pageInfo) {
		WebTarget webTarget = buildSmartCollectionsEndpoint().queryParam(LIMIT_QUERY_PARAMETER, pageSize).queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		if (Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
					.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString());
		}
		final Response response = get(webTarget);
		return getSmartCollections(response);
	}

	public int getUpdatedSmartCollectionsCount(final DateTime minimumUpdatedAtDate, final DateTime maximumUpdatedAtDate) {
		final Response response = get(getWebTarget().path(SMART_COLLECTIONS).path(COUNT)
				.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
				.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString()));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
	}

	private ShopifyPage<ShopifySmartCollection> getSmartCollections(Response response) {
		final ShopifySmartCollectionsRoot shopifySmartCollectionsRoot = response
				.readEntity(ShopifySmartCollectionsRoot.class);
		return mapPagedResponse(shopifySmartCollectionsRoot.getSmartCollections(), response);
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

	public List<ShopifyRecurringApplicationCharge> getAllRecurringApplicationCharges(final String sinceId) {
		WebTarget target = getWebTarget().path(RECURRING_APPLICATION_CHARGES);
		if (sinceId != null) {
			target = target.queryParam(SINCE_ID_QUERY_PARAMETER, sinceId);
		}

		final Response response = get(target);
		return response.readEntity(ShopifyRecurringApplicationChargeListRoot.class).getRecurringApplicationCharges();
	}

	public List<ShopifyApplicationCharge> getApplicationCharge(final String sinceId) {
		WebTarget target = getWebTarget().path(APPLICATION_CHARGES);
		if (sinceId != null) {
			target = target.queryParam(SINCE_ID_QUERY_PARAMETER, sinceId);
		}

		final Response response = get(target);
		return response.readEntity(ShopifyApplicationChargeRoot.class).getApplicationCharges();

	}

	public List<ShopifyApplicationCredit> getApplicationCredits() {
		final Response response = get(getWebTarget().path(APPLICATION_CREDITS));
		return response.readEntity(ShopifyApplicationCreditRoot.class).getApplicationCredits();
	}

	public List<ShopifyUsageCharge> getUsageCharges(final String recurringApplicationChargeId) {
		final Response response = get(getWebTarget().path(RECURRING_APPLICATION_CHARGES).path(recurringApplicationChargeId).path(USAGE_CHARGES));
		return response.readEntity(ShopifyUsageChargeRoot.class).getUsageCharges();
	}

	public ShopifyPaymentsBalance getPaymentsBalance() {
		final Response response = get(buildPaymentsEndpoint().path(BALANCE));
		return response.readEntity(ShopifyPaymentsBalanceRoot.class).getBalance().get(0);
	}

	public ShopifyPage<ShopifyPaymentsDispute> getPaymentsDisputes(final String pageInfo, final int pageSize) {
		final Response response = get(buildPaymentsEndpoint().path(DISPUTES).queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo));
		final ShopifyPaymentsDisputeRoot shopifyPaymentsDisputeRoot = response.readEntity(ShopifyPaymentsDisputeRoot.class);
		return mapPagedResponse(shopifyPaymentsDisputeRoot.getDisputes(), response);
	}

	public ShopifyPage<ShopifyPaymentsPayout> getPaymentsPayouts(final String pageInfo, final int pageSize,
																 final Date date) {
		WebTarget webTarget = buildPaymentsEndpoint().path(PAYOUTS)
				.queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);

		if(Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(DATE_QUERY_PARAMETER, date);
		}

		final Response response = get(webTarget);
		final ShopifyPaymentsPayoutRoot shopifyPaymentsPayoutRoot = response.readEntity(ShopifyPaymentsPayoutRoot.class);
		return mapPagedResponse(shopifyPaymentsPayoutRoot.getPayouts(), response);
	}

	public ShopifyPage<ShopifyPaymentsTransaction> getPaymentsTransactions(final String pageInfo, final int pageSize,
																		   final String payoutId) {
		WebTarget webTarget = buildPaymentsEndpoint().path(BALANCE).path(TRANSACTIONS)
				.queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);

		if(Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(PAYOUT_ID_QUERY_PARAMETER, payoutId);
		}

		final Response response = get(webTarget);
		final ShopifyPaymentsTransactionRoot shopifyPaymentsTransactionRoot = response.readEntity(ShopifyPaymentsTransactionRoot.class);
		return mapPagedResponse(shopifyPaymentsTransactionRoot.getTransactions(), response);
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

	public int getOrderTransactionsCount(final String orderId) {
		final Response response = get(buildOrdersEndpoint().path(orderId).path(TRANSACTIONS).path(COUNT));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
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

	public ShopifyPage<ShopifyOrder> getUpdatedOrders(final DateTime minimumUpdatedAtDate,
																   final DateTime maximumUpdatedAtDate,
													  final int pageSize, final String pageInfo) {
		WebTarget webTarget = buildOrdersEndpoint().queryParam(LIMIT_QUERY_PARAMETER, pageSize).queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		if (Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
					.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString())
					.queryParam(STATUS_QUERY_PARAMETER, ANY_STATUSES);
		}
		final Response response = get(webTarget);
		return getOrders(response);
	}


	public int getUpdatedOrdersCount(final DateTime minimumUpdatedAtDate, final DateTime maximumUpdatedAtDate) {
		final Response response = get(getWebTarget().path(ORDERS).path(COUNT)
				.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
				.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString()));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
	}

	public ShopifyPage<ShopifyTenderTransaction> getProcessedTenderTransactions(final DateTime minimumUpdatedAtDate,
													  final DateTime maximumUpdatedAtDate,
													  final int pageSize, final String pageInfo) {
		WebTarget webTarget = buildTenderTransactionsEndpoint().queryParam(LIMIT_QUERY_PARAMETER, pageSize).queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		if (Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(PROCESSED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
					.queryParam(PROCESSED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString())
					.queryParam(STATUS_QUERY_PARAMETER, ANY_STATUSES);
		}
		final Response response = get(webTarget);
		return getTenderTransactions(response);
	}


	public ShopifyFulfillment createFulfillment(
			final ShopifyFulfillmentCreationRequest shopifyFulfillmentCreationRequest) {
		final ShopifyFulfillmentRoot shopifyFulfillmentRoot = new ShopifyFulfillmentRoot();
		final ShopifyFulfillment shopifyFulfillment = shopifyFulfillmentCreationRequest.getRequest();

		shopifyFulfillmentRoot.setFulfillment(shopifyFulfillment);
		final Response response = post(buildOrdersEndpoint().path(shopifyFulfillment.getOrderId()).path(FULFILLMENTS),
				shopifyFulfillmentRoot);
		final ShopifyFulfillmentRoot shopifyFulfillmentRootResponse = response.readEntity(ShopifyFulfillmentRoot.class);
		return shopifyFulfillmentRootResponse.getFulfillment();
	}

	public ShopifyFulfillment updateFulfillment(final ShopifyFulfillmentUpdateRequest shopifyFulfillmentUpdateRequest) {
		final ShopifyFulfillmentRoot shopifyFulfillmentRoot = new ShopifyFulfillmentRoot();
		final ShopifyFulfillment shopifyFulfillment = shopifyFulfillmentUpdateRequest.getRequest();
		shopifyFulfillmentRoot.setFulfillment(shopifyFulfillment);
		final Response response = put(buildOrdersEndpoint().path(shopifyFulfillment.getOrderId()).path(FULFILLMENTS)
				.path(shopifyFulfillment.getId()), shopifyFulfillmentRoot);
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

	public ShopifyPage<ShopifyCustomerSavedSearch> getCustomerSavedSearches(final int pageSize, final String pageInfo) {
		WebTarget webTarget = buildCustomerSavedSearchesEndpoint()
				.queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		final Response response = get(webTarget);
		return getCustomerSavedSearches(response);
	}

	public int getCustomerSavedSearchesCount() {
		final Response response = get(getWebTarget().path(CUSTOMER_SAVED_SEARCHES).path(COUNT));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
	}

	public ShopifyPage<ShopifyCustomer> getUpdatedCustomers(final DateTime minimumUpdatedAtDate,
																				 final DateTime maximumUpdatedAtDate,
																				 final int pageSize, final String pageInfo) {
		WebTarget webTarget = buildCustomersEndpoint()
				.queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		if (Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
					.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString());
		}
		final Response response = get(webTarget);
		return getCustomers(response);
	}

	public int getUpdatedCustomersCount(final DateTime minimumUpdatedAtDate, final DateTime maximumUpdatedAtDate) {
		final Response response = get(getWebTarget().path(CUSTOMERS).path(COUNT)
				.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
				.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString()));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
	}

	public ShopifyPage<ShopifyCheckout> getUpdatedAbandonedCheckouts(final DateTime minimumUpdatedAtDate,
															final DateTime maximumUpdatedAtDate,
															final int pageSize, final String pageInfo) {
		WebTarget webTarget = buildAbandonedCheckoutsEndpoint()
				.queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		if (Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
					.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString());
		}
		final Response response = get(webTarget);
		return getAbandonedCheckouts(response);
	}

	public int getAbandonedCheckoutsCount(final DateTime minimumUpdatedAtDate, final DateTime maximumUpdatedAtDate) {
		final Response response = get(getWebTarget().path(CHECKOUTS).path(COUNT)
				.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
				.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString()));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
	}

	public ShopifyPage<ShopifyDraftOrder> getUpdatedDraftOrders(final DateTime minimumUpdatedAtDate,
																	 final DateTime maximumUpdatedAtDate,
																	 final int pageSize, final String pageInfo) {
		WebTarget webTarget = buildDraftOrdersEndpoint()
				.queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		if (Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
					.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString());
		}
		final Response response = get(webTarget);
		return getDraftOrders(response);
	}

	public int getUpdatedDraftOrdersCount(final DateTime minimumUpdatedAtDate, final DateTime maximumUpdatedAtDate) {
		final Response response = get(getWebTarget().path(DRAFT_ORDERS).path(COUNT)
				.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
				.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString()));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
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

	public ShopifyPage<Metafield> getResourceMetafields(final String resourceId, final String resource, final int pageSize, final String pageInfo) {
		if (isMetafieldResource(resource)) {
			WebTarget webTarget = getWebTarget().path(VERSION_2021_10).path(resource.toLowerCase()).path(resourceId).path(METAFIELDS).queryParam(LIMIT_QUERY_PARAMETER, pageSize);
			if (!Strings.isNullOrEmpty(pageInfo)) {
				webTarget = webTarget.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
			}
			final Response response = get(webTarget);
			return mapPagedResponse(response.readEntity(MetafieldsRoot.class).getMetafields(), response);
		}
		return new ShopifyPage<>();
	}

	public ShopifyPage<Metafield> getShopMetafields(final DateTime minimumUpdatedAtDate,
											 final DateTime maximumUpdatedAtDate,
											 final int pageSize, final String pageInfo) {
		WebTarget webTarget = buildShopMetafieldsEndpoint().queryParam(LIMIT_QUERY_PARAMETER, pageSize).queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		if (Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
					.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString());
		}
		final Response response = get(webTarget);
		final MetafieldsRoot metafieldsRootResponse = response.readEntity(MetafieldsRoot.class);
		return mapPagedResponse(metafieldsRootResponse.getMetafields(), response);
	}

	public int getShopMetafieldsCount(final DateTime minimumUpdatedAtDate, final DateTime maximumUpdatedAtDate) {
		final Response response = get(getWebTarget().path(METAFIELDS).path(COUNT)
				.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
				.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString()));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
	}

	public ShopifyPage<ShopifyPriceRule> getPriceRules(final DateTime minimumUpdatedAtDate,
													final DateTime maximumUpdatedAtDate,
													final int pageSize, final String pageInfo) {
		WebTarget webTarget = buildPriceRulesEndpoint().queryParam(LIMIT_QUERY_PARAMETER, pageSize).queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		if (Strings.isNullOrEmpty(pageInfo)) {
			webTarget = webTarget.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
					.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString());
		}
		final Response response = get(webTarget);
		final ShopifyPriceRulesRoot shopifyPriceRulesRoot = response.readEntity(ShopifyPriceRulesRoot.class);
		return mapPagedResponse(shopifyPriceRulesRoot.getPriceRules(), response);
	}

	public int getPriceRulesCount(final DateTime minimumUpdatedAtDate, final DateTime maximumUpdatedAtDate) {
		final Response response = get(getWebTarget().path(PRICE_RULES).path(COUNT)
				.queryParam(UPDATED_AT_MIN_QUERY_PARAMETER, minimumUpdatedAtDate.toString())
				.queryParam(UPDATED_AT_MAX_QUERY_PARAMETER, maximumUpdatedAtDate.toString()));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
	}

	public ShopifyPage<ShopifyDiscountCode> getDiscountCodes(final String priceRuleId,
													   final int pageSize, final String pageInfo) {
		WebTarget webTarget = getWebTarget().path(VERSION_2020_10)
				.path(PRICE_RULES).path(priceRuleId).path(DISCOUNT_CODES).queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		final Response response = get(webTarget);
		final ShopifyDiscountCodesRoot shopifyDiscountCodesRoot = response.readEntity(ShopifyDiscountCodesRoot.class);
		return mapPagedResponse(shopifyDiscountCodesRoot.getDiscountCodes(), response);
	}

	public ShopifyPage<ShopifyMarketingEvent> getMarketingEvents(final int pageSize, final String pageInfo) {
		WebTarget webTarget = getWebTarget().path(VERSION_2020_10)
				.path(MARKETING_EVENTS).queryParam(LIMIT_QUERY_PARAMETER, pageSize)
				.queryParam(PAGE_INFO_QUERY_PARAMETER, pageInfo);
		final Response response = get(webTarget);
		final ShopifyMarketingEventsRoot shopifyMarketingEventsRoot = response.readEntity(ShopifyMarketingEventsRoot.class);
		return mapPagedResponse(shopifyMarketingEventsRoot.getMarketingEvents(), response);
	}

	public int getMarketingEventsCount() {
		final Response response = get(getWebTarget().path(MARKETING_EVENTS).path(COUNT));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
	}

	public List<ShopifyOrderRisk> getOrderRisks(final String orderId) {
		final Response response = get(buildOrdersEndpoint().path(orderId).path(RISKS));
		final ShopifyOrderRisksRoot shopifyOrderRisksRootResponse = response.readEntity(ShopifyOrderRisksRoot.class);
		return shopifyOrderRisksRootResponse.getRisks();
	}

	public List<ShopifyCountry> getCountries() {
		final Response response = get(buildCountriesEndpoint());
		final ShopifyCountriesRoot shopifyCountriesRoot = response.readEntity(ShopifyCountriesRoot.class);
		return shopifyCountriesRoot.getCountries();
	}

	public int getCountriesCount() {
		final Response response = get(getWebTarget().path(COUNTRIES).path(COUNT));
		final Count count = response.readEntity(Count.class);
		return count.getCount();
	}

	public List<ShopifyPolicy> getPolicies() {
		final Response response = get(buildPoliciesEndpoint());
		final ShopifyPoliciesRoot shopifyPoliciesRoot = response.readEntity(ShopifyPoliciesRoot.class);
		return shopifyPoliciesRoot.getPolicies();
	}

	public List<ShopifyShippingZone> getShippingZones() {
		final Response response = get(buildShippingZoneEndpoint());
		final ShopifyShippingZonesRoot shopifyShippingZonesRoot = response.readEntity(ShopifyShippingZonesRoot.class);
		return shopifyShippingZonesRoot.getShippingZones();
	}

	public List<ShopifyCarrierService> getCarrierServices() {
		final Response response = get(buildCarrierServiceEndpoint());
		final ShopifyCarrierServiceRoot shopifyCarrierServiceRoot = response.readEntity(ShopifyCarrierServiceRoot.class);
		return shopifyCarrierServiceRoot.getCarrierServices();
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

	private ShopifyPage<ShopifyCheckout> getAbandonedCheckouts(final Response response) {
		final ShopifyCheckoutsRoot shopifyCheckoutsRoot = response.readEntity(ShopifyCheckoutsRoot.class);
		return mapPagedResponse(shopifyCheckoutsRoot.getCheckouts(), response);
	}

	private ShopifyPage<ShopifyDraftOrder> getDraftOrders(final Response response) {
		final ShopifyDraftOrdersRoot shopifyDraftOrdersRoot = response.readEntity(ShopifyDraftOrdersRoot.class);
		return mapPagedResponse(shopifyDraftOrdersRoot.getDraftOrders(), response);
	}

	private ShopifyPage<ShopifyCustomerSavedSearch> getCustomerSavedSearches(final Response response) {
		final ShopifyCustomerSavedSearchesRoot shopifyCustomerSavedSearchesRoot = response.readEntity(ShopifyCustomerSavedSearchesRoot.class);
		return mapPagedResponse(shopifyCustomerSavedSearchesRoot.getCustomerSavedSearches(), response);
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

	private ShopifyPage<ShopifyTenderTransaction> getTenderTransactions(final Response response) {
		final ShopifyTenderTransactionsRoot shopifyTenderTransactionsRoot = response.readEntity(ShopifyTenderTransactionsRoot.class);
		return mapPagedResponse(shopifyTenderTransactionsRoot.getTenderTransactions(), response);
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
		return RetryerBuilder.<Response>newBuilder().retryIfResult(ShopifySdk::shouldRetryResponse).retryIfException()
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

	private String generateToken() {
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

		return ClientBuilder.newClient().register(provider);
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

	private WebTarget buildTenderTransactionsEndpoint() {
		return getWebTarget().path(TENDER_TRANSACTIONS);
	}

	private WebTarget buildCountriesEndpoint() {
		return getWebTarget().path(COUNTRIES);
	}

	private WebTarget buildPoliciesEndpoint() {
		return getWebTarget().path(POLICIES);
	}

	private WebTarget buildProductsEndpoint() {
		return getWebTarget().path(PRODUCTS);
	}

	private WebTarget buildPaymentsEndpoint() {
		return getWebTarget().path(SHOPIFY_PAYMENTS);
	}

	private WebTarget buildInventoryLevelsEndpoint() {
		return getWebTarget().path(INVENTORY_LEVELS);
	}
	private WebTarget buildInventoryItemsEndpoint() {
		return getWebTarget().path(INVENTORY_ITEMS);
	}

	private WebTarget buildShopMetafieldsEndpoint() {
		return getWebTarget().path(VERSION_2021_10).path(METAFIELDS);
	}

	private WebTarget buildPriceRulesEndpoint() {
		return getWebTarget().path(PRICE_RULES);
	}

	private WebTarget buildCollectsEndpoint() {
		return getWebTarget().path(COLLECTS);
	}

	private WebTarget buildCustomCollectionsEndpoint() {
		return getWebTarget().path(CUSTOM_COLLECTIONS);
	}

	private WebTarget buildSmartCollectionsEndpoint() {
		return getWebTarget().path(SMART_COLLECTIONS);
	}

	private WebTarget buildDraftOrdersEndpoint() {
		return getWebTarget().path(DRAFT_ORDERS);
	}

	private WebTarget buildCustomerSavedSearchesEndpoint() {
		return getWebTarget().path(CUSTOMER_SAVED_SEARCHES);
	}

	private WebTarget buildCustomersEndpoint() {
		return getWebTarget().path(CUSTOMERS);
	}

	private WebTarget buildAbandonedCheckoutsEndpoint() {
		return getWebTarget().path(CHECKOUTS);
	}

	private WebTarget buildShippingZoneEndpoint() {
		return getWebTarget().path(SHIPPING_ZONES);
	}

	private WebTarget buildCarrierServiceEndpoint() {
		return getWebTarget().path(CARRIER_SERVICES);
	}

	private boolean isMetafieldResource(final String resource) {
		return resource.toLowerCase().equals(COLLECTIONS) ||
				resource.toLowerCase().equals(CUSTOMERS) ||
				resource.toLowerCase().equals(DRAFT_ORDERS) ||
				resource.toLowerCase().equals(PRODUCTS) ||
				resource.toLowerCase().equals(ORDERS);

	}

}
