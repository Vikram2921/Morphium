# For-Of Loops

Comprehensive guide to for-of loops for iterating over array elements in Morphium DSL.

---

## Syntax

```javascript
for (item of array) {
  expression
}
```

### Parameters
- **item**: Variable name for the current element
- **array**: Array expression to iterate over
- **expression**: Body expression evaluated for each element

### Returns
An array containing the results of each iteration (non-null values).

---

## Description

The for-of loop iterates over array elements, binding each element to a variable and evaluating the body expression. It's the primary way to process arrays element-by-element.

**Key Features:**
- Iterates over array values directly
- Creates new scope for each iteration
- Collects non-null results into an array
- Supports break and continue statements
- Can be nested

---

## Basic Examples

### Example 1: Simple Iteration

```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5]
}

// Script
{
  doubled: for (num of $.numbers) {
    num * 2
  }
}

// Output
{
  "doubled": [2, 4, 6, 8, 10]
}
```

### Example 2: Object Processing

```javascript
// Input
{
  "users": [
    {"name": "Alice", "age": 30},
    {"name": "Bob", "age": 25},
    {"name": "Charlie", "age": 35}
  ]
}

// Script
{
  names: for (user of $.users) {
    user.name
  },
  adults: for (user of $.users) {
    user.age >= 18 ? user.name : null
  }
}

// Output
{
  "names": ["Alice", "Bob", "Charlie"],
  "adults": ["Alice", "Bob", "Charlie"]
}
```

### Example 3: Transformation

```javascript
// Input
{
  "products": [
    {"name": "Widget", "price": 10},
    {"name": "Gadget", "price": 20},
    {"name": "Tool", "price": 15}
  ]
}

// Script
{
  withTax: for (product of $.products) {
    {
      name: product.name,
      price: product.price,
      tax: product.price * 0.08,
      total: product.price * 1.08
    }
  }
}

// Output
{
  "withTax": [
    {"name": "Widget", "price": 10, "tax": 0.8, "total": 10.8},
    {"name": "Gadget", "price": 20, "tax": 1.6, "total": 21.6},
    {"name": "Tool", "price": 15, "tax": 1.2, "total": 16.2}
  ]
}
```

---

## Break and Continue

### Using break

Break immediately exits the loop and returns results collected so far.

```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
}

// Script
{
  untilFive: for (num of $.numbers) {
    num > 5 ? break : num * 2
  }
}

// Output
{
  "untilFive": [2, 4, 6, 8, 10]
}
```

**Example: Find First Match**
```javascript
// Input
{
  "items": [
    {"id": 1, "status": "pending"},
    {"id": 2, "status": "active"},
    {"id": 3, "status": "active"},
    {"id": 4, "status": "pending"}
  ]
}

// Script
{
  firstActive: for (item of $.items) {
    if (item.status == "active") {
      break;
      item
    } else {
      null
    };
    item
  }
}

// Alternatively, simpler syntax:
{
  firstActive: for (item of $.items) {
    item.status == "active" ? break : null;
    item
  }
}
```

### Using continue

Continue skips the current iteration and moves to the next element.

```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
}

// Script
{
  evensOnly: for (num of $.numbers) {
    num % 2 != 0 ? continue : null;
    num
  }
}

// Output
{
  "evensOnly": [2, 4, 6, 8, 10]
}
```

**Example: Skip Invalid Items**
```javascript
// Input
{
  "data": [
    {"value": 10, "valid": true},
    {"value": 20, "valid": false},
    {"value": 30, "valid": true},
    {"value": 40, "valid": false},
    {"value": 50, "valid": true}
  ]
}

// Script
{
  validValues: for (item of $.data) {
    !item.valid ? continue : null;
    item.value * 2
  }
}

// Output
{
  "validValues": [20, 60, 100]
}
```

---

## Advanced Examples

### Example 1: Nested Loops

```javascript
// Input
{
  "orders": [
    {
      "id": "ORD-1",
      "items": [
        {"name": "Widget", "qty": 2, "price": 10},
        {"name": "Gadget", "qty": 1, "price": 20}
      ]
    },
    {
      "id": "ORD-2",
      "items": [
        {"name": "Tool", "qty": 3, "price": 15}
      ]
    }
  ]
}

// Script
{
  allItems: for (order of $.orders) {
    for (item of order.items) {
      {
        orderId: order.id,
        itemName: item.name,
        quantity: item.qty,
        itemTotal: item.qty * item.price
      }
    }
  }
}

// Output
{
  "allItems": [
    {"orderId": "ORD-1", "itemName": "Widget", "quantity": 2, "itemTotal": 20},
    {"orderId": "ORD-1", "itemName": "Gadget", "quantity": 1, "itemTotal": 20},
    {"orderId": "ORD-2", "itemName": "Tool", "quantity": 3, "itemTotal": 45}
  ]
}
```

### Example 2: Conditional Processing with Break/Continue

