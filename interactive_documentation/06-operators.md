# Operators

Complete reference for all operators in Morphium DSL.

---

## Overview

Morphium DSL supports a comprehensive set of operators for arithmetic, comparison, logical operations, and more. Operators follow standard precedence rules similar to JavaScript.

---

## Arithmetic Operators

### Basic Arithmetic

| Operator | Description | Example | Result |
|----------|-------------|---------|--------|
| `+` | Addition | `5 + 3` | `8` |
| `-` | Subtraction | `5 - 3` | `2` |
| `*` | Multiplication | `5 * 3` | `15` |
| `/` | Division | `15 / 3` | `5` |
| `%` | Modulo (remainder) | `17 % 5` | `2` |
| `**` | Exponentiation | `2 ** 3` | `8` |

**Examples:**

```javascript
// Input
{
  "a": 10,
  "b": 3
}

// Script
let a = $.a;
let b = $.b;

{
  sum: a + b,
  difference: a - b,
  product: a * b,
  quotient: a / b,
  remainder: a % b,
  power: a ** 2
}

// Output
{
  "sum": 13,
  "difference": 7,
  "product": 30,
  "quotient": 3.333...,
  "remainder": 1,
  "power": 100
}
```

### Unary Operators

| Operator | Description | Example | Result |
|----------|-------------|---------|--------|
| `-` | Negation | `-5` | `-5` |
| `+` | Unary plus | `+"5"` | `5` |

```javascript
let positive = 42;
let negative = -positive;  // -42
let stringNum = "100";
let converted = +stringNum;  // 100
```

---

## Comparison Operators

### Equality and Relational

| Operator | Description | Example | Result |
|----------|-------------|---------|--------|
| `==` | Equal to | `5 == 5` | `true` |
| `!=` | Not equal to | `5 != 3` | `true` |
| `<` | Less than | `3 < 5` | `true` |
| `>` | Greater than | `5 > 3` | `true` |
| `<=` | Less than or equal | `5 <= 5` | `true` |
| `>=` | Greater than or equal | `5 >= 3` | `true` |

**Examples:**

```javascript
// Input
{
  "age": 25,
  "minAge": 18,
  "maxAge": 65
}

// Script
let age = $.age;
let min = $.minAge;
let max = $.maxAge;

{
  isAdult: age >= min,
  isSenior: age >= max,
  isWorking: age >= min && age < max,
  isEqual: age == 25,
  isNotEqual: age != 30
}

// Output
{
  "isAdult": true,
  "isSenior": false,
  "isWorking": true,
  "isEqual": true,
  "isNotEqual": true
}
```

### Null-Safe Comparison

```javascript
let value = $.optionalField;

{
  isNull: value == null,
  isNotNull: value != null,
  hasValue: value ?? "default"
}
```

---

## Logical Operators

### Boolean Logic

| Operator | Description | Example | Result |
|----------|-------------|---------|--------|
| `&&` | Logical AND | `true && false` | `false` |
| `\|\|` | Logical OR | `true \|\| false` | `true` |
| `!` | Logical NOT | `!true` | `false` |

**Short-Circuit Evaluation:**

```javascript
// AND: Returns first falsy value or last value
true && true && 5    // 5
false && error()     // false (error() not called)

// OR: Returns first truthy value or last value
false || null || "default"  // "default"
true || error()             // true (error() not called)
```

**Examples:**

```javascript
// Input
{
  "user": {
    "age": 25,
    "isPremium": true,
    "hasLicense": true
  }
}

// Script
let user = $.user;
let age = user.age;
let isPremium = user.isPremium;
let hasLicense = user.hasLicense;

{
  canRentCar: age >= 21 && hasLicense,
  hasDiscount: age < 25 || age > 65 || isPremium,
  needsApproval: !isPremium && age < 25,
  fullAccess: isPremium && hasLicense && age >= 18
}

// Output
{
  "canRentCar": true,
  "hasDiscount": true,
  "needsApproval": false,
  "fullAccess": true
}
```

### Complex Logical Expressions

```javascript
// Input
{
  "product": {
    "price": 150,
    "inStock": true,
    "category": "electronics",
    "rating": 4.5
  }
}

// Script
let p = $.product;

{
  shouldPromote: (p.category == "electronics" || p.category == "books") 
                 && p.rating >= 4.0 
                 && p.inStock,
  
  isPremiumDeal: p.price > 100 && p.rating > 4.5,
  
  needsRestock: !p.inStock || (p.inStock && p.price < 50)
}

// Output
{
  "shouldPromote": true,
  "isPremiumDeal": false,
  "needsRestock": false
}
```

