package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.morphium.runtime.Context;
import lombok.Getter;

@Getter
public class GlobalVarStatement implements Expression {
    private final String name;
    private final Expression value;

    public GlobalVarStatement(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public JsonNode evaluate(Context context) {
        JsonNode val = value.evaluate(context);
        context.defineGlobal(name, val);
        return NullNode.getInstance();
    }

}
