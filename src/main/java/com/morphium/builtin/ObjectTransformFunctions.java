package com.morphium.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import com.morphium.util.JsonUtil;
import com.morphium.runtime.Context;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Object transformation functions for Morphium DSL.
 * Week 11-12 implementation - FINAL PHASE 1 DELIVERABLE.
 */
public class ObjectTransformFunctions {
    
    /**
     * Pick specific keys from an object.
     * pick(obj, keys)
     */
    public static JsonNode pick(JsonNode[] args) {
        return pick(args, null);
    }
    
    public static JsonNode pick(JsonNode[] args, Context context) {
        if (args.length < 2) return JsonUtil.createObject();
        
        JsonNode obj = args[0];
        JsonNode keysArg = args[1];
        
        if (!obj.isObject()) return JsonUtil.createObject();
        
        Set<String> keys = new HashSet<>();
        if (keysArg.isArray()) {
            for (JsonNode key : keysArg) {
                keys.add(key.asText());
            }
        } else if (keysArg.isTextual()) {
            keys.add(keysArg.asText());
        }
        
        ObjectNode result = JsonUtil.createObject();
        for (String key : keys) {
            if (obj.has(key)) {
                result.set(key, obj.get(key));
            }
        }
        
        return result;
    }
    
    /**
     * Omit specific keys from an object.
     * omit(obj, keys)
     */
    public static JsonNode omit(JsonNode[] args) {
        return omit(args, null);
    }
    
    public static JsonNode omit(JsonNode[] args, Context context) {
        if (args.length < 2) return args.length > 0 ? args[0] : JsonUtil.createObject();
        
        JsonNode obj = args[0];
        JsonNode keysArg = args[1];
        
        if (!obj.isObject()) return obj;
        
        Set<String> keysToOmit = new HashSet<>();
        if (keysArg.isArray()) {
            for (JsonNode key : keysArg) {
                keysToOmit.add(key.asText());
            }
        } else if (keysArg.isTextual()) {
            keysToOmit.add(keysArg.asText());
        }
        
        ObjectNode result = JsonUtil.createObject();
        Iterator<Map.Entry<String, JsonNode>> fields = obj.fields();
        
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            if (!keysToOmit.contains(entry.getKey())) {
                result.set(entry.getKey(), entry.getValue());
            }
        }
        
