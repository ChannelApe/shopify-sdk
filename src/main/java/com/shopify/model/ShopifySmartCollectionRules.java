package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifySmartCollectionRules {
    private String column;
    private String relation;
    private String condition;

    public String getColumn() {
        return column;
    }
    public void setColumn(final String column) {
        this.column = column;
    }
    public String getRelation() {
        return relation;
    }
    public void setRelation(final String relation) {
        this.relation = relation;
    }
    public String getCondition() {
        return condition;
    }
    public void setCondition(final String condition) {
        this.condition = condition;
    }
}
