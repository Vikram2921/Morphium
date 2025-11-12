package com.morphium.examples;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import com.morphium.runtime.Logger;

/**
 * Simple demonstration of error handling and logging features
 */
public class SimpleLoggingExample {
    
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MorphiumEngine engine = new MorphiumEngine();
        
        // Set up a simple logger
        engine.setLogger(new Logger() {
            @Override
            public void log(Level level, String message) {
                System.out.println("[" + level + "] " + message);
            }
        });
        
        System.out.println("=== Example 1: Basic Logging ===");
        String script1 = 
            "log('Starting transformation'); " +
            "let result = $.value * 2; " +
            "logInfo('Calculated result:', result); " +
            "{ output: result }";
        
        JsonNode input1 = mapper.readTree("{\"value\": 10}");
        JsonNode result1 = engine.transformFromString(script1, input1);
        System.out.println("Result: " + result1 + "\n");
        
        System.out.println("=== Example 2: Error Handling ===");
        String script2 = 
            "let age = $.age; " +
            "let check = age < 0 ? error('Age cannot be negative') : age; " +
            "{ validAge: age }";
        
        try {
            JsonNode badInput = mapper.readTree("{\"age\": -5}");
            engine.transformFromString(script2, badInput);
        } catch (Exception e) {
            System.out.println("Caught error: " + e.getMessage());
        }
        
        JsonNode goodInput = mapper.readTree("{\"age\": 25}");
        JsonNode result2 = engine.transformFromString(script2, goodInput);
        System.out.println("Valid result: " + result2 + "\n");
        
        System.out.println("=== Example 3: Log Levels ===");
        String script3 = 
            "logDebug('Debug info'); " +
            "logInfo('Processing...'); " +
            "logWarn('Approaching limit'); " +
            "logError('Simulated error'); " +
            "{ done: true }";
        
        JsonNode input3 = mapper.readTree("{}");
        engine.transformFromString(script3, input3);
    }
}
