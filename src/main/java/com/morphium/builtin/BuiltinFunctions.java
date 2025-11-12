package com.morphium.builtin;

import com.google.gson.*;
import com.morphium.parser.ast.Expression;
import com.morphium.parser.ast.LiteralExpr;
import com.morphium.runtime.Context;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class BuiltinFunctions {

    public static JsonElement call(String name, List<Expression> argExprs, Context context) {
        // Special handling for functions that need unevaluated expressions
        switch (name) {
            case "map": return map(argExprs, context);
            case "filter": return filter(argExprs, context);
            case "reduce": return reduce(argExprs, context);
        }
        
        // Evaluate arguments for other functions
        JsonElement[] args = evaluateArgs(argExprs, context);
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

    // map(array, "itemName", expr)
    private static JsonElement map(List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) throw new RuntimeException("map requires 3 arguments");
        
        JsonElement arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isJsonArray()) return new JsonArray();
        
        String itemName = argExprs.get(1).evaluate(context).getAsString();
        Expression mapExpr = argExprs.get(2);
        
        JsonArray result = new JsonArray();
        JsonArray array = arrayArg.getAsJsonArray();
        
        for (JsonElement item : array) {
            Context itemContext = new Context(context);
            itemContext.define(itemName, item);
            JsonElement mapped = mapExpr.evaluate(itemContext);
            result.add(mapped);
        }
        
        return result;
    }

    // filter(array, "itemName", predicateExpr)
    private static JsonElement filter(List<Expression> argExprs, Context context) {
        if (argExprs.size() < 3) throw new RuntimeException("filter requires 3 arguments");
        
        JsonElement arrayArg = argExprs.get(0).evaluate(context);
        if (!arrayArg.isJsonArray()) return new JsonArray();
        
        String itemName = argExprs.get(1).evaluate(context).getAsString();
        Expression predicateExpr = argExprs.get(2);
        
        JsonArray result = new JsonArray();
        JsonArray array = arrayArg.getAsJsonArray();
        
        for (JsonElement item : array) {
            Context itemContext = new Context(context);
            itemContext.define(itemName, item);
            JsonElement test = predicateExpr.evaluate(itemContext);
            
            if (isTruthy(test)) {
                result.add(item);
            }
        }
        
        return result;
    }

    // reduce(array, "accName", "itemName", init, expr)
    private static JsonElement reduce(List<Expression> argExprs, Context context) {
        if (argExprs.size() < 5) throw new RuntimeException("reduce requires 5 arguments");
        
        JsonElement arrayArg = argExprs.get(0).evaluate(context);
        JsonElement initVal = argExprs.get(3).evaluate(context);
        if (!arrayArg.isJsonArray()) return initVal;
        
        String accName = argExprs.get(1).evaluate(context).getAsString();
        String itemName = argExprs.get(2).evaluate(context).getAsString();
        JsonElement accumulator = initVal;
        Expression reduceExpr = argExprs.get(4);
        
        JsonArray array = arrayArg.getAsJsonArray();
        
        for (JsonElement item : array) {
            Context itemContext = new Context(context);
            itemContext.define(accName, accumulator);
            itemContext.define(itemName, item);
            accumulator = reduceExpr.evaluate(itemContext);
        }
        
        return accumulator;
    }

    // merge(obj1, obj2, ...)
    private static JsonElement merge(JsonElement[] args) {
        JsonObject result = new JsonObject();
        
        for (JsonElement arg : args) {
            if (arg.isJsonObject()) {
                mergeInto(result, arg.getAsJsonObject());
            }
        }
        
        return result;
    }

    private static void mergeInto(JsonObject target, JsonObject source) {
        for (Map.Entry<String, JsonElement> entry : source.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            
            if (value.isJsonObject() && target.has(key) && target.get(key).isJsonObject()) {
                mergeInto(target.getAsJsonObject(key), value.getAsJsonObject());
            } else {
                target.add(key, value);
            }
        }
    }

    // pluck(array, key)
    private static JsonElement pluck(JsonElement[] args) {
        if (args.length < 2) throw new RuntimeException("pluck requires 2 arguments");
        
        JsonElement arrayArg = args[0];
        if (!arrayArg.isJsonArray()) return new JsonArray();
        
        String key = args[1].getAsString();
        JsonArray result = new JsonArray();
        
        for (JsonElement item : arrayArg.getAsJsonArray()) {
            if (item.isJsonObject() && item.getAsJsonObject().has(key)) {
                result.add(item.getAsJsonObject().get(key));
            } else {
                result.add(JsonNull.INSTANCE);
            }
        }
        
        return result;
    }

    // indexBy(array, key)
    private static JsonElement indexBy(JsonElement[] args) {
        if (args.length < 2) throw new RuntimeException("indexBy requires 2 arguments");
        
        JsonElement arrayArg = args[0];
        if (!arrayArg.isJsonArray()) return new JsonObject();
        
        String key = args[1].getAsString();
        JsonObject result = new JsonObject();
        
        for (JsonElement item : arrayArg.getAsJsonArray()) {
            if (item.isJsonObject()) {
                JsonObject obj = item.getAsJsonObject();
                if (obj.has(key)) {
                    String indexKey = obj.get(key).getAsString();
                    result.add(indexKey, item);
                }
            }
        }
        
        return result;
    }

    // exists(x)
    private static JsonElement exists(JsonElement[] args) {
        if (args.length < 1) return new JsonPrimitive(false);
        JsonElement value = args[0];
        return new JsonPrimitive(value != null && !value.isJsonNull());
    }

    // len(x)
    private static JsonElement len(JsonElement[] args) {
        if (args.length < 1) return new JsonPrimitive(0);
        
        JsonElement value = args[0];
        if (value == null || value.isJsonNull()) return new JsonPrimitive(0);
        
        if (value.isJsonArray()) {
            return new JsonPrimitive(value.getAsJsonArray().size());
        }
        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
            return new JsonPrimitive(value.getAsString().length());
        }
        
        return new JsonPrimitive(0);
    }

    // now()
    private static JsonElement now(JsonElement[] args) {
        return new JsonPrimitive(Instant.now().toString());
    }

    // formatDate(dateStr, fmt)
    private static JsonElement formatDate(JsonElement[] args) {
        if (args.length < 2) throw new RuntimeException("formatDate requires 2 arguments");
        
        String dateStr = args[0].getAsString();
        String format = args[1].getAsString();
        
        try {
            LocalDate date = LocalDate.parse(dateStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return new JsonPrimitive(date.format(formatter));
        } catch (Exception e) {
            return new JsonPrimitive(dateStr);
        }
    }

    // split(str, sep)
    private static JsonElement split(JsonElement[] args) {
        if (args.length < 2) throw new RuntimeException("split requires 2 arguments");
        
        String str = args[0].getAsString();
        String sep = args[1].getAsString();
        String[] parts = str.split(sep, -1);
        
        JsonArray result = new JsonArray();
        for (String part : parts) {
            result.add(new JsonPrimitive(part));
        }
        
        return result;
    }

    // join(array, sep)
    private static JsonElement join(JsonElement[] args) {
        if (args.length < 2) throw new RuntimeException("join requires 2 arguments");
        
        JsonElement arrayArg = args[0];
        if (!arrayArg.isJsonArray()) return new JsonPrimitive("");
        
        String sep = args[1].getAsString();
        StringBuilder sb = new StringBuilder();
        JsonArray array = arrayArg.getAsJsonArray();
        
        for (int i = 0; i < array.size(); i++) {
            if (i > 0) sb.append(sep);
            JsonElement elem = array.get(i);
            if (elem.isJsonPrimitive()) {
                sb.append(elem.getAsString());
            } else {
                sb.append(elem.toString());
            }
        }
        
        return new JsonPrimitive(sb.toString());
    }

    // upper(str)
    private static JsonElement upper(JsonElement[] args) {
        if (args.length < 1) return new JsonPrimitive("");
        return new JsonPrimitive(args[0].getAsString().toUpperCase());
    }

    // lower(str)
    private static JsonElement lower(JsonElement[] args) {
        if (args.length < 1) return new JsonPrimitive("");
        return new JsonPrimitive(args[0].getAsString().toLowerCase());
    }

    // trim(str)
    private static JsonElement trim(JsonElement[] args) {
        if (args.length < 1) return new JsonPrimitive("");
        return new JsonPrimitive(args[0].getAsString().trim());
    }

    // replace(str, pattern, replacement)
    private static JsonElement replace(JsonElement[] args) {
        if (args.length < 3) throw new RuntimeException("replace requires 3 arguments");
        
        String str = args[0].getAsString();
        String pattern = args[1].getAsString();
        String replacement = args[2].getAsString();
        
        return new JsonPrimitive(str.replace(pattern, replacement));
    }

    // toNumber(x)
    private static JsonElement toNumber(JsonElement[] args) {
        if (args.length < 1) return new JsonPrimitive(0);
        
        JsonElement value = args[0];
        if (value.isJsonPrimitive()) {
            JsonPrimitive prim = value.getAsJsonPrimitive();
            if (prim.isNumber()) {
                return value;
            }
            if (prim.isString()) {
                try {
                    return new JsonPrimitive(Double.parseDouble(prim.getAsString()));
                } catch (NumberFormatException e) {
                    return new JsonPrimitive(0);
                }
            }
        }
        
        return new JsonPrimitive(0);
    }

    // toString(x)
    private static JsonElement toStringFunc(JsonElement[] args) {
        if (args.length < 1) return new JsonPrimitive("");
        
        JsonElement value = args[0];
        if (value == null || value.isJsonNull()) {
            return new JsonPrimitive("null");
        }
        if (value.isJsonPrimitive()) {
            return new JsonPrimitive(value.getAsString());
        }
        
        return new JsonPrimitive(value.toString());
    }

    // toBool(x)
    private static JsonElement toBool(JsonElement[] args) {
        if (args.length < 1) return new JsonPrimitive(false);
        return new JsonPrimitive(isTruthy(args[0]));
    }

    // jsonParse(str)
    private static JsonElement jsonParse(JsonElement[] args) {
        if (args.length < 1) throw new RuntimeException("jsonParse requires 1 argument");
        
        String json = args[0].getAsString();
        try {
            return JsonParser.parseString(json);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Invalid JSON: " + e.getMessage());
        }
    }

    // jsonStringify(value)
    private static JsonElement jsonStringify(JsonElement[] args) {
        if (args.length < 1) return new JsonPrimitive("null");
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return new JsonPrimitive(gson.toJson(args[0]));
    }

    // get(obj, "path.to.node")
    private static JsonElement get(JsonElement[] args) {
        if (args.length < 2) throw new RuntimeException("get requires 2 arguments");
        
        JsonElement obj = args[0];
        String path = args[1].getAsString();
        String[] parts = path.split("\\.");
        
        JsonElement current = obj;
        for (String part : parts) {
            if (current == null || current.isJsonNull()) {
                return JsonNull.INSTANCE;
            }
            if (current.isJsonObject()) {
                current = current.getAsJsonObject().get(part);
            } else {
                return JsonNull.INSTANCE;
            }
        }
        
        return current != null ? current : JsonNull.INSTANCE;
    }

    // set(obj, "path.to.node", value) - immutable
    private static JsonElement set(JsonElement[] args) {
        if (args.length < 3) throw new RuntimeException("set requires 3 arguments");
        
        JsonElement obj = args[0];
        String path = args[1].getAsString();
        JsonElement value = args[2];
        
        if (!obj.isJsonObject()) {
            throw new RuntimeException("set requires an object as first argument");
        }
        
        JsonObject result = deepCopy(obj.getAsJsonObject());
        String[] parts = path.split("\\.");
        
        JsonObject current = result;
        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];
            if (!current.has(part) || !current.get(part).isJsonObject()) {
                current.add(part, new JsonObject());
            }
            current = current.getAsJsonObject(part);
        }
        
        current.add(parts[parts.length - 1], value);
        return result;
    }

    private static JsonObject deepCopy(JsonObject obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return gson.fromJson(json, JsonObject.class);
    }

    private static boolean isTruthy(JsonElement value) {
        if (value == null || value.isJsonNull()) return false;
        if (value.isJsonPrimitive()) {
            JsonPrimitive prim = value.getAsJsonPrimitive();
            if (prim.isBoolean()) return prim.getAsBoolean();
            if (prim.isNumber()) return prim.getAsDouble() != 0;
            if (prim.isString()) return !prim.getAsString().isEmpty();
        }
        return true;
    }

    private static JsonElement[] evaluateArgs(List<Expression> argExprs, Context context) {
        JsonElement[] args = new JsonElement[argExprs.size()];
        for (int i = 0; i < argExprs.size(); i++) {
            args[i] = argExprs.get(i).evaluate(context);
        }
        return args;
    }
}
