# Control Flow - If-Else Statements

Conditional branching with if-else statements.

---

## Syntax

```javascript
if (condition) {
  thenExpression
} else if (condition2) {
  elseIfExpression
} else {
  elseExpression
}
```

### Parameters
- **condition**: Boolean expression to evaluate
- **thenExpression**: Expression to evaluate if condition is true
- **elseIfExpression**: Optional else-if branches
- **elseExpression**: Optional else branch

### Returns
The value of the executed branch.

---

## Description

If-else statements allow conditional execution of code based on boolean conditions. They can be used as both statements and expressions, meaning they can return values.

---

## Basic Examples

### Example 1: Simple If-Else
```javascript
// Input
{
  "score": 85
}

// Script
{
  grade: if ($.score >= 90) {
    'A'
  } else {
    'B'
  }
}

// Output
{
  "grade": "B"
}
```

### Example 2: If-Else Chain
```javascript
// Input
{
  "age": 25
}

// Script
{
  category: if ($.age < 13) {
    'child'
  } else if ($.age < 20) {
    'teenager'
  } else if ($.age < 65) {
    'adult'
  } else {
    'senior'
  }
}

// Output
{
  "category": "adult"
}
```

### Example 3: Nested Conditions
```javascript
// Input
{
  "user": {
    "premium": true,
    "purchaseAmount": 150
  }
}

// Script
{
  discount: if ($.user.premium) {
    if ($.user.purchaseAmount >= 100) {
      0.20
    } else {
      0.10
    }
  } else {
    if ($.user.purchaseAmount >= 200) {
      0.05
    } else {
      0
    }
  }
}

// Output
{
  "discount": 0.20
}
```

---

## Advanced Examples

### Example 4: If-Else in Object Construction
```javascript
// Input
{
  "orders": [
    {"id": 1, "total": 100, "priority": "high"},
    {"id": 2, "total": 50, "priority": "low"},
    {"id": 3, "total": 200, "priority": "high"}
  ]
}

// Script
{
  processed: for (order of $.orders) {
    {
      id: order.id,
      total: order.total,
      shipping: if (order.priority == 'high') {
        0
      } else if (order.total > 100) {
        5
      } else {
        10
      },
      label: if (order.priority == 'high') {
        'URGENT'
      } else {
        'STANDARD'
      }
    }
  }
}

// Output
{
  "processed": [
    {"id": 1, "total": 100, "shipping": 0, "label": "URGENT"},
    {"id": 2, "total": 50, "shipping": 10, "label": "STANDARD"},
    {"id": 3, "total": 200, "shipping": 0, "label": "URGENT"}
  ]
}
```

### Example 5: Multiple Condition Checks
```javascript
// Input
{
  "temperature": 25,
  "humidity": 70,
  "season": "summer"
}

// Script
{
  comfort: if ($.season == 'summer') {
    if ($.temperature > 30 && $.humidity > 60) {
      'too hot and humid'
    } else if ($.temperature > 30) {
      'hot but comfortable'
    } else {
      'pleasant'
    }
  } else if ($.season == 'winter') {
    if ($.temperature < 10) {
      'cold'
    } else {
      'mild'
    }
  } else {
    'moderate'
  }
}

// Output
{
  "comfort": "pleasant"
}
```

### Example 6: Guard Clauses
```javascript
// Input
{
  "user": {
    "name": "John",
    "email": "john@example.com",
    "verified": true,
    "age": 25
  }
}

// Script
{
  canProceed: if (!exists($.user)) {
    false
  } else if (!$.user.verified) {
    false
  } else if ($.user.age < 18) {
    false
  } else {
    true
  },
  message: if (!exists($.user)) {
    'User not found'
  } else if (!$.user.verified) {
    'Please verify your email'
  } else if ($.user.age < 18) {
    'Must be 18 or older'
  } else {
    'Welcome!'
  }
}

// Output
{
  "canProceed": true,
  "message": "Welcome!"
}
```

---

## Common Use Cases

### 1. Grade Assignment
```javascript
if (score >= 90) { 'A' }
else if (score >= 80) { 'B' }
else if (score >= 70) { 'C' }
else if (score >= 60) { 'D' }
else { 'F' }
```

### 2. Price Calculation
```javascript
if (quantity > 100) { price * 0.8 }
else if (quantity > 50) { price * 0.9 }
else { price }
```

### 3. Status Determination
```javascript
if (order.shipped && order.delivered) { 'completed' }
else if (order.shipped) { 'in transit' }
else { 'processing' }
```

### 4. Validation
```javascript
if (!exists(user.email)) { 'Email required' }
else if (!exists(user.password)) { 'Password required' }
else { 'Valid' }
```

---

## Best Practices

### ✅ Do's
- Use meaningful condition names
- Keep conditions simple and readable
- Order conditions from most to least specific
- Use else-if for mutually exclusive conditions
- Return early with guard clauses

### ❌ Don'ts
- Don't nest too deeply (max 2-3 levels)
- Avoid complex boolean logic in conditions
- Don't repeat code in multiple branches
- Don't use if-else for simple ternary cases

---

## If-Else vs Ternary Operator

### Use If-Else For:
- Multiple branches (else-if)
- Complex logic in each branch
- Multi-line expressions

```javascript
if (condition) {
  // Complex logic
  let x = calculate();
  x * 2
} else {
  // Other complex logic
  0
}
```

### Use Ternary For:
- Simple two-way choices
- Single-line expressions
- Inline conditions

```javascript
condition ? value1 : value2
```

---

## If-Else vs Switch

### Use If-Else When:
- Comparing ranges (`>`, `<`, `>=`, `<=`)
- Complex boolean conditions
- Different fields in each condition

### Use Switch When:
- Comparing same value against multiple constants
- Exact equality checks
- Many (5+) branches for same field

```javascript
// If-Else - Good for ranges
if (age < 13) { 'child' }
else if (age < 20) { 'teen' }
else { 'adult' }

// Switch - Good for exact matches
switch (status) {
  case 'pending': 'yellow'
  case 'approved': 'green'
  case 'rejected': 'red'
  default: 'gray'
}
```

---

## Performance Tips

1. **Order Matters**: Put most likely conditions first
2. **Avoid Complex Conditions**: Extract to variables
3. **Use Short-Circuit Evaluation**: Place cheaper checks first

```javascript
// Good - cheap check first
if ($.active && complexCalculation()) { ... }

// Less optimal
if (complexCalculation() && $.active) { ... }
```

---

## Related Features

- [**Switch Statements**](09-switch.md) - Multi-way branching
- [**Ternary Operator**](06-operators.md) - Inline conditionals
- [**For-Of Loops**](10-for-of.md) - Iteration with conditionals

---

**Category:** Control Flow  
**Since:** 1.0.0
