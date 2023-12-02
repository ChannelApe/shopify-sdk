package com.shopify.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class ImageAltTextCreationRequestTest {

	@Test
	void givenSomeImageAltTextWhenBuildingMetafieldsThenExpectCorrectMetafields() {
		final String expectedImageAltText = "banana";
		final List<Metafield> actualMetafields = ImageAltTextCreationRequest.newBuilder()
				.withImageAltText(expectedImageAltText).build();
		assertEquals(1, actualMetafields.size());
		final Metafield actualFirstMetafield = actualMetafields.get(0);
		assertEquals(ImageAltTextCreationRequest.KEY, actualFirstMetafield.getKey());
		assertEquals(ImageAltTextCreationRequest.NAMESPACE, actualFirstMetafield.getNamespace());
		assertEquals(ImageAltTextCreationRequest.TYPE, actualFirstMetafield.getType());
		assertEquals(expectedImageAltText, actualFirstMetafield.getValue());
	}

}
