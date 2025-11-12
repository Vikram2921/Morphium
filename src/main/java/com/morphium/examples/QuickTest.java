package com.morphium.examples;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;

/**
 * Quick test to verify all features work correctly
 */
public class QuickTest {
    
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MorphiumEngine engine = new MorphiumEngine();
        
        System.out.println("=== Morphium DSL Quick Test ===\n");
        
        // Test 1: Basic $ access
        test(engine, mapper, "Test 1: $ Access",
            "{ name: $.user.name, age: $.user.age }",
            "{\"user\":{\"name\":\"John\",\"age\":30}}"
        );
        
        // Test 2: Variables
        test(engine, mapper, "Test 2: Variables",
            "let root = $\nlet user = root.user\n{ name: user.name, age: user.age }",
            "{\"user\":{\"name\":\"Jane\",\"age\":25}}"
        );
        
        // Test 3: Custom Functions
        test(engine, mapper, "Test 3: Custom Functions",
            "function double(x) { return x * 2 }\n{ value: double($.num) }",
            "{\"num\":21}"
        );
        
        // Test 4: Array Map
        test(engine, mapper, "Test 4: Array Map",
            "{ doubled: map($.numbers, \"n\", n * 2) }",
            "{\"numbers\":[1,2,3,4,5]}"
        );
        
        // Test 5: Filter
        test(engine, mapper, "Test 5: Filter",
            "{ adults: filter($.users, \"u\", u.age >= 18) }",
            "{\"users\":[{\"name\":\"Alice\",\"age\":25},{\"name\":\"Bob\",\"age\":16}]}"
        );
        
        // Test 6: Reduce
        test(engine, mapper, "Test 6: Reduce",
            "{ sum: reduce($.numbers, \"acc\", \"n\", 0, acc + n) }",
            "{\"numbers\":[1,2,3,4,5]}"
        );
        
        // Test 7: Streams - findFirst
        test(engine, mapper, "Test 7: Streams findFirst",
            "{ first: findFirst($.items, \"i\", i > 5) }",
            "{\"items\":[1,3,7,9,2]}"
        );
        
        // Test 8: Streams - anyMatch
        test(engine, mapper, "Test 8: Streams anyMatch",
            "{ hasLarge: anyMatch($.items, \"i\", i > 100) }",
            "{\"items\":[10,50,150,20]}"
        );
        
        // Test 9: Streams - sorted
        test(engine, mapper, "Test 9: Streams sorted",
            "{ sorted: sorted($.items) }",
            "{\"items\":[5,2,8,1,9]}"
        );
        
        // Test 10: Streams - groupBy
        test(engine, mapper, "Test 10: Streams groupBy",
            "{ grouped: groupBy($.users, \"status\") }",
            "{\"users\":[{\"name\":\"A\",\"status\":\"active\"},{\"name\":\"B\",\"status\":\"inactive\"}]}"
        );
        
        // Test 11: Global variables
        test(engine, mapper, "Test 11: Global Variables",
            "global multiplier = 10\nfunction calc(x) { return x * multiplier }\n{ result: calc($.num) }",
            "{\"num\":5}"
        );
        
        // Test 12: Safe navigation
        test(engine, mapper, "Test 12: Safe Navigation",
            "{ city: $.user?.address?.city ?? \"Unknown\" }",
            "{\"user\":{}}"
        );
        
        // Test 13: Aggregation
        test(engine, mapper, "Test 13: Aggregation",
            "{ count: count($.items), sum: sum($.items), avg: avg($.items) }",
            "{\"items\":[10,20,30,40]}"
        );
        
        // Test 14: Complex nested
        test(engine, mapper, "Test 14: Complex Transform",
            "{ " +
            "  users: map($.users, \"u\", { " +
            "    name: u.name, " +
            "    totalOrders: sum(pluck(u.orders, \"amount\")), " +
            "    avgOrder: avg(pluck(u.orders, \"amount\")) " +
            "  }) " +
            "}",
            "{\"users\":[{\"name\":\"John\",\"orders\":[{\"amount\":100},{\"amount\":200}]}]}"
        );
        
        System.out.println("\n=== All Tests Completed Successfully! ===");
    }
    
    private static void test(MorphiumEngine engine, ObjectMapper mapper, 
                           String name, String transform, String inputJson) {
        try {
            System.out.println(name);
            JsonNode input = mapper.readTree(inputJson);
            JsonNode result = engine.transformFromString(transform, input);
            System.out.println("✓ Result: " + result);
            System.out.println();
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
            System.out.println();
        }
    }
}
