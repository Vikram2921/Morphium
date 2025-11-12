# Break & Continue

Complete guide to loop control flow with break and continue statements in Morphium DSL.

---

## Overview

Break and continue statements provide fine-grained control over loop execution:
- **break**: Exits the loop immediately
- **continue**: Skips the current iteration and continues with the next

Both work with **for-of** and **for-in** loops.

---

## Break Statement

### Syntax

```javascript
for (item of array) {
  condition ? break : null;
  expression
}
```

### Description

The `break` statement immediately terminates the loop and returns all results collected up to that point.

---

## Break Examples

### Example 1: Exit on Condition

```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
}

// Script
{
  untilSix: for (num of $.numbers) {
    num > 5 ? break : null;
    num * 2
  }
}

// Output
{
  "untilSix": [2, 4, 6, 8, 10]
}
```

### Example 2: Find First Match

```javascript
// Input
{
  "users": [
    {"name": "Alice", "role": "user"},
    {"name": "Bob", "role": "admin"},
    {"name": "Charlie", "role": "user"}
  ]
}

// Script
{
  firstAdmin: for (user of $.users) {
    user.role == "admin" ? break : null;
    null
  }
}

// To actually return the found item:
{
  firstAdmin: for (user of $.users) {
    if (user.role == "admin") {
      break;
      user
    } else {
      null
    };
    user
  }
}
```

### Example 3: Budget Limit

```javascript
// Input
{
  "items": [
    {"name": "Book", "price": 15},
    {"name": "Pen", "price": 5},
    {"name": "Notebook", "price": 10},
    {"name": "Laptop", "price": 500},
    {"name": "Mouse", "price": 20}
  ]
}

// Script
global budget = 50;
global spent = 0;

{
  purchased: for (item of $.items) {
    let newTotal = spent + item.price;
    
    newTotal > budget ? break : null;
    
    global spent = newTotal;
    {
      name: item.name,
      price: item.price,
      remainingBudget: budget - spent
    }
  },
  totalSpent: spent
}

// Output
{
  "purchased": [
    {"name": "Book", "price": 15, "remainingBudget": 35},
    {"name": "Pen", "price": 5, "remainingBudget": 30},
    {"name": "Notebook", "price": 10, "remainingBudget": 20}
  ],
  "totalSpent": 30
}
```

### Example 4: Early Exit with Index

```javascript
// Input
{
  "data": [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
}

// Script
{
  firstFive: for (i in $.data) {
    i >= 5 ? break : null;
    $.data[i]
  }
}

// Output
{
  "firstFive": [10, 20, 30, 40, 50]
}
```

---

## Continue Statement

### Syntax

```javascript
for (item of array) {
  condition ? continue : null;
  expression
}
```

### Description

The `continue` statement skips the rest of the current iteration and immediately proceeds to the next iteration.

---

## Continue Examples

### Example 1: Skip Condition

```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
}

// Script
{
  evenOnly: for (num of $.numbers) {
    num % 2 != 0 ? continue : null;
    num
  }
}

// Output
{
  "evenOnly": [2, 4, 6, 8, 10]
}
```

### Example 2: Skip Invalid Items

```javascript
// Input
{
  "products": [
    {"name": "Widget", "price": 10, "available": true},
    {"name": "Gadget", "price": 20, "available": false},
    {"name": "Tool", "price": 15, "available": true},
    {"name": "Device", "price": 25, "available": false}
  ]
}

// Script
{
  availableProducts: for (product of $.products) {
    !product.available ? continue : null;
    {
      name: product.name,
      price: product.price
    }
  }
}

// Output
{
  "availableProducts": [
    {"name": "Widget", "price": 10},
    {"name": "Tool", "price": 15}
  ]
}
```

### Example 3: Multiple Skip Conditions

```javascript
// Input
{
  "transactions": [
    {"id": 1, "amount": 100, "status": "completed", "verified": true},
    {"id": 2, "amount": 50, "status": "pending", "verified": true},
    {"id": 3, "amount": 200, "status": "completed", "verified": false},
    {"id": 4, "amount": 75, "status": "completed", "verified": true},
    {"id": 5, "amount": 0, "status": "completed", "verified": true}
  ]
}

// Script
{
  valid: for (txn of $.transactions) {
    // Skip non-completed
    txn.status != "completed" ? continue : null;
    
    // Skip unverified
    !txn.verified ? continue : null;
    
    // Skip zero amounts
    txn.amount == 0 ? continue : null;
    
    {
      id: txn.id,
      amount: txn.amount
    }
  }
}

// Output
{
  "valid": [
    {"id": 1, "amount": 100},
    {"id": 4, "amount": 75}
  ]
}
```

### Example 4: Skip by Index

```javascript
// Input
{
  "values": [10, 20, 30, 40, 50, 60, 70, 80]
}

// Script
{
  oddIndices: for (i in $.values) {
    i % 2 == 0 ? continue : null;
    $.values[i]
  }
}

// Output
{
  "oddIndices": [20, 40, 60, 80]
}
```

---

