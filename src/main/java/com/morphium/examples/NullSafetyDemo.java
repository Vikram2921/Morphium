package com.morphium.examples;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;

public class NullSafetyDemo {
    public static void main(String[] args) throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        ObjectMapper mapper = new ObjectMapper();
        
        System.out.println("=== Null Safety Functions Demo ===\n");
        
        // Test 1: coalesce
        System.out.println("Test 1: coalesce() - First non-null value");
        String input1 = "{\n" +
            "  \"user\": {\n" +
            "    \"nickname\": null,\n" +
            "    \"firstName\": \"Alice\",\n" +
            "    \"username\": \"alice123\"\n" +
            "  }\n" +
            "}";
        
        String morph1 = "{\n" +
            "  name: coalesce($.user.nickname, $.user.firstName, $.user.username, \"Guest\")\n" +
            "}";
        
        JsonNode result1 = engine.transformFromString(morph1, mapper.readTree(input1));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result1));
        System.out.println();
        
        // Test 2: ifNull
        System.out.println("Test 2: ifNull() - Value with fallback");
        String input2 = "{\n" +
            "  \"product\": {\n" +
            "    \"name\": \"Widget\",\n" +
            "    \"discount\": null\n" +
            "  }\n" +
            "}";
        
        String morph2 = "{\n" +
            "  product: {\n" +
            "    name: $.product.name,\n" +
            "    discount: ifNull($.product.discount, 0)\n" +
            "  }\n" +
            "}";
        
        JsonNode result2 = engine.transformFromString(morph2, mapper.readTree(input2));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result2));
        System.out.println();
        
        // Test 3: safeGet
        System.out.println("Test 3: safeGet() - Safe property access");
        String input3 = "{\n" +
            "  \"user\": {\n" +
            "    \"name\": \"Bob\"\n" +
            "  }\n" +
            "}";
        
        String morph3 = "{\n" +
            "  city: safeGet($.user, \"address.city\"),\n" +
            "  name: safeGet($.user, \"name\")\n" +
            "}";
        
        JsonNode result3 = engine.transformFromString(morph3, mapper.readTree(input3));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result3));
        System.out.println();
        
        // Test 4: tryGet with default
        System.out.println("Test 4: tryGet() - Safe access with default");
        String input4 = "{\n" +
            "  \"order\": {\n" +
            "    \"id\": 123\n" +
            "  }\n" +
            "}";
        
        String morph4 = "{\n" +
            "  orderId: tryGet($.order, \"id\", 0),\n" +
            "  status: tryGet($.order, \"status\", \"pending\"),\n" +
            "  total: tryGet($.order, \"total\", 0)\n" +
            "}";
        
        JsonNode result4 = engine.transformFromString(morph4, mapper.readTree(input4));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result4));
        System.out.println();
        
        // Test 5: removeNulls
        System.out.println("Test 5: removeNulls() - Remove null properties");
        String input5 = "{\n" +
            "  \"user\": {\n" +
            "    \"name\": \"Charlie\",\n" +
            "    \"age\": null,\n" +
            "    \"email\": \"charlie@example.com\",\n" +
            "    \"phone\": null\n" +
            "  }\n" +
            "}";
        
        String morph5 = "{\n" +
            "  user: removeNulls($.user)\n" +
            "}";
        
        JsonNode result5 = engine.transformFromString(morph5, mapper.readTree(input5));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result5));
        System.out.println();
        
        // Test 6: replaceNulls
        System.out.println("Test 6: replaceNulls() - Replace nulls with default");
        String input6 = "{\n" +
            "  \"config\": {\n" +
            "    \"timeout\": 5000,\n" +
            "    \"retries\": null,\n" +
            "    \"debug\": null\n" +
            "  }\n" +
            "}";
        
        String morph6 = "{\n" +
            "  config: replaceNulls($.config, 0)\n" +
            "}";
        
        JsonNode result6 = engine.transformFromString(morph6, mapper.readTree(input6));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result6));
        System.out.println();
        
        // Test 7: nullIf
        System.out.println("Test 7: nullIf() - Conditional null");
        String input7 = "{\n" +
            "  \"user\": {\n" +
            "    \"name\": \"\",\n" +
            "    \"status\": \"unknown\"\n" +
            "  }\n" +
            "}";
        
        String morph7 = "{\n" +
            "  name: nullIf($.user.name, \"\"),\n" +
            "  status: nullIf($.user.status, \"unknown\")\n" +
            "}";
        
        JsonNode result7 = engine.transformFromString(morph7, mapper.readTree(input7));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result7));
        System.out.println();
        
        // Test 8: Complex real-world example
        System.out.println("Test 8: Complex API response handling");
        String input8 = "{\n" +
            "  \"data\": {\n" +
            "    \"user\": {\n" +
            "      \"id\": 456,\n" +
            "      \"preferredName\": null,\n" +
            "      \"firstName\": \"David\",\n" +
            "      \"contact\": {\n" +
            "        \"email\": \"david@example.com\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
        
        String morph8 = "{\n" +
            "  userId: coalesce(safeGet($.data, \"user.id\"), 0),\n" +
            "  userName: coalesce($.data.user.preferredName, $.data.user.firstName, \"Guest\"),\n" +
            "  email: tryGet($.data, \"user.contact.email\", \"no-email\"),\n" +
            "  phone: safeGet($.data, \"user.contact.phone\")\n" +
            "}";
        
        JsonNode result8 = engine.transformFromString(morph8, mapper.readTree(input8));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result8));
        System.out.println();
        
        System.out.println("=== All null safety tests completed successfully! ===");
    }
}
