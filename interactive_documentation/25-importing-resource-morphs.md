# Importing Resource Morph Files

Learn how to import and use pre-built morph utility modules from your resources folder.

---

## Overview

Morphium allows you to:
- **Import utility modules** from the resources folder
- **Use pre-built functions** like NumberUtils, ArrayUtils, etc.
- **Create reusable libraries** for your organization
- **Share code** across multiple transformations

---

## Resource Morph Files Location

Built-in utility morph files are located at:
```
src/main/resources/morphs/
├── NumberUtils.morph
├── ArrayUtils.morph
├── DateUtils.morph
├── FormatUtils.morph
├── ObjectUtils.morph
└── ValidationUtils.morph
```

---

## Import Syntax

### Basic Import from Resources

```javascript
import "morphs/NumberUtils.morph" as numUtils
```

### Using Imported Functions

```javascript
import "morphs/NumberUtils.morph" as numUtils

{
  rounded: numUtils.numberUtils.round($.price, 2),
  formatted: numUtils.numberUtils.formatCurrency($.amount, "$")
}
```

---

## Example 1: Using NumberUtils.roundNumber()

### NumberUtils Module Structure

The `NumberUtils.morph` exports a `numberUtils` object with various number functions:

```javascript
// From NumberUtils.morph
function roundNumber(num, decimals) {
  let multiplier = pow(10, decimals ?? 2)
  return round(num * multiplier) / multiplier
}

export numberUtils = {
  round: roundNumber,
  formatCurrency: formatCurrency,
  formatPercent: formatPercent,
  // ... more functions
}
```

### Using roundNumber in Your Script

**Input:**
```json
{
  "price": 99.9567,
  "tax": 8.33333,
  "total": 108.28903
}
```

**Morph Script:**
```javascript
import "morphs/NumberUtils.morph" as numUtils

{
  originalPrice: $.price,
  roundedPrice: numUtils.numberUtils.round($.price, 2),
  roundedTax: numUtils.numberUtils.round($.tax, 2),
  roundedTotal: numUtils.numberUtils.round($.total, 2),
  
  // Format as currency
  displayPrice: numUtils.numberUtils.formatCurrency($.price, "$"),
  displayTotal: numUtils.numberUtils.formatCurrency($.total, "$")
}
```

**Output:**
```json
{
  "originalPrice": 99.9567,
  "roundedPrice": 99.96,
  "roundedTax": 8.33,
  "roundedTotal": 108.29,
  "displayPrice": "$99.96",
  "displayTotal": "$108.29"
}
```

---

## Example 2: Importing from Custom Resource Morph

Let's say you have a custom `NumberUtils.morph` in your resources folder:

### Directory Structure
```
src/main/resources/
└── morphs/
    └── NumberUtils.morph
```

### Your Custom Morph File (my-script.morph)

**File: my-script.morph**
```javascript
import "morphs/NumberUtils.morph" as numUtils

{
  items: map($.items, "item", {
    name: item.name,
    originalPrice: item.price,
    roundedPrice: numUtils.numberUtils.round(item.price, 2),
    discountPercent: numUtils.numberUtils.formatPercent(item.discount * 100, 1),
    finalPrice: numUtils.numberUtils.round(item.price * (1 - item.discount), 2)
  })
}
```

**Input:**
```json
{
  "items": [
    {"name": "Laptop", "price": 999.9567, "discount": 0.15},
    {"name": "Mouse", "price": 29.9933, "discount": 0.10},
    {"name": "Keyboard", "price": 79.456, "discount": 0.20}
  ]
}
```

**Output:**
```json
{
  "items": [
    {
      "name": "Laptop",
      "originalPrice": 999.9567,
      "roundedPrice": 999.96,
      "discountPercent": "15.0%",
      "finalPrice": 849.96
    },
    {
      "name": "Mouse",
      "originalPrice": 29.9933,
      "roundedPrice": 29.99,
      "discountPercent": "10.0%",
      "finalPrice": 26.99
    },
    {
      "name": "Keyboard",
      "originalPrice": 79.456,
      "roundedPrice": 79.46,
      "discountPercent": "20.0%",
      "finalPrice": 63.56
    }
  ]
}
```

---

## Example 3: Java API Usage

### Setting Up Module Resolver with Resources

```java
import com.morphium.core.MorphiumEngine;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Paths;

public class ResourceMorphExample {
    public static void main(String[] args) throws Exception {
        // Create engine
        MorphiumEngine engine = new MorphiumEngine();
        
        // Set base path to resources folder
        Path resourcePath = Paths.get("src/main/resources");
        engine.getModuleResolver().setBasePath(resourcePath);
        
        // Your morph script
        String script = """
            import "morphs/NumberUtils.morph" as numUtils
            
            {
              rounded: numUtils.numberUtils.round($.value, 2),
              formatted: numUtils.numberUtils.formatCurrency($.value, "$")
            }
            """;
        
        // Transform data
        ObjectMapper mapper = new ObjectMapper();
        JsonNode input = mapper.readTree("{\"value\": 123.456789}");
        JsonNode result = engine.transformFromString(script, input);
        
        System.out.println(result);
        // Output: {"rounded":123.46,"formatted":"$123.46"}
    }
}
```

