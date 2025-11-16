package com.morphium.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import com.morphium.util.JsonUtil;
import com.morphium.runtime.Context;
import com.morphium.parser.ast.Expression;

import java.util.*;

/**
 * Advanced array and collection operations for Morphium DSL.
 * Week 9-10 implementation.
 */
public class CollectionFunctions {
    
    /**
     * Split array into chunks of given size.
     * chunk(array, size)
     */
    public static JsonNode chunk(JsonNode[] args) {
        return chunk(args, null);
    }
    
    public static JsonNode chunk(JsonNode[] args, Context context) {
        if (args.length < 2) return JsonUtil.createArray();
        
        JsonNode array = args[0];
        int chunkSize = args[1].asInt();
        
        if (!array.isArray() || chunkSize <= 0) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        ArrayNode currentChunk = JsonUtil.createArray();
        
        int count = 0;
        for (JsonNode item : array) {
            currentChunk.add(item);
            count++;
            
            if (count == chunkSize) {
                result.add(currentChunk);
                currentChunk = JsonUtil.createArray();
                count = 0;
            }
        }
        
        if (currentChunk.size() > 0) {
            result.add(currentChunk);
        }
        
        return result;
    }
    
    /**
     * Remove falsy values from array.
     * compact(array)
     */
    public static JsonNode compact(JsonNode[] args) {
        return compact(args, null);
    }
    
