package com.shopify;

import com.shopify.model.ShopifyProduct;
import com.shopify.model.ShopifyProducts;
import com.shopify.model.ShopifyShop;
import com.shopify.model.ShopifyTheme;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

  public static void main(String[] args){
    final ShopifySdk  shopifySdk = ShopifySdk.newBuilder().withSubdomain("sukiyu09").withAccessToken("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")
            .withMinimumRequestRetryRandomDelay(200, TimeUnit.MILLISECONDS)
            .withMaximumRequestRetryTimeout(225, TimeUnit.MILLISECONDS)
            .withConnectionTimeout(500, TimeUnit.MILLISECONDS).build();
//    final ShopifySdk shopifySdk = ShopifySdk.newBuilder().withSubdomain
//    ("sukiyu09")
//        .withAccessToken("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").build();
////

    System.out.println(shopifySdk.getThemes());

//    final ShopifyProducts products = shopifySdk.getShop()
//    System.out.println(products.size());
  }
}
