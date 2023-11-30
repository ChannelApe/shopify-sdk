package com.shopify.model.adapters;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ShopifyPropertyValueSerializer extends JsonSerializer<Object> {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (value == null) {
			gen.writeNull();
		} else {
			if (value instanceof String) {
				gen.writeString((String) value);
			} else {
				JsonNode node = objectMapper.valueToTree(value);
				if (node instanceof ObjectNode) {
					// Convert the complex object to a JSON string
					String jsonString = objectMapper.writeValueAsString(node);
					gen.writeString(jsonString);
				} else {
					// Other JSON types (arrays, primitives) can be treated as strings
					gen.writeString(node.toString());
				}
			}
		}
	}
}