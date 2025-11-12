# Control Flow Statements: If-Else, Switch, and For-Of

## Overview
Morphium DSL now supports traditional control flow statements: `if-else`, `switch`, and `for-of` loops. These statements can be used both as statements and as expressions.

---

## 1. If-Else Statements

### Syntax

```javascript
if (condition) {
  // then branch
} else {
  // else branch (optional)
}
```

### Features

- **As expression**: Can be used directly in assignments or object properties
- **Nested if-else**: Supports else-if chains
- **Optional else**: The else branch is optional
- **Truthiness**: Follows standard JavaScript truthiness rules

### Examples

#### Simple If-Else

```javascript
{
  status: if ($.age >= 18) { 'adult' } else { 'minor' }
}
```

#### Variable Assignment

```javascript
let category = if ($.score > 90) {
  'excellent'
} else if ($.score > 75) {
  'good'
} else {
  'needs improvement'
};
{ category: category }
```

#### Without Else

```javascript
let bonus = if ($.performance == 'excellent') { 1000 };
// bonus will be null if condition is false
```

### Truthiness Rules

The following values are considered falsy:
- `null`
- `false`
- `0`
- `""` (empty string)
- `[]` (empty array)
- `{}` (empty object)

Everything else is truthy.

---

## 2. Switch Statements

### Syntax

```javascript
switch (expression) {
  case value1: result1
  case value2: result2
  default: defaultResult
}
```

### Features

- **Type-aware comparison**: Numeric and string comparisons work correctly
- **Expression-based**: Each case can be any expression
- **Optional default**: Default case is optional
- **No fall-through**: Unlike JavaScript/Java, each case automatically breaks
- **As expression**: Can be used directly in assignments

### Examples

#### String Matching

```javascript
{
  dayType: switch ($.day) {
    case 'Monday': 'weekday'
    case 'Tuesday': 'weekday'
    case 'Wednesday': 'weekday'
    case 'Thursday': 'weekday'
    case 'Friday': 'weekday'
    case 'Saturday': 'weekend'
    case 'Sunday': 'weekend'
    default: 'unknown'
  }
}
```

#### Numeric Matching

```javascript
{
  level: switch ($.points) {
    case 0: 'beginner'
    case 1: 'novice'
    case 2: 'intermediate'
    case 3: 'advanced'
    default: 'expert'
  }
}
```

#### Expression Cases

```javascript
{
  price: switch ($.membership) {
    case 'basic': $.basePrice
    case 'premium': $.basePrice * 0.9
    case 'vip': $.basePrice * 0.75
    default: $.basePrice
  }
}
```

### Comparison Behavior

- Numbers are compared numerically: `1` matches `1.0`
- Strings are compared as strings: `"1"` does NOT match `1`
- Booleans, null, and objects use strict equality

---

## 3. For-Of Loops

### Syntax

```javascript
for (variableName of arrayExpression) {
  // body expression
}
```

### Features

- **Returns array**: Collects results from each iteration
- **Scoped variable**: Loop variable is scoped to the loop body
- **Works with any array**: Input must be a JSON array
- **Expression-based**: Body can be any expression

### Examples

#### Simple Transformation

```javascript
{
  doubled: for (n of $.numbers) {
    n * 2
  }
}

// Input: {"numbers": [1, 2, 3]}
// Output: {"doubled": [2, 4, 6]}
```

#### Object Transformation

```javascript
{
  users: for (user of $.users) {
    {
      id: user.id,
      fullName: user.firstName + ' ' + user.lastName,
      email: user.email
    }
  }
}
```

#### With Filtering (Combined with If)

```javascript
{
  adults: for (person of $.people) {
    if (person.age >= 18) {
      person
    }
  }
}
// Note: If condition is false, null is added to result array
```

#### Nested Loops

```javascript
{
  pairs: for (a of $.list1) {
    for (b of $.list2) {
      { first: a, second: b }
    }
  }
}
```

---

## Combining Control Flow

Control flow statements can be combined to create complex transformations:

### Example: Order Processing

```javascript
{
  orderId: $.orderId,
  items: for (item of $.items) {
    {
      name: item.name,
      discount: switch (item.category) {
        case 'electronics': item.price * 0.1
        case 'books': item.price * 0.15
        case 'clothing': item.price * 0.2
        default: 0
      },
      finalPrice: if (item.onSale) {
        item.price * 0.5
      } else {
        item.price
      }
    }
  },
  shippingCost: if ($.total > 100) { 0 } else { 10 }
}
```

---

## Comparison with Functional Style

Morphium also supports functional-style operations. Here's how they compare:

### For-Of vs Map

```javascript
// For-of style
for (item of $.items) { item * 2 }

// Functional style
map($.items, "item", item * 2)
```

**Use for-of when:**
- You want imperative-style code
- You're more familiar with traditional loops
- You need complex multi-statement bodies

**Use map when:**
- You want functional composition
- You're chaining operations
- You want to pass transformation as a parameter

### If-Else vs Ternary

```javascript
// If-else
if ($.value > 10) { 'high' } else { 'low' }

// Ternary operator (still supported)
$.value > 10 ? 'high' : 'low'
```

Both are equivalent. Use whichever is more readable for your use case.

---

## Best Practices

### 1. Use Blocks for Readability

```javascript
// Good - multi-line is readable
if ($.score >= 90) {
  'A'
} else if ($.score >= 80) {
  'B'
} else {
  'C'
}

// Also OK - single line for simple cases
if ($.active) { 'enabled' } else { 'disabled' }
```

### 2. Prefer Switch for Multiple Equality Checks

```javascript
// Good - use switch
switch ($.status) {
  case 'pending': 1
  case 'processing': 2
  case 'complete': 3
  default: 0
}

// Avoid - nested if-else for equality
if ($.status == 'pending') {
  1
} else if ($.status == 'processing') {
  2
} else if ($.status == 'complete') {
  3
} else {
  0
}
```

### 3. Use For-Of for Transformations

```javascript
// Good - clear transformation
for (user of $.users) {
  { name: user.name, email: user.email }
}

// Alternative - functional style is also good
map($.users, "user", { name: user.name, email: user.email })
```

### 4. Combine Control Flow Judiciously

Don't nest too deeply. If you find yourself with many levels of nesting, consider breaking the transformation into multiple steps or using helper functions.

---

## Limitations

1. **Break/Continue**: Not yet supported (each for-of iteration always completes)
2. **While/Do-While**: Not supported (use for-of with arrays)
3. **For with counter**: Not supported (use for-of with array indices if needed)

---

## Examples

See `ControlFlowExamples.java` in the examples folder for comprehensive demonstrations.

---

## Version

Added in: Morphium DSL 1.0.0-SNAPSHOT  
Date: 2025-11-12
