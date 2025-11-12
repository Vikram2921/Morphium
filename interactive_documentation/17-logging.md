# Logging

Learn how to use logging functions in Morphium DSL for debugging, monitoring, and tracking transformation execution.

---

## Overview

Logging in Morphium DSL helps you:
- **Debug transformations** by inspecting values
- **Monitor execution** flow and performance
- **Track data** as it flows through transformations
- **Identify issues** in production
- **Audit operations** for compliance

---

## Available Logging Functions

```javascript
log(message)           // General logging
logInfo(message)       // Information messages
logWarn(message)       // Warning messages
logError(message)      // Error messages
logDebug(message)      // Debug messages
```

---

## Basic Examples

### Example 1: Simple Logging

```javascript
{
    start: log("Starting transformation"),
    data: $.value,
    doubled: $.value * 2,
    end: log("Transformation complete")
}
```

**Console Output:**
```
[LOG] Starting transformation
[LOG] Transformation complete
```

**JSON Output:**
```json
{
    "start": null,
    "data": 10,
    "doubled": 20,
    "end": null
}
```

---

### Example 2: Logging Different Levels

```javascript
{
    debug: logDebug("Debug: Processing input"),
    info: logInfo("Info: Transformation started"),
    warn: logWarn("Warn: Missing optional field"),
    error: logError("Error: Invalid data detected"),
    result: $.value
}
```

**Console Output:**
```
[DEBUG] Debug: Processing input
[INFO] Info: Transformation started
[WARN] Warn: Missing optional field
[ERROR] Error: Invalid data detected
```

---

### Example 3: Logging with Values

```javascript
{
    input: log("Input value: " + toString($.value)),
    processed: $.value * 2,
    output: log("Output value: " + toString($.value * 2))
}
```

**Console Output:**
```
[LOG] Input value: 10
[LOG] Output value: 20
```

---

## Debugging with Logging

### Example 4: Trace Execution Flow

```javascript
{
    step1: log("Step 1: Filtering active users"),
    activeUsers: filter($.users, "u", u.active),
    
    step2: log("Step 2: Found " + toString(len(filter($.users, "u", u.active))) + " active users"),
    
    step3: log("Step 3: Mapping to names"),
    names: map(filter($.users, "u", u.active), "u", u.name),
    
    step4: log("Step 4: Complete"),
    result: map(filter($.users, "u", u.active), "u", u.name)
}
```

**Console Output:**
```
[LOG] Step 1: Filtering active users
[LOG] Step 2: Found 3 active users
[LOG] Step 3: Mapping to names
[LOG] Step 4: Complete
```

---

### Example 5: Loop Debugging

```javascript
{
    items: for (item, index of $.items) {
        logDebug("Processing item " + toString(index) + ": " + item.name);
        
        if (item.price < 0) {
            logError("Invalid price for item: " + item.name);
            continue
        }
        
        {
            name: item.name,
            price: item.price,
            processed: log("Processed: " + item.name)
        }
    }
}
```

**Console Output:**
```
[DEBUG] Processing item 0: Widget A
[LOG] Processed: Widget A
[DEBUG] Processing item 1: Widget B
[ERROR] Invalid price for item: Widget B
[DEBUG] Processing item 2: Widget C
[LOG] Processed: Widget C
```

---

### Example 6: Conditional Logging

```javascript
{
    result: for (user of $.users) {
        if (!exists(user.email)) {
            logWarn("User " + toString(user.id) + " missing email");
        } else {
            logDebug("Processing user: " + user.email);
        }
        
        if (user.age < 18) {
            logInfo("Minor user detected: " + toString(user.id));
        }
        
        user
    }
}
```

---

## Advanced Logging Patterns

### Example 7: Performance Logging

```javascript
{
    start: logInfo("Starting transformation at: " + toString(now())),
    startTime: now(),
    
    // Do transformation
    result: map($.items, "i", {
        id: i.id,
        processed: true
    }),
    
    endTime: now(),
    duration: now() - startTime,
    
    end: logInfo("Completed in " + toString(now() - startTime) + "ms")
}
```

---

### Example 8: Data Validation Logging