---

## String Operators

### Concatenation

| Operator | Description | Example | Result |
|----------|-------------|---------|--------|
| `+` | String concatenation | `"Hello" + " " + "World"` | `"Hello World"` |

**Examples:**

```javascript
// Input
{
  "firstName": "John",
  "lastName": "Doe",
  "age": 30
}

// Script
let first = $.firstName;
let last = $.lastName;
let age = $.age;

{
  fullName: first + " " + last,
  greeting: "Hello, " + first + "!",
  info: first + " is " + toString(age) + " years old",
  formatted: first + " " + last + " (" + toString(age) + ")"
}

// Output
{
  "fullName": "John Doe",
  "greeting": "Hello, John!",
  "info": "John is 30 years old",
  "formatted": "John Doe (30)"
}
```

### String Comparison

```javascript
let str1 = "apple";
let str2 = "banana";

{
  equal: str1 == str2,           // false
  notEqual: str1 != str2,        // true
  lexicographic: str1 < str2     // true (alphabetical order)
}
```

---

## Member Access Operators

### Dot Notation

```javascript
// Input
{
  "user": {
    "name": "Alice",
    "address": {
      "city": "New York"
    }
  }
}

// Script
{
  name: $.user.name,
  city: $.user.address.city
}
```

### Bracket Notation

```javascript
// Input
{
  "data": {
    "field-with-dash": "value",
    "items": [1, 2, 3]
  }
}

// Script
{
  field: $.data["field-with-dash"],
  firstItem: $.data.items[0],
  lastItem: $.data.items[2]
}
```

### Dynamic Access

```javascript
// Input
{
  "config": {
    "theme": "dark",
    "language": "en"
  },
  "key": "theme"
}

// Script
let key = $.key;
{
  value: $.config[key]  // Accesses $.config.theme
}

// Output
{
  "value": "dark"
}
```

---

## Array Index Operator

### Basic Indexing

```javascript
let arr = [10, 20, 30, 40, 50];

{
  first: arr[0],      // 10
  second: arr[1],     // 20
  last: arr[4]        // 50
}
```

### Negative Indexing (if supported)

```javascript
let arr = [1, 2, 3, 4, 5];

{
  last: arr[-1],      // 5
  secondLast: arr[-2] // 4
}
```

---

## Ternary Operator

### Conditional Expression

**Syntax:** `condition ? trueValue : falseValue`

```javascript
// Input
{
  "score": 85
}

// Script
{
  grade: $.score >= 90 ? "A" :
         $.score >= 80 ? "B" :
         $.score >= 70 ? "C" :
         $.score >= 60 ? "D" : "F",
         
  passed: $.score >= 60 ? "Pass" : "Fail",
  
  bonus: $.score > 95 ? 100 : 
         $.score > 85 ? 50 : 0
}

// Output
{
  "grade": "B",
  "passed": "Pass",
  "bonus": 0
}
```

### Nested Ternary

```javascript
// Input
{
  "user": {
    "age": 25,
    "type": "premium"
  }
}

// Script
let user = $.user;
let discount = user.type == "premium" 
  ? (user.age < 25 ? 0.25 : 0.15)
  : (user.age < 25 ? 0.10 : 0.05);

{ discount: discount }

// Output
{
  "discount": 0.25
}
```

---

## Null Coalescing Operator

### Default Values

**Syntax:** `value ?? defaultValue`

```javascript
// Input
{
  "config": {
    "timeout": null,
    "retries": 3
  }
}

// Script
{
  timeout: $.config.timeout ?? 30,
  retries: $.config.retries ?? 5,
  host: $.config.host ?? "localhost"
}

// Output
{
  "timeout": 30,
  "retries": 3,
  "host": "localhost"
}
```

### Chaining

```javascript
let primary = $.primary;
let secondary = $.secondary;
let tertiary = $.tertiary;

{
  value: primary ?? secondary ?? tertiary ?? "default"
}
```

---

## Operator Precedence

**From highest to lowest:**

