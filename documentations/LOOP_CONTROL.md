# Loop Control: Break, Continue, and For-In

## Overview
Morphium DSL now supports complete loop control with `break`, `continue`, and indexed `for-in` loops.

---

## 1. Break Statement

### Syntax
```javascript
break
```

### Description
Exits the current loop immediately. No further iterations will be executed.

### Examples

#### Break with Ternary
```javascript
{
  results: for (n of $.numbers) {
    n > 10 ? break : null;
    n * 2
  }
}

// Input: {"numbers": [1, 5, 8, 12, 15]}
// Output: {"results": [2, 10, 16]}  // Stops at 12
```

#### Early Exit on Condition
```javascript
{
  found: for (item of $.items) {
    item.status == 'error' ? break : null;
    item
  }
}
```

---

## 2. Continue Statement

### Syntax
```javascript
continue
```

### Description
Skips the rest of the current iteration and continues with the next iteration.

### Examples

#### Skip Even Numbers
```javascript
{
  odds: for (n of $.numbers) {
    n % 2 == 0 ? continue : null;
    n
  }
}

// Input: {"numbers": [1, 2, 3, 4, 5, 6]}
// Output: {"odds": [1, 3, 5]}
```

#### Filter with Continue
```javascript
{
  activeUsers: for (user of $.users) {
    !user.active ? continue : null;
    { name: user.name, email: user.email }
  }
}
```

---

## 3. For-In Loop (Indexed Loop)

### Syntax
```javascript
for (index in array) {
  // body
}
```

### Description
Iterates over array indices (0, 1, 2, ...) instead of values. The loop variable contains the numeric index.

### Examples

#### Basic Index Iteration
```javascript
{
  indices: for (i in $.items) {
    i
  }
}

// Input: {"items": ["a", "b", "c"]}
// Output: {"indices": [0, 1, 2]}
```

#### Index with Value Access
```javascript
{
  result: for (i in $.items) {
    {
      index: i,
      value: $.items[i]
    }
  }
}

// Input: {"items": ["apple", "banana", "cherry"]}
// Output: {
//   "result": [
//     {"index": 0, "value": "apple"},
//     {"index": 1, "value": "banana"},
//     {"index": 2, "value": "cherry"}
//   ]
// }
```

#### Processing Every Nth Element
```javascript
{
  everyThird: for (i in $.data) {
    i % 3 != 0 ? continue : null;
    $.data[i]
  }
}

// Gets elements at indices 0, 3, 6, 9, ...
```

---

## Combining Break and Continue

You can use both in the same loop for complex control flow:

```javascript
{
  filtered: for (n of $.numbers) {
    n > 100 ? break : null;      // Stop if too large
    n < 10 ? continue : null;    // Skip if too small
    n                             // Keep if in range [10, 100]
  }
}

// Input: {"numbers": [5, 15, 25, 8, 35, 105, 110]}
// Output: {"filtered": [15, 25, 35]}
```

---

## Nested Loops

Break and continue only affect the innermost loop:

```javascript
{
  pairs: for (i of $.list1) {
    for (j of $.list2) {
      j > 3 ? break : null;     // Breaks inner loop only
      { i: i, j: j }
    }
  }
}
```

---

## For-Of vs For-In

### For-Of (Values)
- Iterates over array **values**
- Loop variable contains the element
- Use when you need the actual data

```javascript
for (item of $.items) {
  // item is the actual value
  item.name
}
```

### For-In (Indices)
- Iterates over array **indices**
- Loop variable contains the index number
- Use when you need position or want to access multiple arrays

```javascript
for (i in $.items) {
  // i is the index (0, 1, 2, ...)
  {
    index: i,
    item: $.items[i],
    other: $.otherArray[i]
  }
}
```

---

## Best Practices

### 1. Use Ternary for Single Conditions

```javascript
// Good - concise
n > 10 ? break : null;

// Also works - more traditional
if (n > 10) break;
```

### 2. Break vs Filter

```javascript
// Use break when you want to stop early
for (item of $.items) {
  item.error ? break : null;
  process(item)
}

// Use filter when you want to skip items
for (item of $.items) {
  !item.active ? continue : null;
  process(item)
}
```

### 3. For-In for Paired Arrays

```javascript
// Good - process two arrays together
for (i in $.names) {
  {
    name: $.names[i],
    age: $.ages[i],
    score: $.scores[i]
  }
}
```

### 4. Early Exit Pattern

```javascript
// Find first match and stop
let found = for (item of $.items) {
  item.id == $.searchId ? break : null;
  null
};
// Last non-null value before break is the found item
```

---

## Performance Considerations

- **Break** can significantly improve performance by avoiding unnecessary iterations
- **Continue** is cleaner than nested if statements for filtering
- **For-in** is as fast as for-of, choose based on what you need

---

## Limitations

1. **No labeled break/continue** - Cannot break/continue outer loops from inner loops
2. **Break/continue only in loops** - Using outside a loop will throw an error
3. **No for(i=0; i<n; i++)** - Use `for (i in array)` instead

---

## Complete Example

```javascript
{
  processed: for (i in $.orders) {
    let order = $.orders[i];
    
    // Skip cancelled orders
    order.status == 'cancelled' ? continue : null;
    
    // Stop if we hit an error
    order.status == 'error' ? break : null;
    
    // Process valid order
    {
      orderNum: i + 1,
      id: order.id,
      total: order.total,
      status: order.status
    }
  }
}
```

---

## Examples

See `LoopControlTest.java` for comprehensive test cases demonstrating all features.

---

## Version

Added in: Morphium DSL 1.0.0-SNAPSHOT  
Date: 2025-11-12
