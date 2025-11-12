# Java API

Learn how to integrate and use Morphium DSL from Java applications with the complete API reference.

---

## Overview

The Morphium Java API provides:
- **Simple integration** with Java applications
- **Type-safe transformations** with Java objects
- **Custom function registration** for extending functionality
- **Performance optimization** with caching
- **Error handling** with exceptions
- **Logging configuration** for monitoring

---

## Quick Start

### Maven Dependency

```xml
<dependency>
    <groupId>com.morphium</groupId>
    <artifactId>morphium-dsl</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

---

### Basic Usage

```java
import com.morphium.core.MorphiumEngine;
import com.morphium.core.MorphiumException;

public class Example {
    public static void main(String[] args) {
        // Create engine instance
        MorphiumEngine engine = new MorphiumEngine();
        
        // Define transformation script
        String script = "{name: $.user.name, age: $.user.age}";
        
        // Input data as JSON string
        String input = "{\"user\": {\"name\": \"John\", \"age\": 30}}";
        
        try {
            // Execute transformation
            String result = engine.transform(script, input);
            System.out.println(result);
            // Output: {"name":"John","age":30}
        } catch (MorphiumException e) {
            System.err.println("Transformation failed: " + e.getMessage());
        }
    }
}
```

---

## Core API

### MorphiumEngine Class

The main class for executing transformations.

```java
public class MorphiumEngine {
    // Constructor
    public MorphiumEngine()
    
    // Transform JSON string input
    public String transform(String script, String jsonInput) throws MorphiumException
    
    // Transform Java Object input
    public Object transform(String script, Object input) throws MorphiumException
    
    // Transform with Map input
    public Map<String, Object> transform(String script, Map<String, Object> input) throws MorphiumException
    
    // Register custom function
    public void registerFunction(String name, MorphiumFunction function)
    
    // Set custom logger
    public void setLogger(MorphiumLogger logger)
    
    // Enable/disable caching
    public void setCachingEnabled(boolean enabled)
    
    // Clear script cache
    public void clearCache()
}
```

---

## Input/Output Options

### Example 1: JSON String Input/Output

```java
MorphiumEngine engine = new MorphiumEngine();

String script = """
    {
        total: sum(map($.orders, "o", o.amount)),
        count: len($.orders)
    }
    """;

String input = """
    {
        "orders": [
            {"id": 1, "amount": 100},
            {"id": 2, "amount": 200}
        ]
    }
    """;

String result = engine.transform(script, input);
System.out.println(result);
// {"total":300,"count":2}
```

---

### Example 2: Java Object Input

```java
// Define Java classes
class Order {
    int id;
    double amount;
    // constructors, getters, setters
}

class OrderData {
    List<Order> orders;
    // constructors, getters, setters
}

// Create input
OrderData data = new OrderData();
data.orders = Arrays.asList(
    new Order(1, 100.0),
    new Order(2, 200.0)
);

String script = """
    {
        total: sum(map($.orders, "o", o.amount)),
        count: len($.orders)
    }
    """;

Object result = engine.transform(script, data);
System.out.println(result);
```

---

### Example 3: Map Input/Output

```java
Map<String, Object> input = new HashMap<>();
input.put("value", 42);
input.put("name", "Test");

String script = "{doubled: $.value * 2, name: upper($.name)}";

Map<String, Object> result = engine.transform(script, input);
System.out.println(result.get("doubled")); // 84
System.out.println(result.get("name"));    // TEST
```

---

## Error Handling

### Example 4: Handling Exceptions

```java
MorphiumEngine engine = new MorphiumEngine();

String script = """
    {
        result: if ($.value < 0) {
            error("Value must be positive")
        } else {
            $.value * 2
        }
    }
    """;

String input = "{\"value\": -5}";

