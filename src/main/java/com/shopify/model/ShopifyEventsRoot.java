package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ShopifyEventsRoot {
    @JsonProperty("events")
    private List<ShopifyEvent> events;
}
