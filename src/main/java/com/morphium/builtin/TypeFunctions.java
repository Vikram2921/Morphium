package com.morphium.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import com.morphium.runtime.Context;

/**
 * Type System Functions
 * 
 * Provides type checking, validation, and conversion functions.
 * Part of Phase 1, Week 1-2 implementation.
 */
public class TypeFunctions {
    
    // ==================== TYPE CHECKING ====================
    
    /**
     * Check if value is a string
     * @param args [value]
     * @return boolean
     */
    public static JsonNode isString(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        JsonNode value = args[0];
        return BooleanNode.valueOf(value != null && value.isTextual());
    }
    
    /**
     * Check if value is a number
     * @param args [value]
     * @return boolean
     */
    public static JsonNode isNumber(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        JsonNode value = args[0];
        return BooleanNode.valueOf(value != null && value.isNumber());
    }
    
    /**
     * Check if value is a boolean
     * @param args [value]
     * @return boolean
     */
    public static JsonNode isBoolean(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        JsonNode value = args[0];
        return BooleanNode.valueOf(value != null && value.isBoolean());
    }
    
    /**
     * Check if value is an array
     * @param args [value]
     * @return boolean
     */
    public static JsonNode isArray(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        JsonNode value = args[0];
        return BooleanNode.valueOf(value != null && value.isArray());
    }
    
    /**
     * Check if value is an object
     * @param args [value]
     * @return boolean
     */
    public static JsonNode isObject(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        JsonNode value = args[0];
        return BooleanNode.valueOf(value != null && value.isObject());
    }
    
    /**
     * Check if value is null
     * @param args [value]
     * @return boolean
     */
    public static JsonNode isNull(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.TRUE;
        }
        JsonNode value = args[0];
        return BooleanNode.valueOf(value == null || value.isNull());
    }
    
    /**
     * Check if value is empty (null, undefined, "", [], {})
     * @param args [value]
     * @return boolean
     */
    public static JsonNode isEmpty(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.TRUE;
        }
        
        JsonNode value = args[0];
        
        // Null or missing
        if (value == null || value.isNull() || value.isMissingNode()) {
            return BooleanNode.TRUE;
        }
        
        // Empty string
        if (value.isTextual()) {
            return BooleanNode.valueOf(value.asText().isEmpty());
        }
        
        // Empty array
        if (value.isArray()) {
            return BooleanNode.valueOf(value.size() == 0);
        }
        
        // Empty object
        if (value.isObject()) {
            return BooleanNode.valueOf(value.size() == 0);
        }
        
        // Everything else is not empty
        return BooleanNode.FALSE;
    }
    
    /**
     * Get detailed type information
     * @param args [value]
     * @return string (type name)
     */
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
    
    // ==================== TYPE CONVERSION ====================
    
    /**
     * Convert value to integer
     * @param args [value]
     * @return integer or null
     */
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
                // Handle decimal strings by parsing as double first
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
    
    /**
     * Convert value to float/double
     * @param args [value]
     * @return float or null
     */
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
    
    /**
     * Convert value to string
     * @param args [value]
     * @return string
     */
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
            // Return JSON string representation
            return TextNode.valueOf(value.toString());
        }
        
        return TextNode.valueOf(value.asText());
    }
    
    /**
     * Convert value to boolean
     * @param args [value]
     * @return boolean
     */
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
            // Non-empty string is truthy
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
    
    // ==================== ADDITIONAL TYPE UTILITIES ====================
    
    /**
     * Check if value is a finite number (not NaN, not Infinity)
     * @param args [value]
     * @return boolean
     */
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
    
    /**
     * Check if value is NaN
     * @param args [value]
     * @return boolean
     */
    public static JsonNode isNaN(JsonNode[] args, Context context) {
        if (args.length == 0) {
            return BooleanNode.FALSE;
        }
        
        JsonNode value = args[0];
        
        if (!value.isNumber()) {
            // Try to parse as number
            try {
                double num = Double.parseDouble(value.asText());
                return BooleanNode.valueOf(Double.isNaN(num));
            } catch (Exception e) {
                return BooleanNode.FALSE;
            }
        }
        
        return BooleanNode.valueOf(Double.isNaN(value.asDouble()));
    }
    
    /**
     * Check if value is an integer (whole number)
     * @param args [value]
     * @return boolean
     */
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
