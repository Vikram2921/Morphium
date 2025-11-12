# indexBy() - Create Object Indexed by Field

## Syntax
```javascript
indexBy(array, varName, keyExpression, valueExpression)
```

## Description
Transforms an array into an object where keys are derived from a field or expression, and values are the corresponding elements or transformed values.

## Parameters
- `array` - Source array
- `varName` - Variable name for iteration (string)
- `keyExpression` - Expression to compute the key (unevaluated)
- `valueExpression` - Expression to compute the value (unevaluated)

## Returns
Object indexed by the key expression with values from value expression.

## Examples

### Index by ID
```javascript
// Input
{
  "users": [
    {"id": 1, "name": "Alice", "email": "alice@example.com"},
    {"id": 2, "name": "Bob", "email": "bob@example.com"},
    {"id": 3, "name": "Charlie", "email": "charlie@example.com"}
  ]
}

// Morph
{
  usersById: indexBy($.users, "u", u.id, u)
}

// Output
{
  "usersById": {
    "1": {"id": 1, "name": "Alice", "email": "alice@example.com"},
    "2": {"id": 2, "name": "Bob", "email": "bob@example.com"},
    "3": {"id": 3, "name": "Charlie", "email": "charlie@example.com"}
  }
}
```

### Index by Name with Custom Value
```javascript
// Input
{
  "products": [
    {"sku": "LAP001", "name": "Laptop", "price": 999},
    {"sku": "MOU001", "name": "Mouse", "price": 25},
    {"sku": "KEY001", "name": "Keyboard", "price": 75}
  ]
}

// Morph
{
  pricesBySku: indexBy($.products, "p", p.sku, p.price)
}

// Output
{
  "pricesBySku": {
    "LAP001": 999,
    "MOU001": 25,
    "KEY001": 75
  }
}
```

### Create Lookup Table
```javascript
// Input
{
  "employees": [
    {"empId": "E001", "name": "Alice", "dept": "Engineering", "salary": 90000},
    {"empId": "E002", "name": "Bob", "dept": "Sales", "salary": 75000},
    {"empId": "E003", "name": "Charlie", "dept": "Engineering", "salary": 95000}
  ]
}

// Morph
{
  empLookup: indexBy($.employees, "e", e.empId, {
    name: e.name,
    department: e.dept,
    salary: e.salary
  })
}

// Output
{
  "empLookup": {
    "E001": {"name": "Alice", "department": "Engineering", "salary": 90000},
    "E002": {"name": "Bob", "department": "Sales", "salary": 75000},
    "E003": {"name": "Charlie", "department": "Engineering", "salary": 95000}
  }
}
```

### Index by Computed Key
```javascript
// Input
{
  "users": [
    {"firstName": "Alice", "lastName": "Smith", "age": 25},
    {"firstName": "Bob", "lastName": "Jones", "age": 30}
  ]
}

// Morph
{
  usersByFullName: indexBy(
    $.users,
    "u",
    u.firstName + " " + u.lastName,
    u.age
  )
}

// Output
{
  "usersByFullName": {
    "Alice Smith": 25,
    "Bob Jones": 30
  }
}
```

### Email to Name Mapping
```javascript
// Input
{
  "contacts": [
    {"name": "Alice", "email": "alice@example.com", "phone": "555-0001"},
    {"name": "Bob", "email": "bob@example.com", "phone": "555-0002"},
    {"name": "Charlie", "email": "charlie@example.com", "phone": "555-0003"}
  ]
}

// Morph
{
  nameByEmail: indexBy($.contacts, "c", c.email, c.name)
}

// Output
{
  "nameByEmail": {
    "alice@example.com": "Alice",
    "bob@example.com": "Bob",
    "charlie@example.com": "Charlie"
  }
}
```

### Complex Transformation
```javascript
// Input
{
  "orders": [
    {"orderId": "ORD001", "customerId": "C001", "total": 100, "items": 3},
    {"orderId": "ORD002", "customerId": "C002", "total": 200, "items": 5}
  ]
}

// Morph
{
  orderDetails: indexBy($.orders, "o", o.orderId, {
    customer: o.customerId,
    total: o.total,
    avgItemPrice: o.total / o.items
  })
}

// Output
{
  "orderDetails": {
    "ORD001": {"customer": "C001", "total": 100, "avgItemPrice": 33.33},
    "ORD002": {"customer": "C002", "total": 200, "avgItemPrice": 40}
  }
}
```

## Common Use Cases

1. **Fast Lookups**: Convert arrays to objects for O(1) access
2. **Data Denormalization**: Create lookup tables
3. **ID Mapping**: Map IDs to objects or values
4. **Cache Building**: Build in-memory caches

## Tips and Best Practices

- Keys are converted to strings in JSON
- If duplicate keys exist, last value wins
- Useful for improving lookup performance
- Combine with get() for safe nested access

## Related Functions

- [groupBy()](groupBy.md) - Group multiple items by key
- [pluck()](pluck.md) - Extract single field
- [get()](get.md) - Get values from indexed objects
- [keys()](keys.md) - Get all keys from result

## Performance Notes

- O(n) to build index
- O(1) for subsequent lookups
- Efficient for large datasets with frequent lookups

---

[← Back to Functions](../README.md#object-functions)
