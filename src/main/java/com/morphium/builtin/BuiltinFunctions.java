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
}
