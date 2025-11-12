package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.morphium.runtime.Context;
import com.morphium.util.JsonUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectExpr implements Expression {
    private final Map<String, Expression> properties;
    private final Map<Expression, Expression> computedProperties;

    public ObjectExpr() {
        this.properties = new LinkedHashMap<>();
        this.computedProperties = new LinkedHashMap<>();
    }

    public void addProperty(String key, Expression value) {
        properties.put(key, value);
    }

    public void addComputedProperty(Expression keyExpr, Expression valueExpr) {
        computedProperties.put(keyExpr, valueExpr);
    }

    @Override
    public JsonNode evaluate(Context context) {
        ObjectNode result = JsonUtil.createObject();

        for (Map.Entry<String, Expression> entry : properties.entrySet()) {
            JsonNode value = entry.getValue().evaluate(context);
            result.set(entry.getKey(), value);
        }

        for (Map.Entry<Expression, Expression> entry : computedProperties.entrySet()) {
            JsonNode keyElement = entry.getKey().evaluate(context);
            String key = keyElement.isValueNode() ? keyElement.asText() : keyElement.toString();
            JsonNode value = entry.getValue().evaluate(context);
            result.set(key, value);
        }

        return result;
    }
}
