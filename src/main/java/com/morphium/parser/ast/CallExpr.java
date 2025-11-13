package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.runtime.Context;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CallExpr implements Expression {
    private final Expression callee;
    @Getter
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
            return evaluateMemberCall((MemberAccessExpr) callee, context);
        }
        throw new RuntimeException("Cannot call non-function");
    }

    private JsonNode evaluateMemberCall(MemberAccessExpr memberExpr, Context context) {
        Expression objectExpr = memberExpr.getObject();
        Expression propertyExpr = memberExpr.getProperty();
        
        if (objectExpr instanceof IdentifierExpr && propertyExpr instanceof IdentifierExpr) {
            String namespace = ((IdentifierExpr) objectExpr).getName();
            String funcName = ((IdentifierExpr) propertyExpr).getName();
            
            com.morphium.runtime.UserFunction moduleFunc = context.getModuleFunction(namespace, funcName);
            if (moduleFunc != null) {
                return callUserFunction(moduleFunc, context);
            }
        }
        
        throw new RuntimeException("Member function call not supported for " + 
            ((objectExpr instanceof IdentifierExpr) ? ((IdentifierExpr)objectExpr).getName() : "?") + 
            "." + 
            ((propertyExpr instanceof IdentifierExpr) ? ((IdentifierExpr)propertyExpr).getName() : "?"));
    }

    private JsonNode callUserFunction(com.morphium.runtime.UserFunction func, Context context) {
        java.util.List<JsonNode> argValues = new java.util.ArrayList<>();
        for (Expression argExpr : arguments) {
            argValues.add(argExpr.evaluate(context));
        }

        // Use the function's defining context as parent if available, otherwise use current context
        Context parentContext = func.getDefiningContext() != null ? func.getDefiningContext() : context;
        Context funcContext = new Context(parentContext);

        java.util.List<String> params = func.getParameters();
        for (int i = 0; i < params.size(); i++) {
            JsonNode value = i < argValues.size() ? argValues.get(i) : com.fasterxml.jackson.databind.node.NullNode.getInstance();
            funcContext.define(params.get(i), value);
        }

        return func.getBody().evaluate(funcContext);
    }

}
