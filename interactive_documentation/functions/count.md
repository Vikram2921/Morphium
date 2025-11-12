# count() - Count Elements

## Overview
The `count()` function counts the number of elements in an array, optionally filtering by a condition.

## Syntax
```javascript
// Count all elements
count(array)

// Count matching elements
count(array, "itemName", condition)
```

## Parameters
- **array**: The input array to count
- **itemName** (optional): Variable name for each element
- **condition** (optional): Expression to filter elements

## Return Value
Returns the count as a number.

## Basic Examples

### Example 1: Count All Elements
```javascript
let items = [1, 2, 3, 4, 5];

export default = count(items);
// Result: 5
```

### Example 2: Count Matching Elements
```javascript
let numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

export default = count(numbers, "n", n > 5);
// Result: 5 (numbers 6, 7, 8, 9, 10)
```

### Example 3: Count by Status
```javascript
let orders = $.orders;

export default = {
  total: count(orders),
  completed: count(orders, "o", o.status == "completed"),
  pending: count(orders, "o", o.status == "pending"),
  cancelled: count(orders, "o", o.status == "cancelled")
};
```

## Advanced Examples

### Example 4: Statistics Dashboard
```javascript
let products = $.products;

export default = {
  totalProducts: count(products),
  inStock: count(products, "p", p.stock > 0),
  outOfStock: count(products, "p", p.stock == 0),
  lowStock: count(products, "p", p.stock > 0 && p.stock < 10),
  highValue: count(products, "p", p.price > 1000)
};
```

### Example 5: Validation Summary
```javascript
let records = $.records;

export default = {
  total: count(records),
  valid: count(records, "r", exists(r.id) && exists(r.value) && r.value >= 0),
  invalid: count(records, "r", !exists(r.id) || !exists(r.value) || r.value < 0),
  missingId: count(records, "r", !exists(r.id)),
  missingValue: count(records, "r", !exists(r.value)),
  negativeValue: count(records, "r", r.value < 0)
};
```

### Example 6: User Analytics
```javascript
let users = $.users;

export default = {
  totalUsers: count(users),
  active: count(users, "u", u.status == "active"),
  verified: count(users, "u", u.emailVerified),
  premium: count(users, "u", u.isPremium),
  activeAndPremium: count(users, "u", u.status == "active" && u.isPremium)
};
```

## Common Patterns

### Pattern 1: Total Count
```javascript
count(array)
```

### Pattern 2: Conditional Count
```javascript
count(array, "item", item.condition)
```

### Pattern 3: Multiple Counts
```javascript
{
  total: count(array),
  category1: count(array, "item", item.category == "A"),
  category2: count(array, "item", item.category == "B")
}
```

### Pattern 4: Percentage Calculation
```javascript
let total = count(array);
let matching = count(array, "item", condition);
{ percentage: (matching * 100) / total }
```

## Common Use Cases

- **Statistics** - Generate counts for analytics
- **Validation** - Count valid/invalid items
- **Summaries** - Create count-based summaries
- **Progress tracking** - Count completed items
- **Category distribution** - Count items per category
- **Quality metrics** - Count items meeting criteria

## Related Functions

- **len()** - Get array length (simpler alternative for total count)
- **filter()** - Get matching elements (then use len)
- **sum()** - Sum numeric values
- **avg()** - Calculate average

## Example: Complete Analytics Report
```javascript
let transactions = $.transactions;
let totalAmount = sum(transactions, "t", t.amount);

export default = {
  summary: {
    totalTransactions: count(transactions),
    successfulTxs: count(transactions, "t", t.status == "success"),
    failedTxs: count(transactions, "t", t.status == "failed"),
    pendingTxs: count(transactions, "t", t.status == "pending")
  },
  amounts: {
    totalAmount: totalAmount,
    averageAmount: totalAmount / count(transactions),
    largeTransactions: count(transactions, "t", t.amount > 1000)
  },
  successRate: {
    rate: (count(transactions, "t", t.status == "success") * 100) / count(transactions),
    unit: "%"
  }
};
```

## See Also
- [len()](len.md) - Get array/string length
- [sum()](sum.md) - Sum values
- [avg()](avg.md) - Calculate average
- [filter()](filter.md) - Filter elements
