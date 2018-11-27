package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopifyTransactionsRoot {

	private List<ShopifyTransaction> transactions = new LinkedList<>();

	public List<ShopifyTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(final List<ShopifyTransaction> transactions) {
		this.transactions = transactions;
	}
}
