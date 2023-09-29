package com.shopify;

import java.util.stream.Collectors;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

  public static void main(String[] args){
    final ShopifySdk  shopifySdk = ShopifySdk.newBuilder().withSubdomain("sukiyu09").withAccessToken("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")
            .withMinimumRequestRetryRandomDelay(200, TimeUnit.MILLISECONDS)
            .withMaximumRequestRetryTimeout(225, TimeUnit.MILLISECONDS)
            .withConnectionTimeout(500, TimeUnit.MILLISECONDS).build();
    List<String> ids = shopifySdk.getThemes().stream().map(x -> x.getId()).collect(Collectors.toList());
    System.out.println(shopifySdk.getThemes().stream().map(x -> x.getId()).collect(Collectors.toList()));
    String key = shopifySdk.getAssets(ids.get(0)).get(100).getKey();
    System.out.println(key);
    System.out.println(shopifySdk.getAsset("97060814984",key ).getValue());
  }
}
