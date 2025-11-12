package com.morphium;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import com.morphium.core.MorphiumException;
import com.morphium.runtime.DynamicScriptResolver;
import com.morphium.runtime.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ErrorAndLoggingTest {
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Test
    public void testLogFunction() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        List<String> logs = new ArrayList<>();
        engine.setLogger((level, message) -> logs.add(message));
        
        String script = "log('Hello', 'World'); { result: 'done' }";
        JsonNode input = mapper.readTree("{}");
        JsonNode result = engine.transformFromString(script, input);
        
        assertEquals("done", result.get("result").asText());
        assertFalse(logs.isEmpty());
    }
    
    @Test
    public void testLogLevels() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        List<String> logs = new ArrayList<>();
        engine.setLogger(new Logger() {
            @Override
            public void log(Level level, String message) {
                logs.add(level + ":" + message);
            }
        });
        
        String script = 
            "logInfo('info message'); " +
            "logWarn('warn message'); " +
            "logError('error message'); " +
            "logDebug('debug message'); " +
            "{ result: 'ok' }";
        
        JsonNode input = mapper.readTree("{}");
        engine.transformFromString(script, input);
        
        assertEquals(4, logs.size());
        assertTrue(logs.get(0).startsWith("INFO:"));
        assertTrue(logs.get(1).startsWith("WARN:"));
        assertTrue(logs.get(2).startsWith("ERROR:"));
        assertTrue(logs.get(3).startsWith("DEBUG:"));
    }
    
    @Test(expected = MorphiumException.class)
    public void testErrorFunction() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = "error('Custom error message')";
        JsonNode input = mapper.readTree("{}");
        
        engine.transformFromString(script, input);
    }
    
    @Test
    public void testConditionalError() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        String script = 
            "let value = $.value; " +
            "if (value < 0, error('Value must be positive'), null); " +
            "{ result: value }";
        
        // Test error case
        JsonNode badInput = mapper.readTree("{\"value\": -5}");
        try {
            engine.transformFromString(script, badInput);
            fail("Expected MorphiumException");
        } catch (MorphiumException e) {
            // Expected
        }
        
        // Test valid case
        JsonNode goodInput = mapper.readTree("{\"value\": 10}");
        JsonNode result = engine.transformFromString(script, goodInput);
        assertEquals(10, result.get("result").asInt());
    }
    
    @Test
    public void testDynamicScriptImport() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        // Register a simple dynamic resolver
        engine.getModuleResolver().registerDynamicResolver("getMultiplier", 
            (functionName, args) -> {
                JsonNode factor = (JsonNode) args[0];
                int multiplier = factor.asInt();
                return "export value = " + (multiplier * 10);
            }
        );
        
        String script = "import getMultiplier(5) as math; math.value";
        JsonNode input = mapper.readTree("{}");
        JsonNode result = engine.transformFromString(script, input);
        
        assertEquals(50, result.asInt());
    }
    
    @Test
    public void testDynamicImportWithStringArg() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        engine.getModuleResolver().registerDynamicResolver("getFieldValue", 
            (functionName, args) -> {
                JsonNode fieldName = (JsonNode) args[0];
                String field = fieldName.asText();
                return "export fieldName = \"" + field + "\"";
            }
        );
        
        String script = "import getFieldValue(\"name\") as extractor; extractor.fieldName";
        JsonNode input = mapper.readTree("{}");
        JsonNode result = engine.transformFromString(script, input);
        
        assertEquals("name", result.asText());
    }
    
    @Test
    public void testDynamicImportCaching() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        int[] callCount = {0};
        engine.getModuleResolver().registerDynamicResolver("getCached", 
            (functionName, args) -> {
                callCount[0]++;
                return "export value = 42";
            }
        );
        
        String script = "import getCached(1) as m1; m1.value";
        JsonNode input = mapper.readTree("{}");
        
        // First call
        engine.transformFromString(script, input);
        int firstCount = callCount[0];
        
        // Second call with same args - should use cache
        engine.transformFromString(script, input);
        
        assertEquals("Dynamic script should be cached", firstCount, callCount[0]);
    }
    
    @Test
    public void testMultipleLogCallsInScript() throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        List<String> logs = new ArrayList<>();
        engine.setLogger((level, message) -> logs.add(message));
        
        String script = 
            "let step1 = log('Step 1'); " +
            "let step2 = log('Step 2'); " +
            "log('Step 3'); " +
            "{ done: true }";
        
        JsonNode input = mapper.readTree("{}");
        engine.transformFromString(script, input);
        
        assertEquals(3, logs.size());
        assertTrue(logs.get(0).contains("Step 1"));
        assertTrue(logs.get(1).contains("Step 2"));
        assertTrue(logs.get(2).contains("Step 3"));
    }
}
