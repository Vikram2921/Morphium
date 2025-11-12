package com.morphium;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoopControlTest {
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Test
    public void testBreakInForOf() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let result = for (n of $.numbers) { " +
            "  n > 5 ? break : null; " +
            "  n * 2 " +
            "}; " +
            "{ doubled: result }";
        
        JsonNode input = mapper.readTree("{\"numbers\": [1, 2, 3, 6, 7, 8]}");
        JsonNode result = engine.transformFromString(script, input);
        
        // Should only process 1, 2, 3 (stops at 6)
        assertEquals(3, result.get("doubled").size());
        assertEquals(2, result.get("doubled").get(0).asInt());
        assertEquals(4, result.get("doubled").get(1).asInt());
        assertEquals(6, result.get("doubled").get(2).asInt());
    }
    
    @Test
    public void testContinueInForOf() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let result = for (n of $.numbers) { " +
            "  n % 2 == 0 ? continue : null; " +
            "  n * 2 " +
            "}; " +
            "{ doubled: result }";
        
        JsonNode input = mapper.readTree("{\"numbers\": [1, 2, 3, 4, 5, 6]}");
        JsonNode result = engine.transformFromString(script, input);
        
        // Should only process odd numbers: 1, 3, 5
        assertEquals(3, result.get("doubled").size());
        assertEquals(2, result.get("doubled").get(0).asInt());
        assertEquals(6, result.get("doubled").get(1).asInt());
        assertEquals(10, result.get("doubled").get(2).asInt());
    }
    
    @Test
    public void testForInLoop() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let indices = for (i in $.items) { i }; " +
            "{ indices: indices }";
        
        JsonNode input = mapper.readTree("{\"items\": [\"a\", \"b\", \"c\", \"d\"]}");
        JsonNode result = engine.transformFromString(script, input);
        
        assertEquals(4, result.get("indices").size());
        assertEquals(0, result.get("indices").get(0).asInt());
        assertEquals(1, result.get("indices").get(1).asInt());
        assertEquals(2, result.get("indices").get(2).asInt());
        assertEquals(3, result.get("indices").get(3).asInt());
    }
    
    @Test
    public void testForInWithArrayAccess() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let result = for (i in $.items) { " +
            "  { index: i, value: $.items[i] } " +
            "}; " +
            "{ result: result }";
        
        JsonNode input = mapper.readTree("{\"items\": [10, 20, 30]}");
        JsonNode result = engine.transformFromString(script, input);
        
        assertEquals(3, result.get("result").size());
        assertEquals(0, result.get("result").get(0).get("index").asInt());
        assertEquals(10, result.get("result").get(0).get("value").asInt());
        assertEquals(2, result.get("result").get(2).get("index").asInt());
        assertEquals(30, result.get("result").get(2).get("value").asInt());
    }
    
    @Test
    public void testBreakInForIn() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let result = for (i in $.numbers) { " +
            "  i >= 3 ? break : null; " +
            "  $.numbers[i] " +
            "}; " +
            "{ values: result }";
        
        JsonNode input = mapper.readTree("{\"numbers\": [10, 20, 30, 40, 50]}");
        JsonNode result = engine.transformFromString(script, input);
        
        // Should only get first 3 values
        assertEquals(3, result.get("values").size());
        assertEquals(10, result.get("values").get(0).asInt());
        assertEquals(20, result.get("values").get(1).asInt());
        assertEquals(30, result.get("values").get(2).asInt());
    }
    
    @Test
    public void testContinueInForIn() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let result = for (i in $.numbers) { " +
            "  i % 2 == 0 ? continue : null; " +
            "  $.numbers[i] " +
            "}; " +
            "{ values: result }";
        
        JsonNode input = mapper.readTree("{\"numbers\": [10, 20, 30, 40, 50]}");
        JsonNode result = engine.transformFromString(script, input);
        
        // Should only get values at odd indices: 1, 3
        assertEquals(2, result.get("values").size());
        assertEquals(20, result.get("values").get(0).asInt());
        assertEquals(40, result.get("values").get(1).asInt());
    }
    
    @Test
    public void testNestedLoopsWithBreak() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let result = for (i of $.outer) { " +
            "  for (j of $.inner) { " +
            "    j > 2 ? break : null; " +
            "    { i: i, j: j } " +
            "  } " +
            "}; " +
            "{ result: result }";
        
        JsonNode input = mapper.readTree("{\"outer\": [1, 2], \"inner\": [1, 2, 3, 4]}");
        JsonNode result = engine.transformFromString(script, input);
        
        // Each outer loop should produce 2 items (inner breaks at 3)
        assertEquals(2, result.get("result").size());
        
        // First outer iteration
        assertEquals(2, result.get("result").get(0).size());
        assertEquals(1, result.get("result").get(0).get(0).get("i").asInt());
        assertEquals(1, result.get("result").get(0).get(0).get("j").asInt());
        
        // Second outer iteration
        assertEquals(2, result.get("result").get(1).size());
        assertEquals(2, result.get("result").get(1).get(0).get("i").asInt());
    }
    
    @Test
    public void testBreakAndContinueTogether() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let result = for (n of $.numbers) { " +
            "  n > 10 ? break : null; " +
            "  n < 5 ? continue : null; " +
            "  n " +
            "}; " +
            "{ values: result }";
        
        JsonNode input = mapper.readTree("{\"numbers\": [1, 2, 5, 6, 7, 11, 12]}");
        JsonNode result = engine.transformFromString(script, input);
        
        // Should skip 1,2 (continue), process 5,6,7, stop at 11 (break)
        assertEquals(3, result.get("values").size());
        assertEquals(5, result.get("values").get(0).asInt());
        assertEquals(6, result.get("values").get(1).asInt());
        assertEquals(7, result.get("values").get(2).asInt());
    }
    
    @Test
    public void testForInIndexAccess() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        // Use index to access array elements
        String script = 
            "let result = for (i in $.items) { " +
            "  { index: i, value: $.items[i] } " +
            "}; " +
            "{ result: result }";
        
        JsonNode input = mapper.readTree("{\"items\": [\"a\", \"b\", \"c\"]}");
        JsonNode result = engine.transformFromString(script, input);
        
        assertEquals(3, result.get("result").size());
        assertEquals(0, result.get("result").get(0).get("index").asInt());
        assertEquals("a", result.get("result").get(0).get("value").asText());
        assertEquals(2, result.get("result").get(2).get("index").asInt());
        assertEquals("c", result.get("result").get(2).get("value").asText());
    }
}
