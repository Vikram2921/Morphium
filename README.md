# Morphium DSL

A JavaScript-like JSON transformation DSL for Java with **Java Streams API equivalent** operations, user-defined functions, modules, and powerful data processing capabilities.

## 🎯 Quick Links

- 🚀 **[Quick Start Guide](QUICKSTART.md)** - Get started in 5 minutes!
- 📖 **[Complete Feature Guide](FEATURES.md)** - All features and examples
- 🎮 **[Playground Guide](PLAYGROUND.md)** - Interactive web interface
- 📚 **[Implementation Details](IMPLEMENTATION_SUMMARY.md)** - Architecture & design

## 🚀 Try the Interactive Playground!

```bash
mvn compile exec:java
```

Open **http://localhost:8080** in your browser for a real-time playground!

### Playground Features:
- ✨ **Auto-transform** - Real-time transformation as you type
- 📝 **CodeMirror Editor** - Professional code editor with syntax highlighting
- 🎨 **Beautiful UI** - Modern gradient design with dark output panel
- 📚 **Quick Examples** - 7 pre-built examples to get started
- ⚡ **Fast Feedback** - Execution time display and error highlighting
- 🔄 **Format JSON** - One-click JSON formatting

👉 See [PLAYGROUND.md](PLAYGROUND.md) for full playground documentation.

## Key Features

- ✅ **JavaScript-like syntax** - Familiar to JS developers
- ✅ **`$` input variable** - Use `$` to access input (like `$.name`)
- ✅ **Java Streams API** - Complete streams equivalent (map, filter, flatMap, groupBy, partition, etc.)
- ✅ **User-defined functions** - Create reusable logic
- ✅ **Global & local variables** - Proper scoping with `let` and `global`
- ✅ **Variable assignment** - Assign `$` to variables: `let root = $`
- ✅ **Module system** - Import and export functions
- ✅ **Run other morph files** - `runMorph("file.morph", data)`
- ✅ **Rich built-ins** - 40+ functions (map, filter, reduce, flatMap, groupBy, sum, avg, etc.)
- ✅ **Jackson-based** - Uses `JsonNode` instead of Gson
- ✅ **Custom functions** - Implement `MorphiumFunction` interface
- ✅ **Real-time playground** - Interactive web UI with auto-transform

## Quick Start

```java
import com.morphium.core.MorphiumEngine;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.morphium.util.JsonUtil;

MorphiumEngine engine = new MorphiumEngine();

// Create input
ObjectNode input = JsonUtil.createObject();
input.put("value", 42);

// Transform using $ for input
String transform = "{ doubled: $.value * 2 }";
JsonNode output = engine.transformFromString(transform, input);
```

## The `$` Variable

Morphium uses `$` to represent the input JSON (similar to JSONata):

```javascript
// Direct access
{ name: $.person.name, age: $.person.age }

// Assign to variables
let root = $
let person = root.person
{ fullName: person.first + " " + person.last }

// Use in functions
map($.items, "item", item.price * 2)
```

## Custom Functions

Implement the `MorphiumFunction` interface:

```java
import com.morphium.function.MorphiumFunction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.DoubleNode;

public class AddFunction implements MorphiumFunction {
    @Override
    public String getName() {
        return "add";
    }
    
    @Override
    public int getMinParams() {
        return 2;
    }
    
    @Override
    public int getMaxParams() {
        return 2;
    }
    
    @Override
    public JsonNode call(JsonNode root, JsonNode[] params) {
        double a = params[0].asDouble();
        double b = params[1].asDouble();
        return DoubleNode.valueOf(a + b);
    }
}

// Register and use
engine.registerFunction(new AddFunction());
String transform = "{ sum: add($.a, $.b) }";
```

## Transform Examples

### User-Defined Functions
```javascript
function calculateTax(amount, rate) {
  return amount * rate
}

{
  price: input.price,
  tax: calculateTax(input.price, 0.1),
  total: input.price + calculateTax(input.price, 0.1)
}
```

### Global Variables
```javascript
global TAX_RATE = 0.08
global DISCOUNT = 0.15

function applyTax(amount) {
  return amount * (1 + TAX_RATE)
}

{ finalPrice: applyTax(input.price * (1 - DISCOUNT)) }
```

### Array Operations
```javascript
{
  items: map(input.items, "it", {
    id: it.id,
    total: it.qty * it.price
  }),
  filtered: filter(input.items, "it", it.price > 100)
}
```

## Built-in Functions

### Java Streams API Equivalent
- **Filtering**: `filter`, `anyMatch`, `allMatch`, `noneMatch`, `findFirst`
- **Mapping**: `map`, `flatMap`, `pluck`
- **Aggregation**: `sum`, `avg`, `min`, `max`, `count`, `reduce`
- **Sorting**: `sorted`, `reverse`, `distinct`
- **Slicing**: `limit`, `skip`, `slice`
- **Grouping**: `groupBy`, `partition`, `indexBy`
- **Side Effects**: `peek`, `forEach`

### Array & Object
- **Array**: `concat`, `join`, `split`, `reverse`
- **Object**: `merge`, `get`, `set`, `keys`, `values`, `entries`

### String Operations
- `upper`, `lower`, `trim`, `replace`, `split`, `join`

### Utility Functions
- `len`, `exists`, `now`, `formatDate`
- `toNumber`, `toString`, `toBool`
- `jsonParse`, `jsonStringify`

### Module Execution
- `runMorph(file, input)` - Execute another morph file

👉 See **[FEATURES.md](FEATURES.md)** for complete documentation and examples.

## Building

```bash
mvn clean install
```

## Running Demo

```bash
mvn exec:java -Dexec.mainClass=com.morphium.Demo
```

## Testing

```bash
mvn test
```

## Migration from Gson

This project uses Jackson instead of Gson:
- `JsonElement` → `JsonNode`
- `JsonObject` → `ObjectNode`
- `JsonArray` → `ArrayNode`
- `JsonPrimitive` → `TextNode`, `IntNode`, `DoubleNode`, `BooleanNode`
- `JsonNull.INSTANCE` → `NullNode.getInstance()`

Use `JsonUtil` helper class for creating nodes:
```java
ObjectNode obj = JsonUtil.createObject();
ArrayNode arr = JsonUtil.createArray();
JsonNode primitive = JsonUtil.createPrimitive(value);
```

## License

MIT
