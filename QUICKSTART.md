# Morphium DSL - Quick Start Guide

## Installation & Setup

### Prerequisites
- Java 11 or higher
- Maven 3.6+

### Clone and Build
```bash
git clone <repository-url>
cd Morphium
mvn clean install
```

## 🚀 Launch the Playground (Recommended!)

The **fastest way** to learn Morphium is through the interactive playground:

```bash
mvn compile exec:java
```

Then open your browser to: **http://localhost:8080**

You'll see:
- ✨ **Transform Editor** (top) - Write your Morphium DSL code
- 📥 **Input JSON** (bottom-left) - Your data
- 📤 **Output JSON** (bottom-right) - Transformed result

**Features:**
- **Auto-transform** - Results update as you type (toggle on/off)
- **Syntax highlighting** - CodeMirror editor
- **7 built-in examples** - Click to load
- **Keyboard shortcuts** - Ctrl+Enter to transform

## Your First Transform

### 1. Basic Object Transformation

**Input JSON:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "age": 30
}
```

**Transform:**
```javascript
{
  fullName: $.firstName + " " + $.lastName,
  yearOfBirth: 2024 - $.age,
  isAdult: $.age >= 18
}
```

**Output:**
```json
{
  "fullName": "John Doe",
  "yearOfBirth": 1994,
  "isAdult": true
}
```

### 2. Array Mapping

**Input:**
```json
{
  "items": [
    {"name": "Apple", "price": 1.20, "qty": 5},
    {"name": "Banana", "price": 0.80, "qty": 10}
  ]
}
```

**Transform:**
```javascript
{
  items: map($.items, "item", {
    name: item.name,
    subtotal: item.price * item.qty,
    total: (item.price * item.qty) * 1.1
  })
}
```

### 3. Using Variables

**Transform:**
```javascript
let root = $
let config = root.settings
let discount = config.discount ?? 0

{
  originalPrice: $.price,
  discount: discount,
  finalPrice: $.price * (1 - discount)
}
```

### 4. Custom Functions

**Transform:**
```javascript
function calculateTax(amount) {
  return amount * 0.18
}

function finalPrice(base) {
  return base + calculateTax(base)
}

{
  basePrice: $.price,
  tax: calculateTax($.price),
  total: finalPrice($.price)
}
```

## Using in Java Code

### Basic Usage

```java
import com.morphium.core.MorphiumEngine;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Example {
    public static void main(String[] args) throws Exception {
        // Create engine
        MorphiumEngine engine = new MorphiumEngine();
        
        // Parse input JSON
        ObjectMapper mapper = new ObjectMapper();
        String inputJson = "{\"name\":\"John\",\"age\":30}";
        JsonNode input = mapper.readTree(inputJson);
        
        // Transform
        String transform = "{ greeting: 'Hello ' + $.name, adult: $.age >= 18 }";
        JsonNode result = engine.transformFromString(transform, input);
        
        // Print result
        System.out.println(result.toPrettyString());
    }
}
```

### With Custom Functions

```java
import com.morphium.function.MorphiumFunction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.DoubleNode;

// 1. Implement the interface
public class MultiplyFunction implements MorphiumFunction {
    @Override
    public String getName() {
        return "multiply";
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
        return DoubleNode.valueOf(a * b);
    }
}

// 2. Register and use
MorphiumEngine engine = new MorphiumEngine();
engine.registerFunction(new MultiplyFunction());

String transform = "{ result: multiply($.x, $.y) }";
JsonNode result = engine.transformFromString(transform, input);
```

## Key Concepts

### The `$` Variable
`$` always refers to the input JSON root:
```javascript
$.name           // Access property
$.user.email     // Nested access
$.items[0]       // Array access
$.data?.value    // Safe navigation
```

### Variable Scoping
```javascript
// Global - accessible everywhere
global TAX_RATE = 0.18

// Local - block scoped
let temp = $.value * 2
```

### Built-in Functions

**Array:**
- `map(array, "item", expression)` - Transform each element
- `filter(array, "item", predicate)` - Filter elements
- `reduce(array, "acc", "item", init, expr)` - Reduce to single value
- `pluck(array, "property")` - Extract property from each item
- `indexBy(array, "key")` - Create object indexed by key

**String:**
- `upper(str)`, `lower(str)`, `trim(str)`
- `split(str, separator)`, `join(array, separator)`
- `replace(str, pattern, replacement)`

**Object:**
- `merge(obj1, obj2, ...)` - Deep merge objects
- `get(obj, "path.to.value")` - Safe path access
- `set(obj, "path.to.value", newValue)` - Set value (returns new object)

**Utility:**
- `len(array | string)` - Length
- `exists(value)` - Check if not null/undefined
- `now()` - Current timestamp
- `type(value)` - Get type name

## Common Patterns

### Conditional Fields
```javascript
{
  name: $.name,
  status: $.active ? "Active" : "Inactive",
  discount: $.isPremium ? 0.2 : 0
}
```

### Default Values
```javascript
{
  name: $.name ?? "Unknown",
  email: $.contact?.email ?? "no-email@example.com"
}
```

### Filtering & Mapping
```javascript
{
  adults: filter($.users, "u", u.age >= 18),
  names: pluck($.users, "name"),
  total: reduce($.items, "sum", "item", 0, sum + item.price)
}
```

### Nested Transformations
```javascript
{
  userData: {
    profile: {
      name: $.firstName + " " + $.lastName,
      age: $.age
    },
    orders: map($.orders, "order", {
      id: order.id,
      total: order.amount * 1.1
    })
  }
}
```

## Playground Examples

Once the playground is running, try these examples:

1. **Basic Transform** - Simple field mapping
2. **Array Map** - Transform array items
3. **Filter** - Separate adults from minors
4. **Merge** - Combine objects with defaults
5. **Conditional** - Age categories
6. **Function** - Tax calculation
7. **$ Variables** - Working with $ assignments

## Tips & Tricks

1. **Use Auto-transform** - Leave it on to see results instantly
2. **Start Simple** - Begin with basic transforms, then add complexity
3. **Check Examples** - Learn from the 7 built-in examples
4. **Use Variables** - Make complex transforms readable
5. **Safe Navigation** - Use `?.` to avoid null errors
6. **Functions** - Extract reusable logic into functions

## Troubleshooting

### Playground won't start
```bash
# Make sure port 8080 is free
netstat -ano | findstr :8080

# Rebuild
mvn clean compile exec:java
```

### Transform errors
- Check syntax (missing commas, brackets)
- Verify input JSON is valid
- Use safe navigation `?.` for optional fields
- Check function names and parameter counts

### Custom function not working
- Verify `getName()` returns correct name
- Check param count matches usage
- Ensure function is registered before transform
- Return proper JsonNode type

## Next Steps

1. ✅ **Try the playground** - http://localhost:8080
2. 📚 **Read examples** - Click example buttons
3. 🔧 **Create custom functions** - Implement MorphiumFunction
4. 📖 **Read full docs** - Check README.md and IMPLEMENTATION_SUMMARY.md

## Need Help?

- Check error messages in playground output panel
- Look at execution time for performance insights
- Try simpler transforms first
- Use console.log in browser DevTools for debugging

---

**Happy Transforming! 🚀**

Start with the playground, experiment with examples, and gradually build more complex transformations!
