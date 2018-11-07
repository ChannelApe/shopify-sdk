package com.shopify.model.adapters;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang3.StringUtils;

public class TagsAdapter extends XmlAdapter<String, Set<String>> {

	private static final String TAG_DELIMITTER = ", ";

	@Override
	public Set<String> unmarshal(final String tags) throws Exception {
		if (StringUtils.isBlank(tags)) {
			return Collections.emptySet();
		}
		final String[] tagArray = tags.split(TAG_DELIMITTER);
		return new HashSet<>(Arrays.asList(tagArray));
	}

	@Override
	public String marshal(final Set<String> tags) throws Exception {
		if ((tags == null) || tags.isEmpty()) {
			return null;
		}

		final StringBuilder tagStringBuilder = new StringBuilder();
		final Iterator<String> tagIterator = tags.iterator();
		while (tagIterator.hasNext()) {
			final String tag = tagIterator.next();
			tagStringBuilder.append(tag);
			if (tagIterator.hasNext()) {
				tagStringBuilder.append(TAG_DELIMITTER);
			}
		}
		return tagStringBuilder.toString();
	}

}
