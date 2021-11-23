package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyLineItem {

	private String id;
	@XmlElement(name = "variant_id")
	private String variantId;
	private String title;
	private long quantity;
	private BigDecimal price;
	private long grams;
	private String sku;
	@XmlElement(name = "variant_title")
	private String variantTitle;
	private String vendor;
	@XmlElement(name = "product_id")
	private String productId;
	@XmlElement(name = "requires_shipping")
	private boolean requiresShipping;
	private boolean taxable;
	@XmlElement(name = "gift_card")
	private boolean giftCard;
	private String name;
	@XmlElement(name = "variant_inventory_management")
	private String variantInventoryManagement;
	@XmlElement(name = "fulfillable_quantity")
	private long fulfillableQuantity;
	@XmlElement(name = "total_discount")
	private BigDecimal totalDiscount;
	@XmlElement(name = "fulfillment_status")
	private String fulfillmentStatus;
	@XmlElement(name = "fulfillment_service")
	private String fulfillmentService;
	private boolean custom;
	@XmlElement(name = "applied_discount")
	private AppliedDiscountCode appliedDiscountCode;
	@JsonDeserialize(using = ShopifyPropertyDeserializer.class)
	private List<ShopifyProperty> properties;
	@XmlElement(name = "tax_lines")
	private List<ShopifyTaxLine> taxLines;
	@XmlElement(name = "price_set")
	private PriceSet priceSet;
	@XmlElement(name = "total_discount_set")
	private PriceSet totalDiscountSet;
	@XmlElement(name = "discount_allocations")
	private List<DiscountAllocation> discountAllocations;
	@XmlElement(name = "origin_location")
	private ShopifyAddress originalLocation;
	private List<ShopifyDuty> duties;
	@XmlElement(name = "product_exists")
	private boolean productExists;

	public static class ShopifyPropertyDeserializer extends JsonDeserializer<List<ShopifyProperty>> {

		@Override
		public List<ShopifyProperty> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			JsonNode node = jsonParser.readValueAsTree();
			List<ShopifyProperty> shopifyProperties = new ArrayList<>();
			ObjectMapper mapper = new ObjectMapper();

			if (node.isNull() || node.toString().isEmpty() || node.size() == 0) {
				return null;
			}

			if (node.isArray()) {
				for (int i = 0; i < node.size(); i++) {
					ShopifyProperty shopifyProperty = mapper.readValue(node.get(i).toString(), ShopifyProperty.class);
					shopifyProperties.add(shopifyProperty);
				}
			} else {
				ShopifyProperty shopifyProperty = mapper.readValue(node.toString(), ShopifyProperty.class);
				shopifyProperties.add(shopifyProperty);
			}

			return shopifyProperties;
		}
	}
}
