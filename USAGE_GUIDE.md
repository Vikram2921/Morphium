# Morphium - JavaScript-like JSON Transformation DSL

A powerful, fast, and intuitive JSON transformation library for Java with JavaScript-like syntax.

## Features

- 🚀 **JavaScript-like Syntax** - Feels natural for JS/TS developers
- ⚡ **Fast Performance** - Optimized execution engine with Java Streams API
- 🔧 **Custom Functions** - Easy to define and use custom functions
- 📦 **Built-in Utilities** - DateUtils, NumberUtils, FormatUtils, and more
- 🎯 **Type Safe** - Works with Jackson JsonNode
- 🔄 **Immutable** - Functional programming by default
- 📝 **Import/Export** - Modular morph files
- 🎮 **Interactive Playground** - Test transforms in real-time

## Quick Start

### 1. Add to your project

```xml
<dependency>
    <groupId>com.morphium</groupId>
    <artifactId>morphium-dsl</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 2. Basic Usage

```java
import com.morphium.core.MorphiumEngine;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

ObjectMapper mapper = new ObjectMapper();
MorphiumEngine engine = new MorphiumEngine();

// Input JSON
String inputJson = "{\"person\": {\"first\": \"John\", \"last\": \"Doe\", \"age\": 30}}";
JsonNode input = mapper.readTree(inputJson);

// Transform
String transform = """
{
  fullName: $.person.first + " " + $.person.last,
  age: $.person.age,
  isAdult: $.person.age >= 18
}
""";

JsonNode result = engine.transformFromString(transform, input);
System.out.println(result);
```

Output:
```json
{
  "fullName": "John Doe",
  "age": 30,
  "isAdult": true
}
```

## Syntax Overview

### Access Input with `$`

```javascript
// Direct access
$.user.name

// Optional chaining
$.user?.address?.city

// Assign to variables
let root = $
let user = root.user
let name = user.name
```

### Variables

```javascript
// Local variables (scoped)
let total = $.price * $.quantity
let tax = total * 0.1

// Global variables (accessible everywhere)
global discount = 0.1
```

### Functions

```javascript
// Define custom functions
function calculateTotal(price, qty) {
  return price * qty
}

function applyTax(amount) {
  let tax = amount * 0.1
  return amount + tax
}

// Use functions
{
  subtotal: calculateTotal($.price, $.qty),
  total: applyTax(calculateTotal($.price, $.qty))
}
```

### Arrays & Streams API

```javascript
// Map
let doubled = map($.numbers, "n", n * 2)

// Filter
let adults = filter($.users, "u", u.age >= 18)

// Reduce
let sum = reduce($.numbers, "acc", "n", 0, acc + n)

// Java Streams equivalent operations
{
  first: findFirst($.items, "i", i.price > 100),
  hasAny: anyMatch($.items, "i", i.stock > 0),
  allValid: allMatch($.items, "i", i.price > 0),
  sorted: sorted($.items, "price"),
  distinct: distinct($.duplicates),
  grouped: groupBy($.users, "status"),
  flattened: flatMap($.nested, "arr", arr)
}
```

### Conditionals

```javascript
// Ternary
let status = $.age >= 18 ? "adult" : "minor"

// Nested ternary
let category = $.age < 18 ? "minor" : ($.age < 65 ? "adult" : "senior")

// Null coalescing
let name = $.user?.name ?? "Unknown"
```

### Import/Export Modules

```javascript
// math-utils.morph
export mathUtils = {
  add: (a, b) => a + b,
  multiply: (a, b) => a * b
}

// main.morph
import "math-utils.morph" as math

{
  sum: math.mathUtils.add(10, 20),
  product: math.mathUtils.multiply(5, 6)
}
```

## Built-in Utilities

Morphium comes with comprehensive utility libraries:

### DateUtils
```javascript
import "morphs/DateUtils.morph" as dateUtils

{
  now: dateUtils.now(),
  formatted: dateUtils.formatShort("2024-01-15"),
  year: dateUtils.extractYear("2024-01-15"),
  quarter: dateUtils.getQuarterName("2024-01-15")
}
```

### NumberUtils
```javascript
import "morphs/NumberUtils.morph" as numUtils

{
  rounded: numUtils.round(3.14159, 2),
  currency: numUtils.formatCurrency(1234.56, "$"),
  compact: numUtils.formatCompact(1500000),  // "1.5M"
  percentage: numUtils.percentage(25, 100)
}
```

### FormatUtils
```javascript
import "morphs/FormatUtils.morph" as formatUtils

{
  title: formatUtils.titleCase("hello world"),
  slug: formatUtils.slugify("Hello World!"),
  truncated: formatUtils.truncate("Long text...", 10),
  camelCase: formatUtils.camelCase("hello world")
}
```

### ArrayUtils
```javascript
import "morphs/ArrayUtils.morph" as arrayUtils

{
  first: arrayUtils.first($.items, null),
  last: arrayUtils.last($.items, null),
  unique: arrayUtils.unique($.duplicates),
  chunked: arrayUtils.chunk($.items, 3)
}
```

### ValidationUtils
```javascript
import "morphs/ValidationUtils.morph" as validate

