package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.morphium.runtime.Context;
import com.morphium.util.JsonUtil;
import lombok.Getter;

/**
 * For-of loop: for (item of array) body
 */
@Getter
public class ForOfStatement implements Expression {
    private final String itemName;
    private final Expression iterable;
    private final Expression body;

    public ForOfStatement(String itemName, Expression iterable, Expression body) {
        this.itemName = itemName;
        this.iterable = iterable;
        this.body = body;
    }

    @Override
    public JsonNode evaluate(Context context) {
        JsonNode iterableValue = iterable.evaluate(context);
        
        if (!iterableValue.isArray()) {
            throw new RuntimeException("For-of loop requires an array");
        }
        
        ArrayNode results = JsonUtil.createArray();
        
        // Iterate over array
        for (JsonNode item : iterableValue) {
            // Create new context for loop iteration
            Context loopContext = new Context(context);
            loopContext.define(itemName, item);
            
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
