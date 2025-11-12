# Morphium DSL - Complete Feature Guide

## Overview

Morphium is a JavaScript-like JSON transformation DSL for Java that provides powerful data transformation capabilities with a syntax familiar to JS developers. It includes comprehensive Java Streams API equivalent operations for high-performance data processing.

## Key Features

### 1. **JavaScript-Familiar Syntax**
- Object and array literals: `{ key: value }`, `[ 1, 2, 3 ]`
- Safe navigation: `$.user?.address?.city`
- Null coalescing: `$.value ?? "default"`
- Ternary operators: `condition ? true : false`
- Comments: `// single line` and `/* multi-line */`

### 2. **$ as Input Variable**
The special variable `$` represents the root input JSON:
```javascript
let root = $
let user = $.user
let name = $.user.name
```

### 3. **Variable Declarations**
```javascript
// Local variables
let x = 10
let name = $.user.name

// Global variables (accessible across scopes)
let global config = $.config
```

### 4. **Custom Functions**
Define reusable functions:
```javascript
function calculateTax(amount) {
  return amount * 0.1
}

function getFullName(first, last) {
  return first + " " + last
}

{
  tax: calculateTax(100),
  name: getFullName($.firstName, $.lastName)
}
```

### 5. **Java Streams API Equivalent**

#### Filtering & Matching
```javascript
// filter - keep elements that match predicate
filter($.items, "item", item.price > 100)

// anyMatch - true if any element matches
anyMatch($.items, "item", item.active)

// allMatch - true if all elements match
allMatch($.items, "item", item.price > 0)

// noneMatch - true if no elements match
noneMatch($.items, "item", item.price < 0)

// findFirst - return first matching element
findFirst($.items, "item", item.status == "active")
```

#### Mapping & Transformation
```javascript
// map - transform each element
map($.items, "item", {
  id: item.id,
  total: item.qty * item.price
})

// flatMap - map and flatten nested arrays
flatMap($.users, "user", user.tags)

// pluck - extract a field from all elements
pluck($.items, "name")
```

#### Aggregation
```javascript
// count - count elements
count($.items)
count($.items, "item", item.active)  // conditional count

// sum - sum numeric values
sum(pluck($.items, "price"))

// avg - average of numeric values
avg(pluck($.items, "price"))

// min/max - minimum/maximum values
min(pluck($.items, "price"))
max(pluck($.items, "price"))

// reduce - custom aggregation
reduce($.items, "acc", "item", 0, acc + item.price)
```

#### Sorting & Ordering
```javascript
// sorted - sort elements
sorted($.numbers)           // natural order
sorted($.items, "price")    // sort by field

// reverse - reverse order
reverse($.items)

// distinct - remove duplicates
distinct($.items)
```

#### Slicing & Limiting
```javascript
// limit - take first N elements
limit($.items, 5)

// skip - skip first N elements
skip($.items, 2)

// slice - extract a range
slice($.items, 2, 5)  // items from index 2 to 5
```

#### Grouping & Partitioning
```javascript
// groupBy - group by a field
groupBy($.users, "department")
// Result: { "Engineering": [...], "Sales": [...] }

// partition - split into two groups
partition($.users, "user", user.active)
// Result: { "true": [...], "false": [...] }

// indexBy - create lookup map
indexBy($.users, "id")
// Result: { "u1": {...}, "u2": {...} }
```

#### Inspection & Side Effects
```javascript
// peek - perform side effects without modifying stream
peek($.items, "item", /* some action */)

// forEach - iterate with side effects
forEach($.items, "item", /* some action */)
```

### 6. **Array & Object Operations**

#### Array Operations
```javascript
// concat - combine arrays
concat([1, 2], [3, 4], [5])

// join - join array elements into string
join($.items, ", ")

// split - split string into array
split("a,b,c", ",")

// reverse - reverse array
reverse($.items)
```

#### Object Operations
```javascript
// merge - deep merge objects
merge({a: 1}, {b: 2}, {c: 3})

// keys - get object keys
keys($.user)  // ["name", "age", "email"]

// values - get object values
values($.user)

// entries - get key-value pairs
entries($.user)  // [["name", "John"], ["age", 30]]

// get - safe path access
get($, "user.address.city")

// set - immutable update
set($, "user.name", "New Name")
```

### 7. **String Operations**
```javascript
upper("hello")              // "HELLO"
lower("HELLO")              // "hello"
trim("  hello  ")           // "hello"
replace("hello", "l", "r")  // "herro"
split("a,b,c", ",")         // ["a", "b", "c"]
join(["a", "b"], ",")       // "a,b"
```

### 8. **Type Conversions**
```javascript
toNumber("123")      // 123
toString(123)        // "123"
toBool(1)            // true
jsonParse('{"a":1}') // {a: 1}
jsonStringify({a:1}) // '{"a":1}'
```

### 9. **Utility Functions**
```javascript
exists($.user)       // true if not null
len($.items)         // array length or string length
now()                // current ISO timestamp
formatDate("2024-01-01", "dd/MM/yyyy")
```

### 10. **Module System**

#### Importing Modules
```javascript
// Import entire module
import "utils.morph" as utils
utils.someFunction()

// Import specific exports
from "utils.morph" import calculateTax, formatDate
```

#### Exporting
```javascript
export root = {
  calculateTax: function(amount) { return amount * 0.1 },
  constants: { taxRate: 0.1 }
}
```

### 11. **Running Other Morph Files**
Execute another morph file with custom input:
```javascript
// Run another transformation
let enrichedUser = runMorph("enrichUser.morph", $.user)

// Chain transformations
let processed = runMorph("step1.morph", $)
let final = runMorph("step2.morph", processed)
```

