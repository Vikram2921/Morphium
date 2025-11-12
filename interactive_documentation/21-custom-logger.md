# Custom Logger

Learn how to implement and configure custom logging for Morphium DSL transformations.

---

## Overview

Custom loggers allow you to:
- **Integrate with existing logging frameworks** (SLF4J, Log4j, etc.)
- **Control log output** format and destination
- **Filter logs** by level and content
- **Monitor transformations** in production
- **Debug issues** with detailed logging

---

## Logger Interface

```java
package com.morphium.core;

public interface MorphiumLogger {
    void log(String level, String message);
    void debug(String message);
    void info(String message);
    void warn(String message);
    void error(String message);
}
```

---

## Basic Implementation

### Example 1: Simple Console Logger

```java
import com.morphium.core.MorphiumLogger;

public class ConsoleLogger implements MorphiumLogger {
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
engine.setLogger(new ConsoleLogger());
```

---

### Example 2: Timestamped Logger

```java
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampedLogger implements MorphiumLogger {
    private final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    private String timestamp() {
        return LocalDateTime.now().format(formatter);
    }
    
    @Override
    public void log(String level, String message) {
        System.out.println(timestamp() + " [" + level + "] " + message);
    }
    
    @Override
    public void debug(String message) {
        System.out.println(timestamp() + " [DEBUG] " + message);
    }
    
    @Override
    public void info(String message) {
        System.out.println(timestamp() + " [INFO] " + message);
    }
    
    @Override
    public void warn(String message) {
        System.err.println(timestamp() + " [WARN] " + message);
    }
    
    @Override
    public void error(String message) {
        System.err.println(timestamp() + " [ERROR] " + message);
    }
}
```

---

## Integration with Logging Frameworks

### Example 3: SLF4J Logger

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JLogger implements MorphiumLogger {
    private final Logger logger;
    
    public SLF4JLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }
    
    public SLF4JLogger(String name) {
        this.logger = LoggerFactory.getLogger(name);
    }
    
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

