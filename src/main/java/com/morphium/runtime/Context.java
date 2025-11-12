package com.morphium.runtime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.morphium.builtin.BuiltinFunctions;
import com.morphium.parser.ast.Expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private final Context parent;
    private final Map<String, JsonNode> variables;
    private final Map<String, UserFunction> userFunctions;
    private final Map<String, JsonNode> exports;
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

    public void define(String name, JsonNode value) {
        variables.put(name, value);
    }

    public void defineGlobal(String name, JsonNode value) {
        if (isGlobal) {
            variables.put(name, value);
        } else if (parent != null) {
            parent.defineGlobal(name, value);
        }
    }

    public JsonNode get(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        if (parent != null) {
            return parent.get(name);
        }
        return NullNode.getInstance();
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

    public void export(String name, JsonNode value) {
        exports.put(name, value);
    }

    public JsonNode getExport(String name) {
        return exports.get(name);
    }

    public Map<String, JsonNode> getAllExports() {
        return new HashMap<>(exports);
    }

    public JsonNode callFunction(String name, List<Expression> argExprs) {
        // Check user-defined functions first
        UserFunction userFunc = getFunction(name);
        if (userFunc != null) {
            return callUserFunction(userFunc, argExprs);
        }

        // Check built-in functions (pass unevaluated expressions)
        JsonNode result = BuiltinFunctions.call(name, argExprs, this);
        if (result != null) {
            return result;
        }

        // For host functions, evaluate arguments first
        if (functionRegistry != null) {
            JsonNode[] args = new JsonNode[argExprs.size()];
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

    private JsonNode callUserFunction(UserFunction func, List<Expression> argExprs) {
        // Evaluate arguments
        List<JsonNode> argValues = new java.util.ArrayList<>();
        for (Expression argExpr : argExprs) {
            argValues.add(argExpr.evaluate(this));
        }

        // Create new context for function execution
        Context funcContext = new Context(this);

        // Bind parameters
        List<String> params = func.getParameters();
        for (int i = 0; i < params.size(); i++) {
            JsonNode value = i < argValues.size() ? argValues.get(i) : NullNode.getInstance();
            funcContext.define(params.get(i), value);
        }

        // Execute function body
        return func.getBody().evaluate(funcContext);
    }

    public HostFunctionRegistry getFunctionRegistry() {
        return functionRegistry;
    }
}
