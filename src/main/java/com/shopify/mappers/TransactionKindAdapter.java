package com.shopify.mappers;

import com.shopify.model.TransactionKind;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TransactionKindAdapter extends XmlAdapter<String, TransactionKind> {

    @Override
    public TransactionKind unmarshal(String s) throws Exception {
        return TransactionKind.toEnum(s);
    }

    @Override
    public String marshal(TransactionKind transactionKind) throws Exception {
        return transactionKind.toString();
    }
}
