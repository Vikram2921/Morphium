# flatMap() - Map and Flatten Arrays

## Overview
The `flatMap()` function combines mapping and flattening operations into a single step. It's useful when you want to transform each element in an array into multiple elements (or arrays) and then flatten the result into a single array.

## Syntax
```javascript
flatMap(array, "itemName", expression)
```

## Parameters
- **array**: The input array to process
- **itemName**: Variable name to reference each item in the mapping expression
- **expression**: Expression that transforms each item (can return an array or single value)

## Return Value
Returns a new array with all mapped results flattened one level deep.

## How It Works
1. Iterates through each element in the input array
2. Evaluates the expression for each element
3. If the result is an array, adds all its elements to the result
4. If the result is not an array, adds it directly to the result
5. Returns the flattened array

## Basic Examples

### Example 1: Expanding Nested Arrays
```javascript
let data = [
  { items: [1, 2] },
  { items: [3, 4] },
  { items: [5] }
];

export default = flatMap(data, "obj", obj.items);
// Result: [1, 2, 3, 4, 5]
```

### Example 2: Generating Multiple Items from One
```javascript
let numbers = [1, 2, 3];

export default = flatMap(numbers, "n", [n, n * 10]);
// Result: [1, 10, 2, 20, 3, 30]
```

### Example 3: Conditional Flattening
```javascript
let products = [
  { name: "Laptop", variants: ["13-inch", "15-inch"] },
  { name: "Mouse", variants: [] },
  { name: "Keyboard", variants: ["Wired", "Wireless"] }
];

export default = flatMap(products, "p", 
  len(p.variants) > 0 ? 
    map(p.variants, "v", { product: p.name, variant: v }) : 
    []
);
// Result: [
//   { product: "Laptop", variant: "13-inch" },
//   { product: "Laptop", variant: "15-inch" },
//   { product: "Keyboard", variant: "Wired" },
//   { product: "Keyboard", variant: "Wireless" }
// ]
```

## Advanced Examples

### Example 4: Splitting Text into Words
```javascript
let sentences = [
  { text: "Hello world" },
  { text: "Good morning" },
  { text: "How are you" }
];

export default = flatMap(sentences, "s", split(s.text, " "));
// Result: ["Hello", "world", "Good", "morning", "How", "are", "you"]
```

### Example 5: Expanding Date Ranges
```javascript
let bookings = [
  { guest: "Alice", days: 2 },
  { guest: "Bob", days: 3 }
];

export default = flatMap(bookings, "b",
  map([1, 2, 3], "day", 
    day <= b.days ? { guest: b.guest, day: day } : null
  )
);
// Creates individual records for each day of each booking
```

### Example 6: Processing Hierarchical Data
```javascript
let departments = [
  { name: "Engineering", teams: ["Backend", "Frontend", "DevOps"] },
  { name: "Sales", teams: ["Direct", "Channel"] }
];

export default = flatMap(departments, "dept",
  map(dept.teams, "team", { 
    department: dept.name, 
    team: team 
  })
);
// Result: [
//   { department: "Engineering", team: "Backend" },
//   { department: "Engineering", team: "Frontend" },
//   { department: "Engineering", team: "DevOps" },
//   { department: "Sales", team: "Direct" },
//   { department: "Sales", team: "Channel" }
// ]
```

### Example 7: Extracting Tags from Multiple Objects
```javascript
let articles = [
  { title: "Article 1", tags: ["java", "programming"] },
  { title: "Article 2", tags: ["javascript", "web"] },
  { title: "Article 3", tags: ["java", "spring"] }
];

export default = distinct(flatMap(articles, "a", a.tags));
// Result: ["java", "programming", "javascript", "web", "spring"]
```

### Example 8: Combining with Filter
```javascript
let orders = [
  { id: 1, items: [{ name: "A", qty: 5 }, { name: "B", qty: 0 }] },
  { id: 2, items: [{ name: "C", qty: 3 }] }
];

export default = flatMap(orders, "order",
  map(
    filter(order.items, "item", item.qty > 0),
    "item",
    { orderId: order.id, item: item.name, qty: item.qty }
  )
);
// Result: [
//   { orderId: 1, item: "A", qty: 5 },
//   { orderId: 2, item: "C", qty: 3 }
// ]
```

