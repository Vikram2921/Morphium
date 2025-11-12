package com.morphium;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.morphium.core.MorphiumEngine;
import com.morphium.util.JsonUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class MorphiumEngineTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testFlattenAndRename() throws Exception {
        String transform = "{ fullName: $.person.first + \" \" + $.person.last, years: $.age }";
        
        ObjectNode input = JsonUtil.createObject();
        ObjectNode person = JsonUtil.createObject();
        person.put("first", "John");
        person.put("last", "Doe");
        input.set("person", person);
        input.put("age", 30);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        assertTrue(result.isObject());
        ObjectNode obj = (ObjectNode) result;
        assertEquals("John Doe", obj.get("fullName").asText());
        assertEquals(30, obj.get("years").asInt());
    }

    @Test
    public void testArrayLiterals() throws Exception {
        String transform = "[1, 2, 3, $.value]";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("value", 4);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        assertTrue(result.isArray());
        ArrayNode arr = (ArrayNode) result;
        assertEquals(4, arr.size());
        assertEquals(1, arr.get(0).asInt());
        assertEquals(4, arr.get(3).asInt());
    }

    @Test
    public void testArithmeticOperations() throws Exception {
        String transform = "{ sum: $.a + $.b, product: $.a * $.b }";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("a", 5);
        input.put("b", 3);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals(8, obj.get("sum").asInt());
        assertEquals(15, obj.get("product").asInt());
    }

    @Test
    public void testConditionalExpression() throws Exception {
        String transform = "{ status: $.age >= 18 ? \"adult\" : \"minor\" }";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("age", 20);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals("adult", obj.get("status").asText());
    }

    @Test
    public void testSafeNavigation() throws Exception {
        String transform = "{ city: $?.address?.city ?? \"Unknown\" }";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals("Unknown", obj.get("city").asText());
    }

    @Test
    public void testStringOperations() throws Exception {
        String transform = "{ upperName: upper($.name), lowerName: lower($.name) }";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("name", "John");

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals("JOHN", obj.get("upperName").asText());
        assertEquals("john", obj.get("lowerName").asText());
    }

    @Test
    public void testLengthFunction() throws Exception {
        String transform = "{ arrayLen: len($.items), stringLen: len($.text) }";
        
        ObjectNode input = JsonUtil.createObject();
        input.set("items", mapper.readTree("[1,2,3]"));
        input.put("text", "hello");

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals(3, obj.get("arrayLen").asInt());
        assertEquals(5, obj.get("stringLen").asInt());
    }

    @Test
    public void testExistsFunction() throws Exception {
        String transform = "{ hasName: exists($.name), hasAge: exists($.age) }";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("name", "John");

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertTrue(obj.get("hasName").asBoolean());
        assertFalse(obj.get("hasAge").asBoolean());
    }

    @Test
    public void testMergeFunction() throws Exception {
        String transform = "merge({a: 1}, {b: 2}, {c: 3})";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals(1, obj.get("a").asInt());
        assertEquals(2, obj.get("b").asInt());
        assertEquals(3, obj.get("c").asInt());
    }

    @Test
    public void testNowFunction() throws Exception {
        String transform = "{ timestamp: now() }";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertNotNull(obj.get("timestamp").asText());
        assertTrue(obj.get("timestamp").asText().contains("T"));
    }

    @Test
    public void testComputedPropertyKeys() throws Exception {
        String transform = "{ [$.keyName]: $.value }";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("keyName", "dynamicKey");
        input.put("value", 42);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = (ObjectNode) result;
        assertEquals(42, obj.get("dynamicKey").asInt());
    }
}
