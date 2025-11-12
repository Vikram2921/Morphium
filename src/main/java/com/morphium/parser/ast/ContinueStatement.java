package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.runtime.Context;

/**
 * Continue statement: continue
 * Used to skip to the next iteration of a loop
 */
public class ContinueStatement implements Expression {
    
    @Override
    public JsonNode evaluate(Context context) {
        throw new ContinueException();
    }
    
    /**
     * Exception used for control flow to continue to next loop iteration
     */
    public static class ContinueException extends RuntimeException {
        public ContinueException() {
            super(null, null, false, false);
        }
    }
}
