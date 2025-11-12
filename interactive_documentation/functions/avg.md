# avg() - Calculate Average

## Overview
The `avg()` function calculates the average (mean) of all numeric elements in an array.

## Syntax
```javascript
avg(array)
```

## Parameters
- **array**: Array of numbers to average

## Return Value
Returns the average as a number. Non-numeric values are ignored. Returns 0 for empty arrays.

## Basic Examples

### Example 1: Average of Numbers
```javascript
let numbers = [10, 20, 30, 40, 50];

export default = avg(numbers);
// Result: 30
```

### Example 2: Average Score
```javascript
let scores = [85, 92, 78, 88, 95];

export default = avg(scores);
// Result: 87.6
```

### Example 3: Average from Objects
```javascript
let students = [
  { name: "Alice", score: 85 },
  { name: "Bob", score: 92 },
  { name: "Charlie", score: 78 }
];

export default = avg(map(students, "s", s.score));
// Result: 85
```

## Advanced Examples

### Example 4: Performance Metrics
```javascript
let metrics = $.metrics;

export default = {
  avgResponseTime: avg(map(metrics, "m", m.responseTime)),
  avgCpuUsage: avg(map(metrics, "m", m.cpuUsage)),
  avgMemoryUsage: avg(map(metrics, "m", m.memoryUsage))
};
```

### Example 5: Rating Analysis
```javascript
let reviews = $.reviews;

export default = {
  averageRating: avg(map(reviews, "r", r.rating)),
  totalReviews: len(reviews),
  highRatings: count(reviews, "r", r.rating >= 4),
  percentageHigh: (count(reviews, "r", r.rating >= 4) * 100) / len(reviews)
};
```

### Example 6: Price Analysis by Category
```javascript
let products = $.products;

export default = {
  electronics: avg(map(
    filter(products, "p", p.category == "Electronics"),
    "p", p.price
  )),
  books: avg(map(
    filter(products, "p", p.category == "Books"),
    "p", p.price
  )),
  clothing: avg(map(
    filter(products, "p", p.category == "Clothing"),
    "p", p.price
  ))
};
```

## Common Patterns

### Pattern 1: Simple Average
```javascript
avg(numbers)
```

### Pattern 2: Average Object Property
```javascript
avg(map(objects, "obj", obj.property))
```

### Pattern 3: Average with Filter
```javascript
avg(map(filter(items, "i", condition), "i", i.value))
```

### Pattern 4: Multiple Averages
```javascript
{
  overall: avg(map(items, "i", i.value)),
  categoryA: avg(map(filter(items, "i", i.category == "A"), "i", i.value))
}
```

## Common Use Cases

- **Performance metrics** - Average response times
- **Rating systems** - Average customer ratings
- **Academic scores** - Average test scores
- **Price analysis** - Average prices
- **Statistical analysis** - Mean calculations
- **Quality metrics** - Average quality scores

## Related Functions

- **sum()** - Sum values
- **count()** - Count elements
- **min()** - Find minimum
- **max()** - Find maximum
- **reduce()** - Custom aggregation

## Example: Student Performance Report
```javascript
let students = $.students;

export default = map(students, "s", {
  name: s.name,
  scores: s.scores,
  average: avg(s.scores),
  grade: avg(s.scores) >= 90 ? "A" :
         avg(s.scores) >= 80 ? "B" :
         avg(s.scores) >= 70 ? "C" :
         avg(s.scores) >= 60 ? "D" : "F"
});
```

## See Also
- [sum()](sum.md) - Sum values
- [count()](count.md) - Count elements
- [min()](min.md) - Find minimum
- [max()](max.md) - Find maximum
- [reduce()](reduce.md) - Custom aggregation