{
  isValidEmail: validate.isEmail($.email),
  isValidUrl: validate.isUrl($.website),
  isInRange: validate.inRange($.age, 18, 65)
}
```

## Custom Host Functions

Register Java functions to use in transforms:

```java
import com.morphium.function.MorphiumFunction;
import com.morphium.runtime.HostFunctionRegistry;

// Define a custom function
public class UpperCaseFunction implements MorphiumFunction {
    @Override
    public String name() { return "toUpper"; }
    
    @Override
    public int minParams() { return 1; }
    
    @Override
    public int maxParams() { return 1; }
    
    @Override
    public JsonNode call(JsonNode root, JsonNode... params) {
        String text = params[0].asText();
        return TextNode.valueOf(text.toUpperCase());
    }
}

// Register the function
HostFunctionRegistry registry = new HostFunctionRegistry();
registry.register("string", new UpperCaseFunction());

MorphiumEngine engine = new MorphiumEngine();
engine.setFunctionRegistry(registry);

// Use in transform
String transform = "{ upper: string.toUpper($.text) }";
```

## Interactive Playground

Start the playground server:

```bash
mvn exec:java
# or
./run-playground.sh    # Linux/Mac
run-playground.bat     # Windows
```

Then open http://localhost:8080 in your browser.

Features:
- ✨ **Real-time transformation** - Auto-transform on code changes
- 🎨 **Syntax highlighting** - CodeMirror editor with JavaScript mode
- 📚 **Built-in examples** - Quick start templates
- ⚡ **Performance metrics** - See execution time
- 🔍 **Error messages** - Clear error reporting

## Advanced Examples

### Complex Data Transformation

```javascript
let orders = $.orders

{
  summary: {
    total: sum(pluck(orders, "amount")),
    count: count(orders),
    average: avg(pluck(orders, "amount"))
  },
  
  byStatus: groupBy(orders, "status"),
  
  highValue: filter(orders, "o", o.amount > 1000),
  
  enriched: map(orders, "order", {
    id: order.id,
    total: order.amount * 1.1,
    category: order.amount > 1000 ? "premium" : "standard",
    formatted: "$" + toString(order.amount)
  })
}
```

### Nested Transformations

```javascript
function enrichUser(user) {
  return {
    id: user.id,
    name: user.firstName + " " + user.lastName,
    age: user.age,
    orders: map(user.orders, "o", {
      id: o.id,
      total: o.price * o.qty
    })
  }
}

{
  users: map($.users, "u", enrichUser(u))
}
```

### Running Another Morph File

```javascript
// Use import to run another morph file
import "morphs/calculate-tax.morph" as taxCalc

{
  items: map($.items, "item", {
    name: item.name,
    price: item.price,
    tax: taxCalc.calculateTax(item.price),
    total: item.price + taxCalc.calculateTax(item.price)
  })
}
```

## Built-in Functions

### Array Operations
- `map(array, itemName, expr)` - Transform each element
- `filter(array, itemName, predicate)` - Filter elements
- `reduce(array, accName, itemName, init, expr)` - Reduce to single value
- `pluck(array, key)` - Extract property from each element
- `indexBy(array, key)` - Create object keyed by property

### Streams API (Java-like)
- `findFirst(array, itemName, predicate)` - Find first matching
- `anyMatch(array, itemName, predicate)` - Check if any match
- `allMatch(array, itemName, predicate)` - Check if all match
- `noneMatch(array, itemName, predicate)` - Check if none match
- `distinct(array)` - Remove duplicates
- `sorted(array, key?)` - Sort array
- `limit(array, n)` - Take first n elements
- `skip(array, n)` - Skip first n elements
- `flatMap(array, itemName, expr)` - Flatten mapped arrays
- `groupBy(array, key)` - Group by property
- `partition(array, itemName, predicate)` - Split by predicate
- `concat(array1, array2)` - Concatenate arrays
- `reverse(array)` - Reverse array

### Aggregation
- `count(array)` - Count elements
- `sum(array)` - Sum numbers
- `avg(array)` - Average
- `min(array)` - Minimum value
- `max(array)` - Maximum value

### Object Operations
- `merge(obj1, obj2, ...)` - Deep merge objects
- `get(obj, "path")` - Safe path access
- `set(obj, "path", value)` - Set nested value (immutable)
- `delete(obj, "path")` - Delete property (immutable)
- `exists(value)` - Check existence

### String Operations
- `split(str, sep)` - Split string
- `join(array, sep)` - Join array
- `upper(str)` - Uppercase
- `lower(str)` - Lowercase
- `trim(str)` - Trim whitespace
- `replace(str, pattern, replacement)` - Replace text
- `slice(str, start, end)` - Substring

### Type Conversions
- `toString(value)` - Convert to string
- `toNumber(value)` - Convert to number
- `toBool(value)` - Convert to boolean

### JSON Operations
- `jsonParse(str)` - Parse JSON string
- `jsonStringify(value)` - Stringify to JSON

### Date/Time
- `now()` - Current timestamp
- `formatDate(dateStr, format)` - Format date

## Performance Tips

1. **Use Streams API** - Optimized for large datasets
2. **Avoid nested maps** - Use flatMap instead
3. **Use pluck** - Faster than manual mapping
4. **Cache calculations** - Use variables for repeated expressions
5. **Use built-in functions** - They're optimized

## License

MIT License

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
