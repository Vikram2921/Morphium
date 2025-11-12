# sum() - Sum Numeric Values

## Overview
The `sum()` function calculates the sum of all numeric elements in an array.

## Syntax
```javascript
sum(array)
```

## Parameters
- **array**: Array of numbers to sum

## Return Value
Returns the sum as a number. Non-numeric values are ignored.

## Basic Examples

### Example 1: Sum Numbers
```javascript
let numbers = [1, 2, 3, 4, 5];

export default = sum(numbers);
// Result: 15
```

### Example 2: Sum Prices
```javascript
let prices = [10.50, 25.00, 7.99, 15.50];

export default = sum(prices);
// Result: 58.99
```

### Example 3: Sum from Objects
```javascript
let orders = [
  { id: 1, total: 100 },
  { id: 2, total: 250 },
  { id: 3, total: 75 }
];

let totals = map(orders, "o", o.total);
export default = sum(totals);
// Result: 425
```

## Advanced Examples

### Example 4: Calculate Total Revenue
```javascript
let sales = $.sales;

export default = {
  totalRevenue: sum(map(sales, "s", s.amount)),
  totalOrders: len(sales),
  averageOrderValue: sum(map(sales, "s", s.amount)) / len(sales)
};
```

### Example 5: Sum with Filter
```javascript
let transactions = $.transactions;

export default = {
  totalCredit: sum(map(
    filter(transactions, "t", t.type == "credit"),
    "t", t.amount
  )),
  totalDebit: sum(map(
    filter(transactions, "t", t.type == "debit"),
    "t", t.amount
  ))
};
```

### Example 6: Inventory Value
```javascript
let products = $.products;

export default = {
  totalValue: sum(map(products, "p", p.price * p.stock)),
  totalItems: sum(map(products, "p", p.stock))
};
```

## Common Patterns

### Pattern 1: Simple Sum
```javascript
sum(numbers)
```

### Pattern 2: Sum Object Property
```javascript
sum(map(objects, "obj", obj.property))
```

### Pattern 3: Sum with Filter
```javascript
sum(map(filter(items, "i", condition), "i", i.value))
```

### Pattern 4: Calculate Total
```javascript
sum(map(items, "i", i.quantity * i.price))
```

## Common Use Cases

- **Revenue calculation** - Sum sales amounts
- **Inventory valuation** - Sum product values
- **Score totals** - Sum points or scores
- **Financial reports** - Sum transactions
- **Quantity totals** - Sum quantities
- **Metrics aggregation** - Sum metric values

## Related Functions

- **avg()** - Calculate average
- **count()** - Count elements
- **min()** - Find minimum
- **max()** - Find maximum
- **reduce()** - Custom aggregation

## Example: Financial Report
```javascript
let transactions = $.transactions;

let credits = filter(transactions, "t", t.type == "credit");
let debits = filter(transactions, "t", t.type == "debit");

export default = {
  totalCredit: sum(map(credits, "c", c.amount)),
  totalDebit: sum(map(debits, "d", d.amount)),
  balance: sum(map(credits, "c", c.amount)) - sum(map(debits, "d", d.amount)),
  transactionCount: len(transactions)
};
```

## See Also
- [avg()](avg.md) - Calculate average
- [count()](count.md) - Count elements
- [min()](min.md) - Find minimum
- [max()](max.md) - Find maximum
- [reduce()](reduce.md) - Custom aggregation
