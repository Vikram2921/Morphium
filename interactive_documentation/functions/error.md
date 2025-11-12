# error() - Throw Custom Errors

Throw custom errors with descriptive messages in Morphium DSL.

---

## Syntax

```javascript
error(message)
```

### Parameters
- **message**: String message describing the error

### Returns
Never returns - throws an exception

---

## Description

The `error()` function throws a custom exception with the provided message, immediately terminating script execution. This is useful for validation, business rule enforcement, and error handling.

---

## Basic Examples

### Example 1: Simple Validation

```javascript
// Input
{
  "age": -5
}

// Script
let age = $.age;

age < 0 ? error("Age cannot be negative") : null;
age > 150 ? error("Age seems invalid") : null;

{ validAge: age }

// Throws: RuntimeException: Age cannot be negative
```

### Example 2: Required Field Validation

```javascript
// Input
{
  "user": {
    "name": "",
    "email": null
  }
}

// Script
let user = $.user;

user.name == null || len(user.name) == 0 
  ? error("Name is required") 
  : null;

user.email == null 
  ? error("Email is required") 
  : null;

{ user: user }

// Throws: RuntimeException: Name is required
```

### Example 3: Value Range Check

```javascript
// Input
{
  "temperature": 150
}

// Script
let temp = $.temperature;

temp < -50 || temp > 100 
  ? error("Temperature out of valid range (-50 to 100)")
  : null;

{ temperature: temp }

// Throws: RuntimeException: Temperature out of valid range (-50 to 100)
```

---

## Advanced Examples

### Example 1: Business Rule Validation

```javascript
// Input
{
  "order": {
    "items": [],
    "total": 0,
    "customerId": null
  }
}

// Script
let order = $.order;

// Validate customer
order.customerId == null 
  ? error("Order must have a customer ID") 
  : null;

// Validate items
len(order.items) == 0 
  ? error("Order must contain at least one item") 
  : null;

// Validate total
order.total <= 0 
  ? error("Order total must be greater than zero") 
  : null;

{ validOrder: order }

// Throws: RuntimeException: Order must have a customer ID
```

### Example 2: Conditional Validation

```javascript
// Input
{
  "payment": {
    "method": "credit_card",
    "cardNumber": null,
    "amount": 100
  }
}

// Script
let payment = $.payment;

// Validate based on payment method
if (payment.method == "credit_card") {
  payment.cardNumber == null || len(payment.cardNumber) == 0
    ? error("Credit card number is required for credit card payments")
    : null;
  
  len(payment.cardNumber) != 16
    ? error("Credit card number must be 16 digits")
    : null
} else if (payment.method == "bank_transfer") {
  payment.accountNumber == null
    ? error("Account number is required for bank transfers")
    : null
} else {
  null
};

{ validPayment: payment }
```

### Example 3: Data Integrity Checks

```javascript
// Input
{
  "transaction": {
    "type": "transfer",
    "from": "ACC-001",
    "to": "ACC-001",
    "amount": -50
  }
}

// Script
let txn = $.transaction;

// Amount validation
txn.amount <= 0 
  ? error("Transaction amount must be positive") 
  : null;

// Same account validation
txn.type == "transfer" && txn.from == txn.to
  ? error("Cannot transfer to the same account")
  : null;

// Account validation
txn.from == null || len(txn.from) == 0
  ? error("Source account is required")
  : null;

txn.to == null || len(txn.to) == 0
  ? error("Destination account is required")
  : null;

{ validTransaction: txn }

// Throws: RuntimeException: Cannot transfer to the same account
```

### Example 4: Array Validation in Loops

```javascript
// Input
{
  "users": [
    {"username": "alice", "email": "alice@example.com"},
    {"username": "bob", "email": null},
    {"username": "", "email": "charlie@example.com"}
  ]
}

// Script
{
  validated: for (user of $.users) {
    // Validate username
    user.username == null || len(user.username) == 0
      ? error("Username cannot be empty for user: " + jsonStringify(user))
      : null;
    
    // Validate email
    user.email == null || len(user.email) == 0
      ? error("Email cannot be empty for user: " + user.username)
      : null;
    
    user
  }
}

// Throws: RuntimeException: Email cannot be empty for user: bob
```

---

## Patterns and Use Cases

### Pattern 1: Guard Clauses

```javascript
let value = $.value;

// Early return with validation
value == null ? error("Value is required") : null;
value < 0 ? error("Value must be non-negative") : null;
value > 1000 ? error("Value exceeds maximum limit") : null;

{ processedValue: value * 2 }
```

