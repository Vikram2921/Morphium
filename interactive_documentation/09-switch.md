# Switch Statements

Comprehensive guide to switch-case control flow in Morphium DSL.

---

## Syntax

```javascript
switch (expression) {
  case value1: result1;
  case value2: result2;
  case value3: result3;
  default: defaultResult;
}
```

### Parameters
- **expression**: Expression to evaluate and match against cases
- **value**: Constant value to match (can be number, string, boolean, null)
- **result**: Expression to evaluate when case matches
- **default**: Optional default case when no cases match

### Returns
The result expression of the matched case, or the default result.

---

## Description

Switch statements provide multi-way branching based on the value of an expression. They're more concise than multiple if-else statements when comparing a single value against multiple possibilities.

**Key Features:**
- No fall-through: Each case automatically breaks
- Expression-based: Returns a value
- Type-sensitive: Matches use strict equality (==)
- Default case optional but recommended

---

## Basic Examples

### Example 1: Simple Switch

```javascript
// Input
{
  "day": 3
}

// Script
{
  dayName: switch ($.day) {
    case 1: "Monday";
    case 2: "Tuesday";
    case 3: "Wednesday";
    case 4: "Thursday";
    case 5: "Friday";
    case 6: "Saturday";
    case 7: "Sunday";
    default: "Invalid day";
  }
}

// Output
{
  "dayName": "Wednesday"
}
```

### Example 2: String Matching

```javascript
// Input
{
  "status": "pending"
}

// Script
{
  message: switch ($.status) {
    case "pending": "Order is being processed";
    case "shipped": "Order is on the way";
    case "delivered": "Order has been delivered";
    case "cancelled": "Order was cancelled";
    default: "Unknown status";
  }
}

// Output
{
  "message": "Order is being processed"
}
```

### Example 3: With Computed Results

```javascript
// Input
{
  "grade": "B"
}

// Script
{
  gpa: switch ($.grade) {
    case "A": 4.0;
    case "B": 3.0;
    case "C": 2.0;
    case "D": 1.0;
    case "F": 0.0;
    default: null;
  },
  
  passed: switch ($.grade) {
    case "A": true;
    case "B": true;
    case "C": true;
    case "D": true;
    case "F": false;
    default: false;
  }
}

// Output
{
  "gpa": 3.0,
  "passed": true
}
```

---

## Advanced Examples

### Example 1: Switch with Complex Expressions

```javascript
// Input
{
  "role": "admin",
  "action": "delete"
}

// Script
let role = $.role;

{
  permissions: switch (role) {
    case "admin": ["read", "write", "delete", "manage"];
    case "editor": ["read", "write"];
    case "viewer": ["read"];
    case "guest": [];
    default: [];
  },
  
  canPerformAction: switch ($.action) {
    case "read": true;
    case "write": role == "admin" || role == "editor";
    case "delete": role == "admin";
    default: false;
  }
}

// Output
{
  "permissions": ["read", "write", "delete", "manage"],
  "canPerformAction": true
}
```

### Example 2: Nested Switch Statements

```javascript
// Input
{
  "category": "electronics",
  "subcategory": "phone"
}

// Script
{
  department: switch ($.category) {
    case "electronics": switch ($.subcategory) {
      case "phone": "Mobile Devices";
      case "laptop": "Computers";
      case "tv": "Home Entertainment";
      default: "General Electronics";
    };
    case "clothing": switch ($.subcategory) {
      case "men": "Men's Fashion";
      case "women": "Women's Fashion";
      case "kids": "Children's Clothing";
      default: "General Apparel";
    };
    case "books": "Literature";
    default: "General";
  }
}

// Output
{
  "department": "Mobile Devices"
}
```

### Example 3: Switch with Object Results

```javascript
// Input
{
  "membershipTier": "gold"
}

// Script
{
  benefits: switch ($.membershipTier) {
    case "platinum": {
      discount: 0.25,
      freeShipping: true,
      priority: "high",
      rewards: 5
    };
    case "gold": {
      discount: 0.15,
      freeShipping: true,
      priority: "medium",
      rewards: 3
    };
    case "silver": {
      discount: 0.10,
      freeShipping: false,
      priority: "medium",
      rewards: 2
    };
    case "bronze": {
      discount: 0.05,
      freeShipping: false,
      priority: "low",
      rewards: 1
    };
    default: {
      discount: 0.0,
      freeShipping: false,
      priority: "low",
      rewards: 0
    };
  }
}

// Output
{
  "benefits": {
    "discount": 0.15,
    "freeShipping": true,
    "priority": "medium",
    "rewards": 3
  }
}
```

### Example 4: Switch in Array Processing

```javascript
// Input
{
  "transactions": [
    {"type": "deposit", "amount": 100},
    {"type": "withdrawal", "amount": 50},
    {"type": "transfer", "amount": 75},
    {"type": "fee", "amount": 5}
  ]
}

// Script
{
  categorized: map($.transactions, "t", {
    type: t.type,
    amount: t.amount,
    category: switch (t.type) {
      case "deposit": "Income";
      case "withdrawal": "Expense";
      case "transfer": "Transfer";
      case "fee": "Expense";
      default: "Other";
    },
    impact: switch (t.type) {
      case "deposit": t.amount;
      case "withdrawal": -t.amount;
      case "transfer": 0;
      case "fee": -t.amount;
      default: 0;
    }
  })
}

// Output
{
  "categorized": [
    {"type": "deposit", "amount": 100, "category": "Income", "impact": 100},
    {"type": "withdrawal", "amount": 50, "category": "Expense", "impact": -50},
    {"type": "transfer", "amount": 75, "category": "Transfer", "impact": 0},
    {"type": "fee", "amount": 5, "category": "Expense", "impact": -5}
  ]
}
```

