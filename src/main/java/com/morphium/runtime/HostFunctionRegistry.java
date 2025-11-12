package com.morphium.runtime;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.core.MorphiumEngine.HostFunction;

import java.util.HashMap;
import java.util.Map;

public class HostFunctionRegistry {
    private final Map<String, HostFunction> functions = new HashMap<>();
    private Logger logger;

    public void register(String namespace, String name, HostFunction function) {
        String fullName = namespace.isEmpty() ? name : namespace + "." + name;
        functions.put(fullName, function);
    }

    public void register(String name, HostFunction function) {
        register("", name, function);
    }

    public JsonNode call(String name, JsonNode[] args) {
        HostFunction function = functions.get(name);
        if (function != null) {
            return function.call(args);
        }
        return null;
    }

    public boolean has(String name) {
        return functions.containsKey(name);
    }
    
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    
    public Logger getLogger() {
        return logger;
    }
}
