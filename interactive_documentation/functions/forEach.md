# forEach() - Iterate with Side Effects

## Overview
The `forEach()` function iterates over an array and executes an expression for each element. Unlike `map()`, it doesn't create a new array—it's designed for side effects like logging, updating global state, or performing operations that don't produce transformed values.

## Syntax
```javascript
forEach(array, "itemName", expression)
```

## Parameters
- **array**: The input array to iterate over
- **itemName**: Variable name to reference each item in the expression
- **expression**: Expression to execute for each item (return value is ignored)

## Return Value
Returns the original input array unchanged.

## How It Works
1. Iterates through each element in the input array
2. Evaluates the expression for each element
3. Discards the result of the expression (side effects only)
4. Returns the original array

## Basic Examples

### Example 1: Logging Each Element
```javascript
let numbers = [1, 2, 3, 4, 5];

forEach(numbers, "n", log("Processing:", n));
// Console output:
// Processing: 1
// Processing: 2
// Processing: 3
// Processing: 4
// Processing: 5

export default = numbers;  // Original array returned
```

### Example 2: Multiple Side Effects
```javascript
let items = [
  { id: 1, status: "pending" },
  { id: 2, status: "completed" }
];

forEach(items, "item", {
  logInfo("Item ID:", item.id);
  logDebug("Status:", item.status)
});

export default = items;  // Original array unchanged
```

### Example 3: Validation with Logging
```javascript
let orders = [
  { orderId: "O1", total: 100 },
  { orderId: "O2", total: -50 },
  { orderId: "O3", total: 200 }
];

forEach(orders, "order",
  order.total < 0 ? logWarn("Invalid order:", order.orderId) : null
);

export default = orders;
```

## Advanced Examples

### Example 4: Tracking Progress
```javascript
global processedCount = 0;

let items = [1, 2, 3, 4, 5];

forEach(items, "item", {
  log("Processing item", item);
  processedCount = processedCount + 1;
  logInfo("Progress:", processedCount, "/", len(items))
});

export default = {
  items: items,
  totalProcessed: processedCount
};
```

### Example 5: Conditional Logging
```javascript
let transactions = [
  { id: 1, amount: 100, type: "credit" },
  { id: 2, amount: 50, type: "debit" },
  { id: 3, amount: 1000, type: "credit" },
  { id: 4, amount: 25, type: "debit" }
];

forEach(transactions, "tx",
  tx.amount > 500 ? logWarn("Large transaction:", tx.id, tx.amount) :
  tx.type == "credit" ? logInfo("Credit:", tx.amount) :
  logDebug("Debit:", tx.amount)
);

export default = transactions;
```

### Example 6: Debugging Data Pipeline
```javascript
let data = $.items;

// Log before transformation
forEach(data, "item", logDebug("Input:", item));

// Transform
let transformed = map(data, "item", { 
  id: item.id, 
  value: item.value * 2 
});

// Log after transformation
forEach(transformed, "item", logDebug("Output:", item));

export default = transformed;
```

### Example 7: Accumulating Statistics
```javascript
global totalValue = 0;
global itemCount = 0;
global errorCount = 0;

let items = $.items;

forEach(items, "item", {
  itemCount = itemCount + 1;
  exists(item.value) && item.value > 0 ? 
    totalValue = totalValue + item.value :
    errorCount = errorCount + 1
});

export default = {
  items: items,
  statistics: {
    totalItems: itemCount,
    totalValue: totalValue,
    errorCount: errorCount,
    averageValue: totalValue / (itemCount - errorCount)
  }
};
```

### Example 8: Multi-Level Logging
```javascript
let orders = [
  { id: "O1", items: [{ sku: "A", qty: 2 }, { sku: "B", qty: 1 }] },
  { id: "O2", items: [{ sku: "C", qty: 5 }] }
];

forEach(orders, "order", {
  logInfo("Processing order:", order.id);
  forEach(order.items, "item", 
    logDebug("  Item:", item.sku, "Qty:", item.qty)
  )
});

export default = orders;
// Output:
// [INFO] Processing order: O1
// [DEBUG]   Item: A Qty: 2
// [DEBUG]   Item: B Qty: 1
// [INFO] Processing order: O2
// [DEBUG]   Item: C Qty: 5
```

## Comparison with map()

### Using map() - Creates New Array
```javascript
let numbers = [1, 2, 3];
let result = map(numbers, "n", {
  log("Value:", n);
  n * 2
});
// result = [2, 4, 6] - new array created
```

