package com.morphium.runtime;

import com.morphium.parser.ast.Expression;

import java.util.List;

public class UserFunction {
    private final String name;
    private final List<String> parameters;
    private final Expression body;
    private final Context definingContext;

    public UserFunction(String name, List<String> parameters, Expression body) {
        this(name, parameters, body, null);
    }

    public UserFunction(String name, List<String> parameters, Expression body, Context definingContext) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
        this.definingContext = definingContext;
    }

    public String getName() {
        return name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public Expression getBody() {
        return body;
    }

    public Context getDefiningContext() {
        return definingContext;
    }
}