---

## Comparison with If-Else

### When to Use Switch

✅ **Use Switch When:**
- Comparing one variable against multiple specific values
- Values are constants (literals)
- Need cleaner syntax for many conditions
- All comparisons use equality (==)

```javascript
// Good use of switch
switch ($.status) {
  case "pending": "Processing";
  case "completed": "Done";
  case "failed": "Error";
  default: "Unknown";
}
```

### When to Use If-Else

✅ **Use If-Else When:**
- Need range comparisons (<, >, <=, >=)
- Need complex boolean logic (&&, ||)
- Conditions involve different variables
- Need more flexible condition expressions

```javascript
// Better with if-else
if ($.score >= 90) {
  "A"
} else if ($.score >= 80) {
  "B"
} else if ($.score >= 70) {
  "C"
} else {
  "F"
}
```

---

## Common Patterns

### Pattern 1: Status Mapping

```javascript
let status = $.orderStatus;
{
  displayStatus: switch (status) {
    case "pending": "⏳ Pending";
    case "processing": "⚙️ Processing";
    case "shipped": "🚚 Shipped";
    case "delivered": "✅ Delivered";
    case "cancelled": "❌ Cancelled";
    default: "❓ Unknown";
  }
}
```

### Pattern 2: Configuration Selection

```javascript
let env = $.environment;
{
  config: switch (env) {
    case "production": {
      host: "api.example.com",
      port: 443,
      ssl: true,
      debug: false
    };
    case "staging": {
      host: "staging.example.com",
      port: 443,
      ssl: true,
      debug: true
    };
    case "development": {
      host: "localhost",
      port: 8080,
      ssl: false,
      debug: true
    };
    default: {
      host: "localhost",
      port: 8080,
      ssl: false,
      debug: true
    };
  }
}
```

### Pattern 3: Type-Based Processing

```javascript
let dataType = $.type;
let value = $.value;

{
  processed: switch (dataType) {
    case "number": toNumber(value);
    case "string": toString(value);
    case "boolean": toBool(value);
    case "array": jsonParse(value);
    default: value;
  }
}
```

---

## Complex Example: Order Processing System

```javascript
// Input
{
  "orders": [
    {
      "id": "ORD-001",
      "status": "pending",
      "priority": "high",
      "total": 150
    },
    {
      "id": "ORD-002",
      "status": "processing",
      "priority": "normal",
      "total": 75
    },
    {
      "id": "ORD-003",
      "status": "shipped",
      "priority": "high",
      "total": 200
    }
  ]
}

// Script
{
  processedOrders: map($.orders, "order", {
    id: order.id,
    status: order.status,
    statusColor: switch (order.status) {
      case "pending": "yellow";
      case "processing": "blue";
      case "shipped": "green";
      case "delivered": "green";
      case "cancelled": "red";
      default: "gray";
    },
    priorityLevel: switch (order.priority) {
      case "urgent": 1;
      case "high": 2;
      case "normal": 3;
      case "low": 4;
      default: 5;
    },
    processingTime: switch (order.status) {
      case "pending": "0-2 hours";
      case "processing": "2-24 hours";
      case "shipped": "2-5 days";
      case "delivered": "Complete";
      default: "Unknown";
    },
    nextAction: switch (order.status) {
      case "pending": "Begin processing";
      case "processing": "Prepare for shipment";
      case "shipped": "Track delivery";
      case "delivered": "Request feedback";
      case "cancelled": "Process refund";
      default: "Review order";
    },
    canCancel: switch (order.status) {
      case "pending": true;
      case "processing": true;
      case "shipped": false;
      case "delivered": false;
      case "cancelled": false;
      default: false;
    }
  })
}
```

---

## Best Practices

### ✅ Do:
- Always include a default case
- Use switch for multiple equality comparisons
- Keep case values simple (literals)
- Use consistent result types across cases
- Consider readability over brevity

### ❌ Don't:
- Don't use for range comparisons
- Don't use complex expressions in case values
- Don't omit default case without good reason
- Don't mix result types unnecessarily
- Don't nest switches too deeply (max 2-3 levels)

---

## Performance Tips

1. **Case Order**: Put most common cases first
2. **Simple Values**: Keep case values as simple literals
3. **Default Case**: Always provide for better error handling
4. **Variable Storage**: Store switch expression in variable if complex

---

## Tips

1. **Readability**: Switch is often clearer than long if-else chains
2. **Maintenance**: Adding new cases is straightforward
3. **Type Safety**: Use consistent types for all case values
4. **Defaults**: Default case prevents unexpected null returns

---

## Related Topics

- [If-Else Statements](08-if-else.md) - Alternative conditional control
- [Ternary Operator](08-if-else.md#ternary-operator) - Simple conditions
- [For Loops](10-for-of.md) - Iteration with conditionals
- [Operators](06-operators.md) - Comparison operators

---

**See Also:**
- [Quick Start](01-quick-start.md)
- [Basic Concepts](02-basic-concepts.md)
- [Syntax Overview](03-syntax-overview.md)
