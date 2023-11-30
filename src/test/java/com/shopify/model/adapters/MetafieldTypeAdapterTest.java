package com.shopify.model.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shopify.model.MetafieldType;

class MetafieldTypeAdapterTest {

	private MetafieldTypeAdapter metafieldTypeAdapter;

	@BeforeEach
	void setUp() {
		metafieldTypeAdapter = new MetafieldTypeAdapter();
	}

	@Test
	void giveSomeMetafieldSingleLineTextTypeWhenMarshallingThenReturnMarshalledSingleLineTextField()
			throws Exception {
		final String actualMarshalledString = metafieldTypeAdapter.marshal(MetafieldType.SINGLE_LINE_TEXT);
		assertEquals(MetafieldType.SINGLE_LINE_TEXT.toString(), actualMarshalledString);
	}

	@Test
	void givenSomeStringWhenUnmarshallingThenReturnSingleLineTextFieldMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter.unmarshal("string");
		assertEquals(MetafieldType.SINGLE_LINE_TEXT, actualMetafieldType);
	}

	@Test
	void givenSomeStringWhenUnmarshallingThenReturnMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter
				.unmarshal(MetafieldType.NUMBER_INTEGER.toString());
		assertEquals(MetafieldType.NUMBER_INTEGER, actualMetafieldType);
	}

	@Test
	void givenSomeIntegerTypeWhenUnmarshallingThenReturnNumberIntegerFieldMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter
				.unmarshal(MetafieldType.MULTI_LINE_TEXT.toString());
		assertEquals(MetafieldType.MULTI_LINE_TEXT, actualMetafieldType);
	}

	@Test
	void givenSomePageReferenceTypeWhenUnmarshallingThenReturnPageReferenceMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter
				.unmarshal(MetafieldType.PAGE_REFERENCE.toString());
		assertEquals(MetafieldType.PAGE_REFERENCE, actualMetafieldType);
	}

	@Test
	void givenSomeProductReferenceTypeWhenUnmarshallingThenReturnProductReferenceMetafieldType()
			throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter
				.unmarshal(MetafieldType.PRODUCT_REFERENCE.toString());
		assertEquals(MetafieldType.PRODUCT_REFERENCE, actualMetafieldType);
	}

	@Test
	void givenFileReferenceTypeWhenUnmarshallingThenReturnFileReferenceMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter
				.unmarshal(MetafieldType.FILE_REFERENCE.toString());
		assertEquals(MetafieldType.FILE_REFERENCE, actualMetafieldType);
	}

	@Test
	void givenSomeDecimalTypeWhenUnmarshallingThenReturnDecimalMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter.unmarshal(MetafieldType.DECIMAL.toString());
		assertEquals(MetafieldType.DECIMAL, actualMetafieldType);
	}

	@Test
	void givenSomeDateTypeWhenUnmarshallingThenReturnDateMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter.unmarshal(MetafieldType.DATE.toString());
		assertEquals(MetafieldType.DATE, actualMetafieldType);
	}

	@Test
	void givenSomeDateAndTimeTypeWhenUnmarshallingThenReturnDateAndTimeMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter
				.unmarshal(MetafieldType.DATE_AND_TIME.toString());
		assertEquals(MetafieldType.DATE_AND_TIME, actualMetafieldType);
	}

	@Test
	void givenSomeUrlTypeWhenUnmarshallingThenReturnUrlMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter.unmarshal(MetafieldType.URL.toString());
		assertEquals(MetafieldType.URL, actualMetafieldType);
	}

	@Test
	void givenSomeJsonStringTypeWhenUnmarshallingThenReturnJsonStringFieldMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter.unmarshal(MetafieldType.JSON_STRING.toString());
		assertEquals(MetafieldType.JSON_STRING, actualMetafieldType);
	}

	@Test
	void givenSomeBooleanTypeWhenUnmarshallingThenReturnBooleanMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter.unmarshal(MetafieldType.BOOLEAN.toString());
		assertEquals(MetafieldType.BOOLEAN, actualMetafieldType);
	}

	@Test
	void givenSomeWeightTypeWhenUnmarshallingThenReturnWeightMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter.unmarshal(MetafieldType.WEIGHT.toString());
		assertEquals(MetafieldType.WEIGHT, actualMetafieldType);
	}

	@Test
	void givenSomeVolumeTypeWhenUnmarshallingThenReturnVolumeMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter.unmarshal(MetafieldType.VOLUME.toString());
		assertEquals(MetafieldType.VOLUME, actualMetafieldType);
	}

	@Test
	void givenSomeDimensionTypeWhenUnmarshallingThenReturnDimensionMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter.unmarshal(MetafieldType.DIMENSION.toString());
		assertEquals(MetafieldType.DIMENSION, actualMetafieldType);
	}

	@Test
	void givenSomeRatingTypeWhenUnmarshallingThenReturnRatingMetafieldType() throws Exception {
		final MetafieldType actualMetafieldType = metafieldTypeAdapter.unmarshal(MetafieldType.RATING.toString());
		assertEquals(MetafieldType.RATING, actualMetafieldType);
	}

}
