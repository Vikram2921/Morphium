# Morphium DSL

A JavaScript-like JSON transformation DSL for Java developers.

## Design Goals

- **Familiar Syntax**: Looks and feels like JavaScript expressions/objects
- **Functional/Immutable**: Expressions return new JSON values by default
- **Minimal Keywords**: `let`, `if`, `for`, `import`, `export`
- **Rich Built-ins**: Common operations (map/filter/merge/format/date/string)
- **Extensible**: Easy registration of Java functions under namespaces

## Quick Start

```java
import com.morphium.core.MorphiumEngine;
import com.google.gson.JsonObject;

MorphiumEngine engine = new MorphiumEngine();
JsonObject input = ...; // your input JSON
JsonObject output = engine.transform("path/to/transform.morph", input);
```

## Transform Example

```javascript
// flatten-rename.morph
{
  fullName: input.person.first + " " + input.person.last,
  years: input.age,
  items: map(input.items, "it", {
    id: it.id,
    total: it.qty * it.price
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

## Examples

See the `examples/` directory for complete transform examples.

## License

MIT
