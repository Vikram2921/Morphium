# Morphium DSL - Quick Reference Card

## Input Access
```javascript
$.property              // Direct access
$.person.name          // Nested access
$.items[0]             // Array access
$.user?.address?.city  // Safe navigation
let root = $           // Assign to variable
```

## Variables
```javascript
let local = value      // Local variable (scoped)
global shared = value  // Global variable (everywhere)
```

## Functions
```javascript
function name(param1, param2) {
  let result = param1 + param2
  return result
}

let value = name(10, 20)
```

## Built-in Functions

### Arrays
```javascript
map(array, "item", expr)           // Transform each
filter(array, "item", predicate)   // Filter elements
reduce(array, "acc", "item", init, expr)  // Reduce
pluck(array, "key")                // Extract property
indexBy(array, "key")              // Create lookup object
```

### Streams API
```javascript
findFirst(array, "item", predicate)   // Find first match
anyMatch(array, "item", predicate)    // Has any match?
allMatch(array, "item", predicate)    // All match?
noneMatch(array, "item", predicate)   // None match?
distinct(array)                       // Remove duplicates
sorted(array, "key"?)                 // Sort array
limit(array, n)                       // Take first n
skip(array, n)                        // Skip first n
slice(array, start, end)              // Slice range
flatMap(array, "item", expr)          // Map and flatten
groupBy(array, "key")                 // Group by property
partition(array, "item", predicate)   // Split by condition
concat(array1, array2)                // Concatenate
reverse(array)                        // Reverse order
```

### Aggregation
```javascript
count(array)           // Count elements
sum(array)            // Sum numbers
avg(array)            // Average
min(array)            // Minimum
max(array)            // Maximum
len(value)            // Length
```

### Object
```javascript
merge(obj1, obj2, ...)        // Deep merge
get(obj, "path.to.prop")      // Safe get
set(obj, "path", value)       // Immutable set
delete(obj, "path")           // Immutable delete
exists(value)                 // Check existence
```

### String
```javascript
split(str, sep)               // Split string
join(array, sep)              // Join array
upper(str)                    // Uppercase
lower(str)                    // Lowercase
trim(str)                     // Trim whitespace
replace(str, old, new)        // Replace text
slice(str, start, end)        // Substring
```

### Type Conversion
```javascript
toString(value)               // To string
toNumber(value)               // To number
toBool(value)                 // To boolean
jsonParse(str)                // Parse JSON
jsonStringify(value)          // Stringify JSON
```

### Date/Time
```javascript
now()                         // Current timestamp
formatDate(dateStr, format)   // Format date
```

## Operators
```javascript
// Arithmetic
+ - * / %

// Comparison
== === != !== < <= > >=

// Logical
&& || !

// Ternary
condition ? true : false

// Null coalescing
value ?? default

// Safe navigation
obj?.prop?.nested
```

## Import/Export
```javascript
// Import module
import "path/file.morph" as name

// Named import
from "path/file.morph" import name1, name2

// Export
export myValue = { ... }
export myFunction = (x) => x * 2
```

## Built-in Utilities

### DateUtils
```javascript
import "morphs/DateUtils.morph" as dateUtils

dateUtils.now()                      // Current time
dateUtils.formatShort("2024-01-15")  // dd/MM/yyyy
dateUtils.formatLong("2024-01-15")   // dd MMM yyyy
dateUtils.extractYear(dateStr)       // Extract year
dateUtils.extractMonth(dateStr)      // Extract month
dateUtils.getQuarterName(dateStr)    // Q1, Q2, etc.
dateUtils.monthName(monthNum)        // Full month name
```

### NumberUtils
```javascript
import "morphs/NumberUtils.morph" as numUtils

numUtils.round(3.14159, 2)           // 3.14
numUtils.formatCurrency(1234.56, "$") // $1234.56
numUtils.formatPercent(0.15, 1)      // 15.0%
numUtils.formatCompact(1500000)      // 1.5M
numUtils.percentage(25, 100)         // 25
numUtils.abs(-5)                     // 5
numUtils.clamp(15, 0, 10)           // 10
```

