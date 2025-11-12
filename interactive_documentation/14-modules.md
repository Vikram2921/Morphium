# Module System

Learn how to organize and reuse Morphium DSL code using the module system with imports and exports.

---

## Overview

The Morphium module system allows you to:
- **Organize code** into reusable modules
- **Share functions** across multiple transformations
- **Create libraries** of common utilities
- **Improve maintainability** with modular design

---

## Export Syntax

```javascript
export function functionName(params) {
    // function body
}

export variable variableName = value;
```

---

## Import Syntax

```javascript
import { functionName, variableName } from "path/to/module.morph";
import * as moduleName from "path/to/module.morph";
```

---

## Basic Examples

### Example 1: Creating a Module

**File: utils/math.morph**
```javascript
export function add(a, b) {
    a + b
}

export function multiply(a, b) {
    a * b
}

export variable PI = 3.14159;
```

**File: main.morph**
```javascript
import { add, multiply, PI } from "utils/math.morph";

{
    sum: add(5, 3),
    product: multiply(4, 7),
    circumference: 2 * PI * 10
}
```

**Output:**
```json
{
    "sum": 8,
    "product": 28,
    "circumference": 62.8318
}
```

---

### Example 2: Import All Exports

**File: utils/strings.morph**
```javascript
export function capitalize(str) {
    upper(slice(str, 0, 1)) + lower(slice(str, 1, len(str)))
}

export function reverse(str) {
    join(reverse(split(str, "")), "")
}

export function truncate(str, maxLen) {
    if (len(str) > maxLen) {
        slice(str, 0, maxLen) + "..."
    } else {
        str
    }
}
```

**File: main.morph**
```javascript
import * as strings from "utils/strings.morph";

{
    capitalized: strings.capitalize("hello world"),
    reversed: strings.reverse("morphium"),
    truncated: strings.truncate("This is a long string", 10)
}
```

**Output:**
```json
{
    "capitalized": "Hello world",
    "reversed": "muihprom",
    "truncated": "This is a ..."
}
```

---

### Example 3: Multiple Imports

**File: utils/validators.morph**
```javascript
export function isEmail(str) {
    exists(str) && len(str) > 5 && exists(split(str, "@")[1])
}

export function isPositive(num) {
    num > 0
}
```

**File: utils/formatters.morph**
```javascript
export function formatPhone(phone) {
    replace(replace(phone, "-", ""), " ", "")
}

export function formatCurrency(amount) {
    "$" + toString(amount)
}
```

**File: main.morph**
```javascript
import { isEmail, isPositive } from "utils/validators.morph";
import { formatPhone, formatCurrency } from "utils/formatters.morph";

{
    emailValid: isEmail($.email),
    amountValid: isPositive($.amount),
    phone: formatPhone($.phone),
    price: formatCurrency($.price)
}
```

---

## Advanced Examples

### Example 4: Nested Module Structure

**File: lib/data/transformers.morph**
```javascript
export function enrichUser(user) {
    {
        id: user.id,
        fullName: user.firstName + " " + user.lastName,
        email: lower(user.email),
        isActive: user.active == true
    }
}

export function enrichOrder(order) {
    {
        orderId: order.id,
        total: order.amount + (order.amount * 0.1),
        status: upper(order.status)
    }
}
```

**File: main.morph**
```javascript
import { enrichUser, enrichOrder } from "lib/data/transformers.morph";

{
    users: map($.users, "u", enrichUser(u)),
    orders: map($.orders, "o", enrichOrder(o))
}
```

---

### Example 5: Shared Constants

**File: config/constants.morph**
```javascript
export variable TAX_RATE = 0.1;
export variable SHIPPING_COST = 5.99;
export variable FREE_SHIPPING_THRESHOLD = 50;
export variable MAX_ITEMS_PER_ORDER = 100;
```

