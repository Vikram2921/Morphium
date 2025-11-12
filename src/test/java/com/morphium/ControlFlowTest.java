package com.morphium;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControlFlowTest {
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Test
    public void testIfStatement() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let result = if ($.value > 10) { " +
            "  'high' " +
            "} else { " +
            "  'low' " +
            "}; " +
            "{ result: result }";
        
        JsonNode input1 = mapper.readTree("{\"value\": 15}");
        JsonNode result1 = engine.transformFromString(script, input1);
        assertEquals("high", result1.get("result").asText());
        
        JsonNode input2 = mapper.readTree("{\"value\": 5}");
        JsonNode result2 = engine.transformFromString(script, input2);
        assertEquals("low", result2.get("result").asText());
    }
    
    @Test
    public void testIfWithoutElse() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let bonus = if ($.score > 90) { 10 }; " +
            "{ score: $.score, bonus: bonus }";
        
        JsonNode input1 = mapper.readTree("{\"score\": 95}");
        JsonNode result1 = engine.transformFromString(script, input1);
        assertEquals(10, result1.get("bonus").asInt());
        
        JsonNode input2 = mapper.readTree("{\"score\": 85}");
        JsonNode result2 = engine.transformFromString(script, input2);
        assertTrue(result2.get("bonus").isNull());
    }
    
    @Test
    public void testNestedIf() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let grade = if ($.score >= 90) { " +
            "  'A' " +
            "} else if ($.score >= 80) { " +
            "  'B' " +
            "} else { " +
            "  'C' " +
            "}; " +
            "{ grade: grade }";
        
        JsonNode input1 = mapper.readTree("{\"score\": 95}");
        JsonNode result1 = engine.transformFromString(script, input1);
        assertEquals("A", result1.get("grade").asText());
        
        JsonNode input2 = mapper.readTree("{\"score\": 85}");
        JsonNode result2 = engine.transformFromString(script, input2);
        assertEquals("B", result2.get("grade").asText());
        
        JsonNode input3 = mapper.readTree("{\"score\": 70}");
        JsonNode result3 = engine.transformFromString(script, input3);
        assertEquals("C", result3.get("grade").asText());
    }
    
    @Test
    public void testSwitchStatement() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let day = switch ($.dayNum) { " +
            "  case 1: 'Monday' " +
            "  case 2: 'Tuesday' " +
            "  case 3: 'Wednesday' " +
            "  default: 'Other' " +
            "}; " +
            "{ day: day }";
        
        JsonNode input1 = mapper.readTree("{\"dayNum\": 1}");
        JsonNode result1 = engine.transformFromString(script, input1);
        assertEquals("Monday", result1.get("day").asText());
        
        JsonNode input2 = mapper.readTree("{\"dayNum\": 3}");
        JsonNode result2 = engine.transformFromString(script, input2);
        assertEquals("Wednesday", result2.get("day").asText());
        
        JsonNode input3 = mapper.readTree("{\"dayNum\": 5}");
        JsonNode result3 = engine.transformFromString(script, input3);
        assertEquals("Other", result3.get("day").asText());
    }
    
    @Test
    public void testSwitchWithExpressions() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let price = switch ($.category) { " +
            "  case 'basic': $.price " +
            "  case 'premium': $.price * 2 " +
            "  case 'vip': $.price * 3 " +
            "  default: 0 " +
            "}; " +
            "{ finalPrice: price }";
        
        JsonNode input1 = mapper.readTree("{\"category\":\"basic\",\"price\":10}");
        JsonNode result1 = engine.transformFromString(script, input1);
        assertEquals(10.0, result1.get("finalPrice").asDouble(), 0.01);
        
        JsonNode input2 = mapper.readTree("{\"category\":\"premium\",\"price\":10}");
        JsonNode result2 = engine.transformFromString(script, input2);
        assertEquals(20.0, result2.get("finalPrice").asDouble(), 0.01);
    }
    
    @Test
    public void testForOfLoop() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let doubled = for (item of $.numbers) { " +
            "  item * 2 " +
            "}; " +
            "{ doubled: doubled }";
        
        JsonNode input = mapper.readTree("{\"numbers\": [1, 2, 3, 4, 5]}");
        JsonNode result = engine.transformFromString(script, input);
        
        assertEquals(5, result.get("doubled").size());
        assertEquals(2, result.get("doubled").get(0).asInt());
        assertEquals(4, result.get("doubled").get(1).asInt());
        assertEquals(10, result.get("doubled").get(4).asInt());
    }
    
    @Test
    public void testForOfWithObjects() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let names = for (user of $.users) { " +
            "  user.name " +
            "}; " +
            "{ names: names }";
        
        JsonNode input = mapper.readTree(
            "{\"users\": [" +
            "  {\"name\":\"Alice\",\"age\":30}," +
            "  {\"name\":\"Bob\",\"age\":25}" +
            "]}"
        );
        JsonNode result = engine.transformFromString(script, input);
        
        assertEquals(2, result.get("names").size());
        assertEquals("Alice", result.get("names").get(0).asText());
        assertEquals("Bob", result.get("names").get(1).asText());
    }
    
    @Test
    public void testForOfWithTransformation() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let transformed = for (item of $.items) { " +
            "  { " +
            "    id: item.id, " +
            "    doubled: item.value * 2 " +
            "  } " +
            "}; " +
            "{ result: transformed }";
        
        JsonNode input = mapper.readTree(
            "{\"items\": [" +
            "  {\"id\":1,\"value\":10}," +
            "  {\"id\":2,\"value\":20}" +
            "]}"
        );
        JsonNode result = engine.transformFromString(script, input);
        
        assertEquals(2, result.get("result").size());
        assertEquals(1, result.get("result").get(0).get("id").asInt());
        assertEquals(20, result.get("result").get(0).get("doubled").asInt());
        assertEquals(40, result.get("result").get(1).get("doubled").asInt());
    }
    
    @Test
    public void testCombinedControlFlow() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let processed = for (item of $.items) { " +
            "  if (item.value > 10) { " +
            "    { id: item.id, status: 'high', value: item.value } " +
            "  } else { " +
            "    { id: item.id, status: 'low', value: item.value } " +
            "  } " +
            "}; " +
            "{ result: processed }";
        
        JsonNode input = mapper.readTree(
            "{\"items\": [" +
            "  {\"id\":1,\"value\":5}," +
            "  {\"id\":2,\"value\":15}" +
            "]}"
        );
        JsonNode result = engine.transformFromString(script, input);
        
        assertEquals(2, result.get("result").size());
        assertEquals("low", result.get("result").get(0).get("status").asText());
        assertEquals("high", result.get("result").get(1).get("status").asText());
    }
    
    @Test
    public void testIfAsExpression() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "{ " +
            "  value: $.value, " +
            "  label: if ($.value > 100) { 'large' } else { 'small' } " +
            "}";
        
        JsonNode input = mapper.readTree("{\"value\": 150}");
        JsonNode result = engine.transformFromString(script, input);
        
        assertEquals(150, result.get("value").asInt());
        assertEquals("large", result.get("label").asText());
    }
}