try {
    String result = engine.transform(script, input);
    System.out.println(result);
} catch (MorphiumException e) {
    System.err.println("Error: " + e.getMessage());
    // Error: Value must be positive
    
    // Get stack trace
    e.printStackTrace();
    
    // Get error location
    if (e.getLine() != -1) {
        System.err.println("Error at line: " + e.getLine());
    }
}
```

---

### Example 5: Validation Before Transformation

```java
public class TransformationService {
    private final MorphiumEngine engine = new MorphiumEngine();
    
    public String safeTransform(String script, String input) {
        // Validate inputs
        if (script == null || script.isEmpty()) {
            throw new IllegalArgumentException("Script cannot be empty");
        }
        
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }
        
        try {
            return engine.transform(script, input);
        } catch (MorphiumException e) {
            // Log error
            System.err.println("Transformation failed: " + e.getMessage());
            
            // Return error response
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }
}
```

---

## Custom Functions

### Example 6: Registering Custom Function

```java
import com.morphium.function.MorphiumFunction;

public class CustomFunctions {
    public static void setupEngine(MorphiumEngine engine) {
        // Register custom UUID generator
        engine.registerFunction("uuid", new MorphiumFunction() {
            @Override
            public Object call(Object... args) {
                return UUID.randomUUID().toString();
            }
        });
        
        // Register custom hash function
        engine.registerFunction("hash", new MorphiumFunction() {
            @Override
            public Object call(Object... args) {
                if (args.length == 0) return null;
                String input = args[0].toString();
                return Integer.toHexString(input.hashCode());
            }
        });
        
        // Register custom validation
        engine.registerFunction("validateEmail", new MorphiumFunction() {
            @Override
            public Object call(Object... args) {
                if (args.length == 0) return false;
                String email = args[0].toString();
                return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
            }
        });
    }
    
    public static void main(String[] args) throws MorphiumException {
        MorphiumEngine engine = new MorphiumEngine();
        setupEngine(engine);
        
        String script = """
            {
                id: uuid(),
                emailHash: hash($.email),
                emailValid: validateEmail($.email)
            }
            """;
        
        String input = "{\"email\": \"test@example.com\"}";
        String result = engine.transform(script, input);
        System.out.println(result);
    }
}
```

---

### Example 7: Custom Function with Multiple Arguments

```java
engine.registerFunction("between", new MorphiumFunction() {
    @Override
    public Object call(Object... args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("between requires 3 arguments");
        }
        
        double value = ((Number) args[0]).doubleValue();
        double min = ((Number) args[1]).doubleValue();
        double max = ((Number) args[2]).doubleValue();
        
        return value >= min && value <= max;
    }
});

// Usage in script
String script = "{valid: between($.age, 18, 65)}";
```

---

### Example 8: Custom Function with Complex Logic

```java
engine.registerFunction("calculateDiscount", new MorphiumFunction() {
    @Override
    public Object call(Object... args) {
        if (args.length < 2) return 0.0;
        
        double price = ((Number) args[0]).doubleValue();
        String customerTier = args[1].toString();
        
        double discount = switch (customerTier) {
            case "gold" -> 0.20;
            case "silver" -> 0.10;
            case "bronze" -> 0.05;
            default -> 0.0;
        };
        
        return price * (1 - discount);
    }
});
```

---

## Logging Configuration

### Example 9: Custom Logger

```java
import com.morphium.core.MorphiumLogger;

public class CustomLogger implements MorphiumLogger {
    @Override
    public void log(String level, String message) {
        System.out.println("[" + level + "] " + message);
    }
    
    @Override
    public void debug(String message) {
        System.out.println("[DEBUG] " + message);
    }
    
    @Override
    public void info(String message) {
        System.out.println("[INFO] " + message);
    }
    
    @Override
    public void warn(String message) {
        System.err.println("[WARN] " + message);
    }
    
    @Override
    public void error(String message) {
        System.err.println("[ERROR] " + message);
    }
}

