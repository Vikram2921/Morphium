# Error Handling

Learn how to handle errors effectively in Morphium DSL transformations with custom errors, try-catch patterns, and error recovery.

---

## Overview

Error handling in Morphium DSL allows you to:
- **Throw custom errors** with meaningful messages
- **Validate data** and fail gracefully
- **Recover from errors** with fallback values
- **Debug issues** with detailed error information
- **Build robust transformations** that handle edge cases

---

## Throwing Errors

### Syntax

```javascript
error("Error message")
error("Error message", errorCode)
```

---

## Basic Examples

### Example 1: Simple Error

```javascript
{
    result: if ($.value < 0) {
        error("Value must be non-negative")
    } else {
        $.value * 2
    }
}
```

**Input (valid):**
```json
{"value": 10}
```

**Output:**
```json
{"result": 20}
```

**Input (invalid):**
```json
{"value": -5}
```

**Error:**
```
MorphiumException: Value must be non-negative
```

---

### Example 2: Error with Context

```javascript
{
    result: if (!exists($.email)) {
        error("Email is required for user ID: " + toString($.id))
    } else {
        $.email
    }
}
```

**Input (invalid):**
```json
{"id": 123}
```

**Error:**
```
MorphiumException: Email is required for user ID: 123
```

---

### Example 3: Conditional Validation

```javascript
function validateAge(age) {
    if (age < 0) {
        error("Age cannot be negative")
    } else if (age > 150) {
        error("Age seems unrealistic: " + toString(age))
    } else {
        age
    }
}

{
    age: validateAge($.age)
}
```

---

## Validation Patterns

### Example 4: Required Field Validation

```javascript
function required(value, fieldName) {
    if (!exists(value) || value == "") {
        error(fieldName + " is required")
    } else {
        value
    }
}

{
    id: required($.id, "ID"),
    name: required($.name, "Name"),
    email: required($.email, "Email")
}
```

---

### Example 5: Type Validation

```javascript
function validateNumber(value, fieldName) {
    variable num = toNumber(value);
    if (num == null) {
        error(fieldName + " must be a valid number, got: " + toString(value))
    } else {
        num
    }
}

{
    price: validateNumber($.price, "Price"),
    quantity: validateNumber($.quantity, "Quantity")
}
```

---

### Example 6: Range Validation

```javascript
function validateRange(value, min, max, fieldName) {
    if (value < min) {
        error(fieldName + " must be at least " + toString(min))
    } else if (value > max) {
        error(fieldName + " must be at most " + toString(max))
    } else {
        value
    }
}

{
    rating: validateRange($.rating, 1, 5, "Rating"),
    percentage: validateRange($.percentage, 0, 100, "Percentage")
}
```

---

### Example 7: Format Validation

```javascript
function validateEmail(email) {
    if (!exists(email)) {
        error("Email is required")
    } else if (len(email) < 5) {
        error("Email is too short")
    } else if (!exists(split(email, "@")[1])) {
        error("Email must contain @ symbol")
    } else {
        email
    }
}

{
    email: validateEmail($.email)
}
```

---

### Example 8: Comprehensive Object Validation

```javascript
function validateUser(user) {
    variable errors = [];
    
    // Collect all validation errors
    if (!exists(user.id)) {
        errors = concat(errors, ["ID is required"])
    }
    
    if (!exists(user.name) || len(user.name) < 2) {
        errors = concat(errors, ["Name must be at least 2 characters"])
    }
    
    if (!exists(user.email) || !exists(split(user.email, "@")[1])) {
        errors = concat(errors, ["Valid email is required"])
    }
    
    if (user.age < 18) {
        errors = concat(errors, ["User must be 18 or older"])
    }
    
    // Throw if any errors
    if (len(errors) > 0) {
        error("Validation failed: " + join(errors, "; "))
    } else {
        user
    }
}

{
    validatedUser: validateUser($.user)
}
```

---

## Error Recovery Patterns

### Example 9: Fallback Values

```javascript
function safeGet(obj, path, defaultValue) {
    variable value = get(obj, path);
    if (!exists(value)) {
        defaultValue
    } else {
        value
    }
}

{
    name: safeGet($, "user.name", "Unknown"),
    age: safeGet($, "user.age", 0),
    country: safeGet($, "user.address.country", "US")
}
```

---

### Example 10: Try-Catch Pattern

```javascript
function tryParse(jsonString, fallback) {
    variable parsed = jsonParse(jsonString);
    if (!exists(parsed)) {
        fallback
    } else {
        parsed
    }
}

{
    config: tryParse($.configJson, {default: true})
}
```

