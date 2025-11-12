package com.morphium;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.morphium.core.MorphiumEngine;
import org.junit.Test;

import static org.junit.Assert.*;

public class MorphiumEngineTest {

    private final Gson gson = new Gson();

    @Test
    public void testFlattenAndRename() {
        String transform = "{ fullName: input.person.first + \" \" + input.person.last, years: input.age }";
        
        JsonObject input = new JsonObject();
        JsonObject person = new JsonObject();
        person.addProperty("first", "John");
        person.addProperty("last", "Doe");
        input.add("person", person);
        input.addProperty("age", 30);

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        assertTrue(result.isJsonObject());
        JsonObject obj = result.getAsJsonObject();
        assertEquals("John Doe", obj.get("fullName").getAsString());
        assertEquals(30, obj.get("years").getAsInt());
    }

    @Test
    public void testArrayLiterals() {
        String transform = "[1, 2, 3, input.value]";
        
        JsonObject input = new JsonObject();
        input.addProperty("value", 4);

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        assertTrue(result.isJsonArray());
        assertEquals(4, result.getAsJsonArray().size());
        assertEquals(4, result.getAsJsonArray().get(3).getAsInt());
    }

    @Test
    public void testArithmeticOperations() {
        String transform = "{ sum: input.a + input.b, product: input.a * input.b }";
        
        JsonObject input = new JsonObject();
        input.addProperty("a", 10);
        input.addProperty("b", 5);

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals(15, obj.get("sum").getAsInt());
        assertEquals(50, obj.get("product").getAsInt());
    }

    @Test
    public void testConditionalExpression() {
        String transform = "{ status: input.age >= 18 ? \"adult\" : \"minor\" }";
        
        JsonObject input = new JsonObject();
        input.addProperty("age", 20);

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals("adult", obj.get("status").getAsString());
    }

    @Test
    public void testSafeNavigation() {
        String transform = "{ name: input?.user?.name ?? \"Unknown\" }";
        
        JsonObject input = new JsonObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals("Unknown", obj.get("name").getAsString());
    }

    @Test
    public void testStringOperations() {
        String transform = "{ upper: upper(input.text), lower: lower(input.text) }";
        
        JsonObject input = new JsonObject();
        input.addProperty("text", "Hello World");

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals("HELLO WORLD", obj.get("upper").getAsString());
        assertEquals("hello world", obj.get("lower").getAsString());
    }

    @Test
    public void testLen() {
        String transform = "{ arrayLen: len(input.items), stringLen: len(input.text) }";
        
        JsonObject input = new JsonObject();
        input.add("items", gson.fromJson("[1,2,3]", JsonElement.class));
        input.addProperty("text", "hello");

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals(3, obj.get("arrayLen").getAsInt());
        assertEquals(5, obj.get("stringLen").getAsInt());
    }

    @Test
    public void testExists() {
        String transform = "{ hasName: exists(input.name), hasAge: exists(input.age) }";
        
        JsonObject input = new JsonObject();
        input.addProperty("name", "John");

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertTrue(obj.get("hasName").getAsBoolean());
        assertFalse(obj.get("hasAge").getAsBoolean());
    }

    @Test
    public void testMerge() {
        String transform = "merge({a: 1}, {b: 2}, {c: 3})";
        
        JsonObject input = new JsonObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals(1, obj.get("a").getAsInt());
        assertEquals(2, obj.get("b").getAsInt());
        assertEquals(3, obj.get("c").getAsInt());
    }

    @Test
    public void testNow() {
        String transform = "{ timestamp: now() }";
        
        JsonObject input = new JsonObject();

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertNotNull(obj.get("timestamp"));
        assertTrue(obj.get("timestamp").getAsString().contains("T"));
    }

    @Test
    public void testComputedPropertyKeys() {
        String transform = "{ [input.keyName]: \"value\" }";
        
        JsonObject input = new JsonObject();
        input.addProperty("keyName", "dynamicKey");

        MorphiumEngine engine = new MorphiumEngine();
        JsonElement result = engine.transformFromString(transform, input);

        JsonObject obj = result.getAsJsonObject();
        assertEquals("value", obj.get("dynamicKey").getAsString());
    }
}
