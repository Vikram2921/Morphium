package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.runtime.Context;

import java.util.ArrayList;
import java.util.List;

public class CallExpr implements Expression {
    private final Expression callee;
    private final List<Expression> arguments;

    public CallExpr(Expression callee) {
        this.callee = callee;
        this.arguments = new ArrayList<>();
    }

    public void addArgument(Expression arg) {
        arguments.add(arg);
    }

    @Override
    public JsonNode evaluate(Context context) {
        if (callee instanceof IdentifierExpr) {
            String funcName = ((IdentifierExpr) callee).getName();
            return context.callFunction(funcName, arguments);
        } else if (callee instanceof MemberAccessExpr) {
            return evaluateMemberCall(context);
        }
        throw new RuntimeException("Cannot call non-function");
    }

    private JsonNode evaluateMemberCall(Context context) {
        // For namespace.function calls
        MemberAccessExpr memberExpr = (MemberAccessExpr) callee;
        // This is simplified - full implementation would extract namespace and function name
        throw new RuntimeException("Member function calls not yet implemented");
    }

    public List<Expression> getArguments() {
        return arguments;
    }
}
