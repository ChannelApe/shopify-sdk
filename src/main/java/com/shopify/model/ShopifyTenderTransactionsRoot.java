package com.shopify.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ShopifyTenderTransactionsRoot {

	@XmlElement(name = "tender_transactions")
	private List<ShopifyTenderTransaction> tenderTransactions = new LinkedList<>();

	public List<ShopifyTenderTransaction> getTenderTransactions() {
		return tenderTransactions;
	}

	public void setTenderTransactions(final List<ShopifyTenderTransaction> tenderTransactions) {
		this.tenderTransactions = tenderTransactions;
	}
}
