package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.morphium.runtime.Context;
import com.morphium.util.JsonUtil;
import lombok.Getter;

/**
 * For-in loop: for (index in array) body
 * Provides index-based iteration over arrays
 */
@Getter
public class ForInStatement implements Expression {
    private final String indexName;
    private final Expression iterable;
    private final Expression body;

    public ForInStatement(String indexName, Expression iterable, Expression body) {
        this.indexName = indexName;
        this.iterable = iterable;
        this.body = body;
    }

    @Override
    public JsonNode evaluate(Context context) {
        JsonNode iterableValue = iterable.evaluate(context);
        
        if (!iterableValue.isArray()) {
            throw new RuntimeException("For-in loop requires an array");
        }
        
        ArrayNode results = JsonUtil.createArray();
        int size = iterableValue.size();
        
        // Iterate over indices
        for (int i = 0; i < size; i++) {
            // Create new context for loop iteration
            Context loopContext = new Context(context);
            loopContext.define(indexName, IntNode.valueOf(i));
            
            try {
                // Evaluate body
                JsonNode result = body.evaluate(loopContext);
                
                // Collect results
                if (result != null && !result.isNull()) {
                    results.add(result);
                }
            } catch (BreakStatement.BreakException e) {
                // Break out of loop
                break;
            } catch (ContinueStatement.ContinueException e) {
                // Continue to next iteration
                continue;
            }
        }
        
        return results;
    }

}
