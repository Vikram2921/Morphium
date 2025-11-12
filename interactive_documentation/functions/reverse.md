# reverse() - Reverse Array Order

## Overview
The `reverse()` function reverses the order of elements in an array, returning a new array with elements in opposite order. The original array remains unchanged.

## Syntax
```javascript
reverse(array)
```

## Parameters
- **array**: The input array to reverse

## Return Value
Returns a new array with elements in reverse order.

## How It Works
1. Creates a copy of the input array
2. Reverses the order of all elements
3. Returns the reversed array

## Basic Examples

### Example 1: Reverse Numbers
```javascript
let numbers = [1, 2, 3, 4, 5];

export default = reverse(numbers);
// Result: [5, 4, 3, 2, 1]
```

### Example 2: Reverse Strings
```javascript
let names = ["Alice", "Bob", "Charlie"];

export default = reverse(names);
// Result: ["Charlie", "Bob", "Alice"]
```

### Example 3: Reverse Objects
```javascript
let users = [
  { id: 1, name: "Alice" },
  { id: 2, name: "Bob" },
  { id: 3, name: "Charlie" }
];

export default = reverse(users);
// Result: [
//   { id: 3, name: "Charlie" },
//   { id: 2, name: "Bob" },
//   { id: 1, name: "Alice" }
// ]
```

## Advanced Examples

### Example 4: Reverse for Descending Order
```javascript
let scores = [65, 92, 78, 95, 81];

export default = reverse(sorted(scores));
// Result: [95, 92, 81, 78, 65]
```

### Example 5: Latest Items First
```javascript
let events = [
  { id: 1, timestamp: "2024-01-01T10:00:00Z" },
  { id: 2, timestamp: "2024-01-02T11:00:00Z" },
  { id: 3, timestamp: "2024-01-03T12:00:00Z" }
];

export default = reverse(sorted(events, "timestamp"));
// Result: Latest events first
```

### Example 6: Reverse After Filtering
```javascript
let products = [
  { id: 1, name: "A", active: true },
  { id: 2, name: "B", active: false },
  { id: 3, name: "C", active: true },
  { id: 4, name: "D", active: true }
];

export default = reverse(
  filter(products, "p", p.active)
);
// Result: [
//   { id: 4, name: "D", active: true },
//   { id: 3, name: "C", active: true },
//   { id: 1, name: "A", active: true }
// ]
```

### Example 7: Reverse Pagination
```javascript
let items = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

// Get last 3 items in reverse order
export default = limit(reverse(items), 3);
// Result: [10, 9, 8]
```

### Example 8: Breadcrumb Trail Reversal
```javascript
let path = ["Home", "Products", "Electronics", "Laptops"];

export default = reverse(path);
// Result: ["Laptops", "Electronics", "Products", "Home"]
// Useful for building breadcrumb trails from child to parent
```

## Common Patterns

### Pattern 1: Simple Reversal
```javascript
reverse(array)
```

### Pattern 2: Descending Sort
```javascript
reverse(sorted(array))
reverse(sorted(objects, "property"))
```

### Pattern 3: Latest N Items
```javascript
limit(reverse(sorted(items, "date")), n)
```

### Pattern 4: Reverse After Transform
```javascript
reverse(map(items, "item", item.value))
```

## Performance Considerations

1. **Creates New Array**: reverse() doesn't modify the original array
2. **Linear Time**: O(n) complexity - processes each element once
3. **Memory Usage**: Creates a complete copy of the array
4. **Efficient Operation**: One of the simpler array operations

## Best Practices

1. **Use with Sorted**: Common pattern for descending order
2. **Consider Alternatives**: For simple desc sort, reverse(sorted()) is clear and readable
3. **Chain Operations**: Combine with limit() for "last N" queries
4. **Preserve Originals**: Remember that the original array is unchanged

## Common Use Cases

- **Descending sort** - reverse(sorted(array))
- **Latest items first** - Chronological data in reverse order
- **Stack operations** - LIFO (Last In, First Out) behavior
- **Breadcrumbs** - Reverse path hierarchies
- **Mirror arrays** - Create opposite order copies
- **Pagination** - "Previous items" or "last N" queries
- **Undo operations** - Process history in reverse

## Related Functions

- **sorted()** - Sort arrays (often used with reverse)
- **limit()** - Take first N elements (after reverse for "last N")
- **skip()** - Skip first N elements
- **slice()** - Extract array portion