**File: checkout.morph**
```javascript
import { TAX_RATE, SHIPPING_COST, FREE_SHIPPING_THRESHOLD } from "config/constants.morph";

function calculateTotal(subtotal) {
    variable tax = subtotal * TAX_RATE;
    variable shipping = if (subtotal >= FREE_SHIPPING_THRESHOLD) { 0 } else { SHIPPING_COST };
    subtotal + tax + shipping
}

{
    subtotal: sum(map($.items, "i", i.price)),
    total: calculateTotal(sum(map($.items, "i", i.price)))
}
```

---

### Example 6: Utility Library

**File: lib/array-utils.morph**
```javascript
export function unique(arr) {
    distinct(arr)
}

export function sortDesc(arr) {
    reverse(sorted(arr))
}

export function take(arr, n) {
    limit(arr, n)
}

export function drop(arr, n) {
    skip(arr, n)
}

export function chunk(arr, size) {
    // Split array into chunks of specified size
    variable result = [];
    variable index = 0;
    
    for (item of arr) {
        if (index % size == 0) {
            result = concat(result, [[item]])
        } else {
            variable lastChunk = result[len(result) - 1];
            result[len(result) - 1] = concat(lastChunk, [item])
        }
        index = index + 1
    }
    
    result
}
```

**File: main.morph**
```javascript
import * as arrays from "lib/array-utils.morph";

{
    unique: arrays.unique([1, 2, 2, 3, 3, 3]),
    topThree: arrays.take(arrays.sortDesc([5, 2, 8, 1, 9]), 3),
    chunks: arrays.chunk([1, 2, 3, 4, 5, 6, 7], 3)
}
```

**Output:**
```json
{
    "unique": [1, 2, 3],
    "topThree": [9, 8, 5],
    "chunks": [[1, 2, 3], [4, 5, 6], [7]]
}
```

---

### Example 7: Domain-Specific Module

**File: domains/ecommerce.morph**
```javascript
export function calculateDiscount(price, discountPercent) {
    price * (1 - discountPercent)
}

export function applyLoyaltyBonus(price, loyaltyTier) {
    switch (loyaltyTier) {
        case "gold": price * 0.85
        case "silver": price * 0.90
        case "bronze": price * 0.95
        default: price
    }
}

export function calculateShipping(weight, distance) {
    variable baseRate = 5.0;
    variable weightRate = 0.5 * weight;
    variable distanceRate = 0.01 * distance;
    baseRate + weightRate + distanceRate
}

export function enrichProduct(product, userTier) {
    {
        id: product.id,
        name: product.name,
        originalPrice: product.price,
        discountedPrice: calculateDiscount(product.price, product.discount),
        finalPrice: applyLoyaltyBonus(
            calculateDiscount(product.price, product.discount),
            userTier
        ),
        inStock: product.quantity > 0
    }
}
```

**File: product-catalog.morph**
```javascript
import { enrichProduct } from "domains/ecommerce.morph";

{
    products: map($.products, "p", enrichProduct(p, $.user.loyaltyTier))
}
```

---

## Module Organization Patterns

### Pattern 1: Feature-Based Structure

```
modules/
├── user/
│   ├── validators.morph
│   ├── transformers.morph
│   └── formatters.morph
├── order/
│   ├── calculations.morph
│   └── enrichers.morph
└── shared/
    ├── constants.morph
    └── utils.morph
```

---

### Pattern 2: Layer-Based Structure

```
modules/
├── core/
│   ├── types.morph
│   └── constants.morph
├── utils/
│   ├── arrays.morph
│   ├── strings.morph
│   └── objects.morph
├── business/
│   ├── pricing.morph
│   └── inventory.morph
└── transformers/
    ├── user.morph
    └── order.morph
```

---

## Best Practices

### ✅ Do's

1. **Keep modules focused** - Each module should have a single responsibility
2. **Use clear names** - Module names should describe their purpose
3. **Export only public API** - Don't export internal helpers
4. **Document exports** - Add comments explaining what each export does
5. **Use relative paths** - Keep imports maintainable
6. **Version your modules** - Track changes to shared modules

