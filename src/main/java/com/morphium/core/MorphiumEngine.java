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

            return rootExpression.evaluate(evalContext);
        } catch (Exception e) {
            throw new MorphiumException("Error evaluating transform: " + e.getMessage(), e);
        }
    }

    @FunctionalInterface
    public interface HostFunction {
        JsonElement call(JsonElement... args);
    }
}
