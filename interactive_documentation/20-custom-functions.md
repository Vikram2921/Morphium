# Custom Functions

Learn how to create and register custom functions to extend Morphium DSL with Java code.

---

## Overview

Custom functions allow you to:
- **Extend Morphium DSL** with Java functionality
- **Integrate external libraries** and APIs
- **Implement business logic** in Java
- **Access Java ecosystem** from transformations
- **Create reusable utilities** across projects

---

## Creating Custom Functions

### Basic Function Interface

```java
import com.morphium.function.MorphiumFunction;

public interface MorphiumFunction {
    Object call(Object... args);
}
```

---

## Simple Examples

### Example 1: Parameterless Function

```java
engine.registerFunction("random", new MorphiumFunction() {
    @Override
    public Object call(Object... args) {
        return Math.random();
    }
});
```

**Usage in script:**
```javascript
{value: random()}
```

---

### Example 2: Single Parameter Function

```java
engine.registerFunction("square", new MorphiumFunction() {
    @Override
    public Object call(Object... args) {
        if (args.length == 0) return null;
        double value = ((Number) args[0]).doubleValue();
        return value * value;
    }
});
```

**Usage:**
```javascript
{result: square($.value)}
```

---

### Example 3: Multiple Parameters

```java
engine.registerFunction("power", new MorphiumFunction() {
    @Override
    public Object call(Object... args) {
        if (args.length < 2) return null;
        double base = ((Number) args[0]).doubleValue();
        double exponent = ((Number) args[1]).doubleValue();
        return Math.pow(base, exponent);
    }
});
```

**Usage:**
```javascript
{result: power(2, 8)}  // 256
```

---

## Advanced Examples

### Example 4: UUID Generation

```java
import java.util.UUID;

engine.registerFunction("uuid", new MorphiumFunction() {
    @Override
    public Object call(Object... args) {
        return UUID.randomUUID().toString();
    }
});

engine.registerFunction("uuidShort", new MorphiumFunction() {
    @Override
    public Object call(Object... args) {
        return UUID.randomUUID().toString().substring(0, 8);
    }
});
```

---

### Example 5: Date/Time Functions

```java
import java.time.*;
import java.time.format.DateTimeFormatter;

// Get current date
engine.registerFunction("currentDate", args -> 
    LocalDate.now().toString()
);

// Format date
engine.registerFunction("formatDate", args -> {
    if (args.length < 2) return null;
    long timestamp = ((Number) args[0]).longValue();
    String pattern = args[1].toString();
    
    LocalDateTime dateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(timestamp), 
        ZoneId.systemDefault()
    );
    
    return dateTime.format(DateTimeFormatter.ofPattern(pattern));
});

// Add days to date
engine.registerFunction("addDays", args -> {
    if (args.length < 2) return null;
    long timestamp = ((Number) args[0]).longValue();
    int days = ((Number) args[1]).intValue();
    
    LocalDateTime dateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(timestamp), 
        ZoneId.systemDefault()
    );
    
    return dateTime.plusDays(days)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli();
});
```

---

### Example 6: String Functions

```java
// Capitalize
engine.registerFunction("capitalize", args -> {
    if (args.length == 0) return null;
    String str = args[0].toString();
    if (str.isEmpty()) return str;
    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
});

// Slugify
engine.registerFunction("slugify", args -> {
    if (args.length == 0) return null;
    return args[0].toString()
        .toLowerCase()
        .replaceAll("\\s+", "-")
        .replaceAll("[^a-z0-9-]", "");
});

// Truncate with ellipsis
engine.registerFunction("truncate", args -> {
    if (args.length < 2) return null;
    String str = args[0].toString();
    int maxLen = ((Number) args[1]).intValue();
    
    if (str.length() <= maxLen) return str;
    return str.substring(0, maxLen) + "...";
});
```

---

### Example 7: Validation Functions

