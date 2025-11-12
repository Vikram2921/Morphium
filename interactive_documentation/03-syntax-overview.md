# Syntax Overview

Complete guide to Morphium DSL syntax, operators, and language constructs.

## Table of Contents
- [Variables](#variables)
- [Operators](#operators)
- [Literals](#literals)
- [Arrays](#arrays)
- [Objects](#objects)
- [Functions](#functions)
- [Lambda Expressions](#lambda-expressions)
- [Control Flow](#control-flow)
- [Method Calls](#method-calls)
- [Property Access](#property-access)
- [Spread Operator](#spread-operator)
- [String Interpolation](#string-interpolation)
- [Operator Precedence](#operator-precedence)
- [Syntax Rules](#syntax-rules)

---

## Variables

### Declaration and Assignment

```morph
// Simple assignment
name = "John"
age = 30

// Multiple assignments
firstName = "John"
lastName = "Doe"
fullName = firstName + " " + lastName

// Reassignment
counter = 0
counter = counter + 1
counter = counter + 1  // counter is now 2
```

### Naming Conventions

```morph
// Recommended: camelCase
firstName = "John"
totalAmount = 1000
isActiveUser = true

// Also valid: snake_case
first_name = "John"
total_amount = 1000
is_active_user = true

// Mixed (valid but not recommended)
userID = 123
XMLParser = "..."
```

### Reserved Keywords

These cannot be used as variable names:
- `if`, `else`, `switch`, `case`, `default`
- `for`, `of`, `in`, `while`, `do`
- `break`, `continue`, `return`
- `true`, `false`, `null`
- `function`, `import`, `as`
- `typeof`, `new`

---

## Operators

### Arithmetic Operators

```morph
// Addition
sum = 10 + 5          // 15
concat = "Hello" + " World"  // "Hello World"

// Subtraction
diff = 10 - 5         // 5

// Multiplication
product = 10 * 5      // 50

// Division
quotient = 10 / 5     // 2
decimal = 10 / 3      // 3.333...

// Modulo (remainder)
remainder = 10 % 3    // 1

// Power
power = 2 ** 8        // 256
```

### Comparison Operators

```morph
// Equality
isEqual = 5 == 5           // true
isNotEqual = 5 != 3        // true

// Relational
greater = 10 > 5           // true
less = 10 < 5              // false
greaterOrEqual = 10 >= 10  // true
lessOrEqual = 5 <= 10      // true

// String comparison
same = "hello" == "hello"  // true
different = "Hello" != "hello"  // true (case-sensitive)
```

### Logical Operators

```morph
// AND - both must be true
canAccess = isLoggedIn && hasPermission

// OR - at least one must be true
isValid = hasEmail || hasPhone

// NOT - negation
isInactive = !isActive

// Complex expressions
eligible = (age >= 18) && (hasLicense || hasPermit)

// Short-circuit evaluation
result = false && expensiveCheck()  // expensiveCheck() never called
```

### Ternary Operator (via if expression)

```morph
// If expression as ternary
status = if (score >= 60) "Pass" else "Fail"

// Nested ternaries
grade = if (score >= 90) "A" 
        else if (score >= 80) "B"
        else if (score >= 70) "C"
        else "F"
```

### Assignment Operators

```morph
// Basic assignment
x = 10

// Currently, compound assignment not supported
// x += 5  // NOT SUPPORTED
// Use:
x = x + 5
x = x - 3
x = x * 2
x = x / 2
```

---

## Literals

### Number Literals

```morph
// Integers
zero = 0
positive = 42
negative = -100
large = 1000000

// Decimals
pi = 3.14159
small = 0.001
negative = -99.99

// Scientific notation
avogadro = 6.022e23
planck = 6.626e-34

// Special values
infinity = 1 / 0      // Infinity
negInfinity = -1 / 0  // -Infinity
notANumber = 0 / 0    // NaN
```

### String Literals

```morph
// Double quotes (required)
simple = "Hello, World!"
empty = ""

// Escape sequences
newline = "Line 1\nLine 2"
tab = "Column1\tColumn2"
quote = "She said \"Hi\""
backslash = "Path: C:\\Users\\John"

// Common escape sequences:
// \n  - newline
// \t  - tab
// \"  - double quote
// \\  - backslash
// \r  - carriage return
```

### Boolean Literals

```morph
// Boolean values
yes = true
no = false

// Boolean from expressions
isPositive = number > 0
isEmpty = items.len() == 0
hasData = data != null
```

### Null Literal

```morph
// Represents absence of value
notSet = null
optional = null

// Checking for null
if (value == null) {
  log("Value is null")
}

// Default values
displayName = if (name != null) name else "Anonymous"
```

---

## Arrays

### Array Literals

```morph
// Empty array
empty = []

// Number array
numbers = [1, 2, 3, 4, 5]

// String array
names = ["Alice", "Bob", "Charlie"]

// Mixed types (allowed but not recommended)
mixed = [1, "text", true, null]

// Multi-line arrays
users = [
  "Alice",
  "Bob",
  "Charlie",
  "David"
]
```

### Array Indexing

```morph
items = ["a", "b", "c", "d"]

// Zero-based indexing
first = items[0]     // "a"
second = items[1]    // "b"

// Negative indexing (from end)
last = items[-1]     // "d"
secondLast = items[-2]  // "c"

// Out of bounds returns null
notFound = items[99] // null
```

### Nested Arrays

```morph
// 2D array
matrix = [
  [1, 2, 3],
  [4, 5, 6],
  [7, 8, 9]
]

// Accessing nested elements
element = matrix[1][2]  // 6 (second row, third column)

// Array of objects
users = [
  {name: "Alice", age: 30},
  {name: "Bob", age: 25}
]

firstUser = users[0]        // {name: "Alice", age: 30}
firstName = users[0].name   // "Alice"
```

---

## Objects

### Object Literals

```morph
// Empty object
empty = {}

// Simple object
person = {
  name: "John",
  age: 30,
  email: "john@example.com"
}

// Multi-line object (recommended)
user = {
  id: 1,
  username: "johndoe",
  email: "john@example.com",
  isActive: true,
  role: "admin"
}
```

### Nested Objects

```morph
// Object with nested objects
company = {
  name: "TechCorp",
  address: {
    street: "123 Main St",
    city: "Boston",
    state: "MA",
    zip: "02101"
  },
  contact: {
    email: "info@techcorp.com",
    phone: "555-0100"
  }
}

// Accessing nested properties
city = company.address.city       // "Boston"
email = company.contact.email     // "info@techcorp.com"
```

### Objects with Arrays

```morph
// Object containing arrays
student = {
  name: "Alice",
  grades: [90, 85, 92, 88],
  courses: ["Math", "Science", "History"]
}

// Accessing array in object
firstGrade = student.grades[0]     // 90
courseCount = student.courses.len() // 3
```

### Dynamic Keys

```morph
// Currently, computed property names not supported directly
// key = "name"
// obj = {key: "John"}  // Creates {key: "John"}, not {name: "John"}

// Workaround: Build objects programmatically
obj = {}
obj.set("name", "John")  // If set() method available
```

---

## Functions

### Lambda Functions (Anonymous Functions)

```morph
// Single parameter, single expression
double = x -> x * 2

// Multiple parameters
add = (a, b) -> a + b

// Block body with multiple statements
process = x -> {
  doubled = x * 2
  squared = doubled * doubled
  return squared
}

// No parameters
random = () -> Math.random()
```

### Function Usage

```morph
// Defining and calling
square = x -> x * x
result = square(5)  // 25

// Inline in method calls
numbers = [1, 2, 3, 4, 5]
doubled = numbers.map(x -> x * 2)
evens = numbers.filter(x -> x % 2 == 0)

// Multiple uses
multiply = (a, b) -> a * b
ten = multiply(2, 5)
twenty = multiply(4, 5)
```

### Higher-Order Functions

```morph
// Functions that take functions as parameters
applyTwice = (fn, value) -> fn(fn(value))

// Usage
increment = x -> x + 1
result = applyTwice(increment, 5)  // 7

// Function returning function
multiplier = factor -> (x -> x * factor)
double = multiplier(2)
triple = multiplier(3)

result1 = double(5)  // 10
result2 = triple(5)  // 15
```

---

## Lambda Expressions

### Single Expression Lambda

```morph
// No braces needed for single expression
square = x -> x * x
add = (a, b) -> a + b
isEven = n -> n % 2 == 0

// In method calls
numbers.map(x -> x * 2)
users.filter(u -> u.active)
names.sorted((a, b) -> a.localeCompare(b))
```

### Block Lambda

```morph
// Braces and return for multiple statements
process = x -> {
  log("Processing: " + x)
  doubled = x * 2
  return doubled
}

// Complex transformation
transform = item -> {
  if (item.value < 0) {
    return 0
  }
  adjusted = item.value * 1.1
  rounded = Math.round(adjusted)
  return rounded
}
```

### Lambda Parameters

```morph
// No parameters
getCurrentTime = () -> now()

// Single parameter (parentheses optional)
double = x -> x * 2
double = (x) -> x * 2  // Also valid

// Multiple parameters (parentheses required)
add = (a, b) -> a + b
max = (a, b) -> if (a > b) a else b

// Destructuring in parameters (if supported)
getName = ({name, age}) -> name
```

---

## Control Flow

### If Statement

```morph
// Simple if
if (score >= 60) {
  log("Pass")
}

// If-else
if (age >= 18) {
  status = "Adult"
} else {
  status = "Minor"
}

// If-else-if chain
if (score >= 90) {
  grade = "A"
} else if (score >= 80) {
  grade = "B"
} else if (score >= 70) {
  grade = "C"
} else {
  grade = "F"
}
```

### If Expression

```morph
// Returns a value
status = if (score >= 60) "Pass" else "Fail"

// Multi-line
message = if (errors.len() > 0) {
  "Found " + errors.len() + " errors"
} else {
  "All good!"
}
```

### Switch Statement

```morph
// Basic switch
switch (day) {
  case "Monday":
    mood = "😴"
    break
  case "Friday":
    mood = "🎉"
    break
  case "Saturday":
  case "Sunday":
    mood = "😎"
    break
  default:
    mood = "😐"
}

// Switch with expressions
result = switch (status) {
  case "success": "✓"
  case "error": "✗"
  case "pending": "..."
  default: "?"
}
```

### For-Of Loop

```morph
// Iterate over array
for (item of items) {
  log(item)
}

// With block
for (user of users) {
  if (user.active) {
    log(user.name)
  }
}

// Break and continue
for (item of items) {
  if (item == null) {
    continue  // Skip this item
  }
  if (item.value > 100) {
    break  // Stop loop
  }
  process(item)
}
```

### For-In Loop

```morph
// Iterate with index
for (value, index in array) {
  log(index + ": " + value)
}

// Using both index and value
for (user, i in users) {
  log("User " + (i + 1) + ": " + user.name)
}
```

---

## Method Calls

### Calling Methods

```morph
// Method on object
length = text.len()
upper = text.upper()

// Method with arguments
contains = text.contains("hello")
replaced = text.replace("old", "new")

// Method chaining
result = text
  .trim()
  .upper()
  .replace(" ", "_")
```

### Array Methods

```morph
numbers = [1, 2, 3, 4, 5]

// Transformation methods
doubled = numbers.map(x -> x * 2)
evens = numbers.filter(x -> x % 2 == 0)
sum = numbers.reduce((a, b) -> a + b)

// Terminal methods
first = numbers.findFirst(x -> x > 2)
has = numbers.anyMatch(x -> x > 10)
count = numbers.count()

// Chaining methods
result = numbers
  .filter(x -> x > 2)
  .map(x -> x * 2)
  .sorted()
```

### String Methods

```morph
text = "Hello, World!"

// Query methods
len = text.len()              // 13
upper = text.upper()          // "HELLO, WORLD!"
lower = text.lower()          // "hello, world!"

// Search methods
has = text.contains("World")  // true
starts = text.startsWith("Hello")  // true
ends = text.endsWith("!")     // true

// Transform methods
trimmed = "  text  ".trim()   // "text"
replaced = text.replace("World", "Universe")
split = text.split(", ")      // ["Hello", "World!"]
```

---

## Property Access

### Dot Notation

```morph
user = {
  name: "John",
  age: 30,
  email: "john@example.com"
}

// Access properties
userName = user.name    // "John"
userAge = user.age      // 30

// Nested access
address = {
  city: "Boston",
  location: {
    lat: 42.3601,
    lng: -71.0589
  }
}

city = address.city               // "Boston"
latitude = address.location.lat   // 42.3601
```

### Bracket Notation

```morph
// Access with string key
user = {name: "John", age: 30}
userName = user["name"]    // "John"

// Dynamic key access
key = "age"
userAge = user[key]        // 30

// Array access
items = ["a", "b", "c"]
first = items[0]           // "a"
last = items[-1]           // "c"
```

### Safe Navigation

```morph
// Check before access
name = if (user != null) user.name else "Unknown"

// Nested null checks
city = if (user != null && user.address != null) {
  user.address.city
} else {
  "Not specified"
}

// Using default values
email = if (user != null && user.email != null) {
  user.email
} else {
  "no-email@example.com"
}
```

---

## Spread Operator

### Object Spread

```morph
// Copy and merge objects
original = {a: 1, b: 2}
copy = {...original}  // {a: 1, b: 2}

// Merge multiple objects
defaults = {color: "blue", size: "medium"}
custom = {color: "red"}
final = {...defaults, ...custom}  // {color: "red", size: "medium"}

// Add properties
user = {name: "John", age: 30}
extended = {...user, role: "admin", active: true}
// {name: "John", age: 30, role: "admin", active: true}
```

### Array Spread

```morph
// Copy array
original = [1, 2, 3]
copy = [...original]  // [1, 2, 3]

// Concatenate arrays
first = [1, 2, 3]
second = [4, 5, 6]
combined = [...first, ...second]  // [1, 2, 3, 4, 5, 6]

// Add elements
numbers = [2, 3, 4]
extended = [1, ...numbers, 5]  // [1, 2, 3, 4, 5]
```

---

## String Interpolation

### Template Strings (if supported)

```morph
// Basic interpolation
name = "John"
greeting = `Hello, ${name}!`  // "Hello, John!"

// Expression interpolation
age = 30
message = `${name} is ${age} years old`
// "John is 30 years old"

// Complex expressions
price = 99.99
tax = 0.08
total = `Total: $${price * (1 + tax)}`
// "Total: $107.99"
```

### Concatenation (Current)

```morph
// String concatenation with +
name = "John"
age = 30
message = "Hello, " + name + "! You are " + age + " years old."

// Convert numbers to string
total = "Total: $" + (price * (1 + tax))

// Multi-line building
report = "Name: " + name + "\n" +
         "Age: " + age + "\n" +
         "Status: " + status
```

---

## Operator Precedence

From highest to lowest precedence:

```morph
// 1. Parentheses
result = (2 + 3) * 4  // 20, not 14

// 2. Member access, array indexing
obj.prop[0].method()

// 3. Function calls
func(arg)

// 4. Unary operators
!condition
-number

// 5. Exponentiation
2 ** 3  // 8

// 6. Multiplication, Division, Modulo
a * b / c % d

// 7. Addition, Subtraction
a + b - c

// 8. Comparison
a > b
a < b
a >= b
a <= b

// 9. Equality
a == b
a != b

// 10. Logical AND
a && b

// 11. Logical OR
a || b

// 12. Assignment
x = value
```

### Precedence Examples

```morph
// Without parentheses
result = 2 + 3 * 4        // 14 (multiplication first)
condition = x > 5 && y < 10  // comparison before AND

// With parentheses for clarity
result = (2 + 3) * 4      // 20
condition = (x > 5) && (y < 10)  // Same result, more clear

// Complex expressions
value = (a + b) * (c - d) / (e + f)
allowed = (age >= 18) && (hasLicense || hasPermit)
```

---

## Syntax Rules

### Statement Terminators

```morph
// Semicolons are optional (and not typically used)
name = "John"
age = 30

// Multiple statements on one line (not recommended)
x = 1; y = 2; z = 3
```

### Block Syntax

```morph
// Blocks use braces
if (condition) {
  statement1
  statement2
}

// Single statement can omit braces (not recommended)
if (condition)
  statement

// Recommended: always use braces
if (condition) {
  statement
}
```

### Line Breaks

```morph
// Method chaining across lines
result = data
  .filter(x -> x > 0)
  .map(x -> x * 2)
  .reduce((a, b) -> a + b)

// Array across lines
items = [
  "item1",
  "item2",
  "item3"
]

// Object across lines
user = {
  name: "John",
  age: 30,
  email: "john@example.com"
}
```

### Whitespace

```morph
// Whitespace is flexible
compact=1+2*3
spaced = 1 + 2 * 3

// Both are valid, but spaced is more readable
```

---

## Complete Example

Here's a comprehensive example using multiple syntax features:

```morph
/*
 * Process customer orders and calculate totals
 */

// Input data
orders = [
  {id: 1, customer: "Alice", amount: 100, status: "completed"},
  {id: 2, customer: "Bob", amount: 200, status: "pending"},
  {id: 3, customer: "Alice", amount: 150, status: "completed"},
  {id: 4, customer: "Charlie", amount: 75, status: "cancelled"}
]

// Filter and transform
validOrders = orders.filter(order -> {
  // Only completed orders
  if (order.status != "completed") {
    return false
  }
  // Amount must be positive
  if (order.amount <= 0) {
    return false
  }
  return true
})

// Group by customer
byCustomer = validOrders.groupBy(order -> order.customer)

// Calculate totals
customerTotals = byCustomer.entries().map(entry -> {
  customer = entry.key
  customerOrders = entry.value
  
  total = customerOrders
    .map(order -> order.amount)
    .reduce((sum, amount) -> sum + amount)
  
  return {
    customer: customer,
    orderCount: customerOrders.len(),
    total: total
  }
})

// Sort by total descending
topCustomers = customerTotals
  .sorted((a, b) -> b.total - a.total)
  .slice(0, 10)

// Log results
for (customer, index in topCustomers) {
  rank = index + 1
  log(rank + ". " + customer.customer + 
      ": $" + customer.total + 
      " (" + customer.orderCount + " orders)")
}

// Return top customer
topCustomer = if (topCustomers.len() > 0) {
  topCustomers[0]
} else {
  null
}
```

---

## Quick Reference Card

```morph
// Variables
name = "value"

// Data Types
number = 42
string = "text"
boolean = true
array = [1, 2, 3]
object = {key: "value"}
nothing = null

// Operators
+ - * / %           // Arithmetic
== != > < >= <=     // Comparison
&& || !             // Logical

// Arrays
items[0]            // Access
items.map(fn)       // Transform
items.filter(fn)    // Filter
items.reduce(fn)    // Reduce

// Objects
obj.prop            // Access
obj["key"]          // Bracket access
{...obj, new: val}  // Spread

// Functions
fn = x -> x * 2
fn = x -> { return x * 2 }

// Control Flow
if (cond) { }
for (item of items) { }
switch (val) { case x: break }

// Methods
text.upper()
array.map(fn)
number.toString()
```

---

## Navigation

- [← Back: Basic Concepts](02-basic-concepts.md)
- [→ Next: Function Reference](FUNCTION_REFERENCE.md)
- [Control Flow Guide](08-if-else.md)
- [README](README.md)

---

*Last updated: 2025-01-12*
