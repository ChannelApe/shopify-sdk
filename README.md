# Shopify SDK

Java SDK for Shopify REST APIs

| Service   | Develop | Master |
|-----------|---------|--------|
| CI Status | ![example branch parameter](https://github.com/plevie/shopify-sdk/actions/workflows/maven-develop.yml/badge.svg?branch=develop) | ![example branch parameter](https://github.com/plevie/shopify-sdk/actions/workflows/maven.yml/badge.svg?branch=master) |

[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=plevie_shopify-sdk&metric=alert_status)](https://sonarcloud.io/dashboard?id=plevie_shopify-sdk) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=plevie_shopify-sdk&metric=coverage)](https://sonarcloud.io/component_measures?id=plevie_shopify-sdk&metric=coverage)
[![Maven Central](https://img.shields.io/maven-central/v/com.channelape/shopify-sdk.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.channelape%22%20AND%20a:%22shopify-sdk%22)


## Quickstart
Creating SDK with store subdomain and access token, then making a sample call:

```java
final ShopifySdk shopifySdk = ShopifySdk.newBuilder()
  .withSubdomain(subdomain)
  .withAccessToken(accessToken).build();
final ShopifyShop shopifyShop = shopifySdk.getShop();
```
For private apps, `accessToken` should be the private app's Admin API password.

## Optional Configuration
The final parameters of the SDK builder are optional and will use default values when not supplied:

| Parameter   | Description | Default |
|-----------|---------|--------|
|Minimum Request Retry Random Delay|Shopify SDK uses a random wait strategy when calculating to perform the next attempt. This is the minimum duration to wait before performing the failed request.|1 second|
|Maximum Request Retry Random Delay|Shopify SDK uses a random wait strategy when calculating to perform the next attempt. This is the maximum duration to wait before performing the failed request.|5 seconds|
|Maximum Request Retry Timeout|The maximum time to keep retrying failed requests.|3 minutes|
|Connection Timeout|The duration to attempt to connect to Shopify's API.|1 minute|
|Read Timeout|The duration to attempt to read a response from Shopify's API.|15 Seconds|

## Building from source

	1. Install Maven
	2. Install JDK 8
	3. Clone the repository.
	3. Navigate to repository directory and run `mvn install`

## Release Notes
Please see our release notes here:  [https://github.com/plevie/shopify-sdk/releases](https://github.com/plevie/shopify-sdk/releases)


