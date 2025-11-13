package com.morphium.examples;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.core.MorphiumEngine;
import com.morphium.runtime.ModuleResolver;
import com.morphium.util.JsonUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModuleImportExample {
    public static void main(String[] args) throws Exception {
        // Setup module resolver
        ModuleResolver moduleResolver = new ModuleResolver() {
            @Override
            public String resolve(String modulePath) throws java.io.IOException {
                Path testResourcePath = Paths.get("src/test/resources", modulePath);
                if (Files.exists(testResourcePath)) {
                    return Files.readString(testResourcePath);
                }
                throw new java.io.IOException("Module not found: " + modulePath);
            }
        };
        
        MorphiumEngine engine = new MorphiumEngine(moduleResolver);
        
        // Example 1: Simple function call
        System.out.println("=== Example 1: Import and use add function ===");
        String script1 = "import \"morphs/NumberUtils.morph\" as nutil;\n" +
                         "nutil.add(10, 32)";
        
        JsonNode input = JsonUtil.createObject();
        JsonNode result1 = engine.transformFromString(script1, input);
        System.out.println("Result: " + result1);
        
        // Example 2: Multiple function calls
        System.out.println("\n=== Example 2: Multiple function calls ===");
        String script2 = "import \"morphs/NumberUtils.morph\" as math;\n" +
                         "let a = math.add(15, 25);\n" +
                         "let b = math.multiply(a, 2);\n" +
                         "b";
        
        JsonNode result2 = engine.transformFromString(script2, input);
        System.out.println("Result: " + result2);
        
        // Example 3: Using different alias
        System.out.println("\n=== Example 3: Different alias name ===");
        String script3 = "import \"morphs/NumberUtils.morph\" as calc;\n" +
                         "calc.multiply(7, 8)";
        
        JsonNode result3 = engine.transformFromString(script3, input);
        System.out.println("Result: " + result3);
        
        System.out.println("\n✓ Module import feature working successfully!");
    }
}
