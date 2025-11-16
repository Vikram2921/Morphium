package com.morphium;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeyManipulationTest {
    
    private MorphiumEngine engine;
    private ObjectMapper mapper;
    
    @Before
    public void setUp() {
        engine = new MorphiumEngine();
        mapper = new ObjectMapper();
    }
    
    @Test
    public void testRemoveKey() throws Exception {
        String input = "{\"user\": {\"name\": \"Alice\", \"password\": \"secret\", \"email\": \"alice@example.com\"}}";
        String morph = "{ user: removeKey($.user, \"password\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertNotNull(result);
        assertTrue(result.has("user"));
        assertFalse(result.get("user").has("password"));
        assertTrue(result.get("user").has("name"));
        assertTrue(result.get("user").has("email"));
        assertEquals("Alice", result.get("user").get("name").asText());
    }
    
    @Test
    public void testRemoveKeyChained() throws Exception {
        String input = "{\"product\": {\"id\": 1, \"name\": \"Widget\", \"internal\": \"X\", \"cost\": 10}}";
        String morph = "{ product: removeKey(removeKey($.product, \"internal\"), \"cost\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertNotNull(result);
        assertTrue(result.has("product"));
        assertFalse(result.get("product").has("internal"));
        assertFalse(result.get("product").has("cost"));
        assertTrue(result.get("product").has("id"));
        assertTrue(result.get("product").has("name"));
    }
    
    @Test
    public void testRenameKey() throws Exception {
        String input = "{\"user\": {\"first_name\": \"Alice\", \"last_name\": \"Smith\"}}";
        String morph = "{ user: renameKey($.user, \"first_name\", \"firstName\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertNotNull(result);
        assertTrue(result.has("user"));
        assertFalse(result.get("user").has("first_name"));
        assertTrue(result.get("user").has("firstName"));
        assertEquals("Alice", result.get("user").get("firstName").asText());
    }
    
    @Test
    public void testRenameKeyChained() throws Exception {
        String input = "{\"user\": {\"first_name\": \"Alice\", \"last_name\": \"Smith\"}}";
        String morph = "{ user: renameKey(renameKey($.user, \"first_name\", \"firstName\"), \"last_name\", \"lastName\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertNotNull(result);
        assertTrue(result.has("user"));
        assertFalse(result.get("user").has("first_name"));
        assertFalse(result.get("user").has("last_name"));
        assertTrue(result.get("user").has("firstName"));
        assertTrue(result.get("user").has("lastName"));
        assertEquals("Alice", result.get("user").get("firstName").asText());
        assertEquals("Smith", result.get("user").get("lastName").asText());
    }
    
    @Test
    public void testRemoveKeyNonExistent() throws Exception {
        String input = "{\"user\": {\"name\": \"Alice\"}}";
        String morph = "{ user: removeKey($.user, \"nonexistent\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertNotNull(result);
        assertTrue(result.has("user"));
        assertTrue(result.get("user").has("name"));
        assertEquals("Alice", result.get("user").get("name").asText());
    }
    
    @Test
    public void testRenameKeyNonExistent() throws Exception {
        String input = "{\"user\": {\"name\": \"Alice\"}}";
        String morph = "{ user: renameKey($.user, \"nonexistent\", \"newName\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertNotNull(result);
        assertTrue(result.has("user"));
        assertTrue(result.get("user").has("name"));
        assertFalse(result.get("user").has("newName"));
        assertEquals("Alice", result.get("user").get("name").asText());
    }
    
    @Test
    public void testCombinedRemoveAndRename() throws Exception {
        String input = "{\"order\": {\"ord_id\": 456, \"cust_name\": \"Bob\", \"internal\": \"secret\"}}";
        String morph = "{ order: removeKey(renameKey($.order, \"ord_id\", \"orderId\"), \"internal\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        
        assertNotNull(result);
        assertTrue(result.has("order"));
        assertFalse(result.get("order").has("ord_id"));
        assertFalse(result.get("order").has("internal"));
        assertTrue(result.get("order").has("orderId"));
        assertTrue(result.get("order").has("cust_name"));
        assertEquals(456, result.get("order").get("orderId").asInt());
    }
}
