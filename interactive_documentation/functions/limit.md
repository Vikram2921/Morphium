# limit() - Take First N Elements

## Overview
The `limit()` function returns the first N elements from an array, useful for pagination and restricting result sizes.

## Syntax
```javascript
limit(array, n)
```

## Parameters
- **array**: The input array
- **n**: Maximum number of elements to return

## Return Value
Returns a new array containing at most N elements from the beginning.

## Basic Examples

### Example 1: First 5 Elements
```javascript
let numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

export default = limit(numbers, 5);
// Result: [1, 2, 3, 4, 5]
```

### Example 2: Top 10 Results
```javascript
let products = reverse(sorted($.products, "rating"));

export default = limit(products, 10);
// Top 10 highest-rated products
```

### Example 3: Preview Items
```javascript
let articles = $.articles;

export default = {
  preview: limit(articles, 3),
  total: len(articles),
  hasMore: len(articles) > 3
};
```

## Advanced Examples

### Example 4: Top N with Ranking
```javascript
let players = reverse(sorted($.players, "score"));
let topPlayers = limit(players, 10);

export default = map(topPlayers, "p", {
  rank: indexOf(topPlayers, p) + 1,
  name: p.name,
  score: p.score
});
```

### Example 5: Pagination - First Page
```javascript
let allItems = $.items;
let pageSize = 20;

export default = {
  items: limit(allItems, pageSize),
  page: 1,
  pageSize: pageSize,
  total: len(allItems),
  totalPages: (len(allItems) + pageSize - 1) / pageSize
};
```

## Common Patterns

### Pattern 1: Simple Limit
```javascript
limit(array, n)
```

### Pattern 2: Top N (with sort)
```javascript
limit(reverse(sorted(array, "field")), n)
```

### Pattern 3: Limit + Skip (Pagination)
```javascript
limit(skip(array, offset), pageSize)
```

### Pattern 4: Filter + Limit
```javascript
limit(filter(array, "item", condition), n)
```

## Common Use Cases

- **Pagination** - First page of results
- **Top N queries** - Leaderboards, best sellers
- **Preview/Summary** - Show first few items
- **Rate limiting** - Restrict output size
- **Performance optimization** - Process fewer items

## Related Functions

- **skip()** - Skip first N elements
- **slice()** - Extract range
- **sorted()** - Sort arrays (often used before limit)
- **filter()** - Conditional selection

## Example: Top Products Dashboard
```javascript
let products = $.products;

export default = {
  topRated: limit(reverse(sorted(products, "rating")), 5),
  topSelling: limit(reverse(sorted(products, "salesCount")), 5),
  newest: limit(reverse(sorted(products, "createdAt")), 5),
  onSale: limit(filter(products, "p", p.onSale), 10)
};
```

## See Also
- [skip()](skip.md) - Skip first N elements
- [slice()](slice.md) - Extract array slice
- [sorted()](sorted.md) - Sort arrays
- [filter()](filter.md) - Filter elements
