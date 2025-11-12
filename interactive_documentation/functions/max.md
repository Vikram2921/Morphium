# max() - Find Maximum Value

## Overview
The `max()` function finds the maximum (largest) value in an array.

## Syntax
```javascript
max(array)
```

## Parameters
- **array**: Array of values to search

## Return Value
Returns the maximum value. For empty arrays, returns `null`.

## Basic Examples

### Example 1: Maximum Number
```javascript
let numbers = [5, 2, 8, 1, 9];

export default = max(numbers);
// Result: 9
```

### Example 2: Maximum Score
```javascript
let scores = [85, 92, 78, 95, 88];

export default = max(scores);
// Result: 95
```

### Example 3: Maximum from Objects
```javascript
let products = [
  { name: "A", price: 100 },
  { name: "B", price: 250 },
  { name: "C", price: 175 }
];

export default = max(map(products, "p", p.price));
// Result: 250
```

## Advanced Examples

### Example 4: Find Top Performer
```javascript
let students = $.students;
let scores = map(students, "s", s.score);
let maxScore = max(scores);

export default = findFirst(students, "s", s.score == maxScore);
// Returns student with highest score
```

### Example 5: Statistical Summary
```javascript
let data = $.metrics;

export default = {
  maximum: max(data),
  minimum: min(data),
  range: max(data) - min(data),
  average: avg(data),
  count: len(data)
};
```

## Common Patterns

### Pattern 1: Simple Max
```javascript
max(numbers)
```

### Pattern 2: Max from Property
```javascript
max(map(objects, "obj", obj.property))
```

### Pattern 3: Max with Filter
```javascript
max(map(filter(items, "i", condition), "i", i.value))
```

## Common Use Cases

- **Leaderboards** - Find highest score
- **Performance analysis** - Find peak values
- **Price comparison** - Find most expensive
- **Capacity planning** - Find maximum load
- **Record tracking** - Find record values

## Related Functions

- **min()** - Find minimum
- **avg()** - Calculate average
- **sum()** - Sum values
- **sorted()** - Sort and take last

## See Also
- [min()](min.md) - Find minimum
- [avg()](avg.md) - Calculate average
- [sum()](sum.md) - Sum values
- [sorted()](sorted.md) - Sort arrays
