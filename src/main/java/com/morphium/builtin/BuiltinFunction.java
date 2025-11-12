package com.morphium.builtin;

import com.google.gson.JsonElement;
import com.morphium.parser.ast.Expression;
import com.morphium.runtime.Context;

import java.util.List;

@FunctionalInterface
public interface BuiltinFunction {
    JsonElement call(List<Expression> args, Context context);
}