        return result;
    }
    
    /**
     * Invert object - swap keys and values.
     * invert(obj)
     */
    public static JsonNode invert(JsonNode[] args) {
        return invert(args, null);
    }
    
    public static JsonNode invert(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createObject();
        
        JsonNode obj = args[0];
        if (!obj.isObject()) return JsonUtil.createObject();
        
        ObjectNode result = JsonUtil.createObject();
        Iterator<Map.Entry<String, JsonNode>> fields = obj.fields();
        
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String newKey = entry.getValue().asText();
            result.set(newKey, TextNode.valueOf(entry.getKey()));
        }
        
        return result;
    }
    
    /**
     * Map over object keys.
     * mapKeys(obj, fn) - where fn transforms key names
     */
    public static JsonNode mapKeys(JsonNode[] args) {
        return mapKeys(args, null);
    }
    
    public static JsonNode mapKeys(JsonNode[] args, Context context) {
        if (args.length < 2) return args.length > 0 ? args[0] : JsonUtil.createObject();
        
        JsonNode obj = args[0];
        String transform = args[1].asText(); // "upper", "lower", "camel", "snake"
        
        if (!obj.isObject()) return obj;
        
        ObjectNode result = JsonUtil.createObject();
        Iterator<Map.Entry<String, JsonNode>> fields = obj.fields();
        
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String newKey = transformKey(entry.getKey(), transform);
            result.set(newKey, entry.getValue());
        }
        
        return result;
    }
    
    /**
     * Map over object values.
     * mapValues(obj, fn) - transforms values (simplified version)
     */
    public static JsonNode mapValues(JsonNode[] args) {
        return mapValues(args, null);
    }
    
    public static JsonNode mapValues(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createObject();
        
        JsonNode obj = args[0];
        if (!obj.isObject()) return obj;
        
        // Returns copy for now - full transform would need expression evaluation
        return obj.deepCopy();
    }
    
    /**
     * Flatten nested object with dot notation.
     * flattenObj(obj, separator)
     */
    public static JsonNode flattenObj(JsonNode[] args) {
        return flattenObj(args, null);
    }
    
    public static JsonNode flattenObj(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createObject();
        
        JsonNode obj = args[0];
        String separator = args.length > 1 ? args[1].asText() : ".";
        
        if (!obj.isObject()) return obj;
        
        ObjectNode result = JsonUtil.createObject();
        flattenHelper(obj, "", separator, result);
        
        return result;
    }
    
    private static void flattenHelper(JsonNode node, String prefix, String separator, ObjectNode result) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String key = prefix.isEmpty() ? entry.getKey() : prefix + separator + entry.getKey();
                
                if (entry.getValue().isObject() && entry.getValue().size() > 0) {
                    flattenHelper(entry.getValue(), key, separator, result);
                } else {
                    result.set(key, entry.getValue());
                }
            }
        } else {
            result.set(prefix, node);
        }
    }
    
    /**
     * Unflatten object from dot notation.
     * unflattenObj(obj, separator)
     */
    public static JsonNode unflattenObj(JsonNode[] args) {
        return unflattenObj(args, null);
    }
    
    public static JsonNode unflattenObj(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createObject();
        
        JsonNode obj = args[0];
        String separator = args.length > 1 ? args[1].asText() : ".";
        
        if (!obj.isObject()) return obj;
        
        ObjectNode result = JsonUtil.createObject();
        Iterator<Map.Entry<String, JsonNode>> fields = obj.fields();
        
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String[] parts = entry.getKey().split(Pattern.quote(separator));
            
            ObjectNode current = result;
            for (int i = 0; i < parts.length - 1; i++) {
                String part = parts[i];
                if (!current.has(part)) {
                    current.set(part, JsonUtil.createObject());
                }
                current = (ObjectNode) current.get(part);
            }
            
            current.set(parts[parts.length - 1], entry.getValue());
        }
        
        return result;
    }
    
    /**
     * Convert object keys to camelCase.
     * toCamelCase(obj)
     */
    public static JsonNode toCamelCase(JsonNode[] args) {
        return toCamelCase(args, null);
    }
    
    public static JsonNode toCamelCase(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createObject();
        
        JsonNode obj = args[0];
        if (!obj.isObject()) return obj;
        
        return transformObjectKeys(obj, ObjectTransformFunctions::toCamelCaseStr);
    }
    
    /**
     * Convert object keys to snake_case.
     * toSnakeCase(obj)
     */
    public static JsonNode toSnakeCase(JsonNode[] args) {
        return toSnakeCase(args, null);
    }
    
    public static JsonNode toSnakeCase(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createObject();
        
        JsonNode obj = args[0];
        if (!obj.isObject()) return obj;
        
        return transformObjectKeys(obj, ObjectTransformFunctions::toSnakeCaseStr);
    }
    
    /**
     * Convert object keys to kebab-case.
     * toKebabCase(obj)
     */
    public static JsonNode toKebabCase(JsonNode[] args) {
        return toKebabCase(args, null);
    }
    
    public static JsonNode toKebabCase(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createObject();
        
        JsonNode obj = args[0];
        if (!obj.isObject()) return obj;
        
        return transformObjectKeys(obj, ObjectTransformFunctions::toKebabCaseStr);
    }
    
    /**
     * Deep clone an object.
     * deepClone(obj)
     */
    public static JsonNode deepClone(JsonNode[] args) {
        return deepClone(args, null);
    }
    
    public static JsonNode deepClone(JsonNode[] args, Context context) {
        if (args.length == 0) return NullNode.getInstance();
        
        return args[0].deepCopy();
    }
    
    // Helper methods
    
    private static JsonNode transformObjectKeys(JsonNode obj, KeyTransformer transformer) {
        if (!obj.isObject()) return obj;
        
        ObjectNode result = JsonUtil.createObject();
        Iterator<Map.Entry<String, JsonNode>> fields = obj.fields();
        
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String newKey = transformer.transform(entry.getKey());
            JsonNode value = entry.getValue();
            
            // Recursively transform nested objects
            if (value.isObject()) {
                value = transformObjectKeys(value, transformer);
            }
            
            result.set(newKey, value);
        }
        
        return result;
    }
    
    @FunctionalInterface
    private interface KeyTransformer {
        String transform(String key);
    }
    
    private static String transformKey(String key, String transform) {
        switch (transform.toLowerCase()) {
            case "upper": return key.toUpperCase();
            case "lower": return key.toLowerCase();
            case "camel": return toCamelCaseStr(key);
            case "snake": return toSnakeCaseStr(key);
            case "kebab": return toKebabCaseStr(key);
            default: return key;
        }
    }
    
    private static String toCamelCaseStr(String str) {
        if (str == null || str.isEmpty()) return str;
        
        // Handle snake_case and kebab-case
        String[] parts = str.split("[_-]");
        StringBuilder result = new StringBuilder(parts[0].toLowerCase());
        
        for (int i = 1; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                result.append(parts[i].substring(0, 1).toUpperCase());
                if (parts[i].length() > 1) {
                    result.append(parts[i].substring(1).toLowerCase());
                }
            }
        }
        
        return result.toString();
    }
    
    private static String toSnakeCaseStr(String str) {
        if (str == null || str.isEmpty()) return str;
        
        // Convert camelCase to snake_case
        String result = str.replaceAll("([a-z])([A-Z])", "$1_$2");
        return result.toLowerCase().replace("-", "_");
    }
    
    private static String toKebabCaseStr(String str) {
        if (str == null || str.isEmpty()) return str;
        
        // Convert camelCase to kebab-case
        String result = str.replaceAll("([a-z])([A-Z])", "$1-$2");
        return result.toLowerCase().replace("_", "-");
    }
}