1. Member access: `.` `[]`
2. Unary: `!` `-` `+`
3. Exponentiation: `**`
4. Multiplicative: `*` `/` `%`
5. Additive: `+` `-`
6. Relational: `<` `>` `<=` `>=`
7. Equality: `==` `!=`
8. Logical AND: `&&`
9. Logical OR: `||`
10. Ternary: `? :`
11. Null coalescing: `??`

**Examples:**

```javascript
// Precedence examples
let result1 = 2 + 3 * 4;        // 14 (not 20)
let result2 = (2 + 3) * 4;      // 20 (parentheses)
let result3 = 10 > 5 && 3 < 7;  // true
let result4 = !false && true;   // true
let result5 = 5 + 3 > 2 * 4;    // false (8 > 8)
```

---

## Advanced Examples

### Example 1: Complex Expression

```javascript
// Input
{
  "order": {
    "subtotal": 100,
    "items": 5,
    "customer": {
      "isPremium": true,
      "age": 25
    }
  }
}

// Script
let order = $.order;
let customer = order.customer;

let discount = customer.isPremium 
  ? (customer.age < 25 ? 0.20 : 0.15)
  : 0.05;

let shipping = order.subtotal > 50 && customer.isPremium ? 0 : 10;
let tax = (order.subtotal * (1 - discount) + shipping) * 0.08;

{
  subtotal: order.subtotal,
  discount: discount,
  afterDiscount: order.subtotal * (1 - discount),
  shipping: shipping,
  tax: tax,
  total: order.subtotal * (1 - discount) + shipping + tax
}
```

### Example 2: Logical Chains

```javascript
// Input
{
  "user": {
    "role": "admin",
    "active": true,
    "verified": true
  },
  "action": "delete"
}

// Script
let user = $.user;
let action = $.action;

let canDelete = user.role == "admin" 
                && user.active 
                && user.verified;

let canEdit = (user.role == "admin" || user.role == "editor")
              && user.active;

let canView = user.role != "guest" || user.verified;

{
  action: action,
  authorized: (action == "delete" && canDelete) ||
              (action == "edit" && canEdit) ||
              (action == "view" && canView)
}
```

### Example 3: Safe Navigation Chain

```javascript
// Input
{
  "data": {
    "user": {
      "profile": {
        "address": {
          "city": "New York"
        }
      }
    }
  }
}

// Script
let data = $.data;
let user = data.user ?? null;
let profile = user != null ? user.profile : null;
let address = profile != null ? profile.address : null;
let city = address != null ? address.city : "Unknown";

{ city: city }

// Or shorter with chaining:
{
  city: $.data.user?.profile?.address?.city ?? "Unknown"
}
```

---

## Common Patterns

### Pattern 1: Conditional Calculation

```javascript
let price = $.price;
let quantity = $.quantity;
let discount = quantity > 10 ? 0.1 : 0;

{
  total: price * quantity * (1 - discount)
}
```

### Pattern 2: Validation

```javascript
let email = $.email;
let isValid = email != null 
              && len(email) > 0 
              && contains(email, "@");

{
  email: email,
  valid: isValid
}
```

### Pattern 3: Multi-Condition Check

```javascript
let status = $.status;
let isSuccess = status == "completed" 
                || status == "success" 
                || status == "done";

let isFailed = status == "failed" 
               || status == "error" 
               || status == "cancelled";
```

---

## Best Practices

### ✅ Do:
- Use parentheses to clarify complex expressions
- Leverage short-circuit evaluation for performance
- Use null coalescing for default values
- Break complex expressions into variables

### ❌ Don't:
- Don't rely on implicit type coercion
- Don't create overly nested ternary operators
- Don't ignore operator precedence
- Don't compare incompatible types without conversion

---

## Tips

1. **Parentheses**: When in doubt, use parentheses to make precedence explicit
2. **Readability**: Break complex expressions into intermediate variables
3. **Type Safety**: Use explicit type conversion instead of relying on coercion
4. **Short-Circuit**: Use `&&` and `||` for conditional logic to avoid errors

---

## Related Topics

- [Variables and Scope](04-variables-scope.md) - Using operators with variables
- [Data Types](05-data-types.md) - Type-specific operations
- [If-Else Statements](08-if-else.md) - Conditional control flow
- [Ternary Expression](08-if-else.md#ternary-operator) - Conditional expressions

---

**See Also:**
- [Quick Start](01-quick-start.md)
- [Basic Concepts](02-basic-concepts.md)
- [Syntax Overview](03-syntax-overview.md)
