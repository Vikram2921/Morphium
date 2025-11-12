package com.morphium;

import com.morphium.util.JsonUtil;

import com.fasterxml.jackson.databind.node.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
        
        ObjectNode input = JsonUtil.createObject();
        input.put("value", 5);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals(10, obj.get("result").asInt());
    }

    @Test
    public void testFunctionWithMultipleParameters() {
        String transform = 
            "function add(a, b) { return a + b }\n" +
            "{ sum: add(input.x, input.y) }";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("x", 10);
        input.put("y", 20);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals(30, obj.get("sum").asInt());
    }

    @Test
    public void testFunctionCallingFunction() {
        String transform = 
            "function square(x) { return x * x }\n" +
            "function sumOfSquares(a, b) { return square(a) + square(b) }\n" +
            "{ result: sumOfSquares(3, 4) }";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals(25, obj.get("result").asInt()); // 9 + 16
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
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals(36, obj.get("result").asInt()); // (3*2)^2 = 36
    }

    @Test
    public void testGlobalVariable() {
        String transform = 
            "global PI = 3.14159\n" +
            "function circleArea(radius) { return PI * radius * radius }\n" +
            "{ area: circleArea(10) }";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        double area = obj.get("area").asDouble();
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
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals(10.0, obj.get("tax").asDouble(), 0.01);
    }

    @Test
    public void testFunctionInMap() {
        String transform = 
            "function double(x) { return x * 2 }\n" +
            "{ doubled: map(input.numbers, \"n\", double(n)) }";
        
        ObjectNode input = JsonUtil.createObject();
        input.set("numbers", gson.fromJson("[1,2,3,4,5]", JsonNode.class));

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals(5, obj.get("doubled").asJsonArray().size());
        assertEquals(2, obj.get("doubled").asJsonArray().get(0).asInt());
        assertEquals(10, obj.get("doubled").asJsonArray().get(4).asInt());
    }

    @Test
    public void testArrowFunction() {
        String transform = 
            "function double(x) => x * 2\n" +
            "{ result: double(21) }";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals(42, obj.get("result").asInt());
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
        
        ObjectNode input = gson.fromJson(inputJson, ObjectNode.class);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals(2, obj.get("items").asJsonArray().size());
        
        // First item: (10 * 2) * 1.08 = 21.6
        double total1 = obj.get("items").asJsonArray().get(0)
            .asJsonObject().get("total").asDouble();
        assertEquals(21.6, total1, 0.01);
        
        // Second item: (20 * 1) * 1.08 = 21.6
        double total2 = obj.get("items").asJsonArray().get(1)
            .asJsonObject().get("total").asDouble();
        assertEquals(21.6, total2, 0.01);
    }
}
