# partition() - Split Array into Two Groups

## Syntax
```javascript
partition(array, varName, predicateExpression)
```

## Description
Splits an array into two groups based on a predicate: one group where the predicate is true, and another where it's false.

## Parameters
- `array` - Source array
- `varName` - Variable name for iteration (string)
- `predicateExpression` - Boolean expression to test each element (unevaluated)

## Returns
Object with two keys: `"true"` (matching elements) and `"false"` (non-matching elements).

## Examples

### Partition by Price
```javascript
// Input
{
  "products": [
    {"name": "Laptop", "price": 999},
    {"name": "Mouse", "price": 25},
    {"name": "Keyboard", "price": 75},
    {"name": "Monitor", "price": 350}
  ]
}

// Morph
{
  partitioned: partition($.products, "p", p.price > 100)
}

// Output
{
  "partitioned": {
    "true": [
      {"name": "Laptop", "price": 999},
      {"name": "Monitor", "price": 350}
    ],
    "false": [
      {"name": "Mouse", "price": 25},
      {"name": "Keyboard", "price": 75}
    ]
  }
}
```

### Active vs Inactive Users
```javascript
// Input
{
  "users": [
    {"name": "Alice", "active": true, "lastLogin": "2025-01-10"},
    {"name": "Bob", "active": false, "lastLogin": "2024-06-15"},
    {"name": "Charlie", "active": true, "lastLogin": "2025-01-11"}
  ]
}

// Morph
{
  users: partition($.users, "u", u.active)
}

// Output
{
  "users": {
    "true": [
      {"name": "Alice", "active": true, "lastLogin": "2025-01-10"},
      {"name": "Charlie", "active": true, "lastLogin": "2025-01-11"}
    ],
    "false": [
      {"name": "Bob", "active": false, "lastLogin": "2024-06-15"}
    ]
  }
}
```

### Pass/Fail Students
```javascript
// Input
{
  "students": [
    {"name": "Alice", "score": 85},
    {"name": "Bob", "score": 55},
    {"name": "Charlie", "score": 92},
    {"name": "Diana", "score": 48}
  ]
}

// Morph
{
  results: partition($.students, "s", s.score >= 60),
  passCount: count(filter($.students, "s", s.score >= 60)),
  failCount: count(filter($.students, "s", s.score < 60))
}

// Output
{
  "results": {
    "true": [
      {"name": "Alice", "score": 85},
      {"name": "Charlie", "score": 92}
    ],
    "false": [
      {"name": "Bob", "score": 55},
      {"name": "Diana", "score": 48}
    ]
  },
  "passCount": 2,
  "failCount": 2
}
```

### Positive vs Negative Numbers
```javascript
// Input
{
  "numbers": [10, -5, 23, -8, 0, 15, -3]
}

// Morph
{
  partitioned: partition($.numbers, "n", n >= 0)
}

// Output
{
  "partitioned": {
    "true": [10, 23, 0, 15],
    "false": [-5, -8, -3]
  }
}
```

### Orders: Completed vs Pending
```javascript
// Input
{
  "orders": [
    {"id": 1, "status": "completed", "amount": 100},
    {"id": 2, "status": "pending", "amount": 150},
    {"id": 3, "status": "completed", "amount": 200},
    {"id": 4, "status": "pending", "amount": 75}
  ]
}

// Morph
{
  orderGroups: partition($.orders, "o", o.status == "completed"),
  completedTotal: sum(map(filter($.orders, "o", o.status == "completed"), "o", o.amount)),
  pendingTotal: sum(map(filter($.orders, "o", o.status == "pending"), "o", o.amount))
}

// Output
{
  "orderGroups": {
    "true": [
      {"id": 1, "status": "completed", "amount": 100},
      {"id": 3, "status": "completed", "amount": 200}
    ],
    "false": [
      {"id": 2, "status": "pending", "amount": 150},
      {"id": 4, "status": "pending", "amount": 75}
    ]
  },
  "completedTotal": 300,
  "pendingTotal": 225
}
```

### In Stock vs Out of Stock
```javascript
// Input
{
  "inventory": [
    {"item": "Laptop", "quantity": 5},
    {"item": "Mouse", "quantity": 0},
    {"item": "Keyboard", "quantity": 12},
    {"item": "Monitor", "quantity": 0}
  ]
}

// Morph
{
  stock: partition($.inventory, "i", i.quantity > 0)
}

// Output
{
  "stock": {
    "true": [
      {"item": "Laptop", "quantity": 5},
      {"item": "Keyboard", "quantity": 12}
    ],
    "false": [
      {"item": "Mouse", "quantity": 0},
      {"item": "Monitor", "quantity": 0}
    ]
  }
}
```

## Common Use Cases

1. **Binary Classification**: Split data into two categories
2. **Status Segregation**: Separate active/inactive, pass/fail, etc.
3. **Threshold Splitting**: Divide by numeric thresholds
4. **Validation**: Separate valid vs invalid records

## Tips and Best Practices

- Returns object with "true" and "false" keys (as strings)
- Both groups are always present (empty arrays if no matches)
- More efficient than running filter twice
- Use destructuring to access groups easily

## Related Functions

- [groupBy()](groupBy.md) - Group into multiple categories
- [filter()](filter.md) - Get only matching elements
- [anyMatch()](anyMatch.md) - Check if any element matches
- [allMatch()](allMatch.md) - Check if all elements match

## Performance Notes

- O(n) where n is array length
- Single pass through array (more efficient than two filters)
- Memory proportional to array size

---

[← Back to Functions](../README.md#object-functions)
