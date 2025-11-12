# Morphium DSL - Getting Started

## What is Morphium?

Morphium is a JavaScript-like JSON transformation DSL designed for Java developers. It allows you to write concise, readable transformations that feel natural if you know JavaScript, while running entirely in your Java application.

## Quick Example

```javascript
// Transform input JSON
{
  fullName: input.person.first + " " + input.person.last,
  items: map(input.orders, "order", {
    id: order.id,
    total: order.qty * order.price
  })
}
```

## Installation

### Maven

```xml
<dependency>
    <groupId>com.morphium</groupId>
    <artifactId>morphium-dsl</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Basic Usage

```java
import com.morphium.core.MorphiumEngine;
import com.google.gson.JsonObject;

// Create engine
MorphiumEngine engine = new MorphiumEngine();

// Your input JSON
JsonObject input = ...; // your JSON data

// Transform from file
JsonElement output = engine.transform("transform.morph", input);

// Or transform from string
String transformCode = "{ name: upper(input.name) }";
JsonElement output = engine.transformFromString(transformCode, input);
```

## Language Features

### 1. Values and Literals

```javascript
// Numbers, strings, booleans, null
42
3.14
"hello"
true
false
null

// Arrays
[1, 2, 3]
[input.a, input.b, input.c]

// Objects
{ name: "Alice", age: 30 }
{ "quoted-key": value, [computed]: expr }
```

### 2. Operators

```javascript
// Arithmetic
a + b, a - b, a * b, a / b, a % b

// Comparison
a == b, a === b, a != b, a !== b
a < b, a <= b, a > b, a >= b

// Logical
a && b, a || b, !a

// Ternary
condition ? thenValue : elseValue

// Null coalescing
a ?? b  // returns b if a is null

// Safe navigation
a?.b?.c  // returns null if any part is missing
a?.[0]   // safe array access
```

### 3. Variables

```javascript
let discount = 0.1
let total = input.price * (1 - discount)
{ price: input.price, discounted: total }
```

### 4. Built-in Functions

#### Array Operations

```javascript
// map(array, "itemName", expression)
map(input.items, "it", { id: it.id, double: it.value * 2 })

// filter(array, "itemName", predicate)
filter(input.items, "it", it.price > 100)

// reduce(array, "accName", "itemName", initial, expression)
reduce(input.items, "sum", "it", 0, sum + it.value)

// pluck(array, key)
pluck(input.users, "email")  // ["a@x.com", "b@y.com"]

// indexBy(array, key)
indexBy(input.users, "id")  // { "u1": {...}, "u2": {...} }
```

#### Object Operations

```javascript
// merge(obj1, obj2, ...) - deep merge
merge({ a: 1 }, { b: 2 }, { c: 3 })

// get(object, "path.to.value")
get(input, "customer.address.city")

// set(object, "path.to.value", newValue) - returns new object
set(input, "customer.status", "active")
```

#### String Operations

```javascript
upper("hello")              // "HELLO"
lower("WORLD")              // "world"
trim("  text  ")            // "text"
split("a,b,c", ",")         // ["a", "b", "c"]
join(["a", "b"], ", ")      // "a, b"
replace("hello", "l", "L")  // "heLLo"
```

#### Utility Functions

```javascript
len([1, 2, 3])              // 3
len("hello")                // 5
exists(input.field)         // true/false
now()                       // current ISO timestamp
formatDate("2024-01-01", "dd/MM/yyyy")

toNumber("42")              // 42
toString(123)               // "123"
toBool(1)                   // true

jsonParse('{"a":1}')        // parsed JSON
jsonStringify({a:1})        // JSON string
```

## Real-World Examples

### Example 1: Flatten Nested Data

**Input:**
```json
{
  "person": { "first": "Alice", "last": "Smith" },
  "age": 28
}
```

**Transform:**
```javascript
{
  fullName: input.person.first + " " + input.person.last,
  years: input.age
}
```

**Output:**
```json
{ "fullName": "Alice Smith", "years": 28 }
```

### Example 2: Map and Calculate

**Input:**
```json
{
  "items": [
    { "id": "A", "qty": 2, "price": 10 },
    { "id": "B", "qty": 3, "price": 5 }
  ]
}
```

**Transform:**
```javascript
{
  items: map(input.items, "it", {
    id: it.id,
    total: it.qty * it.price
  })
}
```

**Output:**
```json
{
  "items": [
    { "id": "A", "total": 20 },
    { "id": "B", "total": 15 }
  ]
}
```

### Example 3: Filter and Index

**Transform:**
```javascript
let activeUsers = filter(input.users, "u", u.active)
let byId = indexBy(activeUsers, "id")
byId
```

### Example 4: Safe Navigation

**Transform:**
```javascript
{
  customerName: input.order?.customer?.name ?? "Unknown",
  hasItems: len(input.order?.items ?? []) > 0
}
```

## Registering Custom Java Functions

You can extend Morphium with your own Java functions:

```java
MorphiumEngine engine = new MorphiumEngine();

// Register a custom function
engine.registerFunction("math", "add", args -> {
    double a = args[0].getAsDouble();
    double b = args[1].getAsDouble();
    return new JsonPrimitive(a + b);
});

// Use in transform
String transform = "{ sum: math.add(input.a, input.b) }";
```

## Best Practices

1. **Keep transforms small** - Split complex logic into multiple files using `import`
2. **Use `let` for readability** - Name intermediate values
3. **Leverage built-ins** - Don't reinvent map/filter/reduce
4. **Safe navigation** - Use `?.` to handle missing data gracefully
5. **Default values** - Use `??` to provide fallbacks

## File Extension

Use `.morph` extension for transform files (e.g., `user-transform.morph`)

## Error Handling

Morphium provides helpful error messages with file location:

```
MorphiumException: Expected ')' after expression at transform.morph:5:12
```

## Running the Demo

```bash
mvn compile exec:java -Dexec.mainClass=com.morphium.Demo
```

## Running Tests

```bash
mvn test
```

## Building

```bash
mvn clean package
```

## License

MIT

## Support

For issues and questions, please visit the project repository.
