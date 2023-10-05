package com.shopify;

import com.shopify.model.ShopifyWebhook;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args){
    final ShopifySdk  shopifySdk = ShopifySdk.newBuilder().withSubdomain("sukiyu09").withAccessToken("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")
            .withMinimumRequestRetryRandomDelay(200, TimeUnit.MILLISECONDS)
            .withMaximumRequestRetryTimeout(225, TimeUnit.MILLISECONDS)
            .withConnectionTimeout(500, TimeUnit.MILLISECONDS).build();
    ShopifyWebhook request = new ShopifyWebhook();
    request.setAddress("https://google.com.vn");
    request.setFormat("json");
    request.setTopic("app/uninstalled");
    shopifySdk.createWebhook(request);
    List<String> ids = shopifySdk.getWebhooks().stream().map(x -> x.getId()).collect(Collectors.toList());
    System.out.println(ids);
    shopifySdk.deleteWebhook(ids.get(0));
    List<String> ids1 = shopifySdk.getWebhooks().stream().map(x -> x.getId()).collect(Collectors.toList());
    System.out.println(ids1);
  }
}
