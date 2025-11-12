package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.node.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.runtime.Context;

import java.util.List;

public class ImportStatement implements Expression {
    private final String modulePath;
    private final String alias;
    private final List<String> specificImports;

    public ImportStatement(String modulePath, String alias, List<String> specificImports) {
        this.modulePath = modulePath;
        this.alias = alias;
        this.specificImports = specificImports;
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
}
