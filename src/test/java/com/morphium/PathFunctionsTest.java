package com.morphium;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathFunctionsTest {
    
    private MorphiumEngine engine;
    private ObjectMapper mapper;
    
    @Before
    public void setUp() {
        engine = new MorphiumEngine();
        mapper = new ObjectMapper();
    }
    
    @Test
    public void testGetInWithDotNotation() throws Exception {
        String input = "{\"user\": {\"address\": {\"city\": \"NYC\"}}}";
        String morph = "{ result: getIn($, \"user.address.city\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("NYC", result.get("result").asText());
    }
    
    @Test
    public void testGetInWithArrayPath() throws Exception {
        String input = "{\"user\": {\"address\": {\"city\": \"NYC\"}}}";
        String morph = "{ result: getIn($, [\"user\", \"address\", \"city\"]) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("NYC", result.get("result").asText());
    }
    
    @Test
    public void testGetInWithDefault() throws Exception {
        String input = "{\"user\": {\"name\": \"Alice\"}}";
        String morph = "{ result: getIn($, \"user.address.city\", \"Unknown\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("Unknown", result.get("result").asText());
    }
    
    @Test
    public void testGetInWithArrayIndex() throws Exception {
        String input = "{\"users\": [{\"name\": \"Alice\"}, {\"name\": \"Bob\"}]}";
        String morph = "{ result: getIn($, \"users.1.name\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("Bob", result.get("result").asText());
    }
    
    @Test
    public void testSetInSimple() throws Exception {
        String input = "{\"user\": {\"name\": \"Alice\"}}";
        String morph = "{ result: setIn($.user, \"age\", 25) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("Alice", result.get("result").get("name").asText());
        assertEquals(25, result.get("result").get("age").asInt());
    }
    
    @Test
    public void testSetInDeepPath() throws Exception {
        String input = "{\"user\": {}}";
        String morph = "{ result: setIn($.user, \"address.city\", \"NYC\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("result").has("address"));
        assertEquals("NYC", result.get("result").get("address").get("city").asText());
    }
    
    @Test
    public void testSetInWithArrayPath() throws Exception {
        String input = "{\"data\": {}}";
        String morph = "{ result: setIn($.data, [\"user\", \"name\"], \"Alice\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("Alice", result.get("result").get("user").get("name").asText());
    }
    
    @Test
    public void testDeleteIn() throws Exception {
        String input = "{\"user\": {\"name\": \"Alice\", \"age\": 25, \"city\": \"NYC\"}}";
        String morph = "{ result: deleteIn($.user, \"age\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("result").has("name"));
        assertFalse(result.get("result").has("age"));
        assertTrue(result.get("result").has("city"));
    }
    
    @Test
    public void testDeleteInDeepPath() throws Exception {
        String input = "{\"user\": {\"address\": {\"street\": \"Main St\", \"city\": \"NYC\"}}}";
        String morph = "{ result: deleteIn($.user, \"address.street\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("result").has("address"));
        assertFalse(result.get("result").get("address").has("street"));
        assertTrue(result.get("result").get("address").has("city"));
    }
    
    @Test
    public void testHasPath() throws Exception {
        String input = "{\"user\": {\"address\": {\"city\": \"NYC\"}}}";
        String morph = "{\n" +
            "  exists: hasPath($, \"user.address.city\"),\n" +
            "  missing: hasPath($, \"user.address.country\")\n" +
            "}";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("exists").asBoolean());
        assertFalse(result.get("missing").asBoolean());
    }
    
    @Test
    public void testHasPathWithArrayIndex() throws Exception {
        String input = "{\"users\": [{\"name\": \"Alice\"}, {\"name\": \"Bob\"}]}";
        String morph = "{\n" +
            "  exists: hasPath($, \"users.1.name\"),\n" +
            "  missing: hasPath($, \"users.5.name\")\n" +
            "}";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("exists").asBoolean());
        assertFalse(result.get("missing").asBoolean());
    }
    
    @Test
    public void testGetPaths() throws Exception {
        String input = "{\"user\": {\"name\": \"Alice\", \"age\": 25, \"city\": \"NYC\"}}";
        String morph = "{ result: getPaths($.user, [\"name\", \"age\", \"country\"]) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("result").isArray());
        assertEquals(3, result.get("result").size());
        assertEquals("Alice", result.get("result").get(0).asText());
        assertEquals(25, result.get("result").get(1).asInt());
        assertTrue(result.get("result").get(2).isNull());
    }
    
    @Test
    public void testPathDepth() throws Exception {
        String input = "{}";
        String morph = "{\n" +
            "  simple: pathDepth(\"name\"),\n" +
            "  deep: pathDepth(\"user.address.city\")\n" +
            "}";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals(1, result.get("simple").asInt());
        assertEquals(3, result.get("deep").asInt());
    }
    
    @Test
    public void testNormalizePath() throws Exception {
        String input = "{}";
        String morph = "{ result: normalizePath(\"user.address.city\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("result").isArray());
        assertEquals(3, result.get("result").size());
        assertEquals("user", result.get("result").get(0).asText());
        assertEquals("address", result.get("result").get(1).asText());
        assertEquals("city", result.get("result").get(2).asText());
    }
    
    @Test
    public void testPathExists() throws Exception {
        String input = "{\"user\": {\"name\": \"Alice\"}}";
        String morph = "{\n" +
            "  exists: pathExists($, \"user.name\"),\n" +
            "  missing: pathExists($, \"user.age\")\n" +
            "}";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("exists").asBoolean());
        assertFalse(result.get("missing").asBoolean());
    }
    
    @Test
    public void testComplexPathOperations() throws Exception {
        String input = "{\"data\": {\"users\": [{\"id\": 1, \"name\": \"Alice\"}]}}";
        String morph = "{\n" +
            "  name: getIn($, \"data.users.0.name\", \"Unknown\"),\n" +
            "  hasId: hasPath($, \"data.users.0.id\"),\n" +
            "  updated: setIn($, \"data.users.0.active\", true)\n" +
            "}";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("Alice", result.get("name").asText());
        assertTrue(result.get("hasId").asBoolean());
        assertTrue(result.get("updated").get("data").get("users").get(0).get("active").asBoolean());
    }
}
