package com.morphium.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import com.morphium.util.JsonUtil;
import com.morphium.runtime.Context;

import java.util.ArrayList;
import java.util.List;

public class PathFunctions {
    
    public static JsonNode getIn(JsonNode[] args) {
        return getIn(args, null);
    }
    
    public static JsonNode getIn(JsonNode[] args, Context context) {
        if (args.length < 2) return NullNode.getInstance();
        
        JsonNode obj = args[0];
        JsonNode pathArg = args[1];
        JsonNode defaultValue = args.length > 2 ? args[2] : NullNode.getInstance();
        
        if (obj == null || obj.isNull()) return defaultValue;
        
        List<String> pathSegments = parsePathArgument(pathArg);
        JsonNode current = obj;
        
        for (String segment : pathSegments) {
            if (current == null || current.isNull()) return defaultValue;
            
            if (current.isArray() && isNumeric(segment)) {
                int index = Integer.parseInt(segment);
                if (index < 0 || index >= current.size()) return defaultValue;
                current = current.get(index);
            } else if (current.isObject()) {
                current = current.get(segment);
                if (current == null) return defaultValue;
            } else {
                return defaultValue;
            }
        }
        
        return current != null ? current : defaultValue;
    }
    
    public static JsonNode setIn(JsonNode[] args) {
        return setIn(args, null);
    }
    
    public static JsonNode setIn(JsonNode[] args, Context context) {
        if (args.length < 3) return args.length > 0 ? args[0] : NullNode.getInstance();
        
        JsonNode obj = args[0];
        JsonNode pathArg = args[1];
        JsonNode value = args[2];
        
        if (obj == null || obj.isNull()) {
            obj = JsonUtil.createObject();
        }
        
        List<String> pathSegments = parsePathArgument(pathArg);
        if (pathSegments.isEmpty()) return obj;
        
        JsonNode result = obj.deepCopy();
        JsonNode current = result;
        
        for (int i = 0; i < pathSegments.size() - 1; i++) {
            String segment = pathSegments.get(i);
            String nextSegment = pathSegments.get(i + 1);
            boolean nextIsArray = isNumeric(nextSegment);
            
            if (current.isArray() && isNumeric(segment)) {
                int index = Integer.parseInt(segment);
                current = ensureArrayIndex((ArrayNode) current, index, nextIsArray);
            } else if (current.isObject()) {
                ObjectNode objNode = (ObjectNode) current;
                JsonNode next = objNode.get(segment);
                
                if (next == null) {
                    next = nextIsArray ? JsonUtil.createArray() : JsonUtil.createObject();
                    objNode.set(segment, next);
                }
                current = next;
            }
        }
        
        String lastSegment = pathSegments.get(pathSegments.size() - 1);
        if (current.isArray() && isNumeric(lastSegment)) {
            int index = Integer.parseInt(lastSegment);
            ArrayNode arrayNode = (ArrayNode) current;
            ensureArraySize(arrayNode, index + 1);
            arrayNode.set(index, value);
        } else if (current.isObject()) {
            ((ObjectNode) current).set(lastSegment, value);
        }
        
        return result;
    }
    
    public static JsonNode deleteIn(JsonNode[] args) {
        return deleteIn(args, null);
    }
    
    public static JsonNode deleteIn(JsonNode[] args, Context context) {
        if (args.length < 2) return args.length > 0 ? args[0] : NullNode.getInstance();
        
        JsonNode obj = args[0];
        JsonNode pathArg = args[1];
        
        if (obj == null || obj.isNull()) return obj;
        
        List<String> pathSegments = parsePathArgument(pathArg);
        if (pathSegments.isEmpty()) return obj;
        
        JsonNode result = obj.deepCopy();

        JsonNode parent = result;
        for (int i = 0; i < pathSegments.size() - 1; i++) {
            String segment = pathSegments.get(i);
            if (parent.isArray() && isNumeric(segment)) {
                int index = Integer.parseInt(segment);
                parent = parent.get(index);
            } else if (parent.isObject()) {
                parent = parent.get(segment);
            }
            if (parent == null) return result;
        }

        String lastSegment = pathSegments.get(pathSegments.size() - 1);
        if (parent != null && parent.isObject()) {
            ((ObjectNode) parent).remove(lastSegment);
        } else if (parent != null && parent.isArray() && isNumeric(lastSegment)) {
            int index = Integer.parseInt(lastSegment);
            ((ArrayNode) parent).remove(index);
        }
        
        return result;
    }
    
