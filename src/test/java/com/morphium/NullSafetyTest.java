package com.morphium;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NullSafetyTest {
    
    private MorphiumEngine engine;
    private ObjectMapper mapper;
    
    @Before
    public void setUp() {
        engine = new MorphiumEngine();
        mapper = new ObjectMapper();
    }
    
    @Test
    public void testCoalesce() throws Exception {
        String input = "{\"a\": null, \"b\": \"value\", \"c\": \"ignored\"}";
        String morph = "{ result: coalesce($.a, $.b, $.c) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("value", result.get("result").asText());
    }
    
    @Test
    public void testCoalesceAllNull() throws Exception {
        String input = "{\"a\": null, \"b\": null}";
        String morph = "{ result: coalesce($.a, $.b) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("result").isNull());
    }
    
    @Test
    public void testIfNull() throws Exception {
        String input = "{\"value\": null}";
        String morph = "{ result: ifNull($.value, \"default\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("default", result.get("result").asText());
    }
    
    @Test
    public void testIfNullWithValue() throws Exception {
        String input = "{\"value\": \"exists\"}";
        String morph = "{ result: ifNull($.value, \"default\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("exists", result.get("result").asText());
    }
    
    @Test
    public void testNullIf() throws Exception {
        String input = "{\"value\": \"test\"}";
        String morph = "{ result: nullIf($.value, \"test\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("result").isNull());
    }
    
    @Test
    public void testNullIfNotEqual() throws Exception {
        String input = "{\"value\": \"test\"}";
        String morph = "{ result: nullIf($.value, \"other\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("test", result.get("result").asText());
    }
    
    @Test
    public void testSafeGet() throws Exception {
        String input = "{\"user\": {\"name\": \"Alice\"}}";
        String morph = "{ result: safeGet($.user, \"name\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("Alice", result.get("result").asText());
    }
    
    @Test
    public void testSafeGetMissingPath() throws Exception {
        String input = "{\"user\": {\"name\": \"Alice\"}}";
        String morph = "{ result: safeGet($.user, \"address.city\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("result").isNull());
    }
    
    @Test
    public void testTryGet() throws Exception {
        String input = "{\"user\": {\"name\": \"Bob\"}}";
        String morph = "{ result: tryGet($.user, \"city\", \"Unknown\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("Unknown", result.get("result").asText());
    }
    
    @Test
    public void testTryGetWithValue() throws Exception {
        String input = "{\"user\": {\"city\": \"NYC\"}}";
        String morph = "{ result: tryGet($.user, \"city\", \"Unknown\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("NYC", result.get("result").asText());
    }
    
    @Test
    public void testRemoveNulls() throws Exception {
        String input = "{\"user\": {\"name\": \"Charlie\", \"age\": null, \"email\": \"c@test.com\"}}";
        String morph = "{ user: removeNulls($.user) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertNotNull(result.get("user"));
        assertTrue(result.get("user").has("name"));
        assertFalse(result.get("user").has("age"));
        assertTrue(result.get("user").has("email"));
    }
    
    @Test
    public void testReplaceNulls() throws Exception {
        String input = "{\"config\": {\"timeout\": 5000, \"retries\": null}}";
        String morph = "{ config: replaceNulls($.config, 0) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals(5000, result.get("config").get("timeout").asInt());
        assertEquals(0, result.get("config").get("retries").asInt());
    }
    
    @Test
    public void testIsNullOrEmpty() throws Exception {
        String input = "{\"empty\": \"\", \"value\": \"test\", \"nullVal\": null}";
        String morph = "{\n" +
            "  emptyCheck: isNullOrEmpty($.empty),\n" +
            "  valueCheck: isNullOrEmpty($.value),\n" +
            "  nullCheck: isNullOrEmpty($.nullVal)\n" +
            "}";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("emptyCheck").asBoolean());
        assertFalse(result.get("valueCheck").asBoolean());
        assertTrue(result.get("nullCheck").asBoolean());
    }
    
    @Test
    public void testFirstValid() throws Exception {
        String input = "{\"a\": null, \"b\": \"\", \"c\": \"valid\"}";
        String morph = "{ result: firstValid($.a, $.b, $.c, \"default\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("valid", result.get("result").asText());
    }
    
    @Test
    public void testComplexNullSafety() throws Exception {
        String input = "{\n" +
            "  \"data\": {\n" +
            "    \"user\": {\n" +
            "      \"id\": 123,\n" +
            "      \"nickname\": null,\n" +
            "      \"firstName\": \"Alice\"\n" +
            "    }\n" +
            "  }\n" +
            "}";
        
        String morph = "{\n" +
            "  userId: coalesce(safeGet($.data, \"user.id\"), 0),\n" +
            "  userName: coalesce($.data.user.nickname, $.data.user.firstName, \"Guest\"),\n" +
            "  email: tryGet($.data, \"user.email\", \"no-email\")\n" +
            "}";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals(123, result.get("userId").asInt());
        assertEquals("Alice", result.get("userName").asText());
        assertEquals("no-email", result.get("email").asText());
    }
}
