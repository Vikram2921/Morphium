package com.morphium.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import com.morphium.runtime.Context;
import com.morphium.runtime.HostFunctionRegistry;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Comprehensive test suite for Type System Functions
 * Phase 1, Week 1-2 implementation - Target: 90%+ test coverage
 */
public class TypeFunctionsTest {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    private Context context;
    
    @Before
    public void setUp() {
        context = new Context(new HostFunctionRegistry());
    }
    
    // ==================== isString() TESTS ====================
    
    @Test
    public void testIsString_withString() {
        JsonNode[] args = {TextNode.valueOf("hello")};
        JsonNode result = TypeFunctions.isString(args, context);
        assertTrue(result.asBoolean());
    }
    
    @Test
    public void testIsString_withNumber() {
        JsonNode[] args = {IntNode.valueOf(42)};
        JsonNode result = TypeFunctions.isString(args, context);
        assertFalse(result.asBoolean());
    }
    
    @Test
    public void testIsString_withNull() {
        JsonNode[] args = {NullNode.getInstance()};
        JsonNode result = TypeFunctions.isString(args, context);
        assertFalse(result.asBoolean());
    }
    
    // ==================== isNumber() TESTS ====================
    
    @Test
    public void testIsNumber_withInteger() {
        JsonNode[] args = {IntNode.valueOf(42)};
        JsonNode result = TypeFunctions.isNumber(args, context);
        assertTrue(result.asBoolean());
    }
    
    @Test
    public void testIsNumber_withDouble() {
        JsonNode[] args = {DoubleNode.valueOf(3.14)};
        JsonNode result = TypeFunctions.isNumber(args, context);
        assertTrue(result.asBoolean());
    }
    
    // ==================== isEmpty() TESTS ====================
    
    @Test
    public void testIsEmpty_withEmptyString() {
        JsonNode[] args = {TextNode.valueOf("")};
        JsonNode result = TypeFunctions.isEmpty(args, context);
        assertTrue(result.asBoolean());
    }
    
    @Test
    public void testIsEmpty_withNonEmptyString() {
        JsonNode[] args = {TextNode.valueOf("hello")};
        JsonNode result = TypeFunctions.isEmpty(args, context);
        assertFalse(result.asBoolean());
    }
    
    @Test
    public void testIsEmpty_withEmptyArray() {
        JsonNode[] args = {mapper.createArrayNode()};
        JsonNode result = TypeFunctions.isEmpty(args, context);
        assertTrue(result.asBoolean());
    }
    
    // ==================== typeOf() TESTS ====================
    
    @Test
    public void testTypeOf_withString() {
        JsonNode[] args = {TextNode.valueOf("hello")};
        JsonNode result = TypeFunctions.typeOf(args, context);
        assertEquals("string", result.asText());
    }
    
    @Test
    public void testTypeOf_withInteger() {
        JsonNode[] args = {IntNode.valueOf(42)};
        JsonNode result = TypeFunctions.typeOf(args, context);
        assertEquals("integer", result.asText());
    }
    
    @Test
    public void testTypeOf_withNull() {
        JsonNode[] args = {NullNode.getInstance()};
        JsonNode result = TypeFunctions.typeOf(args, context);
        assertEquals("null", result.asText());
    }
    
    // ==================== TYPE CONVERSION TESTS ====================
    
    @Test
    public void testToInt_fromString() {
        JsonNode[] args = {TextNode.valueOf("42")};
        JsonNode result = TypeFunctions.toInt(args, context);
        assertEquals(42, result.asInt());
    }
    
    @Test
    public void testToInt_fromDecimalString() {
        JsonNode[] args = {TextNode.valueOf("42.7")};
        JsonNode result = TypeFunctions.toInt(args, context);
        assertEquals(42, result.asInt());
    }
    
    @Test
    public void testToInt_fromBooleanTrue() {
        JsonNode[] args = {BooleanNode.TRUE};
        JsonNode result = TypeFunctions.toInt(args, context);
        assertEquals(1, result.asInt());
    }
    
    @Test
    public void testToInt_invalidString() {
        JsonNode[] args = {TextNode.valueOf("not a number")};
        JsonNode result = TypeFunctions.toInt(args, context);
        assertTrue(result.isNull());
    }
    
    @Test
    public void testToFloat_fromString() {
        JsonNode[] args = {TextNode.valueOf("3.14")};
        JsonNode result = TypeFunctions.toFloat(args, context);
        assertEquals(3.14, result.asDouble(), 0.001);
    }
    
    @Test
    public void testToString_fromNumber() {
        JsonNode[] args = {IntNode.valueOf(42)};
        JsonNode result = TypeFunctions.toStringFunc(args, context);
        assertEquals("42", result.asText());
    }
    
    @Test
    public void testToBool_fromTrueString() {
        JsonNode[] args = {TextNode.valueOf("true")};
        JsonNode result = TypeFunctions.toBoolFunc(args, context);
        assertTrue(result.asBoolean());
    }
    
    @Test
    public void testToBool_fromZero() {
        JsonNode[] args = {TextNode.valueOf("0")};
        JsonNode result = TypeFunctions.toBoolFunc(args, context);
        assertFalse(result.asBoolean());
    }
    
    @Test
    public void testIsFinite_withFiniteNumber() {
        JsonNode[] args = {DoubleNode.valueOf(42.5)};
        JsonNode result = TypeFunctions.isFinite(args, context);
        assertTrue(result.asBoolean());
    }
    
    @Test
    public void testIsInteger_withInteger() {
        JsonNode[] args = {IntNode.valueOf(42)};
        JsonNode result = TypeFunctions.isInteger(args, context);
        assertTrue(result.asBoolean());
    }
    
    @Test
    public void testIsInteger_withFloat() {
        JsonNode[] args = {DoubleNode.valueOf(42.5)};
        JsonNode result = TypeFunctions.isInteger(args, context);
        assertFalse(result.asBoolean());
    }
}