### Pattern 2: Type Validation

```javascript
let data = $.data;

toNumber(data) == null 
  ? error("Data must be a valid number") 
  : null;

{ numericData: toNumber(data) }
```

### Pattern 3: Complex Conditional Errors

```javascript
let user = $.user;

switch (user.status) {
  case "active": null;
  case "pending": null;
  case "suspended": error("Cannot process suspended user: " + user.id);
  case "deleted": error("Cannot process deleted user: " + user.id);
  default: error("Unknown user status: " + user.status);
};

{ user: user }
```

### Pattern 4: Calculated Validation

```javascript
let items = $.items;
let total = sum(map(items, "item", item.price * item.quantity));
let declaredTotal = $.declaredTotal;

abs(total - declaredTotal) > 0.01
  ? error("Calculated total (" + toString(total) + ") does not match declared total (" + toString(declaredTotal) + ")")
  : null;

{ validTotal: total }
```

---

## Error Messages Best Practices

### ✅ Good Error Messages

```javascript
// Descriptive and actionable
error("Email format is invalid. Expected format: user@domain.com")

// Include context
error("Product ID " + productId + " not found in inventory")

// Suggest fix
error("Price must be between 0 and 10000. Received: " + price)

// Include relevant data
error("Duplicate order ID: " + orderId + " already exists")
```

### ❌ Poor Error Messages

```javascript
// Too vague
error("Invalid")

// No context
error("Error")

// Not helpful
error("Something went wrong")

// Missing details
error("Bad value")
```

---

## Combining with Logging

```javascript
// Log before error for debugging
let amount = $.amount;

if (amount < 0) {
  logError("Negative amount detected: " + toString(amount));
  error("Amount cannot be negative");
} else {
  null
};

{ amount: amount }
```

---

## Try-Catch Pattern (Java Side)

While Morphium DSL doesn't have try-catch, you can catch errors on the Java side:

```java
try {
    JsonNode result = engine.transformFromString(script, input);
    System.out.println("Success: " + result);
} catch (MorphiumException e) {
    System.err.println("Validation failed: " + e.getMessage());
    // Handle the error appropriately
}
```

---

## Common Use Cases

### 1. Input Validation

```javascript
let email = $.email;
!contains(email, "@") ? error("Invalid email format") : null;
```

### 2. Business Rules

```javascript
let age = $.age;
let hasParentalConsent = $.hasParentalConsent;

age < 18 && !hasParentalConsent 
  ? error("Minors require parental consent") 
  : null;
```

### 3. Data Integrity

```javascript
let startDate = $.startDate;
let endDate = $.endDate;

endDate < startDate 
  ? error("End date cannot be before start date") 
  : null;
```

### 4. Authorization

```javascript
let userRole = $.user.role;
let action = $.action;

action == "delete" && userRole != "admin"
  ? error("Only administrators can delete records")
  : null;
```

### 5. Resource Limits

```javascript
let fileSize = $.fileSize;
let maxSize = 10485760; // 10MB

fileSize > maxSize 
  ? error("File size exceeds maximum limit of 10MB") 
  : null;
```

---

## Performance Considerations

1. **Early Validation**: Validate early to avoid unnecessary processing
2. **Specific Checks**: Be specific to avoid false positives
3. **Expensive Checks Last**: Order validations from cheap to expensive
4. **Batch Validation**: Validate all items before processing when possible

---

## Best Practices

### ✅ Do:
- Provide clear, descriptive error messages
- Include relevant context and values
- Validate early in the transformation
- Use for business rule enforcement
- Log before throwing errors for debugging

### ❌ Don't:
- Don't use for control flow (use if-else instead)
- Don't throw errors for expected conditions
- Don't include sensitive data in error messages
- Don't make error messages too technical
- Don't use when returning null is appropriate

---

## Tips

1. **Context**: Include variable values in error messages
2. **Actionable**: Tell user what went wrong and how to fix it
3. **Validation**: Use at transformation entry points
4. **Debugging**: Combine with logging for better debugging
5. **Java Integration**: Catch exceptions on Java side for graceful handling

---

## Related Topics

- [Logging Functions](logging.md) - Log errors before throwing
- [If-Else Statements](../08-if-else.md) - Conditional validation
- [exists() Function](exists.md) - Check value existence
- [Error Handling](../16-error-handling.md) - Error handling guide

---

**See Also:**
- [Quick Start](../01-quick-start.md)
- [Basic Concepts](../02-basic-concepts.md)
- [Data Validation Patterns](../23-common-patterns.md)
