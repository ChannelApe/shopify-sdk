package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyPriceRule {
    @XmlElement(name = "allocation_method")
    private String allocationMethod;
    @XmlElement(name = "created_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime createdAt;
    @XmlElement(name = "updated_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime updatedAt;
    @XmlElement(name = "customer_selection")
    private String customerSelection;
    @XmlElement(name = "starts_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime startsAt;
    @XmlElement(name = "ends_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime endsAt;
    @XmlElement(name = "entitled_collection_ids")
    private List<String> entitledCollectionIds;
    @XmlElement(name = "entitled_country_ids")
    private List<String> entitledCountryIds;
    @XmlElement(name = "entitled_product_ids")
    private List<String> entitledProductIds;
    @XmlElement(name = "entitled_variant_ids")
    private List<String> entitledVariantIds;
    private String id;
    @XmlElement(name = "once_per_customer")
    private boolean oncePerCustomer;
    @XmlElement(name = "prerequisite_customer_ids")
    private List<String> prerequisiteCustomerIds;
    @XmlElement(name = "prerequisite_saved_search_ids")
    private List<String> prerequisiteSavedSearchIds;
    @XmlElement(name = "prerequisite_quantity_range")
    private Object prerequisiteQuantityRange;
    @XmlElement(name = "prerequisite_shipping_price_range")
    private Object prerequisiteShippingPriceRange;
    @XmlElement(name = "prerequisite_subtotal_range")
    private Object prerequisiteSubtotalRange;
    @XmlElement(name = "target_selection")
    private String targetSelection;
    @XmlElement(name = "target_type")
    private String targetType;
    @XmlElement(name = "title")
    private String title;
    @XmlElement(name = "usage_limit")
    private int usageLimit;
    @XmlElement(name = "prerequisite_product_ids")
    private List<String> prerequisiteProductIds;
    @XmlElement(name = "prerequisite_variant_ids")
    private List<String> prerequisiteVariantIds;
    @XmlElement(name = "prerequisite_collection_ids")
    private List<String> prerequisiteCollectionIds;
    private BigDecimal value;
    @XmlElement(name =  "value_type")
    private String valueType;
    @XmlElement(name = "prerequisite_to_entitlement_quantity_ratio")
    private Object prerequisiteToEntitlementQuantity_Ratio;
    @XmlElement(name = "allocation_limit")
    private int allocationLimit;

    public String getAllocationMethod() {
        return allocationMethod;
    }
    public DateTime getCreatedAt() {
        return createdAt;
    }
    public DateTime getUpdatedAt() {
        return updatedAt;
    }
    public String getCustomerSelection() {
        return customerSelection;
    }
    public DateTime getStartsAt() {
        return startsAt;
    }
    public DateTime getEndsAt() {
        return endsAt;
    }
    public List<String> getEntitledCollectionIds() {
        return entitledCollectionIds;
    }
    public List<String> getEntitledCountryIds() {
        return entitledCountryIds;
    }
    public List<String> getEntitledProductIds() {
        return entitledProductIds;
    }
    public List<String> getEntitledVariantIds() {
        return entitledVariantIds;
    }
    public String getId() {
        return id;
    }
    public boolean getOncePerCustomer() {
        return oncePerCustomer;
    }
    public List<String> getPrerequisiteCustomerIds() {
        return prerequisiteCustomerIds;
    }
    public List<String> getPrerequisiteSavedSearchIds() {
        return prerequisiteSavedSearchIds;
    }
    public Object getPrerequisiteQuantityRange() {
        return prerequisiteQuantityRange;
    }
    public Object getPrerequisiteShippingPriceRange() {
        return prerequisiteShippingPriceRange;
    }
    public Object getPrerequisiteSubtotalRange() {
        return prerequisiteSubtotalRange;
    }
    public String getTargetSelection() {
        return targetSelection;
    }
    public String getTargetType() {
        return targetType;
    }
    public String getTitle() {
        return title;
    }
    public int getUsageLimit() {
        return usageLimit;
    }
    public List<String> getPrerequisiteProductIds() {
        return prerequisiteProductIds;
    }
    public List<String> getPrerequisiteVariantIds() {
        return prerequisiteVariantIds;
    }
    public List<String> getPrerequisiteCollectionIds() {
        return prerequisiteCollectionIds;
    }
    public BigDecimal getValue() {
        return value;
    }
    public String getValueType() {
        return valueType;
    }
    public Object getPrerequisiteToEntitlementQuantity_Ratio() {
        return prerequisiteToEntitlementQuantity_Ratio;
    }
    public int getAllocationLimit() {
        return allocationLimit;
    }
    public void setAllocationMethod(final String allocationMethod) {
        this.allocationMethod = allocationMethod;
    }
    public void setCreatedAt(final DateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(final DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public void setCustomerSelection(final String customerSelection) {
        this.customerSelection = customerSelection;
    }
    public void setStartsAt(final DateTime startsAt) {
        this.startsAt = startsAt;
    }
    public void setEndsAt(final DateTime endsAt) {
        this.endsAt = endsAt;
    }
    public void setEntitledCollectionIds(final List<String> entitledCollectionIds) {
        this.entitledCollectionIds = entitledCollectionIds;
    }
    public void setEntitledCountryIds(final List<String> entitledCountryIds) {
        this.entitledCountryIds = entitledCountryIds;
    }
    public void setEntitledProductIds(final List<String> entitledProductIds) {
        this.entitledProductIds = entitledProductIds;
    }
    public void setEntitledVariantIds(final List<String> entitledVariantIds) {
        this.entitledVariantIds = entitledVariantIds;
    }
    public void setId(final String id) {
        this.id = id;
    }
    public void setOncePerCustomer(final boolean oncePerCustomer) {
        this.oncePerCustomer = oncePerCustomer;
    }
    public void setPrerequisiteCustomerIds(final List<String> prerequisiteCustomerIds) {
        this.prerequisiteCustomerIds = prerequisiteCustomerIds;
    }
    public void setPrerequisiteSavedSearchIds(final List<String> prerequisiteSavedSearchIds) {
        this.prerequisiteSavedSearchIds = prerequisiteSavedSearchIds;
    }
    public void setPrerequisiteQuantityRange(final Object prerequisiteQuantityRange) {
        this.prerequisiteQuantityRange = prerequisiteQuantityRange;
    }
    public void setPrerequisiteShippingPriceRange(final Object prerequisiteShippingPriceRange) {
        this.prerequisiteShippingPriceRange = prerequisiteShippingPriceRange;
    }
    public void setPrerequisiteSubtotalRange(final Object prerequisiteSubtotalRange) {
        this.prerequisiteSubtotalRange = prerequisiteSubtotalRange;
    }
    public void setTargetSelection(final String targetSelection) {
        this.targetSelection = targetSelection;
    }
    public void setTargetType(final String targetType) {
        this.targetType = targetType;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public void setUsageLimit(final int usageLimit) {
        this.usageLimit = usageLimit;
    }
    public void setPrerequisiteProductIds(final List<String> prerequisiteProductIds) {
        this.prerequisiteProductIds = prerequisiteProductIds;
    }
    public void setPrerequisiteVariantIds(final List<String> prerequisiteVariantIds) {
        this.prerequisiteVariantIds = prerequisiteVariantIds;
    }
    public void setPrerequisiteCollectionIds(final List<String> prerequisiteCollectionIds) {
        this.prerequisiteCollectionIds = prerequisiteCollectionIds;
    }
    public void setValue(final BigDecimal value) {
        this.value = value;
    }
    public void setValueType(final String valueType) {
        this.valueType = valueType;
    }
    public void setPrerequisiteToEntitlementQuantity_Ratio(final Object prerequisiteToEntitlementQuantity_Ratio) {
        this.prerequisiteToEntitlementQuantity_Ratio = prerequisiteToEntitlementQuantity_Ratio;
    }
    public void setAllocationLimit(final int allocationLimit) {
        this.allocationLimit = allocationLimit;
    }
}
