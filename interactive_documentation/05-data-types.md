# Data Types

Understanding the type system in Morphium DSL.

---

## Overview

Morphium DSL is built on top of JSON and supports all JSON data types plus additional runtime types. The type system is dynamic - variables don't have fixed types and can change during execution.

---

## Core Data Types

### 1. Null

Represents the absence of a value.

```javascript
let empty = null;

// Output: null
```

**Usage:**
- Default value for undefined variables
- Representing missing data
- Conditional checks

```javascript
let value = $.optionalField;
if (value == null) {
  { status: 'missing' }
} else {
  { status: 'present', value: value }
}
```

---

### 2. Boolean

True or false values.

```javascript
let isActive = true;
let isExpired = false;
```

**Operations:**
```javascript
// Input
{
  "age": 25,
  "hasLicense": true
}

// Script
let canDrive = $.age >= 18 && $.hasLicense;
let needsTest = !$.hasLicense || $.age < 16;

{
  canDrive: canDrive,
  needsTest: needsTest
}

// Output
{
  "canDrive": true,
  "needsTest": false
}
```

**Truthy/Falsy Values:**
- `false`, `null`, `0`, `""` (empty string) are falsy
- Everything else is truthy

```javascript
let values = [true, false, 0, 1, "", "text", null];
{
  results: map(values, "v", if (v) { "truthy" } else { "falsy" })
}

// Output
{
  "results": ["truthy", "falsy", "falsy", "truthy", "falsy", "truthy", "falsy"]
}
```

---

### 3. Number

Numeric values (integers and floating-point).

```javascript
let integer = 42;
let float = 3.14159;
let negative = -10;
let scientific = 1.5e10;
```

**Arithmetic Operations:**
```javascript
// Input
{
  "price": 100,
  "quantity": 5
}

// Script
let price = $.price;
let quantity = $.quantity;
let subtotal = price * quantity;
let tax = subtotal * 0.08;
let total = subtotal + tax;

{
  subtotal: subtotal,
  tax: tax,
  total: total,
  average: total / quantity
}

// Output
{
  "subtotal": 500,
  "tax": 40,
  "total": 540,
  "average": 108
}
```

**Math Operations:**
```javascript
let x = 10;
let y = 3;

{
  sum: x + y,           // 13
  difference: x - y,    // 7
  product: x * y,       // 30
  quotient: x / y,      // 3.333...
  remainder: x % y,     // 1
  power: x ** y         // 1000 (if supported)
}
```

---

### 4. String

Text values enclosed in single or double quotes.

```javascript
let single = 'Hello';
let double = "World";
let withQuotes = 'It\'s a nice day';
```

**String Operations:**
```javascript
// Input
{
  "firstName": "John",
  "lastName": "Doe"
}

// Script
let first = $.firstName;
let last = $.lastName;
let full = first + ' ' + last;
let upper = upper(full);
let lower = lower(full);

{
  fullName: full,
  uppercase: upper,
  lowercase: lower,
  length: len(full),
  initials: first[0] + last[0]
}

// Output
{
  "fullName": "John Doe",
  "uppercase": "JOHN DOE",
  "lowercase": "john doe",
  "length": 8,
  "initials": "JD"
}
```

**String Functions:**
```javascript
let text = "  Hello, World!  ";

{
  original: text,
  trimmed: trim(text),
  upper: upper(text),
  lower: lower(text),
  replaced: replace(text, "World", "Morphium"),
  split: split(trim(text), ", ")
}

// Output
{
  "original": "  Hello, World!  ",
  "trimmed": "Hello, World!",
  "upper": "  HELLO, WORLD!  ",
  "lower": "  hello, world!  ",
  "replaced": "  Hello, Morphium!  ",
  "split": ["Hello", "World!"]
}
```

---

### 5. Array

Ordered collections of values.

```javascript
let numbers = [1, 2, 3, 4, 5];
let mixed = [1, "two", true, null, { key: "value" }];
let nested = [[1, 2], [3, 4], [5, 6]];
```

**Array Operations:**
```javascript
// Input
{
  "items": [10, 20, 30, 40, 50]
}

// Script
let items = $.items;

{
  original: items,
  length: len(items),
  first: items[0],
  last: items[len(items) - 1],
  doubled: map(items, "x", x * 2),
  filtered: filter(items, "x", x > 25),
  sum: sum(items),
  average: avg(items)
}

// Output
{
  "original": [10, 20, 30, 40, 50],
  "length": 5,
  "first": 10,
  "last": 50,
  "doubled": [20, 40, 60, 80, 100],
  "filtered": [30, 40, 50],
  "sum": 150,
  "average": 30
}
```

**Array Construction:**
```javascript
// Empty array
let empty = [];

// Array literal
let colors = ["red", "green", "blue"];

// Array from mapping
let squares = map([1, 2, 3, 4], "x", x * x);

// Array from filtering
let evens = filter([1, 2, 3, 4, 5, 6], "x", x % 2 == 0);

// Nested arrays
let matrix = [
  [1, 2, 3],
  [4, 5, 6],
  [7, 8, 9]
];
```

---

### 6. Object

Key-value collections (JSON objects).

```javascript
let person = {
  name: "Alice",
  age: 30,
  city: "New York"
};
```

**Object Operations:**
```javascript
// Input
{
  "user": {
    "id": 123,
    "name": "Alice",
    "email": "alice@example.com",
    "address": {
      "city": "New York",
      "country": "USA"
    }
  }
}

// Script
let user = $.user;

{
  id: user.id,
  name: user.name,
  email: user.email,
  location: user.address.city + ", " + user.address.country,
  keys: keys(user),
  values: values(user)
}

// Output
{
  "id": 123,
  "name": "Alice",
  "email": "alice@example.com",
  "location": "New York, USA",
  "keys": ["id", "name", "email", "address"],
  "values": [123, "Alice", "alice@example.com", {...}]
}
```

