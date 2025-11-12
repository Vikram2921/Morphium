# distinct() - Remove Duplicates

## Overview
The `distinct()` function removes duplicate elements from an array, returning only unique values. It compares elements by their JSON representation, so objects with the same structure and values are considered equal.

## Syntax
```javascript
distinct(array)
```

## Parameters
- **array**: The input array to process

## Return Value
Returns a new array containing only unique elements (duplicates removed).

## How It Works
1. Iterates through each element in the input array
2. Converts each element to its JSON string representation
3. Tracks which string representations have been seen
4. Only includes the first occurrence of each unique value
5. Returns array with duplicates removed

## Basic Examples

### Example 1: Remove Duplicate Numbers
```javascript
let numbers = [1, 2, 2, 3, 3, 3, 4, 5, 5];

export default = distinct(numbers);
// Result: [1, 2, 3, 4, 5]
```

### Example 2: Remove Duplicate Strings
```javascript
let colors = ["red", "blue", "green", "red", "blue", "yellow"];

export default = distinct(colors);
// Result: ["red", "blue", "green", "yellow"]
```

### Example 3: Unique Object Values
```javascript
let items = [
  { id: 1, name: "A" },
  { id: 2, name: "B" },
  { id: 1, name: "A" },
  { id: 3, name: "C" }
];

export default = distinct(items);
// Result: [
//   { id: 1, name: "A" },
//   { id: 2, name: "B" },
//   { id: 3, name: "C" }
// ]
```

## Advanced Examples

### Example 4: Unique Tags from Multiple Objects
```javascript
let articles = [
  { title: "Article 1", tags: ["java", "programming", "java"] },
  { title: "Article 2", tags: ["javascript", "web", "javascript"] }
];

export default = distinct(
  flatMap(articles, "a", a.tags)
);
// Result: ["java", "programming", "javascript", "web"]
```

### Example 5: Unique Values from Nested Data
```javascript
let orders = [
  { items: [{ sku: "A" }, { sku: "B" }, { sku: "A" }] },
  { items: [{ sku: "C" }, { sku: "B" }] }
];

export default = distinct(
  flatMap(orders, "o", pluck(o.items, "sku"))
);
// Result: ["A", "B", "C"]
```

### Example 6: Remove Duplicate Complex Objects
```javascript
let events = [
  { type: "click", target: "button1", timestamp: 100 },
  { type: "click", target: "button1", timestamp: 100 },
  { type: "hover", target: "button2", timestamp: 200 },
  { type: "click", target: "button1", timestamp: 100 }
];

export default = distinct(events);
// Result: [
//   { type: "click", target: "button1", timestamp: 100 },
//   { type: "hover", target: "button2", timestamp: 200 }
// ]
```

### Example 7: Combining with Map
```javascript
let products = [
  { id: 1, category: "Electronics" },
  { id: 2, category: "Books" },
  { id: 3, category: "Electronics" },
  { id: 4, category: "Clothing" }
];

export default = distinct(
  map(products, "p", p.category)
);
// Result: ["Electronics", "Books", "Clothing"]
```

### Example 8: Unique User IDs
```javascript
let transactions = [
  { userId: 101, amount: 50 },
  { userId: 102, amount: 75 },
  { userId: 101, amount: 100 },
  { userId: 103, amount: 25 },
  { userId: 102, amount: 60 }
];

export default = distinct(
  map(transactions, "t", t.userId)
);
// Result: [101, 102, 103]
```

## Object Comparison

### Objects are Compared by Structure and Values
```javascript
let items = [
  { x: 1, y: 2 },
  { x: 1, y: 2 },  // Duplicate - same structure and values
  { y: 2, x: 1 },  // Duplicate - same values, different order
  { x: 1, y: 3 }   // Unique - different values
];

export default = distinct(items);
// Result: [{ x: 1, y: 2 }, { x: 1, y: 3 }]
// Note: Property order doesn't matter for object comparison
```

## Common Patterns

### Pattern 1: Unique Simple Values
```javascript
distinct(array)
```

### Pattern 2: Extract Unique Property Values
```javascript
distinct(map(items, "item", item.property))
```

### Pattern 3: Unique Values from Nested Arrays
```javascript
distinct(flatMap(items, "item", item.nestedArray))
```

### Pattern 4: Filter Then Get Unique
```javascript
distinct(
  filter(items, "item", item.active)
)
```

## Performance Considerations

1. **String Comparison**: Uses JSON string comparison, which can be slow for large objects
2. **Memory Usage**: Maintains a set of all unique string representations
3. **Order Preserved**: The first occurrence of each unique value is preserved
4. **Large Arrays**: Efficient for most use cases, but consider alternatives for very large datasets

## Best Practices