// Usage
MorphiumEngine engine = new MorphiumEngine();
engine.setLogger(new CustomLogger());
```

---

### Example 10: SLF4J Integration

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JLogger implements MorphiumLogger {
    private static final Logger logger = LoggerFactory.getLogger(SLF4JLogger.class);
    
    @Override
    public void log(String level, String message) {
        switch (level.toUpperCase()) {
            case "DEBUG" -> logger.debug(message);
            case "INFO" -> logger.info(message);
            case "WARN" -> logger.warn(message);
            case "ERROR" -> logger.error(message);
            default -> logger.info(message);
        }
    }
    
    @Override
    public void debug(String message) {
        logger.debug(message);
    }
    
    @Override
    public void info(String message) {
        logger.info(message);
    }
    
    @Override
    public void warn(String message) {
        logger.warn(message);
    }
    
    @Override
    public void error(String message) {
        logger.error(message);
    }
}
```

---

## Performance Optimization

### Example 11: Script Caching

```java
public class CachedTransformationService {
    private final MorphiumEngine engine;
    
    public CachedTransformationService() {
        engine = new MorphiumEngine();
        // Enable caching (enabled by default)
        engine.setCachingEnabled(true);
    }
    
    public String transform(String script, String input) throws MorphiumException {
        // Script is parsed once and cached
        // Subsequent calls with same script use cached version
        return engine.transform(script, input);
    }
    
    public void clearCache() {
        // Clear cache when scripts are updated
        engine.clearCache();
    }
}
```

---

### Example 12: Reusing Engine Instance

```java
@Service
public class TransformationService {
    // Reuse single engine instance (thread-safe)
    private final MorphiumEngine engine = new MorphiumEngine();
    
    public TransformationService() {
        // Setup custom functions once
        setupCustomFunctions();
    }
    
    private void setupCustomFunctions() {
        engine.registerFunction("timestamp", args -> System.currentTimeMillis());
        // Register other functions...
    }
    
    public String processOrder(String orderJson) throws MorphiumException {
        String script = loadScript("order-transformation.morph");
        return engine.transform(script, orderJson);
    }
    
    private String loadScript(String filename) {
        // Load script from file/resource
        return "...";
    }
}
```

---

## Integration Patterns

### Example 13: Spring Boot Integration

```java
@Configuration
public class MorphiumConfig {
    @Bean
    public MorphiumEngine morphiumEngine() {
        MorphiumEngine engine = new MorphiumEngine();
        engine.setLogger(new SLF4JLogger());
        
        // Register custom functions
        registerCustomFunctions(engine);
        
        return engine;
    }
    
    private void registerCustomFunctions(MorphiumEngine engine) {
        engine.registerFunction("uuid", args -> UUID.randomUUID().toString());
        // More functions...
    }
}

@Service
public class DataTransformationService {
    @Autowired
    private MorphiumEngine engine;
    
    public Map<String, Object> transformData(Map<String, Object> input) {
        String script = "{ /* transformation */ }";
        try {
            return engine.transform(script, input);
        } catch (MorphiumException e) {
            throw new RuntimeException("Transformation failed", e);
        }
    }
}
```

---

### Example 14: REST API Endpoint

```java
@RestController
@RequestMapping("/api/transform")
public class TransformationController {
    @Autowired
    private MorphiumEngine engine;
    
    @PostMapping
    public ResponseEntity<?> transform(@RequestBody TransformRequest request) {
        try {
            String result = engine.transform(request.getScript(), request.getData());
            return ResponseEntity.ok(result);
        } catch (MorphiumException e) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }
}

class TransformRequest {
    private String script;
    private String data;
    // getters, setters
}
```

---

### Example 15: Batch Processing

```java
public class BatchProcessor {
    private final MorphiumEngine engine = new MorphiumEngine();
    
    public List<String> processBatch(String script, List<String> inputs) {
        List<String> results = new ArrayList<>();
        
        for (String input : inputs) {
            try {
                String result = engine.transform(script, input);
                results.add(result);
            } catch (MorphiumException e) {
                // Log error and continue
                System.err.println("Failed to process: " + e.getMessage());
                results.add("{\"error\": \"" + e.getMessage() + "\"}");
            }
        }
        
        return results;
    }
    
    public CompletableFuture<List<String>> processBatchAsync(
            String script, List<String> inputs) {
        return CompletableFuture.supplyAsync(() -> processBatch(script, inputs));
    }
}
```

