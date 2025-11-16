package com.morphium.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import com.morphium.runtime.Context;

public class TypeFunctions {

    public static JsonNode isString(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        JsonNode value = args[0];
        return BooleanNode.valueOf(value != null && value.isTextual());
    }
    
    public static JsonNode isNumber(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        JsonNode value = args[0];
        return BooleanNode.valueOf(value != null && value.isNumber());
    }
    
    public static JsonNode isBoolean(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        JsonNode value = args[0];
        return BooleanNode.valueOf(value != null && value.isBoolean());
    }
    
    public static JsonNode isArray(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        JsonNode value = args[0];
        return BooleanNode.valueOf(value != null && value.isArray());
    }
    
    public static JsonNode isObject(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        JsonNode value = args[0];
        return BooleanNode.valueOf(value != null && value.isObject());
    }
    
    public static JsonNode isNull(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.TRUE;
        }
        JsonNode value = args[0];
        return BooleanNode.valueOf(value == null || value.isNull());
    }
    
    public static JsonNode isEmpty(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.TRUE;
        }
        
        JsonNode value = args[0];

        if (value == null || value.isNull() || value.isMissingNode()) {
            return BooleanNode.TRUE;
        }

        if (value.isTextual()) {
            return BooleanNode.valueOf(value.asText().isEmpty());
        }

        if (value.isArray()) {
            return BooleanNode.valueOf(value.size() == 0);
        }

        if (value.isObject()) {
            return BooleanNode.valueOf(value.size() == 0);
        }

        return BooleanNode.FALSE;
    }
    
    public static JsonNode typeOf(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return TextNode.valueOf("undefined");
        }
        
        JsonNode value = args[0];
        
        if (value == null || value.isNull()) {
            return TextNode.valueOf("null");
        }
        
        if (value.isMissingNode()) {
            return TextNode.valueOf("undefined");
        }
        
        if (value.isBoolean()) {
            return TextNode.valueOf("boolean");
        }
        
        if (value.isIntegralNumber()) {
            return TextNode.valueOf("integer");
        }
        
        if (value.isFloatingPointNumber()) {
            return TextNode.valueOf("float");
        }
        
        if (value.isNumber()) {
            return TextNode.valueOf("number");
        }
        
        if (value.isTextual()) {
            return TextNode.valueOf("string");
        }
        
        if (value.isArray()) {
            return TextNode.valueOf("array");
        }
        
        if (value.isObject()) {
            return TextNode.valueOf("object");
        }
        
        if (value.isBinary()) {
            return TextNode.valueOf("binary");
        }
        
        return TextNode.valueOf("unknown");
    }

    public static JsonNode toInt(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return NullNode.getInstance();
        }
        
        JsonNode value = args[0];
        
        if (value == null || value.isNull()) {
            return NullNode.getInstance();
        }
        
        try {
            if (value.isNumber()) {
                return IntNode.valueOf(value.asInt());
            }
            
            if (value.isTextual()) {
                String text = value.asText().trim();
                if (text.contains(".")) {
                    return IntNode.valueOf((int) Double.parseDouble(text));
                }
                return IntNode.valueOf(Integer.parseInt(text));
            }
            
            if (value.isBoolean()) {
                return IntNode.valueOf(value.asBoolean() ? 1 : 0);
            }
            
            return NullNode.getInstance();
        } catch (NumberFormatException e) {
            return NullNode.getInstance();
        }
    }
    
    public static JsonNode toFloat(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return NullNode.getInstance();
        }
        
        JsonNode value = args[0];
        
        if (value == null || value.isNull()) {
            return NullNode.getInstance();
        }
        
        try {
            if (value.isNumber()) {
                return DoubleNode.valueOf(value.asDouble());
            }
            
            if (value.isTextual()) {
                String text = value.asText().trim();
                return DoubleNode.valueOf(Double.parseDouble(text));
            }
            
            if (value.isBoolean()) {
                return DoubleNode.valueOf(value.asBoolean() ? 1.0 : 0.0);
            }
            
            return NullNode.getInstance();
        } catch (NumberFormatException e) {
            return NullNode.getInstance();
        }
    }
    
    public static JsonNode toStringFunc(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return TextNode.valueOf("");
        }
        
        JsonNode value = args[0];
        
        if (value == null || value.isNull()) {
            return TextNode.valueOf("null");
        }
        
        if (value.isTextual()) {
            return value;
        }
        
        if (value.isNumber() || value.isBoolean()) {
            return TextNode.valueOf(value.asText());
        }
        
        if (value.isArray() || value.isObject()) {
            return TextNode.valueOf(value.toString());
        }
        
        return TextNode.valueOf(value.asText());
    }
    
    public static JsonNode toBoolFunc(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        
        JsonNode value = args[0];
        
        if (value == null || value.isNull()) {
            return BooleanNode.FALSE;
        }
        
        if (value.isBoolean()) {
            return value;
        }
        
        if (value.isNumber()) {
            return BooleanNode.valueOf(value.asDouble() != 0);
        }
        
        if (value.isTextual()) {
            String text = value.asText().trim().toLowerCase();
            if (text.equals("true") || text.equals("1") || text.equals("yes") || text.equals("y")) {
                return BooleanNode.TRUE;
            }
            if (text.equals("false") || text.equals("0") || text.equals("no") || text.equals("n") || text.isEmpty()) {
                return BooleanNode.FALSE;
            }
            return BooleanNode.TRUE;
        }
        
        if (value.isArray()) {
            return BooleanNode.valueOf(value.size() > 0);
        }
        
        if (value.isObject()) {
            return BooleanNode.valueOf(value.size() > 0);
        }
        
        return BooleanNode.FALSE;
    }

    public static JsonNode isFinite(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        
        JsonNode value = args[0];
        
        if (!value.isNumber()) {
            return BooleanNode.FALSE;
        }
        
        double num = value.asDouble();
        return BooleanNode.valueOf(!Double.isInfinite(num) && !Double.isNaN(num));
    }
    
    public static JsonNode isNaN(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        
        JsonNode value = args[0];
        
        if (!value.isNumber()) {
            try {
                double num = Double.parseDouble(value.asText());
                return BooleanNode.valueOf(Double.isNaN(num));
            } catch (Exception e) {
                return BooleanNode.FALSE;
            }
        }
        
        return BooleanNode.valueOf(Double.isNaN(value.asDouble()));
    }
    
    public static JsonNode isInteger(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        
        JsonNode value = args[0];
        
        if (!value.isNumber()) {
            return BooleanNode.FALSE;
        }
        
        double num = value.asDouble();
        return BooleanNode.valueOf(num == Math.floor(num) && !Double.isInfinite(num));
    }
}
