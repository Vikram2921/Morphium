# pluck() - Extract Field from Objects

## Syntax
```javascript
pluck(array, fieldName)
```

## Description
Extracts a specific field from each object in an array, returning an array of just those field values.

## Parameters
- `array` - Array of objects
- `fieldName` - Name of the field to extract (string)

## Returns
Array containing the value of the specified field from each object.

## Examples

### Basic Field Extraction
```javascript
// Input
{
  "users": [
    {"name": "Alice", "age": 25},
    {"name": "Bob", "age": 30},
    {"name": "Charlie", "age": 35}
  ]
}

// Morph
{
  names: pluck($.users, "name")
}

// Output
{
  "names": ["Alice", "Bob", "Charlie"]
}
```

### Extract Nested Fields
```javascript
// Input
{
  "orders": [
    {"id": 1, "customer": {"name": "Alice", "email": "alice@example.com"}},
    {"id": 2, "customer": {"name": "Bob", "email": "bob@example.com"}}
  ]
}

// Morph
{
  customerNames: pluck($.orders, "customer.name")
}

// Output
{
  "customerNames": ["Alice", "Bob"]
}
```

### Extract IDs
```javascript
// Input
{
  "products": [
    {"id": 101, "name": "Laptop", "price": 999},
    {"id": 102, "name": "Mouse", "price": 25},
    {"id": 103, "name": "Keyboard", "price": 75}
  ]
}

// Morph
{
  productIds: pluck($.products, "id"),
  prices: pluck($.products, "price")
}

// Output
{
  "productIds": [101, 102, 103],
  "prices": [999, 25, 75]
}
```

### With Missing Fields
```javascript
// Input
{
  "items": [
    {"name": "Item1", "price": 10},
    {"name": "Item2"},
    {"name": "Item3", "price": 30}
  ]
}

// Morph
{
  prices: pluck($.items, "price")
}

// Output (null for missing fields)
{
  "prices": [10, null, 30]
}
```

### Combined with Other Functions
```javascript
// Input
{
  "orders": [
    {"id": 1, "amount": 100, "status": "completed"},
    {"id": 2, "amount": 200, "status": "completed"},
    {"id": 3, "amount": 150, "status": "pending"}
  ]
}

// Morph
{
  completedAmounts: pluck(
    filter($.orders, "o", o.status == "completed"),
    "amount"
  ),
  totalCompleted: sum(pluck(
    filter($.orders, "o", o.status == "completed"),
    "amount"
  ))
}

// Output
{
  "completedAmounts": [100, 200],
  "totalCompleted": 300
}
```

### Email List from Users
```javascript
// Input
{
  "team": [
    {"name": "Alice", "email": "alice@company.com", "role": "developer"},
    {"name": "Bob", "email": "bob@company.com", "role": "designer"},
    {"name": "Charlie", "email": "charlie@company.com", "role": "manager"}
  ]
}

// Morph
{
  emailList: join(pluck($.team, "email"), ", ")
}

// Output
{
  "emailList": "alice@company.com, bob@company.com, charlie@company.com"
}
```

## Common Use Cases

1. **Data Extraction**: Get specific fields from complex objects
2. **ID Collection**: Extract IDs for further processing
3. **Email Lists**: Create contact lists from user data
4. **Aggregation Prep**: Prepare data for sum, avg, etc.

## Tips and Best Practices

- Returns null for missing fields (doesn't skip them)
- More efficient than map() for simple field extraction
- Works with nested field paths (dot notation)
- Combine with filter() to extract from subset of data

## Related Functions

- [map()](map.md) - Transform array elements with custom logic
- [filter()](filter.md) - Filter arrays before plucking
- [indexBy()](indexBy.md) - Create indexed objects
- [groupBy()](groupBy.md) - Group objects by field value

## Performance Notes

- O(n) where n is array length
- Very efficient for simple field extraction
- Faster than map() for single field extraction

---

[← Back to Functions](../README.md#object-functions)
