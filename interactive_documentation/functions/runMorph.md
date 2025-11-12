# runMorph() - Run Nested Transformation

## Syntax
```javascript
runMorph(morphScript, inputData)
```

## Description
Executes a Morphium transformation script on provided data and returns the result. Allows nested transformations and dynamic script execution.

## Parameters
- `morphScript` - String containing Morphium DSL script
- `inputData` - Data to pass as input to the transformation

## Returns
Result of executing the transformation.

## Examples

### Basic Nested Transformation
```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5]
}

// Morph
{
  doubled: runMorph("map($, 'n', n * 2)", $.numbers)
}

// Output
{
  "doubled": [2, 4, 6, 8, 10]
}
```

### Dynamic Script Execution
```javascript
// Input
{
  "data": [10, 20, 30, 40],
  "script": "{ total: sum($), average: avg($) }"
}

// Morph
{
  result: runMorph($.script, $.data)
}

// Output
{
  "result": {
    "total": 100,
    "average": 25
  }
}
```

### Reusable Transformation Logic
```javascript
// Input
{
  "users": [
    {"firstName": "Alice", "lastName": "Smith"},
    {"firstName": "Bob", "lastName": "Jones"}
  ],
  "products": [
    {"name": "laptop", "brand": "dell"},
    {"name": "mouse", "brand": "logitech"}
  ]
}

// Morph
{
  normalizeScript: "map($, 'item', { name: upper(item.firstName + ' ' + item.lastName) })",
  normalizedUsers: runMorph(
    "map($, 'u', { fullName: upper(u.firstName + ' ' + u.lastName) })",
    $.users
  ),
  normalizedProducts: runMorph(
    "map($, 'p', { fullName: upper(p.name + ' ' + p.brand) })",
    $.products
  )
}

// Output
{
  "normalizedUsers": [
    {"fullName": "ALICE SMITH"},
    {"fullName": "BOB JONES"}
  ],
  "normalizedProducts": [
    {"fullName": "LAPTOP DELL"},
    {"fullName": "MOUSE LOGITECH"}
  ]
}
```

### Complex Nested Processing
```javascript
// Input
{
  "orders": [
    {
      "id": 1,
      "items": [
        {"product": "A", "price": 10, "qty": 2},
        {"product": "B", "price": 20, "qty": 1}
      ]
    },
    {
      "id": 2,
      "items": [
        {"product": "C", "price": 15, "qty": 3}
      ]
    }
  ]
}

// Morph
{
  orderTotals: map($.orders, "o", {
    orderId: o.id,
    itemCount: len(o.items),
    total: runMorph("sum(map($, 'i', i.price * i.qty))", o.items)
  })
}

// Output
{
  "orderTotals": [
    {"orderId": 1, "itemCount": 2, "total": 40},
    {"orderId": 2, "itemCount": 1, "total": 45}
  ]
}
```

### Apply Different Transformations Conditionally
```javascript
// Input
{
  "dataType": "users",
  "data": [
    {"name": "Alice", "age": 25},
    {"name": "Bob", "age": 30}
  ]
}

// Morph
{
  result: if ($.dataType == "users") {
    runMorph("map($, 'u', { userName: upper(u.name), age: u.age })", $.data)
  } else {
    runMorph("map($, 'p', { productName: upper(p.name), price: p.price })", $.data)
  }
}

// Output
{
  "result": [
    {"userName": "ALICE", "age": 25},
    {"userName": "BOB", "age": 30}
  ]
}
```

### Template-Based Transformation
```javascript
// Input
{
  "template": "{ names: map($, 'item', item.name), count: len($) }",
  "datasets": [
    [{"name": "A"}, {"name": "B"}],
    [{"name": "X"}, {"name": "Y"}, {"name": "Z"}]
  ]
}

// Morph
{
  results: map($.datasets, "ds", runMorph($.template, ds))
}

// Output
{
  "results": [
    {"names": ["A", "B"], "count": 2},
    {"names": ["X", "Y", "Z"], "count": 3}
  ]
}
```

### Data Validation with Scripts
```javascript
// Input
{
  "validationScript": "allMatch($, 'item', item > 0)",
  "dataset1": [1, 2, 3, 4],
  "dataset2": [1, -2, 3, 4]
}

// Morph
{
  dataset1Valid: runMorph($.validationScript, $.dataset1),
  dataset2Valid: runMorph($.validationScript, $.dataset2)
}

// Output
{
  "dataset1Valid": true,
  "dataset2Valid": false
}
```

## Common Use Cases

1. **Dynamic Transformations**: Execute transformations based on runtime data
2. **Reusable Logic**: Apply same transformation to different datasets
3. **Template Processing**: Use transformation templates
4. **Nested Processing**: Process nested data structures
5. **Conditional Logic**: Apply different transformations conditionally

## Tips and Best Practices

- Script is compiled and executed in isolated context
- Input data is available as `$` in the script
- Useful for dynamic transformation logic
- Can be combined with stored transformation templates
- Error in script will propagate to main transformation

## Related Functions

- [map()](map.md) - Transform array elements
- [filter()](filter.md) - Filter arrays
- [reduce()](reduce.md) - Reduce arrays

## Performance Notes

- O(n) where n is script complexity
- Script is parsed and compiled on each call
- Cache scripts when possible for better performance
- Consider overhead of nested execution

## Security Considerations

- Scripts execute with full Morphium capabilities
- Validate/sanitize script strings from untrusted sources
- Use with caution when scripts come from user input

---

[← Back to Functions](../README.md#advanced)
