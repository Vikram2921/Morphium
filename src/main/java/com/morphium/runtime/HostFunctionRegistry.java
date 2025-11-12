package com.morphium.runtime;

import com.google.gson.JsonElement;
import com.morphium.core.MorphiumEngine.HostFunction;

import java.util.HashMap;
import java.util.Map;

public class HostFunctionRegistry {
    private final Map<String, HostFunction> functions = new HashMap<>();

    public void register(String namespace, String name, HostFunction function) {
        String fullName = namespace.isEmpty() ? name : namespace + "." + name;
        functions.put(fullName, function);
    }

    public void register(String name, HostFunction function) {
        register("", name, function);
    }

    public JsonElement call(String name, JsonElement[] args) {
        HostFunction function = functions.get(name);
        if (function != null) {
            return function.call(args);
        }
        return null;
    }

    public boolean has(String name) {
        return functions.containsKey(name);
    }
}
