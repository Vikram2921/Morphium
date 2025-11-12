# anyMatch() - Check If Any Element Matches

## Overview
The `anyMatch()` function tests whether at least one element in an array satisfies a given condition.

## Syntax
```javascript
anyMatch(array, "itemName", condition)
```

## Parameters
- **array**: The input array to test
- **itemName**: Variable name for each element
- **condition**: Expression that evaluates to true/false

## Return Value
Returns `true` if at least one element matches the condition, `false` otherwise.

## Basic Examples

### Example 1: Check for Any Adult
```javascript
let users = [
  { name: "Alice", age: 15 },
  { name: "Bob", age: 25 },
  { name: "Charlie", age: 17 }
];

export default = anyMatch(users, "u", u.age >= 18);
// Result: true (Bob is 25)
```

### Example 2: Check for Negative Numbers
```javascript
let numbers = [5, 10, -3, 20];

export default = anyMatch(numbers, "n", n < 0);
// Result: true (-3 is negative)
```

### Example 3: Check for Active Status
```javascript
let products = $.products;

export default = {
  hasActiveProducts: anyMatch(products, "p", p.status == "active"),
  hasOutOfStock: anyMatch(products, "p", p.stock == 0)
};
```

## Advanced Examples

### Example 4: Validation Check
```javascript
let orders = $.orders;

let hasInvalidOrders = anyMatch(orders, "o", 
  !exists(o.customerId) || o.total <= 0
);

export default = {
  valid: !hasInvalidOrders,
  message: hasInvalidOrders ? "Some orders are invalid" : "All orders valid"
};
```

### Example 5: Permission Check
```javascript
let userPermissions = $.user.permissions;

export default = {
  canEdit: anyMatch(userPermissions, "p", p == "edit" || p == "admin"),
  canDelete: anyMatch(userPermissions, "p", p == "delete" || p == "admin"),
  isAdmin: anyMatch(userPermissions, "p", p == "admin")
};
```

### Example 6: Alert Detection
```javascript
let metrics = $.systemMetrics;

export default = {
  hasAlert: anyMatch(metrics, "m", m.value > m.threshold),
  hasCritical: anyMatch(metrics, "m", m.severity == "critical"),
  needsAttention: anyMatch(metrics, "m", m.value > m.threshold || m.severity == "critical")
};
```

## Common Patterns

### Pattern 1: Simple Existence Check
```javascript
anyMatch(array, "item", item.property == value)
```

### Pattern 2: Validation
```javascript
anyMatch(array, "item", !item.isValid)
```

### Pattern 3: Permission/Access Check
```javascript
anyMatch(permissions, "p", p == requiredPermission)
```

### Pattern 4: Alert/Warning Detection
```javascript
anyMatch(items, "item", item.value > threshold)
```

## Common Use Cases

- **Validation** - Check if any item fails validation
- **Permission checks** - Verify user has required permission
- **Alert detection** - Check if any metric exceeds threshold
- **Search** - Check if any item matches criteria
- **Data quality** - Check for invalid or missing data
- **Status checks** - Verify if any item has specific status

## Related Functions

- **allMatch()** - Check if all elements match
- **noneMatch()** - Check if no elements match
- **findFirst()** - Find first matching element
- **filter()** - Get all matching elements
- **count()** - Count matching elements

## Example: Complete Validation
```javascript
let data = $.records;

export default = {
  hasErrors: anyMatch(data, "r", !exists(r.id) || !exists(r.value)),
  hasWarnings: anyMatch(data, "r", r.value < 0),
  hasHighValues: anyMatch(data, "r", r.value > 1000),
  allValid: !anyMatch(data, "r", !exists(r.id) || !exists(r.value))
};
```

## See Also
- [allMatch()](allMatch.md) - Check if all match
- [noneMatch()](noneMatch.md) - Check if none match
- [findFirst()](findFirst.md) - Find first match
- [filter()](filter.md) - Get all matches
