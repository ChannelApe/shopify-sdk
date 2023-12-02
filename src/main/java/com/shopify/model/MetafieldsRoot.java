package com.shopify.model;

import java.util.LinkedList;
import java.util.List;
import lombok.Data;

@Data
public class MetafieldsRoot {

	private List<Metafield> metafields = new LinkedList<>();

}
