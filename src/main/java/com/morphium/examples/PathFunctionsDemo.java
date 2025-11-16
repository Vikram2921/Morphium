package com.morphium.examples;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;

public class PathFunctionsDemo {
    public static void main(String[] args) throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        ObjectMapper mapper = new ObjectMapper();
        
        System.out.println("=== Deep Path Operations Demo ===\n");
        
        // Test 1: getIn with dot notation
        System.out.println("Test 1: getIn() - Deep property access");
        String input1 = "{\n" +
            "  \"user\": {\n" +
            "    \"profile\": {\n" +
            "      \"name\": \"Alice\",\n" +
            "      \"address\": {\n" +
            "        \"city\": \"NYC\",\n" +
            "        \"country\": \"USA\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
        
        String morph1 = "{\n" +
            "  city: getIn($, \"user.profile.address.city\"),\n" +
            "  state: getIn($, \"user.profile.address.state\", \"Unknown\")\n" +
            "}";
        
        JsonNode result1 = engine.transformFromString(morph1, mapper.readTree(input1));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result1));
        System.out.println();
        
        // Test 2: getIn with array paths
        System.out.println("Test 2: getIn() - Array path syntax");
        String input2 = "{\n" +
            "  \"users\": [\n" +
            "    {\"name\": \"Alice\", \"age\": 25},\n" +
            "    {\"name\": \"Bob\", \"age\": 30}\n" +
            "  ]\n" +
            "}";
        
        String morph2 = "{\n" +
            "  firstUser: getIn($, [\"users\", \"0\", \"name\"]),\n" +
            "  secondUser: getIn($, \"users.1.name\")\n" +
            "}";
        
        JsonNode result2 = engine.transformFromString(morph2, mapper.readTree(input2));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result2));
        System.out.println();
        
        // Test 3: setIn - Creating nested structures
        System.out.println("Test 3: setIn() - Auto-create nested paths");
        String input3 = "{\n" +
            "  \"data\": {}\n" +
            "}";
        
        String morph3 = "{\n" +
            "  result: setIn(setIn(setIn($.data,\n" +
            "    \"user.name\", \"Charlie\"),\n" +
            "    \"user.age\", 35),\n" +
            "    \"user.address.city\", \"LA\")\n" +
            "}";
        
        JsonNode result3 = engine.transformFromString(morph3, mapper.readTree(input3));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result3));
        System.out.println();
        
        // Test 4: deleteIn
        System.out.println("Test 4: deleteIn() - Remove nested properties");
        String input4 = "{\n" +
            "  \"user\": {\n" +
            "    \"name\": \"David\",\n" +
            "    \"email\": \"david@example.com\",\n" +
            "    \"password\": \"secret123\",\n" +
            "    \"internalId\": \"INT-456\"\n" +
            "  }\n" +
            "}";
        
        String morph4 = "{\n" +
            "  user: deleteIn(deleteIn($.user, \"password\"), \"internalId\")\n" +
            "}";
        
        JsonNode result4 = engine.transformFromString(morph4, mapper.readTree(input4));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result4));
        System.out.println();
        
        // Test 5: hasPath
        System.out.println("Test 5: hasPath() - Check path existence");
        String input5 = "{\n" +
            "  \"config\": {\n" +
            "    \"database\": {\n" +
            "      \"host\": \"localhost\",\n" +
            "      \"port\": 5432\n" +
            "    }\n" +
            "  }\n" +
            "}";
        
        String morph5 = "{\n" +
            "  hasHost: hasPath($, \"config.database.host\"),\n" +
            "  hasUser: hasPath($, \"config.database.user\"),\n" +
            "  hasPort: hasPath($, \"config.database.port\")\n" +
            "}";
        
        JsonNode result5 = engine.transformFromString(morph5, mapper.readTree(input5));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result5));
        System.out.println();
        
        // Test 6: getPaths - Multiple paths at once
        System.out.println("Test 6: getPaths() - Get multiple values");
        String input6 = "{\n" +
            "  \"order\": {\n" +
            "    \"id\": 123,\n" +
            "    \"total\": 99.99,\n" +
            "    \"status\": \"shipped\"\n" +
            "  }\n" +
            "}";
        
        String morph6 = "{\n" +
            "  values: getPaths($.order, [\"id\", \"total\", \"customer\", \"status\"])\n" +
            "}";
        
        JsonNode result6 = engine.transformFromString(morph6, mapper.readTree(input6));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result6));
        System.out.println();
        
        // Test 7: Path utilities
        System.out.println("Test 7: Path utilities - normalizePath, pathDepth");
        String input7 = "{}";
        
        String morph7 = "{\n" +
            "  normalized: normalizePath(\"user.address.city\"),\n" +
            "  depth: pathDepth(\"user.address.city.zipcode\")\n" +
            "}";
        
        JsonNode result7 = engine.transformFromString(morph7, mapper.readTree(input7));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result7));
        System.out.println();
        
        // Test 8: Complex real-world scenario
        System.out.println("Test 8: Complex API transformation");
        String input8 = "{\n" +
            "  \"response\": {\n" +
            "    \"data\": {\n" +
            "      \"users\": [\n" +
            "        {\"id\": 1, \"name\": \"Alice\", \"email\": \"alice@example.com\"},\n" +
            "        {\"id\": 2, \"name\": \"Bob\", \"email\": \"bob@example.com\"}\n" +
            "      ],\n" +
            "      \"metadata\": {\n" +
            "        \"total\": 2,\n" +
            "        \"page\": 1\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
        
        String morph8 = "{\n" +
            "  users: getIn($, \"response.data.users\", []),\n" +
            "  total: getIn($, \"response.data.metadata.total\", 0),\n" +
            "  hasNextPage: hasPath($, \"response.data.metadata.nextPage\"),\n" +
            "  firstUser: getIn($, \"response.data.users.0.name\", \"N/A\")\n" +
            "}";
        
        JsonNode result8 = engine.transformFromString(morph8, mapper.readTree(input8));
        System.out.println("Output: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result8));
        System.out.println();
        
        System.out.println("=== All path operations completed successfully! ===");
    }
}