## Example: Complete Leaderboard Pipeline
```javascript
let players = [
  { name: "Alice", score: 1250, level: 15 },
  { name: "Bob", score: 980, level: 12 },
  { name: "Charlie", score: 1400, level: 18 },
  { name: "David", score: 1100, level: 14 },
  { name: "Eve", score: 1350, level: 16 }
];

// Top 3 players by score (descending)
let topPlayers = limit(
  reverse(sorted(players, "score")),
  3
);

// Add rankings
export default = map(topPlayers, "p",
  let rank = indexOf(topPlayers, p) + 1;
  {
    rank: rank,
    name: p.name,
    score: p.score,
    level: p.level,
    medal: rank == 1 ? "🥇" : rank == 2 ? "🥈" : "🥉"
  }
);

// Result: [
//   { rank: 1, name: "Charlie", score: 1400, level: 18, medal: "🥇" },
//   { rank: 2, name: "Eve", score: 1350, level: 16, medal: "🥈" },
//   { rank: 3, name: "Alice", score: 1250, level: 15, medal: "🥉" }
// ]
```

## Working with Time-Series Data

### Example: Latest Transactions
```javascript
let transactions = [
  { id: 1, amount: 100, date: "2024-01-15" },
  { id: 2, amount: 250, date: "2024-02-20" },
  { id: 3, amount: 75, date: "2024-03-10" },
  { id: 4, amount: 150, date: "2024-03-25" }
];

// Get 2 most recent transactions
export default = limit(
  reverse(sorted(transactions, "date")),
  2
);
// Result: [
//   { id: 4, amount: 150, date: "2024-03-25" },
//   { id: 3, amount: 75, date: "2024-03-10" }
// ]
```

### Example: Reverse Chronological Feed
```javascript
let posts = $.posts;

export default = {
  title: "Recent Posts",
  items: limit(
    reverse(sorted(posts, "publishedAt")),
    10
  )
};
// Shows 10 most recent posts first
```

## Error Handling
```javascript
// Handle null or undefined arrays
reverse(exists($.items) ? $.items : [])

// Handle non-array input
let data = $.data;
reverse(data.isArray ? data : [data])

// Safe reversal with validation
exists($.items) && len($.items) > 0 ? 
  reverse($.items) : 
  []
```

## Edge Cases

### Empty Array
```javascript
reverse([])  // Result: []
```

### Single Element
```javascript
reverse([42])  // Result: [42]
```

### Two Elements
```javascript
reverse([1, 2])  // Result: [2, 1]
```

### Palindrome Array
```javascript
reverse([1, 2, 3, 2, 1])  // Result: [1, 2, 3, 2, 1]
// Same as original
```

## Combining with Other Functions

### Reverse + Sorted + Filter
```javascript
let products = $.products;

export default = limit(
  reverse(
    sorted(
      filter(products, "p", p.inStock),
      "price"
    )
  ),
  5
);
// Top 5 most expensive in-stock products
```

### Reverse + Map
```javascript
let numbers = [1, 2, 3, 4, 5];

export default = reverse(
  map(numbers, "n", n * n)
);
// Result: [25, 16, 9, 4, 1]
```

### Double Reverse (Original Order)
```javascript
let array = [1, 2, 3, 4, 5];

export default = reverse(reverse(array));
// Result: [1, 2, 3, 4, 5] - back to original order
```

### Reverse + Slice
```javascript
let items = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

// Get last 3 items (but keep original order within those 3)
let last3 = reverse(limit(reverse(items), 3));
// Result: [8, 9, 10]
```

## Alternative: Getting Last Elements

### Using Reverse + Limit
```javascript
// Get last 5 elements
limit(reverse(array), 5)
// Returns: Last 5 in reverse order
```

### Using Reverse + Limit + Reverse
```javascript
// Get last 5 elements in original order
reverse(limit(reverse(array), 5))
// Returns: Last 5 in original order
```

### Using Slice
```javascript
// Get last 5 elements (if slice supports negative indices)
slice(array, -5)
// Alternative approach depending on slice implementation
```

## Practical Examples

### Example: Processing Stack (LIFO)
```javascript
let stack = [1, 2, 3, 4, 5];

// Process in LIFO order
export default = map(
  reverse(stack),
  "item",
  { processed: item, order: "LIFO" }
);
// Result: Processes 5, 4, 3, 2, 1
```

### Example: Building Navigation
```javascript
let currentPath = ["Home", "Products", "Electronics", "Laptops", "Gaming"];

export default = {
  title: "Gaming Laptops",
  breadcrumbs: map(
    reverse(currentPath),
    "crumb",
    { text: crumb, link: "/" + lower(crumb) }
  )
};
// Breadcrumbs from current page back to home
```

### Example: Recent Activity Log
```javascript
let activities = [
  { action: "login", time: "09:00" },
  { action: "view_product", time: "09:15" },
  { action: "add_to_cart", time: "09:20" },
  { action: "checkout", time: "09:30" }
];

export default = {
  recentActivity: limit(reverse(activities), 3)
};
// Shows 3 most recent activities
```

## See Also
- [sorted()](sorted.md) - Sort arrays
- [limit()](limit.md) - Take first N elements
- [skip()](skip.md) - Skip first N elements
- [slice()](slice.md) - Extract array portion
- [concat()](concat.md) - Combine arrays
