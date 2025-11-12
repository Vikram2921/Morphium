package com.morphium.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode createPrimitive(Object value) {
        if (value == null) {
            return NullNode.getInstance();
        }
        if (value instanceof Boolean) {
            return BooleanNode.valueOf((Boolean) value);
        }
        if (value instanceof Integer || value instanceof Long) {
            return IntNode.valueOf(((Number) value).intValue());
        }
        if (value instanceof Number) {
            return DoubleNode.valueOf(((Number) value).doubleValue());
        }
        if (value instanceof String) {
            return TextNode.valueOf((String) value);
        }
        throw new IllegalArgumentException("Unsupported type: " + value.getClass());
    }

    public static ObjectNode createObject() {
        return mapper.createObjectNode();
    }

    public static ArrayNode createArray() {
        return mapper.createArrayNode();
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
