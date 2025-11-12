# Morphium DSL - Quick Reference Card

## Syntax at a Glance

### Values
```javascript
42                  // number
"hello"             // string
true, false         // boolean
null                // null
[1, 2, 3]          // array
{a: 1, b: 2}       // object
```

### Operators
```javascript
+  -  *  /  %      // arithmetic
==  ===  !=  !==   // equality
<  <=  >  >=       // comparison
&&  ||  !          // logical
condition ? a : b   // ternary
a ?? b             // null coalesce
a?.b               // safe navigation
```

### Variables
```javascript
let x = 10
let name = input.person.name
```

## Built-in Functions

### Arrays
| Function | Example | Description |
|----------|---------|-------------|
| `map` | `map(arr, "x", x * 2)` | Transform each element |
| `filter` | `filter(arr, "x", x > 5)` | Filter by predicate |
| `reduce` | `reduce(arr, "a", "x", 0, a + x)` | Reduce to single value |
| `pluck` | `pluck(users, "email")` | Extract property |
| `indexBy` | `indexBy(users, "id")` | Create lookup map |

### Objects
| Function | Example | Description |
|----------|---------|-------------|
| `merge` | `merge({a:1}, {b:2})` | Deep merge |
| `get` | `get(obj, "a.b.c")` | Safe path lookup |
| `set` | `set(obj, "a.b", val)` | Immutable update |

### Strings
| Function | Example | Description |
|----------|---------|-------------|
| `upper` | `upper("hi")` → `"HI"` | Uppercase |
| `lower` | `lower("HI")` → `"hi"` | Lowercase |
| `trim` | `trim(" x ")` → `"x"` | Trim whitespace |
| `split` | `split("a,b", ",")` → `["a","b"]` | Split string |
| `join` | `join(["a","b"], ",")` → `"a,b"` | Join array |
| `replace` | `replace("hi", "i", "o")` → `"ho"` | Replace text |

### Utilities
| Function | Example | Description |
|----------|---------|-------------|
| `exists` | `exists(input.x)` | Check if defined |
| `len` | `len([1,2,3])` → `3` | Array/string length |
| `now` | `now()` | Current ISO timestamp |
| `formatDate` | `formatDate("2024-01-01", "dd/MM")` | Format date |
| `toNumber` | `toNumber("42")` → `42` | Convert to number |
| `toString` | `toString(42)` → `"42"` | Convert to string |
| `toBool` | `toBool(1)` → `true` | Convert to boolean |
| `jsonParse` | `jsonParse('{"a":1}')` | Parse JSON string |
| `jsonStringify` | `jsonStringify({a:1})` | Stringify to JSON |

## Common Patterns

### Flatten Object
```javascript
{
  fullName: input.person.first + " " + input.person.last,
  age: input.person.age
}
```

### Transform Array
```javascript
map(input.items, "item", {
  id: item.id,
  total: item.qty * item.price
})
```

### Filter Array
```javascript
filter(input.users, "u", u.active && u.age >= 18)
```

### Sum Array Values
```javascript
reduce(input.numbers, "sum", "n", 0, sum + n)
```

### Safe Access with Default
```javascript
input.user?.email ?? "no-email@example.com"
```

### Conditional Field
```javascript
{
  status: input.age >= 18 ? "adult" : "minor"
}
```

### Extract Email List
```javascript
pluck(input.users, "email")
```

### Group by ID
```javascript
indexBy(input.items, "id")
```

### Merge Defaults
```javascript
merge({ status: "new", active: true }, input)
```

### Clean & Transform
```javascript
map(input.records, "r", {
  id: r.id,
  email: lower(trim(r.email)),
  timestamp: now()
})
```

## Java Integration

### Basic Usage
```java
MorphiumEngine engine = new MorphiumEngine();
JsonElement output = engine.transformFromString(code, input);
```

### Register Custom Function
```java
engine.registerFunction("math", "add", args -> {
    double a = args[0].getAsDouble();
    double b = args[1].getAsDouble();
    return new JsonPrimitive(a + b);
});
```

### Transform from File
```java
JsonElement output = engine.transform("transform.morph", input);
```

## Tips

✓ Use `?.` for safe navigation
✓ Use `??` for default values  
✓ Use `let` to name intermediate values
✓ Prefer built-ins over custom logic
✓ Keep transforms small and focused
✓ Use `.morph` file extension

## Error Messages

Morphium provides helpful errors with location:
```
MorphiumException: Expected ')' at transform.morph:5:12
```

---

**Quick Start:** `mvn compile exec:java -Dexec.mainClass=com.morphium.Demo`

**Run Tests:** `mvn test`

**Documentation:** See `GETTING_STARTED.md`