    public static JsonNode compact(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createArray();
        
        JsonNode array = args[0];
        if (!array.isArray()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        
        for (JsonNode item : array) {
            if (isTruthy(item)) {
                result.add(item);
            }
        }
        
        return result;
    }
    
    /**
     * Get unique values from array.
     * unique(array)
     */
    public static JsonNode unique(JsonNode[] args) {
        return unique(args, null);
    }
    
    public static JsonNode unique(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createArray();
        
        JsonNode array = args[0];
        if (!array.isArray()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        Set<String> seen = new HashSet<>();
        
        for (JsonNode item : array) {
            String key = item.toString();
            if (!seen.contains(key)) {
                seen.add(key);
                result.add(item);
            }
        }
        
        return result;
    }
    
    /**
     * Get unique values by a key field.
     * uniqueBy(array, key)
     */
    public static JsonNode uniqueBy(JsonNode[] args) {
        return uniqueBy(args, null);
    }
    
    public static JsonNode uniqueBy(JsonNode[] args, Context context) {
        if (args.length < 2) return JsonUtil.createArray();
        
        JsonNode array = args[0];
        String key = args[1].asText();
        
        if (!array.isArray()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        Set<String> seen = new HashSet<>();
        
        for (JsonNode item : array) {
            if (item.isObject() && item.has(key)) {
                String keyValue = item.get(key).toString();
                if (!seen.contains(keyValue)) {
                    seen.add(keyValue);
                    result.add(item);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Zip multiple arrays together.
     * zip(arr1, arr2, arr3, ...)
     */
    public static JsonNode zip(JsonNode[] args) {
        return zip(args, null);
    }
    
    public static JsonNode zip(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createArray();
        
        // Find max length
        int maxLength = 0;
        for (JsonNode arg : args) {
            if (arg.isArray() && arg.size() > maxLength) {
                maxLength = arg.size();
            }
        }
        
        ArrayNode result = JsonUtil.createArray();
        
        for (int i = 0; i < maxLength; i++) {
            ArrayNode tuple = JsonUtil.createArray();
            for (JsonNode arg : args) {
                if (arg.isArray() && i < arg.size()) {
                    tuple.add(arg.get(i));
                } else {
                    tuple.add(NullNode.getInstance());
                }
            }
            result.add(tuple);
        }
        
        return result;
    }
    
    /**
     * Unzip an array of arrays.
     * unzip(array)
     */
    public static JsonNode unzip(JsonNode[] args) {
        return unzip(args, null);
    }
    
    public static JsonNode unzip(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createArray();
        
        JsonNode array = args[0];
        if (!array.isArray() || array.size() == 0) return JsonUtil.createArray();
        
        // Find max tuple length
        int maxLength = 0;
        for (JsonNode tuple : array) {
            if (tuple.isArray() && tuple.size() > maxLength) {
                maxLength = tuple.size();
            }
        }
        
        ArrayNode result = JsonUtil.createArray();
        
        for (int i = 0; i < maxLength; i++) {
            ArrayNode column = JsonUtil.createArray();
            for (JsonNode tuple : array) {
                if (tuple.isArray() && i < tuple.size()) {
                    column.add(tuple.get(i));
                } else {
                    column.add(NullNode.getInstance());
                }
            }
            result.add(column);
        }
        
        return result;
    }
    
    /**
     * Find index of element matching condition.
     * findIndex(array, itemName, condition)
     */
    public static JsonNode findIndex(List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) return IntNode.valueOf(-1);
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return IntNode.valueOf(-1);
        
        String itemName = argExprs.get(1).evaluate(context).asText();
        Expression conditionExpr = argExprs.get(2);
        
        Context itemContext = new Context(context, 2);
        int index = 0;
        
        for (JsonNode item : arrayArg) {
            itemContext.redefine(itemName, item);
            JsonNode result = conditionExpr.evaluate(itemContext);
            if (isTruthy(result)) {
                return IntNode.valueOf(index);
            }
            index++;
        }
        
        return IntNode.valueOf(-1);
    }
    
    /**
     * Find last index of element matching condition.
     * findLastIndex(array, itemName, condition)
     */
    public static JsonNode findLastIndex(List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) return IntNode.valueOf(-1);
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return IntNode.valueOf(-1);
        
        String itemName = argExprs.get(1).evaluate(context).asText();
        Expression conditionExpr = argExprs.get(2);
        
        Context itemContext = new Context(context, 2);
        int lastIndex = -1;
        int index = 0;
        
        for (JsonNode item : arrayArg) {
            itemContext.redefine(itemName, item);
            JsonNode result = conditionExpr.evaluate(itemContext);
            if (isTruthy(result)) {
                lastIndex = index;
            }
            index++;
        }
        
        return IntNode.valueOf(lastIndex);
    }
    
    /**
     * Calculate cumulative sum.
     * cumSum(array)
     */
    public static JsonNode cumSum(JsonNode[] args) {
        return cumSum(args, null);
    }
    
    public static JsonNode cumSum(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createArray();
        
        JsonNode array = args[0];
        if (!array.isArray()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        double sum = 0;
        
        for (JsonNode item : array) {
            if (item.isNumber()) {
                sum += item.asDouble();
            }
            result.add(DoubleNode.valueOf(sum));
        }
        
        return result;
    }
    
    /**
     * Calculate differences between consecutive elements.
     * diff(array)
     */
    public static JsonNode diff(JsonNode[] args) {
        return diff(args, null);
    }
    
    public static JsonNode diff(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createArray();
        
        JsonNode array = args[0];
        if (!array.isArray() || array.size() < 2) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        double prev = 0;
        boolean first = true;
        
        for (JsonNode item : array) {
            if (item.isNumber()) {
                if (!first) {
                    result.add(DoubleNode.valueOf(item.asDouble() - prev));
                }
                prev = item.asDouble();
                first = false;
            }
        }
        
        return result;
    }
    
    /**
     * Calculate moving average.
     * movingAvg(array, window)
     */
    public static JsonNode movingAvg(JsonNode[] args) {
        return movingAvg(args, null);
    }
    
    public static JsonNode movingAvg(JsonNode[] args, Context context) {
        if (args.length < 2) return JsonUtil.createArray();
        
        JsonNode array = args[0];
        int window = args[1].asInt();
        
        if (!array.isArray() || window <= 0) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        List<Double> values = new ArrayList<>();
        
        for (JsonNode item : array) {
            if (item.isNumber()) {
                values.add(item.asDouble());
                
                if (values.size() > window) {
                    values.remove(0);
                }
                
                if (values.size() > 0) {
                    double sum = values.stream().mapToDouble(Double::doubleValue).sum();
                    double avg = sum / values.size();
                    result.add(DoubleNode.valueOf(avg));
                }
            }
        }
        
        return result;
    }
    
    /**
     * Flatten nested arrays by one level.
     * flatten(array)
     */
    public static JsonNode flatten(JsonNode[] args) {
        return flatten(args, null);
    }
    
    public static JsonNode flatten(JsonNode[] args, Context context) {
        if (args.length == 0) return JsonUtil.createArray();
        
        JsonNode array = args[0];
        if (!array.isArray()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        
        for (JsonNode item : array) {
            if (item.isArray()) {
                for (JsonNode subItem : item) {
                    result.add(subItem);
                }
            } else {
                result.add(item);
            }
        }
        
        return result;
    }
    
    /**
     * Take first n elements.
     * take(array, n)
     */
    public static JsonNode take(JsonNode[] args) {
        return take(args, null);
    }
    
    public static JsonNode take(JsonNode[] args, Context context) {
        if (args.length < 2) return JsonUtil.createArray();
        
        JsonNode array = args[0];
        int n = args[1].asInt();
        
        if (!array.isArray()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        int count = 0;
        
        for (JsonNode item : array) {
            if (count >= n) break;
            result.add(item);
            count++;
        }
        
        return result;
    }
    
    /**
     * Drop first n elements.
     * drop(array, n)
     */
    public static JsonNode drop(JsonNode[] args) {
        return drop(args, null);
    }
    
    public static JsonNode drop(JsonNode[] args, Context context) {
        if (args.length < 2) return JsonUtil.createArray();
        
        JsonNode array = args[0];
        int n = args[1].asInt();
        
        if (!array.isArray()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        int count = 0;
        
        for (JsonNode item : array) {
            if (count >= n) {
                result.add(item);
            }
            count++;
        }
        
        return result;
    }
    
    // Helper methods
    
    private static boolean isTruthy(JsonNode node) {
        if (node == null || node.isNull()) return false;
        if (node.isBoolean()) return node.asBoolean();
        if (node.isNumber()) return node.asDouble() != 0;
        if (node.isTextual()) return !node.asText().isEmpty();
        if (node.isArray()) return !node.isEmpty();
        if (node.isObject()) return !node.isEmpty();
        return true;
    }
}
