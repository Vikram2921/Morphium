# map()

Transform each element of an array using a transformation expression.

---

## Syntax

```javascript
map(array, "itemName", expression)
```

### Parameters
- **array**: The input array to transform
- **itemName**: Variable name to reference each element (string)
- **expression**: Transformation to apply to each element

### Returns
A new array with transformed elements.

---

## Description

The `map()` function applies a transformation to each element of an array and returns a new array containing the results. The original array is not modified.

Each element is available through the variable name you specify, and you can perform any transformation on it.

---

## Basic Examples

### Example 1: Simple Mapping
```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5]
}

// Script
{
  doubled: map($.numbers, "n", n * 2)
}

// Output
{
  "doubled": [2, 4, 6, 8, 10]
}
```

### Example 2: Extract Field from Objects
```javascript
// Input
{
  "users": [
    {"name": "Alice", "age": 25},
    {"name": "Bob", "age": 30},
    {"name": "Charlie", "age": 35}
  ]
}

// Script
{
  names: map($.users, "user", user.name),
  ages: map($.users, "user", user.age)
}

// Output
{
  "names": ["Alice", "Bob", "Charlie"],
  "ages": [25, 30, 35]
}
```

### Example 3: Transform to New Objects
```javascript
// Input
{
  "products": [
    {"name": "Laptop", "price": 1000},
    {"name": "Mouse", "price": 25}
  ]
}

// Script
{
  items: map($.products, "p", {
    product: p.name,
    cost: p.price,
    tax: p.price * 0.1
  })
}

// Output
{
  "items": [
    {"product": "Laptop", "cost": 1000, "tax": 100},
    {"product": "Mouse", "cost": 25, "tax": 2.5}
  ]
}
```

---

## Advanced Examples

### Example 4: Nested Mapping
```javascript
// Input
{
  "departments": [
    {
      "name": "Engineering",
      "employees": [
        {"name": "Alice", "salary": 80000},
        {"name": "Bob", "salary": 90000}
      ]
    },
    {
      "name": "Sales",
      "employees": [
        {"name": "Charlie", "salary": 70000}
      ]
    }
  ]
}

// Script
{
  summary: map($.departments, "dept", {
    department: dept.name,
    employeeNames: map(dept.employees, "emp", emp.name),
    totalSalary: sum(map(dept.employees, "emp", emp.salary))
  })
}

// Output
{
  "summary": [
    {
      "department": "Engineering",
      "employeeNames": ["Alice", "Bob"],
      "totalSalary": 170000
    },
    {
      "department": "Sales",
      "employeeNames": ["Charlie"],
      "totalSalary": 70000
    }
  ]
}
```

### Example 5: Conditional Mapping
```javascript
// Input
{
  "orders": [
    {"id": 1, "amount": 100, "isPremium": true},
    {"id": 2, "amount": 200, "isPremium": false},
    {"id": 3, "amount": 150, "isPremium": true}
  ]
}

// Script
{
  processed: map($.orders, "order", {
    id: order.id,
    total: if (order.isPremium) {
      order.amount * 0.9  // 10% discount for premium
    } else {
      order.amount
    }
  })
}

// Output
{
  "processed": [
    {"id": 1, "total": 90},
    {"id": 2, "total": 200},
    {"id": 3, "total": 135}
  ]
}
```

### Example 6: Map with Index (using variable scope)
```javascript
// Input
{
  "items": ["apple", "banana", "cherry"]
}

// Script - Use for-in for indexed mapping
{
  indexed: for (i in $.items) {
    {
      index: i,
      value: $.items[i],
      label: $.items[i] + ' #' + toString(i + 1)
    }
  }
}

// Output
{
  "indexed": [
    {"index": 0, "value": "apple", "label": "apple #1"},
    {"index": 1, "value": "banana", "label": "banana #2"},
    {"index": 2, "value": "cherry", "label": "cherry #3"}
  ]
}
```

### Example 7: Chaining with Filter
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
  activeUserNames: map(
    filter($.users, "u", u.active),
    "u",
    u.name
  )
}

// Output
{
  "activeUserNames": ["Alice", "Charlie"]
}
```

---

## Common Use Cases

### 1. Data Extraction
Extract specific fields from an array of objects:
```javascript
map($.users, "u", u.email)
```

### 2. Data Enrichment
Add calculated fields to objects:
```javascript
map($.products, "p", {
  name: p.name,
  price: p.price,
  priceWithTax: p.price * 1.1,
  discounted: p.price * 0.9
})
```

### 3. Data Reformatting
Convert data to a different structure:
```javascript
map($.items, "item", {
  id: item.itemId,
  value: item.itemValue,
  timestamp: now()
})
```

### 4. Normalization
Normalize data formats:
```javascript
map($.entries, "e", {
  name: upper(trim(e.name)),
  value: toNumber(e.value)
})
```

---

## Performance Tips

1. **Avoid Nested Maps**: Nested maps can be expensive. Consider using `flatMap()` when appropriate.
2. **Use Simple Expressions**: Complex expressions in map slow down processing.
3. **Filter First**: Apply `filter()` before `map()` to reduce the number of elements to transform.
4. **Reuse Variables**: Store complex expressions in variables:
   ```javascript
   let filtered = filter($.data, "d", d.active);
   {
     names: map(filtered, "d", d.name),
     ages: map(filtered, "d", d.age)
   }
   ```

---

## Edge Cases

### Empty Array
```javascript
map([], "x", x * 2)  // Returns: []
```

### Null Values
```javascript
// Input: [1, null, 3]
map($.numbers, "n", n * 2)  
// Output: [2, null, 6]  // null stays null
```

### Undefined Fields
```javascript
// Input: [{"name": "Alice"}, {"age": 25}]
map($.items, "i", i.name)
// Output: ["Alice", null]
```

---

## Related Functions

- [**filter()**](filter.md) - Filter array elements
- [**flatMap()**](flatMap.md) - Map and flatten nested arrays
- [**forEach()**](forEach.md) - Iterate with side effects
- [**reduce()**](reduce.md) - Reduce array to single value

---

## See Also

- [Array Functions Overview](README.md#array-functions)
- [Control Flow - For-Of Loop](../10-for-of.md)
- [Real-World Examples](../22-real-world-examples.md)

---

**Category:** Array Functions  
**Since:** 1.0.0  
**Complexity:** O(n) where n is array length
