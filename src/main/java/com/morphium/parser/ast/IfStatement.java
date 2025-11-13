package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.runtime.Context;
import lombok.Getter;

/**
 * If-else statement: if (condition) thenBranch else elseBranch
 */
@Getter
public class IfStatement implements Expression {
    private final Expression condition;
    private final Expression thenBranch;
    private final Expression elseBranch;

    public IfStatement(Expression condition, Expression thenBranch, Expression elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public JsonNode evaluate(Context context) {
        JsonNode conditionValue = condition.evaluate(context);
        
        // Evaluate condition as boolean
        boolean isTrue = isTruthy(conditionValue);
        
        if (isTrue) {
            return thenBranch.evaluate(context);
        } else if (elseBranch != null) {
            return elseBranch.evaluate(context);
        }
        
        return com.fasterxml.jackson.databind.node.NullNode.getInstance();
    }
    
    private boolean isTruthy(JsonNode node) {
        if (node == null || node.isNull()) return false;
        if (node.isBoolean()) return node.asBoolean();
        if (node.isNumber()) return node.asDouble() != 0;
        if (node.isTextual()) return !node.asText().isEmpty();
        if (node.isArray()) return !node.isEmpty();
        if (node.isObject()) return !node.isEmpty();
        return true;
    }

}
