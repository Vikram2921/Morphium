# Type System Functions - Quick Reference Guide

## Type Checking Functions

### isString(value)
Check if value is a string.

```javascript
isString("hello")     // true
isString(42)          // false
isString(null)        // false
```

### isNumber(value)
Check if value is a number.

```javascript
isNumber(42)          // true
isNumber(3.14)        // true
isNumber("42")        // false
```

### isBoolean(value)
Check if value is a boolean.

```javascript
isBoolean(true)       // true
isBoolean(false)      // true
isBoolean(1)          // false
```

### isArray(value)
Check if value is an array.

```javascript
isArray([1, 2, 3])    // true
isArray({})           // false
```

### isObject(value)
Check if value is an object.

```javascript
isObject({key: "val"}) // true
isObject([])           // false
```

### isNull(value)
Check if value is null.

```javascript
isNull(null)          // true
isNull(0)             // false
isNull("")            // false
```

### isEmpty(value)
Check if value is empty (null, "", [], {}).

```javascript
isEmpty(null)         // true
isEmpty("")           // true
isEmpty([])           // true
isEmpty({})           // true
isEmpty(0)            // false
isEmpty(false)        // false
```

### typeOf(value)
Get detailed type information.

```javascript
typeOf("hello")       // "string"
typeOf(42)            // "integer"
typeOf(3.14)          // "float"
typeOf(true)          // "boolean"
typeOf([])            // "array"
typeOf({})            // "object"
typeOf(null)          // "null"
```

## Type Conversion Functions

### toInt(value)
Convert value to integer.

```javascript
toInt("42")           // 42
toInt("42.7")         // 42
toInt(true)           // 1
toInt(false)          // 0
toInt("invalid")      // null
```

### toFloat(value)
Convert value to float/double.

```javascript
toFloat("3.14")       // 3.14
toFloat(42)           // 42.0
toFloat(true)         // 1.0
toFloat("invalid")    // null
```

### toStr(value)
Convert value to string.

```javascript
toStr(42)             // "42"
toStr(true)           // "true"
toStr(null)           // "null"
toStr({a: 1})         // "{\"a\":1}"
```

### toBoolNew(value)
Convert value to boolean.

```javascript
toBoolNew("true")     // true
toBoolNew("yes")      // true
toBoolNew("1")        // true
toBoolNew("false")    // false
toBoolNew("0")        // false
toBoolNew(42)         // true
toBoolNew(0)          // false
toBoolNew([1])        // true
toBoolNew([])         // false
```

## Utility Functions

### isFinite(value)
Check if number is finite (not NaN, not Infinity).

```javascript
isFinite(42.5)        // true
isFinite(Infinity)    // false
isFinite(NaN)         // false
```

### isNaN(value)
Check if value is NaN.

```javascript
isNaN(NaN)            // true
isNaN(42)             // false
```

### isInteger(value)
Check if value is an integer (whole number).

```javascript
isInteger(42)         // true
isInteger(42.0)       // true
isInteger(42.5)       // false
```

## Real-World Examples

### 1. Email Validation
```javascript
{
  validEmails: filter($.users, "u",
    isString(u.email) &&
    !isEmpty(u.email) &&
    contains(u.email, "@")
  )
}
```

### 2. Data Type Validation
```javascript
{
  validRecords: filter($.records, "r",
    isString(r.name) &&
    isNumber(r.age) &&
    isArray(r.tags) &&
    !isEmpty(r.name)
  )
}
```

### 3. Safe Type Conversion
```javascript
{
  age: isNumber($.age) ? $.age : toInt($.age),
  price: isNumber($.price) ? $.price : toFloat($.price),
  active: isBoolean($.active) ? $.active : toBoolNew($.active)
}
```

### 4. Conditional Processing
```javascript
{
  value: typeOf($.data) == "string" ? toNumber($.data) : $.data
}
```

### 5. Empty Value Handling
```javascript
{
  email: !isEmpty($.email) ? $.email : "no-email@example.com",
  phone: !isEmpty($.phone) ? $.phone : "N/A"
}
```

### 6. Type-Safe Array Operations
```javascript
{
  numbers: filter($.values, "v", isNumber(v) && isFinite(v)),
  sum: sum(numbers)
}
```

### 7. Complex Validation
```javascript
{
  validUser: 
    isString($.name) && !isEmpty($.name) &&
    isNumber($.age) && $.age >= 18 &&
    isString($.email) && contains($.email, "@") &&
    isArray($.roles) && !isEmpty($.roles)
}
```

### 8. Type Coercion
```javascript
{
  users: map($.rawUsers, "u", {
    name: toStr(u.name),
    age: toInt(u.age),
    active: toBoolNew(u.active),
    score: toFloat(u.score)
  })
}
```

### 9. Data Quality Report
```javascript
{
  totalRecords: count($.records),
  validRecords: count($.records, "r",
    isString(r.id) && !isEmpty(r.id) &&
    isNumber(r.value) && isFinite(r.value)
  ),
  invalidRecords: count($.records, "r",
    isEmpty(r.id) || !isNumber(r.value)
  )
}
```

### 10. Type-Based Routing
```javascript
{
  processedValue: 
    typeOf($.input) == "string" ? parseString($.input) :
    typeOf($.input) == "number" ? processNumber($.input) :
    typeOf($.input) == "array" ? processArray($.input) :
    $.input
}
```

## Best Practices

1. **Always validate before processing**
   ```javascript
   // Good
   { value: isNumber($.data) ? $.data * 2 : 0 }
   
   // Bad (might throw error)
   { value: $.data * 2 }
   ```

2. **Check for empty values**
   ```javascript
   // Good
   { email: !isEmpty($.email) ? $.email : "default@example.com" }
   
   // Bad (might process empty string)
   { email: $.email }
   ```

3. **Use isEmpty() instead of multiple checks**
   ```javascript
   // Good
   { hasData: !isEmpty($.data) }
   
   // Bad (verbose)
   { hasData: $.data != null && $.data != "" }
   ```

4. **Validate array contents**
   ```javascript
   // Good
   { numbers: filter($.values, "v", isNumber(v) && isFinite(v)) }
   
   // Bad (might include non-numbers)
   { numbers: $.values }
   ```

5. **Use typeOf() for detailed inspection**
   ```javascript
   // Good
   { type: typeOf($.data), isValid: typeOf($.data) == "integer" }
   
   // Bad (less precise)
   { isValid: isNumber($.data) }
   ```

## Performance Tips

- Type checking functions are very fast (< 1ms)
- Use them liberally for data quality
- Filter early to reduce processing
- Cache results of expensive type checks

## Common Patterns

### Pattern 1: Validation Filter
```javascript
filter($.items, "item", 
  isString(item.id) && 
  isNumber(item.value) &&
  !isEmpty(item.name)
)
```

### Pattern 2: Safe Conversion
```javascript
isNumber($.value) ? $.value : toInt($.value)
```

### Pattern 3: Type-Based Default
```javascript
isNull($.data) || isEmpty($.data) ? defaultValue : $.data
```

### Pattern 4: Multi-Type Validation
```javascript
(isString($.input) || isNumber($.input)) && !isEmpty($.input)
```

---

**For More Information:**
- See TypeFunctionsTest.java for unit tests
- See TypeFunctionsIntegrationTest.java for integration examples
- See PHASE1_WEEK1-2_REPORT.md for implementation details