```java
// Email validation
engine.registerFunction("isValidEmail", args -> {
    if (args.length == 0) return false;
    String email = args[0].toString();
    return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
});

// URL validation
engine.registerFunction("isValidUrl", args -> {
    if (args.length == 0) return false;
    try {
        new URL(args[0].toString());
        return true;
    } catch (Exception e) {
        return false;
    }
});

// Phone validation (US)
engine.registerFunction("isValidPhone", args -> {
    if (args.length == 0) return false;
    String phone = args[0].toString();
    return phone.matches("^\\d{3}-\\d{3}-\\d{4}$");
});
```

---

### Example 8: Mathematical Functions

```java
// Round to decimals
engine.registerFunction("roundTo", args -> {
    if (args.length < 2) return null;
    double value = ((Number) args[0]).doubleValue();
    int decimals = ((Number) args[1]).intValue();
    
    double scale = Math.pow(10, decimals);
    return Math.round(value * scale) / scale;
});

// Clamp value
engine.registerFunction("clamp", args -> {
    if (args.length < 3) return null;
    double value = ((Number) args[0]).doubleValue();
    double min = ((Number) args[1]).doubleValue();
    double max = ((Number) args[2]).doubleValue();
    
    return Math.max(min, Math.min(max, value));
});

// Percentage
engine.registerFunction("percentage", args -> {
    if (args.length < 2) return null;
    double part = ((Number) args[0]).doubleValue();
    double total = ((Number) args[1]).doubleValue();
    
    if (total == 0) return 0.0;
    return (part / total) * 100;
});
```

---

### Example 9: External API Integration

```java
import java.net.http.*;

// HTTP GET request
engine.registerFunction("httpGet", args -> {
    if (args.length == 0) return null;
    String url = args[0].toString();
    
    try {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();
        
        HttpResponse<String> response = client.send(
            request, 
            HttpResponse.BodyHandlers.ofString()
        );
        
        return response.body();
    } catch (Exception e) {
        return null;
    }
});
```

---

### Example 10: Database Integration

```java
import java.sql.*;

public class DatabaseFunctions {
    private final DataSource dataSource;
    
    public void register(MorphiumEngine engine) {
        // Query database
        engine.registerFunction("dbQuery", args -> {
            if (args.length == 0) return null;
            String sql = args[0].toString();
            
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                List<Map<String, Object>> results = new ArrayList<>();
                ResultSetMetaData meta = rs.getMetaData();
                
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        row.put(meta.getColumnName(i), rs.getObject(i));
                    }
                    results.add(row);
                }
                
                return results;
            } catch (SQLException e) {
                throw new RuntimeException("Query failed", e);
            }
        });
    }
}
```

---

### Example 11: Caching Function

```java
import java.util.concurrent.ConcurrentHashMap;

public class CachingFunctions {
    private final Map<String, Object> cache = new ConcurrentHashMap<>();
    
    public void register(MorphiumEngine engine) {
        engine.registerFunction("cacheGet", args -> {
            if (args.length == 0) return null;
            return cache.get(args[0].toString());
        });
        
        engine.registerFunction("cacheSet", args -> {
            if (args.length < 2) return null;
            String key = args[0].toString();
            Object value = args[1];
            cache.put(key, value);
            return value;
        });
        
        engine.registerFunction("cacheClear", args -> {
            if (args.length == 0) {
                cache.clear();
            } else {
                cache.remove(args[0].toString());
            }
            return null;
        });
    }
}
```

---

### Example 12: Cryptography Functions

```java
import javax.crypto.*;
import java.security.*;
import java.util.Base64;

// MD5 hash
engine.registerFunction("md5", args -> {
    if (args.length == 0) return null;
    try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(args[0].toString().getBytes());
        return Base64.getEncoder().encodeToString(hash);
    } catch (Exception e) {
        return null;
    }
});

// SHA256 hash
engine.registerFunction("sha256", args -> {
    if (args.length == 0) return null;
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(args[0].toString().getBytes());
        return Base64.getEncoder().encodeToString(hash);
    } catch (Exception e) {
        return null;
    }
});
```

---

## Function Bundles

### Example 13: Creating Function Bundle

