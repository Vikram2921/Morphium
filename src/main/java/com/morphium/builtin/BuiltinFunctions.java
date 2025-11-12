package com.morphium.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import com.morphium.parser.ast.Expression;
import com.morphium.runtime.Context;
import com.morphium.util.JsonUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class BuiltinFunctions {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode call(String name, java.util.List<Expression> argExprs, Context context) {
        switch (name) {
            case "map": return map(argExprs, context);
            case "filter": return filter(argExprs, context);
            case "reduce": return reduce(argExprs, context);
            case "flatMap": return flatMap(argExprs, context);
            case "forEach": return forEach(argExprs, context);
            case "anyMatch": return anyMatch(argExprs, context);
            case "allMatch": return allMatch(argExprs, context);
            case "noneMatch": return noneMatch(argExprs, context);
            case "findFirst": return findFirst(argExprs, context);
            case "count": return count(argExprs, context);
            case "distinct": return distinct(argExprs, context);
            case "sorted": return sorted(argExprs, context);
            case "skip": return skip(argExprs, context);
            case "limit": return limit(argExprs, context);
            case "peek": return peek(argExprs, context);
            case "groupBy": return groupBy(argExprs, context);
            case "partition": return partition(argExprs, context);
            case "sum": return sum(argExprs, context);
            case "avg": return avg(argExprs, context);
            case "min": return min(argExprs, context);
            case "max": return max(argExprs, context);
            case "runMorph": return runMorph(argExprs, context);
        }
        
        JsonNode[] args = evaluateArgs(argExprs, context);
        switch (name) {
            case "merge": return merge(args);
            case "pluck": return pluck(args);
            case "indexBy": return indexBy(args);
            case "exists": return exists(args);
            case "len": return len(args);
            case "now": return now(args);
            case "formatDate": return formatDate(args);
            case "split": return split(args);
            case "join": return join(args);
            case "upper": return upper(args);
            case "lower": return lower(args);
            case "trim": return trim(args);
            case "replace": return replace(args);
            case "toNumber": return toNumber(args);
            case "toString": return toStringFunc(args);
            case "toBool": return toBool(args);
            case "jsonParse": return jsonParse(args);
            case "jsonStringify": return jsonStringify(args);
            case "get": return get(args);
            case "set": return set(args);
            case "reverse": return reverse(args);
            case "concat": return concat(args);
            case "slice": return slice(args);
            case "keys": return keys(args);
            case "values": return values(args);
            case "entries": return entries(args);
            default: return null;
        }
    }

    private static JsonNode[] evaluateArgs(java.util.List<Expression> argExprs, Context context) {
        JsonNode[] args = new JsonNode[argExprs.size()];
        for (int i = 0; i < argExprs.size(); i++) {
            args[i] = argExprs.get(i).evaluate(context);
        }
        return args;
    }

    private static JsonNode map(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) throw new RuntimeException("map requires 3 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return JsonUtil.createArray();
        
        String itemName = argExprs.get(1).evaluate(context).asText();
        Expression mapExpr = argExprs.get(2);
        
        ArrayNode result = JsonUtil.createArray();
        for (JsonNode item : arrayArg) {
            Context itemContext = new Context(context);
            itemContext.define(itemName, item);
            JsonNode mapped = mapExpr.evaluate(itemContext);
            result.add(mapped);
        }
        
        return result;
    }

    private static JsonNode filter(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) throw new RuntimeException("filter requires 3 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return JsonUtil.createArray();
        
        String itemName = argExprs.get(1).evaluate(context).asText();
        Expression predicateExpr = argExprs.get(2);
        
        ArrayNode result = JsonUtil.createArray();
        for (JsonNode item : arrayArg) {
            Context itemContext = new Context(context);
            itemContext.define(itemName, item);
            JsonNode predicate = predicateExpr.evaluate(itemContext);
            if (isTruthy(predicate)) {
                result.add(item);
            }
        }
        
        return result;
    }

    private static JsonNode reduce(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 5) throw new RuntimeException("reduce requires 5 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return NullNode.getInstance();
        
        String accName = argExprs.get(1).evaluate(context).asText();
        String itemName = argExprs.get(2).evaluate(context).asText();
        JsonNode initValue = argExprs.get(3).evaluate(context);
        Expression reduceExpr = argExprs.get(4);
        
        JsonNode accumulator = initValue;
        for (JsonNode item : arrayArg) {
            Context reduceContext = new Context(context);
            reduceContext.define(accName, accumulator);
            reduceContext.define(itemName, item);
            accumulator = reduceExpr.evaluate(reduceContext);
        }
        
        return accumulator;
    }

    private static JsonNode merge(JsonNode[] args) {
        ObjectNode result = JsonUtil.createObject();
        for (JsonNode arg : args) {
            if (arg.isObject()) {
                Iterator<String> fieldNames = arg.fieldNames();
                while (fieldNames.hasNext()) {
                    String fieldName = fieldNames.next();
                    result.set(fieldName, arg.get(fieldName));
                }
            }
        }
        return result;
    }

    private static JsonNode pluck(JsonNode[] args) {
        if (args.length < 2) return JsonUtil.createArray();
        JsonNode array = args[0];
        String key = args[1].asText();
        
        ArrayNode result = JsonUtil.createArray();
        if (array.isArray()) {
            for (JsonNode item : array) {
                if (item.has(key)) {
                    result.add(item.get(key));
                }
            }
        }
        return result;
    }

    private static JsonNode indexBy(JsonNode[] args) {
        if (args.length < 2) return JsonUtil.createObject();
        JsonNode array = args[0];
        String key = args[1].asText();
        
        ObjectNode result = JsonUtil.createObject();
        if (array.isArray()) {
            for (JsonNode item : array) {
                if (item.has(key)) {
                    String indexKey = item.get(key).asText();
                    result.set(indexKey, item);
                }
            }
        }
        return result;
    }

    private static JsonNode exists(JsonNode[] args) {
        if (args.length == 0) return BooleanNode.FALSE;
        JsonNode val = args[0];
        return BooleanNode.valueOf(val != null && !val.isNull());
    }

    private static JsonNode len(JsonNode[] args) {
        if (args.length == 0) return IntNode.valueOf(0);
        JsonNode val = args[0];
        if (val.isArray()) {
            return IntNode.valueOf(val.size());
        }
        if (val.isTextual()) {
            return IntNode.valueOf(val.asText().length());
        }
        return IntNode.valueOf(0);
    }

    private static JsonNode now(JsonNode[] args) {
        return TextNode.valueOf(Instant.now().toString());
    }

    private static JsonNode formatDate(JsonNode[] args) {
        if (args.length < 2) return TextNode.valueOf("");
        String dateStr = args[0].asText();
        String format = args[1].asText();
        
        try {
            LocalDate date = LocalDate.parse(dateStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return TextNode.valueOf(date.format(formatter));
        } catch (Exception e) {
            return TextNode.valueOf(dateStr);
        }
    }

    private static JsonNode split(JsonNode[] args) {
        if (args.length < 2) return JsonUtil.createArray();
        String str = args[0].asText();
        String separator = args[1].asText();
        
        ArrayNode result = JsonUtil.createArray();
        String[] parts = str.split(java.util.regex.Pattern.quote(separator));
        for (String part : parts) {
            result.add(TextNode.valueOf(part));
        }
        return result;
    }

    private static JsonNode join(JsonNode[] args) {
        if (args.length < 2) return TextNode.valueOf("");
        JsonNode array = args[0];
        String separator = args[1].asText();
        
        if (!array.isArray()) return TextNode.valueOf("");
        
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (JsonNode item : array) {
            if (!first) sb.append(separator);
            sb.append(item.asText());
            first = false;
        }
        return TextNode.valueOf(sb.toString());
    }

    private static JsonNode upper(JsonNode[] args) {
        if (args.length == 0) return TextNode.valueOf("");
        return TextNode.valueOf(args[0].asText().toUpperCase());
    }

    private static JsonNode lower(JsonNode[] args) {
        if (args.length == 0) return TextNode.valueOf("");
        return TextNode.valueOf(args[0].asText().toLowerCase());
    }

    private static JsonNode trim(JsonNode[] args) {
        if (args.length == 0) return TextNode.valueOf("");
        return TextNode.valueOf(args[0].asText().trim());
    }

    private static JsonNode replace(JsonNode[] args) {
        if (args.length < 3) return TextNode.valueOf("");
        String str = args[0].asText();
        String target = args[1].asText();
        String replacement = args[2].asText();
        return TextNode.valueOf(str.replace(target, replacement));
    }

    private static JsonNode toNumber(JsonNode[] args) {
        if (args.length == 0) return IntNode.valueOf(0);
        JsonNode val = args[0];
        if (val.isNumber()) return val;
        try {
            String text = val.asText();
            if (text.contains(".")) {
                return DoubleNode.valueOf(Double.parseDouble(text));
            }
            return IntNode.valueOf(Integer.parseInt(text));
        } catch (Exception e) {
            return IntNode.valueOf(0);
        }
    }

    private static JsonNode toStringFunc(JsonNode[] args) {
        if (args.length == 0) return TextNode.valueOf("");
        return TextNode.valueOf(args[0].asText());
    }

    private static JsonNode toBool(JsonNode[] args) {
        if (args.length == 0) return BooleanNode.FALSE;
        return BooleanNode.valueOf(isTruthy(args[0]));
    }

    private static JsonNode jsonParse(JsonNode[] args) {
        if (args.length == 0) return NullNode.getInstance();
        try {
            return mapper.readTree(args[0].asText());
        } catch (Exception e) {
            return NullNode.getInstance();
        }
    }

    private static JsonNode jsonStringify(JsonNode[] args) {
        if (args.length == 0) return TextNode.valueOf("null");
        try {
            return TextNode.valueOf(mapper.writeValueAsString(args[0]));
        } catch (Exception e) {
            return TextNode.valueOf("null");
        }
    }

    private static JsonNode get(JsonNode[] args) {
        if (args.length < 2) return NullNode.getInstance();
        JsonNode obj = args[0];
        String path = args[1].asText();
        
        String[] parts = path.split("\\.");
        JsonNode current = obj;
        for (String part : parts) {
            if (current == null || current.isNull()) return NullNode.getInstance();
            current = current.get(part);
        }
        return current != null ? current : NullNode.getInstance();
    }

    private static JsonNode set(JsonNode[] args) {
        if (args.length < 3) return args.length > 0 ? args[0] : NullNode.getInstance();
        
        JsonNode obj = args[0];
        String path = args[1].asText();
        JsonNode value = args[2];
        
        if (!obj.isObject()) return obj;
        
        ObjectNode result = obj.deepCopy();
        String[] parts = path.split("\\.");
        
        ObjectNode current = result;
        for (int i = 0; i < parts.length - 1; i++) {
            JsonNode next = current.get(parts[i]);
            if (next == null || !next.isObject()) {
                ObjectNode newObj = JsonUtil.createObject();
                current.set(parts[i], newObj);
                current = newObj;
            } else {
                current = (ObjectNode) next;
            }
        }
        current.set(parts[parts.length - 1], value);
        
        return result;
    }

    private static boolean isTruthy(JsonNode node) {
        if (node == null || node.isNull()) return false;
        if (node.isBoolean()) return node.asBoolean();
        if (node.isNumber()) return node.asDouble() != 0;
        if (node.isTextual()) return !node.asText().isEmpty();
        if (node.isArray()) return !node.isEmpty();
        if (node.isObject()) return !node.isEmpty();
        return true;
    }

    // Stream-like operations
    private static JsonNode flatMap(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) throw new RuntimeException("flatMap requires 3 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return JsonUtil.createArray();
        
        String itemName = argExprs.get(1).evaluate(context).asText();
        Expression mapExpr = argExprs.get(2);
        
        ArrayNode result = JsonUtil.createArray();
        for (JsonNode item : arrayArg) {
            Context itemContext = new Context(context);
            itemContext.define(itemName, item);
            JsonNode mapped = mapExpr.evaluate(itemContext);
            if (mapped.isArray()) {
                for (JsonNode subItem : mapped) {
                    result.add(subItem);
                }
            } else {
                result.add(mapped);
            }
        }
        
        return result;
    }

    private static JsonNode forEach(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) throw new RuntimeException("forEach requires 3 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return NullNode.getInstance();
        
        String itemName = argExprs.get(1).evaluate(context).asText();
        Expression forEachExpr = argExprs.get(2);
        
        for (JsonNode item : arrayArg) {
            Context itemContext = new Context(context);
            itemContext.define(itemName, item);
            forEachExpr.evaluate(itemContext);
        }
        
        return arrayArg;
    }

    private static JsonNode anyMatch(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) throw new RuntimeException("anyMatch requires 3 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return BooleanNode.FALSE;
        
        String itemName = argExprs.get(1).evaluate(context).asText();
        Expression predicateExpr = argExprs.get(2);
        
        for (JsonNode item : arrayArg) {
            Context itemContext = new Context(context);
            itemContext.define(itemName, item);
            JsonNode predicate = predicateExpr.evaluate(itemContext);
            if (isTruthy(predicate)) {
                return BooleanNode.TRUE;
            }
        }
        
        return BooleanNode.FALSE;
    }

    private static JsonNode allMatch(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) throw new RuntimeException("allMatch requires 3 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return BooleanNode.TRUE;
        
        String itemName = argExprs.get(1).evaluate(context).asText();
        Expression predicateExpr = argExprs.get(2);
        
        for (JsonNode item : arrayArg) {
            Context itemContext = new Context(context);
            itemContext.define(itemName, item);
            JsonNode predicate = predicateExpr.evaluate(itemContext);
            if (!isTruthy(predicate)) {
                return BooleanNode.FALSE;
            }
        }
        
        return BooleanNode.TRUE;
    }

    private static JsonNode noneMatch(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) throw new RuntimeException("noneMatch requires 3 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return BooleanNode.TRUE;
        
        String itemName = argExprs.get(1).evaluate(context).asText();
        Expression predicateExpr = argExprs.get(2);
        
        for (JsonNode item : arrayArg) {
            Context itemContext = new Context(context);
            itemContext.define(itemName, item);
            JsonNode predicate = predicateExpr.evaluate(itemContext);
            if (isTruthy(predicate)) {
                return BooleanNode.FALSE;
            }
        }
        
        return BooleanNode.TRUE;
    }

    private static JsonNode findFirst(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) throw new RuntimeException("findFirst requires 3 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return NullNode.getInstance();
        
        String itemName = argExprs.get(1).evaluate(context).asText();
        Expression predicateExpr = argExprs.get(2);
        
        for (JsonNode item : arrayArg) {
            Context itemContext = new Context(context);
            itemContext.define(itemName, item);
            JsonNode predicate = predicateExpr.evaluate(itemContext);
            if (isTruthy(predicate)) {
                return item;
            }
        }
        
        return NullNode.getInstance();
    }

    private static JsonNode count(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 1) throw new RuntimeException("count requires 1 argument");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return IntNode.valueOf(0);
        
        if (argExprs.size() < 3) {
            return IntNode.valueOf(arrayArg.size());
        }
        
        String itemName = argExprs.get(1).evaluate(context).asText();
        Expression predicateExpr = argExprs.get(2);
        
        int count = 0;
        for (JsonNode item : arrayArg) {
            Context itemContext = new Context(context);
            itemContext.define(itemName, item);
            JsonNode predicate = predicateExpr.evaluate(itemContext);
            if (isTruthy(predicate)) {
                count++;
            }
        }
        
        return IntNode.valueOf(count);
    }

    private static JsonNode distinct(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 1) throw new RuntimeException("distinct requires 1 argument");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        java.util.Set<String> seen = new java.util.HashSet<>();
        
        for (JsonNode item : arrayArg) {
            String key = item.toString();
            if (!seen.contains(key)) {
                seen.add(key);
                result.add(item);
            }
        }
        
        return result;
    }

    private static JsonNode sorted(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 1) throw new RuntimeException("sorted requires 1 argument");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return JsonUtil.createArray();
        
        java.util.List<JsonNode> list = new java.util.ArrayList<>();
        for (JsonNode item : arrayArg) {
            list.add(item);
        }
        
        if (argExprs.size() < 2) {
            list.sort((a, b) -> compareNodes(a, b));
        } else {
            String key = argExprs.get(1).evaluate(context).asText();
            list.sort((a, b) -> {
                JsonNode aVal = a.has(key) ? a.get(key) : NullNode.getInstance();
                JsonNode bVal = b.has(key) ? b.get(key) : NullNode.getInstance();
                return compareNodes(aVal, bVal);
            });
        }
        
        ArrayNode result = JsonUtil.createArray();
        for (JsonNode item : list) {
            result.add(item);
        }
        
        return result;
    }

    private static int compareNodes(JsonNode a, JsonNode b) {
        if (a.isNumber() && b.isNumber()) {
            return Double.compare(a.asDouble(), b.asDouble());
        }
        return a.asText().compareTo(b.asText());
    }

    private static JsonNode skip(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 2) throw new RuntimeException("skip requires 2 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        int n = argExprs.get(1).evaluate(context).asInt();
        
        if (!arrayArg.isArray()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        int index = 0;
        for (JsonNode item : arrayArg) {
            if (index >= n) {
                result.add(item);
            }
            index++;
        }
        
        return result;
    }

    private static JsonNode limit(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 2) throw new RuntimeException("limit requires 2 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        int n = argExprs.get(1).evaluate(context).asInt();
        
        if (!arrayArg.isArray()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        int index = 0;
        for (JsonNode item : arrayArg) {
            if (index >= n) break;
            result.add(item);
            index++;
        }
        
        return result;
    }

    private static JsonNode peek(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) throw new RuntimeException("peek requires 3 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return arrayArg;
        
        String itemName = argExprs.get(1).evaluate(context).asText();
        Expression peekExpr = argExprs.get(2);
        
        for (JsonNode item : arrayArg) {
            Context itemContext = new Context(context);
            itemContext.define(itemName, item);
            peekExpr.evaluate(itemContext);
        }
        
        return arrayArg;
    }

    private static JsonNode groupBy(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 2) throw new RuntimeException("groupBy requires 2 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return JsonUtil.createObject();
        
        String key = argExprs.get(1).evaluate(context).asText();
        
        ObjectNode result = JsonUtil.createObject();
        for (JsonNode item : arrayArg) {
            String groupKey = item.has(key) ? item.get(key).asText() : "null";
            if (!result.has(groupKey)) {
                result.set(groupKey, JsonUtil.createArray());
            }
            ((ArrayNode) result.get(groupKey)).add(item);
        }
        
        return result;
    }

    private static JsonNode partition(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) throw new RuntimeException("partition requires 3 arguments");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) {
            ObjectNode result = JsonUtil.createObject();
            result.set("true", JsonUtil.createArray());
            result.set("false", JsonUtil.createArray());
            return result;
        }
        
        String itemName = argExprs.get(1).evaluate(context).asText();
        Expression predicateExpr = argExprs.get(2);
        
        ArrayNode truePartition = JsonUtil.createArray();
        ArrayNode falsePartition = JsonUtil.createArray();
        
        for (JsonNode item : arrayArg) {
            Context itemContext = new Context(context);
            itemContext.define(itemName, item);
            JsonNode predicate = predicateExpr.evaluate(itemContext);
            if (isTruthy(predicate)) {
                truePartition.add(item);
            } else {
                falsePartition.add(item);
            }
        }
        
        ObjectNode result = JsonUtil.createObject();
        result.set("true", truePartition);
        result.set("false", falsePartition);
        return result;
    }

    private static JsonNode sum(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 1) throw new RuntimeException("sum requires 1 argument");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray()) return IntNode.valueOf(0);
        
        double sum = 0;
        for (JsonNode item : arrayArg) {
            if (item.isNumber()) {
                sum += item.asDouble();
            }
        }
        
        return DoubleNode.valueOf(sum);
    }

    private static JsonNode avg(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 1) throw new RuntimeException("avg requires 1 argument");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray() || arrayArg.size() == 0) return IntNode.valueOf(0);
        
        double sum = 0;
        int count = 0;
        for (JsonNode item : arrayArg) {
            if (item.isNumber()) {
                sum += item.asDouble();
                count++;
            }
        }
        
        return count > 0 ? DoubleNode.valueOf(sum / count) : IntNode.valueOf(0);
    }

    private static JsonNode min(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 1) throw new RuntimeException("min requires 1 argument");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray() || arrayArg.size() == 0) return NullNode.getInstance();
        
        JsonNode minVal = null;
        for (JsonNode item : arrayArg) {
            if (minVal == null || compareNodes(item, minVal) < 0) {
                minVal = item;
            }
        }
        
        return minVal != null ? minVal : NullNode.getInstance();
    }

    private static JsonNode max(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 1) throw new RuntimeException("max requires 1 argument");
        
        JsonNode arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isArray() || arrayArg.size() == 0) return NullNode.getInstance();
        
        JsonNode maxVal = null;
        for (JsonNode item : arrayArg) {
            if (maxVal == null || compareNodes(item, maxVal) > 0) {
                maxVal = item;
            }
        }
        
        return maxVal != null ? maxVal : NullNode.getInstance();
    }

    private static JsonNode reverse(JsonNode[] args) {
        if (args.length == 0) return JsonUtil.createArray();
        JsonNode array = args[0];
        if (!array.isArray()) return array;
        
        ArrayNode result = JsonUtil.createArray();
        java.util.List<JsonNode> list = new java.util.ArrayList<>();
        for (JsonNode item : array) {
            list.add(item);
        }
        java.util.Collections.reverse(list);
        for (JsonNode item : list) {
            result.add(item);
        }
        
        return result;
    }

    private static JsonNode concat(JsonNode[] args) {
        ArrayNode result = JsonUtil.createArray();
        for (JsonNode arg : args) {
            if (arg.isArray()) {
                for (JsonNode item : arg) {
                    result.add(item);
                }
            } else {
                result.add(arg);
            }
        }
        return result;
    }

    private static JsonNode slice(JsonNode[] args) {
        if (args.length < 2) return JsonUtil.createArray();
        JsonNode array = args[0];
        if (!array.isArray()) return array;
        
        int start = args[1].asInt();
        int end = args.length > 2 ? args[2].asInt() : array.size();
        
        ArrayNode result = JsonUtil.createArray();
        int index = 0;
        for (JsonNode item : array) {
            if (index >= start && index < end) {
                result.add(item);
            }
            index++;
        }
        
        return result;
    }

    private static JsonNode keys(JsonNode[] args) {
        if (args.length == 0) return JsonUtil.createArray();
        JsonNode obj = args[0];
        if (!obj.isObject()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        Iterator<String> fieldNames = obj.fieldNames();
        while (fieldNames.hasNext()) {
            result.add(TextNode.valueOf(fieldNames.next()));
        }
        
        return result;
    }

    private static JsonNode values(JsonNode[] args) {
        if (args.length == 0) return JsonUtil.createArray();
        JsonNode obj = args[0];
        if (!obj.isObject()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        Iterator<JsonNode> elements = obj.elements();
        while (elements.hasNext()) {
            result.add(elements.next());
        }
        
        return result;
    }

    private static JsonNode entries(JsonNode[] args) {
        if (args.length == 0) return JsonUtil.createArray();
        JsonNode obj = args[0];
        if (!obj.isObject()) return JsonUtil.createArray();
        
        ArrayNode result = JsonUtil.createArray();
        Iterator<String> fieldNames = obj.fieldNames();
        while (fieldNames.hasNext()) {
            String key = fieldNames.next();
            ArrayNode entry = JsonUtil.createArray();
            entry.add(TextNode.valueOf(key));
            entry.add(obj.get(key));
            result.add(entry);
        }
        
        return result;
    }

    private static JsonNode runMorph(java.util.List<Expression> argExprs, Context context) {
        if (argExprs.size() < 2) throw new RuntimeException("runMorph requires 2 arguments: morphFile and input");
        
        String morphFile = argExprs.get(0).evaluate(context).asText();
        JsonNode input = argExprs.get(1).evaluate(context);
        
        try {
            com.morphium.runtime.ModuleResolver resolver = new com.morphium.runtime.ModuleResolver();
            String source = resolver.resolve(morphFile);
            
            com.morphium.parser.Lexer lexer = new com.morphium.parser.Lexer(source, morphFile);
            com.morphium.parser.Parser parser = new com.morphium.parser.Parser(lexer);
            Expression morphExpr = parser.parse();
            
            Context morphContext = new Context(context);
            morphContext.define("$", input);
            
            return morphExpr.evaluate(morphContext);
        } catch (Exception e) {
            throw new RuntimeException("Failed to run morph file " + morphFile + ": " + e.getMessage(), e);
        }
    }
}
