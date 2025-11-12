package com.morphium.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.parser.ast.Expression;
import com.morphium.runtime.Context;

import java.util.List;

@FunctionalInterface
public interface BuiltinFunction {
    JsonNode call(List<Expression> args, Context context);
}
