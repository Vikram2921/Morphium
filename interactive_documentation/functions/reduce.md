# reduce()

Reduce an array to a single value by applying an accumulator function.

---

## Syntax

```javascript
reduce(array, "accumulator", "item", initialValue, expression)
```

### Parameters
- **array**: The input array to reduce
- **accumulator**: Variable name for the accumulated value (string)
- **item**: Variable name for the current element (string)
- **initialValue**: Starting value for the accumulator
- **expression**: Expression that combines accumulator and item

### Returns
A single value computed by applying the expression across all elements.

---

## Description

The `reduce()` function processes an array from left to right, applying an expression that combines an accumulator with each element, ultimately returning a single value.

---

## Basic Examples

### Example 1: Sum Array
```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5]
}

// Script
{
  total: reduce($.numbers, "acc", "n", 0, acc + n)
}

// Output
{
  "total": 15
}
```

### Example 2: Product of Numbers
```javascript
// Input
{
  "values": [2, 3, 4]
}

// Script
{
  product: reduce($.values, "acc", "v", 1, acc * v)
}

// Output
{
  "product": 24
}
```

### Example 3: Concatenate Strings
```javascript
// Input
{
  "words": ["Hello", "World", "from", "Morphium"]
}

// Script
{
  sentence: reduce($.words, "acc", "word", "", acc + word + " ")
}

// Output
{
  "sentence": "Hello World from Morphium "
}
```

---

## Advanced Examples

### Example 4: Count Occurrences
```javascript
// Input
{
  "items": ["apple", "banana", "apple", "cherry", "banana", "apple"]
}

// Script
{
  counts: reduce(
    $.items,
    "acc",
    "item",
    {},
    set(acc, item, get(acc, item, 0) + 1)
  )
}

// Output
{
  "counts": {
    "apple": 3,
    "banana": 2,
    "cherry": 1
  }
}
```

### Example 5: Max Value
```javascript
// Input
{
  "numbers": [45, 12, 89, 23, 67]
}

// Script
{
  maximum: reduce(
    $.numbers,
    "acc",
    "n",
    0,
    if (n > acc) { n } else { acc }
  )
}

// Output
{
  "maximum": 89
}
```

### Example 6: Build Object from Array
```javascript
// Input
{
  "users": [
    {"id": 1, "name": "Alice"},
    {"id": 2, "name": "Bob"},
    {"id": 3, "name": "Charlie"}
  ]
}

// Script
{
  userMap: reduce(
    $.users,
    "acc",
    "user",
    {},
    set(acc, toString(user.id), user.name)
  )
}

// Output
{
  "userMap": {
    "1": "Alice",
    "2": "Bob",
    "3": "Charlie"
  }
}
```

### Example 7: Flatten Nested Arrays
```javascript
// Input
{
  "nested": [[1, 2], [3, 4], [5, 6]]
}

// Script
{
  flattened: reduce(
    $.nested,
    "acc",
    "arr",
    [],
    concat(acc, arr)
  )
}

// Output
{
  "flattened": [1, 2, 3, 4, 5, 6]
}
```

---

## Common Use Cases

### 1. Aggregation
```javascript
// Sum
reduce($.numbers, "sum", "n", 0, sum + n)

// Average (use with len())
let total = reduce($.numbers, "sum", "n", 0, sum + n);
{ average: total / len($.numbers) }
```

### 2. String Building
```javascript
reduce($.items, "result", "item", "", result + item.name + ", ")
```

### 3. Object Construction
```javascript
reduce($.entries, "obj", "e", {}, set(obj, e.key, e.value))
```

### 4. Finding Maximum/Minimum
```javascript
// Max
reduce($.values, "max", "v", 0, if (v > max) { v } else { max })

// Min
reduce($.values, "min", "v", 999999, if (v < min) { v } else { min })
```

### 5. Grouping Data
```javascript
reduce(
  $.items,
  "groups",
  "item",
  {},
  set(groups, item.category, concat(get(groups, item.category, []), [item]))
)
```

---

## Best Practices

### ✅ Do's
- Choose appropriate initial value
- Keep expressions simple and readable
- Use for mathematical operations and aggregations
- Consider using specialized functions (sum, max, etc.) when available

### ❌ Don'ts
- Don't use for simple transformations (use map instead)
- Avoid complex nested reduces
- Don't mutate external state

---

## Performance Tips

1. **Use Built-in Functions**: For common operations, use specialized functions:
```javascript
// Instead of
reduce($.numbers, "sum", "n", 0, sum + n)

// Use
sum($.numbers)
```

2. **Optimize Initial Value**: Choose the right starting point
```javascript
// For multiplication, start with 1
reduce($.numbers, "product", "n", 1, product * n)

// For finding max, start with first element or very small number
```

---

## Edge Cases

### Empty Array
```javascript
reduce([], "acc", "item", 0, acc + item)  // Returns: 0 (initial value)
```

### Single Element
```javascript
reduce([5], "acc", "n", 0, acc + n)  // Returns: 5
```

### Initial Value Matters
```javascript
// Different initial values give different results
reduce([1, 2, 3], "prod", "n", 1, prod * n)  // Returns: 6
reduce([1, 2, 3], "prod", "n", 0, prod * n)  // Returns: 0
```

---

## Related Functions

- [**sum()**](sum.md) - Sum array elements (specialized reduce)
- [**map()**](map.md) - Transform each element
- [**filter()**](filter.md) - Select elements
- [**flatMap()**](flatMap.md) - Map and flatten

---

## Alternative Approaches

### Using Sum for Totals
```javascript
// Instead of
reduce($.numbers, "sum", "n", 0, sum + n)

// Use
sum($.numbers)
```

### Using For-Of for Complex Logic
```javascript
let total = 0;
for (n of $.numbers) {
  total = total + n
};
{ total: total }
```

---

**Category:** Array Functions  
**Since:** 1.0.0  
**Complexity:** O(n) where n is array length
