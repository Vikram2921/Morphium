# Migration Guide

Guide for migrating data transformations from other tools to Morphium DSL.

---

## From JSONata

### Basic Transformations

**JSONata:**
```jsonata
{
    "name": firstName & " " & lastName,
    "email": $lowercase(email)
}
```

**Morphium DSL:**
```javascript
{
    name: $.firstName + " " + $.lastName,
    email: lower($.email)
}
```

---

### Array Operations

**JSONata:**
```jsonata
products[price > 100].{
    "name": name,
    "price": price
}
```

**Morphium DSL:**
```javascript
map(
    filter($.products, "p", p.price > 100),
    "p",
    {name: p.name, price: p.price}
)
```

---

### Aggregations

**JSONata:**
```jsonata
$sum(orders.amount)
```

**Morphium DSL:**
```javascript
sum(map($.orders, "o", o.amount))
```

---

## From JOLT

### Shift Operation

**JOLT:**
```json
{
    "operation": "shift",
    "spec": {
        "user": {
            "name": "userName",
            "email": "userEmail"
        }
    }
}
```

**Morphium DSL:**
```javascript
{
    userName: $.user.name,
    userEmail: $.user.email
}
```

---

### Default Operation

**JOLT:**
```json
{
    "operation": "default",
    "spec": {
        "status": "active",
        "role": "user"
    }
}
```

**Morphium DSL:**
```javascript
{
    status: exists($.status) ? $.status : "active",
    role: exists($.role) ? $.role : "user"
}
```

---

## From JMESPath

### Basic Selection

**JMESPath:**
```
people[?age > `18`].name
```

**Morphium DSL:**
```javascript
map(
    filter($.people, "p", p.age > 18),
    "p",
    p.name
)
```

---

### Pipe Expressions

**JMESPath:**
```
people | sort_by(@, &age) | [0]
```

**Morphium DSL:**
```javascript
sorted($.people, "p", p.age)[0]
```

---

## From JavaScript/Node.js

### Array Methods

**JavaScript:**
```javascript
data.orders
    .filter(o => o.status === 'active')
    .map(o => ({
        id: o.id,
        total: o.amount * 1.1
    }))
```

**Morphium DSL:**
```javascript
map(
    filter($.orders, "o", o.status == "active"),
    "o",
    {id: o.id, total: o.amount * 1.1}
)
```

---

### Reduce

**JavaScript:**
```javascript
data.items.reduce((sum, item) => sum + item.price, 0)
```

**Morphium DSL:**
```javascript
reduce($.items, "item", "sum", sum + item.price, 0)
```

---

## From Python

### List Comprehensions

**Python:**
```python
[user['name'] for user in data['users'] if user['active']]
```

**Morphium DSL:**
```javascript
map(
    filter($.users, "u", u.active),
    "u",
    u.name
)
```

---

### Dictionary Comprehensions

**Python:**
```python
{user['id']: user['name'] for user in data['users']}
```

**Morphium DSL:**
```javascript
indexBy($.users, "u", "id", u.name)
```

---

## From Apache Camel

### Simple Language

**Camel Simple:**
```
${body.user.name} - ${body.user.email}
```

**Morphium DSL:**
```javascript
{
    result: $.user.name + " - " + $.user.email
}
```

---

### Transform

**Camel:**
```java
.transform()
    .groovy("body.items.sum { it.price }")
```

**Morphium DSL:**
```javascript
{
    total: sum(map($.items, "i", i.price))
}
```

---

## Common Migration Patterns

### Pattern 1: Null Safety

**Before (JavaScript):**
```javascript
data.user && data.user.profile && data.user.profile.name || 'Unknown'
```

**After (Morphium DSL):**
```javascript
exists($.user.profile.name) ? $.user.profile.name : "Unknown"
// or
get($, "user.profile.name", "Unknown")
```

---

### Pattern 2: Conditional Logic

**Before (JSONata):**
```jsonata
price > 100 ? price * 0.9 : price
```

**After (Morphium DSL):**
```javascript
if ($.price > 100) { $.price * 0.9 } else { $.price }
// or shorter
$.price > 100 ? $.price * 0.9 : $.price
```

---

### Pattern 3: Array Flattening

**Before (JavaScript):**
```javascript
data.users.flatMap(u => u.orders)
```

**After (Morphium DSL):**
```javascript
flatMap($.users, "u", u.orders)
```

---

### Pattern 4: Grouping

