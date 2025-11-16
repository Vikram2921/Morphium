package com.morphium;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectTransformTest {
    
    private MorphiumEngine engine;
    private ObjectMapper mapper;
    
    @Before
    public void setUp() {
        engine = new MorphiumEngine();
        mapper = new ObjectMapper();
    }
    
    @Test
    public void testPick() throws Exception {
        String input = "{\"user\": {\"name\": \"Alice\", \"age\": 25, \"email\": \"alice@test.com\", \"password\": \"secret\"}}";
        String morph = "{ user: pick($.user, [\"name\", \"email\"]) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("user").has("name"));
        assertTrue(result.get("user").has("email"));
        assertFalse(result.get("user").has("age"));
        assertFalse(result.get("user").has("password"));
    }
    
    @Test
    public void testOmit() throws Exception {
        String input = "{\"user\": {\"name\": \"Bob\", \"age\": 30, \"password\": \"secret\", \"internal_id\": \"123\"}}";
        String morph = "{ user: omit($.user, [\"password\", \"internal_id\"]) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("user").has("name"));
        assertTrue(result.get("user").has("age"));
        assertFalse(result.get("user").has("password"));
        assertFalse(result.get("user").has("internal_id"));
    }
    
    @Test
    public void testInvert() throws Exception {
        String input = "{\"roles\": {\"admin\": \"1\", \"user\": \"2\", \"guest\": \"3\"}}";
        String morph = "{ inverted: invert($.roles) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("admin", result.get("inverted").get("1").asText());
        assertEquals("user", result.get("inverted").get("2").asText());
        assertEquals("guest", result.get("inverted").get("3").asText());
    }
    
    @Test
    public void testMapKeys() throws Exception {
        String input = "{\"data\": {\"firstName\": \"Alice\", \"lastName\": \"Smith\"}}";
        String morph = "{ upper: mapKeys($.data, \"upper\"), lower: mapKeys($.data, \"lower\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("upper").has("FIRSTNAME"));
        assertTrue(result.get("lower").has("firstname"));
    }
    
    @Test
    public void testFlattenUnflatten() throws Exception {
        String input = "{\"user\": {\"profile\": {\"name\": \"Charlie\", \"address\": {\"city\": \"NYC\"}}}}";
        String morph = "{\n" +
            "  flat: flattenObj($.user.profile),\n" +
            "  unflat: unflattenObj({\"name\": \"Test\", \"address.city\": \"LA\"})\n" +
            "}";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("flat").has("name"));
        assertTrue(result.get("flat").has("address.city"));
        assertEquals("Charlie", result.get("flat").get("name").asText());
        
        assertTrue(result.get("unflat").has("address"));
        assertEquals("LA", result.get("unflat").get("address").get("city").asText());
    }
    
    @Test
    public void testToCamelCase() throws Exception {
        String input = "{\"data\": {\"first_name\": \"David\", \"last_name\": \"Jones\", \"email_address\": \"d@test.com\"}}";
        String morph = "{ result: toCamelCase($.data) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("result").has("firstName"));
        assertTrue(result.get("result").has("lastName"));
        assertTrue(result.get("result").has("emailAddress"));
        assertEquals("David", result.get("result").get("firstName").asText());
    }
    
    @Test
    public void testToSnakeCase() throws Exception {
        String input = "{\"data\": {\"firstName\": \"Emma\", \"lastName\": \"Wilson\", \"emailAddress\": \"e@test.com\"}}";
        String morph = "{ result: toSnakeCase($.data) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("result").has("first_name"));
        assertTrue(result.get("result").has("last_name"));
        assertTrue(result.get("result").has("email_address"));
        assertEquals("Emma", result.get("result").get("first_name").asText());
    }
    
    @Test
    public void testToKebabCase() throws Exception {
        String input = "{\"data\": {\"firstName\": \"Frank\", \"lastName\": \"Brown\"}}";
        String morph = "{ result: toKebabCase($.data) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("result").has("first-name"));
        assertTrue(result.get("result").has("last-name"));
        assertEquals("Frank", result.get("result").get("first-name").asText());
    }
    
    @Test
    public void testDeepClone() throws Exception {
        String input = "{\"original\": {\"name\": \"Test\", \"nested\": {\"value\": 42}}}";
        String morph = "{ cloned: deepClone($.original) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertEquals("Test", result.get("cloned").get("name").asText());
        assertEquals(42, result.get("cloned").get("nested").get("value").asInt());
    }
    
    @Test
    public void testComplexTransformation() throws Exception {
        String input = "{\n" +
            "  \"user_data\": {\n" +
            "    \"first_name\": \"Grace\",\n" +
            "    \"last_name\": \"Hopper\",\n" +
            "    \"email_address\": \"grace@test.com\",\n" +
            "    \"password_hash\": \"xxx\",\n" +
            "    \"internal_id\": \"123\"\n" +
            "  }\n" +
            "}";
        
        String morph = "{\n" +
            "  user: toCamelCase(omit($.user_data, [\"password_hash\", \"internal_id\"]))\n" +
            "}";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertTrue(result.get("user").has("firstName"));
        assertTrue(result.get("user").has("lastName"));
        assertTrue(result.get("user").has("emailAddress"));
        assertFalse(result.get("user").has("passwordHash"));
        assertFalse(result.get("user").has("internalId"));
    }
}
