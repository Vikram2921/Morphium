# groupBy() - Group Elements by Key

## Syntax
```javascript
groupBy(array, varName, keyExpression, valueExpression)
```

## Description
Groups array elements into an object where each key maps to an array of elements that share that key value.

## Parameters
- `array` - Source array
- `varName` - Variable name for iteration (string)
- `keyExpression` - Expression to compute the grouping key (unevaluated)
- `valueExpression` - Expression to compute the value for each element (unevaluated)

## Returns
Object where each key maps to an array of values sharing that key.

## Examples

### Group by Category
```javascript
// Input
{
  "products": [
    {"name": "Laptop", "category": "electronics", "price": 999},
    {"name": "Shirt", "category": "clothing", "price": 29},
    {"name": "Phone", "category": "electronics", "price": 699},
    {"name": "Pants", "category": "clothing", "price": 49}
  ]
}

// Morph
{
  byCategory: groupBy($.products, "p", p.category, p)
}

// Output
{
  "byCategory": {
    "electronics": [
      {"name": "Laptop", "category": "electronics", "price": 999},
      {"name": "Phone", "category": "electronics", "price": 699}
    ],
    "clothing": [
      {"name": "Shirt", "category": "clothing", "price": 29},
      {"name": "Pants", "category": "clothing", "price": 49}
    ]
  }
}
```

### Group by Status
```javascript
// Input
{
  "orders": [
    {"id": 1, "status": "completed", "amount": 100},
    {"id": 2, "status": "pending", "amount": 150},
    {"id": 3, "status": "completed", "amount": 200},
    {"id": 4, "status": "cancelled", "amount": 75}
  ]
}

// Morph
{
  ordersByStatus: groupBy($.orders, "o", o.status, o)
}

// Output
{
  "ordersByStatus": {
    "completed": [
      {"id": 1, "status": "completed", "amount": 100},
      {"id": 3, "status": "completed", "amount": 200}
    ],
    "pending": [
      {"id": 2, "status": "pending", "amount": 150}
    ],
    "cancelled": [
      {"id": 4, "status": "cancelled", "amount": 75}
    ]
  }
}
```

### Group with Transformed Values
```javascript
// Input
{
  "employees": [
    {"name": "Alice", "dept": "Engineering", "salary": 90000},
    {"name": "Bob", "dept": "Sales", "salary": 75000},
    {"name": "Charlie", "dept": "Engineering", "salary": 95000},
    {"name": "Diana", "dept": "Sales", "salary": 80000}
  ]
}

// Morph
{
  salariesByDept: groupBy($.employees, "e", e.dept, e.salary)
}

// Output
{
  "salariesByDept": {
    "Engineering": [90000, 95000],
    "Sales": [75000, 80000]
  }
}
```

### Group by Computed Key
```javascript
// Input
{
  "students": [
    {"name": "Alice", "score": 95},
    {"name": "Bob", "score": 72},
    {"name": "Charlie", "score": 88},
    {"name": "Diana", "score": 65}
  ]
}

// Morph
{
  byGrade: groupBy($.students, "s", 
    if (s.score >= 90) { "A" } 
    else if (s.score >= 80) { "B" }
    else if (s.score >= 70) { "C" }
    else { "F" },
    s
  )
}

// Output
{
  "byGrade": {
    "A": [{"name": "Alice", "score": 95}],
    "B": [{"name": "Charlie", "score": 88}],
    "C": [{"name": "Bob", "score": 72}],
    "F": [{"name": "Diana", "score": 65}]
  }
}
```

### Group and Aggregate
```javascript
// Input
{
  "sales": [
    {"product": "Laptop", "region": "North", "amount": 1000},
    {"product": "Phone", "region": "North", "amount": 500},
    {"product": "Laptop", "region": "South", "amount": 1200},
    {"product": "Phone", "region": "South", "amount": 600}
  ]
}

// Morph
{
  grouped: groupBy($.sales, "s", s.region, s),
  totals: {
    North: sum(map(filter($.sales, "s", s.region == "North"), "s", s.amount)),
    South: sum(map(filter($.sales, "s", s.region == "South"), "s", s.amount))
  }
}

// Output
{
  "grouped": {
    "North": [
      {"product": "Laptop", "region": "North", "amount": 1000},
      {"product": "Phone", "region": "North", "amount": 500}
    ],
    "South": [
      {"product": "Laptop", "region": "South", "amount": 1200},
      {"product": "Phone", "region": "South", "amount": 600}
    ]
  },
  "totals": {
    "North": 1500,
    "South": 1800
  }
}
```

### Group Names by First Letter
```javascript
// Input
{
  "people": [
    {"name": "Alice"},
    {"name": "Bob"},
    {"name": "Charlie"},
    {"name": "Anna"},
    {"name": "David"}
  ]
}

// Morph
{
  byFirstLetter: groupBy($.people, "p", 
    slice(p.name, 0, 1),
    p.name
  )
}

// Output
{
  "byFirstLetter": {
    "A": ["Alice", "Anna"],
    "B": ["Bob"],
    "C": ["Charlie"],
    "D": ["David"]
  }
}
```

## Common Use Cases

1. **Data Analysis**: Group data for aggregation and reporting
2. **Categorization**: Organize items by category or type
3. **Status Tracking**: Group by status, state, or phase
4. **Report Generation**: Create grouped reports

## Tips and Best Practices

- Each group is an array (even if single element)
- Keys are converted to strings in JSON
- Useful before aggregation operations
- Combine with map/reduce for complex analysis

## Related Functions

- [indexBy()](indexBy.md) - Create single-value index (no arrays)
- [partition()](partition.md) - Split into two groups
- [filter()](filter.md) - Filter before grouping
- [sum()](sum.md), [avg()](avg.md) - Aggregate grouped data

## Performance Notes

- O(n) where n is array length
- Memory proportional to number of groups
- Efficient for small to medium group counts

---

[← Back to Functions](../README.md#object-functions)
