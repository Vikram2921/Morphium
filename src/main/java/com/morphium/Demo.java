package com.morphium;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.morphium.core.MorphiumEngine;
import com.morphium.util.JsonUtil;

public class Demo {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        MorphiumEngine engine = new MorphiumEngine();

        System.out.println("=== Morphium DSL Demo - Enhanced Features ===\n");

        demo1(engine, mapper);
        demo2(engine, mapper);
        demo3(engine, mapper);
        demo4(engine, mapper);
        demo5(engine, mapper);
        demo6(engine, mapper);
        demo7(engine, mapper);
    }

    private static void demo1(MorphiumEngine engine, ObjectMapper mapper) {
        System.out.println("--- Demo 1: Flatten and Rename ---");
        
        String transform = "{ fullName: input.person.first + \" \" + input.person.last, years: input.age }";
        
        ObjectNode input = JsonUtil.createObject();
        ObjectNode person = JsonUtil.createObject();
        person.put("first", "Alice");
        person.put("last", "Smith");
        input.set("person", person);
        input.put("age", 28);

        System.out.println("Input:");
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JsonNode result = engine.transformFromString(transform, input);
        
        System.out.println("\nOutput:");
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void demo2(MorphiumEngine engine, ObjectMapper mapper) {
        System.out.println("--- Demo 2: Array Operations ---");
        
        String transform = "{ " +
            "items: map(input.items, \"it\", { id: it.id, total: it.qty * it.price })" +
            " }";
        
        String inputJson = "{ \"items\": [" +
            "{ \"id\": \"A\", \"qty\": 2, \"price\": 10 }," +
            "{ \"id\": \"B\", \"qty\": 3, \"price\": 5 }," +
            "{ \"id\": \"C\", \"qty\": 1, \"price\": 20 }" +
            "] }";
        
        try {
            ObjectNode input = (ObjectNode) mapper.readTree(inputJson);
            
            System.out.println("Input:");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
            
            JsonNode result = engine.transformFromString(transform, input);
            
            System.out.println("\nOutput:");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void demo3(MorphiumEngine engine, ObjectMapper mapper) {
        System.out.println("--- Demo 3: Safe Navigation ---");
        
        String transform = "{ " +
            "customerName: input.order?.customer?.name ?? \"Unknown\", " +
            "itemCount: len(input.order?.items ?? []) " +
            "}";
        
        ObjectNode input = JsonUtil.createObject();
        ObjectNode order = JsonUtil.createObject();
        input.set("order", order);
        
        System.out.println("Input:");
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JsonNode result = engine.transformFromString(transform, input);
        
        System.out.println("\nOutput:");
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void demo4(MorphiumEngine engine, ObjectMapper mapper) {
        System.out.println("--- Demo 4: String Operations ---");
        
        String transform = "{ " +
            "upperName: upper(input.name), " +
            "lowerName: lower(input.name), " +
            "parts: split(input.email, \"@\"), " +
            "tagString: join(input.tags, \", \") " +
            "}";
        
        String inputJson = "{ " +
            "\"name\": \"John Doe\", " +
            "\"email\": \"john@example.com\", " +
            "\"tags\": [\"developer\", \"java\", \"morphium\"] " +
            "}";
        
        try {
            ObjectNode input = (ObjectNode) mapper.readTree(inputJson);
            
            System.out.println("Input:");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
            
            JsonNode result = engine.transformFromString(transform, input);
            
            System.out.println("\nOutput:");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void demo5(MorphiumEngine engine, ObjectMapper mapper) {
        System.out.println("--- Demo 5: User-Defined Functions ---");
        
        String transform = 
            "function calculateTax(amount, rate) { return amount * rate }\n" +
            "function formatPrice(value) { return \"$\" + toString(toNumber(value)) }\n" +
            "{\n" +
            "  items: map(input.items, \"item\", {\n" +
            "    id: item.id,\n" +
            "    price: item.price,\n" +
            "    tax: calculateTax(item.price, 0.1),\n" +
            "    total: item.price + calculateTax(item.price, 0.1),\n" +
            "    formatted: formatPrice(item.price + calculateTax(item.price, 0.1))\n" +
            "  })\n" +
            "}";
        
        String inputJson = "{ \"items\": [" +
            "{ \"id\": \"A\", \"price\": 100 }," +
            "{ \"id\": \"B\", \"price\": 200 }" +
            "] }";
        
        try {
            ObjectNode input = (ObjectNode) mapper.readTree(inputJson);
            
            System.out.println("Input:");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
            
            JsonNode result = engine.transformFromString(transform, input);
            
            System.out.println("\nOutput:");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void demo6(MorphiumEngine engine, ObjectMapper mapper) {
        System.out.println("--- Demo 6: Global Variables ---");
        
        String transform = 
            "global PI = 3.14159\n" +
            "function calculateCircleArea(radius) {\n" +
            "  let squared = radius * radius\n" +
            "  return PI * squared\n" +
            "}\n" +
            "{\n" +
            "  circles: map(input.circles, \"c\", {\n" +
            "    radius: c.radius,\n" +
            "    area: calculateCircleArea(c.radius)\n" +
            "  })\n" +
            "}";
        
        String inputJson = "{ \"circles\": [" +
            "{ \"radius\": 5 }," +
            "{ \"radius\": 10 }" +
            "] }";
        
        try {
            ObjectNode input = (ObjectNode) mapper.readTree(inputJson);
            
            System.out.println("Input:");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
            
            JsonNode result = engine.transformFromString(transform, input);
            
            System.out.println("\nOutput:");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void demo7(MorphiumEngine engine, ObjectMapper mapper) {
        System.out.println("--- Demo 7: Functions Calling Functions ---");
        
        String transform = 
            "function square(x) { return x * x }\n" +
            "function cube(x) { return x * square(x) }\n" +
            "function sumOfSquares(a, b) { return square(a) + square(b) }\n" +
            "{\n" +
            "  value: input.value,\n" +
            "  squared: square(input.value),\n" +
            "  cubed: cube(input.value),\n" +
            "  sumOfSquares: sumOfSquares(3, 4)\n" +
            "}";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("value", 5);
        
        System.out.println("Input:");
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JsonNode result = engine.transformFromString(transform, input);
        
        System.out.println("\nOutput:");
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
