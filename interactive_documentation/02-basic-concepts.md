# Basic Concepts and Fundamentals

Learn the foundational concepts of Morphium DSL that will help you understand how to write effective transformation scripts.

## Table of Contents
- [What is Morphium DSL?](#what-is-morphium-dsl)
- [Core Philosophy](#core-philosophy)
- [Data Flow Model](#data-flow-model)
- [Variables and Assignment](#variables-and-assignment)
- [Data Types](#data-types)
- [Comments](#comments)
- [Expressions vs Statements](#expressions-vs-statements)
- [Function Chaining](#function-chaining)
- [Error Handling](#error-handling)
- [Best Practices](#best-practices)

---

## What is Morphium DSL?

Morphium DSL (Domain-Specific Language) is a purpose-built language for data transformation. It's designed to make complex data manipulations simple, readable, and maintainable.

### Key Characteristics

- **Declarative**: Focus on *what* to do, not *how* to do it
- **Functional**: Emphasizes immutability and pure functions
- **Chainable**: Operations can be chained together fluently
- **Type-Safe**: Strong type checking at runtime
- **Java Integration**: Seamlessly integrates with Java applications

### When to Use Morphium

✅ **Good Use Cases:**
- ETL (Extract, Transform, Load) operations
- Data normalization and cleaning
- JSON/XML transformations
- Report generation
- Data aggregation and analysis
- API response transformation

❌ **Not Ideal For:**
- Complex business logic with many branches
- Performance-critical operations (use Java instead)
- UI rendering
- Database queries (use SQL)

---

## Core Philosophy

### 1. Immutability First

Data transformations create new objects rather than modifying existing ones:

```morph
// Original data is never modified
data = [1, 2, 3]
doubled = data.map(x -> x * 2)

// data is still [1, 2, 3]
// doubled is [2, 4, 6]
```

### 2. Function Composition

Build complex transformations from simple functions:

```morph
// Each step is clear and testable
result = data
  .filter(x -> x > 0)      // Step 1: Keep positive numbers
  .map(x -> x * 2)         // Step 2: Double each value
  .reduce((a, b) -> a + b) // Step 3: Sum all values
```

### 3. Readability Over Brevity

Prefer clear, self-documenting code:

```morph
// Good: Clear intent
activeUsers = users.filter(user -> user.active == true)

// Avoid: Too terse
au = u.filter(x -> x.a)
```

---

## Data Flow Model

Morphium uses a **pipeline model** where data flows through transformations:

```
Input Data → Transform 1 → Transform 2 → Transform 3 → Output
```

### Simple Example

```morph
// Start with raw data
orders = [
  {id: 1, amount: 100, status: "completed"},
  {id: 2, amount: 200, status: "pending"},
  {id: 3, amount: 150, status: "completed"}
]

// Transform through pipeline
totalCompleted = orders
  .filter(order -> order.status == "completed")  // Filter completed orders
  .map(order -> order.amount)                    // Extract amounts
  .sum()                                         // Calculate total

// Result: 250
```

### Visualizing the Flow

```
orders (3 items)
  ↓
filter by status == "completed" (2 items)
  ↓
map to amount ([100, 150])
  ↓
sum (250)
```

---

## Variables and Assignment

### Declaring Variables

Use simple assignment with `=`:

```morph
// Basic assignment
name = "John"
age = 30
isActive = true

// Array assignment
numbers = [1, 2, 3, 4, 5]

// Object assignment
user = {
  name: "Alice",
  email: "alice@example.com",
  role: "admin"
}
```

### Variable Naming Rules

✅ **Valid Names:**
```morph
userName = "John"
user_name = "John"
user2 = "John"
_private = "John"
camelCase = "preferred"
```

❌ **Invalid Names:**
```morph
2user = "John"        // Cannot start with number
user-name = "John"    // No hyphens
class = "John"        // Reserved keyword
```

### Variable Scope

Variables are scoped to their block:

```morph
// Global scope
total = 0

// Block scope in functions
result = numbers.map(x -> {
  doubled = x * 2    // Only exists in this block
  return doubled
})

// 'doubled' is not accessible here
```

### Reassignment

Variables can be reassigned:

```morph
counter = 0
counter = counter + 1  // counter is now 1
counter = counter * 2  // counter is now 2
```

---

## Data Types

Morphium supports several core data types:

### 1. Numbers

Both integers and decimals:

```morph
// Integers
age = 25
count = 1000

// Decimals
price = 99.99
percentage = 0.15
scientific = 1.5e10

// Negative numbers
debt = -500
```

### 2. Strings

Text enclosed in double quotes:

```morph
// Basic strings
name = "John Doe"
message = "Hello, World!"

// Strings with special characters
path = "C:\\Users\\John"
quote = "She said \"Hello\""

// Empty strings
empty = ""
```

### 3. Booleans

True or false values:

```morph
isActive = true
isDeleted = false

// Boolean expressions
hasAccess = age >= 18
isValid = username != "" && password != ""
```

### 4. Arrays

Ordered collections of values:

```morph
// Simple arrays
numbers = [1, 2, 3, 4, 5]
names = ["Alice", "Bob", "Charlie"]

// Mixed types (not recommended)
mixed = [1, "text", true]

// Nested arrays
matrix = [
  [1, 2, 3],
  [4, 5, 6],
  [7, 8, 9]
]

// Empty array
empty = []
```

### 5. Objects

Key-value pairs:

```morph
// Simple object
user = {
  name: "John",
  age: 30,
  email: "john@example.com"
}

// Nested objects
company = {
  name: "TechCorp",
  address: {
    street: "123 Main St",
    city: "Boston",
    zip: "02101"
  },
  employees: 500
}

// Empty object
empty = {}
```

### 6. Null

Represents absence of value:

```morph
optionalField = null

// Checking for null
if (optionalField == null) {
  log("Field is null")
}
```

---

## Comments

### Single-Line Comments

Use `//` for comments:

```morph
// This is a single-line comment
name = "John"  // Comment after code

// TODO: Add validation
age = 30
```

### Multi-Line Comments

Use `/* */` for block comments:

```morph
/*
 * This is a multi-line comment
 * It can span multiple lines
 * Useful for documentation
 */
result = data.map(x -> x * 2)

/* 
   Quick comment block
*/
```

### Documentation Comments

Use comments to explain complex logic:

```morph
/*
 * Calculate total revenue for completed orders
 * 
 * Process:
 * 1. Filter out pending/cancelled orders
 * 2. Extract order amounts
 * 3. Sum all amounts
 */
revenue = orders
  .filter(o -> o.status == "completed")
  .map(o -> o.amount)
  .sum()
```

---

## Expressions vs Statements

### Expressions

Expressions produce a value:

```morph
// Arithmetic expressions
sum = 10 + 20              // 30
product = 5 * 6            // 30

// Comparison expressions
isAdult = age >= 18        // true or false
isEqual = name == "John"   // true or false

// Logical expressions
canAccess = isActive && hasPermission
shouldAlert = level > 5 || critical == true

// Function call expressions
length = len(items)
upper = name.upper()
```

### Statements

Statements perform actions:

```morph
// Assignment statement
total = 0

// If statement
if (score >= 90) {
  grade = "A"
}

// For loop statement
for (item of items) {
  log(item)
}

// Return statement
return result
```

### Expression Statements

Some expressions can be used as statements:

```morph
// Function call as statement
log("Processing...")
error("Invalid input")

// Method chain as statement
data.forEach(x -> log(x))
```

---

## Function Chaining

One of Morphium's most powerful features is method chaining:

### Basic Chaining

```morph
result = numbers
  .filter(x -> x > 0)
  .map(x -> x * 2)
  .sorted()
```

### Multi-Step Chains

```morph
// Data transformation pipeline
processed = rawData
  .filter(item -> item.status == "active")  // Step 1
  .map(item -> {                            // Step 2
    return {
      id: item.id,
      name: item.name.upper(),
      score: item.score * 1.1
    }
  })
  .sorted((a, b) -> b.score - a.score)      // Step 3
  .slice(0, 10)                             // Step 4
```

### Breaking Chains for Clarity

When chains get long, break them into named steps:

```morph
// Long chain - hard to read
final = data.filter(x -> x.a).map(x -> x.b).filter(x -> x > 0).sum()

// Better - named intermediate steps
active = data.filter(x -> x.active)
values = active.map(x -> x.value)
positive = values.filter(x -> x > 0)
total = positive.sum()
```

### Debugging Chains

Use `peek()` to inspect intermediate values:

```morph
result = numbers
  .filter(x -> x > 0)
  .peek(x -> log("After filter: " + x))
  .map(x -> x * 2)
  .peek(x -> log("After map: " + x))
  .sum()
```

---

## Error Handling

### Using error() Function

Throw errors when validation fails:

```morph
// Basic error
if (age < 0) {
  error("Age cannot be negative")
}

// Error with context
if (users.len() == 0) {
  error("No users found in dataset")
}
```

### Validation Patterns

```morph
// Input validation
if (email == null || email == "") {
  error("Email is required")
}

if (!email.contains("@")) {
  error("Invalid email format: " + email)
}

// Range validation
if (quantity < 1 || quantity > 100) {
  error("Quantity must be between 1 and 100, got: " + quantity)
}

// Type validation
if (typeof(amount) != "number") {
  error("Amount must be a number")
}
```

### Safe Operations

Use conditional checks to prevent errors:

```morph
// Safe division
result = if (divisor != 0) {
  numerator / divisor
} else {
  0
}

// Safe array access
first = if (items.len() > 0) {
  items[0]
} else {
  null
}

// Safe object access
name = if (user != null) {
  user.name
} else {
  "Unknown"
}
```

---

## Best Practices

### 1. Use Descriptive Names

```morph
// Good
activeUsers = users.filter(u -> u.active)
totalRevenue = orders.map(o -> o.amount).sum()

// Avoid
x = users.filter(u -> u.a)
t = orders.map(o -> o.amt).sum()
```

### 2. Keep Functions Small

```morph
// Good - each function does one thing
isValid = user -> user.email != null && user.email.contains("@")
activeUsers = users.filter(isValid)

// Avoid - doing too much at once
result = users.filter(u -> {
  valid = u.email != null && u.email.contains("@")
  active = u.status == "active"
  recent = u.lastLogin > threshold
  return valid && active && recent
})
```

### 3. Use Appropriate Data Structures

```morph
// Good - use object for key-value mapping
userMap = users.indexBy(u -> u.id)
user = userMap.get("123")

// Avoid - searching array repeatedly
user = users.filter(u -> u.id == "123")[0]
```

### 4. Comment Complex Logic

```morph
// Calculate weighted average score
// Formula: (score * weight) / total_weight
weightedAvg = items
  .map(item -> item.score * item.weight)
  .sum() / items.map(item -> item.weight).sum()
```

### 5. Validate Early

```morph
// Validate at the start
if (data == null || data.len() == 0) {
  error("Data is required")
}

// Then process
result = data.map(item -> process(item))
```

### 6. Use Immutability

```morph
// Good - create new objects
updated = original.map(item -> {
  return {
    ...item,
    processed: true
  }
})

// Avoid - don't try to modify in place
// This won't work as expected
original.forEach(item -> {
  item.processed = true  // Creates new object, doesn't modify original
})
```

### 7. Profile Performance

```morph
// Log timing for expensive operations
start = now()
result = largeDataset
  .filter(complexCondition)
  .map(expensiveTransform)
log("Processing took: " + (now() - start) + "ms")
```

---

## Quick Reference

### Variable Declaration
```morph
variableName = value
```

### Data Types
```morph
number = 42
string = "text"
boolean = true
array = [1, 2, 3]
object = {key: "value"}
nullValue = null
```

### Comments
```morph
// Single line
/* Multi-line */
```

### Basic Operations
```morph
// Arithmetic
sum = a + b
diff = a - b
product = a * b
quotient = a / b
remainder = a % b

// Comparison
equal = a == b
notEqual = a != b
greater = a > b
less = a < b
greaterOrEqual = a >= b
lessOrEqual = a <= b

// Logical
and = a && b
or = a || b
not = !a
```

### Function Chaining
```morph
result = data
  .filter(predicate)
  .map(transform)
  .reduce(accumulator)
```

---

## Next Steps

Now that you understand the basics, continue learning:

1. **[Syntax Overview](03-syntax-overview.md)** - Detailed syntax guide
2. **[Control Flow](08-if-else.md)** - If-else, switch, loops
3. **[Function Reference](FUNCTION_REFERENCE.md)** - All available functions
4. **[Quick Start](01-quick-start.md)** - Hands-on examples

---

## Navigation

- [← Back to README](README.md)
- [→ Next: Syntax Overview](03-syntax-overview.md)
- [Function Reference](FUNCTION_REFERENCE.md)
- [Quick Start](01-quick-start.md)

---