### ❌ Don'ts

1. **Don't create circular dependencies** - Module A imports B, B imports A
2. **Don't export everything** - Keep implementation details private
3. **Don't use absolute paths** - They break portability
4. **Don't duplicate code** - Extract shared logic to modules
5. **Don't create god modules** - Split large modules into smaller ones

---

## Common Patterns

### Pattern 1: Barrel Exports

**File: utils/index.morph**
```javascript
import * as strings from "./strings.morph";
import * as arrays from "./arrays.morph";
import * as objects from "./objects.morph";

export * from "./strings.morph";
export * from "./arrays.morph";
export * from "./objects.morph";
```

**Usage:**
```javascript
import { capitalize, unique, merge } from "utils/index.morph";
```

---

### Pattern 2: Configuration Module

**File: config.morph**
```javascript
export variable config = {
    api: {
        baseUrl: "https://api.example.com",
        timeout: 5000
    },
    features: {
        enableCache: true,
        enableLogging: true
    },
    limits: {
        maxPageSize: 100,
        maxRetries: 3
    }
};
```

---

### Pattern 3: Factory Functions

**File: factories/user.morph**
```javascript
export function createUser(data) {
    {
        id: data.id,
        name: data.name,
        email: lower(data.email),
        createdAt: now(),
        role: exists(data.role) ? data.role : "user"
    }
}

export function createAdmin(data) {
    merge(createUser(data), {role: "admin", permissions: ["all"]})
}
```

---

## Module Resolution

### Resolution Order

1. **Relative paths** - `"./module.morph"` or `"../module.morph"`
2. **Module paths** - `"lib/module.morph"`
3. **Standard library** - Built-in modules

### Example:

```javascript
// Relative import
import { func1 } from "./utils.morph";

// Module import
import { func2 } from "lib/helpers.morph";

// Parent directory
import { func3 } from "../shared/common.morph";
```

---

## Practical Example: Complete Module System

**File: lib/validators.morph**
```javascript
export function required(value) {
    exists(value) && value != ""
}

export function email(value) {
    required(value) && len(value) > 5 && exists(split(value, "@")[1])
}

export function minLength(value, min) {
    required(value) && len(value) >= min
}

export function maxLength(value, max) {
    required(value) && len(value) <= max
}
```

**File: lib/transformers.morph**
```javascript
import { required, email, minLength } from "./validators.morph";

export function validateAndTransformUser(user) {
    {
        id: user.id,
        name: user.name,
        email: user.email,
        valid: required(user.name) && email(user.email) && minLength(user.name, 2),
        errors: [
            if (!required(user.name)) { "Name is required" } else { null },
            if (!email(user.email)) { "Invalid email" } else { null },
            if (!minLength(user.name, 2)) { "Name too short" } else { null }
        ] | filter("e", e != null)
    }
}
```

**File: main.morph**
```javascript
import { validateAndTransformUser } from "lib/transformers.morph";

{
    users: map($.users, "u", validateAndTransformUser(u))
}
```

---

## Performance Considerations

1. **Module caching** - Modules are parsed once and cached
2. **Import cost** - Import overhead is minimal
3. **Tree shaking** - Only imported functions are loaded
4. **Lazy loading** - Use dynamic imports for conditional loading

---

## Related Topics

- [User-Defined Functions](13-user-functions.md) - Create functions to export
- [Dynamic Imports](15-dynamic-imports.md) - Load modules at runtime
- [Performance Tips](18-performance.md) - Optimize module usage
- [Java API](19-java-api.md) - Use modules from Java

---

## Summary

The Morphium module system provides:
- ✅ Code organization and reusability
- ✅ Namespace management
- ✅ Easy sharing of utilities
- ✅ Maintainable codebase structure
- ✅ Support for complex projects

---

[← Back to Documentation](README.md)
