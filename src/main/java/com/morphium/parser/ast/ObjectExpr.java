package com.morphium.parser.ast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.morphium.runtime.Context;

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
    public JsonElement evaluate(Context context) {
        JsonObject result = new JsonObject();

        for (Map.Entry<String, Expression> entry : properties.entrySet()) {
            JsonElement value = entry.getValue().evaluate(context);
            result.add(entry.getKey(), value);
        }

        for (Map.Entry<Expression, Expression> entry : computedProperties.entrySet()) {
            JsonElement keyElement = entry.getKey().evaluate(context);
            String key = keyElement.isJsonPrimitive() ? keyElement.getAsString() : keyElement.toString();
            JsonElement value = entry.getValue().evaluate(context);
            result.add(key, value);
        }

        return result;
    }
}
