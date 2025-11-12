# filter()

Select elements from an array that satisfy a condition.

---

## Syntax

```javascript
filter(array, "itemName", condition)
```

### Parameters
- **array**: The input array to filter
- **itemName**: Variable name to reference each element (string)
- **condition**: Boolean expression to test each element

### Returns
A new array containing only elements where condition is true.

---

## Description

The `filter()` function tests each element of an array against a condition and returns a new array containing only the elements that pass the test. The original array is not modified.

---

## Basic Examples

### Example 1: Filter Numbers
```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
}

// Script
{
  evens: filter($.numbers, "n", n % 2 == 0),
  greaterThanFive: filter($.numbers, "n", n > 5)
}

// Output
{
  "evens": [2, 4, 6, 8, 10],
  "greaterThanFive": [6, 7, 8, 9, 10]
}
```

### Example 2: Filter Objects by Field
```javascript
// Input
{
  "users": [
    {"name": "Alice", "age": 25, "active": true},
    {"name": "Bob", "age": 30, "active": false},
    {"name": "Charlie", "age": 35, "active": true},
    {"name": "David", "age": 28, "active": false}
  ]
}

// Script
{
  activeUsers: filter($.users, "u", u.active),
  adults: filter($.users, "u", u.age >= 30)
}

// Output
{
  "activeUsers": [
    {"name": "Alice", "age": 25, "active": true},
    {"name": "Charlie", "age": 35, "active": true}
  ],
  "adults": [
    {"name": "Bob", "age": 30, "active": false},
    {"name": "Charlie", "age": 35, "active": true}
  ]
}
```

### Example 3: Multiple Conditions
```javascript
// Input
{
  "products": [
    {"name": "Laptop", "price": 1000, "inStock": true},
    {"name": "Mouse", "price": 25, "inStock": false},
    {"name": "Keyboard", "price": 75, "inStock": true},
    {"name": "Monitor", "price": 300, "inStock": true}
  ]
}

// Script
{
  available: filter($.products, "p", p.inStock && p.price < 500)
}

// Output
{
  "available": [
    {"name": "Keyboard", "price": 75, "inStock": true},
    {"name": "Monitor", "price": 300, "inStock": true}
  ]
}
```

---

## Advanced Examples

### Example 4: Filter with Nested Field Access
```javascript
// Input
{
  "orders": [
    {"id": 1, "customer": {"type": "premium"}, "amount": 100},
    {"id": 2, "customer": {"type": "regular"}, "amount": 50},
    {"id": 3, "customer": {"type": "premium"}, "amount": 200}
  ]
}

// Script
{
  premiumOrders: filter($.orders, "o", o.customer.type == 'premium'),
  largeOrders: filter($.orders, "o", o.amount > 75)
}

// Output
{
  "premiumOrders": [
    {"id": 1, "customer": {"type": "premium"}, "amount": 100},
    {"id": 3, "customer": {"type": "premium"}, "amount": 200}
  ],
  "largeOrders": [
    {"id": 1, "customer": {"type": "premium"}, "amount": 100},
    {"id": 3, "customer": {"type": "premium"}, "amount": 200}
  ]
}
```

### Example 5: Complex Conditions
```javascript
// Input
{
  "employees": [
    {"name": "Alice", "dept": "Engineering", "salary": 80000, "experience": 5},
    {"name": "Bob", "dept": "Sales", "salary": 60000, "experience": 3},
    {"name": "Charlie", "dept": "Engineering", "salary": 95000, "experience": 8},
    {"name": "David", "dept": "Marketing", "salary": 70000, "experience": 4}
  ]
}

// Script
{
  seniorEngineers: filter(
    $.employees,
    "e",
    e.dept == 'Engineering' && e.experience >= 5
  ),
  highEarners: filter(
    $.employees,
    "e",
    e.salary >= 80000 || e.experience >= 7
  )
}

// Output
{
  "seniorEngineers": [
    {"name": "Alice", "dept": "Engineering", "salary": 80000, "experience": 5},
    {"name": "Charlie", "dept": "Engineering", "salary": 95000, "experience": 8}
  ],
  "highEarners": [
    {"name": "Alice", "dept": "Engineering", "salary": 80000, "experience": 5},
    {"name": "Charlie", "dept": "Engineering", "salary": 95000, "experience": 8}
  ]
}
```

