package com.shopify.mappers;

import com.shopify.model.TransactionErrorCode;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TransactionErrorCodeAdapter extends XmlAdapter<String, TransactionErrorCode> {

    @Override
    public TransactionErrorCode unmarshal(String s) throws Exception {
        return TransactionErrorCode.toEnum(s);
    }

    @Override
    public String marshal(TransactionErrorCode transactionErrorCode) throws Exception {
        return transactionErrorCode.toString();
    }
}
