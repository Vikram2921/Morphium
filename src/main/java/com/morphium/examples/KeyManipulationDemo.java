package com.morphium.examples;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;

public class KeyManipulationDemo {
    public static void main(String[] args) throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        ObjectMapper mapper = new ObjectMapper();
        
        System.out.println("=== Key Manipulation Demo ===\n");
        
        // Test 1: removeKey
        System.out.println("Test 1: removeKey()");
        String input1 = "{\n" +
            "  \"user\": {\n" +
            "    \"name\": \"Alice\",\n" +
            "    \"age\": 25,\n" +
            "    \"password\": \"secret123\",\n" +
            "    \"email\": \"alice@example.com\"\n" +
            "  }\n" +
            "}";
        
        String morph1 = "{\n" +
            "  user: removeKey($.user, \"password\")\n" +
            "}";
        
        JsonNode result1 = engine.transformFromString(morph1, mapper.readTree(input1));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result1));
        System.out.println();
        
        // Test 2: Multiple removeKey
        System.out.println("Test 2: Multiple removeKey()");
        String input2 = "{\n" +
            "  \"product\": {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"Widget\",\n" +
            "    \"price\": 29.99,\n" +
            "    \"internalCode\": \"INT-001\",\n" +
            "    \"costPrice\": 15.00\n" +
            "  }\n" +
            "}";
        
        String morph2 = "{\n" +
            "  product: removeKey(\n" +
            "    removeKey($.product, \"internalCode\"),\n" +
            "    \"costPrice\"\n" +
            "  )\n" +
            "}";
        
        JsonNode result2 = engine.transformFromString(morph2, mapper.readTree(input2));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result2));
        System.out.println();
        
        // Test 3: renameKey
        System.out.println("Test 3: renameKey()");
        String input3 = "{\n" +
            "  \"user\": {\n" +
            "    \"first_name\": \"Alice\",\n" +
            "    \"last_name\": \"Smith\"\n" +
            "  }\n" +
            "}";
        
        String morph3 = "{\n" +
            "  user: renameKey(\n" +
            "    renameKey($.user, \"first_name\", \"firstName\"),\n" +
            "    \"last_name\", \"lastName\"\n" +
            "  )\n" +
            "}";
        
        JsonNode result3 = engine.transformFromString(morph3, mapper.readTree(input3));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result3));
        System.out.println();
        
        // Test 4: Snake case to Camel case conversion
        System.out.println("Test 4: Snake case to Camel case with renameKey()");
        String input4 = "{\n" +
            "  \"product\": {\n" +
            "    \"product_id\": 123,\n" +
            "    \"product_name\": \"Widget\",\n" +
            "    \"unit_price\": 29.99\n" +
            "  }\n" +
            "}";
        
        String morph4 = "{\n" +
            "  product: renameKey(\n" +
            "    renameKey(\n" +
            "      renameKey($.product, \"product_id\", \"productId\"),\n" +
            "      \"product_name\", \"productName\"),\n" +
            "    \"unit_price\", \"unitPrice\"\n" +
            "  )\n" +
            "}";
        
        JsonNode result4 = engine.transformFromString(morph4, mapper.readTree(input4));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result4));
        System.out.println();
        
        // Test 5: Combined removeKey and renameKey
        System.out.println("Test 5: Combined removeKey() and renameKey()");
        String input5 = "{\n" +
            "  \"order\": {\n" +
            "    \"ord_id\": 456,\n" +
            "    \"cust_name\": \"Bob\",\n" +
            "    \"total_amt\": 100.00,\n" +
            "    \"internal_note\": \"private\"\n" +
            "  }\n" +
            "}";
        
        String morph5 = "{\n" +
            "  order: removeKey(\n" +
            "    renameKey(\n" +
            "      renameKey($.order, \"ord_id\", \"orderId\"),\n" +
            "      \"cust_name\", \"customerName\"),\n" +
            "    \"internal_note\"\n" +
            "  )\n" +
            "}";
        
        JsonNode result5 = engine.transformFromString(morph5, mapper.readTree(input5));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result5));
        System.out.println();
        
        System.out.println("=== All tests completed successfully! ===");
    }
}
