package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.DateTimeAdapter;
import lombok.Data;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyEvent {

    @XmlElement(name = "created_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime createdAt;

    @XmlElement(name = "subject_id")
    private String subjectId;

    @XmlElement(name = "subject_type")
    private String subjectType;

    @XmlElement(name = "verb")
    private String verb;
}