---

## Testing

### Example 16: Unit Testing Transformations

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransformationTest {
    private final MorphiumEngine engine = new MorphiumEngine();
    
    @Test
    void testSimpleTransformation() throws MorphiumException {
        String script = "{doubled: $.value * 2}";
        String input = "{\"value\": 21}";
        
        String result = engine.transform(script, input);
        
        assertTrue(result.contains("\"doubled\":42"));
    }
    
    @Test
    void testErrorHandling() {
        String script = "{result: error('Test error')}";
        String input = "{}";
        
        assertThrows(MorphiumException.class, () -> {
            engine.transform(script, input);
        });
    }
    
    @Test
    void testCustomFunction() throws MorphiumException {
        engine.registerFunction("double", args -> 
            ((Number) args[0]).doubleValue() * 2
        );
        
        String script = "{result: double($.value)}";
        String input = "{\"value\": 10}";
        
        String result = engine.transform(script, input);
        assertTrue(result.contains("\"result\":20"));
    }
}
```

---

## Best Practices

### ✅ Do's

1. **Reuse engine instances** - They're thread-safe
2. **Register functions once** - During initialization
3. **Enable caching** - For better performance
4. **Handle exceptions** - Always catch MorphiumException
5. **Use custom logger** - Integrate with your logging system
6. **Validate inputs** - Check before transformation
7. **Test transformations** - Write unit tests

### ❌ Don'ts

1. **Don't create engine per request** - Performance overhead
2. **Don't ignore exceptions** - Handle errors properly
3. **Don't disable caching** - Unless necessary
4. **Don't transform untrusted scripts** - Security risk
5. **Don't block threads** - Use async for long operations

---

## Complete Example

```java
public class CompleteExample {
    private final MorphiumEngine engine;
    
    public CompleteExample() {
        engine = new MorphiumEngine();
        setupEngine();
    }
    
    private void setupEngine() {
        // Custom logger
        engine.setLogger(new SLF4JLogger());
        
        // Custom functions
        engine.registerFunction("uuid", args -> UUID.randomUUID().toString());
        engine.registerFunction("timestamp", args -> System.currentTimeMillis());
        
        // Enable caching
        engine.setCachingEnabled(true);
    }
    
    public String processUser(User user) {
        String script = """
            {
                id: uuid(),
                name: $.firstName + " " + $.lastName,
                email: lower($.email),
                registered: timestamp(),
                role: if ($.admin) { "admin" } else { "user" }
            }
            """;
        
        try {
            return engine.transform(script, user);
        } catch (MorphiumException e) {
            System.err.println("Transformation failed: " + e.getMessage());
            throw new RuntimeException("User processing failed", e);
        }
    }
    
    public static void main(String[] args) {
        CompleteExample example = new CompleteExample();
        
        User user = new User("John", "Doe", "john@example.com", false);
        String result = example.processUser(user);
        
        System.out.println(result);
    }
}
```

---

## Related Topics

- [Custom Functions](20-custom-functions.md) - Extend with Java functions
- [Custom Logger](21-custom-logger.md) - Implement custom logging
- [Error Handling](16-error-handling.md) - Handle errors in scripts
- [Performance Tips](18-performance.md) - Optimize performance

---

## Summary

The Morphium Java API provides:
- ✅ Simple integration with Java applications
- ✅ Multiple input/output formats
- ✅ Custom function registration
- ✅ Flexible logging configuration
- ✅ Built-in caching for performance
- ✅ Thread-safe execution
- ✅ Comprehensive error handling

---

[← Back to Documentation](README.md)