### 12. **Custom Java Functions**
Register Java functions that can be called from DSL:

```java
engine.registerFunction(new MorphiumFunction() {
    @Override
    public String getName() {
        return "customFunction";
    }
    
    @Override
    public int getMinParams() {
        return 1;
    }
    
    @Override
    public int getMaxParams() {
        return 2;
    }
    
    @Override
    public JsonNode call(JsonNode root, JsonNode[] params) {
        // Your custom logic
        return result;
    }
});
```

## Complete Examples

### Example 1: E-Commerce Order Processing
```javascript
// Input: Order with items
let items = $.items
let customerType = $.customerType ?? "regular"

// Calculate totals with discounts
let enrichedItems = map(items, "item", {
  id: item.id,
  name: item.name,
  price: item.price,
  quantity: item.quantity,
  subtotal: item.price * item.quantity,
  discount: item.price * item.quantity * 
    (customerType == "vip" ? 0.2 : 0.05)
})

let subtotal = sum(pluck(enrichedItems, "subtotal"))
let totalDiscount = sum(pluck(enrichedItems, "discount"))
let tax = (subtotal - totalDiscount) * 0.1

{
  orderId: $.orderId,
  customer: $.customer,
  items: enrichedItems,
  summary: {
    subtotal: subtotal,
    discount: totalDiscount,
    tax: tax,
    total: subtotal - totalDiscount + tax,
    itemCount: count(items)
  },
  processedAt: now()
}
```

### Example 2: Data Analytics Pipeline
```javascript
// Input: Sales data
let sales = $.sales

{
  // Time-based analysis
  salesByMonth: groupBy(sales, "month"),
  
  // Performance metrics
  topPerformers: limit(sorted(sales, "amount"), 10),
  lowPerformers: limit(reverse(sorted(sales, "amount")), 10),
  
  // Statistical analysis
  stats: {
    total: sum(pluck(sales, "amount")),
    average: avg(pluck(sales, "amount")),
    median: sorted(pluck(sales, "amount"))[count(sales) / 2],
    max: max(pluck(sales, "amount")),
    min: min(pluck(sales, "amount"))
  },
  
  // Segmentation
  highValue: partition(sales, "s", s.amount > 10000),
  byRegion: groupBy(sales, "region"),
  
  // Trends
  quarterlyTrend: map(
    groupBy(sales, "quarter"),
    "q",
    {
      quarter: q.key,
      total: sum(pluck(q.value, "amount")),
      count: count(q.value)
    }
  )
}
```

### Example 3: User Data Enrichment
```javascript
let users = $.users

{
  enrichedUsers: map(users, "user", {
    // Original fields
    id: user.id,
    name: user.name,
    email: user.email,
    age: user.age,
    
    // Computed fields
    isAdult: user.age >= 18,
    category: user.age < 18 ? "minor" : 
              user.age < 65 ? "adult" : "senior",
    emailDomain: split(user.email, "@")[1],
    initials: upper(
      split(user.name, " ")[0][0] + 
      split(user.name, " ")[1][0]
    ),
    
    // Nested enrichment
    profile: {
      age: user.age,
      ageGroup: user.age / 10,
      generation: user.age < 25 ? "Gen Z" :
                  user.age < 41 ? "Millennial" : "Gen X"
    }
  }),
  
  // Summary statistics
  summary: {
    total: count(users),
    adults: count(users, "u", u.age >= 18),
    avgAge: avg(pluck(users, "age")),
    byGeneration: groupBy(
      map(users, "u", {
        gen: u.age < 25 ? "Gen Z" : "Other"
      }),
      "gen"
    )
  }
}
```

### Example 4: Advanced Stream Operations
```javascript
let products = $.products

{
  // Complex filtering
  filtered: filter(
    filter(products, "p", p.category == "Electronics"),
    "p",
    p.price > 100
  ),
  
  // Nested mapping
  categorizedProducts: map(
    groupBy(products, "category"),
    "cat",
    {
      category: cat.key,
      items: map(cat.value, "item", {
        name: item.name,
        price: item.price
      }),
      stats: {
        count: count(cat.value),
        avgPrice: avg(pluck(cat.value, "price"))
      }
    }
  ),
  
  // Flat mapping
  allTags: distinct(flatMap(products, "p", p.tags)),
  
  // Conditional aggregation
  expensiveItemsTotal: sum(
    pluck(
      filter(products, "p", p.price > 500),
      "price"
    )
  ),
  
  // Pagination
  page1: limit(sorted(products, "price"), 10),
  page2: limit(skip(sorted(products, "price"), 10), 10)
}
```

## Performance Tips

1. **Use streams efficiently**: Chain operations to minimize iterations
2. **Leverage distinct early**: Remove duplicates before expensive operations
3. **Use findFirst**: Stop processing once a match is found
4. **Optimize filters**: Apply most restrictive filters first
5. **Consider runMorph**: Modularize complex transformations

## Playground

Start the interactive playground to test transformations in real-time:

```bash
mvn exec:java
```

Then open http://localhost:8080 in your browser.

## API Usage

```java
import com.morphium.core.MorphiumEngine;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// Create engine
MorphiumEngine engine = new MorphiumEngine();

// Transform from file
JsonNode result = engine.transform("transform.morph", inputJson);

// Transform from string
String transform = "{ fullName: $.first + ' ' + $.last }";
JsonNode result = engine.transformFromString(transform, inputJson);

// Register custom functions
engine.registerFunction(customFunction);
```

## License

MIT License
