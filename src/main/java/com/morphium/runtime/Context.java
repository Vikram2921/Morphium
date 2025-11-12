package com.morphium.runtime;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.morphium.builtin.BuiltinFunctions;
import com.morphium.parser.ast.Expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private final Context parent;
    private final Map<String, JsonElement> variables;
    private final HostFunctionRegistry functionRegistry;

    public Context(HostFunctionRegistry functionRegistry) {
        this.parent = null;
        this.variables = new HashMap<>();
        this.functionRegistry = functionRegistry;
    }

    public Context(Context parent) {
        this.parent = parent;
        this.variables = new HashMap<>();
        this.functionRegistry = parent.functionRegistry;
    }

    public void define(String name, JsonElement value) {
        variables.put(name, value);
    }

    public JsonElement get(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        if (parent != null) {
            return parent.get(name);
        }
        return JsonNull.INSTANCE;
    }

    public JsonElement callFunction(String name, List<Expression> argExprs) {
        // Check built-in functions first (pass unevaluated expressions)
        JsonElement result = BuiltinFunctions.call(name, argExprs, this);
        if (result != null) {
            return result;
        }

        // For host functions, evaluate arguments first
        if (functionRegistry != null) {
            JsonElement[] args = new JsonElement[argExprs.size()];
            for (int i = 0; i < argExprs.size(); i++) {
                args[i] = argExprs.get(i).evaluate(this);
            }
            result = functionRegistry.call(name, args);
            if (result != null) {
                return result;
            }
        }

        throw new RuntimeException("Unknown function: " + name);
    }

    public HostFunctionRegistry getFunctionRegistry() {
        return functionRegistry;
    }
}
