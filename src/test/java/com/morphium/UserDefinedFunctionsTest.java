package com.morphium;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.morphium.core.MorphiumEngine;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDefinedFunctionsTest {

    private final Gson gson = new Gson();

    @Test
    public void testSimpleFunction() {
        String transform = 
            "function double(x) { return x * 2 }\n" +
            "{ result: double(input.value) }";
        
        JsonObject input = new JsonObject();
        input.addProperty("value", 5);

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals(10, obj.get("result").getAsInt());
    }

    @Test
    public void testFunctionWithMultipleParameters() {
        String transform = 
            "function add(a, b) { return a + b }\n" +
            "{ sum: add(input.x, input.y) }";
        
        JsonObject input = new JsonObject();
        input.addProperty("x", 10);
        input.addProperty("y", 20);

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals(30, obj.get("sum").getAsInt());
    }

    @Test
    public void testFunctionCallingFunction() {
        String transform = 
            "function square(x) { return x * x }\n" +
            "function sumOfSquares(a, b) { return square(a) + square(b) }\n" +
            "{ result: sumOfSquares(3, 4) }";
        
        JsonObject input = new JsonObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals(25, obj.get("result").getAsInt()); // 9 + 16
    }

    @Test
    public void testFunctionWithLocalVariables() {
        String transform = 
            "function calculate(x) {\n" +
            "  let doubled = x * 2\n" +
            "  let squared = doubled * doubled\n" +
            "  return squared\n" +
            "}\n" +
            "{ result: calculate(3) }";
        
        JsonObject input = new JsonObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals(36, obj.get("result").getAsInt()); // (3*2)^2 = 36
    }

    @Test
    public void testGlobalVariable() {
        String transform = 
            "global PI = 3.14159\n" +
            "function circleArea(radius) { return PI * radius * radius }\n" +
            "{ area: circleArea(10) }";
        
        JsonObject input = new JsonObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        double area = obj.get("area").getAsDouble();
        assertTrue(area > 314 && area < 315);
    }

    @Test
    public void testGlobalAndLocalVariables() {
        String transform = 
            "global RATE = 0.1\n" +
            "function calculateTax(amount) {\n" +
            "  let tax = amount * RATE\n" +
            "  return tax\n" +
            "}\n" +
            "{ tax: calculateTax(100) }";
        
        JsonObject input = new JsonObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals(10.0, obj.get("tax").getAsDouble(), 0.01);
    }

    @Test
    public void testFunctionInMap() {
        String transform = 
            "function double(x) { return x * 2 }\n" +
            "{ doubled: map(input.numbers, \"n\", double(n)) }";
        
        JsonObject input = new JsonObject();
        input.add("numbers", gson.fromJson("[1,2,3,4,5]", JsonElement.class));

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals(5, obj.get("doubled").getAsJsonArray().size());
        assertEquals(2, obj.get("doubled").getAsJsonArray().get(0).getAsInt());
        assertEquals(10, obj.get("doubled").getAsJsonArray().get(4).getAsInt());
    }

    @Test
    public void testArrowFunction() {
        String transform = 
            "function double(x) => x * 2\n" +
            "{ result: double(21) }";
        
        JsonObject input = new JsonObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals(42, obj.get("result").getAsInt());
    }

    @Test
    public void testComplexFunctionScenario() {
        String transform = 
            "global TAX_RATE = 0.08\n" +
            "function calculateSubtotal(price, qty) { return price * qty }\n" +
            "function calculateTax(subtotal) { return subtotal * TAX_RATE }\n" +
            "function calculateTotal(price, qty) {\n" +
            "  let subtotal = calculateSubtotal(price, qty)\n" +
            "  let tax = calculateTax(subtotal)\n" +
            "  return subtotal + tax\n" +
            "}\n" +
            "{\n" +
            "  items: map(input.items, \"item\", {\n" +
            "    name: item.name,\n" +
            "    total: calculateTotal(item.price, item.qty)\n" +
            "  })\n" +
            "}";
        
        String inputJson = "{ \"items\": [" +
            "{ \"name\": \"A\", \"price\": 10, \"qty\": 2 }," +
            "{ \"name\": \"B\", \"price\": 20, \"qty\": 1 }" +
            "] }";
        
        JsonObject input = gson.fromJson(inputJson, JsonObject.class);

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals(2, obj.get("items").getAsJsonArray().size());
        
        // First item: (10 * 2) * 1.08 = 21.6
        double total1 = obj.get("items").getAsJsonArray().get(0)
            .getAsJsonObject().get("total").getAsDouble();
        assertEquals(21.6, total1, 0.01);
        
        // Second item: (20 * 1) * 1.08 = 21.6
        double total2 = obj.get("items").getAsJsonArray().get(1)
            .getAsJsonObject().get("total").getAsDouble();
        assertEquals(21.6, total2, 0.01);
    }
}
