package com.morphium.core;

import com.morphium.util.JsonUtil;

import com.fasterxml.jackson.databind.JsonNode;
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

    public JsonNode transform(String transformPath, JsonNode input) throws IOException {
        String source = loadSource(transformPath);
        return evaluate(source, input, transformPath);
    }

    public JsonNode transformFromString(String source, JsonNode input) {
        return evaluate(source, input, "<string>");
    }

    public HostFunctionRegistry getFunctionRegistry() {
        return functionRegistry;
    }

    public void registerFunction(String namespace, String name, HostFunction function) {
        functionRegistry.register(namespace, name, function);
    }
    
    public void registerFunction(com.morphium.function.MorphiumFunction function) {
        // Store a reference that will be resolved during evaluation
        final com.morphium.function.MorphiumFunction func = function;
        functionRegistry.register("", function.getName(), args -> {
            if (args.length < func.getMinParams() || 
                (func.getMaxParams() != -1 && args.length > func.getMaxParams())) {
                throw new MorphiumException(
                    "Function " + func.getName() + " expects " + 
                    func.getMinParams() + " to " + func.getMaxParams() + 
                    " parameters, got " + args.length
                );
            }
            // Pass null as root for now - will be enhanced later
            return func.call(null, args);
        });
    }

    private String loadSource(String path) throws IOException {
        Path filePath = Paths.get(path);
        return Files.readString(filePath);
    }

    private JsonNode evaluate(String source, JsonNode input, String sourcePath) {
        try {
            Lexer lexer = new Lexer(source, sourcePath);
            Parser parser = new Parser(lexer);
            Expression rootExpression = parser.parse();

            Context evalContext = new Context(rootContext);
            evalContext.define("$", input);

            // Handle imports and evaluate
            JsonNode result = rootExpression.evaluate(evalContext);
            
            // Process imports if any
            result = processImports(rootExpression, evalContext, result);

            return result;
        } catch (Exception e) {
            throw new MorphiumException("Error evaluating transform: " + e.getMessage(), e);
        }
    }

    private JsonNode processImports(Expression rootExpr, Context context, JsonNode result) {
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
                com.fasterxml.jackson.databind.node.ObjectNode exports = com.morphium.util.JsonUtil.createObject();
                for (java.util.Map.Entry<String, JsonNode> entry : moduleContext.getAllExports().entrySet()) {
                    exports.set(entry.getKey(), entry.getValue());
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
        JsonNode call(JsonNode... args);
    }
}