**Object Construction:**
```javascript
// Object literal
let config = {
  host: "localhost",
  port: 8080,
  ssl: true
};

// Dynamic keys from variables
let key = "userName";
let value = "alice";
{
  [key]: value  // Results in { "userName": "alice" }
}

// Nested objects
let data = {
  user: {
    profile: {
      name: "Alice",
      age: 30
    }
  }
};

// Merging objects
let defaults = { theme: "dark", lang: "en" };
let userPrefs = { theme: "light" };
merge(defaults, userPrefs)  // { "theme": "light", "lang": "en" }
```

---

## Type Checking

### Check if Value Exists

```javascript
let value = $.optionalField;

{
  exists: exists(value),
  isNull: value == null,
  hasValue: value != null
}
```

### Type-Specific Checks

```javascript
// Input
{
  "data": [1, "text", true, null, [1, 2], { "key": "value" }]
}

// Script
{
  types: for (item of $.data) {
    if (item == null) {
      "null"
    } else if (item == true || item == false) {
      "boolean"
    } else if (typeof(item) == "array") {
      "array"
    } else if (typeof(item) == "object") {
      "object"
    } else if (typeof(item) == "string") {
      "string"
    } else {
      "number"
    }
  }
}
```

---

## Type Conversion

### To Number

```javascript
let str = "42";
let num = toNumber(str);  // 42

let bool = true;
let numFromBool = toNumber(bool);  // 1

// Invalid conversions return null or error
toNumber("invalid")  // null or error
```

### To String

```javascript
let num = 42;
let str = toString(num);  // "42"

let bool = true;
let strFromBool = toString(bool);  // "true"

let obj = { key: "value" };
let json = jsonStringify(obj);  // '{"key":"value"}'
```

### To Boolean

```javascript
let num = 1;
let bool = toBool(num);  // true

let str = "false";
let boolFromStr = toBool(str);  // true (non-empty string)

let zero = 0;
toBool(zero)  // false
```

### JSON Parsing

```javascript
let jsonString = '{"name":"Alice","age":30}';
let obj = jsonParse(jsonString);

{
  name: obj.name,
  age: obj.age
}

// Output
{
  "name": "Alice",
  "age": 30
}
```

---

## Advanced Examples

### Example 1: Mixed Type Processing

```javascript
// Input
{
  "values": [1, "2", true, null, "hello", 3.14]
}

// Script
{
  numbers: filter(map($.values, "v", toNumber(v)), "n", n != null),
  strings: map($.values, "v", toString(v)),
  booleans: map($.values, "v", toBool(v))
}

// Output
{
  "numbers": [1, 2, 1, 0, 3.14],
  "strings": ["1", "2", "true", "null", "hello", "3.14"],
  "booleans": [true, true, true, false, true, true]
}
```

### Example 2: Complex Object Transformation

```javascript
// Input
{
  "products": [
    {"name": "Widget", "price": "10.99", "inStock": "true"},
    {"name": "Gadget", "price": "25.50", "inStock": "false"}
  ]
}

// Script
{
  products: map($.products, "p", {
    name: p.name,
    price: toNumber(p.price),
    inStock: toBool(p.inStock),
    displayPrice: "$" + p.price
  })
}

// Output
{
  "products": [
    {"name": "Widget", "price": 10.99, "inStock": true, "displayPrice": "$10.99"},
    {"name": "Gadget", "price": 25.50, "inStock": false, "displayPrice": "$25.50"}
  ]
}
```

### Example 3: Type-Safe Operations

```javascript
// Input
{
  "data": [
    {"value": 10},
    {"value": "20"},
    {"value": null},
    {"value": "30"}
  ]
}

// Script
let values = map($.data, "d", d.value);
let numbers = filter(map(values, "v", toNumber(v)), "n", n != null);

{
  allValues: values,
  validNumbers: numbers,
  sum: sum(numbers),
  count: count(numbers)
}

// Output
{
  "allValues": [10, "20", null, "30"],
  "validNumbers": [10, 20, 30],
  "sum": 60,
  "count": 3
}
```

---

## Common Patterns

### Pattern 1: Safe Navigation with Null Checks

```javascript
let user = $.user;
let email = if (user != null && user.contact != null) {
  user.contact.email
} else {
  "no-email@example.com"
};
```

### Pattern 2: Default Values

```javascript
let config = $.config;
{
  host: config.host ?? "localhost",
  port: config.port ?? 8080,
  ssl: config.ssl ?? true
}
```

### Pattern 3: Type Coercion

```javascript
let input = $.userInput;
let safeNumber = if (toNumber(input) != null) {
  toNumber(input)
} else {
  0
};
```

---

## Best Practices

### ✅ Do:
- Use type conversion functions when mixing types
- Check for null before accessing nested properties
- Use appropriate types for your data
- Convert user input to expected types early

### ❌ Don't:
- Don't assume types without checking
- Don't perform arithmetic on strings without conversion
- Don't ignore null values in calculations
- Don't mix incompatible types without explicit conversion

---

## Related Topics

- [Variables and Scope](04-variables-scope.md) - Variable declaration
- [Operators](06-operators.md) - Type-specific operations
- [Type Conversion Functions](functions/toNumber.md) - Conversion utilities
- [JSON Path Access](07-json-path.md) - Accessing typed data

---

**See Also:**
- [Quick Start](01-quick-start.md)
- [Basic Concepts](02-basic-concepts.md)
- [Syntax Overview](03-syntax-overview.md)
