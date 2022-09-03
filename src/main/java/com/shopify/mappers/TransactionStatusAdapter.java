package com.shopify.mappers;

import com.shopify.model.TransactionStatus;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TransactionStatusAdapter extends XmlAdapter<String, TransactionStatus> {

    @Override
    public TransactionStatus unmarshal(String s) throws Exception {
        return TransactionStatus.toEnum(s);
    }

    @Override
    public String marshal(TransactionStatus transactionStatus) throws Exception {
        return transactionStatus.toString();
    }
}
