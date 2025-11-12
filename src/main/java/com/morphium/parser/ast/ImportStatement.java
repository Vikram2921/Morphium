package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.node.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.runtime.Context;

import java.util.List;

public class ImportStatement implements Expression {
    private final String modulePath;
    private final String alias;
    private final List<String> specificImports;
    private final boolean isDynamic;
    private final List<Expression> dynamicArgs;

    public ImportStatement(String modulePath, String alias, List<String> specificImports) {
        this.modulePath = modulePath;
        this.alias = alias;
        this.specificImports = specificImports;
        this.isDynamic = false;
        this.dynamicArgs = null;
    }
    
    public ImportStatement(String modulePath, String alias, List<String> specificImports, 
                          boolean isDynamic, List<Expression> dynamicArgs) {
        this.modulePath = modulePath;
        this.alias = alias;
        this.specificImports = specificImports;
        this.isDynamic = isDynamic;
        this.dynamicArgs = dynamicArgs;
    }

    @Override
    public JsonNode evaluate(Context context) {
        // Module loading is handled by the engine
        // This is just a marker node
        return com.fasterxml.jackson.databind.node.NullNode.getInstance();
    }

    public String getModulePath() {
        return modulePath;
    }

    public String getAlias() {
        return alias;
    }

    public List<String> getSpecificImports() {
        return specificImports;
    }
    
    public boolean isDynamic() {
        return isDynamic;
    }
    
    public List<Expression> getDynamicArgs() {
        return dynamicArgs;
    }
}
