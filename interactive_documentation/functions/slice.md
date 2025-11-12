# slice() - Extract Array Slice

## Overview
The `slice()` function extracts a portion of an array, returning a new array containing elements from a start index to an end index (exclusive).

## Syntax
```javascript
slice(array, start, end)
```

## Parameters
- **array**: The input array to slice
- **start**: Starting index (inclusive)
- **end**: Ending index (exclusive)

## Return Value
Returns a new array containing elements from start to end-1.

## Basic Examples

### Example 1: Extract Middle Elements
```javascript
let numbers = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9];

export default = slice(numbers, 3, 7);
// Result: [3, 4, 5, 6]
```

### Example 2: First N Elements
```javascript
let items = ["a", "b", "c", "d", "e"];

export default = slice(items, 0, 3);
// Result: ["a", "b", "c"]
```

### Example 3: Skip and Take
```javascript
let data = [10, 20, 30, 40, 50, 60];

export default = slice(data, 2, 5);
// Result: [30, 40, 50]
```

## Common Patterns

### Pattern 1: First N Elements (alternative to limit)
```javascript
slice(array, 0, n)
```

### Pattern 2: Skip First N (alternative to skip)
```javascript
slice(array, n, len(array))
```

### Pattern 3: Middle Range
```javascript
slice(array, start, end)
```

### Pattern 4: Pagination
```javascript
let pageSize = 10;
let page = 2;  // Zero-indexed
slice(array, page * pageSize, (page + 1) * pageSize)
```

## Common Use Cases

- **Pagination** - Extract page ranges
- **Windowing** - Process data in chunks
- **Range extraction** - Get specific portions
- **Preview generation** - Show first N items
- **Batch processing** - Split into manageable chunks

## Related Functions

- **limit()** - Take first N elements
- **skip()** - Skip first N elements
- **filter()** - Select elements by condition
- **concat()** - Combine array portions

## Example: Pagination
```javascript
let allItems = $.items;
let pageSize = 10;
let currentPage = $.page || 0;

export default = {
  items: slice(allItems, currentPage * pageSize, (currentPage + 1) * pageSize),
  page: currentPage,
  pageSize: pageSize,
  totalItems: len(allItems),
  totalPages: (len(allItems) + pageSize - 1) / pageSize
};
```

## See Also
- [limit()](limit.md) - Take first N elements
- [skip()](skip.md) - Skip first N elements
- [concat()](concat.md) - Combine arrays
