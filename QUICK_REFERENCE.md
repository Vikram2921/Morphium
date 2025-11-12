# Morphium DSL - Quick Reference Card

## Input Variable
```javascript
$              // Root input JSON
$.field        // Direct field access
$.user.name    // Nested access
$.items[0]     // Array index
$.user?.address // Safe navigation
```

## Variables
```javascript
let x = 10                    // Local variable
let root = $                  // Assign input to variable
global CONFIG = $.config      // Global variable
```

## Functions
```javascript
function add(a, b) {
  return a + b
}

function greet(name) {
  return "Hello " + name
}
```

## Operators
```javascript
// Arithmetic
+  -  *  /  %

// Comparison
==  ===  !=  !==  <  <=  >  >=

// Logical
&&  ||  !

// Ternary
condition ? ifTrue : ifFalse

// Null coalescing
value ?? defaultValue
```

## Stream Operations

### Filtering
```javascript
filter(array, "item", item.price > 100)
anyMatch(array, "item", item.active)
allMatch(array, "item", item.valid)
noneMatch(array, "item", item.error)
findFirst(array, "item", item.id == "ABC")
```

### Mapping
```javascript
map(array, "item", item.name)
map(array, "item", { id: item.id, name: item.name })
flatMap(array, "item", item.tags)
pluck(array, "fieldName")
```

### Aggregation
```javascript
count(array)
count(array, "item", item.active)
sum(array)
avg(array)
min(array)
max(array)
reduce(array, "acc", "item", 0, acc + item.value)
```

### Sorting
```javascript
sorted(array)              // Natural order
sorted(array, "price")     // By field
reverse(array)             // Reverse order
distinct(array)            // Remove duplicates
```

### Slicing
```javascript
limit(array, 10)           // First 10
skip(array, 5)             // Skip first 5
slice(array, 5, 10)        // Elements 5-10
```

### Grouping
```javascript
groupBy(array, "category")
partition(array, "item", item.active)
indexBy(array, "id")
```

### Side Effects
```javascript
peek(array, "item", /* expression */)
forEach(array, "item", /* expression */)
```

## Array Operations
```javascript
concat([1,2], [3,4])       // Combine arrays
join(array, ", ")          // Array to string
split("a,b,c", ",")        // String to array
reverse(array)             // Reverse
```

## Object Operations
```javascript
merge(obj1, obj2, obj3)    // Merge objects
keys(object)               // Get keys
values(object)             // Get values
entries(object)            // Get [key, value] pairs
get(object, "path.to.field")
set(object, "path", value)
```

## String Operations
```javascript
upper("hello")             // "HELLO"
lower("HELLO")             // "hello"
trim("  text  ")           // "text"
replace("text", "t", "x")  // "xexx"
split("a,b,c", ",")        // ["a","b","c"]
join(["a","b"], ",")       // "a,b"
```

## Type Conversion
```javascript
toNumber("123")            // 123
toString(123)              // "123"
toBool(1)                  // true
jsonParse('{"a":1}')       // Parse JSON string
jsonStringify({a:1})       // Stringify to JSON
```

## Utilities
```javascript
exists(value)              // Check if not null
len(array)                 // Array length
len(string)                // String length
now()                      // Current ISO timestamp
formatDate(date, format)   // Format date
```

## Modules
```javascript
// Import entire module
import "module.morph" as mod
mod.function()

// Import specific exports
from "module.morph" import func1, func2

// Export
export root = {
  myFunc: function() { ... }
}
```

## Run Other Morph Files
```javascript
// Execute another morph file
runMorph("transform.morph", data)

// Chain transformations
let step1 = runMorph("step1.morph", $)
let step2 = runMorph("step2.morph", step1)
```

## Complete Example
```javascript
// User processing pipeline
let users = $.users

function isAdult(age) {
  return age >= 18
}

function enrichUser(user) {
  return {
    id: user.id,
    name: user.name,
    age: user.age,
    category: isAdult(user.age) ? "adult" : "minor",
    email: lower(trim(user.email))
  }
}

{
  // Transform all users
  users: map(users, "u", enrichUser(u)),
  
  // Statistics
  stats: {
    total: count(users),
    adults: count(users, "u", isAdult(u.age)),
    avgAge: avg(pluck(users, "age")),
    byCategory: groupBy(
      map(users, "u", enrichUser(u)),
      "category"
    )
  },
  
  // Filtered lists
  activeAdults: filter(
    filter(users, "u", u.active),
    "u",
    isAdult(u.age)
  ),
  
  // Sorted
  byAge: sorted(users, "age"),
  
  // Top 10
  top10: limit(sorted(users, "score"), 10),
  
  // Timestamp
  processedAt: now()
}
```

## Custom Java Functions
```java
// Implement MorphiumFunction interface
engine.registerFunction(new MorphiumFunction() {
    public String getName() { return "myFunc"; }
    public int getMinParams() { return 1; }
    public int getMaxParams() { return 2; }
    public JsonNode call(JsonNode root, JsonNode[] params) {
        // Your logic here
        return result;
    }
});
```

## Playground
```bash
mvn exec:java
# Open http://localhost:8080
```

## Common Patterns

### Map-Filter-Reduce
```javascript
let result = reduce(
  filter(
    map(array, "item", transform(item)),
    "item",
    item.valid
  ),
  "acc", "item", 0, acc + item.value
)
```

### Group and Aggregate
```javascript
let grouped = groupBy(items, "category")
let aggregated = map(grouped, "g", {
  category: g.key,
  count: count(g.value),
  total: sum(pluck(g.value, "amount"))
})
```

### Conditional Processing
```javascript
let processed = map(items, "item", {
  id: item.id,
  value: item.type == "premium" 
    ? item.value * 1.5 
    : item.value,
  discount: item.value > 100 ? 0.1 : 0
})
```

### Nested Transformations
```javascript
{
  orders: map($.orders, "o", {
    orderId: o.id,
    items: map(o.items, "i", {
      name: i.name,
      total: i.price * i.qty
    }),
    total: sum(map(o.items, "i", i.price * i.qty))
  })
}
```

---

**More Info**: See FEATURES.md for complete documentation
