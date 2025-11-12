# User-Defined Functions

Learn how to create and use custom functions in Morphium DSL to encapsulate reusable transformation logic.

---

## Overview

User-defined functions allow you to:
- **Encapsulate** complex transformation logic
- **Reuse** code across transformations
- **Improve readability** by naming operations
- **Create abstractions** for domain-specific logic

---

## Syntax

```javascript
function functionName(param1, param2, ...) {
    // Function body
    // Last expression is the return value
}
```

---

## Basic Examples

### Example 1: Simple Function

```javascript
function double(n) {
    n * 2
}

{
    original: 5,
    doubled: double(5)
}
```

**Output:**
```json
{
    "original": 5,
    "doubled": 10
}
```

---

### Example 2: Function with Multiple Parameters

```javascript
function fullName(firstName, lastName) {
    firstName + " " + lastName
}

{
    name: fullName("John", "Doe"),
    manager: fullName($.manager.first, $.manager.last)
}
```

**Input:**
```json
{
    "manager": {
        "first": "Jane",
        "last": "Smith"
    }
}
```

**Output:**
```json
{
    "name": "John Doe",
    "manager": "Jane Smith"
}
```

---

### Example 3: Function with Conditional Logic

```javascript
function discount(price, isPremium) {
    if (isPremium) {
        price * 0.8
    } else {
        price * 0.9
    }
}

{
    regularPrice: discount(100, false),
    premiumPrice: discount(100, true)
}
```

**Output:**
```json
{
    "regularPrice": 90,
    "premiumPrice": 80
}
```

---

## Working with Arrays

### Example 4: Array Processing Function

```javascript
function sumOfSquares(numbers) {
    sum(map(numbers, "n", n * n))
}

{
    result: sumOfSquares([1, 2, 3, 4, 5])
}
```

**Output:**
```json
{
    "result": 55
}
```

---

### Example 5: Filter and Transform

```javascript
function getActiveUserNames(users) {
    map(
        filter(users, "u", u.active),
        "u",
        u.name
    )
}

{
    activeUsers: getActiveUserNames($.users)
}
```

**Input:**
```json
{
    "users": [
        {"name": "Alice", "active": true},
        {"name": "Bob", "active": false},
        {"name": "Charlie", "active": true}
    ]
}
```

**Output:**
```json
{
    "activeUsers": ["Alice", "Charlie"]
}
```

---

## Complex Functions

### Example 6: Object Transformation

```javascript
function enrichUser(user) {
    {
        id: user.id,
        fullName: user.firstName + " " + user.lastName,
        age: user.age,
        isAdult: user.age >= 18,
        category: if (user.age < 18) { "minor" }
                  else if (user.age < 65) { "adult" }
                  else { "senior" }
    }
}

{
    users: map($.users, "u", enrichUser(u))
}
```

**Input:**
```json
{
    "users": [
        {"id": 1, "firstName": "Alice", "lastName": "Smith", "age": 25},
        {"id": 2, "firstName": "Bob", "lastName": "Jones", "age": 15}
    ]
}
```

**Output:**
```json
{
    "users": [
        {
            "id": 1,
            "fullName": "Alice Smith",
            "age": 25,
            "isAdult": true,
            "category": "adult"
        },
        {
            "id": 2,
            "fullName": "Bob Jones",
            "age": 15,
            "isAdult": false,
            "category": "minor"
        }
    ]
}
```

---

### Example 7: Recursive Functions

```javascript
function factorial(n) {
    if (n <= 1) {
        1
    } else {
        n * factorial(n - 1)
    }
}

{
    fact5: factorial(5),
    fact10: factorial(10)
}
```

**Output:**
```json
{
    "fact5": 120,
    "fact10": 3628800
}
```

---

### Example 8: Higher-Order Functions

```javascript
function applyToEach(array, operation) {
    map(array, "item", operation(item))
}

function triple(n) {
    n * 3
}

{
    numbers: [1, 2, 3, 4, 5],
    tripled: applyToEach([1, 2, 3, 4, 5], triple)
}
```

