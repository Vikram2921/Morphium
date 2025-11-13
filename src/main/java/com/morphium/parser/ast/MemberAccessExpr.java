package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.node.*;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    public JsonNode evaluate(Context context) {
        JsonNode obj = object.evaluate(context);

        if (obj == null || obj.isNull()) {
            if (safe) return NullNode.getInstance();
            throw new RuntimeException("Cannot access property of null");
        }

        String propName;
        if (computed) {
            JsonNode propElement = property.evaluate(context);
            if (propElement.isValueNode() && propElement.isNumber()) {
                int index = propElement.asInt();
                if (obj.isArray()) {
                    ArrayNode arr = (ArrayNode) obj;
                    if (index >= 0 && index < arr.size()) {
                        return arr.get(index);
                    }
                    return safe ? NullNode.getInstance() : null;
                }
            }
            propName = propElement.isValueNode() ? propElement.asText() : propElement.toString();
        } else if (property instanceof IdentifierExpr) {
            propName = ((IdentifierExpr) property).getName();
        } else {
            propName = property.evaluate(context).asText();
        }

        if (obj.isObject()) {
            ObjectNode jsonObj = (ObjectNode) obj;
            if (jsonObj.has(propName)) {
                return jsonObj.get(propName);
            }
        }

        return safe ? NullNode.getInstance() : null;
    }

    public Expression getObject() {
        return object;
    }

    public Expression getProperty() {
        return property;
    }

}
