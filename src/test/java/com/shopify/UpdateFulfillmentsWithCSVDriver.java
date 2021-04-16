package com.shopify;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopify.model.ShopifyFulfillment;
import com.shopify.model.ShopifyFulfillmentUpdateRequest;
import com.shopify.model.ShopifyOrder;

public class UpdateFulfillmentsWithCSVDriver {

	private static final String SHOP_SUBDOMAIN = System.getenv("SHOP_SUBDOMAIN");
	private static final String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");
	private static final String FULFILLMENT_FILE_URL = System.getenv("FULFILLMENT_FILE_URL");

	private static final String TEMP_FILE_PREFIX = "fulfillment_file_";
	private static final String TEMP_FILE_EXTENSION = ".csv";
	private static final String CHARACTER_ENCODING = "UTF-8";
	private static final char CSV_DELIMITER = ',';
	private static final String SHOPIFY_ORDER_ID_HEADER = "Shopify Order ID";
	private static final String SHOPIFY_FULFILLMENT_ID_HEADER = "Shopify Fulfillment ID";
	private static final String TRACKING_NUMBER_HEADER = "Tracking Number";

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateFulfillmentsWithCSVDriver.class);

	private ShopifySdk shopifySdk;

	@Before
	public void setUp() {
		shopifySdk = ShopifySdk.newBuilder().withSubdomain(SHOP_SUBDOMAIN).withAccessToken(ACCESS_TOKEN)
				.withMaximumRequestRetryTimeout(5, TimeUnit.SECONDS)
				.withMaximumRequestRetryRandomDelay(5, TimeUnit.SECONDS).withApiVersion("2020-07").build();
	}

	@Test
	public void updateFulfillmentsWithCSV() throws IOException {
		assertNotNull("Invalid shop", shopifySdk.getShop());

		File csvFile = null;
		try {
			csvFile = downloadFile(FULFILLMENT_FILE_URL);

			final CSVParser csvParser = buildCsvParser(csvFile);
			csvParser.getRecords().stream().forEach(csvRecord -> {
				final String shopifyOrderId = csvRecord.get(SHOPIFY_ORDER_ID_HEADER);
				final String shopifyFulfillmentId = csvRecord.get(SHOPIFY_FULFILLMENT_ID_HEADER);
				final String trackingNumber = csvRecord.get(TRACKING_NUMBER_HEADER);
				updateFulfillmentTrackingNumber(shopifyOrderId, shopifyFulfillmentId, trackingNumber);
			});
			csvParser.close();
		} finally {
			if (csvFile != null) {
				csvFile.delete();
			}
		}

	}

	private File downloadFile(final String fileUrl) throws IOException {
		final URL source = new URL(fileUrl);
		final File csvFile = File.createTempFile(TEMP_FILE_PREFIX + UUID.randomUUID().toString(), TEMP_FILE_EXTENSION);
		FileUtils.copyURLToFile(source, csvFile);
		return csvFile;
	}

	private CSVParser buildCsvParser(final File csvFile) throws IOException {
		final Reader reader = new InputStreamReader(new FileInputStream(csvFile), CHARACTER_ENCODING);
		final CSVFormat csvFormat = CSVFormat.RFC4180.withDelimiter(CSV_DELIMITER)
				.withHeader(SHOPIFY_ORDER_ID_HEADER, SHOPIFY_FULFILLMENT_ID_HEADER, TRACKING_NUMBER_HEADER)
				.withSkipHeaderRecord().withIgnoreEmptyLines();
		return new CSVParser(reader, csvFormat);
	}

	private void updateFulfillmentTrackingNumber(final String shopifyOrderId, final String shopifyFulfillmentId,
			final String trackingNumber) {
		final ShopifyOrder shopifyOrder = shopifySdk.getOrder(shopifyOrderId);
		if (shopifyOrder == null) {
			LOGGER.warn("Skipping order with ID of {} and fulfillment with ID of {} because order cannot be found.",
					shopifyOrderId, shopifyFulfillmentId);
		}
		Optional<ShopifyFulfillment> optionalShopifyFulfillment = shopifyOrder.getFulfillments().stream()
				.filter(shopifyFulfillment -> shopifyFulfillmentId.equals(shopifyFulfillment.getId())).findFirst();
		if (optionalShopifyFulfillment.isPresent()) {
			final ShopifyFulfillment currentShopifyFulfillment = optionalShopifyFulfillment.get();
			final ShopifyFulfillmentUpdateRequest shopifyFulfillmentUpdateRequest = ShopifyFulfillmentUpdateRequest
					.newBuilder().withCurrentShopifyFulfillment(currentShopifyFulfillment)
					.withTrackingCompany(currentShopifyFulfillment.getTrackingCompany())
					.withTrackingNumber(trackingNumber).withNotifyCustomer(false)
					.withLineItems(currentShopifyFulfillment.getLineItems())
					.withLocationId(currentShopifyFulfillment.getLocationId()).withTrackingUrls(Collections.emptyList())
					.build();
			final ShopifyFulfillment updatedShopifyFulfillment = shopifySdk
					.updateFulfillment(shopifyFulfillmentUpdateRequest);
			LOGGER.info("Updated Shopify Fulfillment with ID of {} on order {} with tracking number of {}",
					updatedShopifyFulfillment.getId(), updatedShopifyFulfillment.getOrderId(),
					updatedShopifyFulfillment.getTrackingNumber());
		} else {
			LOGGER.warn(
					"Skipping order with ID of {} and fulfillment with ID of {} because fulfillment cannot be found on order.",
					shopifyOrderId, shopifyFulfillmentId);
		}
	}

}