**Before (JavaScript):**
```javascript
data.items.reduce((groups, item) => {
    const key = item.category;
    groups[key] = groups[key] || [];
    groups[key].push(item);
    return groups;
}, {})
```

**After (Morphium DSL):**
```javascript
groupBy($.items, "item", "category", item)
```

---

## Performance Migration Tips

### Tip 1: Avoid Nested Loops

**Before (inefficient):**
```javascript
for (user of $.users) {
    for (order of $.orders) {
        if (order.userId == user.id) {
            // process
        }
    }
}
```

**After (efficient):**
```javascript
variable ordersByUser = indexBy($.orders, "o", "userId", o);
for (user of $.users) {
    variable userOrders = ordersByUser[user.id];
    // process
}
```

---

### Tip 2: Cache Computations

**Before:**
```javascript
{
    avgPrice: avg(map($.items, "i", i.price)),
    aboveAvg: filter($.items, "i", i.price > avg(map($.items, "i", i.price)))
}
```

**After:**
```javascript
variable avgPrice = avg(map($.items, "i", i.price));
{
    avgPrice: avgPrice,
    aboveAvg: filter($.items, "i", i.price > avgPrice)
}
```

---

## Function Name Mapping

| Other Tools | Morphium DSL |
|-------------|--------------|
| `$uppercase()` (JSONata) | `upper()` |
| `$lowercase()` (JSONata) | `lower()` |
| `$substring()` (JSONata) | `slice()` |
| `$contains()` (JSONata) | `exists(split(str, substr)[1])` |
| `_.uniq()` (Lodash) | `distinct()` |
| `_.flatten()` (Lodash) | `flatMap()` |
| `_.groupBy()` (Lodash) | `groupBy()` |
| `_.sortBy()` (Lodash) | `sorted()` |
| `json.loads()` (Python) | `jsonParse()` |
| `json.dumps()` (Python) | `jsonStringify()` |

---

## Migration Checklist

### ✅ Step-by-Step Migration

1. **Identify transformation logic** - Extract from existing code
2. **Map to Morphium functions** - Use function reference
3. **Convert syntax** - Adjust to Morphium DSL syntax
4. **Handle null safety** - Add exists() checks
5. **Test with sample data** - Verify output
6. **Optimize performance** - Cache repeated calculations
7. **Add error handling** - Use error() and logging
8. **Document transformations** - Add comments

---

## Common Gotchas

### Gotcha 1: Array Index Base

Most languages use 0-based indexing, Morphium DSL uses 0-based as well:
```javascript
$.items[0]  // First item
$.items[len($.items) - 1]  // Last item
```

---

### Gotcha 2: Comparison Operators

Use `==` for equality, not `===`:
```javascript
if ($.status == "active") { ... }  // Correct
```

---

### Gotcha 3: Variable Scope

Variables are scoped to their block:
```javascript
{
    variable x = 10;
    inner: {
        variable x = 20;  // Different variable
        result: x  // 20
    },
    outer: x  // 10
}
```

---

## Best Practices for Migration

### ✅ Do's

1. **Start with simple transformations** - Build confidence
2. **Test incrementally** - Don't migrate everything at once
3. **Use variables** - Cache repeated calculations
4. **Add logging** - Debug during migration
5. **Document differences** - Note  migration decisions
6. **Benchmark performance** - Compare before/after
7. **Keep original code** - Until migration is complete

### ❌ Don'ts

1. **Don't assume syntax** - Check documentation
2. **Don't skip testing** - Verify all edge cases
3. **Don't ignore errors** - Handle migration issues
4. **Don't optimize prematurely** - Get it working first
5. **Don't forget null checks** - Handle missing data

---

## Getting Help

If you encounter issues during migration:

1. **Check documentation** - Function reference and examples
2. **Search patterns** - Common patterns guide
3. **Try examples** - Real-world examples
4. **Ask community** - Forums and discussions
5. **File issues** - Report bugs or unclear docs

---

## Related Topics

- [Real-World Examples](22-real-world-examples.md) - Complete examples
- [Common Patterns](23-common-patterns.md) - Reusable patterns
- [Functions Reference](README.md#functions) - All functions
- [Performance Tips](18-performance.md) - Optimization

---

## Summary

Migration to Morphium DSL:
- ✅ Clear syntax mapping from popular tools
- ✅ Step-by-step migration process
- ✅ Performance optimization tips
- ✅ Common gotchas documented
- ✅ Best practices provided
- ✅ Comprehensive function mapping

---

[← Back to Documentation](README.md)
