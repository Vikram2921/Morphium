# skip() - Skip First N Elements

## Overview
The `skip()` function skips the first N elements of an array and returns the remaining elements.

## Syntax
```javascript
skip(array, n)
```

## Parameters
- **array**: The input array
- **n**: Number of elements to skip from the beginning

## Return Value
Returns a new array with the first N elements removed.

## Basic Examples

### Example 1: Skip First 3
```javascript
let numbers = [1, 2, 3, 4, 5, 6, 7];

export default = skip(numbers, 3);
// Result: [4, 5, 6, 7]
```

### Example 2: Pagination - Skip to Page 2
```javascript
let items = $.items;
let pageSize = 10;

export default = limit(skip(items, pageSize), pageSize);
// Gets page 2 (items 11-20)
```

### Example 3: Skip Header Row
```javascript
let csvData = $.rows;

export default = skip(csvData, 1);
// Skips header, returns data rows
```

## Advanced Examples

### Example 4: Offset Pagination
```javascript
let allProducts = $.products;
let offset = $.offset || 0;
let limit = $.limit || 20;

export default = {
  products: limit(skip(allProducts, offset), limit),
  offset: offset,
  limit: limit,
  total: len(allProducts),
  hasMore: len(allProducts) > (offset + limit)
};
```

### Example 5: Skip and Process
```javascript
let queue = $.tasks;

// Skip first 5 tasks (already processed)
let remaining = skip(queue, 5);

export default = {
  remainingTasks: remaining,
  count: len(remaining)
};
```

## Common Patterns

### Pattern 1: Simple Skip
```javascript
skip(array, n)
```

### Pattern 2: Skip + Limit (Pagination)
```javascript
limit(skip(array, offset), pageSize)
```

### Pattern 3: Skip + Process
```javascript
map(skip(array, n), "item", transform(item))
```

## Common Use Cases

- **Pagination** - Offset-based paging
- **Skip headers** - Remove header rows
- **Process later items** - Skip initial elements
- **Batch processing** - Skip already processed items
- **Data windowing** - Skip to specific range

## Related Functions

- **limit()** - Take first N elements
- **slice()** - Extract range
- **filter()** - Conditional selection

## See Also
- [limit()](limit.md) - Take first N elements
- [slice()](slice.md) - Extract array slice
- [sorted()](sorted.md) - Sort arrays
