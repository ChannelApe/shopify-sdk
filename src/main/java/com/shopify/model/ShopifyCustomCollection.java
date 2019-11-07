package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyCustomCollection {

    private String id;
    private String title;
    private String handle;

    @XmlElement(name = "body_html")
    private String bodyHtml;

    @XmlElement(name = "published_scope")
    private String publishedScope;

    @XmlElement(name = "sort_order")
    private String sortOrder;

    @XmlElement(name = "template_suffix")
    private String templateSuffix;

    @XmlElement(name = "published_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime publishedAt;

    @XmlElement(name = "updated_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime updatedAt;

    @XmlElement(name = "admin_graphql_api_id")
    private String adminGraphqlApiId;


    public String getAdminGraphqlApiId() {
        return adminGraphqlApiId;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public String getHandle() {
        return handle;
    }

    public String getId() {
        return id;
    }

    public DateTime getPublishedAt() {
        return publishedAt;
    }

    public String getPublishedScope() {
        return publishedScope;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public String getTemplateSuffix() {
        return templateSuffix;
    }

    public String getTitle() {
        return title;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public static class Builder {

        private String adminGraphqlApiId;
        private String bodyHtml;
        private String handle;
        private String id;
        private DateTime publishedAt;
        private String publishedScope;
        private String sortOrder;
        private String templateSuffix;
        private String title;
        private DateTime updatedAt;

        public ShopifyCustomCollection .Builder withAdminGraphqlApiId(String adminGraphqlApiId) {
            this.adminGraphqlApiId = adminGraphqlApiId;
            return this;
        }

        public ShopifyCustomCollection .Builder withBodyHtml(String bodyHtml) {
            this.bodyHtml = bodyHtml;
            return this;
        }

        public ShopifyCustomCollection .Builder withHandle(String handle) {
            this.handle = handle;
            return this;
        }

        public ShopifyCustomCollection .Builder withId(String id) {
            this.id = id;
            return this;
        }

        public ShopifyCustomCollection .Builder withPublishedAt(DateTime publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public ShopifyCustomCollection .Builder withPublishedScope(String publishedScope) {
            this.publishedScope = publishedScope;
            return this;
        }

        public ShopifyCustomCollection .Builder withSortOrder(String sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public ShopifyCustomCollection .Builder withTemplateSuffix(String templateSuffix) {
            this.templateSuffix = templateSuffix;
            return this;
        }

        public ShopifyCustomCollection .Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ShopifyCustomCollection .Builder withUpdatedAt(DateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ShopifyCustomCollection build() {
            ShopifyCustomCollection shopifyCustomCollection = new ShopifyCustomCollection();
            shopifyCustomCollection.adminGraphqlApiId = adminGraphqlApiId;
            shopifyCustomCollection.bodyHtml = bodyHtml;
            shopifyCustomCollection.handle = handle;
            shopifyCustomCollection.id = id;
            shopifyCustomCollection.publishedAt = publishedAt;
            shopifyCustomCollection.publishedScope = publishedScope;
            shopifyCustomCollection.sortOrder = sortOrder;
            shopifyCustomCollection.templateSuffix = templateSuffix;
            shopifyCustomCollection.title = title;
            shopifyCustomCollection.updatedAt = updatedAt;
            return shopifyCustomCollection;
        }

    }

}
