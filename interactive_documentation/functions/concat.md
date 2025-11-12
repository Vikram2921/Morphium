# concat() - Concatenate Arrays

## Overview
The `concat()` function combines multiple arrays into a single array, preserving the order of elements from each input array.

## Syntax
```javascript
concat(array1, array2, ..., arrayN)
```

## Parameters
- **arrays**: Multiple arrays to concatenate (variable number of arguments)

## Return Value
Returns a new array containing all elements from all input arrays in order.

## How It Works
1. Creates a new empty array
2. Adds all elements from the first array
3. Adds all elements from the second array
4. Continues for all remaining arrays
5. Returns the concatenated result

## Basic Examples

### Example 1: Concat Two Arrays
```javascript
let first = [1, 2, 3];
let second = [4, 5, 6];

export default = concat(first, second);
// Result: [1, 2, 3, 4, 5, 6]
```

### Example 2: Concat Multiple Arrays
```javascript
let a = [1, 2];
let b = [3, 4];
let c = [5, 6];

export default = concat(a, b, c);
// Result: [1, 2, 3, 4, 5, 6]
```

### Example 3: Concat Objects
```javascript
let users1 = [
  { id: 1, name: "Alice" },
  { id: 2, name: "Bob" }
];
let users2 = [
  { id: 3, name: "Charlie" },
  { id: 4, name: "David" }
];

export default = concat(users1, users2);
// Result: [
//   { id: 1, name: "Alice" },
//   { id: 2, name: "Bob" },
//   { id: 3, name: "Charlie" },
//   { id: 4, name: "David" }
// ]
```

## Advanced Examples

### Example 4: Merge and Deduplicate
```javascript
let list1 = [1, 2, 3];
let list2 = [3, 4, 5];

export default = distinct(concat(list1, list2));
// Result: [1, 2, 3, 4, 5]
```

### Example 5: Combine Different Sources
```javascript
let activeUsers = filter($.users, "u", u.active);
let premiumUsers = filter($.users, "u", u.premium);

export default = distinct(concat(activeUsers, premiumUsers));
// All users who are either active or premium (or both)
```

### Example 6: Merging Paginated Results
```javascript
let page1 = $.page1.items;
let page2 = $.page2.items;
let page3 = $.page3.items;

export default = {
  allItems: concat(page1, page2, page3),
  totalCount: len(concat(page1, page2, page3))
};
```

### Example 7: Building Combined Report
```javascript
let errors = filter($.logs, "log", log.level == "ERROR");
let warnings = filter($.logs, "log", log.level == "WARN");

export default = {
  criticalIssues: concat(errors, warnings),
  errorCount: len(errors),
  warningCount: len(warnings),
  totalIssues: len(concat(errors, warnings))
};
```

### Example 8: Combining Filtered Results
```javascript
let highPriority = filter($.tasks, "t", t.priority == "high");
let overdue = filter($.tasks, "t", t.dueDate < now());

export default = {
  actionRequired: distinct(concat(highPriority, overdue)),
  description: "Tasks that are high priority or overdue"
};
```

## Common Patterns

### Pattern 1: Simple Concatenation
```javascript
concat(array1, array2)
```

### Pattern 2: Merge and Deduplicate
```javascript
distinct(concat(array1, array2))
```

### Pattern 3: Combine Multiple Sources
```javascript
concat(source1, source2, source3, ...)
```

### Pattern 4: Merge Then Sort
```javascript
sorted(concat(array1, array2), "property")
```

## Performance Considerations

1. **Creates New Array**: concat() creates a new array, doesn't modify originals
2. **Linear Time**: O(n) where n is total elements across all arrays
3. **Memory Usage**: Allocates space for all elements combined
4. **Order Preserved**: Elements maintain their original order within each array

## Best Practices

1. **Use for Merging**: Perfect for combining data from multiple sources
2. **Deduplicate if Needed**: Follow with distinct() if duplicates are unwanted
3. **Consider flatMap**: For nested arrays, flatMap might be more appropriate
4. **Empty Arrays OK**: concat handles empty arrays gracefully

## Common Use Cases

- **Merging paginated results** from multiple API calls
- **Combining filtered subsets** of data
- **Aggregating data from multiple sources**
- **Building unified lists** from separate collections
- **Merging user selections** or search results
- **Combining historical and current data**
- **Joining split datasets**

## Related Functions

- **flatMap()** - Flatten nested arrays while mapping
- **distinct()** - Remove duplicates after concatenation
- **merge()** - Merge objects (not arrays)
- **slice()** - Extract portions of arrays
- **filter()** - Filter arrays before concatenating

