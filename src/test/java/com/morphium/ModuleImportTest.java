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

public class ModuleImportTest {
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
    public void testImportModuleWithAlias() {
        String source = "import \"morphs/NumberUtils.morph\" as nutil;\n" +
                        "nutil.add(20, 3.456)";

        JsonNode input = JsonUtil.createObject();
        JsonNode result = engine.transformFromString(source, input);

        assertEquals(23.456, result.asDouble(), 0.001);
    }

    @Test
    public void testImportModuleMultipleFunctionCalls() {
        String source = "import \"morphs/NumberUtils.morph\" as nutil;\n" +
                        "let a = nutil.add(10, 20);\n" +
                        "let b = nutil.multiply(5, 6);\n" +
                        "a + b";

        JsonNode input = JsonUtil.createObject();
        JsonNode result = engine.transformFromString(source, input);

        assertEquals(60.0, result.asDouble(), 0.001);
    }

    @Test
    public void testImportModuleWithVariableUsage() {
        String source = "import \"morphs/NumberUtils.morph\" as math;\n" +
                        "let x = 15.789;\n" +
                        "let y = 10.234;\n" +
                        "math.add(x, y)";

        JsonNode input = JsonUtil.createObject();
        JsonNode result = engine.transformFromString(source, input);

        assertEquals(26.023, result.asDouble(), 0.001);
    }

    @Test
    public void testSimpleModuleImport() {
        String source = "import \"morphs/SimpleTest.morph\" as test;\n" +
                        "test.add(5, 3)";

        JsonNode input = JsonUtil.createObject();
        JsonNode result = engine.transformFromString(source, input);

        assertEquals(8.0, result.asDouble(), 0.001);
    }
}