### FormatUtils
```javascript
import "morphs/FormatUtils.morph" as formatUtils

formatUtils.capitalize("hello")      // Hello
formatUtils.titleCase("hello world") // Hello World
formatUtils.camelCase("hello world") // helloWorld
formatUtils.snakeCase("hello world") // hello_world
formatUtils.kebabCase("hello world") // hello-world
formatUtils.slugify("Hello World!")  // hello-world
formatUtils.truncate(str, 50, "...")  // Truncate
formatUtils.padLeft(str, 10, "0")    // Pad left
```

### ArrayUtils
```javascript
import "morphs/ArrayUtils.morph" as arrayUtils

arrayUtils.first(array, default)     // First element
arrayUtils.last(array, default)      // Last element
arrayUtils.unique(array)             // Remove duplicates
arrayUtils.flatten(array)            // Flatten nested
arrayUtils.chunk(array, 3)           // Split into chunks
arrayUtils.compact(array)            // Remove nulls
```

### ValidationUtils
```javascript
import "morphs/ValidationUtils.morph" as validate

validate.isEmail(str)                // Email check
validate.isUrl(str)                  // URL check
validate.isPhone(str)                // Phone check
validate.inRange(num, min, max)      // Range check
validate.minLength(str, min)         // Min length
validate.maxLength(str, max)         // Max length
validate.isAlphaNumeric(str)         // Alphanumeric
```

## Examples

### Basic Transform
```javascript
{
  fullName: $.person.first + " " + $.person.last,
  age: $.person.age,
  isAdult: $.person.age >= 18
}
```

### Map Array
```javascript
{
  items: map($.items, "item", {
    id: item.id,
    total: item.qty * item.price
  })
}
```

### Filter & Reduce
```javascript
let adults = filter($.users, "u", u.age >= 18)
let totalAge = reduce(adults, "acc", "u", 0, acc + u.age)

{ adults: adults, avgAge: totalAge / count(adults) }
```

### Custom Function
```javascript
function calculateTax(amount) {
  return amount * 0.1
}

{
  subtotal: $.amount,
  tax: calculateTax($.amount),
  total: $.amount + calculateTax($.amount)
}
```

### With Utilities
```javascript
import "morphs/DateUtils.morph" as dateUtils
import "morphs/NumberUtils.morph" as numUtils

{
  date: dateUtils.formatShort($.orderDate),
  amount: numUtils.formatCurrency($.total, "$"),
  quarter: dateUtils.getQuarterName($.orderDate)
}
```

### Streams Operations
```javascript
{
  // Find operations
  firstExpensive: findFirst($.items, "i", i.price > 100),
  hasExpensive: anyMatch($.items, "i", i.price > 100),
  
  // Transform operations
  sorted: sorted($.items, "price"),
  distinct: distinct($.tags),
  grouped: groupBy($.orders, "status"),
  
  // Slice operations
  top5: limit(sorted($.items, "price"), 5),
  skip10: skip($.items, 10),
  middle: slice($.items, 5, 15)
}
```

## Host Function Interface

```java
public class MyFunction implements MorphiumFunction {
    @Override
    public String name() {
        return "myFunc";
    }
    
    @Override
    public int minParams() {
        return 1;
    }
    
    @Override
    public int maxParams() {
        return 2;
    }
    
    @Override
    public JsonNode call(JsonNode root, JsonNode... params) {
        // root = input ($)
        // params = function arguments
        String param1 = params[0].asText();
        return TextNode.valueOf(param1.toUpperCase());
    }
}

// Register
registry.register("myNamespace", new MyFunction());

// Use in morph
{ result: myNamespace.myFunc($.value) }
```

## Run Playground

```bash
mvn exec:java
# Then open http://localhost:8080
```

## Quick Tips

1. Use `$` to access input JSON
2. Use `let` for local variables, `global` for global
3. Functions can be recursive
4. Import utility libraries for common tasks
5. Use streams API for large datasets
6. Safe navigation with `?.` prevents null errors
7. All operations are immutable by default
8. Check examples/ directory for more patterns

---

**Morphium DSL** - Transform JSON with ease! 🚀
