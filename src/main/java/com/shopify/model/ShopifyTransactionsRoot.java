package com.shopify.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

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
