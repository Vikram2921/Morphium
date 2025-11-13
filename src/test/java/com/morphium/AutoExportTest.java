package com.morphium;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.core.MorphiumEngine;
import com.morphium.runtime.ModuleResolver;
import com.morphium.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class AutoExportTest {
    private MorphiumEngine engine;
    private ModuleResolver moduleResolver;

    @Before
    public void setUp() {
        moduleResolver = new ModuleResolver() {
            @Override
            public String resolve(String modulePath) throws IOException {
                Path testResourcePath = Paths.get("src/test/resources", modulePath);
                if (Files.exists(testResourcePath)) {
                    return Files.readString(testResourcePath);
                }
                throw new IOException("Module not found: " + modulePath);
            }
        };
        engine = new MorphiumEngine(moduleResolver);
    }

    @Test
    public void testAutoExportFunctions() {
        String source = "import \"morphs/AutoExportTest.morph\" as util;\n" +
                        "util.add(20, 3.456)";

        JsonNode input = JsonUtil.createObject();
        JsonNode result = engine.transformFromString(source, input);

        assertEquals(23.456, result.asDouble(), 0.001);
    }

    @Test
    public void testAutoExportMultipleFunctions() {
        String source = "import \"morphs/AutoExportTest.morph\" as util;\n" +
                        "let a = util.add(10, 20);\n" +
                        "let b = util.multiply(5, 6);\n" +
                        "let c = util.round(100, 1);\n" +
                        "a + b + c";

        JsonNode input = JsonUtil.createObject();
        JsonNode result = engine.transformFromString(source, input);
        System.out.println(result);
        assertEquals(160.0, result.asDouble(), 0.001);
    }

    @Test
    public void testAutoExportWithAdd() {
        String source = "import \"morphs/AutoExportTest.morph\" as util;\n" +
                        "util.add(100, 200)";

        JsonNode input = JsonUtil.createObject();
        JsonNode result = engine.transformFromString(source, input);

        assertEquals(300.0, result.asDouble(), 0.001);
    }
}
