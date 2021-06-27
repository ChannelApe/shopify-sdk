package com.shopify.model.adapters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TagsAdapterTest {

	private static final Set<String> TAGS = new HashSet<>(Arrays.asList("tag1", "tag2", "tag3"));
	private static final String TAGS_STRING = "tag1, tag2, tag3";

	private TagsAdapter tagsAdapter;

	@Before
	public void setUp() {
		tagsAdapter = new TagsAdapter();
	}

	@Test
	public void giveSomeTagsWhenMarshallingThenReturnMarshalledString() throws Exception {
		assertEquals(TAGS_STRING, tagsAdapter.marshal(TAGS));
	}

	@Test
	public void givenSomeStringWhenUnmarshallingThenReturnTags() throws Exception {
		assertEquals(TAGS, tagsAdapter.unmarshal(TAGS_STRING));
	}

	@Test
	public void givenSomeEmptyTagsWhenMarshallingThenReturnNullString() throws Exception {
		assertEquals(null, tagsAdapter.marshal(Collections.emptySet()));
	}

	@Test
	public void givenNullTagsWhenMarshallingThenReturnNullString() throws Exception {
		assertNull(tagsAdapter.marshal(null));
	}

	@Test
	public void givenNullStringWhenUnmarshallingThenReturnEmptyTags() throws Exception {
		assertTrue(tagsAdapter.unmarshal(null).isEmpty());
	}

	@Test
	public void givenEmptyStringWhenUnmarshallingThenReturnEmptyTags() throws Exception {
		assertTrue(tagsAdapter.unmarshal("").isEmpty());
	}

}
