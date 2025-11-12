package com.morphium.examples;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import com.morphium.runtime.DynamicScriptResolver;
import com.morphium.runtime.Logger;

/**
 * Examples demonstrating error handling, logging, and dynamic script imports
 */
public class ErrorAndDynamicImportDemo {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== Morphium Error Handling & Dynamic Import Demo ===\n");
        
        // Demo 1: Logging
        demoLogging();
        
        // Demo 2: Error Handling
        demoErrorHandling();
        
        // Demo 3: Dynamic Script Import
        demoDynamicImport();
    }
    
    /**
     * Demonstrates logging functionality
     */
    private static void demoLogging() throws Exception {
        System.out.println("Demo 1: Logging Functions");
        System.out.println("-------------------------");
        
        MorphiumEngine engine = new MorphiumEngine();
        
        // Set up a custom logger
        engine.setLogger(new Logger() {
            @Override
            public void log(Level level, String message) {
                System.out.println("[" + level + "] " + message);
            }
        });
        
        String script = 
            "let data = { count: 5 }; " +
            "log('Processing data with count:', data.count); " +
            "logInfo('This is an info message'); " +
            "logWarn('This is a warning'); " +
            "{ result: data.count * 2 }";
        
        String json = "{}";
        JsonNode input = mapper.readTree(json);
        JsonNode result = engine.transformFromString(script, input);
        
        System.out.println("Result: " + result);
        System.out.println();
    }
    
    /**
     * Demonstrates error throwing in scripts
     */
    private static void demoErrorHandling() throws Exception {
        System.out.println("Demo 2: Error Handling");
        System.out.println("----------------------");
        
        MorphiumEngine engine = new MorphiumEngine();
        
        // Example with validation and error
        String script = 
            "let age = $.age; " +
            "if (age < 0, error('Age cannot be negative'), null); " +
            "if (age > 150, error('Age seems invalid'), null); " +
            "{ validAge: age }";
        
        try {
            String json = "{\"age\": -5}";
            JsonNode input = mapper.readTree(json);
            engine.transformFromString(script, input);
        } catch (Exception e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }
        
        // Valid case
        String validJson = "{\"age\": 25}";
        JsonNode validInput = mapper.readTree(validJson);
        JsonNode result = engine.transformFromString(script, validInput);
        System.out.println("Valid result: " + result);
        System.out.println();
    }
    
    /**
     * Demonstrates dynamic script imports
     */
    private static void demoDynamicImport() throws Exception {
        System.out.println("Demo 3: Dynamic Script Import");
        System.out.println("-----------------------------");
        
        MorphiumEngine engine = new MorphiumEngine();
        
        // Register a dynamic script resolver
        engine.getModuleResolver().registerDynamicResolver("getFieldMapper", 
            (functionName, args) -> {
                // args[0] should be a JSON node containing the field name
                JsonNode fieldNode = (JsonNode) args[0];
                String fieldName = fieldNode.asText();
                
                // Generate a transformation script dynamically
                return String.format(
                    "export transform = function(data) { " +
                    "  { %s: data.%s } " +
                    "}",
                    fieldName, fieldName
                );
            }
        );
        
        // Use the dynamic import in a script
        String script = 
            "import getFieldMapper(\"name\") as nameMapper; " +
            "nameMapper.transform($)";
        
        String json = "{\"name\":\"Alice\",\"age\":30}";
        JsonNode input = mapper.readTree(json);
        JsonNode result = engine.transformFromString(script, input);
        
        System.out.println("Input: " + json);
        System.out.println("Result: " + result);
        System.out.println("\nDynamic import allows generating transformation scripts on-the-fly!");
        System.out.println();
    }
    
    /**
     * Advanced example: Dynamic script with multiple parameters
     */
    public static void advancedDynamicImport() throws Exception {
        System.out.println("Advanced: Multi-parameter Dynamic Import");
        System.out.println("----------------------------------------");
        
        MorphiumEngine engine = new MorphiumEngine();
        
        // Register a more complex resolver
        engine.getModuleResolver().registerDynamicResolver("buildTransform", 
            (functionName, args) -> {
                JsonNode operation = (JsonNode) args[0];
                JsonNode fieldName = (JsonNode) args[1];
                
                String op = operation.asText();
                String field = fieldName.asText();
                
                switch (op) {
                    case "uppercase":
                        return "export transform = function(data) { " +
                               "  upper(data." + field + ") " +
                               "}";
                    case "double":
                        return "export transform = function(data) { " +
                               "  data." + field + " * 2 " +
                               "}";
                    default:
                        return "export transform = function(data) { data." + field + " }";
                }
            }
        );
        
        String script = 
            "import buildTransform(\"uppercase\", \"name\") as transformer; " +
            "{ result: transformer.transform($) }";
        
        String json = "{\"name\":\"hello\"}";
        JsonNode input = mapper.readTree(json);
        JsonNode result = engine.transformFromString(script, input);
        
        System.out.println("Result: " + result);
    }
}
