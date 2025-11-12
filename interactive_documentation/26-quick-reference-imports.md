# Quick Reference: Importing and Using Resource Morphs

Quick copy-paste examples for importing and using utility modules from your resources folder.

---

## Table of Contents
1. [NumberUtils](#numberutils)
2. [ArrayUtils](#arrayutils)
3. [DateUtils](#dateutils)
4. [FormatUtils](#formatutils)
5. [ObjectUtils](#objectutils)
6. [ValidationUtils](#validationutils)
7. [Java Setup](#java-setup)

---

## NumberUtils

### Import Statement
```javascript
import "morphs/NumberUtils.morph" as numUtils
```

### Most Common Functions

#### Round Numbers
```javascript
// Round to 2 decimal places (default)
numUtils.numberUtils.round(99.9567, 2)  // 99.96

// Round to integer
numUtils.numberUtils.round(99.9567, 0)  // 100

// Round up/down
numUtils.numberUtils.roundUp(99.1)      // 100
numUtils.numberUtils.roundDown(99.9)    // 99
```

#### Format Currency
```javascript
numUtils.numberUtils.formatCurrency(99.99, "$")    // "$99.99"
numUtils.numberUtils.formatCurrency(1234.56, "€")  // "€1234.56"
```

#### Format Percentage
```javascript
numUtils.numberUtils.formatPercent(85.5, 1)   // "85.5%"
numUtils.numberUtils.formatPercent(100, 0)    // "100%"
```

#### Compact Notation
```javascript
numUtils.numberUtils.formatCompact(1500)       // "1.5K"
numUtils.numberUtils.formatCompact(1500000)    // "1.5M"
numUtils.numberUtils.formatCompact(1500000000) // "1.5B"
```

#### Number Checks
```javascript
numUtils.numberUtils.isEven(4)         // true
numUtils.numberUtils.isOdd(5)          // true
numUtils.numberUtils.isPositive(10)    // true
numUtils.numberUtils.isNegative(-5)    // true
numUtils.numberUtils.isInteger(42)     // true
```

#### Calculations
```javascript
numUtils.numberUtils.percentage(25, 100)       // 25.0 (25 is 25% of 100)
numUtils.numberUtils.percentageOf(20, 100)     // 20.0 (20% of 100)
numUtils.numberUtils.clamp(150, 0, 100)        // 100 (clamp between 0-100)
```

### Complete NumberUtils Example
```javascript
import "morphs/NumberUtils.morph" as numUtils

{
  invoice: {
    subtotal: numUtils.numberUtils.round($.subtotal, 2),
    tax: numUtils.numberUtils.formatPercent($.taxRate * 100, 1),
    total: numUtils.numberUtils.formatCurrency($.total, "$"),
    discount: numUtils.numberUtils.formatPercent($.discount * 100, 0)
  }
}
```

**Input:**
```json
{
  "subtotal": 99.9567,
  "taxRate": 0.0825,
  "total": 108.23,
  "discount": 0.15
}
```

**Output:**
```json
{
  "invoice": {
    "subtotal": 99.96,
    "tax": "8.3%",
    "total": "$108.23",
    "discount": "15%"
  }
}
```

---

## ArrayUtils

### Import Statement
```javascript
import "morphs/ArrayUtils.morph" as arrUtils
```

### Common Functions

#### Array Operations
```javascript
// Chunk array into groups
arrUtils.arrayUtils.chunk([1,2,3,4,5], 2)  // [[1,2], [3,4], [5]]

// Flatten nested arrays
arrUtils.arrayUtils.flatten([[1,2], [3,4]])  // [1,2,3,4]

// Get unique values
arrUtils.arrayUtils.unique([1,2,2,3,3,3])  // [1,2,3]

// Zip arrays together
arrUtils.arrayUtils.zip([1,2], ["a","b"])  // [[1,"a"], [2,"b"]]
```

### Complete ArrayUtils Example
```javascript
import "morphs/ArrayUtils.morph" as arrUtils

{
  chunked: arrUtils.arrayUtils.chunk($.numbers, 3),
  flattened: arrUtils.arrayUtils.flatten($.nested),
  unique: arrUtils.arrayUtils.unique($.duplicates)
}
```

---

## DateUtils

### Import Statement
```javascript
import "morphs/DateUtils.morph" as dateUtils
```

### Common Functions

#### Format Dates
```javascript
// Format ISO date
dateUtils.dateUtils.formatDate("2025-01-15T10:30:00Z")  // "2025-01-15"

// Format with custom pattern
dateUtils.dateUtils.format("2025-01-15", "MM/DD/YYYY")  // "01/15/2025"

// Get date parts
dateUtils.dateUtils.getYear("2025-01-15")    // 2025
dateUtils.dateUtils.getMonth("2025-01-15")   // 1
dateUtils.dateUtils.getDay("2025-01-15")     // 15
```

### Complete DateUtils Example
```javascript
import "morphs/DateUtils.morph" as dateUtils

{
  event: {
    name: $.name,
    date: dateUtils.dateUtils.formatDate($.eventDate),
    year: dateUtils.dateUtils.getYear($.eventDate),
    formatted: dateUtils.dateUtils.format($.eventDate, "MMMM DD, YYYY")
  }
}
```

---

## FormatUtils

### Import Statement
```javascript
import "morphs/FormatUtils.morph" as formatUtils
```

### Common Functions

#### String Formatting
```javascript
// Capitalize
formatUtils.formatUtils.capitalize("hello")     // "Hello"

// Title case
formatUtils.formatUtils.titleCase("hello world") // "Hello World"

// Kebab case
formatUtils.formatUtils.kebabCase("Hello World") // "hello-world"

// Snake case
formatUtils.formatUtils.snakeCase("Hello World") // "hello_world"

// Camel case
formatUtils.formatUtils.camelCase("hello world") // "helloWorld"
```

### Complete FormatUtils Example
```javascript
import "morphs/FormatUtils.morph" as formatUtils

{
  user: {
    displayName: formatUtils.formatUtils.titleCase($.name),
    slug: formatUtils.formatUtils.kebabCase($.name),
    variable: formatUtils.formatUtils.camelCase($.name)
  }
}
```

---

## ObjectUtils

### Import Statement
```javascript
import "morphs/ObjectUtils.morph" as objUtils
```

### Common Functions

#### Object Operations
```javascript
// Deep merge objects
objUtils.objectUtils.deepMerge(obj1, obj2)

// Pick specific fields
objUtils.objectUtils.pick($.user, ["name", "email"])

// Omit specific fields
objUtils.objectUtils.omit($.user, ["password", "secret"])

// Check if object has key
objUtils.objectUtils.hasKey($.user, "email")  // true/false
```

### Complete ObjectUtils Example
```javascript
import "morphs/ObjectUtils.morph" as objUtils

{
  publicProfile: objUtils.objectUtils.omit($.user, ["password", "ssn"]),
  contactInfo: objUtils.objectUtils.pick($.user, ["name", "email", "phone"])
}
```

---

## ValidationUtils

### Import Statement
```javascript
import "morphs/ValidationUtils.morph" as validUtils
```

### Common Functions

#### Validation
```javascript
// Email validation
validUtils.validationUtils.isValidEmail("user@example.com")  // true

// URL validation
validUtils.validationUtils.isValidUrl("https://example.com")  // true

// Phone validation
validUtils.validationUtils.isValidPhone("+1-555-1234")  // true

// Required field check
validUtils.validationUtils.required($.field) ? $.field : error("Required")
```

### Complete ValidationUtils Example
```javascript
import "morphs/ValidationUtils.morph" as validUtils

{
  user: {
    email: validUtils.validationUtils.isValidEmail($.email) ? 
           $.email : 
           error("Invalid email"),
    phone: validUtils.validationUtils.isValidPhone($.phone) ? 
           $.phone : 
           null
  }
}
```

---

## Combining Multiple Utilities

### Complete Real-World Example

```javascript
import "morphs/NumberUtils.morph" as numUtils
import "morphs/DateUtils.morph" as dateUtils
import "morphs/FormatUtils.morph" as formatUtils
import "morphs/ValidationUtils.morph" as validUtils

{
  order: {
    // Formatting
    orderId: formatUtils.formatUtils.upper($.orderId),
    customerName: formatUtils.formatUtils.titleCase($.customerName),
    
    // Date handling
    orderDate: dateUtils.dateUtils.formatDate($.orderDate),
    orderYear: dateUtils.dateUtils.getYear($.orderDate),
    
    // Number formatting
    subtotal: numUtils.numberUtils.formatCurrency($.subtotal, "$"),
    tax: numUtils.numberUtils.formatPercent($.taxRate * 100, 1),
    total: numUtils.numberUtils.formatCurrency($.total, "$"),
    
    // Validation
    validEmail: validUtils.validationUtils.isValidEmail($.email) ?
                $.email :
                error("Invalid email address"),
    
    // Line items with utilities
    items: map($.items, "item", {
      name: formatUtils.formatUtils.titleCase(item.name),
      price: numUtils.numberUtils.formatCurrency(item.price, "$"),
      quantity: item.quantity,
      total: numUtils.numberUtils.formatCurrency(
        numUtils.numberUtils.round(item.price * item.quantity, 2),
        "$"
      )
    })
  }
}
```

**Input:**
```json
{
  "orderId": "ord-12345",
  "customerName": "john doe",
  "orderDate": "2025-01-15T10:30:00Z",
  "subtotal": 299.99,
  "taxRate": 0.0825,
  "total": 324.73,
  "email": "john@example.com",
  "items": [
    {"name": "premium widget", "price": 99.99, "quantity": 2},
    {"name": "standard gadget", "price": 50.00, "quantity": 2}
  ]
}
```

**Output:**
```json
{
  "order": {
    "orderId": "ORD-12345",
    "customerName": "John Doe",
    "orderDate": "2025-01-15",
    "orderYear": 2025,
    "subtotal": "$299.99",
    "tax": "8.3%",
    "total": "$324.73",
    "validEmail": "john@example.com",
    "items": [
      {
        "name": "Premium Widget",
        "price": "$99.99",
        "quantity": 2,
        "total": "$199.98"
      },
      {
        "name": "Standard Gadget",
        "price": "$50.00",
        "quantity": 2,
        "total": "$100.00"
      }
    ]
  }
}
```

---

## Java Setup

### Basic Setup with Resources

```java
import com.morphium.core.MorphiumEngine;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class MorphiumExample {
    public static void main(String[] args) throws Exception {
        // 1. Create engine
        MorphiumEngine engine = new MorphiumEngine();
        
        // 2. Set base path to resources folder
        Path resourcePath = Paths.get("src/main/resources");
        engine.getModuleResolver().setBasePath(resourcePath);
        
        // 3. Load your morph script
        String script = Files.readString(
            Paths.get("path/to/your/script.morph")
        );
        
        // 4. Prepare input data
        ObjectMapper mapper = new ObjectMapper();
        JsonNode input = mapper.readTree("{\"value\": 123.456}");
        
        // 5. Transform
        JsonNode result = engine.transformFromString(script, input);
        
        // 6. Use result
        System.out.println(result.toPrettyString());
    }
}
```

### Using Inline Script with Imports

```java
public class InlineExample {
    public static void main(String[] args) throws Exception {
        MorphiumEngine engine = new MorphiumEngine();
        
        // Set resource path
        Path resourcePath = Paths.get("src/main/resources");
        engine.getModuleResolver().setBasePath(resourcePath);
        
        // Inline script with imports
        String script = """
            import "morphs/NumberUtils.morph" as numUtils
            
            {
              rounded: numUtils.numberUtils.round($.value, 2),
              formatted: numUtils.numberUtils.formatCurrency($.value, "$")
            }
            """;
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode input = mapper.readTree("{\"value\": 123.456789}");
        JsonNode result = engine.transformFromString(script, input);
        
        System.out.println(result);
        // Output: {"rounded":123.46,"formatted":"$123.46"}
    }
}
```

### Reusable Engine (Best Practice)

```java
public class ReusableEngineExample {
    private static final MorphiumEngine engine;
    private static final ObjectMapper mapper = new ObjectMapper();
    
    static {
        // Initialize once
        engine = new MorphiumEngine();
        Path resourcePath = Paths.get("src/main/resources");
        engine.getModuleResolver().setBasePath(resourcePath);
    }
    
    public static JsonNode transform(String script, JsonNode input) 
            throws Exception {
        // Reuse the same engine for all transformations
        // Parser cache will kick in for repeated scripts
        return engine.transformFromString(script, input);
    }
    
    public static void main(String[] args) throws Exception {
        String script = """
            import "morphs/NumberUtils.morph" as numUtils
            {
              result: numUtils.numberUtils.round($.value, 2)
            }
            """;
        
        // Transform multiple inputs with same engine (cached!)
        JsonNode input1 = mapper.readTree("{\"value\": 123.456}");
        JsonNode input2 = mapper.readTree("{\"value\": 789.012}");
        
        JsonNode result1 = transform(script, input1);
        JsonNode result2 = transform(script, input2);
        
        System.out.println(result1);  // {"result":123.46}
        System.out.println(result2);  // {"result":789.01}
    }
}
```

---

## Common Patterns

### Pattern 1: Financial Processing
```javascript
import "morphs/NumberUtils.morph" as numUtils

function processLineItem(item) {
  let itemTotal = item.price * item.quantity
  let discounted = itemTotal * (1 - item.discount)
  let withTax = discounted * (1 + $.taxRate)
  
  {
    name: item.name,
    quantity: item.quantity,
    unitPrice: numUtils.numberUtils.formatCurrency(item.price, "$"),
    subtotal: numUtils.numberUtils.formatCurrency(itemTotal, "$"),
    discount: numUtils.numberUtils.formatPercent(item.discount * 100, 0),
    total: numUtils.numberUtils.formatCurrency(
      numUtils.numberUtils.round(withTax, 2), 
      "$"
    )
  }
}

{
  items: map($.items, "item", processLineItem(item))
}
```

### Pattern 2: Data Validation Pipeline
```javascript
import "morphs/ValidationUtils.morph" as validUtils
import "morphs/NumberUtils.morph" as numUtils

function validateAndTransform(user) {
  // Validate email
  !validUtils.validationUtils.isValidEmail(user.email) ?
    error("Invalid email: " + user.email) : null
  
  // Validate age
  !numUtils.numberUtils.isPositive(user.age) ?
    error("Age must be positive") : null
  
  // Transform
  {
    email: lower(user.email),
    age: user.age,
    isAdult: user.age >= 18
  }
}

{
  users: map($.users, "user", validateAndTransform(user))
}
```

### Pattern 3: Report Formatting
```javascript
import "morphs/NumberUtils.morph" as numUtils
import "morphs/DateUtils.morph" as dateUtils
import "morphs/FormatUtils.morph" as formatUtils

{
  report: {
    title: formatUtils.formatUtils.titleCase($.reportName),
    generatedDate: dateUtils.dateUtils.formatDate(now()),
    
    summary: {
      totalRevenue: numUtils.numberUtils.formatCurrency(
        sum(map($.transactions, "t", t.amount)),
        "$"
      ),
      transactionCount: count($.transactions),
      averageTransaction: numUtils.numberUtils.formatCurrency(
        avg(map($.transactions, "t", t.amount)),
        "$"
      )
    },
    
    transactions: map($.transactions, "t", {
      id: t.id,
      date: dateUtils.dateUtils.formatDate(t.date),
      amount: numUtils.numberUtils.formatCurrency(t.amount, "$"),
      status: formatUtils.formatUtils.capitalize(t.status)
    })
  }
}
```

---

## Troubleshooting Checklist

### ✅ Import Path Issues
- [ ] Path starts with `"morphs/"`
- [ ] File extension is `.morph`
- [ ] Base path is set correctly in Java
- [ ] File exists at `src/main/resources/morphs/`

### ✅ Function Access Issues
- [ ] Using correct namespace (e.g., `numUtils.numberUtils.round()`)
- [ ] Imported module with `as alias`
- [ ] Function name spelled correctly
- [ ] Correct number of parameters

### ✅ Java Setup Issues
- [ ] MorphiumEngine created
- [ ] Base path set with `engine.getModuleResolver().setBasePath()`
- [ ] Resource path points to correct folder
- [ ] Engine reused for multiple transformations (caching)

---

## Quick Copy-Paste Templates

### Template 1: Single Import
```javascript
import "morphs/NumberUtils.morph" as numUtils

{
  result: numUtils.numberUtils.round($.value, 2)
}
```

### Template 2: Multiple Imports
```javascript
import "morphs/NumberUtils.morph" as numUtils
import "morphs/DateUtils.morph" as dateUtils
import "morphs/FormatUtils.morph" as formatUtils

{
  // Your transformation here
}
```

### Template 3: Java with Imports
```java
MorphiumEngine engine = new MorphiumEngine();
engine.getModuleResolver().setBasePath(Paths.get("src/main/resources"));

String script = """
    import "morphs/NumberUtils.morph" as numUtils
    { result: numUtils.numberUtils.round($.value, 2) }
    """;

JsonNode result = engine.transformFromString(script, input);
```

---

## Summary

- **Import syntax**: `import "morphs/FileName.morph" as alias`
- **Function access**: `alias.exportedObject.function()`
- **Java setup**: `engine.getModuleResolver().setBasePath(resourcePath)`
- **Best practice**: Reuse engine for caching benefits
- **Combine utilities**: Import multiple modules for complex transformations

Happy transforming! 🚀
