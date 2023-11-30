package com.shopify.model;

import java.util.LinkedList;
import java.util.List;

import com.shopify.model.adapters.EscapedStringAdapter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Image {

	private String id;
	@XmlElement(name = "product_id")
	private String productId;
	@XmlJavaTypeAdapter(EscapedStringAdapter.class)
	private String name;
	private int position;
	@XmlElement(name = "src")
	private String source;
	@XmlElement(name = "variant_ids")
	private List<String> variantIds = new LinkedList<>();
	private List<Metafield> metafields = new LinkedList<>();

}