**Output:**
```json
{
    "numbers": [1, 2, 3, 4, 5],
    "tripled": [3, 6, 9, 12, 15]
}
```

---

## Nested Functions

### Example 9: Functions Within Functions

```javascript
function processOrder(order) {
    function calculateTax(amount) {
        amount * 0.1
    }
    
    function calculateTotal(subtotal) {
        subtotal + calculateTax(subtotal)
    }
    
    {
        orderId: order.id,
        subtotal: order.amount,
        tax: calculateTax(order.amount),
        total: calculateTotal(order.amount)
    }
}

{
    order: processOrder({id: 123, amount: 100})
}
```

**Output:**
```json
{
    "order": {
        "orderId": 123,
        "subtotal": 100,
        "tax": 10,
        "total": 110
    }
}
```

---

## Best Practices

### ✅ Do's

1. **Keep functions focused** - Each function should do one thing well
2. **Use descriptive names** - Function names should explain what they do
3. **Document complex logic** - Add comments for non-obvious operations
4. **Avoid side effects** - Functions should return values, not modify external state
5. **Test edge cases** - Consider null, empty, and boundary values

### ❌ Don'ts

1. **Don't create overly long functions** - Break complex logic into smaller functions
2. **Don't use cryptic names** - Avoid single-letter or unclear names
3. **Don't duplicate logic** - Extract common patterns into reusable functions
4. **Don't ignore errors** - Handle potential failure cases

---

## Common Use Cases

### 1. Data Validation

```javascript
function isValidEmail(email) {
    exists(email) && len(email) > 5 && exists(split(email, "@")[1])
}

{
    emails: map($.contacts, "c", {
        email: c.email,
        valid: isValidEmail(c.email)
    })
}
```

---

### 2. Data Normalization

```javascript
function normalizePhone(phone) {
    replace(replace(phone, "-", ""), " ", "")
}

{
    phone: normalizePhone($.contact.phone)
}
```

---

### 3. Complex Calculations

```javascript
function calculateBMI(weight, height) {
    weight / (height * height)
}

function getBMICategory(bmi) {
    if (bmi < 18.5) { "Underweight" }
    else if (bmi < 25) { "Normal" }
    else if (bmi < 30) { "Overweight" }
    else { "Obese" }
}

{
    bmi: calculateBMI($.weight, $.height),
    category: getBMICategory(calculateBMI($.weight, $.height))
}
```

---

### 4. Conditional Transformations

```javascript
function transformByType(item) {
    switch (item.type) {
        case "A": {id: item.id, value: item.value * 2}
        case "B": {id: item.id, value: item.value + 10}
        default: item
    }
}

{
    items: map($.items, "i", transformByType(i))
}
```

---

## Performance Tips

1. **Avoid unnecessary nesting** - Deep nesting can impact readability and performance
2. **Cache repeated calculations** - Store results in variables rather than recalculating
3. **Use built-in functions** - They're optimized for performance
4. **Consider function call overhead** - For very simple operations, inline might be faster

---

## Limitations

1. **No default parameters** - All parameters must be provided
2. **No variable arguments** - Fixed number of parameters
3. **No function overloading** - Can't have multiple functions with same name
4. **Lexical scope only** - Functions can access outer scope variables

---

## Related Topics

- [Module System](14-modules.md) - Import/export functions
- [Dynamic Imports](15-dynamic-imports.md) - Load functions dynamically
- [Error Handling](16-error-handling.md) - Handle errors in functions
- [Performance Tips](18-performance.md) - Optimize function performance

---

## Summary

User-defined functions in Morphium DSL provide powerful abstraction capabilities:
- ✅ Encapsulate reusable logic
- ✅ Support recursion and higher-order functions
- ✅ Access variables from outer scopes
- ✅ Work seamlessly with built-in functions
- ✅ Improve code organization and maintainability

---

[← Back to Documentation](README.md)
