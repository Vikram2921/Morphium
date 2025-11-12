# allMatch() - Check If All Elements Match

## Overview
The `allMatch()` function tests whether all elements in an array satisfy a given condition.

## Syntax
```javascript
allMatch(array, "itemName", condition)
```

## Parameters
- **array**: The input array to test
- **itemName**: Variable name for each element
- **condition**: Expression that evaluates to true/false

## Return Value
Returns `true` if all elements match the condition, `false` if any element doesn't match.

## Basic Examples

### Example 1: Check All Adults
```javascript
let users = [
  { name: "Alice", age: 25 },
  { name: "Bob", age: 30 },
  { name: "Charlie", age: 22 }
];

export default = allMatch(users, "u", u.age >= 18);
// Result: true (all are adults)
```

### Example 2: Check All Positive
```javascript
let numbers = [5, 10, 15, 20];

export default = allMatch(numbers, "n", n > 0);
// Result: true (all positive)
```

### Example 3: Validation
```javascript
let products = $.products;

export default = {
  allInStock: allMatch(products, "p", p.stock > 0),
  allActive: allMatch(products, "p", p.status == "active"),
  allValidPrices: allMatch(products, "p", p.price > 0)
};
```

## Advanced Examples

### Example 4: Data Quality Check
```javascript
let records = $.records;

export default = {
  dataQuality: {
    allHaveIds: allMatch(records, "r", exists(r.id)),
    allHaveValues: allMatch(records, "r", exists(r.value)),
    allPositive: allMatch(records, "r", r.value >= 0),
    isComplete: allMatch(records, "r", 
      exists(r.id) && exists(r.value) && r.value >= 0
    )
  }
};
```

### Example 5: Permission Validation
```javascript
let users = $.users;

export default = {
  allVerified: allMatch(users, "u", u.emailVerified),
  allActive: allMatch(users, "u", u.status == "active"),
  canProceed: allMatch(users, "u", u.emailVerified && u.status == "active")
};
```

### Example 6: Requirement Check
```javascript
let tasks = $.project.tasks;

export default = {
  projectComplete: allMatch(tasks, "t", t.status == "completed"),
  allTestsPassed: allMatch(tasks, "t", !exists(t.tests) || t.tests.passed),
  readyForRelease: allMatch(tasks, "t", 
    t.status == "completed" && (!exists(t.tests) || t.tests.passed)
  )
};
```

## Common Patterns

### Pattern 1: Validation Check
```javascript
allMatch(array, "item", item.isValid)
```

### Pattern 2: Status Check
```javascript
allMatch(array, "item", item.status == "completed")
```

### Pattern 3: Existence Check
```javascript
allMatch(array, "item", exists(item.requiredField))
```

### Pattern 4: Range Validation
```javascript
allMatch(array, "item", item.value >= min && item.value <= max)
```

## Common Use Cases

- **Data validation** - Verify all records are valid
- **Completion checks** - Check if all tasks/steps are done
- **Quality assurance** - Ensure all items meet standards
- **Permission verification** - Verify all users have access
- **Batch validation** - Validate entire datasets
- **Readiness checks** - Confirm all prerequisites met

## Related Functions

- **anyMatch()** - Check if any element matches
- **noneMatch()** - Check if no elements match
- **filter()** - Get all matching elements
- **count()** - Count matching elements

## Example: Complete Readiness Check
```javascript
let deploymentChecks = [
  { name: "Tests", passed: true },
  { name: "Build", passed: true },
  { name: "Security Scan", passed: true },
  { name: "Code Review", passed: false }
];

export default = {
  readyToDeploy: allMatch(deploymentChecks, "c", c.passed),
  failedChecks: filter(deploymentChecks, "c", !c.passed),
  status: allMatch(deploymentChecks, "c", c.passed) ? "Ready" : "Not Ready"
};
```

## See Also
- [anyMatch()](anyMatch.md) - Check if any match
- [noneMatch()](noneMatch.md) - Check if none match
- [filter()](filter.md) - Get all matches
- [count()](count.md) - Count matches
