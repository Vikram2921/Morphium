package com.morphium;

import com.morphium.util.JsonUtil;

import com.fasterxml.jackson.databind.node.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.morphium.core.MorphiumEngine;
import org.junit.Test;

import static org.junit.Assert.*;

public class MorphiumEngineTest {

    private final Gson gson = new Gson();

    @Test
    public void testFlattenAndRename() {
        String transform = "{ fullName: input.person.first + \" \" + input.person.last, years: input.age }";
        
        ObjectNode input = JsonUtil.createObject();
        ObjectNode person = JsonUtil.createObject();
        person.put("first", "John");
        person.put("last", "Doe");
        input.set("person", person);
        input.put("age", 30);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        assertTrue(result.isObject());
        ObjectNode obj = result.asJsonObject();
        assertEquals("John Doe", obj.get("fullName").asText());
        assertEquals(30, obj.get("years").asInt());
    }

    @Test
    public void testArrayLiterals() {
        String transform = "[1, 2, 3, input.value]";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("value", 4);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        assertTrue(result.isArray());
        assertEquals(4, result.asJsonArray().size());
        assertEquals(4, result.asJsonArray().get(3).asInt());
    }

    @Test
    public void testArithmeticOperations() {
        String transform = "{ sum: input.a + input.b, product: input.a * input.b }";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("a", 10);
        input.put("b", 5);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals(15, obj.get("sum").asInt());
        assertEquals(50, obj.get("product").asInt());
    }

    @Test
    public void testConditionalExpression() {
        String transform = "{ status: input.age >= 18 ? \"adult\" : \"minor\" }";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("age", 20);

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals("adult", obj.get("status").asText());
    }

    @Test
    public void testSafeNavigation() {
        String transform = "{ name: input?.user?.name ?? \"Unknown\" }";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals("Unknown", obj.get("name").asText());
    }

    @Test
    public void testStringOperations() {
        String transform = "{ upper: upper(input.text), lower: lower(input.text) }";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("text", "Hello World");

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals("HELLO WORLD", obj.get("upper").asText());
        assertEquals("hello world", obj.get("lower").asText());
    }

    @Test
    public void testLen() {
        String transform = "{ arrayLen: len(input.items), stringLen: len(input.text) }";
        
        ObjectNode input = JsonUtil.createObject();
        input.set("items", gson.fromJson("[1,2,3]", JsonNode.class));
        input.put("text", "hello");

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals(3, obj.get("arrayLen").asInt());
        assertEquals(5, obj.get("stringLen").asInt());
    }

    @Test
    public void testExists() {
        String transform = "{ hasName: exists(input.name), hasAge: exists(input.age) }";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("name", "John");

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertTrue(obj.get("hasName").asBoolean());
        assertFalse(obj.get("hasAge").asBoolean());
    }

    @Test
    public void testMerge() {
        String transform = "merge({a: 1}, {b: 2}, {c: 3})";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals(1, obj.get("a").asInt());
        assertEquals(2, obj.get("b").asInt());
        assertEquals(3, obj.get("c").asInt());
    }

    @Test
    public void testNow() {
        String transform = "{ timestamp: now() }";
        
        ObjectNode input = JsonUtil.createObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertNotNull(obj.get("timestamp"));
        assertTrue(obj.get("timestamp").asText().contains("T"));
    }

    @Test
    public void testComputedPropertyKeys() {
        String transform = "{ [input.keyName]: \"value\" }";
        
        ObjectNode input = JsonUtil.createObject();
        input.put("keyName", "dynamicKey");

        MorphiumEngine engine = new MorphiumEngine();
        JsonNode result = engine.transformFromString(transform, input);

        ObjectNode obj = result.asJsonObject();
        assertEquals("value", obj.get("dynamicKey").asText());
    }
}
