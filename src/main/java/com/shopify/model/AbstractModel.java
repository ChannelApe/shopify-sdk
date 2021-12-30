package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;

public abstract class AbstractModel {

    @XmlElement(name = "admin_graphql_api_id")
    private String adminGraphqlApiId;

    public String getAdminGraphqlApiId() {
        return adminGraphqlApiId;
    }

    public void setAdminGraphqlApiId(final String adminGraphqlApiId) {
        this.adminGraphqlApiId = adminGraphqlApiId;
    }
}
