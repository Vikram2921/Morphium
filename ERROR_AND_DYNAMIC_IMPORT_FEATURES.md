# Error Handling and Dynamic Import Features

## Overview
This document describes the new error handling, logging, and dynamic script import features added to Morphium DSL.

---

## Feature 1: Error Handling

### error() Function

Throw custom errors from within transformation scripts.

**Syntax:**
```javascript
error(message)
```

**Parameters:**
- `message` (string): Error message to throw

**Example:**
```javascript
let age = $.age;
if (age < 0) {
    error('Age cannot be negative');
}
{ validAge: age }
```

**Java Integration:**
```java
try {
    JsonNode result = engine.transformFromString(script, input);
} catch (MorphiumException e) {
    System.err.println("Transform error: " + e.getMessage());
}
```

---

## Feature 2: Logging Functions

### Available Logging Functions

- `log(...messages)` - Log at INFO level
- `logInfo(...messages)` - Log at INFO level explicitly
- `logWarn(...messages)` - Log warning messages
- `logError(...messages)` - Log error messages
- `logDebug(...messages)` - Log debug messages

**Syntax:**
```javascript
log(message1, message2, ...)
logInfo(message)
logWarn(message)
logError(message)
logDebug(message)
```

**Example:**
```javascript
let data = { count: 5 };
log('Processing data with count:', data.count);
logInfo('Starting transformation');
logWarn('This is a warning');
{ result: data.count * 2 }
```

### Custom Logger Integration

Implement the `Logger` interface to capture logs:

```java
import com.morphium.runtime.Logger;

MorphiumEngine engine = new MorphiumEngine();

// Set custom logger
engine.setLogger(new Logger() {
    @Override
    public void log(Level level, String message) {
        System.out.println("[" + level + "] " + message);
    }
});
```

**Logger Levels:**
- `DEBUG` - Detailed debug information
- `INFO` - Informational messages
- `WARN` - Warning messages
- `ERROR` - Error messages

**Default Behavior:**
If no custom logger is set, logs are printed to System.out with the format:
```
[LEVEL] message
```

---

## Feature 3: Dynamic Script Imports

### Overview

Dynamic script imports allow you to generate transformation scripts programmatically at runtime based on parameters. This is useful for:
- Creating configurable transformations
- Template-based script generation
- Runtime code generation based on metadata

### Syntax

```javascript
import functionName(arg1, arg2, ...) as alias
```

### Implementing a Dynamic Resolver

```java
import com.morphium.runtime.DynamicScriptResolver;

MorphiumEngine engine = new MorphiumEngine();

// Register a dynamic resolver
engine.getModuleResolver().registerDynamicResolver("getMultiplier", 
    (functionName, args) -> {
        JsonNode factor = (JsonNode) args[0];
        int multiplier = factor.asInt();
        
        // Generate and return script
        return "export value = " + (multiplier * 10);
    }
);
```

### Using Dynamic Imports

```javascript
// Import dynamically generated script
import getMultiplier(5) as math;

// Use the exported values
{ result: math.value }
```

### Complete Example

```java
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morphium.core.MorphiumEngine;
import com.morphium.runtime.DynamicScriptResolver;

public class DynamicImportExample {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MorphiumEngine engine = new MorphiumEngine();
        
        // Register resolver that generates field mappers
        engine.getModuleResolver().registerDynamicResolver("getFieldMapper", 
            (functionName, args) -> {
                JsonNode fieldNode = (JsonNode) args[0];
                String fieldName = fieldNode.asText();
                
                // Generate transformation script dynamically
                return String.format(
                    "export transform = function(data) { " +
                    "  { %s: data.%s } " +
                    "}",
                    fieldName, fieldName
                );
            }
        );
        
        // Use in transformation
        String script = "import getFieldMapper(\"name\") as mapper; mapper.transform($)";
        
        JsonNode input = mapper.readTree("{\"name\":\"Alice\",\"age\":30}");
        JsonNode result = engine.transformFromString(script, input);
        
        System.out.println(result); // {"name":"Alice"}
    }
}
```

### Advanced: Multi-Parameter Resolvers

```java
engine.getModuleResolver().registerDynamicResolver("buildTransform", 
    (functionName, args) -> {
        JsonNode operation = (JsonNode) args[0];
        JsonNode fieldName = (JsonNode) args[1];
        
        String op = operation.asText();
        String field = fieldName.asText();
        
        switch (op) {
            case "uppercase":
                return "export value = upper($." + field + ")";
            case "double":
                return "export value = $." + field + " * 2";
            default:
                return "export value = $." + field;
        }
    }
);

// Usage
String script = "import buildTransform(\"uppercase\", \"name\") as t; t.value";
```

### Caching

Dynamic scripts are automatically cached based on:
- Function name
- Arguments passed

Subsequent calls with the same arguments will use the cached parsed AST, improving performance.

---

## API Reference

### MorphiumEngine

```java
// Set logger
public void setLogger(Logger logger)

// Get logger
public Logger getLogger()

// Get module resolver for dynamic imports
public ModuleResolver getModuleResolver()
```

### Logger Interface

```java
public interface Logger {
    enum Level { DEBUG, INFO, WARN, ERROR }
    
    void log(Level level, String message);
    
    default void info(String message);
    default void warn(String message);
    default void error(String message);
    default void debug(String message);
}
```

### DynamicScriptResolver Interface

```java
@FunctionalInterface
public interface DynamicScriptResolver {
    /**
     * @param functionName The function being called
     * @param args Arguments passed to the function (as JsonNode objects)
     * @return The generated Morphium script source code
     */
    String resolve(String functionName, Object[] args) throws IOException;
}
```

### ModuleResolver

```java
// Register a dynamic resolver
public void registerDynamicResolver(String functionName, DynamicScriptResolver resolver)

// Check if dynamic resolver exists
public boolean hasDynamicResolver(String functionName)

// Resolve dynamic script
public String resolveDynamic(String functionName, Object[] args) throws IOException
```

---

## Best Practices

### Error Handling
1. **Validate input early** - Use `error()` to fail fast on invalid data
2. **Provide descriptive messages** - Include context in error messages
3. **Catch at boundaries** - Wrap engine calls in try-catch blocks

### Logging
1. **Use appropriate levels** - DEBUG for details, INFO for milestones, WARN for issues, ERROR for failures
2. **Log transformations steps** - Help debugging complex transformations
3. **Implement custom loggers** - Integrate with your logging framework (SLF4J, Log4j, etc.)

### Dynamic Imports
1. **Cache-friendly** - Design resolvers to be deterministic for caching
2. **Security** - Validate and sanitize resolver inputs
3. **Error handling** - Resolvers should throw IOException on failures
4. **Keep it simple** - Generate minimal, focused scripts

---

## Limitations

1. **Dynamic imports cannot call functions** - Currently you can only export values, not callable functions
2. **No nested dynamic imports** - Dynamic imports cannot themselves import other scripts
3. **Args are JsonNodes** - All arguments are evaluated before being passed to resolvers

---

## Examples

See `ErrorAndDynamicImportDemo.java` in the examples folder for comprehensive demonstrations.

---

## Version

Added in: Morphium DSL 1.0.0-SNAPSHOT
Date: 2025-11-12
