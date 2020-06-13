package com.shopify.model;

public class ShopifySmartCollectionRule {
	private String column;
	private String relation;
	private String condition;

	public String getColumn() {
		return column;
	}

	public ShopifySmartCollectionRule setColumn(String column) {
		this.column = column;
		return this;
	}

	public String getRelation() {
		return relation;
	}

	public ShopifySmartCollectionRule setRelation(String relation) {
		this.relation = relation;
		return this;
	}

	public String getCondition() {
		return condition;
	}

	public ShopifySmartCollectionRule setCondition(String condition) {
		this.condition = condition;
		return this;
	}
}
