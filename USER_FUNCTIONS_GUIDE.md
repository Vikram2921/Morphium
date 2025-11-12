# Morphium DSL - User-Defined Functions & Modules Guide

## Overview

Morphium now supports powerful features for code reuse and organization:
- **User-Defined Functions** - Create your own reusable functions
- **Global Variables** - Share constants across functions
- **Local Variables** - Function-scoped variables with `let`
- **Module System** - Import and reuse functions from other files

## User-Defined Functions

### Basic Function Syntax

```javascript
// Block body syntax
function functionName(param1, param2) {
  let localVar = param1 + param2
  return localVar * 2
}

// Arrow function syntax (single expression)
function double(x) => x * 2
```

### Simple Example

```javascript
function calculateTax(amount, rate) {
  return amount * rate
}

{
  price: input.price,
  tax: calculateTax(input.price, 0.1),
  total: input.price + calculateTax(input.price, 0.1)
}
```

### Functions Calling Functions

```javascript
function square(x) {
  return x * x
}

function sumOfSquares(a, b) {
  return square(a) + square(b)
}

{
  result: sumOfSquares(3, 4)  // Returns 25 (9 + 16)
}
```

### Functions with Local Variables

```javascript
function calculateCircleArea(radius) {
  let pi = 3.14159
  let squared = radius * radius
  return pi * squared
}
```

### Functions in Array Operations

```javascript
function double(x) {
  return x * 2
}

{
  doubled: map(input.numbers, "n", double(n))
}
```

## Global Variables

Global variables are accessible from anywhere in your transform, including inside functions.

### Syntax

```javascript
global VARIABLE_NAME = value
```

### Example

```javascript
global TAX_RATE = 0.08
global DISCOUNT_THRESHOLD = 100

function calculateTax(amount) {
  return amount * TAX_RATE
}

function applyDiscount(price) {
  if (price > DISCOUNT_THRESHOLD) {
    return price * 0.9
  } else {
    return price
  }
}

{
  discounted: applyDiscount(input.price),
  tax: calculateTax(applyDiscount(input.price))
}
```

### Best Practices for Globals

- Use UPPERCASE names for constants
- Define at the top of your transform
- Use for configuration values (tax rates, thresholds, etc.)
- Avoid overusing - prefer function parameters when possible

## Local Variables

Local variables are scoped to the function or block where they're defined.

### In Functions

```javascript
function complexCalculation(x, y) {
  let sum = x + y
  let product = x * y
  let average = sum / 2
  return average * product
}
```

### In Main Transform

```javascript
let cleaned = map(input.data, "d", trim(lower(d.name)))
let filtered = filter(cleaned, "c", len(c) > 3)
filtered
```

### Shadowing

Local variables shadow outer variables with the same name:

```javascript
global PI = 3.14

function calculate(PI) {  // Parameter shadows global
  return PI * 2  // Uses parameter, not global
}
```

## Module System

Break your transforms into reusable modules.

### Creating a Module

**File: math-utils.morph**
```javascript
export add = function(a, b) {
  return a + b
}

export multiply = function(a, b) {
  return a * b
}

export square = function(x) {
  return multiply(x, x)
}
```

### Importing a Module

```javascript
import "math-utils.morph" as math

{
  numbers: map(input.numbers, "n", {
    value: n,
    squared: math.square(n),
    doubled: math.multiply(n, 2)
  })
}
```

### Module Best Practices

1. **One module per file** - Keep modules focused
2. **Export functions** - Use `export name = function(...) {...}`
3. **Descriptive names** - `math-utils.morph`, `string-helpers.morph`
4. **Relative paths** - Keep modules in the same directory or subdirectories

## Complete Examples

### Example 1: E-commerce Order Processing

```javascript
global TAX_RATE = 0.08
global SHIPPING_THRESHOLD = 50
global SHIPPING_COST = 5.99

function calculateItemTotal(price, quantity) {
  return price * quantity
}

function calculateSubtotal(items) {
  return reduce(items, "sum", "item", 0, 
    sum + calculateItemTotal(item.price, item.qty))
}

function calculateTax(subtotal) {
  return subtotal * TAX_RATE
}

function calculateShipping(subtotal) {
  if (subtotal >= SHIPPING_THRESHOLD) {
    return 0
  } else {
    return SHIPPING_COST
  }
}

function formatCurrency(amount) {
  return "$" + toNumber(amount).toFixed(2)
}

{
  order: {
    items: map(input.items, "item", {
      name: item.name,
      quantity: item.qty,
      unitPrice: formatCurrency(item.price),
      lineTotal: formatCurrency(calculateItemTotal(item.price, item.qty))
    }),
    summary: {
      subtotal: formatCurrency(calculateSubtotal(input.items)),
      tax: formatCurrency(calculateTax(calculateSubtotal(input.items))),
      shipping: formatCurrency(calculateShipping(calculateSubtotal(input.items))),
      total: formatCurrency(
        calculateSubtotal(input.items) + 
        calculateTax(calculateSubtotal(input.items)) + 
        calculateShipping(calculateSubtotal(input.items))
      )
    }
  }
}
```