---

### Example 11: Safe Division

```javascript
function safeDivide(a, b, defaultValue) {
    if (b == 0) {
        defaultValue
    } else {
        a / b
    }
}

{
    result: safeDivide($.numerator, $.denominator, 0),
    percentage: safeDivide($.part, $.total, 0) * 100
}
```

---

### Example 12: Graceful Degradation

```javascript
function enrichUser(user) {
    {
        id: if (exists(user.id)) { user.id } else { -1 },
        name: if (exists(user.name)) { user.name } else { "Unknown" },
        email: if (exists(user.email)) { lower(user.email) } else { null },
        age: if (exists(user.age) && user.age > 0) { user.age } else { null },
        status: if (exists(user.active)) { 
            if (user.active) { "active" } else { "inactive" }
        } else {
            "unknown"
        }
    }
}

{
    users: map($.users, "u", enrichUser(u))
}
```

---

## Advanced Error Handling

### Example 13: Error Accumulation

```javascript
function validateWithErrors(data) {
    variable errors = [];
    variable warnings = [];
    
    // Critical errors
    if (!exists(data.id)) {
        errors = concat(errors, [{field: "id", message: "Required"}])
    }
    
    if (!exists(data.email)) {
        errors = concat(errors, [{field: "email", message: "Required"}])
    }
    
    // Warnings
    if (!exists(data.phone)) {
        warnings = concat(warnings, [{field: "phone", message: "Recommended"}])
    }
    
    {
        data: data,
        valid: len(errors) == 0,
        errors: errors,
        warnings: warnings
    }
}

{
    validation: validateWithErrors($.input)
}
```

---

### Example 14: Conditional Error Throwing

```javascript
function processOrder(order) {
    // Validate stock
    variable outOfStock = filter(order.items, "i", i.quantity > i.available);
    
    if (len(outOfStock) > 0) {
        variable itemNames = join(map(outOfStock, "i", i.name), ", ");
        error("Items out of stock: " + itemNames)
    }
    
    // Validate total
    variable total = sum(map(order.items, "i", i.price * i.quantity));
    
    if (total > order.creditLimit) {
        error("Order total $" + toString(total) + " exceeds credit limit $" + toString(order.creditLimit))
    }
    
    {
        orderId: order.id,
        total: total,
        status: "approved"
    }
}

{
    result: processOrder($.order)
}
```

---

### Example 15: Business Rule Validation

```javascript
function validateBusinessRules(transaction) {
    // Rule 1: Transaction amount limits
    if (transaction.amount > 10000 && !transaction.approved) {
        error("Transactions over $10,000 require approval")
    }
    
    // Rule 2: Working hours
    variable hour = toNumber(slice(formatDate(now(), "HH"), 0, 2));
    if (hour < 9 || hour > 17) {
        error("Transactions only allowed during business hours (9 AM - 5 PM)")
    }
    
    // Rule 3: Account status
    if (transaction.account.status != "active") {
        error("Account must be active. Current status: " + transaction.account.status)
    }
    
    // Rule 4: Sufficient balance
    if (transaction.amount > transaction.account.balance) {
        error("Insufficient funds. Balance: $" + toString(transaction.account.balance) + 
              ", Required: $" + toString(transaction.amount))
    }
    
    transaction
}

{
    validatedTransaction: validateBusinessRules($.transaction)
}
```

---

## Error Context and Debugging

### Example 16: Error with Full Context

```javascript
function processWithContext(item, index) {
    if (item.price < 0) {
        error("Invalid price at index " + toString(index) + 
              ": " + toString(item.price) + 
              " for item " + item.name)
    } else {
        item
    }
}

{
    results: for (item, index of $.items) {
        processWithContext(item, index)
    }
}
```

---

### Example 17: Detailed Validation Report

```javascript
function createValidationReport(data) {
    variable validations = [
        {
            field: "id",
            valid: exists(data.id),
            message: if (!exists(data.id)) { "ID is required" } else { "OK" }
        },
        {
            field: "email",
            valid: exists(data.email) && exists(split(data.email, "@")[1]),
            message: if (!exists(data.email)) { "Email is required" } 
                    else if (!exists(split(data.email, "@")[1])) { "Invalid email format" }
                    else { "OK" }
        },
        {
            field: "age",
            valid: exists(data.age) && data.age >= 18,
            message: if (!exists(data.age)) { "Age is required" }
                    else if (data.age < 18) { "Must be 18 or older" }
                    else { "OK" }
        }
    ];
    
    variable failed = filter(validations, "v", !v.valid);
    
    {
        valid: len(failed) == 0,
        validations: validations,
        errors: map(failed, "f", f.message)
    }
}

{
    report: createValidationReport($.data),
    shouldProceed: createValidationReport($.data).valid
}
```

