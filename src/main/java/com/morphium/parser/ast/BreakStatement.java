package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.runtime.Context;

/**
 * Break statement: break
 * Used to exit a loop early
 */
public class BreakStatement implements Expression {
    
    @Override
    public JsonNode evaluate(Context context) {
        throw new BreakException();
    }
    
    /**
     * Exception used for control flow to break out of loops
     */
    public static class BreakException extends RuntimeException {
        public BreakException() {
            super(null, null, false, false);
        }
    }
}
