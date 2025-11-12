package com.morphium.runtime;

import java.io.IOException;

/**
 * Interface for dynamically resolving Morphium scripts.
 * This allows users to generate transformation scripts programmatically
 * and import them using: import getScript(...params) as alias
 */
@FunctionalInterface
public interface DynamicScriptResolver {
    
    /**
     * Resolve a script dynamically based on the function name and arguments.
     * 
     * @param functionName The name of the function being called (e.g., "getImportMorph")
     * @param args Arguments passed to the function
     * @return The Morphium script source code as a string
     * @throws IOException if script generation fails
     */
    String resolve(String functionName, Object[] args) throws IOException;
}
