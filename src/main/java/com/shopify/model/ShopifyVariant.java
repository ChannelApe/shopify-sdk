package com.shopify.model;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.DateTimeAdapter;
import com.shopify.model.adapters.EscapedStringAdapter;
import com.shopify.model.adapters.InventoryPolicyAdapter;
import lombok.Data;
import org.joda.time.DateTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@JsonIgnoreProperties(ignoreUnknown =  true)
public class ShopifyVariant {

	private String id;
	@XmlElement(name = "product_id")
	private String productId;
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String title;
	private BigDecimal price;
	@XmlElement(name = "compare_at_price")
	private BigDecimal compareAtPrice;
	private String sku;
	private String barcode;
	private int position;
	private long grams;
	@XmlElement(name = "inventory_quantity")
	private Long inventoryQuantity;
	@XmlElement(name = "image_id")
	private String imageId;
	@XmlJavaTypeAdapter(InventoryPolicyAdapter.class)
	@XmlElement(name = "inventory_policy")
	private InventoryPolicy inventoryPolicy;
	@XmlElement(name = "inventory_management")
	private String inventoryManagement;
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String option1;
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String option2;
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String option3;
	@XmlElement(name = "fulfillment_service")
	private String fulfillmentService;
	@XmlElement(name = "requires_shipping")
	private boolean requiresShipping;
	private boolean taxable;
	@XmlElement(name = "tax_code")
	private String taxCode;

	private BigDecimal weight;
	@XmlElement(name = "weight_unit")
	private String weightUnit;

	@XmlElement(name = "inventory_item_id")
	private String inventoryItemId;

	@XmlTransient
	private long available;

	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	@XmlElement(name = "presentment_prices")
	private List<PresentmentPrice> presentmentPrices;
	@XmlElement(name = "admin_graphql_api_id")
	private String adminGraphqlApiId;

	@XmlElement(name = "old_inventory_quantity")
	private long oldInventoryQuantity;
}
