package com.shopify.model.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TagsAdapterTest {

	private static final Set<String> TAGS = new HashSet<>(Arrays.asList("tag1", "tag2", "tag3"));
	private static final String TAGS_STRING = "tag1, tag2, tag3";

	private TagsAdapter tagsAdapter;

	@BeforeEach
	void setUp() {
		tagsAdapter = new TagsAdapter();
	}

	@Test
	void giveSomeTagsWhenMarshallingThenReturnMarshalledString() throws Exception {
		assertEquals(TAGS_STRING, tagsAdapter.marshal(TAGS));
	}

	@Test
	void givenSomeStringWhenUnmarshallingThenReturnTags() throws Exception {
		assertEquals(TAGS, tagsAdapter.unmarshal(TAGS_STRING));
	}

	@Test
	void givenSomeEmptyTagsWhenMarshallingThenReturnNullString() throws Exception {
		assertNull(tagsAdapter.marshal(Collections.emptySet()));
	}

	@Test
	void givenNullTagsWhenMarshallingThenReturnNullString() throws Exception {
		assertNull(tagsAdapter.marshal(null));
	}

	@Test
	void givenNullStringWhenUnmarshallingThenReturnEmptyTags() throws Exception {
		assertTrue(tagsAdapter.unmarshal(null).isEmpty());
	}

	@Test
	void givenEmptyStringWhenUnmarshallingThenReturnEmptyTags() throws Exception {
		assertTrue(tagsAdapter.unmarshal("").isEmpty());
	}

}
