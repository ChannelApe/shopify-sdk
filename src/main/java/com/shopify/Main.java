package com.shopify;

import com.shopify.model.ShopifyProduct;
import com.shopify.model.ShopifyProducts;
import com.shopify.model.ShopifyShop;
import com.shopify.model.ShopifyTheme;
import java.util.List;

public class Main {
  public static void main(String[] args){

    final ShopifySdk shopifySdk = ShopifySdk.newBuilder()
        .withSubdomain("sukiyu09.myshopify.com")
        .withAccessToken("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").build();
    System.out.println(shopifySdk.getShop().getShop().getName());

//    final ShopifyProducts products = shopifySdk.getShop()
//    System.out.println(products.size());
  }
}
