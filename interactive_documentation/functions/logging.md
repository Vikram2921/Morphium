# Logging Functions

Comprehensive guide to logging capabilities in Morphium DSL.

---

## Overview

Morphium DSL provides five logging functions for different severity levels:
- `log()` - General logging (INFO level)
- `logInfo()` - Informational messages
- `logWarn()` - Warning messages
- `logError()` - Error messages
- `logDebug()` - Debug messages

---

## log() - General Logging

### Syntax

```javascript
log(message1, message2, ...)
```

### Parameters
- **messages**: One or more values to log (automatically converted to strings)

### Returns
`null` (always returns null, used for side effects)

### Description
Logs messages at INFO level. Multiple arguments are concatenated with spaces.

---

## logInfo() - Information Level

### Syntax

```javascript
logInfo(message1, message2, ...)
```

### Parameters
- **messages**: One or more values to log

### Returns
`null`

### Description
Explicitly logs at INFO level for informational messages.

---

## logWarn() - Warning Level

### Syntax

```javascript
logWarn(message1, message2, ...)
```

### Parameters
- **messages**: One or more values to log

### Returns
`null`

### Description
Logs warning messages that indicate potential issues.

---

## logError() - Error Level

### Syntax

```javascript
logError(message1, message2, ...)
```

### Parameters
- **messages**: One or more values to log

### Returns
`null`

### Description
Logs error messages for serious problems.

---

## logDebug() - Debug Level

### Syntax

```javascript
logDebug(message1, message2, ...)
```

### Parameters
- **messages**: One or more values to log

### Returns
`null`

### Description
Logs debug messages for detailed diagnostic information.

---

## Basic Examples

### Example 1: Simple Logging

```javascript
// Input
{
  "count": 5
}

// Script
let data = { count: $.count };

log("Processing data with count:", data.count);
logInfo("This is an info message");
logWarn("This is a warning");
logError("This is an error message");
logDebug("Debug info: count =", data.count);

{ result: data.count * 2 }

// Console Output:
// [INFO] Processing data with count: 5
// [INFO] This is an info message
// [WARN] This is a warning
// [ERROR] This is an error message
// [DEBUG] Debug info: count = 5
```

### Example 2: Logging in Transformations

```javascript
// Input
{
  "users": [
    {"name": "Alice", "age": 30},
    {"name": "Bob", "age": 25}
  ]
}

// Script
log("Starting user transformation");

{
  users: for (user of $.users) {
    logInfo("Processing user:", user.name);
    {
      name: user.name,
      age: user.age,
      category: user.age >= 30 ? "senior" : "junior"
    }
  }
}

// Console Output:
// [INFO] Starting user transformation
// [INFO] Processing user: Alice
// [INFO] Processing user: Bob
```

### Example 3: Multi-Argument Logging

```javascript
// Input
{
  "order": {
    "id": "ORD-123",
    "total": 150.75
  }
}

// Script
let order = $.order;

log("Order ID:", order.id, "Total:", order.total);

{ processed: true }

// Console Output:
// [INFO] Order ID: ORD-123 Total: 150.75
```

---

## Advanced Examples

### Example 1: Debug Tracing

```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5]
}

// Script
logDebug("Input array length:", len($.numbers));

let doubled = map($.numbers, "n", {
  logDebug("Processing number:", n);
  n * 2
});

logDebug("Output array length:", len(doubled));

{ result: doubled }

// Console Output:
// [DEBUG] Input array length: 5
// [DEBUG] Processing number: 1
// [DEBUG] Processing number: 2
// [DEBUG] Processing number: 3
// [DEBUG] Processing number: 4
// [DEBUG] Processing number: 5
// [DEBUG] Output array length: 5
```

### Example 2: Warning on Edge Cases

```javascript
// Input
{
  "items": [
    {"name": "Widget", "stock": 5},
    {"name": "Gadget", "stock": 2},
    {"name": "Tool", "stock": 50}
  ]
}

// Script
{
  items: for (item of $.items) {
    if (item.stock < 5) {
      logWarn("Low stock alert for:", item.name, "- Only", item.stock, "remaining");
      null
    } else {
      logInfo("Stock OK for:", item.name);
      null
    };
    
    {
      name: item.name,
      stock: item.stock,
      status: item.stock < 5 ? "low" : "ok"
    }
  }
}

// Console Output:
// [INFO] Stock OK for: Widget
// [WARN] Low stock alert for: Gadget - Only 2 remaining
// [INFO] Stock OK for: Tool
```

### Example 3: Error Logging with Validation

```javascript
// Input
{
  "transactions": [
    {"id": 1, "amount": 100},
    {"id": 2, "amount": -50},
    {"id": 3, "amount": 75}
  ]
}

// Script
{
  valid: for (txn of $.transactions) {
    if (txn.amount < 0) {
      logError("Invalid transaction detected - ID:", txn.id, "Amount:", txn.amount);
      continue
    } else {
      logInfo("Processing valid transaction:", txn.id);
      null
    };
    
    {
      id: txn.id,
      amount: txn.amount
    }
  }
}

// Console Output:
// [INFO] Processing valid transaction: 1
// [ERROR] Invalid transaction detected - ID: 2 Amount: -50
// [INFO] Processing valid transaction: 3
```

### Example 4: Performance Logging

```javascript
// Input
{
  "data": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
}

// Script
log("Starting processing at:", now());

let filtered = filter($.data, "x", {
  logDebug("Filtering value:", x);
  x % 2 == 0
});

log("Filtered count:", len(filtered));

let result = map(filtered, "x", {
  logDebug("Transforming value:", x);
  x * 2
});

log("Completed processing at:", now());

{ result: result }
```