    public static JsonNode hasPath(JsonNode[] args) {
        return hasPath(args, null);
    }
    
    public static JsonNode hasPath(JsonNode[] args, Context context) {
        if (args.length < 2) return BooleanNode.valueOf(false);
        
        JsonNode obj = args[0];
        JsonNode pathArg = args[1];
        
        if (obj == null || obj.isNull()) return BooleanNode.valueOf(false);
        
        List<String> pathSegments = parsePathArgument(pathArg);
        JsonNode current = obj;
        
        for (String segment : pathSegments) {
            if (current == null || current.isNull()) return BooleanNode.valueOf(false);
            
            if (current.isArray() && isNumeric(segment)) {
                int index = Integer.parseInt(segment);
                if (index < 0 || index >= current.size()) return BooleanNode.valueOf(false);
                current = current.get(index);
            } else if (current.isObject()) {
                if (!current.has(segment)) return BooleanNode.valueOf(false);
                current = current.get(segment);
            } else {
                return BooleanNode.valueOf(false);
            }
        }
        
        return BooleanNode.valueOf(current != null && !current.isNull());
    }
    
    public static JsonNode getPaths(JsonNode[] args) {
        return getPaths(args, null);
    }
    
    public static JsonNode getPaths(JsonNode[] args, Context context) {
        if (args.length < 2) return JsonUtil.createArray();
        
        JsonNode obj = args[0];
        JsonNode pathsArg = args[1];
        
        if (!pathsArg.isArray()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        for (JsonNode pathNode : pathsArg) {
            JsonNode value = getIn(new JsonNode[]{obj, pathNode}, context);
            result.add(value);
        }
        
        return result;
    }
    
    public static JsonNode pathDepth(JsonNode[] args) {
        return pathDepth(args, null);
    }
    
    public static JsonNode pathDepth(JsonNode[] args, Context context) {
        if (args.length < 1) return IntNode.valueOf(0);
        
        List<String> segments = parsePathArgument(args[0]);
        return IntNode.valueOf(segments.size());
    }
    
    public static JsonNode normalizePath(JsonNode[] args) {
        return normalizePath(args, null);
    }
    
    public static JsonNode normalizePath(JsonNode[] args, Context context) {
        if (args.length < 1) return JsonUtil.createArray();
        
        List<String> segments = parsePathArgument(args[0]);
        ArrayNode result = JsonUtil.createArray();
        for (String segment : segments) {
            result.add(TextNode.valueOf(segment));
        }
        
        return result;
    }
    
    public static JsonNode pathExists(JsonNode[] args) {
        return pathExists(args, null);
    }
    
    public static JsonNode pathExists(JsonNode[] args, Context context) {
        return hasPath(args, context);
    }

    private static List<String> parsePathArgument(JsonNode pathArg) {
        List<String> segments = new ArrayList<>();
        
        if (pathArg.isArray()) {
            for (JsonNode segment : pathArg) {
                segments.add(segment.asText());
            }
        } else if (pathArg.isTextual()) {
            String pathStr = pathArg.asText();
            if (!pathStr.isEmpty()) {
                for (String segment : pathStr.split("\\.")) {
                    if (!segment.isEmpty()) {
                        segments.add(segment);
                    }
                }
            }
        }
        
        return segments;
    }
    
    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private static JsonNode ensureArrayIndex(ArrayNode array, int index, boolean nextIsArray) {
        ensureArraySize(array, index + 1);
        JsonNode node = array.get(index);
        if (node == null || node.isNull()) {
            node = nextIsArray ? JsonUtil.createArray() : JsonUtil.createObject();
            array.set(index, node);
        }
        return node;
    }
    
    private static void ensureArraySize(ArrayNode array, int minSize) {
        while (array.size() < minSize) {
            array.add(NullNode.getInstance());
        }
    }
}