```javascript
function validateAndLog(user) {
    variable errors = [];
    
    if (!exists(user.id)) {
        errors = concat(errors, ["Missing ID"]);
        logError("User missing ID: " + jsonStringify(user))
    }
    
    if (!exists(user.email)) {
        errors = concat(errors, ["Missing email"]);
        logWarn("User " + toString(user.id) + " missing email")
    }
    
    if (len(errors) == 0) {
        logInfo("User " + toString(user.id) + " validated successfully");
        user
    } else {
        logError("Validation failed for user " + toString(user.id) + ": " + join(errors, ", "));
        merge(user, {valid: false, errors: errors})
    }
}

{
    users: map($.users, "u", validateAndLog(u))
}
```

---

### Example 9: Pipeline Logging

```javascript
{
    stage1: log("=== Stage 1: Filter ==="),
    filtered: filter($.items, "i", i.active),
    filteredCount: log("Filtered to " + toString(len(filter($.items, "i", i.active))) + " items"),
    
    stage2: log("=== Stage 2: Transform ==="),
    transformed: map(filter($.items, "i", i.active), "i", {
        id: i.id,
        name: upper(i.name)
    }),
    transformedLog: log("Transformed " + toString(len(filter($.items, "i", i.active))) + " items"),
    
    stage3: log("=== Stage 3: Aggregate ==="),
    total: sum(map(filter($.items, "i", i.active), "i", i.price)),
    totalLog: log("Total: $" + toString(sum(map(filter($.items, "i", i.active), "i", i.price)))),
    
    complete: log("=== Pipeline Complete ===")
}
```

---

### Example 10: Error Context Logging

```javascript
function safeProcess(item, index) {
    logDebug("Processing item at index " + toString(index));
    
    if (!exists(item.id)) {
        logError("Item at index " + toString(index) + " missing ID: " + jsonStringify(item));
        error("Missing ID at index " + toString(index))
    }
    
    if (item.price < 0) {
        logError("Item " + toString(item.id) + " has negative price: " + toString(item.price));
        error("Invalid price for item " + toString(item.id))
    }
    
    logInfo("Successfully processed item " + toString(item.id));
    item
}

{
    results: for (item, index of $.items) {
        safeProcess(item, index)
    }
}
```

---

## Structured Logging

### Example 11: JSON Logging

```javascript
{
    start: log(jsonStringify({
        event: "transformation_start",
        timestamp: now(),
        itemCount: len($.items)
    })),
    
    result: map($.items, "i", i.name),
    
    end: log(jsonStringify({
        event: "transformation_complete",
        timestamp: now(),
        resultCount: len(map($.items, "i", i.name))
    }))
}
```

**Console Output:**
```
[LOG] {"event":"transformation_start","timestamp":1699804800000,"itemCount":5}
[LOG] {"event":"transformation_complete","timestamp":1699804801000,"resultCount":5}
```

---

### Example 12: Log Aggregation

```javascript
{
    summary: for (item of $.items) {
        variable logEntry = {
            id: item.id,
            name: item.name,
            status: if (item.active) { "active" } else { "inactive" },
            timestamp: now()
        };
        
        logInfo(jsonStringify(logEntry));
        
        item
    },
    
    complete: logInfo("Logged " + toString(len($.items)) + " items")
}
```

---

## Logging Best Practices

### Example 13: Function-Level Logging

```javascript
function processOrder(order) {
    logInfo("processOrder called with order ID: " + toString(order.id));
    
    variable subtotal = sum(map(order.items, "i", i.price * i.quantity));
    logDebug("Calculated subtotal: $" + toString(subtotal));
    
    variable tax = subtotal * 0.1;
    logDebug("Calculated tax: $" + toString(tax));
    
    variable total = subtotal + tax;
    logInfo("Order " + toString(order.id) + " total: $" + toString(total));
    
    {
        orderId: order.id,
        subtotal: subtotal,
        tax: tax,
        total: total
    }
}

{
    result: processOrder($.order)
}
```

---

### Example 14: Conditional Debug Logging

```javascript
variable DEBUG = true;

{
    result: for (item of $.items) {
        if (DEBUG) {
            logDebug("Item: " + jsonStringify(item));
        }
        
        variable processed = {
            id: item.id,
            value: item.value * 2
        };
        
        if (DEBUG) {
            logDebug("Processed: " + jsonStringify(processed));
        }
        
        processed
    }
}
```

---

### Example 15: Transaction Logging

```javascript
{
    transactionStart: logInfo("=== Transaction Start ==="),
    transactionId: log("Transaction ID: " + toString($.transactionId)),
    
    validation: logInfo("Validating transaction..."),
    validated: if ($.amount > 0) {
        logInfo("Validation passed")
    } else {
        logError("Validation failed: Invalid amount");
        error("Invalid amount")
    },
    
    processing: logInfo("Processing transaction..."),
    result: {
        id: $.transactionId,
        amount: $.amount,
        status: "completed"
    },
    
    completion: logInfo("Transaction completed successfully"),
    transactionEnd: logInfo("=== Transaction End ===")
}
```

