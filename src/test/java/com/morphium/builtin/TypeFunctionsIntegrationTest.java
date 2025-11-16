package com.morphium.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Integration tests for Type System Functions
 * Tests functions in actual transformation scenarios
 */
public class TypeFunctionsIntegrationTest {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    private MorphiumEngine engine;
    
    @Before
    public void setUp() {
        engine = new MorphiumEngine();
    }
    
    @Test
    public void testIsStringInTransformation() throws Exception {
        String transform = "{ valid: isString($.name), type: typeOf($.name) }";
        String input = "{\"name\": \"John\"}";
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        
        assertTrue(result.get("valid").asBoolean());
        assertEquals("string", result.get("type").asText());
    }
    
    @Test
    public void testIsEmptyValidation() throws Exception {
        String transform = "{ hasEmail: !isEmpty($.email), hasPhone: !isEmpty($.phone) }";
        String input = "{\"email\": \"test@example.com\", \"phone\": \"\"}";
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        
        assertTrue(result.get("hasEmail").asBoolean());
        assertFalse(result.get("hasPhone").asBoolean());
    }
    
    @Test
    public void testTypeConversion() throws Exception {
        String transform = "{ age: toInt($.ageString), price: toFloat($.priceString) }";
        String input = "{\"ageString\": \"25\", \"priceString\": \"19.99\"}";
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        
        assertEquals(25, result.get("age").asInt());
        assertEquals(19.99, result.get("price").asDouble(), 0.01);
    }
    
    @Test
    public void testTypeValidationInFilter() throws Exception {
        String transform = "{ validUsers: filter($.users, \"u\", isString(u.email) && !isEmpty(u.email)) }";
        String input = "{\"users\": [{\"email\": \"john@example.com\"}, {\"email\": \"\"}, {\"email\": 123}]}";
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        
        assertEquals(1, result.get("validUsers").size());
        assertEquals("john@example.com", result.get("validUsers").get(0).get("email").asText());
    }
    
    @Test
    public void testConditionalTypeChecking() throws Exception {
        String transform = "{ value: isNumber($.data) ? $.data : toInt($.data) }";
        String input = "{\"data\": \"42\"}";
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        
        assertEquals(42, result.get("value").asInt());
    }
    
    @Test
    public void testBooleanConversionLogic() throws Exception {
        String transform = "{ active: toBoolNew($.status) }";
        String input = "{\"status\": \"true\"}";
        
        JsonNode result = engine.transformFromString(transform, mapper.readTree(input));
        
        assertTrue(result.get("active").asBoolean());
    }
}