### Using forEach() - Returns Original Array
```javascript
let numbers = [1, 2, 3];
let result = forEach(numbers, "n", log("Value:", n));
// result = [1, 2, 3] - original array returned
```

## Common Patterns

### Pattern 1: Logging for Debugging
```javascript
forEach(items, "item", logDebug("Item:", item))
```

### Pattern 2: Conditional Logging
```javascript
forEach(items, "item",
  item.important ? logWarn("Important:", item) : null
)
```

### Pattern 3: Progress Tracking
```javascript
global progress = 0;
forEach(items, "item", {
  progress = progress + 1;
  log("Progress:", progress, "/", len(items))
})
```

### Pattern 4: Validation with Warnings
```javascript
forEach(items, "item",
  !exists(item.requiredField) ? 
    logWarn("Missing field in item:", item.id) : 
    null
)
```

## When to Use forEach()

### Use forEach() When:
- Logging or debugging data
- Updating global state or counters
- Performing side effects without transformation
- Validating data and reporting issues
- Tracking progress through a pipeline

### Use map() Instead When:
- You need to transform the array
- You want to create a new array from existing data
- The result matters (not just side effects)

### Use filter() Instead When:
- You need to select specific elements
- You want to remove items based on criteria

## Performance Considerations

1. **Side Effects Only**: forEach is optimized for side effects, not transformations
2. **No New Array**: More memory efficient than map when you don't need transformation
3. **Global State**: Be careful with global variables in concurrent scenarios
4. **Logging Overhead**: Excessive logging can slow down transformations

## Best Practices

1. **Use for Side Effects**: Only use forEach when you need side effects (logging, stats, etc.)
2. **Keep Expressions Simple**: Complex expressions are harder to debug
3. **Avoid in Production**: Remove or minimize debug logging in production code
4. **Use Appropriate Log Levels**: logDebug for verbose, logInfo for important, logWarn for issues
5. **Don't Rely on Return Value**: The expression result is discarded

## Common Use Cases

- **Debugging transformations** by logging intermediate values
- **Progress reporting** for long-running transformations
- **Validation logging** to report data quality issues
- **Statistics collection** using global variables
- **Audit logging** for tracking what data was processed
- **Conditional warnings** for business rule violations

## Related Functions

- **map()** - Transform array elements
- **filter()** - Select array elements based on condition
- **log()** - General logging
- **logInfo()** - Information logging
- **logWarn()** - Warning logging
- **logError()** - Error logging
- **logDebug()** - Debug logging

## Example: Complete Validation Pipeline
```javascript
global validCount = 0;
global invalidCount = 0;
global warnings = [];

let products = $.products;

// Validate and log issues
forEach(products, "p", {
  !exists(p.sku) ? {
    logError("Missing SKU in product:", p.id);
    invalidCount = invalidCount + 1
  } :
  p.price <= 0 ? {
    logWarn("Invalid price for:", p.sku);
    invalidCount = invalidCount + 1
  } :
  p.stock < 10 ? {
    logInfo("Low stock for:", p.sku, "Stock:", p.stock);
    validCount = validCount + 1
  } : {
    validCount = validCount + 1
  }
});

export default = {
  products: products,
  validation: {
    valid: validCount,
    invalid: invalidCount,
    total: len(products)
  }
};
```

## Error Handling
```javascript
// Handle null or undefined arrays
forEach(
  exists($.items) ? $.items : [],
  "item",
  log("Item:", item)
)

// Safe property access
forEach(items, "item",
  exists(item.value) ? 
    logInfo("Value:", item.value) : 
    logWarn("Missing value in item")
)
```

## Integration with Other Functions

### Combining with map()
```javascript
// Log before transformation
forEach($.items, "item", logDebug("Input:", item));

// Transform
let result = map($.items, "item", { 
  id: item.id, 
  doubled: item.value * 2 
});

// Log after transformation
forEach(result, "item", logDebug("Output:", item));

export default = result;
```

### Combining with filter()
```javascript
// Filter with logging
let filtered = filter($.items, "item", {
  !item.active ? logInfo("Skipping inactive:", item.id) : null;
  item.active
});

export default = filtered;
```

## See Also
- [map()](map.md) - Transform array elements
- [filter()](filter.md) - Filter array elements
- [logging](logging.md) - Logging functions reference
- [For-Of Loops](../10-for-of.md) - Alternative iteration method
