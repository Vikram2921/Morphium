package com.morphium.runtime;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Data
public class ModuleResolver {
    private Path basePath;
    private final Map<String, DynamicScriptResolver> dynamicResolvers;

    public ModuleResolver() {
        this.basePath = Paths.get(System.getProperty("user.dir"));
        this.dynamicResolvers = new HashMap<>();
    }

    public ModuleResolver(Path basePath) {
        this.basePath = basePath;
        this.dynamicResolvers = new HashMap<>();
    }
    
    /**
     * Register a dynamic script resolver for a specific function name
     * @param functionName The name that will be used in import statements
     * @param resolver The resolver that will generate the script
     */
    public void registerDynamicResolver(String functionName, DynamicScriptResolver resolver) {
        dynamicResolvers.put(functionName, resolver);
    }
    
    /**
     * Resolve a dynamic script
     * @param functionName The function name
     * @param args Arguments to pass to the resolver
     * @return The generated script source code
     * @throws IOException if resolution fails
     */
    public String resolveDynamic(String functionName, Object[] args) throws IOException {
        DynamicScriptResolver resolver = dynamicResolvers.get(functionName);
        if (resolver == null) {
            throw new IOException("No dynamic resolver registered for: " + functionName);
        }
        return resolver.resolve(functionName, args);
    }
    
    /**
     * Check if a dynamic resolver exists for the given function name
     * @param functionName The function name to check
     * @return true if a resolver exists
     */
    public boolean hasDynamicResolver(String functionName) {
        return dynamicResolvers.containsKey(functionName);
    }

    public String resolve(String modulePath) throws IOException {
        Path fullPath = basePath.resolve(modulePath);
        if (Files.exists(fullPath)) {
            return Files.readString(fullPath);
        }

        // Fallback: try reading from classpath (resources)
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(modulePath)) {
            if (is == null) {
                throw new IOException("Module not found: " + modulePath);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