---

## Example 4: Using Multiple Utility Modules

**Morph Script:**
```javascript
import "morphs/NumberUtils.morph" as numUtils
import "morphs/DateUtils.morph" as dateUtils
import "morphs/FormatUtils.morph" as formatUtils

{
  order: {
    id: $.orderId,
    total: numUtils.numberUtils.formatCurrency($.total, "$"),
    tax: numUtils.numberUtils.formatPercent($.taxRate * 100, 1),
    date: dateUtils.formatDate($.orderDate),
    status: formatUtils.capitalize($.status)
  }
}
```

**Input:**
```json
{
  "orderId": "ORD-12345",
  "total": 299.99,
  "taxRate": 0.0825,
  "orderDate": "2025-01-15T10:30:00Z",
  "status": "pending"
}
```

**Output:**
```json
{
  "order": {
    "id": "ORD-12345",
    "total": "$299.99",
    "tax": "8.3%",
    "date": "2025-01-15",
    "status": "Pending"
  }
}
```

---

## Available NumberUtils Functions

The `NumberUtils.morph` module provides these functions:

### Rounding
- `round(num, decimals)` - Round to decimal places
- `roundUp(num)` - Round up (ceiling)
- `roundDown(num)` - Round down (floor)

### Math Operations
- `abs(num)` - Absolute value
- `ceil(num)` - Ceiling function
- `floor(num)` - Floor function
- `pow(base, exp)` - Power function
- `sqrt(num)` - Square root
- `max(a, b)` - Maximum of two numbers
- `min(a, b)` - Minimum of two numbers
- `clamp(num, min, max)` - Clamp value between min and max

### Number Checks
- `isEven(num)` - Check if even
- `isOdd(num)` - Check if odd
- `isPositive(num)` - Check if positive
- `isNegative(num)` - Check if negative
- `isZero(num)` - Check if zero
- `isInteger(num)` - Check if integer
- `isFloat(num)` - Check if float

### Formatting
- `formatCurrency(amount, symbol)` - Format as currency
- `formatPercent(value, decimals)` - Format as percentage
- `formatNumber(num, decimals)` - Format number
- `formatCompact(num)` - Format with K/M/B suffixes
- `toOrdinal(num)` - Convert to ordinal (1st, 2nd, 3rd)

### Calculations
- `percentage(value, total)` - Calculate percentage
- `percentageOf(percent, total)` - Get percentage of total
- `average(numbers)` - Calculate average
- `median(numbers)` - Calculate median
- `inRange(num, start, end)` - Check if in range

---

## Module Resolution Order

When you import a morph file, the engine resolves it in this order:

1. **Relative to current script** - `./NumberUtils.morph`
2. **Relative to base path** - `morphs/NumberUtils.morph`
3. **Absolute path** - Full file system path

### Examples:

```javascript
// From resources folder
import "morphs/NumberUtils.morph" as numUtils

// From same directory as current script
import "./MyUtils.morph" as myUtils

// From parent directory
import "../shared/Utils.morph" as sharedUtils
```

---

## Best Practices

### 1. Use Consistent Naming
```javascript
// Good - clear and descriptive
import "morphs/NumberUtils.morph" as numUtils
import "morphs/DateUtils.morph" as dateUtils

// Avoid - too generic
import "morphs/NumberUtils.morph" as nu
```

### 2. Import Only What You Need
```javascript
// If the module supports named exports
import { roundNumber, formatCurrency } from "morphs/NumberUtils.morph"

// Use the entire namespace
import "morphs/NumberUtils.morph" as numUtils
```

### 3. Organize Your Utilities
```
src/main/resources/
└── morphs/
    ├── number/
    │   ├── Rounding.morph
    │   └── Formatting.morph
    ├── date/
    │   └── Formatting.morph
    └── validation/
        └── Rules.morph
```

### 4. Cache Module Resolver
```java
// Create once, reuse many times
MorphiumEngine engine = new MorphiumEngine();
Path resourcePath = Paths.get("src/main/resources");
engine.getModuleResolver().setBasePath(resourcePath);

// Reuse engine for multiple transformations (cached)
JsonNode result1 = engine.transformFromString(script1, input1);
JsonNode result2 = engine.transformFromString(script2, input2);
```

---

## Common Patterns