```javascript
// Input
{
  "transactions": [
    {"id": 1, "amount": 100, "type": "credit", "valid": true},
    {"id": 2, "amount": 50, "type": "debit", "valid": true},
    {"id": 3, "amount": 200, "type": "credit", "valid": false},
    {"id": 4, "amount": 75, "type": "debit", "valid": true},
    {"id": 5, "amount": 300, "type": "credit", "valid": true}
  ]
}

// Script
{
  processed: for (txn of $.transactions) {
    // Skip invalid transactions
    !txn.valid ? continue : null;
    
    // Stop at large transactions
    txn.amount > 250 ? break : null;
    
    {
      id: txn.id,
      amount: txn.amount,
      impact: txn.type == "credit" ? txn.amount : -txn.amount
    }
  }
}

// Output
{
  "processed": [
    {"id": 1, "amount": 100, "impact": 100},
    {"id": 2, "amount": 50, "impact": -50},
    {"id": 4, "amount": 75, "impact": -75}
  ]
}
```

### Example 3: Complex Transformation with Variables

```javascript
// Input
{
  "sales": [
    {"product": "A", "quantity": 10, "price": 5},
    {"product": "B", "quantity": 5, "price": 10},
    {"product": "C", "quantity": 20, "price": 3},
    {"product": "D", "quantity": 15, "price": 7}
  ]
}

// Script
global runningTotal = 0;

{
  items: for (sale of $.sales) {
    let itemTotal = sale.quantity * sale.price;
    global runningTotal = runningTotal + itemTotal;
    
    // Skip low-value items
    itemTotal < 40 ? continue : null;
    
    // Stop if total exceeds budget
    runningTotal > 200 ? break : null;
    
    {
      product: sale.product,
      itemTotal: itemTotal,
      runningTotal: runningTotal
    }
  },
  finalTotal: runningTotal
}
```

### Example 4: Early Exit Pattern

```javascript
// Input
{
  "values": [5, 10, 15, 20, 25, 30, 35, 40]
}

// Script
{
  // Process until we find a value divisible by 7
  result: for (val of $.values) {
    val % 7 == 0 ? break : null;
    val * 2
  }
}

// Output
{
  "result": [10, 20, 30, 40, 50, 60]
}
```

---

## Filtering Patterns

### Pattern 1: Filter with Continue

```javascript
// Input
{
  "items": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
}

// Script
{
  multiplesOfThree: for (item of $.items) {
    item % 3 != 0 ? continue : null;
    item
  }
}

// Output
{
  "multiplesOfThree": [3, 6, 9]
}
```

### Pattern 2: Take While Pattern

```javascript
// Input
{
  "numbers": [2, 4, 6, 8, 9, 10, 12]
}

// Script
{
  evenPrefix: for (num of $.numbers) {
    num % 2 != 0 ? break : null;
    num
  }
}

// Output
{
  "evenPrefix": [2, 4, 6, 8]
}
```

### Pattern 3: Limited Results

```javascript
// Input
{
  "items": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
}

// Script
global count = 0;

{
  firstFive: for (item of $.items) {
    global count = count + 1;
    count > 5 ? break : null;
    item * 2
  }
}

// Output
{
  "firstFive": [2, 4, 6, 8, 10]
}
```

---

## Comparison with map() and filter()

### When to Use For-Of

✅ **Use For-Of When:**
- Need break/continue control flow
- Need complex multi-step processing per iteration
- Need to maintain state across iterations (with global)
- Need nested loops
- Need early exit conditions

```javascript
// For-of is better here
for (item of $.items) {
  item.price > 100 ? break : null;
  !item.available ? continue : null;
  
  let discounted = item.price * 0.9;
  let taxed = discounted * 1.08;
  { name: item.name, final: taxed }
}
```

### When to Use map()/filter()

✅ **Use map()/filter() When:**
- Simple one-to-one transformations
- Simple filtering conditions
- More functional style preferred
- No need for break/continue
- Simpler, more readable code

```javascript
// map/filter is better here
filter(
  map($.items, "item", { name: item.name, price: item.price * 1.1 }),
  "item", 
  item.price < 100
)
```

---

## Best Practices

### ✅ Do:
- Use descriptive variable names for loop items
- Use break for early exit when condition met
- Use continue to skip invalid/unwanted items
- Return null to exclude items from results
- Keep loop bodies concise and focused

### ❌ Don't:
- Don't modify the source array during iteration
- Don't use when map()/filter() would be clearer
- Don't nest loops more than 2-3 levels deep
- Don't forget break can create incomplete results
- Don't use for-of on non-array values

---

## Performance Tips

1. **Early Exit**: Use break to stop processing when condition met
2. **Skip Items**: Use continue instead of conditional wrapping
3. **Variable Reuse**: Store computed values in variables
4. **Null Returns**: Return null to exclude items (more efficient than filter after)

---

## Related Topics

- [For-In Loops](11-for-in.md) - Index-based iteration
- [Break & Continue](12-break-continue.md) - Control flow in loops
- [map() Function](functions/map.md) - Functional alternative
- [filter() Function](functions/filter.md) - Filtering arrays

---

**See Also:**
- [Quick Start](01-quick-start.md)
- [Arrays](05-data-types.md#array)
- [Variables and Scope](04-variables-scope.md)
