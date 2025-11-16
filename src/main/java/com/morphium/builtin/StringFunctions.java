package com.morphium.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import com.morphium.util.JsonUtil;
import com.morphium.runtime.Context;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * String utility functions for Morphium DSL.
 * Week 7-8 implementation.
 */
public class StringFunctions {
    
    /**
     * Check if string contains substring.
     * contains(str, substring)
     */
    public static JsonNode contains(JsonNode[] args) {
        return contains(args, null);
    }
    
    public static JsonNode contains(JsonNode[] args, Context context) {
        if (args.length < 2) return BooleanNode.valueOf(false);
        
        String str = args[0].asText();
        String substring = args[1].asText();
        
        return BooleanNode.valueOf(str.contains(substring));
    }
    
    /**
     * Check if string starts with prefix.
     * startsWith(str, prefix)
     */
    public static JsonNode startsWith(JsonNode[] args) {
        return startsWith(args, null);
    }
    
    public static JsonNode startsWith(JsonNode[] args, Context context) {
        if (args.length < 2) return BooleanNode.valueOf(false);
        
        String str = args[0].asText();
        String prefix = args[1].asText();
        
        return BooleanNode.valueOf(str.startsWith(prefix));
    }
    
    /**
     * Check if string ends with suffix.
     * endsWith(str, suffix)
     */
    public static JsonNode endsWith(JsonNode[] args) {
        return endsWith(args, null);
    }
    
    public static JsonNode endsWith(JsonNode[] args, Context context) {
        if (args.length < 2) return BooleanNode.valueOf(false);
        
        String str = args[0].asText();
        String suffix = args[1].asText();
        
        return BooleanNode.valueOf(str.endsWith(suffix));
    }
    
    /**
     * Find index of substring.
     * indexOf(str, substring)
     */
    public static JsonNode indexOf(JsonNode[] args) {
        return indexOf(args, null);
    }
    
    public static JsonNode indexOf(JsonNode[] args, Context context) {
        if (args.length < 2) return IntNode.valueOf(-1);
        
        String str = args[0].asText();
        String substring = args[1].asText();
        
        return IntNode.valueOf(str.indexOf(substring));
    }
    
    /**
     * Extract substring.
     * substring(str, start, end)
     */
    public static JsonNode substring(JsonNode[] args) {
        return substring(args, null);
    }
    
    public static JsonNode substring(JsonNode[] args, Context context) {
        if (args.length < 2) return TextNode.valueOf("");
        
        String str = args[0].asText();
        int start = args[1].asInt();
        
        if (start < 0 || start >= str.length()) {
            return TextNode.valueOf("");
        }
        
        if (args.length >= 3) {
            int end = args[2].asInt();
            end = Math.min(end, str.length());
            if (end <= start) return TextNode.valueOf("");
            return TextNode.valueOf(str.substring(start, end));
        }
        
        return TextNode.valueOf(str.substring(start));
    }
    
    /**
     * Pad string at start.
     * padStart(str, length, char)
     */
    public static JsonNode padStart(JsonNode[] args) {
        return padStart(args, null);
    }
    
    public static JsonNode padStart(JsonNode[] args, Context context) {
        if (args.length < 2) return args.length > 0 ? args[0] : TextNode.valueOf("");
        
        String str = args[0].asText();
        int targetLength = args[1].asInt();
        String padChar = args.length > 2 ? args[2].asText() : " ";
        
        if (str.length() >= targetLength) return TextNode.valueOf(str);
        
        int padLength = targetLength - str.length();
        String padding = padChar.repeat(padLength);
        
        return TextNode.valueOf(padding + str);
    }
    
    /**
     * Pad string at end.
     * padEnd(str, length, char)
     */
    public static JsonNode padEnd(JsonNode[] args) {
        return padEnd(args, null);
    }
    
    public static JsonNode padEnd(JsonNode[] args, Context context) {
        if (args.length < 2) return args.length > 0 ? args[0] : TextNode.valueOf("");
        
        String str = args[0].asText();
        int targetLength = args[1].asInt();
        String padChar = args.length > 2 ? args[2].asText() : " ";
        
        if (str.length() >= targetLength) return TextNode.valueOf(str);
        
        int padLength = targetLength - str.length();
        String padding = padChar.repeat(padLength);
        
        return TextNode.valueOf(str + padding);
    }
    
