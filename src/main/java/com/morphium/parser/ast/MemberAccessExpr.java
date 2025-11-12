package com.morphium.parser.ast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.morphium.runtime.Context;

public class MemberAccessExpr implements Expression {
    private final Expression object;
    private final Expression property;
    private final boolean safe;
    private final boolean computed;

    public MemberAccessExpr(Expression object, Expression property, boolean safe, boolean computed) {
        this.object = object;
        this.property = property;
        this.safe = safe;
        this.computed = computed;
    }

    @Override
    public JsonElement evaluate(Context context) {
        JsonElement obj = object.evaluate(context);

        if (obj == null || obj.isJsonNull()) {
            if (safe) return JsonNull.INSTANCE;
            throw new RuntimeException("Cannot access property of null");
        }

        String propName;
        if (computed) {
            JsonElement propElement = property.evaluate(context);
            if (propElement.isJsonPrimitive() && propElement.getAsJsonPrimitive().isNumber()) {
                int index = propElement.getAsInt();
                if (obj.isJsonArray()) {
                    JsonArray arr = obj.getAsJsonArray();
                    if (index >= 0 && index < arr.size()) {
                        return arr.get(index);
                    }
                    return safe ? JsonNull.INSTANCE : null;
                }
            }
            propName = propElement.isJsonPrimitive() ? propElement.getAsString() : propElement.toString();
        } else if (property instanceof IdentifierExpr) {
            propName = ((IdentifierExpr) property).getName();
        } else {
            propName = property.evaluate(context).getAsString();
        }

        if (obj.isJsonObject()) {
            JsonObject jsonObj = obj.getAsJsonObject();
            if (jsonObj.has(propName)) {
                return jsonObj.get(propName);
            }
        }

        return safe ? JsonNull.INSTANCE : null;
    }
}
