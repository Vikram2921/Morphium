# Morphium DSL

A JavaScript-like JSON transformation DSL for Java developers with **user-defined functions**, **modules**, and **global/local variables**.

## Design Goals

- **Familiar Syntax**: Looks and feels like JavaScript expressions/objects
- **Functional/Immutable**: Expressions return new JSON values by default
- **Minimal Keywords**: `let`, `function`, `global`, `import`, `export`
- **Rich Built-ins**: Common operations (map/filter/merge/format/date/string)
- **Extensible**: Easy registration of Java functions under namespaces
- **User Functions**: Define your own reusable functions
- **Module System**: Import and share code across transforms

## Quick Start

```java
import com.morphium.core.MorphiumEngine;
import com.google.gson.JsonObject;

MorphiumEngine engine = new MorphiumEngine();
JsonObject input = ...; // your input JSON
JsonElement output = engine.transform("path/to/transform.morph", input);
```

## Transform Example

```javascript
// Define reusable functions
function calculateTax(amount, rate) {
  return amount * rate
}

function formatPrice(value) {
  return "$" + toString(value)
}

// Use functions in transformations
{
  items: map(input.items, "it", {
    id: it.id,
    price: it.price,
    tax: calculateTax(it.price, 0.1),
    total: formatPrice(it.price + calculateTax(it.price, 0.1))
  })
}
```

## Core Features

### Values & Syntax
- Numbers, strings (`"..."`), booleans (`true`/`false`), `null`
- Objects: `{ key: expr, "quoted-key": expr, [computed]: expr }`
- Arrays: `[ expr, expr, ... ]`
- Comments: `//` single-line and `/* multi-line */`

### Operators
- Arithmetic: `+ - * / %`
- Comparisons: `== === != !== < <= > >=`
- Logical: `&& || !`
- Ternary: `cond ? a : b`
- Null coalescing: `a ?? b`
- Safe navigation: `a?.b`, `a?.[0]`

### Variables
```javascript
let x = expr  // block-local, immutable binding
```

### Built-in Functions

**Array operations:**
- `map(array, "itemName", expr)` - Transform each element
- `filter(array, "itemName", predicateExpr)` - Filter elements
- `reduce(array, "accName", "itemName", init, expr)` - Reduce array
- `pluck(array, key)` - Extract property from each item
- `indexBy(array, key)` - Create lookup map by key

**Object operations:**
- `merge(obj1, obj2, ...)` - Deep merge objects
- `get(obj, "path.to.node")` - Safe path lookup
- `set(obj, "path.to.node", value)` - Immutable update

**Utilities:**
- `exists(x)` - Check if value exists and is not null
- `len(x)` - Length of array or string
- `now()` - Current ISO timestamp
- `formatDate(dateStr, fmt)` - Format/parse dates

**String operations:**
- `split(str, sep)`, `join(array, sep)`
- `upper(str)`, `lower(str)`, `trim(str)`
- `replace(str, pattern, replacement)`

**Type conversions:**
- `toNumber(x)`, `toString(x)`, `toBool(x)`
- `jsonParse(str)`, `jsonStringify(value)`

### Modules

```javascript
// utils.morph
export helpers = {
  formatPrice: (v) => "$" + toNumber(v).toFixed(2)
}

// main.morph
import "utils.morph" as utils
{
  price: utils.helpers.formatPrice(input.amount)
}
```

## Documentation

- **[GETTING_STARTED.md](GETTING_STARTED.md)** - Complete user guide
- **[USER_FUNCTIONS_GUIDE.md](USER_FUNCTIONS_GUIDE.md)** - Functions, globals & modules
- **[QUICK_REFERENCE.md](QUICK_REFERENCE.md)** - Quick reference card
- **[PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)** - Technical implementation details

## New Features

### User-Defined Functions
Create reusable functions with parameters and local variables:
```javascript
function formatUserName(first, last) {
  let capitalized = upper(first) + " " + upper(last)
  return trim(capitalized)
}
```

### Global & Local Variables
- **Global**: Shared across entire transform and all functions
- **Local**: Scoped to function or block

### Module System
Split transforms into reusable modules:
```javascript
// validators.morph
export isEmail = function(s) { ... }

// main.morph
import "validators.morph" as v
filter(input.users, "u", v.isEmail(u.email))
```

See **[USER_FUNCTIONS_GUIDE.md](USER_FUNCTIONS_GUIDE.md)** for complete examples.

## License

MIT