---

## Practical Use Cases

### Use Case 1: API Input Validation

```javascript
function validateApiRequest(request) {
    // Validate required fields
    if (!exists(request.apiKey)) {
        error("API key is required")
    }
    
    if (!exists(request.endpoint)) {
        error("Endpoint is required")
    }
    
    // Validate API key format
    if (len(request.apiKey) != 32) {
        error("Invalid API key format")
    }
    
    // Validate rate limit
    if (exists(request.rateLimitRemaining) && request.rateLimitRemaining <= 0) {
        error("Rate limit exceeded. Please try again later.")
    }
    
    request
}

{
    validatedRequest: validateApiRequest($.request)
}
```

---

### Use Case 2: Data Import Validation

```javascript
function validateImportRow(row, rowNumber) {
    variable errors = [];
    
    if (!exists(row.id)) {
        errors = concat(errors, ["Row " + toString(rowNumber) + ": Missing ID"])
    }
    
    if (!exists(row.name)) {
        errors = concat(errors, ["Row " + toString(rowNumber) + ": Missing name"])
    }
    
    if (exists(row.price) && toNumber(row.price) == null) {
        errors = concat(errors, ["Row " + toString(rowNumber) + ": Invalid price"])
    }
    
    if (len(errors) > 0) {
        error(join(errors, "; "))
    } else {
        row
    }
}

{
    imported: for (row, index of $.rows) {
        validateImportRow(row, index + 1)
    }
}
```

---

### Use Case 3: Configuration Validation

```javascript
function validateConfig(config) {
    // Validate required sections
    if (!exists(config.database)) {
        error("Database configuration is required")
    }
    
    if (!exists(config.database.host)) {
        error("Database host is required")
    }
    
    if (!exists(config.database.port)) {
        error("Database port is required")
    }
    
    // Validate port range
    if (config.database.port < 1 || config.database.port > 65535) {
        error("Database port must be between 1 and 65535")
    }
    
    // Validate optional settings
    if (exists(config.cache) && exists(config.cache.ttl) && config.cache.ttl < 0) {
        error("Cache TTL cannot be negative")
    }
    
    config
}

{
    config: validateConfig($.config)
}
```

---

## Best Practices

### ✅ Do's

1. **Provide clear error messages** - Include what went wrong and why
2. **Add context to errors** - Include field names, values, and indices
3. **Validate early** - Fail fast with clear feedback
4. **Use specific error messages** - Avoid generic "Invalid input"
5. **Include recovery hints** - Suggest how to fix the error
6. **Log errors appropriately** - Use logging for debugging
7. **Test error cases** - Write tests for validation logic

### ❌ Don'ts

1. **Don't swallow errors silently** - Always handle or propagate
2. **Don't expose sensitive data** - Be careful with error messages
3. **Don't use errors for control flow** - Use conditional logic instead
4. **Don't create overly generic messages** - Be specific
5. **Don't forget to validate inputs** - Always validate external data

---

## Error Codes and Classification

```javascript
function classifiedError(message, type, code) {
    error(type + " [" + toString(code) + "]: " + message)
}

// Usage examples
{
    validation: if ($.age < 0) {
        classifiedError("Age cannot be negative", "VALIDATION_ERROR", 1001)
    } else { $.age },
    
    business: if ($.balance < $.amount) {
        classifiedError("Insufficient funds", "BUSINESS_ERROR", 2001)
    } else { $.amount },
    
    system: if (!exists($.config)) {
        classifiedError("Configuration not found", "SYSTEM_ERROR", 3001)
    } else { $.config }
}
```

---

## Related Topics

- [Logging](17-logging.md) - Log errors and debug information
- [User-Defined Functions](13-user-functions.md) - Create validation functions
- [Data Types](05-data-types.md) - Understand type checking
- [Operators](06-operators.md) - Use operators for validation

---

## Summary

Error handling in Morphium DSL provides:
- ✅ Custom error messages with context
- ✅ Data validation and business rules
- ✅ Error recovery patterns
- ✅ Graceful degradation
- ✅ Detailed validation reports
- ✅ Debugging support

---

[← Back to Documentation](README.md)
