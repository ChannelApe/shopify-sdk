package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.joda.time.DateTime;

@XmlRootElement
public class ShopifyTheme {
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	public int id;
	public String name;
	public boolean previewable;
	public boolean processing;
	public String role;
	public String src;
	public int themeStoreId;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	private String adminGraphqlApiId;

}
