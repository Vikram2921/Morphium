# For-In Loops

Comprehensive guide to for-in loops for index-based array iteration in Morphium DSL.

---

## Syntax

```javascript
for (index in array) {
  expression
}
```

### Parameters
- **index**: Variable name for the current index (0-based integer)
- **array**: Array expression to iterate over
- **expression**: Body expression evaluated for each index

### Returns
An array containing the results of each iteration (non-null values).

---

## Description

The for-in loop iterates over array indices, providing the numeric index position for each element. This is useful when you need to know the position of elements or perform index-based operations.

**Key Features:**
- Iterates over array indices (0, 1, 2, ...)
- Provides numeric index access
- Creates new scope for each iteration
- Supports break and continue statements
- Can be combined with array access

---

## Basic Examples

### Example 1: Simple Index Iteration

```javascript
// Input
{
  "items": ["apple", "banana", "cherry", "date"]
}

// Script
{
  indexed: for (i in $.items) {
    {
      index: i,
      value: $.items[i]
    }
  }
}

// Output
{
  "indexed": [
    {"index": 0, "value": "apple"},
    {"index": 1, "value": "banana"},
    {"index": 2, "value": "cherry"},
    {"index": 3, "value": "date"}
  ]
}
```

### Example 2: Position-Based Processing

```javascript
// Input
{
  "scores": [85, 92, 78, 95, 88]
}

// Script
{
  rankings: for (i in $.scores) {
    {
      position: i + 1,
      score: $.scores[i],
      medal: i == 0 ? "🥇" : i == 1 ? "🥈" : i == 2 ? "🥉" : ""
    }
  }
}

// Output
{
  "rankings": [
    {"position": 1, "score": 85, "medal": "🥇"},
    {"position": 2, "score": 92, "medal": "🥈"},
    {"position": 3, "score": 78, "medal": "🥉"},
    {"position": 4, "score": 95, "medal": ""},
    {"position": 5, "score": 88, "medal": ""}
  ]
}
```

### Example 3: Conditional by Index

```javascript
// Input
{
  "values": [10, 20, 30, 40, 50]
}

// Script
{
  evenIndexValues: for (i in $.values) {
    i % 2 == 0 ? $.values[i] : null
  }
}

// Output
{
  "evenIndexValues": [10, 30, 50]
}
```

---

## With Break and Continue

### Using break

Stop iteration at a specific index.

```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
}

// Script
{
  firstFive: for (i in $.numbers) {
    i >= 5 ? break : null;
    $.numbers[i] * 2
  }
}

// Output
{
  "firstFive": [2, 4, 6, 8, 10]
}
```

**Example: Break on Condition**
```javascript
// Input
{
  "items": [5, 10, 15, 20, 25, 30]
}

// Script
{
  untilLarge: for (i in $.items) {
    $.items[i] > 20 ? break : null;
    {
      index: i,
      value: $.items[i]
    }
  }
}

// Output
{
  "untilLarge": [
    {"index": 0, "value": 5},
    {"index": 1, "value": 10},
    {"index": 2, "value": 15},
    {"index": 3, "value": 20}
  ]
}
```

### Using continue

Skip specific indices.

```javascript
// Input
{
  "data": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
}

// Script
{
  skipOddIndices: for (i in $.data) {
    i % 2 != 0 ? continue : null;
    $.data[i]
  }
}

// Output
{
  "skipOddIndices": [1, 3, 5, 7, 9]
}
```

**Example: Skip Based on Value and Index**
```javascript
// Input
{
  "values": [10, 20, 30, 40, 50, 60, 70, 80]
}

// Script
{
  selective: for (i in $.values) {
    // Skip first and last
    i == 0 || i == len($.values) - 1 ? continue : null;
    
    // Skip values less than 40
    $.values[i] < 40 ? continue : null;
    
    {
      position: i,
      value: $.values[i]
    }
  }
}

// Output
{
  "selective": [
    {"position": 3, "value": 40},
    {"position": 4, "value": 50},
    {"position": 5, "value": 60}
  ]
}
```

---

## Advanced Examples

### Example 1: Pair Adjacent Elements

```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5]
}

// Script
{
  pairs: for (i in $.numbers) {
    // Skip last index (no pair)
    i == len($.numbers) - 1 ? break : null;
    
    {
      first: $.numbers[i],
      second: $.numbers[i + 1],
      sum: $.numbers[i] + $.numbers[i + 1]
    }
  }
}

// Output
{
  "pairs": [
    {"first": 1, "second": 2, "sum": 3},
    {"first": 2, "second": 3, "sum": 5},
    {"first": 3, "second": 4, "sum": 7},
    {"first": 4, "second": 5, "sum": 9}
  ]
}
```

### Example 2: Window Operations

```javascript
// Input
{
  "prices": [100, 105, 103, 108, 110, 107, 112]
}

// Script
{
  changes: for (i in $.prices) {
    // Skip first element
    i == 0 ? continue : null;
    
    let current = $.prices[i];
    let previous = $.prices[i - 1];
    let change = current - previous;
    
    {
      day: i,
      price: current,
      change: change,
      percentChange: (change / previous) * 100
    }
  }
}

// Output
{
  "changes": [
    {"day": 1, "price": 105, "change": 5, "percentChange": 5.0},
    {"day": 2, "price": 103, "change": -2, "percentChange": -1.9},
    {"day": 3, "price": 108, "change": 5, "percentChange": 4.85},
    {"day": 4, "price": 110, "change": 2, "percentChange": 1.85},
    {"day": 5, "price": 107, "change": -3, "percentChange": -2.73},
    {"day": 6, "price": 112, "change": 5, "percentChange": 4.67}
  ]
}
```

