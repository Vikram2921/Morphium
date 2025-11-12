package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.runtime.Context;

import java.util.List;

/**
 * Switch statement: switch (expression) { case value: ... default: ... }
 */
public class SwitchStatement implements Expression {
    private final Expression expression;
    private final List<CaseClause> cases;
    private final Expression defaultCase;

    public SwitchStatement(Expression expression, List<CaseClause> cases, Expression defaultCase) {
        this.expression = expression;
        this.cases = cases;
        this.defaultCase = defaultCase;
    }

    @Override
    public JsonNode evaluate(Context context) {
        JsonNode switchValue = expression.evaluate(context);
        
        // Try to match a case
        for (CaseClause caseClause : cases) {
            JsonNode caseValue = caseClause.getValue().evaluate(context);
            
            if (equals(switchValue, caseValue)) {
                return caseClause.getBody().evaluate(context);
            }
        }
        
        // No case matched, use default
        if (defaultCase != null) {
            return defaultCase.evaluate(context);
        }
        
        return com.fasterxml.jackson.databind.node.NullNode.getInstance();
    }
    
    private boolean equals(JsonNode a, JsonNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        
        // If both are numbers, compare numerically
        if (a.isNumber() && b.isNumber()) {
            return a.asDouble() == b.asDouble();
        }
        
        // If both are text, compare as strings
        if (a.isTextual() && b.isTextual()) {
            return a.asText().equals(b.asText());
        }
        
        // Otherwise use Jackson's equals
        return a.equals(b);
    }

    public Expression getExpression() {
        return expression;
    }

    public List<CaseClause> getCases() {
        return cases;
    }

    public Expression getDefaultCase() {
        return defaultCase;
    }

    /**
     * Represents a single case in a switch statement
     */
    public static class CaseClause {
        private final Expression value;
        private final Expression body;

        public CaseClause(Expression value, Expression body) {
            this.value = value;
            this.body = body;
        }

        public Expression getValue() {
            return value;
        }

        public Expression getBody() {
            return body;
        }
    }
}