## Combining Break and Continue

### Example 1: Complex Filtering

```javascript
// Input
{
  "items": [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]
}

// Script
{
  result: for (item of $.items) {
    // Skip items less than 15
    item < 15 ? continue : null;
    
    // Stop at items greater than 35
    item > 35 ? break : null;
    
    item
  }
}

// Output
{
  "result": [15, 20, 25, 30, 35]
}
```

### Example 2: Validation Pipeline

```javascript
// Input
{
  "records": [
    {"id": 1, "value": 100, "valid": true, "priority": "high"},
    {"id": 2, "value": 50, "valid": false, "priority": "low"},
    {"id": 3, "value": 200, "valid": true, "priority": "high"},
    {"id": 4, "value": 150, "valid": true, "priority": "medium"},
    {"id": 5, "value": 300, "valid": true, "priority": "high"}
  ]
}

// Script
global count = 0;

{
  processed: for (record of $.records) {
    // Skip invalid records
    !record.valid ? continue : null;
    
    // Skip low priority
    record.priority == "low" ? continue : null;
    
    // Process only 3 records
    count >= 3 ? break : null;
    global count = count + 1;
    
    {
      id: record.id,
      value: record.value,
      priority: record.priority
    }
  }
}

// Output
{
  "processed": [
    {"id": 1, "value": 100, "priority": "high"},
    {"id": 3, "value": 200, "priority": "high"},
    {"id": 4, "value": 150, "priority": "medium"}
  ]
}
```

---

## Nested Loops

### Example: Break Inner Loop

```javascript
// Input
{
  "matrix": [
    [1, 2, 3, 4],
    [5, 6, 7, 8],
    [9, 10, 11, 12]
  ]
}

// Script
{
  result: for (row of $.matrix) {
    for (val of row) {
      val > 7 ? break : null;
      val
    }
  }
}

// Output
{
  "result": [1, 2, 3, 4, 5, 6, 7, 9, 10]
}
```

### Example: Continue Outer Loop Pattern

```javascript
// Input
{
  "groups": [
    {"name": "A", "items": [1, 2, 3]},
    {"name": "B", "items": []},
    {"name": "C", "items": [4, 5, 6]},
    {"name": "D", "items": []}
  ]
}

// Script
{
  result: for (group of $.groups) {
    // Skip empty groups
    len(group.items) == 0 ? continue : null;
    
    {
      group: group.name,
      items: for (item of group.items) {
        item * 2
      }
    }
  }
}

// Output
{
  "result": [
    {"group": "A", "items": [2, 4, 6]},
    {"group": "C", "items": [8, 10, 12]}
  ]
}
```

---

## Common Patterns

### Pattern 1: Take While

```javascript
for (item of $.items) {
  !condition(item) ? break : null;
  item
}
```

### Pattern 2: Skip While

```javascript
global skipDone = false;
for (item of $.items) {
  !skipDone && !condition(item) ? continue : null;
  global skipDone = true;
  item
}
```

### Pattern 3: Find First

```javascript
for (item of $.items) {
  condition(item) ? break : null;
  null
};
// Need different pattern - use findFirst() instead
```

### Pattern 4: Filter and Limit

```javascript
global count = 0;
let limit = 5;

for (item of $.items) {
  !condition(item) ? continue : null;
  count >= limit ? break : null;
  global count = count + 1;
  item
}
```

---

## Best Practices

### ✅ Do:
- Use break for early exit when condition met
- Use continue to skip invalid/unwanted items
- Combine break and continue for complex filtering
- Use with global variables for stateful iteration
- Comment complex break/continue logic

### ❌ Don't:
- Don't use when simple filter() would work
- Don't nest break/continue more than 2 levels
- Don't forget break returns partial results
- Don't use continue when you can reverse the condition
- Don't make logic too complex to follow

---

## Alternative Approaches

### Instead of Complex Break/Continue

**Complex:**
```javascript
for (item of $.items) {
  item.value < 10 ? continue : null;
  item.status != "active" ? continue : null;
  !item.verified ? continue : null;
  item
}
```

**Simpler:**
```javascript
filter($.items, "item", 
  item.value >= 10 && 
  item.status == "active" && 
  item.verified
)
```

### When You Need Early Exit

```javascript
// Good use of break
for (item of $.items) {
  item.value > 1000 ? break : null;
  transform(item)
}
```

---

## Performance Tips

1. **Early Exit**: Use break to avoid unnecessary processing
2. **Skip Fast**: Use continue instead of nested conditionals
3. **Condition Order**: Put most likely skip conditions first
4. **Combine Conditions**: Group related checks together

---

## Related Topics

- [For-Of Loops](10-for-of.md) - Element iteration
- [For-In Loops](11-for-in.md) - Index iteration
- [filter() Function](functions/filter.md) - Alternative filtering
- [findFirst() Function](functions/findFirst.md) - Finding elements

---

**See Also:**
- [Quick Start](01-quick-start.md)
- [Control Flow](08-if-else.md)
- [Arrays](05-data-types.md#array)