### Pattern 1: Financial Calculations
```javascript
import "morphs/NumberUtils.morph" as numUtils

function calculateOrderTotal(items) {
  let subtotal = sum(map(items, "item", item.price * item.quantity))
  let tax = subtotal * 0.0825
  let total = subtotal + tax
  
  {
    subtotal: numUtils.numberUtils.round(subtotal, 2),
    tax: numUtils.numberUtils.round(tax, 2),
    total: numUtils.numberUtils.round(total, 2),
    formatted: numUtils.numberUtils.formatCurrency(total, "$")
  }
}

{
  order: calculateOrderTotal($.items)
}
```

### Pattern 2: Data Validation with Utilities
```javascript
import "morphs/NumberUtils.morph" as numUtils
import "morphs/ValidationUtils.morph" as validUtils

{
  validatedPrice: if (numUtils.numberUtils.isPositive($.price)) {
    numUtils.numberUtils.round($.price, 2)
  } else {
    error("Price must be positive")
  },
  
  validatedQuantity: if (numUtils.numberUtils.isInteger($.quantity) && 
                         numUtils.numberUtils.isPositive($.quantity)) {
    $.quantity
  } else {
    error("Quantity must be a positive integer")
  }
}
```

### Pattern 3: Reusable Transformation Pipeline
```javascript
import "morphs/NumberUtils.morph" as numUtils

function normalizeProduct(product) {
  {
    id: product.id,
    name: upper(product.name),
    price: numUtils.numberUtils.round(product.price, 2),
    discount: numUtils.numberUtils.formatPercent(product.discount * 100, 0),
    inStock: product.stock > 0
  }
}

{
  products: map($.products, "p", normalizeProduct(p))
}
```

---

## Troubleshooting

### Error: "Module not found"

**Problem:**
```javascript
import "NumberUtils.morph" as numUtils  // Wrong!
```

**Solution:**
```javascript
import "morphs/NumberUtils.morph" as numUtils  // Correct
```

Make sure to include the full path relative to the resources folder.

### Error: "Cannot read property of undefined"

**Problem:**
```javascript
import "morphs/NumberUtils.morph" as numUtils
numUtils.round($.price, 2)  // Wrong! Missing numberUtils
```

**Solution:**
```javascript
import "morphs/NumberUtils.morph" as numUtils
numUtils.numberUtils.round($.price, 2)  // Correct
```

The module exports a `numberUtils` object, so you need to access it.

### Java Base Path Issue

**Problem:**
```java
// Module resolver uses current directory by default
// May not find resources folder
```

**Solution:**
```java
MorphiumEngine engine = new MorphiumEngine();
Path resourcePath = Paths.get("src/main/resources");
engine.getModuleResolver().setBasePath(resourcePath);
```

---

## Complete Working Example

**File: src/main/resources/morphs/MyUtils.morph**
```javascript
function discount(price, percent) {
  price * (1 - percent)
}

function addTax(amount, taxRate) {
  amount * (1 + taxRate)
}

export utils = {
  discount: discount,
  addTax: addTax
}
```

**File: myTransform.morph**
```javascript
import "morphs/NumberUtils.morph" as numUtils
import "morphs/MyUtils.morph" as myUtils

{
  items: map($.items, "item", {
    name: item.name,
    originalPrice: numUtils.numberUtils.formatCurrency(item.price, "$"),
    discountedPrice: numUtils.numberUtils.round(
      myUtils.utils.discount(item.price, 0.15), 2
    ),
    withTax: numUtils.numberUtils.round(
      myUtils.utils.addTax(
        myUtils.utils.discount(item.price, 0.15),
        0.0825
      ), 2
    )
  })
}
```

**Java Usage:**
```java
public class CompleteExample {
    public static void main(String[] args) throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        // Set resource path
        Path resourcePath = Paths.get("src/main/resources");
        engine.getModuleResolver().setBasePath(resourcePath);
        
        // Load your script
        String script = Files.readString(Paths.get("myTransform.morph"));
        
        // Transform data
        ObjectMapper mapper = new ObjectMapper();
        String json = """
            {
              "items": [
                {"name": "Widget", "price": 100.00},
                {"name": "Gadget", "price": 250.00}
              ]
            }
            """;
        
        JsonNode input = mapper.readTree(json);
        JsonNode result = engine.transformFromString(script, input);
        
        System.out.println(result.toPrettyString());
    }
}
```

---

## Summary

- **Import resource morphs** using `import "morphs/FileName.morph" as alias`
- **Access functions** via the exported namespace (e.g., `numUtils.numberUtils.round()`)
- **Set base path** in Java using `engine.getModuleResolver().setBasePath()`
- **Reuse modules** across multiple transformations for consistency
- **Cache engine** for better performance with repeated transformations

Resource morph files enable code reuse and maintainable transformation logic!