## Example: Complete Data Merging Pipeline
```javascript
let onlineOrders = $.onlineOrders;
let storeOrders = $.storeOrders;
let phoneOrders = $.phoneOrders;

// Combine all orders
let allOrders = concat(onlineOrders, storeOrders, phoneOrders);

// Add source information and sort
export default = {
  orders: reverse(sorted(
    concat(
      map(onlineOrders, "o", merge(o, { source: "online" })),
      map(storeOrders, "o", merge(o, { source: "store" })),
      map(phoneOrders, "o", merge(o, { source: "phone" }))
    ),
    "orderDate"
  )),
  summary: {
    online: len(onlineOrders),
    store: len(storeOrders),
    phone: len(phoneOrders),
    total: len(allOrders)
  }
};
```

## Working with Empty Arrays

### Example: Safe Concatenation
```javascript
let array1 = exists($.data1) ? $.data1 : [];
let array2 = exists($.data2) ? $.data2 : [];
let array3 = exists($.data3) ? $.data3 : [];

export default = concat(array1, array2, array3);
// Safely handles missing data
```

### Example: Conditional Inclusion
```javascript
let baseItems = $.items;
let bonusItems = $.hasPremium ? $.premiumItems : [];
let specialItems = $.isSpecialEvent ? $.specialItems : [];

export default = concat(baseItems, bonusItems, specialItems);
```

## Error Handling
```javascript
// Handle null/undefined arrays
concat(
  exists($.array1) ? $.array1 : [],
  exists($.array2) ? $.array2 : []
)

// Validate array types
concat(
  $.data1.isArray ? $.data1 : [],
  $.data2.isArray ? $.data2 : []
)
```

## Edge Cases

### Empty Arrays
```javascript
concat([], [])  // Result: []
```

### One Empty, One Filled
```javascript
concat([1, 2, 3], [])  // Result: [1, 2, 3]
concat([], [4, 5, 6])  // Result: [4, 5, 6]
```

### Single Array
```javascript
concat([1, 2, 3])  // Result: [1, 2, 3]
```

### Many Arrays
```javascript
concat([1], [2], [3], [4], [5])  // Result: [1, 2, 3, 4, 5]
```

## Combining with Other Operations

### Concat + Distinct + Sorted
```javascript
let favorites = $.userFavorites;
let recommended = $.recommendations;

export default = sorted(
  distinct(
    concat(favorites, recommended)
  ),
  "title"
);
// Unique items from both lists, sorted
```

### Concat + Filter
```javascript
let source1 = $.source1;
let source2 = $.source2;

export default = filter(
  concat(source1, source2),
  "item",
  item.active && item.price > 0
);
// Merge then filter combined result
```

### Concat + Map
```javascript
let oldFormat = $.oldData;
let newFormat = $.newData;

export default = map(
  concat(oldFormat, newFormat),
  "item",
  { 
    id: item.id, 
    normalized: true,
    data: item 
  }
);
// Merge then transform all items
```

### Nested Concat
```javascript
let group1 = concat($.a, $.b);
let group2 = concat($.c, $.d);

export default = concat(group1, group2);
// Result: All arrays combined
```

## Practical Examples

### Example: Multi-Source Search Results
```javascript
let dbResults = $.database.searchResults;
let cacheResults = $.cache.searchResults;
let externalResults = $.external.searchResults;

export default = {
  results: distinct(
    sorted(
      concat(dbResults, cacheResults, externalResults),
      "relevance"
    )
  ),
  sources: {
    database: len(dbResults),
    cache: len(cacheResults),
    external: len(externalResults)
  }
};
```

### Example: Activity Timeline
```javascript
let comments = map($.comments, "c", {
  type: "comment",
  timestamp: c.createdAt,
  content: c.text
});

let likes = map($.likes, "l", {
  type: "like",
  timestamp: l.createdAt,
  content: "liked"
});

let shares = map($.shares, "s", {
  type: "share",
  timestamp: s.createdAt,
  content: "shared"
});

export default = reverse(
  sorted(
    concat(comments, likes, shares),
    "timestamp"
  )
);
// Unified activity timeline
```

### Example: Combining User Groups
```javascript
let admins = filter($.users, "u", u.role == "admin");
let moderators = filter($.users, "u", u.role == "moderator");
let editors = filter($.users, "u", u.role == "editor");

export default = {
  privilegedUsers: distinct(
    concat(admins, moderators, editors)
  ),
  breakdown: {
    admins: len(admins),
    moderators: len(moderators),
    editors: len(editors)
  }
};
```

## Comparison with flatMap

### Using concat (for flat arrays)
```javascript
let a = [1, 2, 3];
let b = [4, 5, 6];

concat(a, b)  // Result: [1, 2, 3, 4, 5, 6]
```

### Using flatMap (for nested structures)
```javascript
let data = [
  { values: [1, 2, 3] },
  { values: [4, 5, 6] }
];

flatMap(data, "item", item.values)  // Result: [1, 2, 3, 4, 5, 6]
```

## See Also
- [flatMap()](flatMap.md) - Flatten nested arrays
- [distinct()](distinct.md) - Remove duplicates
- [sorted()](sorted.md) - Sort arrays
- [merge()](merge.md) - Merge objects
- [filter()](filter.md) - Filter arrays
