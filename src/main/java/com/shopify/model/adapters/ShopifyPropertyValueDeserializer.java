package com.shopify.model.adapters;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ShopifyPropertyValueDeserializer extends JsonDeserializer<Object> {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonNode node = p.getCodec().readTree(p);
		if (node.isNull()) {
			return null;
		} else if (node.isTextual()) {
			return node.textValue();
		} else if (node.isObject()) {
			// Convert the JSON object to a string
			return objectMapper.writeValueAsString(node);
		} else {
			// Other JSON types (arrays, primitives) can be treated as strings
			return node.toString();
		}
	}
}