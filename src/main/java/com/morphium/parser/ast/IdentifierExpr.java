package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.runtime.Context;
import lombok.Getter;

@Getter
public class IdentifierExpr implements Expression {
    private final String name;

    public IdentifierExpr(String name) {
        this.name = name;
    }

    @Override
    public JsonNode evaluate(Context context) {
        return context.get(name);
    }

}