```java
public class MathFunctionBundle {
    public static void register(MorphiumEngine engine) {
        engine.registerFunction("abs", args -> 
            Math.abs(((Number) args[0]).doubleValue())
        );
        
        engine.registerFunction("ceil", args -> 
            Math.ceil(((Number) args[0]).doubleValue())
        );
        
        engine.registerFunction("floor", args -> 
            Math.floor(((Number) args[0]).doubleValue())
        );
        
        engine.registerFunction("sqrt", args -> 
            Math.sqrt(((Number) args[0]).doubleValue())
        );
        
        engine.registerFunction("sin", args -> 
            Math.sin(((Number) args[0]).doubleValue())
        );
        
        engine.registerFunction("cos", args -> 
            Math.cos(((Number) args[0]).doubleValue())
        );
    }
}

// Usage
MorphiumEngine engine = new MorphiumEngine();
MathFunctionBundle.register(engine);
```

---

## Error Handling in Custom Functions

### Example 14: Safe Function with Error Handling

```java
engine.registerFunction("safeDivide", args -> {
    try {
        if (args.length < 2) {
            throw new IllegalArgumentException("safeDivide requires 2 arguments");
        }
        
        double a = ((Number) args[0]).doubleValue();
        double b = ((Number) args[1]).doubleValue();
        
        if (b == 0) {
            return args.length > 2 ? args[2] : null;  // Return default value
        }
        
        return a / b;
    } catch (Exception e) {
        System.err.println("Error in safeDivide: " + e.getMessage());
        return null;
    }
});
```

---

## Best Practices

### ✅ Do's

1. **Validate arguments** - Check count and types
2. **Handle nulls gracefully** - Return sensible defaults
3. **Document functions** - Add JavaDoc comments
4. **Use meaningful names** - Clear function names
5. **Make functions pure** - No side effects when possible
6. **Handle exceptions** - Don't let errors propagate
7. **Test thoroughly** - Unit test custom functions

### ❌ Don'ts

1. **Don't block threads** - Avoid long-running operations
2. **Don't leak resources** - Close connections/streams
3. **Don't ignore errors** - Handle exceptions properly
4. **Don't modify arguments** - Keep inputs immutable
5. **Don't use global state** - Keep functions isolated

---

## Complete Example

```java
public class CustomFunctionExample {
    public static void main(String[] args) {
        MorphiumEngine engine = new MorphiumEngine();
        
        // Register custom functions
        registerAllFunctions(engine);
        
        String script = """
            {
                id: uuid(),
                slug: slugify($.title),
                createdAt: currentDate(),
                emailValid: isValidEmail($.email),
                discountPrice: roundTo($.price * 0.9, 2)
            }
            """;
        
        String input = """
            {
                "title": "Hello World!",
                "email": "user@example.com",
                "price": 99.99
            }
            """;
        
        try {
            String result = engine.transform(script, input);
            System.out.println(result);
        } catch (MorphiumException e) {
            e.printStackTrace();
        }
    }
    
    private static void registerAllFunctions(MorphiumEngine engine) {
        // UUID
        engine.registerFunction("uuid", args -> 
            UUID.randomUUID().toString()
        );
        
        // Date
        engine.registerFunction("currentDate", args -> 
            LocalDate.now().toString()
        );
        
        // String manipulation
        engine.registerFunction("slugify", args -> 
            args[0].toString()
                .toLowerCase()
                .replaceAll("\\s+", "-")
                .replaceAll("[^a-z0-9-]", "")
        );
        
        // Validation
        engine.registerFunction("isValidEmail", args -> 
            args[0].toString().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        );
        
        // Math
        engine.registerFunction("roundTo", args -> {
            double value = ((Number) args[0]).doubleValue();
            int decimals = ((Number) args[1]).intValue();
            double scale = Math.pow(10, decimals);
            return Math.round(value * scale) / scale;
        });
    }
}
```

---

## Related Topics

- [Java API](19-java-api.md) - Morphium Java integration
- [User-Defined Functions](13-user-functions.md) - Functions in DSL
- [Custom Logger](21-custom-logger.md) - Custom logging
- [Error Handling](16-error-handling.md) - Error management

---

## Summary

Custom functions provide:
- ✅ Java integration capabilities
- ✅ External library access
- ✅ Business logic implementation
- ✅ Enhanced functionality
- ✅ Reusable utilities
- ✅ Type-safe operations

---

[← Back to Documentation](README.md)
