package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.runtime.Context;
import lombok.Getter;

@Getter
public class ExportStatement implements Expression {
    private final String name;
    private final Expression value;

    public ExportStatement(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public JsonNode evaluate(Context context) {
        JsonNode result = value.evaluate(context);
        context.export(name, result);
        return result;
    }

}
