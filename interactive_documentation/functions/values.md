# values() - Get Object Values

## Syntax
```javascript
values(object)
```

## Description
Returns an array containing all the values from an object's properties.

## Parameters
- `object` - The object to get values from

## Returns
Array containing all values from the object.

## Examples

### Basic Object Values
```javascript
// Input
{
  "user": {
    "name": "Alice",
    "age": 25,
    "email": "alice@example.com"
  }
}

// Morph
{
  userValues: values($.user)
}

// Output
{
  "userValues": ["Alice", 25, "alice@example.com"]
}
```

### Sum Numeric Values
```javascript
// Input
{
  "sales": {
    "january": 1000,
    "february": 1500,
    "march": 1200
  }
}

// Morph
{
  monthlySales: values($.sales),
  totalSales: sum(values($.sales)),
  avgSales: avg(values($.sales))
}

// Output
{
  "monthlySales": [1000, 1500, 1200],
  "totalSales": 3700,
  "avgSales": 1233.33
}
```

### Extract Configuration Values
```javascript
// Input
{
  "settings": {
    "host": "localhost",
    "port": 8080,
    "debug": true,
    "timeout": 30
  }
}

// Morph
{
  configValues: values($.settings),
  valueCount: len(values($.settings))
}

// Output
{
  "configValues": ["localhost", 8080, true, 30],
  "valueCount": 4
}
```

### Process All Values
```javascript
// Input
{
  "scores": {
    "math": 85,
    "english": 92,
    "science": 88,
    "history": 78
  }
}

// Morph
{
  allScores: values($.scores),
  maxScore: max(values($.scores)),
  minScore: min(values($.scores)),
  avgScore: avg(values($.scores))
}

// Output
{
  "allScores": [85, 92, 88, 78],
  "maxScore": 92,
  "minScore": 78,
  "avgScore": 85.75
}
```

### Empty Object
```javascript
// Input
{
  "empty": {}
}

// Morph
{
  emptyValues: values($.empty),
  hasValues: len(values($.empty)) > 0
}

// Output
{
  "emptyValues": [],
  "hasValues": false
}
```

### Nested Object Values
```javascript
// Input
{
  "departments": {
    "engineering": {"count": 50, "budget": 500000},
    "sales": {"count": 30, "budget": 300000},
    "marketing": {"count": 20, "budget": 200000}
  }
}

// Morph
{
  deptData: values($.departments),
  totalEmployees: sum(map(values($.departments), "d", d.count)),
  totalBudget: sum(map(values($.departments), "d", d.budget))
}

// Output
{
  "deptData": [
    {"count": 50, "budget": 500000},
    {"count": 30, "budget": 300000},
    {"count": 20, "budget": 200000}
  ],
  "totalEmployees": 100,
  "totalBudget": 1000000
}
```

## Common Use Cases

1. **Aggregation**: Sum, average, or find min/max across all values
2. **Value Collection**: Collect all values for processing
3. **Statistics**: Calculate statistics from object values
4. **Transformation**: Convert object to array for further processing

## Tips and Best Practices

- Returns empty array for empty objects
- Order may not be guaranteed in some implementations
- Works with any value types (numbers, strings, objects, arrays)
- Combine with aggregation functions for analysis

## Related Functions

- [keys()](keys.md) - Get object keys
- [entries()](entries.md) - Get key-value pairs
- [sum()](sum.md), [avg()](avg.md) - Aggregate numeric values
- [map()](map.md) - Transform values

## Performance Notes

- O(n) where n is number of properties
- Fast for small to medium objects
- Memory proportional to number of values

---

[← Back to Functions](../README.md#object-functions)
