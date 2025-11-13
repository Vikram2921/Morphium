package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.morphium.runtime.Context;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BlockExpr implements Expression {
    private final List<Expression> expressions;

    public BlockExpr() {
        this.expressions = new ArrayList<>();
    }

    public void addExpression(Expression expr) {
        expressions.add(expr);
    }

    @Override
    public JsonNode evaluate(Context context) {
        JsonNode result = null;
        Context blockContext = new Context(context);
        
        for (Expression expr : expressions) {
            result = expr.evaluate(blockContext);
            
            // Function definitions, global declarations, and let statements modify context
            // but don't produce a final result
            if (expr instanceof FunctionDefExpr || 
                expr instanceof GlobalVarStatement ||
                expr instanceof LetStatement ||
                expr instanceof ImportStatement ||
                expr instanceof ExportStatement) {
                // These update context but continue to next expression
                // Keep the previous result
                continue;
            }
        }
        
        // If all expressions were declarations, return the last one
        // Otherwise return the last non-declaration result
        return result != null ? result : NullNode.getInstance();
    }

}
