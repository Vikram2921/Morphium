package com.morphium;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.morphium.core.MorphiumEngine;

import java.io.IOException;

public class Demo {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        MorphiumEngine engine = new MorphiumEngine();

        System.out.println("=== Morphium DSL Demo - Enhanced Features ===\n");

        // Demo 1: Simple transformation
        demo1(engine, gson);

        // Demo 2: Array operations
        demo2(engine, gson);

        // Demo 3: Safe navigation
        demo3(engine, gson);

        // Demo 4: String operations
        demo4(engine, gson);

        // Demo 5: User-defined functions
        demo5(engine, gson);

        // Demo 6: Global variables
        demo6(engine, gson);

        // Demo 7: Functions calling functions
        demo7(engine, gson);
    }

    private static void demo1(MorphiumEngine engine, Gson gson) {
        System.out.println("--- Demo 1: Flatten and Rename ---");
        
        String transform = "{ fullName: input.person.first + \" \" + input.person.last, years: input.age }";
        
        JsonObject input = new JsonObject();
        JsonObject person = new JsonObject();
        person.addProperty("first", "Alice");
        person.addProperty("last", "Smith");
        input.add("person", person);
        input.addProperty("age", 28);

        System.out.println("Input:");
        System.out.println(gson.toJson(input));
        
        JsonElement result = engine.transformFromString(transform, input);
        
        System.out.println("\nOutput:");
        System.out.println(gson.toJson(result));
        System.out.println();
    }

    private static void demo2(MorphiumEngine engine, Gson gson) {
        System.out.println("--- Demo 2: Array Operations ---");
        
        String transform = "{ " +
            "items: map(input.items, \"it\", { id: it.id, total: it.qty * it.price })" +
            " }";
        
        String inputJson = "{ \"items\": [" +
            "{ \"id\": \"A\", \"qty\": 2, \"price\": 10 }," +
            "{ \"id\": \"B\", \"qty\": 3, \"price\": 5 }," +
            "{ \"id\": \"C\", \"qty\": 1, \"price\": 20 }" +
            "] }";
        
        JsonObject input = gson.fromJson(inputJson, JsonObject.class);
        
        System.out.println("Input:");
        System.out.println(gson.toJson(input));
        
        JsonElement result = engine.transformFromString(transform, input);
        
        System.out.println("\nOutput:");
        System.out.println(gson.toJson(result));
        System.out.println();
    }

    private static void demo3(MorphiumEngine engine, Gson gson) {
        System.out.println("--- Demo 3: Safe Navigation ---");
        
        String transform = "{ " +
            "customerName: input.order?.customer?.name ?? \"Unknown\", " +
            "itemCount: len(input.order?.items ?? []) " +
            "}";
        
        JsonObject input = new JsonObject();
        JsonObject order = new JsonObject();
        input.add("order", order);
        
        System.out.println("Input:");
        System.out.println(gson.toJson(input));
        
        JsonElement result = engine.transformFromString(transform, input);
        
        System.out.println("\nOutput:");
        System.out.println(gson.toJson(result));
        System.out.println();
    }

    private static void demo4(MorphiumEngine engine, Gson gson) {
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
        
        JsonObject input = gson.fromJson(inputJson, JsonObject.class);
        
        System.out.println("Input:");
        System.out.println(gson.toJson(input));
        
        JsonElement result = engine.transformFromString(transform, input);
        
        System.out.println("\nOutput:");
        System.out.println(gson.toJson(result));
        System.out.println();
    }

    private static void demo5(MorphiumEngine engine, Gson gson) {
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
        
        JsonObject input = gson.fromJson(inputJson, JsonObject.class);
        
        System.out.println("Input:");
        System.out.println(gson.toJson(input));
        
        JsonElement result = engine.transformFromString(transform, input);
        
        System.out.println("\nOutput:");
        System.out.println(gson.toJson(result));
        System.out.println();
    }

    private static void demo6(MorphiumEngine engine, Gson gson) {
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
        
        JsonObject input = gson.fromJson(inputJson, JsonObject.class);
        
        System.out.println("Input:");
        System.out.println(gson.toJson(input));
        
        JsonElement result = engine.transformFromString(transform, input);
        
        System.out.println("\nOutput:");
        System.out.println(gson.toJson(result));
        System.out.println();
    }

    private static void demo7(MorphiumEngine engine, Gson gson) {
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
        
        JsonObject input = new JsonObject();
        input.addProperty("value", 5);
        
        System.out.println("Input:");
        System.out.println(gson.toJson(input));
        
        JsonElement result = engine.transformFromString(transform, input);
        
        System.out.println("\nOutput:");
        System.out.println(gson.toJson(result));
        System.out.println();
    }
}
