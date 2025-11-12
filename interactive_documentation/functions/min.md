# min() - Find Minimum Value

## Overview
The `min()` function finds the minimum (smallest) value in an array.

## Syntax
```javascript
min(array)
```

## Parameters
- **array**: Array of values to search

## Return Value
Returns the minimum value. For empty arrays, returns `null`.

## Basic Examples

### Example 1: Minimum Number
```javascript
let numbers = [5, 2, 8, 1, 9];

export default = min(numbers);
// Result: 1
```

### Example 2: Minimum Price
```javascript
let prices = [99.99, 49.99, 79.99, 29.99];

export default = min(prices);
// Result: 29.99
```

### Example 3: Minimum from Objects
```javascript
let products = [
  { name: "A", price: 100 },
  { name: "B", price: 50 },
  { name: "C", price: 75 }
];

export default = min(map(products, "p", p.price));
// Result: 50
```

## Advanced Examples

### Example 4: Find Cheapest Product
```javascript
let products = $.products;
let prices = map(products, "p", p.price);
let minPrice = min(prices);

export default = findFirst(products, "p", p.price == minPrice);
// Returns the cheapest product
```

### Example 5: Min/Max Analysis
```javascript
let values = $.values;

export default = {
  minimum: min(values),
  maximum: max(values),
  range: max(values) - min(values),
  average: avg(values)
};
```

## Common Patterns

### Pattern 1: Simple Min
```javascript
min(numbers)
```

### Pattern 2: Min from Property
```javascript
min(map(objects, "obj", obj.property))
```

### Pattern 3: Min with Filter
```javascript
min(map(filter(items, "i", condition), "i", i.value))
```

## Common Use Cases

- **Price finding** - Find lowest price
- **Performance optimization** - Find fastest time
- **Quality metrics** - Find lowest score
- **Range calculation** - Determine minimum value
- **Threshold detection** - Find minimum threshold

## Related Functions

- **max()** - Find maximum
- **avg()** - Calculate average
- **sum()** - Sum values
- **sorted()** - Sort and take first

## See Also
- [max()](max.md) - Find maximum
- [avg()](avg.md) - Calculate average
- [sum()](sum.md) - Sum values
- [sorted()](sorted.md) - Sort arrays