    /**
     * Capitalize first letter.
     * capitalize(str)
     */
    public static JsonNode capitalize(JsonNode[] args) {
        return capitalize(args, null);
    }
    
    public static JsonNode capitalize(JsonNode[] args, Context context) {
        if (args.length == 0) return TextNode.valueOf("");
        
        String str = args[0].asText();
        if (str.isEmpty()) return TextNode.valueOf(str);
        
        return TextNode.valueOf(str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase());
    }
    
    /**
     * Convert to title case.
     * titleCase(str)
     */
    public static JsonNode titleCase(JsonNode[] args) {
        return titleCase(args, null);
    }
    
    public static JsonNode titleCase(JsonNode[] args, Context context) {
        if (args.length == 0) return TextNode.valueOf("");
        
        String str = args[0].asText();
        if (str.isEmpty()) return TextNode.valueOf(str);
        
        String[] words = str.split("\\s+");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < words.length; i++) {
            if (i > 0) result.append(" ");
            String word = words[i];
            if (!word.isEmpty()) {
                result.append(word.substring(0, 1).toUpperCase());
                if (word.length() > 1) {
                    result.append(word.substring(1).toLowerCase());
                }
            }
        }
        
        return TextNode.valueOf(result.toString());
    }
    
    /**
     * Clean excessive whitespace.
     * cleanWhitespace(str)
     */
    public static JsonNode cleanWhitespace(JsonNode[] args) {
        return cleanWhitespace(args, null);
    }
    
    public static JsonNode cleanWhitespace(JsonNode[] args, Context context) {
        if (args.length == 0) return TextNode.valueOf("");
        
        String str = args[0].asText();
        // Replace multiple spaces with single space and trim
        String cleaned = str.replaceAll("\\s+", " ").trim();
        
        return TextNode.valueOf(cleaned);
    }
    
    /**
     * Check if string matches regex pattern.
     * matches(str, pattern)
     */
    public static JsonNode matches(JsonNode[] args) {
        return matches(args, null);
    }
    
    public static JsonNode matches(JsonNode[] args, Context context) {
        if (args.length < 2) return BooleanNode.valueOf(false);
        
        String str = args[0].asText();
        String pattern = args[1].asText();
        
        try {
            return BooleanNode.valueOf(str.matches(pattern));
        } catch (Exception e) {
            return BooleanNode.valueOf(false);
        }
    }
    
    /**
     * Test if string matches pattern (with flags).
     * matchesPattern(str, pattern, flags)
     */
    public static JsonNode matchesPattern(JsonNode[] args) {
        return matchesPattern(args, null);
    }
    
    public static JsonNode matchesPattern(JsonNode[] args, Context context) {
        if (args.length < 2) return BooleanNode.valueOf(false);
        
        String str = args[0].asText();
        String patternStr = args[1].asText();
        
        try {
            int flags = 0;
            if (args.length >= 3) {
                String flagsStr = args[2].asText().toLowerCase();
                if (flagsStr.contains("i")) flags |= Pattern.CASE_INSENSITIVE;
                if (flagsStr.contains("m")) flags |= Pattern.MULTILINE;
                if (flagsStr.contains("s")) flags |= Pattern.DOTALL;
            }
            
            Pattern pattern = Pattern.compile(patternStr, flags);
            Matcher matcher = pattern.matcher(str);
            
            return BooleanNode.valueOf(matcher.find());
        } catch (Exception e) {
            return BooleanNode.valueOf(false);
        }
    }
    
    /**
     * Repeat string n times.
     * repeat(str, count)
     */
    public static JsonNode repeat(JsonNode[] args) {
        return repeat(args, null);
    }
    
    public static JsonNode repeat(JsonNode[] args, Context context) {
        if (args.length < 2) return args.length > 0 ? args[0] : TextNode.valueOf("");
        
        String str = args[0].asText();
        int count = args[1].asInt();
        
        if (count <= 0) return TextNode.valueOf("");
        
        return TextNode.valueOf(str.repeat(count));
    }
    
    /**
     * Reverse a string.
     * reverseStr(str)
     */
    public static JsonNode reverseStr(JsonNode[] args) {
        return reverseStr(args, null);
    }
    
    public static JsonNode reverseStr(JsonNode[] args, Context context) {
        if (args.length == 0) return TextNode.valueOf("");
        
        String str = args[0].asText();
        return TextNode.valueOf(new StringBuilder(str).reverse().toString());
    }
}