---

## Use Cases

### Use Case 1: Data Import Monitoring

```javascript
{
    importStart: logInfo("Starting data import"),
    totalRecords: log("Total records: " + toString(len($.records))),
    
    imported: for (record, index of $.records) {
        if (index % 100 == 0) {
            logInfo("Processed " + toString(index) + " records");
        }
        
        if (!exists(record.id)) {
            logWarn("Record at index " + toString(index) + " missing ID");
        }
        
        record
    },
    
    importComplete: logInfo("Import complete. Processed " + toString(len($.records)) + " records")
}
```

---

### Use Case 2: API Request Logging

```javascript
{
    request: logInfo("API Request: " + jsonStringify({
        endpoint: $.endpoint,
        method: $.method,
        timestamp: now()
    })),
    
    processing: logDebug("Processing request..."),
    
    response: {
        status: 200,
        data: $.data
    },
    
    responseLog: logInfo("API Response: " + jsonStringify({
        status: 200,
        timestamp: now(),
        duration: "15ms"
    }))
}
```

---

### Use Case 3: Error Tracking

```javascript
{
    result: for (item of $.items) {
        if (item.price < 0) {
            logError("Error processing item: " + jsonStringify({
                itemId: item.id,
                error: "Negative price",
                value: item.price,
                timestamp: now()
            }));
            continue
        }
        
        if (!exists(item.name)) {
            logWarn("Warning: Item " + toString(item.id) + " missing name");
        }
        
        item
    }
}
```

---

## Performance Considerations

### Tips for Efficient Logging

1. **Use appropriate log levels** - DEBUG for development, INFO for production
2. **Avoid excessive logging in loops** - Log every Nth iteration
3. **Use structured logging** - JSON format for easy parsing
4. **Don't log sensitive data** - Mask passwords, tokens, etc.
5. **Consider log volume** - Balance detail with performance

---

### Example 16: Throttled Logging

```javascript
{
    items: for (item, index of $.items) {
        // Log every 10 items instead of every item
        if (index % 10 == 0) {
            logInfo("Progress: " + toString(index) + "/" + toString(len($.items)));
        }
        
        item
    },
    
    summary: logInfo("Completed processing " + toString(len($.items)) + " items")
}
```

---

## Custom Logger Configuration

```javascript
// From Java side, you can configure custom logger:
// MorphiumEngine.setLogger(new CustomLogger() {
//     @Override
//     public void log(String level, String message) {
//         // Custom logging implementation
//     }
// });
```

---

## Best Practices

### ✅ Do's

1. **Log at entry/exit points** - Function start and end
2. **Log important state changes** - Data transformations
3. **Include context** - IDs, timestamps, values
4. **Use structured logging** - JSON for machine parsing
5. **Log errors with stack traces** - Full context
6. **Use appropriate levels** - DEBUG, INFO, WARN, ERROR
7. **Make logs searchable** - Include keywords

### ❌ Don'ts

1. **Don't log sensitive data** - Passwords, tokens, PII
2. **Don't log in tight loops** - Performance impact
3. **Don't use logging for flow control** - It's for observation
4. **Don't log without context** - Include relevant information
5. **Don't forget to test logs** - Verify they work

---

## Integration with External Systems

### Example 17: Metrics Logging

```javascript
{
    metrics: log(jsonStringify({
        metric: "transformation.duration",
        value: 125,
        unit: "ms",
        tags: {
            transformationType: "userEnrichment",
            status: "success"
        },
        timestamp: now()
    })),
    
    result: $.data
}
```

---

## Related Topics

- [Error Handling](16-error-handling.md) - Handle and log errors
- [Performance Tips](18-performance.md) - Optimize with logging
- [Custom Logger](21-custom-logger.md) - Implement custom logger
- [Java API](19-java-api.md) - Configure logging from Java

---

## Summary

Logging in Morphium DSL provides:
- ✅ Multiple log levels (DEBUG, INFO, WARN, ERROR)
- ✅ Execution flow tracking
- ✅ Performance monitoring
- ✅ Error debugging
- ✅ Structured logging support
- ✅ Custom logger integration
- ✅ Production-ready monitoring

---

[← Back to Documentation](README.md)
