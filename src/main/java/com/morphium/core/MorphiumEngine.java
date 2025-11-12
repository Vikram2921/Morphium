package com.morphium.core;

import com.google.gson.JsonElement;
import com.morphium.parser.Lexer;
import com.morphium.parser.Parser;
import com.morphium.parser.ast.Expression;
import com.morphium.runtime.Context;
import com.morphium.runtime.HostFunctionRegistry;
import com.morphium.runtime.ModuleResolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MorphiumEngine {
    private final ModuleResolver moduleResolver;
    private final HostFunctionRegistry functionRegistry;
    private final Context rootContext;

    public MorphiumEngine() {
        this.moduleResolver = new ModuleResolver();
        this.functionRegistry = new HostFunctionRegistry();
        this.rootContext = new Context(functionRegistry);
    }

    public MorphiumEngine(ModuleResolver moduleResolver) {
        this.moduleResolver = moduleResolver;
        this.functionRegistry = new HostFunctionRegistry();
        this.rootContext = new Context(functionRegistry);
    }

    public JsonElement transform(String transformPath, JsonElement input) throws IOException {
        String source = loadSource(transformPath);
        return evaluate(source, input, transformPath);
    }

    public JsonElement transformFromString(String source, JsonElement input) {
        return evaluate(source, input, "<string>");
    }

    public HostFunctionRegistry getFunctionRegistry() {
        return functionRegistry;
    }

    public void registerFunction(String namespace, String name, HostFunction function) {
        functionRegistry.register(namespace, name, function);
    }

    private String loadSource(String path) throws IOException {
        Path filePath = Paths.get(path);
        return Files.readString(filePath);
    }

    private JsonElement evaluate(String source, JsonElement input, String sourcePath) {
        try {
            Lexer lexer = new Lexer(source, sourcePath);
            Parser parser = new Parser(lexer);
            Expression rootExpression = parser.parse();

            Context evalContext = new Context(rootContext);
            evalContext.define("input", input);

            // Handle imports and evaluate
            JsonElement result = rootExpression.evaluate(evalContext);
            
            // Process imports if any
            result = processImports(rootExpression, evalContext, result);

            return result;
        } catch (Exception e) {
            throw new MorphiumException("Error evaluating transform: " + e.getMessage(), e);
        }
    }

    private JsonElement processImports(Expression rootExpr, Context context, JsonElement result) {
        if (rootExpr instanceof com.morphium.parser.ast.BlockExpr) {
            com.morphium.parser.ast.BlockExpr block = (com.morphium.parser.ast.BlockExpr) rootExpr;
            for (Expression expr : block.getExpressions()) {
                if (expr instanceof com.morphium.parser.ast.ImportStatement) {
                    handleImport((com.morphium.parser.ast.ImportStatement) expr, context);
                }
            }
        }
        return result;
    }

    private void handleImport(com.morphium.parser.ast.ImportStatement importStmt, Context context) {
        try {
            String modulePath = importStmt.getModulePath();
            String moduleSource = moduleResolver.resolve(modulePath);
            
            // Parse and evaluate the module
            Lexer lexer = new Lexer(moduleSource, modulePath);
            Parser parser = new Parser(lexer);
            Expression moduleExpr = parser.parse();
            
            Context moduleContext = new Context(rootContext);
            moduleExpr.evaluate(moduleContext);
            
            // Import exports into current context
            String alias = importStmt.getAlias();
            if (alias != null) {
                // Import all exports under alias namespace
                com.google.gson.JsonObject exports = new com.google.gson.JsonObject();
                for (java.util.Map.Entry<String, JsonElement> entry : moduleContext.getAllExports().entrySet()) {
                    exports.add(entry.getKey(), entry.getValue());
                }
                context.define(alias, exports);
                
                // Also import functions
                importModuleFunctions(moduleContext, context, alias);
            }
        } catch (IOException e) {
            throw new MorphiumException("Failed to import module: " + e.getMessage(), e);
        }
    }

    private void importModuleFunctions(Context moduleContext, Context targetContext, String namespace) {
        // This is a simplified approach - in a full implementation,
        // we'd properly handle function namespacing
    }

    @FunctionalInterface
    public interface HostFunction {
        JsonElement call(JsonElement... args);
    }
}