---

## Integration with Error Handling

### Pattern: Log Before Error

```javascript
let age = $.age;

if (age < 0) {
  logError("Validation failed: negative age detected:", age);
  error("Age cannot be negative");
} else if (age > 150) {
  logError("Validation failed: unrealistic age:", age);
  error("Age seems invalid");
} else {
  logInfo("Age validation passed:", age);
  null
};

{ validAge: age }
```

### Pattern: Log with Validation Results

```javascript
let email = $.email;

let isValid = email != null && len(email) > 0 && contains(email, "@");

if (isValid) {
  logInfo("Email validation passed:", email);
  null
} else {
  logError("Email validation failed:", email);
  error("Invalid email format");
};

{ email: email }
```

---

## Custom Logger Implementation (Java)

### Setting Up Custom Logger

```java
import com.morphium.core.MorphiumEngine;
import com.morphium.runtime.Logger;

MorphiumEngine engine = new MorphiumEngine();

// Set up custom logger
engine.setLogger(new Logger() {
    @Override
    public void log(Level level, String message) {
        System.out.println("[" + level + "] " + message);
        // Or use your logging framework:
        // log4j.log(level, message);
        // slf4j.log(level, message);
    }
});
```

### Integration with Log4j

```java
engine.setLogger(new Logger() {
    private static final org.apache.log4j.Logger logger = 
        org.apache.log4j.Logger.getLogger(MorphiumEngine.class);
    
    @Override
    public void log(Level level, String message) {
        switch (level) {
            case DEBUG: logger.debug(message); break;
            case INFO: logger.info(message); break;
            case WARN: logger.warn(message); break;
            case ERROR: logger.error(message); break;
        }
    }
});
```

### Integration with SLF4J

```java
engine.setLogger(new Logger() {
    private static final org.slf4j.Logger logger = 
        org.slf4j.LoggerFactory.getLogger(MorphiumEngine.class);
    
    @Override
    public void log(Level level, String message) {
        switch (level) {
            case DEBUG: logger.debug(message); break;
            case INFO: logger.info(message); break;
            case WARN: logger.warn(message); break;
            case ERROR: logger.error(message); break;
        }
    }
});
```

---

## Common Patterns

### Pattern 1: Entry/Exit Logging

```javascript
fn processOrder(order) {
  logInfo("Processing order:", order.id);
  
  let result = {
    orderId: order.id,
    total: sum(map(order.items, "i", i.price))
  };
  
  logInfo("Completed order:", order.id, "Total:", result.total);
  result
}
```

### Pattern 2: Conditional Logging

```javascript
global debugMode = true;

for (item of $.items) {
  debugMode ? logDebug("Processing item:", item.id) : null;
  
  {
    id: item.id,
    processed: true
  }
}
```

### Pattern 3: Audit Trail

```javascript
log("Transformation started by:", $.user.name);
log("Input record count:", len($.records));

let result = map($.records, "r", {
  logInfo("Transforming record:", r.id);
  transform(r)
});

log("Output record count:", len(result));
log("Transformation completed");
```

### Pattern 4: Error Context

```javascript
for (record of $.records) {
  if (!isValid(record)) {
    logError("Validation failed for record:", jsonStringify(record));
    logError("Expected format: {id: number, name: string}");
    continue
  } else {
    null
  };
  
  processRecord(record)
}
```

---

## Best Practices

### ✅ Do:
- Use appropriate log levels (DEBUG < INFO < WARN < ERROR)
- Include context in log messages (IDs, counts, values)
- Log at key transformation points
- Use DEBUG for detailed diagnostics
- Log errors before throwing exceptions
- Use INFO for normal operation tracking

### ❌ Don't:
- Don't log sensitive data (passwords, tokens, etc.)
- Don't log excessively in tight loops (affects performance)
- Don't use ERROR for non-error situations
- Don't log entire large objects (use IDs instead)
- Don't forget logs have performance cost
- Don't rely on logs for control flow

---

## Log Levels Guide

| Level | When to Use | Examples |
|-------|-------------|----------|
| **DEBUG** | Detailed diagnostic info | Variable values, loop iterations |
| **INFO** | Normal operation events | Start/end of processes, counts |
| **WARN** | Potentially harmful situations | Low stock, missing optional fields |
| **ERROR** | Error events | Validation failures, exceptions |

---

## Performance Considerations

1. **Avoid in Tight Loops**: Logging in high-frequency loops can impact performance
2. **Conditional Logging**: Use debug flags to enable/disable verbose logging
3. **Lazy Evaluation**: Log messages are only formatted when logger is active
4. **String Concatenation**: Multiple arguments are more efficient than manual concatenation

### Good Performance

```javascript
// Efficient - arguments concatenated by logger
log("User:", user.name, "Age:", user.age);
```

### Poor Performance

```javascript
// Inefficient - string concatenation before logging
log("User: " + user.name + " Age: " + toString(user.age));
```

---

## Tips

1. **Structured**: Keep log messages consistent and searchable
2. **Context**: Always include relevant IDs or identifiers
3. **Levels**: Use appropriate levels for filtering
4. **Format**: Use consistent format for similar messages
5. **Testing**: Enable DEBUG during development, INFO in production

---

## Related Topics

- [error() Function](error.md) - Throwing errors
- [Custom Logger](../21-custom-logger.md) - Java logger integration
- [Logging Guide](../17-logging.md) - Complete logging guide
- [Debugging](../18-performance.md) - Performance and debugging

---

**See Also:**
- [Quick Start](../01-quick-start.md)
- [Error Handling](../16-error-handling.md)
- [Java API](../19-java-api.md)