1. **Extract Properties First**: If comparing objects by a single property, extract it first with map()
2. **Use Early in Pipeline**: Apply distinct() early to reduce data volume for subsequent operations
3. **Understand Object Comparison**: Remember that objects are compared by their full JSON representation
4. **Handle Nulls**: Be aware that null values are treated as distinct values

## Common Use Cases

- **Removing duplicate IDs** from merged datasets
- **Extracting unique categories** or tags
- **Deduplicating user lists** or event logs
- **Finding unique values** in aggregated data
- **Cleaning up merged arrays** from multiple sources
- **Getting unique property values** across collections

## Related Functions

- **filter()** - Remove elements based on condition
- **map()** - Extract specific properties
- **flatMap()** - Flatten nested arrays before deduplication
- **sorted()** - Sort array (often used after distinct)
- **groupBy()** - Group by values instead of removing duplicates

## Example: Complete Deduplication Pipeline
```javascript
let orders = [
  {
    orderId: "O1",
    items: [
      { sku: "A", category: "Electronics" },
      { sku: "B", category: "Books" }
    ]
  },
  {
    orderId: "O2",
    items: [
      { sku: "A", category: "Electronics" },
      { sku: "C", category: "Clothing" }
    ]
  },
  {
    orderId: "O3",
    items: [
      { sku: "B", category: "Books" },
      { sku: "D", category: "Electronics" }
    ]
  }
];

// Get unique SKUs
let uniqueSkus = distinct(
  flatMap(orders, "o", pluck(o.items, "sku"))
);

// Get unique categories
let uniqueCategories = distinct(
  flatMap(orders, "o", pluck(o.items, "category"))
);

export default = {
  skus: uniqueSkus,
  categories: uniqueCategories,
  totalOrders: len(orders)
};

// Result:
// {
//   skus: ["A", "B", "C", "D"],
//   categories: ["Electronics", "Books", "Clothing"],
//   totalOrders: 3
// }
```

## Working with Complex Types

### Example: Distinct with Custom Projection
```javascript
// If you need distinct by specific fields, project first
let users = [
  { id: 1, name: "Alice", age: 30, city: "NYC" },
  { id: 2, name: "Bob", age: 25, city: "LA" },
  { id: 1, name: "Alice", age: 30, city: "NYC" },
  { id: 3, name: "Charlie", age: 25, city: "LA" }
];

// Distinct by id and name only
export default = distinct(
  map(users, "u", { id: u.id, name: u.name })
);
// Result: [
//   { id: 1, name: "Alice" },
//   { id: 2, name: "Bob" },
//   { id: 3, name: "Charlie" }
// ]
```

## Error Handling
```javascript
// Handle null or undefined arrays
distinct(exists($.items) ? $.items : [])

// Handle non-array input
let data = $.data;
distinct(data.isArray ? data : [data])
```

## Edge Cases

### Empty Array
```javascript
distinct([])  // Result: []
```

### Single Element
```javascript
distinct([1])  // Result: [1]
```

### All Duplicates
```javascript
distinct([5, 5, 5, 5])  // Result: [5]
```

### Null Values
```javascript
distinct([1, null, 2, null, 3])  // Result: [1, null, 2, 3]
```

### Mixed Types
```javascript
distinct([1, "1", true, 1, "1"])  // Result: [1, "1", true]
// Different types are treated as distinct
```

## Combining with Other Operations

### Distinct + Sorted
```javascript
let numbers = [5, 2, 8, 2, 1, 5, 3];

export default = sorted(distinct(numbers));
// Result: [1, 2, 3, 5, 8]
```

### Distinct + Filter + Map
```javascript
let products = [
  { id: 1, name: "A", active: true },
  { id: 2, name: "B", active: false },
  { id: 1, name: "A", active: true },
  { id: 3, name: "C", active: true }
];

export default = distinct(
  map(
    filter(products, "p", p.active),
    "p",
    p.id
  )
);
// Result: [1, 3]
```

### Distinct with Count
```javascript
let values = [1, 2, 2, 3, 3, 3, 4, 4, 4, 4];

let uniqueValues = distinct(values);

export default = {
  original: values,
  unique: uniqueValues,
  originalCount: len(values),
  uniqueCount: len(uniqueValues),
  duplicatesRemoved: len(values) - len(uniqueValues)
};
// Result: {
//   original: [1, 2, 2, 3, 3, 3, 4, 4, 4, 4],
//   unique: [1, 2, 3, 4],
//   originalCount: 10,
//   uniqueCount: 4,
//   duplicatesRemoved: 6
// }
```

## See Also
- [filter()](filter.md) - Remove elements based on condition
- [map()](map.md) - Transform array elements
- [flatMap()](flatMap.md) - Flatten nested arrays
- [sorted()](sorted.md) - Sort arrays
- [groupBy()](groupBy.md) - Group elements by key