### Example 2: Data Validation with Functions

```javascript
function isValidEmail(email) {
  let parts = split(email, "@")
  return len(parts) == 2 && len(parts[0]) > 0 && len(parts[1]) > 0
}

function normalizePhone(phone) {
  return replace(replace(replace(phone, "(", ""), ")", ""), "-", "")
}

function validateUser(user) {
  let hasName = exists(user.name) && len(trim(user.name)) > 0
  let hasValidEmail = exists(user.email) && isValidEmail(user.email)
  let hasPhone = exists(user.phone)
  
  return hasName && hasValidEmail && hasPhone
}

{
  validUsers: filter(input.users, "u", validateUser(u)),
  normalizedUsers: map(
    filter(input.users, "u", validateUser(u)),
    "u",
    {
      name: trim(u.name),
      email: lower(trim(u.email)),
      phone: normalizePhone(u.phone)
    }
  )
}
```

### Example 3: Using Modules for Reusability

**File: validation-utils.morph**
```javascript
export isValidEmail = function(email) {
  let parts = split(email, "@")
  return len(parts) == 2
}

export isValidPhone = function(phone) {
  let cleaned = replace(replace(replace(phone, "(", ""), ")", ""), "-", "")
  return len(cleaned) == 10
}

export normalize = function(text) {
  return trim(lower(text))
}
```

**File: transform.morph**
```javascript
import "validation-utils.morph" as validate

{
  users: map(input.users, "u", {
    name: u.name,
    email: validate.normalize(u.email),
    emailValid: validate.isValidEmail(u.email),
    phoneValid: validate.isValidPhone(u.phone)
  }),
  validCount: len(
    filter(input.users, "u", 
      validate.isValidEmail(u.email) && validate.isValidPhone(u.phone))
  )
}
```

## Function Signatures

### Standard Function
```javascript
function name(param1, param2, param3) {
  // Function body with multiple statements
  let local = param1 + param2
  return local * param3
}
```

### Arrow Function
```javascript
function name(param1, param2) => param1 + param2
```

### No Parameters
```javascript
function getCurrentTimestamp() {
  return now()
}
```

## Common Patterns

### 1. Helper Functions
```javascript
function formatDate(dateStr) {
  return formatDate(dateStr, "MM/dd/yyyy")
}

function formatMoney(amount) {
  return "$" + toNumber(amount).toFixed(2)
}
```

### 2. Validators
```javascript
function isAdult(age) {
  return age >= 18
}

function hasRequiredFields(obj) {
  return exists(obj.name) && exists(obj.email) && exists(obj.phone)
}
```

### 3. Transformers
```javascript
function normalizeUser(user) {
  return {
    id: user.id,
    name: upper(trim(user.name)),
    email: lower(trim(user.email)),
    createdAt: now()
  }
}
```

### 4. Calculators
```javascript
function calculateDiscount(price, percentage) {
  return price * (percentage / 100)
}

function applyDiscountedPrice(price, discount) {
  return price - calculateDiscount(price, discount)
}
```

## Tips & Best Practices

### ✅ DO

- Use descriptive function names (`calculateTotal` not `calc`)
- Keep functions small and focused (single responsibility)
- Use global variables for constants
- Use local variables for intermediate calculations
- Create modules for reusable utilities
- Document complex logic with comments
- Use arrow functions for simple one-liners

### ❌ DON'T

- Don't create deeply nested functions
- Don't use globals for temporary values
- Don't duplicate logic - extract to functions
- Don't create God functions that do everything
- Don't forget to export functions in modules

## Error Handling

Functions provide clear error messages:

```javascript
function divide(a, b) {
  if (b == 0) {
    throw("Division by zero")
  }
  return a / b
}
```

## Performance Considerations

- Functions have minimal overhead
- Use pure functions (no side effects) when possible
- Avoid unnecessary function calls in tight loops
- Cache expensive calculations in local variables

## Summary

With user-defined functions, global variables, and modules, you can:

1. ✅ **Write reusable code** - Define once, use everywhere
2. ✅ **Organize complex transforms** - Break into logical pieces
3. ✅ **Share code across projects** - Create utility modules
4. ✅ **Improve maintainability** - Clear, testable functions
5. ✅ **Build libraries** - Collections of domain-specific functions

These features make Morphium suitable for real-world, production use cases!