### Example 6: Filter with String Matching
```javascript
// Input
{
  "items": [
    {"name": "Apple iPhone", "category": "Electronics"},
    {"name": "Samsung TV", "category": "Electronics"},
    {"name": "Apple Watch", "category": "Wearables"},
    {"name": "Dell Laptop", "category": "Electronics"}
  ]
}

// Script - Using startsWith simulation
{
  appleProducts: filter(
    $.items,
    "item",
    item.name >= 'Apple' && item.name < 'Applf'
  ),
  electronics: filter(
    $.items,
    "item",
    item.category == 'Electronics'
  )
}
```

### Example 7: Chaining Filter and Map
```javascript
// Input
{
  "transactions": [
    {"id": 1, "type": "debit", "amount": 100},
    {"id": 2, "type": "credit", "amount": 50},
    {"id": 3, "type": "debit", "amount": 75},
    {"id": 4, "type": "credit", "amount": 200}
  ]
}

// Script
{
  debitAmounts: map(
    filter($.transactions, "t", t.type == 'debit'),
    "t",
    t.amount
  ),
  totalDebits: sum(map(
    filter($.transactions, "t", t.type == 'debit'),
    "t",
    t.amount
  ))
}

// Output
{
  "debitAmounts": [100, 75],
  "totalDebits": 175
}
```

---

## Common Use Cases

### 1. Active/Inactive Filtering
```javascript
filter($.users, "u", u.active)
filter($.users, "u", !u.deleted)
```

### 2. Range Filtering
```javascript
filter($.products, "p", p.price >= 10 && p.price <= 100)
filter($.users, "u", u.age >= 18 && u.age < 65)
```

### 3. Status Filtering
```javascript
filter($.orders, "o", o.status == 'completed' || o.status == 'shipped')
```

### 4. Existence Checks
```javascript
filter($.items, "i", exists(i.optional field))
filter($.records, "r", r.email != null)
```

### 5. Type-Based Filtering
```javascript
filter($.entries, "e", e.value > 0)  // Filters positive numbers
```

---

## Best Practices

### ✅ Do's
- **Filter Early**: Apply filter before map to reduce processing
- **Simple Conditions**: Keep conditions readable
- **Use Boolean Logic**: Combine conditions with `&&`, `||`
- **Handle Nulls**: Use `exists()` or null checks

### ❌ Don'ts
- **Don't Modify Elements**: Filter only tests, doesn't transform
- **Avoid Complex Logic**: Use multiple filters instead
- **Don't Filter Twice**: Store results in variable

---

## Performance Tips

1. **Order Matters**: Apply most restrictive filter first
```javascript
// Good - filters most data first
let active = filter($.users, "u", u.active);
let adults = filter(active, "u", u.age >= 18);

// Less efficient
let adults = filter($.users, "u", u.age >= 18);
let activeAdults = filter(adults, "u", u.active);
```

2. **Combine Conditions**: Use single filter with `&&`
```javascript
// Better
filter($.items, "i", i.active && i.price > 10)

// Less efficient
filter(filter($.items, "i", i.active), "i", i.price > 10)
```

3. **Use Variables**: Store filtered results
```javascript
let filtered = filter($.data, "d", d.active);
{
  count: count(filtered),
  sum: sum(map(filtered, "d", d.value))
}
```

---

## Edge Cases

### Empty Array
```javascript
filter([], "x", x > 0)  // Returns: []
```

### All Match
```javascript
filter([1, 2, 3], "x", x > 0)  // Returns: [1, 2, 3]
```

### None Match
```javascript
filter([1, 2, 3], "x", x > 10)  // Returns: []
```

### Null Values
```javascript
// Input: [{"value": 1}, {"value": null}, {"value": 3}]
filter($.items, "i", i.value > 0)
// Output: [{"value": 1}, {"value": 3}]
```

---

## Related Functions

- [**map()**](map.md) - Transform array elements
- [**anyMatch()**](anyMatch.md) - Check if any element matches
- [**allMatch()**](allMatch.md) - Check if all elements match
- [**partition()**](partition.md) - Split array into two groups
- [**findFirst()**](findFirst.md) - Find first matching element

---

## Alternative Approaches

### Using For-Of with Continue
```javascript
// Instead of filter + map
for (item of $.items) {
  !item.active ? continue : null;
  { name: item.name, value: item.value }
}
```

### Using If-Else
```javascript
for (item of $.items) {
  if (item.active) {
    { name: item.name }
  }
}
```

---

**Category:** Array Functions  
**Since:** 1.0.0  
**Complexity:** O(n) where n is array length
