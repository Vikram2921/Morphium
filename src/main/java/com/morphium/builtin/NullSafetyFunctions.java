package com.morphium.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.morphium.util.JsonUtil;
import com.morphium.runtime.Context;

import java.util.Iterator;

public class NullSafetyFunctions {
    
    public static JsonNode coalesce(JsonNode[] args) {
        return coalesce(args, null);
    }
    
    public static JsonNode coalesce(JsonNode[] args, Context context) {
        for (JsonNode arg : args) {
            if (arg != null && !arg.isNull()) {
                return arg;
            }
        }
        return NullNode.getInstance();
    }
    
    public static JsonNode ifNull(JsonNode[] args) {
        return ifNull(args, null);
    }
    
    public static JsonNode ifNull(JsonNode[] args, Context context) {
        if (args.length < 2) return NullNode.getInstance();
        
        JsonNode value = args[0];
        JsonNode defaultValue = args[1];
        
        return (value != null && !value.isNull()) ? value : defaultValue;
    }
    
    public static JsonNode nullIf(JsonNode[] args) {
        return nullIf(args, null);
    }
    
    public static JsonNode nullIf(JsonNode[] args, Context context) {
        if (args.length < 2) return NullNode.getInstance();
        
        JsonNode val1 = args[0];
        JsonNode val2 = args[1];
        
        if (val1 == null || val2 == null) return NullNode.getInstance();
        
        return val1.equals(val2) ? NullNode.getInstance() : val1;
    }
    
    public static JsonNode safeGet(JsonNode[] args) {
        return safeGet(args, null);
    }
    
    public static JsonNode safeGet(JsonNode[] args, Context context) {
        if (args.length < 2) return NullNode.getInstance();
        
        JsonNode obj = args[0];
        String path = args[1].asText();
        
        if (obj == null || obj.isNull()) return NullNode.getInstance();
        
        try {
            String[] parts = path.split("\\.");
            JsonNode current = obj;
            
            for (String part : parts) {
                if (current == null || current.isNull()) return NullNode.getInstance();
                current = current.get(part);
                if (current == null) return NullNode.getInstance();
            }
            
            return current;
        } catch (Exception e) {
            return NullNode.getInstance();
        }
    }
    
    public static JsonNode tryGet(JsonNode[] args) {
        return tryGet(args, null);
    }
    
    public static JsonNode tryGet(JsonNode[] args, Context context) {
        if (args.length < 2) return NullNode.getInstance();
        
        JsonNode obj = args[0];
        String path = args[1].asText();
        JsonNode defaultValue = args.length > 2 ? args[2] : NullNode.getInstance();
        
        JsonNode result = safeGet(new JsonNode[]{obj, args[1]}, context);
        
        return (result != null && !result.isNull()) ? result : defaultValue;
    }
    
    public static JsonNode removeNulls(JsonNode[] args) {
        return removeNulls(args, null);
    }
    
    public static JsonNode removeNulls(JsonNode[] args, Context context) {
        if (args.length == 0) return NullNode.getInstance();
        
        JsonNode obj = args[0];
        
        if (obj == null || obj.isNull()) return NullNode.getInstance();
        if (!obj.isObject()) return obj;
        
        ObjectNode result = JsonUtil.createObject();
        Iterator<String> fieldNames = obj.fieldNames();
        
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode value = obj.get(fieldName);
            
            if (value != null && !value.isNull()) {
                if (value.isObject()) {
                    value = removeNulls(new JsonNode[]{value}, context);
                }
                result.set(fieldName, value);
            }
        }
        
        return result;
    }
    
    public static JsonNode replaceNulls(JsonNode[] args) {
        return replaceNulls(args, null);
    }
    
    public static JsonNode replaceNulls(JsonNode[] args, Context context) {
        if (args.length < 2) return args.length > 0 ? args[0] : NullNode.getInstance();
        
        JsonNode obj = args[0];
        JsonNode defaultValue = args[1];
        
        if (obj == null || obj.isNull()) return defaultValue;
        if (!obj.isObject()) return obj;
        
        ObjectNode result = obj.deepCopy();
        Iterator<String> fieldNames = result.fieldNames();
        
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode value = result.get(fieldName);
            
            if (value == null || value.isNull()) {
                result.set(fieldName, defaultValue);
            } else if (value.isObject()) {
                result.set(fieldName, replaceNulls(new JsonNode[]{value, defaultValue}, context));
            }
        }
        
        return result;
    }
    
    public static JsonNode isNullOrEmpty(JsonNode[] args) {
        return isNullOrEmpty(args, null);
    }
    
    public static JsonNode isNullOrEmpty(JsonNode[] args, Context context) {
        if (args.length == 0) return BooleanNode.valueOf(true);
        
        JsonNode value = args[0];
        
        if (value == null || value.isNull()) return BooleanNode.valueOf(true);
        if (value.isTextual() && value.asText().isEmpty()) return BooleanNode.valueOf(true);
        if (value.isArray() && value.size() == 0) return BooleanNode.valueOf(true);
        if (value.isObject() && value.size() == 0) return BooleanNode.valueOf(true);
        
        return BooleanNode.valueOf(false);
    }
    
    public static JsonNode firstValid(JsonNode[] args) {
        return firstValid(args, null);
    }
    
    public static JsonNode firstValid(JsonNode[] args, Context context) {
        for (JsonNode arg : args) {
            JsonNode isEmpty = isNullOrEmpty(new JsonNode[]{arg}, context);
            if (!isEmpty.asBoolean()) {
                return arg;
            }
        }
        return NullNode.getInstance();
    }
}
