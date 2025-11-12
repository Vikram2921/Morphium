# sorted() - Sort Arrays

## Overview
The `sorted()` function sorts an array either naturally (for numbers and strings) or by a specific object property. It returns a new sorted array without modifying the original.

## Syntax
```javascript
// Sort naturally
sorted(array)

// Sort by object property
sorted(array, "propertyName")
```

## Parameters
- **array**: The input array to sort
- **propertyName** (optional): The property name to sort by for arrays of objects

## Return Value
Returns a new array with elements sorted in ascending order.

## How It Works
1. Creates a copy of the input array
2. Sorts elements based on comparison rules:
   - Numbers: Numerical comparison
   - Strings: Lexicographical (alphabetical) comparison
   - Objects: By specified property value
3. Returns the sorted array

## Basic Examples

### Example 1: Sort Numbers
```javascript
let numbers = [5, 2, 8, 1, 9, 3];

export default = sorted(numbers);
// Result: [1, 2, 3, 5, 8, 9]
```

### Example 2: Sort Strings
```javascript
let names = ["Charlie", "Alice", "Bob", "David"];

export default = sorted(names);
// Result: ["Alice", "Bob", "Charlie", "David"]
```

### Example 3: Sort Objects by Property
```javascript
let products = [
  { name: "Laptop", price: 1000 },
  { name: "Mouse", price: 25 },
  { name: "Keyboard", price: 75 }
];

export default = sorted(products, "price");
// Result: [
//   { name: "Mouse", price: 25 },
//   { name: "Keyboard", price: 75 },
//   { name: "Laptop", price: 1000 }
// ]
```

### Example 4: Sort by Name
```javascript
let users = [
  { id: 3, name: "Charlie", age: 30 },
  { id: 1, name: "Alice", age: 25 },
  { id: 2, name: "Bob", age: 35 }
];

export default = sorted(users, "name");
// Result: [
//   { id: 1, name: "Alice", age: 25 },
//   { id: 2, name: "Bob", age: 35 },
//   { id: 3, name: "Charlie", age: 30 }
// ]
```

## Advanced Examples

### Example 5: Sort and Reverse for Descending
```javascript
let scores = [85, 92, 78, 95, 88];

export default = reverse(sorted(scores));
// Result: [95, 92, 88, 85, 78]
```

### Example 6: Sort Complex Data
```javascript
let transactions = [
  { id: 1, amount: 100, date: "2024-03-15" },
  { id: 2, amount: 50, date: "2024-03-10" },
  { id: 3, amount: 200, date: "2024-03-20" }
];

export default = sorted(transactions, "date");
// Result: Sorted by date (earliest first)
// [
//   { id: 2, amount: 50, date: "2024-03-10" },
//   { id: 1, amount: 100, date: "2024-03-15" },
//   { id: 3, amount: 200, date: "2024-03-20" }
// ]
```

### Example 7: Sort After Filtering
```javascript
let products = [
  { name: "Laptop", price: 1000, inStock: true },
  { name: "Mouse", price: 25, inStock: true },
  { name: "Monitor", price: 300, inStock: false },
  { name: "Keyboard", price: 75, inStock: true }
];

export default = sorted(
  filter(products, "p", p.inStock),
  "price"
);
// Result: In-stock products sorted by price
// [
//   { name: "Mouse", price: 25, inStock: true },
//   { name: "Keyboard", price: 75, inStock: true },
//   { name: "Laptop", price: 1000, inStock: true }
// ]
```

### Example 8: Sort with Transformation
```javascript
let employees = [
  { firstName: "John", lastName: "Doe", salary: 50000 },
  { firstName: "Jane", lastName: "Smith", salary: 60000 },
  { firstName: "Bob", lastName: "Johnson", salary: 45000 }
];

// Sort by salary, then transform
export default = map(
  sorted(employees, "salary"),
  "e",
  { name: e.firstName + " " + e.lastName, salary: e.salary }
);
// Result: [
//   { name: "Bob Johnson", salary: 45000 },
//   { name: "John Doe", salary: 50000 },
//   { name: "Jane Smith", salary: 60000 }
// ]
```

## Sorting Different Data Types

### Numbers
```javascript
sorted([10, 2.5, -5, 0, 100])
// Result: [-5, 0, 2.5, 10, 100]
```

### Strings (Case-Sensitive)
```javascript
sorted(["banana", "Apple", "cherry", "Banana"])
// Result: ["Apple", "Banana", "banana", "cherry"]
// Note: Capital letters come before lowercase
```

### Mixed Number/String Values in Objects
```javascript
let items = [
  { id: "10", value: 100 },
  { id: "2", value: 50 },
  { id: "20", value: 75 }
];

sorted(items, "id")
// Result: Lexicographic sort on string IDs
// [
//   { id: "10", value: 100 },
//   { id: "2", value: 50 },
//   { id: "20", value: 75 }
// ]

sorted(items, "value")
// Result: Numeric sort on number values
// [
//   { id: "2", value: 50 },
//   { id: "20", value: 75 },
//   { id: "10", value: 100 }
// ]
```

## Common Patterns

### Pattern 1: Simple Natural Sort
```javascript
sorted(array)
```

