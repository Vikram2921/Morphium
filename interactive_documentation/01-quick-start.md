# Quick Start Guide

Get started with Morphium DSL in 5 minutes!

---

## What is Morphium DSL?

Morphium DSL is a powerful **data transformation language** designed for JSON data manipulation. It combines:
- 🎯 Simple, readable syntax
- 🚀 High performance
- 💪 Rich function library
- 🔧 Easy Java integration

---

## Your First Transformation

### 1. Basic Setup (Java)

```java
import com.morphium.core.MorphiumEngine;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// Create engine
MorphiumEngine engine = new MorphiumEngine();
ObjectMapper mapper = new ObjectMapper();

// Input data
String inputJson = "{\"name\": \"John\", \"age\": 30}";
JsonNode input = mapper.readTree(inputJson);

// Transform
String script = "{ fullName: $.name, years: $.age }";
JsonNode result = engine.transformFromString(script, input);

System.out.println(result);
// Output: {"fullName":"John","years":30}
```

###  2. Simple Field Mapping

```javascript
// Input
{
  "user": {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com"
  }
}

// Script
{
  name: $.user.firstName + ' ' + $.user.lastName,
  contact: $.user.email
}

// Output
{
  "name": "John Doe",
  "contact": "john@example.com"
}
```

### 3. Array Transformation

```javascript
// Input
{
  "products": [
    {"name": "Laptop", "price": 1000},
    {"name": "Mouse", "price": 25},
    {"name": "Keyboard", "price": 75}
  ]
}

// Script
{
  items: map($.products, "p", {
    product: p.name,
    cost: p.price
  })
}

// Output
{
  "items": [
    {"product": "Laptop", "cost": 1000},
    {"product": "Mouse", "cost": 25},
    {"product": "Keyboard", "cost": 75}
  ]
}
```

### 4. Filtering Data

```javascript
// Input
{
  "users": [
    {"name": "Alice", "age": 25, "active": true},
    {"name": "Bob", "age": 30, "active": false},
    {"name": "Charlie", "age": 35, "active": true}
  ]
}

// Script
{
  activeUsers: filter($.users, "u", u.active),
  names: map(
    filter($.users, "u", u.active),
    "u",
    u.name
  )
}

// Output
{
  "activeUsers": [
    {"name": "Alice", "age": 25, "active": true},
    {"name": "Charlie", "age": 35, "active": true}
  ],
  "names": ["Alice", "Charlie"]
}
```

### 5. Using Control Flow

```javascript
// Input
{
  "orders": [
    {"id": 1, "status": "pending", "amount": 100},
    {"id": 2, "status": "shipped", "amount": 200},
    {"id": 3, "status": "delivered", "amount": 150}
  ]
}

// Script
{
  processed: for (order of $.orders) {
    {
      id: order.id,
      amount: order.amount,
      label: if (order.status == 'delivered') {
        'Complete'
      } else if (order.status == 'shipped') {
        'In Transit'
      } else {
        'Processing'
      }
    }
  }
}

// Output
{
  "processed": [
    {"id": 1, "amount": 100, "label": "Processing"},
    {"id": 2, "amount": 200, "label": "In Transit"},
    {"id": 3, "amount": 150, "label": "Complete"}
  ]
}
```

---

## Core Concepts

### 1. **Input Access with `$`**
- `$` refers to the root input object
- `$.field` accesses a field
- `$.nested.field` accesses nested fields
- `$.array[0]` accesses array elements

### 2. **Object Construction with `{}`**
```javascript
{
  field1: value1,
  field2: value2
}
```

### 3. **Arrays with `[]`**
```javascript
[value1, value2, value3]
```

### 4. **Variables with `let`**
```javascript
let username = $.user.name;
let age = $.user.age;
{
  name: username,
  years: age
}
```

### 5. **Functions**
```javascript
// Array functions
map(array, "item", expression)
filter(array, "item", condition)
reduce(array, "acc", "item", initialValue, expression)

// String functions
upper(string)
split(string, delimiter)

// Utility functions
len(array)
exists(value)
```

---

## Common Patterns

### Pattern 1: Extract and Rename Fields
```javascript
{
  id: $.userId,
  fullName: $.firstName + ' ' + $.lastName,
  emailAddress: $.email
}
```

### Pattern 2: Filter and Map
```javascript
{
  activeUserNames: map(
    filter($.users, "u", u.active),
    "u",
    u.name
  )
}
```

### Pattern 3: Aggregate Data
```javascript
{
  totalAmount: sum(map($.orders, "o", o.amount)),
  averageAmount: avg(map($.orders, "o", o.amount)),
  orderCount: count($.orders)
}
```

### Pattern 4: Conditional Fields
```javascript
{
  name: $.name,
  discount: if ($.membershipLevel == 'gold') {
    0.20
  } else if ($.membershipLevel == 'silver') {
    0.10
  } else {
    0
  }
}
```

### Pattern 5: Nested Transformations
```javascript
{
  user: {
    name: $.user.name,
    orders: map($.user.orders, "o", {
      id: o.id,
      total: o.amount * 1.1  // Add 10% tax
    })
  }
}
```

---

## Next Steps

1. **Learn More Functions**: Browse the [Function Reference](README.md#functions)
2. **Master Control Flow**: Read [Control Flow Guide](08-if-else.md)
3. **Write Custom Functions**: Check [User-Defined Functions](13-user-functions.md)
4. **See Real Examples**: Explore [Real-World Examples](22-real-world-examples.md)

---

## Helpful Tips

### ✅ Do's
- Use `$` to access input data
- Chain functions for complex transformations
- Use meaningful variable names
- Comment complex logic

### ❌ Don'ts
- Don't mutate input data (transformations are read-only)
- Don't use undefined variables
- Don't forget to handle null values with `exists()` or `?` operator

---

## Getting Help

- 📖 Read the [complete documentation](README.md)
- 💬 Ask questions in the community forum
- 🐛 Report bugs on GitHub
- 📧 Contact support at support@morphium.com

---

**Next:** [Basic Concepts →](02-basic-concepts.md)