## Comparison with map()

### Using map() - Results in Nested Arrays
```javascript
let data = [[1, 2], [3, 4]];
map(data, "arr", arr);
// Result: [[1, 2], [3, 4]] - nested arrays preserved
```

### Using flatMap() - Results in Flat Array
```javascript
let data = [[1, 2], [3, 4]];
flatMap(data, "arr", arr);
// Result: [1, 2, 3, 4] - flattened to single array
```

## Common Patterns

### Pattern 1: One-to-Many Transformation
```javascript
// Transform each order into multiple line items
flatMap(orders, "order", order.lineItems)
```

### Pattern 2: Conditional Expansion
```javascript
// Only expand items that meet criteria
flatMap(items, "item", 
  item.isActive ? item.children : []
)
```

### Pattern 3: Cartesian Product
```javascript
// Generate combinations
flatMap(colors, "color",
  map(sizes, "size", { color: color, size: size })
)
```

### Pattern 4: Denormalization
```javascript
// Flatten nested relationships
flatMap(customers, "customer",
  map(customer.orders, "order", {
    customerId: customer.id,
    customerName: customer.name,
    orderId: order.id,
    orderTotal: order.total
  })
)
```

## Performance Considerations

1. **Memory Usage**: flatMap creates a new array, so it can use significant memory with large datasets
2. **Nested flatMap**: Avoid deeply nested flatMap operations; consider restructuring the data
3. **Empty Arrays**: Returning empty arrays is efficient and commonly used for filtering

## Best Practices

1. **Use for Denormalization**: Great for converting nested data to flat structures
2. **Combine with Filter**: Use filter inside flatMap to conditionally expand items
3. **Keep Expressions Simple**: Complex expressions can be hard to debug; consider breaking into steps
4. **Return Empty Arrays**: Return `[]` instead of null for items you want to exclude

## Common Use Cases

- **Denormalizing nested data** for reporting or analysis
- **Expanding arrays** within objects into a single flat list
- **Splitting text** into words or lines and processing each
- **Generating combinations** from multiple arrays
- **Processing hierarchical data** like categories and subcategories
- **Flattening API responses** that have nested arrays

## Related Functions

- **map()** - Transform without flattening
- **filter()** - Remove items based on condition
- **reduce()** - Aggregate array into single value
- **distinct()** - Remove duplicates after flattening
- **concat()** - Combine multiple arrays

## Example: Complete Transformation Pipeline
```javascript
let orders = [
  {
    orderId: "O1",
    customer: "Alice",
    items: [
      { sku: "A1", price: 10, qty: 2 },
      { sku: "A2", price: 20, qty: 1 }
    ]
  },
  {
    orderId: "O2",
    customer: "Bob",
    items: [
      { sku: "B1", price: 15, qty: 3 }
    ]
  }
];

// Transform to flat line items with totals
export default = flatMap(orders, "order",
  map(order.items, "item", {
    orderId: order.orderId,
    customer: order.customer,
    sku: item.sku,
    price: item.price,
    qty: item.qty,
    total: item.price * item.qty
  })
);

// Result:
// [
//   { orderId: "O1", customer: "Alice", sku: "A1", price: 10, qty: 2, total: 20 },
//   { orderId: "O1", customer: "Alice", sku: "A2", price: 20, qty: 1, total: 20 },
//   { orderId: "O2", customer: "Bob", sku: "B1", price: 15, qty: 3, total: 45 }
// ]
```

## Error Handling
```javascript
// Handle missing or non-array data
flatMap(
  exists($.data) && $.data.isArray ? $.data : [],
  "item",
  item.values
)

// Validate before expanding
flatMap($.items, "item",
  !exists(item.children) ? [] :
  !item.children.isArray ? [] :
  item.children
)
```

## See Also
- [map()](map.md) - Transform array elements
- [filter()](filter.md) - Filter array elements
- [reduce()](reduce.md) - Reduce array to single value
- [distinct()](distinct.md) - Remove duplicates
- [concat()](concat.md) - Combine arrays