### Pattern 2: Sort by Property
```javascript
sorted(objects, "propertyName")
```

### Pattern 3: Sort Descending
```javascript
reverse(sorted(array))
```

### Pattern 4: Top N Items
```javascript
limit(sorted(items, "score"), 10)
```

### Pattern 5: Sort After Filter
```javascript
sorted(filter(items, "item", item.active), "priority")
```

## Performance Considerations

1. **Creates New Array**: sorted() doesn't modify the original array
2. **Comparison Cost**: Sorting large arrays can be expensive (O(n log n))
3. **Property Access**: Sorting objects by property is slightly slower than sorting primitives
4. **Memory Usage**: Creates a copy of the array

## Best Practices

1. **Sort Early**: If possible, sort before other expensive operations
2. **Combine with Limit**: Use limit() after sorted() for "top N" queries
3. **Use Reverse for Descending**: Combine with reverse() for descending order
4. **Filter Before Sorting**: Reduce data volume before sorting
5. **Consider Data Type**: Be aware of how different types are compared

## Common Use Cases

- **Ranking lists** by score, rating, or priority
- **Alphabetical ordering** of names or titles
- **Chronological ordering** of dates or timestamps
- **Price sorting** for products
- **Leaderboards** and top performers
- **Organizing data** for reports
- **Finding min/max** (sort then take first/last)

## Related Functions

- **reverse()** - Reverse array order (use with sorted for descending)
- **filter()** - Filter before sorting
- **limit()** - Take top N after sorting
- **skip()** - Skip first N after sorting
- **min()** - Find minimum value without full sort
- **max()** - Find maximum value without full sort

## Example: Complete Ranking Pipeline
```javascript
let students = [
  { name: "Alice", score: 85, grade: "B" },
  { name: "Bob", score: 92, grade: "A" },
  { name: "Charlie", score: 78, grade: "C" },
  { name: "David", score: 95, grade: "A" },
  { name: "Eve", score: 88, grade: "B" }
];

// Top 3 students by score
let topStudents = limit(
  reverse(sorted(students, "score")),
  3
);

// Add ranking
export default = map(topStudents, "s",
  let index = indexOf(topStudents, s);
  {
    rank: index + 1,
    name: s.name,
    score: s.score,
    grade: s.grade
  }
);

// Result: [
//   { rank: 1, name: "David", score: 95, grade: "A" },
//   { rank: 2, name: "Bob", score: 92, grade: "A" },
//   { rank: 3, name: "Eve", score: 88, grade: "B" }
// ]
```

## Sorting with Multiple Criteria

While sorted() doesn't directly support multi-key sorting, you can achieve it through mapping:

```javascript
let data = [
  { category: "B", priority: 2, name: "Item2" },
  { category: "A", priority: 1, name: "Item1" },
  { category: "A", priority: 2, name: "Item3" },
  { category: "B", priority: 1, name: "Item4" }
];

// Sort by category, then by priority (using composite key)
let withSortKey = map(data, "item", {
  sortKey: item.category + "-" + toString(item.priority),
  data: item
});

export default = map(
  sorted(withSortKey, "sortKey"),
  "item",
  item.data
);
// Result: Sorted by category first, then priority
```

## Error Handling
```javascript
// Handle null or undefined arrays
sorted(exists($.items) ? $.items : [])

// Handle missing property
sorted(items, exists(items[0].price) ? "price" : "id")

// Safe sort with default
let data = $.data;
sorted(data.isArray ? data : [])
```

## Edge Cases

### Empty Array
```javascript
sorted([])  // Result: []
```

### Single Element
```javascript
sorted([42])  // Result: [42]
```

### Already Sorted
```javascript
sorted([1, 2, 3, 4])  // Result: [1, 2, 3, 4]
```

### All Same Values
```javascript
sorted([5, 5, 5])  // Result: [5, 5, 5]
```

### Missing Property
```javascript
let items = [
  { name: "A", price: 10 },
  { name: "B" },  // Missing price
  { name: "C", price: 5 }
];

sorted(items, "price")
// Items without the property are treated as having null/undefined
```

## Combining Sorted with Other Functions

### Sorted + Filter + Map
```javascript
let products = $.products;

export default = map(
  limit(
    reverse(
      sorted(
        filter(products, "p", p.inStock && p.rating >= 4.0),
        "rating"
      )
    ),
    10
  ),
  "p",
  { name: p.name, rating: p.rating, price: p.price }
);
// Top 10 highly-rated in-stock products
```

### Sorted + Distinct
```javascript
let tags = flatMap($.articles, "a", a.tags);

export default = sorted(distinct(tags));
// Unique tags in alphabetical order
```

### Sorted + GroupBy
```javascript
// Sort items within each group
let byCategory = groupBy($.products, "category");

export default = map(
  entries(byCategory),
  "entry",
  {
    category: entry.key,
    items: sorted(entry.value, "name")
  }
);
```

## See Also
- [reverse()](reverse.md) - Reverse array order
- [filter()](filter.md) - Filter array elements
- [limit()](limit.md) - Take first N elements
- [skip()](skip.md) - Skip first N elements
- [min()](min.md) - Find minimum value
- [max()](max.md) - Find maximum value
- [distinct()](distinct.md) - Remove duplicates
