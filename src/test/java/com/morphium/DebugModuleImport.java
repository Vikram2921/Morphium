package com.morphium;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.core.MorphiumEngine;
import com.morphium.runtime.ModuleResolver;
import com.morphium.util.JsonUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DebugModuleImport {
    public static void main(String[] args) throws Exception {
        ModuleResolver moduleResolver = new ModuleResolver() {
            @Override
            public String resolve(String modulePath) throws java.io.IOException {
                Path testResourcePath = Paths.get("src/test/resources", modulePath);
                if (Files.exists(testResourcePath)) {
                    String content = Files.readString(testResourcePath);
                    System.out.println("=== Module content ===");
                    System.out.println(content);
                    System.out.println("=====================");
                    return content;
                }
                throw new java.io.IOException("Module not found: " + modulePath);
            }
        };
        
        MorphiumEngine engine = new MorphiumEngine(moduleResolver);
        
        String source = "import \"morphs/NumberUtils.morph\" as nutil;\n" +
                        "nutil.round(23.456, 2)";
        
        System.out.println("=== Transform source ===");
        System.out.println(source);
        System.out.println("========================");
        
        JsonNode input = JsonUtil.createObject();
        JsonNode result = engine.transformFromString(source, input);
        
        System.out.println("=== Result ===");
        System.out.println(result);
        System.out.println("==============");
    }
}
