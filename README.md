# Shopify SDK

Java SDK for Shopify REST APIs

| Service   | Develop | Master |
|-----------|---------|--------|
| CI Status | [![Build Status](https://travis-ci.org/ChannelApe/shopify-sdk.svg?branch=develop)](https://travis-ci.org/ChannelApe/shopify-sdk) | [![Build Status](https://travis-ci.org/ChannelApe/shopify-sdk.svg?branch=master)](https://travis-ci.org/ChannelApe/shopify-sdk) |

[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.channelape%3Ashopify-sdk&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.channelape%3Ashopify-sdk) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.channelape%3Ashopify-sdk&metric=coverage)](https://sonarcloud.io/component_measures?id=com.channelape%3Ashopify-sdk&metric=coverage)

## Maven
```xml
	<dependency>
	    <groupId>com.channelape</groupId>
	    <artifactId>shopify-sdk</artifactId>
	    <version>1.0.0</version>
	</dependency>
```

## Quickstart
Creating SDK with store subdomain and access token, then making a sample call:

```java
final ShopifySdk shopifySdk = ShopifySdk.newBuilder()
  .withSubdomain(subdomain)
  .withAccessToken(accessToken).build();
final ShopifyShop shopifyShop = shopifySdk.getShop();
```

## Building from source

	1. Install Maven
	2. Install JDK 8
	3. Clone the repository.
	3. Navigate to repository directory and run `mvn install`

## Release Notes
Please see our release notes here:  [https://github.com/ChannelApe/shopify-sdk/releases](https://github.com/ChannelApe/shopify-sdk/releases)


