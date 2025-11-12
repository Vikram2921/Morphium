# Variables and Scope

Understanding variable declaration and scoping rules in Morphium DSL.

---

## Variable Declaration

### Using `let`

Variables are declared using the `let` keyword:

```javascript
let variableName = value;
```

### Parameters
- **variableName**: Identifier for the variable
- **value**: Any valid expression (literal, object, array, function call, etc.)

### Returns
The `let` statement itself returns `null`, but the variable can be used in subsequent expressions.

---

## Description

Morphium DSL uses lexical scoping similar to JavaScript. Variables are declared with `let` and are scoped to the block or context in which they're defined. Variables can shadow outer scope variables.

---

## Basic Examples

### Example 1: Simple Variable Declaration
```javascript
// Input
{
  "price": 100
}

// Script
let tax = $.price * 0.1;
{
  price: $.price,
  tax: tax,
  total: $.price + tax
}

// Output
{
  "price": 100,
  "tax": 10,
  "total": 110
}
```

### Example 2: Multiple Variable Declarations
```javascript
// Input
{
  "width": 10,
  "height": 20
}

// Script
let w = $.width;
let h = $.height;
let area = w * h;
let perimeter = 2 * (w + h);

{
  dimensions: { width: w, height: h },
  area: area,
  perimeter: perimeter
}

// Output
{
  "dimensions": { "width": 10, "height": 20 },
  "area": 200,
  "perimeter": 60
}
```

### Example 3: Variable from Function Result
```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5]
}

// Script
let doubled = map($.numbers, "n", n * 2);
let total = sum(doubled);

{
  original: $.numbers,
  doubled: doubled,
  sum: total
}

// Output
{
  "original": [1, 2, 3, 4, 5],
  "doubled": [2, 4, 6, 8, 10],
  "sum": 30
}
```

---

## Scope Rules

### Block Scope

Variables are scoped to the block where they're defined:

```javascript
// Input
{
  "value": 10
}

// Script
let outer = $.value;
{
  result: if (outer > 5) {
    let inner = outer * 2;
    inner + 10
  } else {
    outer
  }
}

// Output
{
  "result": 30
}
```

### Loop Scope

Loop variables have their own scope for each iteration:

```javascript
// Input
{
  "items": [1, 2, 3]
}

// Script
{
  results: for (item of $.items) {
    let squared = item * item;
    let cubed = squared * item;
    { original: item, squared: squared, cubed: cubed }
  }
}

// Output
{
  "results": [
    { "original": 1, "squared": 1, "cubed": 1 },
    { "original": 2, "squared": 4, "cubed": 8 },
    { "original": 3, "squared": 9, "cubed": 27 }
  ]
}
```

### Function Scope

User-defined functions have their own scope:

```javascript
// Script
fn double(x) {
  let result = x * 2;
  result
}

let globalValue = 10;
{
  doubled: double(globalValue),
  original: globalValue
}

// Output
{
  "doubled": 20,
  "original": 10
}
```

---

## Advanced Examples

### Example 1: Variable Shadowing
```javascript
// Input
{
  "value": 100
}

// Script
let value = $.value;
{
  outer: value,
  transformed: for (i in [1, 2, 3]) {
    let value = i * 10; // Shadows outer 'value'
    value
  }
}

// Output
{
  "outer": 100,
  "transformed": [10, 20, 30]
}
```

### Example 2: Complex Nested Scopes
```javascript
// Input
{
  "orders": [
    {"id": 1, "items": [10, 20, 30]},
    {"id": 2, "items": [15, 25]}
  ]
}

// Script
let taxRate = 0.08;

{
  ordersWithTax: for (order of $.orders) {
    let orderId = order.id;
    let itemsWithTax = for (item of order.items) {
      let itemTax = item * taxRate;
      let total = item + itemTax;
      { price: item, tax: itemTax, total: total }
    };
    { orderId: orderId, items: itemsWithTax }
  }
}

// Output
{
  "ordersWithTax": [
    {
      "orderId": 1,
      "items": [
        {"price": 10, "tax": 0.8, "total": 10.8},
        {"price": 20, "tax": 1.6, "total": 21.6},
        {"price": 30, "tax": 2.4, "total": 32.4}
      ]
    },
    {
      "orderId": 2,
      "items": [
        {"price": 15, "tax": 1.2, "total": 16.2},
        {"price": 25, "tax": 2.0, "total": 27.0}
      ]
    }
  ]
}
```

### Example 3: Variables in Conditional Expressions
```javascript
// Input
{
  "user": {
    "age": 25,
    "membershipType": "premium"
  }
}

// Script
let age = $.user.age;
let isPremium = $.user.membershipType == 'premium';

let discount = if (isPremium) {
  if (age < 25) { 0.25 }
  else if (age < 65) { 0.15 }
  else { 0.30 }
} else {
  if (age < 25) { 0.10 }
  else { 0.05 }
};

{
  age: age,
  membershipType: $.user.membershipType,
  discountRate: discount
}

// Output
{
  "age": 25,
  "membershipType": "premium",
  "discountRate": 0.15
}
```

---

## Global Variables

### Using `global`

Global variables persist across the entire transformation:

```javascript
global sessionId = 'session-123';
global counter = 0;

// These can be accessed anywhere in the script
{
  session: sessionId,
  count: counter
}
```

### Global Variable Scope

Global variables are accessible from:
- The main script
- All user-defined functions
- All imported modules

```javascript
global appVersion = '1.0.0';

fn getInfo() {
  { version: appVersion } // Can access global
}

{
  info: getInfo()
}
```

---

## Common Patterns

### Pattern 1: Computed Variables for Reuse
```javascript
let basePrice = $.price;
let discount = $.discountPercent / 100;
let tax = 0.08;

{
  basePrice: basePrice,
  afterDiscount: basePrice * (1 - discount),
  finalPrice: basePrice * (1 - discount) * (1 + tax)
}
```

### Pattern 2: Variable Chain
```javascript
let input = $.rawData;
let cleaned = filter(input, "x", x != null);
let transformed = map(cleaned, "x", x * 2);
let sorted = sorted(transformed);
let limited = limit(sorted, 10);

{ result: limited }
```

### Pattern 3: Conditional Variable Assignment
```javascript
let userType = $.user.type;
let permissions = if (userType == 'admin') {
  ['read', 'write', 'delete']
} else if (userType == 'editor') {
  ['read', 'write']
} else {
  ['read']
};

{ permissions: permissions }
```

---

## Best Practices

### ✅ Do:
- Use descriptive variable names
- Declare variables close to where they're used
- Keep scope as narrow as possible
- Use `let` for temporary computations
- Use `global` sparingly for truly global state

### ❌ Don't:
- Don't declare unused variables
- Don't rely on variable shadowing for logic
- Don't use globals when locals suffice
- Don't reuse variable names confusingly

---

## Tips

1. **Performance**: Variables are cheap - use them to avoid recomputing expressions
2. **Readability**: Break complex expressions into named variables
3. **Debugging**: Use meaningful names to make scripts self-documenting
4. **Scope**: When in doubt, use narrower scope (prefer local over global)

---

## Related Topics

- [Data Types](05-data-types.md) - Understanding variable types
- [Operators](06-operators.md) - Operations on variables
- [User-Defined Functions](13-user-functions.md) - Function parameter scoping
- [Module System](14-modules.md) - Module-level scope

---

**See Also:**
- [Quick Start](01-quick-start.md)
- [Basic Concepts](02-basic-concepts.md)
- [Syntax Overview](03-syntax-overview.md)
