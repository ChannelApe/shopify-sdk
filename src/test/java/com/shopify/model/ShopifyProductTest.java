package com.shopify.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class ShopifyProductTest {

	@Test
	void givenSomeUnsortedOptionsWhenRetrievingSortedOptionNamesThenReturnSortedOptionNamesList() {
		final List<Option> options = Arrays.asList(buildOption(45, "Size"), buildOption(2, "Viscosity"),
				buildOption(84, "Flavor"));
		final ShopifyProduct shopifyProduct = new ShopifyProduct();
		shopifyProduct.setOptions(options);
		final List<String> actualSortedOptionNames = shopifyProduct.getSortedOptionNames();
		assertEquals(Arrays.asList("Viscosity", "Size", "Flavor"), actualSortedOptionNames);
	}

	private Option buildOption(final int position, final String name) {
		final Option option = new Option();
		option.setPosition(position);
		option.setName(name);
		return option;
	}

}
