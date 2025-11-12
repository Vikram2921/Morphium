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
    private final Map<String, UserFunction> userFunctions;
    private final Map<String, JsonElement> exports;
    private final HostFunctionRegistry functionRegistry;
    private final boolean isGlobal;

    public Context(HostFunctionRegistry functionRegistry) {
        this.parent = null;
        this.variables = new HashMap<>();
        this.userFunctions = new HashMap<>();
        this.exports = new HashMap<>();
        this.functionRegistry = functionRegistry;
        this.isGlobal = true;
    }

    public Context(Context parent) {
        this.parent = parent;
        this.variables = new HashMap<>();
        this.userFunctions = new HashMap<>();
        this.exports = parent.exports; // Share exports with parent
        this.functionRegistry = parent.functionRegistry;
        this.isGlobal = false;
    }

    public void define(String name, JsonElement value) {
        variables.put(name, value);
    }

    public void defineGlobal(String name, JsonElement value) {
        if (isGlobal) {
            variables.put(name, value);
        } else if (parent != null) {
            parent.defineGlobal(name, value);
        }
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

    public void defineFunction(String name, List<String> parameters, Expression body) {
        UserFunction func = new UserFunction(name, parameters, body);
        if (isGlobal) {
            userFunctions.put(name, func);
        } else {
            // Local functions go in current scope
            userFunctions.put(name, func);
        }
    }

    public UserFunction getFunction(String name) {
        if (userFunctions.containsKey(name)) {
            return userFunctions.get(name);
        }
        if (parent != null) {
            return parent.getFunction(name);
        }
        return null;
    }

    public void export(String name, JsonElement value) {
        exports.put(name, value);
    }

    public JsonElement getExport(String name) {
        return exports.get(name);
    }

    public Map<String, JsonElement> getAllExports() {
        return new HashMap<>(exports);
    }

    public JsonElement callFunction(String name, List<Expression> argExprs) {
        // Check user-defined functions first
        UserFunction userFunc = getFunction(name);
        if (userFunc != null) {
            return callUserFunction(userFunc, argExprs);
        }

        // Check built-in functions (pass unevaluated expressions)
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

    private JsonElement callUserFunction(UserFunction func, List<Expression> argExprs) {
        // Evaluate arguments
        List<JsonElement> argValues = new java.util.ArrayList<>();
        for (Expression argExpr : argExprs) {
            argValues.add(argExpr.evaluate(this));
        }

        // Create new context for function execution
        Context funcContext = new Context(this);

        // Bind parameters
        List<String> params = func.getParameters();
        for (int i = 0; i < params.size(); i++) {
            JsonElement value = i < argValues.size() ? argValues.get(i) : JsonNull.INSTANCE;
            funcContext.define(params.get(i), value);
        }

        // Execute function body
        return func.getBody().evaluate(funcContext);
    }

    public HostFunctionRegistry getFunctionRegistry() {
        return functionRegistry;
    }
}
