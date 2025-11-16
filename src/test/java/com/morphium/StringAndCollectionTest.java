package com.morphium;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringAndCollectionTest {
    
    private MorphiumEngine engine;
    private ObjectMapper mapper;
    
    @Before
    public void setUp() {
        engine = new MorphiumEngine();
        mapper = new ObjectMapper();
    }
    
    // String Function Tests
    
    @Test
    public void testContains() throws Exception {
        String input = "{\"text\": \"Hello World\"}";
        String morph = "{ result: contains($.text, \"World\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        assertTrue(result.get("result").asBoolean());
    }
    
    @Test
    public void testStartsWithEndsWith() throws Exception {
        String input = "{\"text\": \"Hello World\"}";
        String morph = "{\n" +
            "  starts: startsWith($.text, \"Hello\"),\n" +
            "  ends: endsWith($.text, \"World\")\n" +
            "}";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        assertTrue(result.get("starts").asBoolean());
        assertTrue(result.get("ends").asBoolean());
    }
    
    @Test
    public void testCapitalize() throws Exception {
        String input = "{\"text\": \"hello world\"}";
        String morph = "{ result: capitalize($.text) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        assertEquals("Hello world", result.get("result").asText());
    }
    
    @Test
    public void testTitleCase() throws Exception {
        String input = "{\"text\": \"hello world test\"}";
        String morph = "{ result: titleCase($.text) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        assertEquals("Hello World Test", result.get("result").asText());
    }
    
    @Test
    public void testPadding() throws Exception {
        String input = "{\"num\": \"5\"}";
        String morph = "{\n" +
            "  padded: padStart($.num, 3, \"0\"),\n" +
            "  paddedEnd: padEnd($.num, 3, \"0\")\n" +
            "}";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        assertEquals("005", result.get("padded").asText());
        assertEquals("500", result.get("paddedEnd").asText());
    }
    
    @Test
    public void testMatches() throws Exception {
        String input = "{\"email\": \"test@example.com\"}";
        String morph = "{ result: matches($.email, \".*@.*\\\\..*\") }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        assertTrue(result.get("result").asBoolean());
    }
    
    // Collection Function Tests
    
    @Test
    public void testChunk() throws Exception {
        String input = "{\"items\": [1, 2, 3, 4, 5, 6]}";
        String morph = "{ result: chunk($.items, 2) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        assertTrue(result.get("result").isArray());
        assertEquals(3, result.get("result").size());
        assertEquals(2, result.get("result").get(0).size());
    }
    
    @Test
    public void testCompact() throws Exception {
        String input = "{\"items\": [1, null, 2, false, 3, 0, 4]}";
        String morph = "{ result: compact($.items) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        assertTrue(result.get("result").isArray());
        // Should keep: 1, 2, 3, 4 (removes null, false, 0)
        assertEquals(4, result.get("result").size());
    }
    
    @Test
    public void testUnique() throws Exception {
        String input = "{\"items\": [1, 2, 2, 3, 3, 3, 4]}";
        String morph = "{ result: unique($.items) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        assertEquals(4, result.get("result").size());
    }
    
    @Test
    public void testZipUnzip() throws Exception {
        String input = "{\"a\": [1, 2, 3], \"b\": [4, 5, 6]}";
        String morph = "{\n" +
            "  zipped: zip($.a, $.b),\n" +
            "  unzipped: unzip([[1,4], [2,5], [3,6]])\n" +
            "}";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        assertEquals(3, result.get("zipped").size());
        assertEquals(2, result.get("zipped").get(0).size());
        assertEquals(2, result.get("unzipped").size());
    }
    
    @Test
    public void testCumSum() throws Exception {
        String input = "{\"values\": [1, 2, 3, 4]}";
        String morph = "{ result: cumSum($.values) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        assertEquals(4, result.get("result").size());
        assertEquals(10.0, result.get("result").get(3).asDouble(), 0.01);
    }
    
    @Test
    public void testFlatten() throws Exception {
        String input = "{\"nested\": [[1, 2], [3, 4], [5]]}";
        String morph = "{ result: flatten($.nested) }";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        assertEquals(5, result.get("result").size());
        assertEquals(1, result.get("result").get(0).asInt());
    }
    
    @Test
    public void testTakeDrop() throws Exception {
        String input = "{\"items\": [1, 2, 3, 4, 5]}";
        String morph = "{\n" +
            "  first3: take($.items, 3),\n" +
            "  skip2: drop($.items, 2)\n" +
            "}";
        
        JsonNode result = engine.transformFromString(morph, mapper.readTree(input));
        assertEquals(3, result.get("first3").size());
        assertEquals(3, result.get("skip2").size());
        assertEquals(3, result.get("skip2").get(0).asInt());
    }
}