// Usage
engine.setLogger(new SLF4JLogger("morphium"));
```

---

### Example 4: Log4j2 Logger

```java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2Logger implements MorphiumLogger {
    private final Logger logger;
    
    public Log4j2Logger(Class<?> clazz) {
        this.logger = LogManager.getLogger(clazz);
    }
    
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

## Advanced Features

### Example 5: Filtered Logger

```java
public class FilteredLogger implements MorphiumLogger {
    private final MorphiumLogger delegate;
    private final Set<String> enabledLevels;
    
    public FilteredLogger(MorphiumLogger delegate, String... levels) {
        this.delegate = delegate;
        this.enabledLevels = new HashSet<>(Arrays.asList(levels));
    }
    
    @Override
    public void log(String level, String message) {
        if (enabledLevels.contains(level.toUpperCase())) {
            delegate.log(level, message);
        }
    }
    
    @Override
    public void debug(String message) {
        if (enabledLevels.contains("DEBUG")) {
            delegate.debug(message);
        }
    }
    
    @Override
    public void info(String message) {
        if (enabledLevels.contains("INFO")) {
            delegate.info(message);
        }
    }
    
    @Override
    public void warn(String message) {
        if (enabledLevels.contains("WARN")) {
            delegate.warn(message);
        }
    }
    
    @Override
    public void error(String message) {
        if (enabledLevels.contains("ERROR")) {
            delegate.error(message);
        }
    }
}

// Usage - only log WARN and ERROR
engine.setLogger(new FilteredLogger(
    new ConsoleLogger(), 
    "WARN", "ERROR"
));
```

---

### Example 6: File Logger

```java
import java.io.*;
import java.nio.file.*;

public class FileLogger implements MorphiumLogger {
    private final Path logFile;
    private final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    public FileLogger(String filename) {
        this.logFile = Paths.get(filename);
    }
    
    private synchronized void writeLog(String level, String message) {
        try {
            String timestamp = LocalDateTime.now().format(formatter);
            String logLine = timestamp + " [" + level + "] " + message + "\n";
            
            Files.write(
                logFile, 
                logLine.getBytes(), 
                StandardOpenOption.CREATE, 
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }
    
    @Override
    public void log(String level, String message) {
        writeLog(level, message);
    }
    
    @Override
    public void debug(String message) {
        writeLog("DEBUG", message);
    }
    
    @Override
    public void info(String message) {
        writeLog("INFO", message);
    }
    
    @Override
    public void warn(String message) {
        writeLog("WARN", message);
    }
    
    @Override
    public void error(String message) {
        writeLog("ERROR", message);
    }
}
```

---

### Example 7: Multi-Destination Logger

```java
public class MultiLogger implements MorphiumLogger {
    private final List<MorphiumLogger> loggers;
    
    public MultiLogger(MorphiumLogger... loggers) {
        this.loggers = Arrays.asList(loggers);
    }
    
    @Override
    public void log(String level, String message) {
        loggers.forEach(logger -> logger.log(level, message));
    }
    
    @Override
    public void debug(String message) {
        loggers.forEach(logger -> logger.debug(message));
    }
    
    @Override
    public void info(String message) {
        loggers.forEach(logger -> logger.info(message));
    }
    
    @Override
    public void warn(String message) {
        loggers.forEach(logger -> logger.warn(message));
    }
    
    @Override
    public void error(String message) {
        loggers.forEach(logger -> logger.error(message));
    }
}

// Usage - log to both console and file
engine.setLogger(new MultiLogger(
    new ConsoleLogger(),
    new FileLogger("morphium.log")
));
```

---

### Example 8: JSON Structured Logger

```java
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonLogger implements MorphiumLogger {
    private final ObjectMapper mapper = new ObjectMapper();
    
    private void logJson(String level, String message) {
        try {
            Map<String, Object> logEntry = Map.of(
                "timestamp", System.currentTimeMillis(),
                "level", level,
                "message", message,
                "application", "morphium"
            );
            
            String json = mapper.writeValueAsString(logEntry);
            System.out.println(json);
        } catch (Exception e) {
            System.err.println("Failed to create JSON log: " + e.getMessage());
        }
    }
    
    @Override
    public void log(String level, String message) {
        logJson(level, message);
    }
    
    @Override
    public void debug(String message) {
        logJson("DEBUG", message);
    }
    
    @Override
    public void info(String message) {
        logJson("INFO", message);
    }
    
    @Override
    public void warn(String message) {
        logJson("WARN", message);
    }
    
    @Override
    public void error(String message) {
        logJson("ERROR", message);
    }
}
```

---

### Example 9: Async Logger

```java
import java.util.concurrent.*;

public class AsyncLogger implements MorphiumLogger {
    private final MorphiumLogger delegate;
    private final ExecutorService executor;
    
    public AsyncLogger(MorphiumLogger delegate) {
        this.delegate = delegate;
        this.executor = Executors.newSingleThreadExecutor();
    }
    
    @Override
    public void log(String level, String message) {
        executor.submit(() -> delegate.log(level, message));
    }
    
    @Override
    public void debug(String message) {
        executor.submit(() -> delegate.debug(message));
    }
    
    @Override
    public void info(String message) {
        executor.submit(() -> delegate.info(message));
    }
    
    @Override
    public void warn(String message) {
        executor.submit(() -> delegate.warn(message));
    }
    
    @Override
    public void error(String message) {
        executor.submit(() -> delegate.error(message));
    }
    
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
```

---

### Example 10: Metrics Logger

```java
import java.util.concurrent.atomic.*;

public class MetricsLogger implements MorphiumLogger {
    private final MorphiumLogger delegate;
    private final AtomicLong debugCount = new AtomicLong();
    private final AtomicLong infoCount = new AtomicLong();
    private final AtomicLong warnCount = new AtomicLong();
    private final AtomicLong errorCount = new AtomicLong();
    
    public MetricsLogger(MorphiumLogger delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public void log(String level, String message) {
        delegate.log(level, message);
    }
    
    @Override
    public void debug(String message) {
        debugCount.incrementAndGet();
        delegate.debug(message);
    }
    
    @Override
    public void info(String message) {
        infoCount.incrementAndGet();
        delegate.info(message);
    }
    
    @Override
    public void warn(String message) {
        warnCount.incrementAndGet();
        delegate.warn(message);
    }
    
    @Override
    public void error(String message) {
        errorCount.incrementAndGet();
        delegate.error(message);
    }
    
    public Map<String, Long> getMetrics() {
        return Map.of(
            "debug", debugCount.get(),
            "info", infoCount.get(),
            "warn", warnCount.get(),
            "error", errorCount.get()
        );
    }
    
    public void resetMetrics() {
        debugCount.set(0);
        infoCount.set(0);
        warnCount.set(0);
        errorCount.set(0);
    }
}
```

---

## Spring Boot Integration

### Example 11: Spring Boot Logger

```java
@Component
public class SpringBootLogger implements MorphiumLogger {
    private static final Logger logger = 
        LoggerFactory.getLogger(SpringBootLogger.class);
    
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

@Configuration
public class MorphiumConfig {
    @Bean
    public MorphiumEngine morphiumEngine(SpringBootLogger logger) {
        MorphiumEngine engine = new MorphiumEngine();
        engine.setLogger(logger);
        return engine;
    }
}
```

---

## Best Practices

### ✅ Do's

1. **Use existing frameworks** - SLF4J, Log4j2, etc.
2. **Add timestamps** - For debugging and audit
3. **Structure logs** - JSON for machine processing
4. **Filter appropriately** - Control log volume
5. **Handle errors gracefully** - Logging shouldn't fail
6. **Make thread-safe** - Use synchronized or concurrent structures
7. **Consider performance** - Use async for high throughput

### ❌ Don'ts

1. **Don't log sensitive data** - Passwords, tokens, PII
2. **Don't block operations** - Use async logging
3. **Don't ignore errors** - Handle logging failures
4. **Don't log excessively** - Balance detail with performance
5. **Don't forget cleanup** - Close resources properly

---

## Complete Example

```java
public class CompleteLoggerExample {
    public static void main(String[] args) {
        // Create multi-destination logger
        MorphiumLogger logger = new MultiLogger(
            new TimestampedLogger(),
            new FileLogger("morphium.log"),
            new JsonLogger()
        );
        
        // Wrap with metrics
        MetricsLogger metricsLogger = new MetricsLogger(logger);
        
        // Create engine with logger
        MorphiumEngine engine = new MorphiumEngine();
        engine.setLogger(metricsLogger);
        
        // Run transformation
        String script = """
            {
                start: log("Starting transformation"),
                result: $.value * 2,
                end: logInfo("Transformation complete")
            }
            """;
        
        try {
            String result = engine.transform(script, "{\"value\": 42}");
            System.out.println("Result: " + result);
            
            // Print metrics
            System.out.println("Metrics: " + metricsLogger.getMetrics());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

## Related Topics

- [Logging](17-logging.md) - Using logging in scripts
- [Java API](19-java-api.md) - Morphium Java integration
- [Error Handling](16-error-handling.md) - Error management
- [Performance Tips](18-performance.md) - Logging performance

---

## Summary

Custom loggers provide:
- ✅ Framework integration (SLF4J, Log4j)
- ✅ Flexible output destinations
- ✅ Filtering and metrics
- ✅ Structured logging (JSON)
- ✅ Async capabilities
- ✅ Production-ready monitoring

---

[← Back to Documentation](README.md)
