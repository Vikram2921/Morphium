package com.morphium;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.morphium.core.MorphiumEngine;
import com.morphium.util.JsonUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDefinedFunctionsTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSimpleFunctionDefinition() throws Exception {
        String transform = 
            "function double(x) { return x * 2 }\n" +
            "{ result: double($.value) }";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("value", 5);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals(10, obj.get("result").asInt());
    }

    @Test
    public void testFunctionWithMultipleParameters() throws Exception {
        String transform = 
            "function add(a, b) { return a + b }\n" +
            "{ result: add($.x, $.y) }";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("x", 3);
        input.put("y", 7);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals(10, obj.get("result").asInt());
    }

    @Test
    public void testFunctionCallingAnotherFunction() throws Exception {
        String transform = 
            "function square(x) { return x * x }\n" +
            "function sumOfSquares(a, b) { return square(a) + square(b) }\n" +
            "{ result: sumOfSquares(3, 4) }";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals(25, obj.get("result").asInt());
    }

    @Test
    public void testFunctionWithLocalVariables() throws Exception {
        String transform = 
            "function calculate(x, y) {\n" +
            "  let sum = x + y\n" +
            "  let product = x * y\n" +
            "  return sum * product\n" +
            "}\n" +
            "{ result: calculate(3, 4) }";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals(84, obj.get("result").asInt());
    }

    @Test
    public void testGlobalVariable() throws Exception {
        String transform = 
            "global PI = 3.14159\n" +
            "function circleArea(r) { return PI * r * r }\n" +
            "{ area: circleArea(5) }";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertTrue(obj.get("area").asDouble() > 78 && obj.get("area").asDouble() < 79);
    }

    @Test
    public void testGlobalAndLocalVariables() throws Exception {
        String transform = 
            "global TAX_RATE = 0.1\n" +
            "function calculateTotal(price) {\n" +
            "  let tax = price * TAX_RATE\n" +
            "  return price + tax\n" +
            "}\n" +
            "{ total: calculateTotal(100) }";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals(110, obj.get("total").asInt());
    }

    @Test
    public void testFunctionInMapOperation() throws Exception {
        String transform = 
            "function double(x) { return x * 2 }\n" +
            "{ items: map($.numbers, \"n\", double(n)) }";
        
        ObjectNode input = JsonUtil.createObject();
        input.set("numbers", mapper.readTree("[1, 2, 3, 4]"));

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        ArrayNode items = (ArrayNode) obj.get("items");
        assertEquals(4, items.size());
        assertEquals(2, items.get(0).asInt());
        assertEquals(4, items.get(1).asInt());
        assertEquals(6, items.get(2).asInt());
        assertEquals(8, items.get(3).asInt());
    }

    @Test
    public void testArrowFunction() throws Exception {
        String transform = 
            "function double(x) => x * 2\n" +
            "{ result: double(5) }";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals(10, obj.get("result").asInt());
    }

    @Test
    public void testComplexScenario() throws Exception {
        String transform = 
            "global DISCOUNT = 0.15\n" +
            "function applyDiscount(price) {\n" +
            "  let discount = price * DISCOUNT\n" +
            "  return price - discount\n" +
            "}\n" +
            "function formatPrice(value) {\n" +
            "  return \"$\" + toString(value)\n" +
            "}\n" +
            "{\n" +
            "  items: map($.items, \"item\", {\n" +
            "    name: item.name,\n" +
            "    originalPrice: item.price,\n" +
            "    discountedPrice: applyDiscount(item.price),\n" +
            "    formatted: formatPrice(applyDiscount(item.price))\n" +
            "  })\n" +
            "}";
        
        ObjectNode input = JsonUtil.createObject();
        input.set("items", mapper.readTree("[{\"name\":\"A\",\"price\":100},{\"name\":\"B\",\"price\":200}]"));

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        ArrayNode items = (ArrayNode) obj.get("items");
        assertEquals(2, items.size());
        
        ObjectNode firstItem = (ObjectNode) items.get(0);
        assertEquals("A", firstItem.get("name").asText());
        assertEquals(100, firstItem.get("originalPrice").asInt());
        assertEquals(85, firstItem.get("discountedPrice").asInt());
        assertTrue(firstItem.get("formatted").asText().contains("85"));
    }

    @Test
    public void testAssignDollarToVariable() throws Exception {
        String transform = 
            "let root = $\n" +
            "let data = root.contextVariables\n" +
            "{\n" +
            "  name: root.name,\n" +
            "  value: data.value\n" +
            "}";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("name", "Test");
        ObjectNode contextVars = JsonUtil.createObject();
        contextVars.put("value", 42);
        input.set("contextVariables", contextVars);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals("Test", obj.get("name").asText());
        assertEquals(42, obj.get("value").asInt());
    }

    @Test
    public void testGlobalVariableWithDollar() throws Exception {
        String transform = 
            "let root = $\n" +
            "let config = root.config\n" +
            "{\n" +
            "  status: config.status,\n" +
            "  items: map(root.items, \"item\", {\n" +
            "    id: item.id,\n" +
            "    enabled: config.enableAll\n" +
            "  })\n" +
            "}";
        
        ObjectNode input = JsonUtil.createObject();
        ObjectNode config = JsonUtil.createObject();
        config.put("status", "active");
        config.put("enableAll", true);
        input.set("config", config);
        input.set("items", mapper.readTree("[{\"id\":\"A\"},{\"id\":\"B\"}]"));

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals("active", obj.get("status").asText());
        ArrayNode items = (ArrayNode) obj.get("items");
        assertEquals(2, items.size());
        assertTrue(((ObjectNode)items.get(0)).get("enabled").asBoolean());
    }
}
