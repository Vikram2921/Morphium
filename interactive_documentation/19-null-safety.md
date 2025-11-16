# Null Safety Functions

Morphium provides comprehensive null safety functions to handle missing, null, or undefined values gracefully. These functions help prevent errors and provide fallback values when dealing with incomplete data.

## Table of Contents

1. [coalesce()](#coalesce) - First non-null value
2. [ifNull()](#ifnull) - Value with fallback
3. [nullIf()](#nullif) - Conditional null
4. [safeGet()](#safeget) - Safe property access
5. [tryGet()](#tryget) - Safe access with default
6. [removeNulls()](#removenulls) - Remove null properties
7. [replaceNulls()](#replacenulls) - Replace nulls with default
8. [isNullOrEmpty()](#isnullorempty) - Check for null/empty
9. [firstValid()](#firstvalid) - First valid value

---

## coalesce()

Returns the first non-null value from a list of arguments.

### Syntax
```javascript
coalesce(val1, val2, val3, ...)
```

### Parameters
- `val1, val2, ...` - Values to check (evaluated left to right)

### Returns
First non-null value, or null if all are null.

### Examples

```javascript
// Basic usage
{ result: coalesce(null, "default") }
// Output: { "result": "default" }

// Multiple values
{ result: coalesce(null, null, "found", "ignored") }
// Output: { "result": "found" }

// With object properties
{
  name: coalesce($.preferredName, $.firstName, $.username, "Anonymous")
}

// API response fallback
{
  value: coalesce($.data.value, $.legacy.val, 0)
}
```

---

## ifNull()

Returns the value if not null, otherwise returns the default.

### Syntax
```javascript
ifNull(value, default)
```

### Parameters
- `value` - Value to check
- `default` - Default value if value is null

### Returns
The value or default.

### Examples

```javascript
// Simple fallback
{ age: ifNull($.age, 0) }

// Chain with other operations
{ 
  discount: ifNull($.discount, 0) * $.total 
}

// Nested objects
{
  city: ifNull($.address.city, "Unknown")
}
```

---

## nullIf()

Returns null if two values are equal, otherwise returns the first value.

### Syntax
```javascript
nullIf(val1, val2)
```

### Parameters
- `val1` - First value
- `val2` - Second value to compare

### Returns
null if equal, otherwise val1.

### Examples

```javascript
// Convert empty strings to null
{ name: nullIf($.name, "") }

// Convert zero to null
{ quantity: nullIf($.quantity, 0) }

// Remove default values
{
  status: nullIf($.status, "unknown")
}
```

---

## safeGet()

Safely access a nested property without throwing errors.

### Syntax
```javascript
safeGet(obj, path)
```

### Parameters
- `obj` - Object to access
- `path` - Dot-separated path string

### Returns
The value at path, or null if not found.

### Examples

```javascript
// Deep navigation
{ value: safeGet($, "user.address.city") }

// Won't throw error if path doesn't exist
{ phone: safeGet($.user, "contact.phone") }

// Safe API response parsing
{
  items: safeGet($.response, "data.items")
}
```

---

## tryGet()

Try to get a value with a fallback default.

### Syntax
```javascript
tryGet(obj, path, default)
```

### Parameters
- `obj` - Object to access
- `path` - Dot-separated path string
- `default` - Default value if path not found

### Returns
The value at path or the default.

### Examples

```javascript
// With default
{ city: tryGet($, "address.city", "Unknown") }

// Numeric default
{ count: tryGet($.data, "items.length", 0) }

// Complex paths
{
  total: tryGet($, "cart.items.0.price", 0) + 
         tryGet($, "cart.items.1.price", 0)
}
```

---

## removeNulls()

Remove all null-valued properties from an object (recursively).

### Syntax
```javascript
removeNulls(obj)
```

### Parameters
- `obj` - Object to clean

### Returns
Object without null properties.

### Examples

```javascript
// Input
{
  "user": {
    "name": "Alice",
    "age": null,
    "email": "alice@example.com",
    "phone": null
  }
}

// Morph
{ user: removeNulls($.user) }

// Output
{
  "user": {
    "name": "Alice",
    "email": "alice@example.com"
  }
}

// Nested objects
{
  data: removeNulls({
    id: $.id,
    name: $.name,
    optional: $.optional,  // removed if null
    nested: {
      value: $.nested.value,
      other: null  // removed
    }
  })
}
```

---

## replaceNulls()

Replace all null values with a default value (recursively).

### Syntax
```javascript
replaceNulls(obj, default)
```

### Parameters
- `obj` - Object to process
- `default` - Value to replace nulls with

### Returns
Object with nulls replaced.

### Examples

```javascript
// Input
{
  "user": {
    "name": "Alice",
    "age": null,
    "city": null
  }
}

// Morph
{ user: replaceNulls($.user, "N/A") }

// Output
{
  "user": {
    "name": "Alice",
    "age": "N/A",
    "city": "N/A"
  }
}

// With numeric default
{
  stats: replaceNulls($.stats, 0)
}

// Nested replacement
{
  config: replaceNulls($.config, false)
}
```

---

## isNullOrEmpty()

Check if a value is null or empty.

### Syntax
```javascript
isNullOrEmpty(value)
```

### Parameters
- `value` - Value to check

### Returns
true if null, empty string, empty array, or empty object.

### Examples

```javascript
// String check
{ isEmpty: isNullOrEmpty($.name) }
// true if name is null or ""

// Array check
{ hasItems: !isNullOrEmpty($.items) }

// Conditional logic
{
  message: isNullOrEmpty($.message) ? "No message" : $.message
}

// Object check
{
  hasData: !isNullOrEmpty($.data)
}
```

---

## firstValid()

Returns the first non-null and non-empty value.

### Syntax
```javascript
firstValid(val1, val2, val3, ...)
```

### Parameters
- `val1, val2, ...` - Values to check

### Returns
First valid (non-null, non-empty) value.

### Examples

```javascript
// Skip empty strings
{ name: firstValid($.nickname, $.fullName, "Guest") }

// Skip empty arrays
{ items: firstValid($.items, $.defaultItems, []) }

// Multiple fallbacks
{
  contact: firstValid(
    $.mobile,
    $.phone,
    $.email,
    "No contact"
  )
}
```

---

## Common Patterns

### API Response Handling
```javascript
{
  userId: coalesce($.data.user.id, $.user_id, 0),
  userName: ifNull($.data.user.name, "Unknown"),
  email: safeGet($, "data.user.email"),
  phone: tryGet($, "data.user.contact.phone", null)
}
```

### Data Cleaning
```javascript
{
  user: removeNulls({
    id: $.id,
    name: firstValid($.name, $.username, "Guest"),
    email: nullIf($.email, ""),
    preferences: replaceNulls($.preferences, {})
  })
}
```

### Safe Navigation
```javascript
{
  address: {
    street: safeGet($.user, "address.street"),
    city: tryGet($.user, "address.city", "Unknown"),
    country: coalesce(
      safeGet($.user, "address.country"),
      "US"
    )
  }
}
```

### Conditional Defaults
```javascript
{
  config: {
    enabled: ifNull($.config.enabled, true),
    timeout: coalesce($.config.timeout, 30000),
    retries: nullIf($.config.retries, -1)
  }
}
```

---

## Best Practices

1. **Use coalesce() for multiple fallbacks**: When you have several possible sources for a value
2. **Use ifNull() for simple defaults**: When you have a single fallback value
3. **Use safeGet() for uncertain paths**: When accessing deeply nested optional properties
4. **Use tryGet() for defaults**: Combines safe access with default value
5. **Use removeNulls() before sending**: Clean data before external API calls
6. **Use replaceNulls() for defaults**: Fill missing values with sensible defaults
7. **Use firstValid() to skip empties**: When empty strings/arrays should also fall through

---

## Performance Tips

- `coalesce()` stops at first non-null value (short-circuit evaluation)
- `safeGet()` is safe but slightly slower than direct access
- `removeNulls()` and `replaceNulls()` recursively process nested objects
- Use simple null checks (`?? operator`) for performance-critical code

---

[← Back to Functions](../README.md#null-safety-functions)
