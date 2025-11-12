# findFirst() - Find First Matching Element

## Overview
The `findFirst()` function returns the first element in an array that satisfies a given condition.

## Syntax
```javascript
findFirst(array, "itemName", condition)
```

## Parameters
- **array**: The input array to search
- **itemName**: Variable name for each element
- **condition**: Expression that evaluates to true/false

## Return Value
Returns the first element that matches the condition, or `null` if no match is found.

## Basic Examples

### Example 1: Find First Adult
```javascript
let users = [
  { name: "Alice", age: 15 },
  { name: "Bob", age: 25 },
  { name: "Charlie", age: 30 }
];

export default = findFirst(users, "u", u.age >= 18);
// Result: { name: "Bob", age: 25 }
```

### Example 2: Find First Negative
```javascript
let numbers = [5, 10, -3, -7, 20];

export default = findFirst(numbers, "n", n < 0);
// Result: -3
```

### Example 3: Find By ID
```javascript
let products = $.products;
let targetId = "P123";

export default = findFirst(products, "p", p.id == targetId);
// Returns first product with matching ID
```

## Advanced Examples

### Example 4: Find First Available
```javascript
let items = [
  { id: 1, name: "A", available: false },
  { id: 2, name: "B", available: true },
  { id: 3, name: "C", available: true }
];

export default = {
  firstAvailable: findFirst(items, "i", i.available),
  message: exists(findFirst(items, "i", i.available)) ?
    "Found available item" :
    "No items available"
};
```

### Example 5: Find Error
```javascript
let logs = $.systemLogs;

let firstError = findFirst(logs, "log", log.level == "ERROR");

export default = {
  hasErrors: exists(firstError),
  firstError: firstError,
  errorMessage: exists(firstError) ? firstError.message : "No errors"
};
```

### Example 6: Find Match with Multiple Conditions
```javascript
let candidates = $.candidates;

export default = findFirst(candidates, "c",
  c.experience >= 5 && c.skills.includes("Java") && c.available
);
```

## Common Patterns

### Pattern 1: Simple Find
```javascript
findFirst(array, "item", item.property == value)
```

### Pattern 2: Find with Default
```javascript
let result = findFirst(array, "item", condition);
exists(result) ? result : defaultValue
```

### Pattern 3: Check Existence
```javascript
exists(findFirst(array, "item", condition))
```

### Pattern 4: Extract Property from Found Item
```javascript
let found = findFirst(array, "item", condition);
exists(found) ? found.property : null
```

## Common Use Cases

- **Search** - Find specific item by criteria
- **Lookup** - Get item by ID or key
- **Validation** - Find first invalid item
- **Error detection** - Find first error
- **Selection** - Choose first available option
- **Matching** - Find first item meeting requirements

## Related Functions

- **filter()** - Get all matching elements
- **anyMatch()** - Check if any element matches
- **map()** - Transform all elements
- **count()** - Count matching elements

## Example: User Lookup with Fallback
```javascript
let users = $.users;
let searchEmail = $.searchEmail;

let foundUser = findFirst(users, "u", u.email == searchEmail);

export default = {
  found: exists(foundUser),
  user: exists(foundUser) ? {
    id: foundUser.id,
    name: foundUser.name,
    email: foundUser.email
  } : null,
  message: exists(foundUser) ? 
    "User found" : 
    "User not found with email: " + searchEmail
};
```

## See Also
- [filter()](filter.md) - Get all matches
- [anyMatch()](anyMatch.md) - Check if any match
- [map()](map.md) - Transform elements
- [count()](count.md) - Count matches
