# Morphium DSL

A JavaScript-like JSON transformation DSL for Java with user-defined functions, modules, and global/local variables.

## Key Features

- ✅ **JavaScript-like syntax** - Familiar to JS developers
- ✅ **User-defined functions** - Create reusable logic
- ✅ **Global & local variables** - Proper scoping
- ✅ **Module system** - Import and export functions
- ✅ **Rich built-ins** - map, filter, reduce, merge, and 20+ functions
- ✅ **Jackson-based** - Uses `JsonNode` instead of Gson
- ✅ **Custom functions** - Implement `MorphiumFunction` interface

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

// Transform
String transform = "{ doubled: input.value * 2 }";
JsonNode output = engine.transformFromString(transform, input);
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
String transform = "{ sum: add(input.a, input.b) }";
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

- **Array**: `map`, `filter`, `reduce`, `pluck`, `indexBy`
- **Object**: `merge`, `get`, `set`
- **String**: `upper`, `lower`, `trim`, `split`, `join`, `replace`
- **Utility**: `len`, `exists`, `now`, `formatDate`
- **Conversion**: `toNumber`, `toString`, `toBool`
- **JSON**: `jsonParse`, `jsonStringify`

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