### Example 3: Chunking Arrays

```javascript
// Input
{
  "items": [1, 2, 3, 4, 5, 6, 7, 8, 9]
}

// Script
let chunkSize = 3;

{
  chunks: for (i in $.items) {
    // Only process at chunk boundaries
    i % chunkSize != 0 ? continue : null;
    
    // Break if not enough elements for full chunk
    i + chunkSize > len($.items) ? break : null;
    
    {
      chunkIndex: i / chunkSize,
      items: [
        $.items[i],
        $.items[i + 1],
        $.items[i + 2]
      ]
    }
  }
}

// Output
{
  "chunks": [
    {"chunkIndex": 0, "items": [1, 2, 3]},
    {"chunkIndex": 1, "items": [4, 5, 6]},
    {"chunkIndex": 2, "items": [7, 8, 9]}
  ]
}
```

### Example 4: Index-Based Filtering

```javascript
// Input
{
  "data": [
    {"value": 10, "type": "A"},
    {"value": 20, "type": "B"},
    {"value": 30, "type": "A"},
    {"value": 40, "type": "B"},
    {"value": 50, "type": "A"}
  ]
}

// Script
{
  processed: for (i in $.data) {
    let item = $.data[i];
    
    // Skip even indices
    i % 2 == 0 ? continue : null;
    
    // Break after processing 2 items
    i > 4 ? break : null;
    
    {
      originalIndex: i,
      value: item.value * 2,
      type: item.type
    }
  }
}

// Output
{
  "processed": [
    {"originalIndex": 1, "value": 40, "type": "B"},
    {"originalIndex": 3, "value": 80, "type": "B"}
  ]
}
```

---

## Comparing Indices

### Example: Finding Differences

```javascript
// Input
{
  "series": [10, 15, 12, 18, 20]
}

// Script
{
  analysis: for (i in $.series) {
    let current = $.series[i];
    let isFirst = i == 0;
    let isLast = i == len($.series) - 1;
    
    {
      index: i,
      value: current,
      isFirst: isFirst,
      isLast: isLast,
      previous: !isFirst ? $.series[i - 1] : null,
      next: !isLast ? $.series[i + 1] : null
    }
  }
}
```

---

## Common Patterns

### Pattern 1: Skip First/Last

```javascript
for (i in $.items) {
  i == 0 || i == len($.items) - 1 ? continue : null;
  $.items[i]
}
```

### Pattern 2: Process Pairs

```javascript
for (i in $.items) {
  i % 2 != 0 ? continue : null;
  i + 1 >= len($.items) ? break : null;
  {
    pair: [$.items[i], $.items[i + 1]]
  }
}
```

### Pattern 3: Take First N

```javascript
for (i in $.items) {
  i >= 10 ? break : null;
  $.items[i]
}
```

### Pattern 4: Every Nth Element

```javascript
let n = 3;
for (i in $.items) {
  i % n != 0 ? continue : null;
  $.items[i]
}
```

---

## For-In vs For-Of

| Feature | For-In | For-Of |
|---------|--------|--------|
| Iterates over | Indices (0, 1, 2...) | Elements directly |
| Variable type | Number | Same as element |
| Access element | `array[index]` | Direct (`element`) |
| Use when | Need index position | Just need values |
| Pair elements | Easy | Harder |
| Position matters | Yes | No |

### When to Use For-In

✅ **Use For-In When:**
- Need to know element position/index
- Need to access neighboring elements
- Performing index-based operations
- Comparing with previous/next elements
- Implementing window/sliding operations

```javascript
// For-in is better
for (i in $.prices) {
  i == 0 ? continue : null;
  let change = $.prices[i] - $.prices[i - 1];
  { day: i, change: change }
}
```

### When to Use For-Of

✅ **Use For-Of When:**
- Only need element values
- Don't care about position
- Simple transformations
- More readable code without indices

```javascript
// For-of is better
for (product of $.products) {
  { name: product.name, price: product.price * 1.1 }
}
```

---

## Best Practices

### ✅ Do:
- Use for-in when index position matters
- Check array bounds before accessing neighboring elements
- Use meaningful index variable names (i, idx, index)
- Combine with for-of for nested iterations
- Use continue to skip irrelevant indices

### ❌ Don't:
- Don't use for-in when for-of would be clearer
- Don't forget array bounds checking
- Don't modify array length during iteration
- Don't create overly complex index arithmetic
- Don't use when simple map() would work

---

## Performance Tips

1. **Cache Length**: Store `len(array)` in variable if used multiple times
2. **Early Break**: Exit early when condition met
3. **Skip with Continue**: More efficient than nested ifs
4. **Index Math**: Pre-compute complex index calculations

---

## Related Topics

- [For-Of Loops](10-for-of.md) - Value-based iteration
- [Break & Continue](12-break-continue.md) - Loop control flow
- [Arrays](05-data-types.md#array) - Array data type
- [map() Function](functions/map.md) - Functional alternative

---

**See Also:**
- [Quick Start](01-quick-start.md)
- [Basic Concepts](02-basic-concepts.md)
- [Variables and Scope](04-variables-scope.md)
