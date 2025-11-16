# Key Manipulation Functions - Feature Summary

## Overview

Two new functions have been added to Morphium DSL for object key manipulation:

1. **removeKey()** - Remove a key from an object
2. **renameKey()** - Rename a key in an object

Both functions return a new object without modifying the original (immutable operations).

## Functions

### removeKey(object, key)

Removes a specified key from an object.

**Syntax:**
```javascript
removeKey(object, key)
```

**Example:**
```javascript
// Input: { "user": { "name": "Alice", "password": "secret", "email": "alice@example.com" } }
// Morph: { user: removeKey($.user, "password") }
// Output: { "user": { "name": "Alice", "email": "alice@example.com" } }
```

**Common Use Cases:**
- Remove sensitive data before sending to external APIs
- Strip debug fields from production data
- Clean up deprecated fields
- Remove internal metadata

### renameKey(object, oldKey, newKey)

Renames a key in an object, preserving its value.

**Syntax:**
```javascript
renameKey(object, oldKey, newKey)
```

**Example:**
```javascript
// Input: { "user": { "first_name": "Alice", "last_name": "Smith" } }
// Morph: { user: renameKey(renameKey($.user, "first_name", "firstName"), "last_name", "lastName") }
// Output: { "user": { "firstName": "Alice", "lastName": "Smith" } }
```

**Common Use Cases:**
- Convert snake_case to camelCase
- Standardize field names across different APIs
- Update legacy field names during migration
- Normalize inconsistent naming conventions

## Chaining

Both functions can be chained together:

```javascript
// Remove sensitive field and rename others
{
  order: removeKey(
    renameKey(
      renameKey($.order, "ord_id", "orderId"),
      "cust_name", "customerName"),
    "internal_note"
  )
}
```

## Implementation Details

- **Location**: `src/main/java/com/morphium/builtin/BuiltinFunctions.java`
- **Type**: Eager functions (arguments evaluated first)
- **Immutability**: Both functions return new objects without modifying the original
- **Performance**: O(n) where n is the number of keys (creates deep copy)

## Documentation

- [removeKey() Documentation](functions/removeKey.md)
- [renameKey() Documentation](functions/renameKey.md)
- [Complete Demo](../src/main/java/com/morphium/examples/KeyManipulationDemo.java)

## Testing

Run the demo to see all examples in action:

```bash
mvn clean compile
java -cp "target/classes;..." com.morphium.examples.KeyManipulationDemo
```

The demo includes:
1. Basic key removal (removeKey)
2. Multiple key removal (chained removeKey)
3. Basic key rename (renameKey)
4. Snake case to camel case conversion
5. Combined removeKey and renameKey operations

## Examples

### Remove Password Field
```javascript
{ user: removeKey($.user, "password") }
```

### Convert API Format
```javascript
{
  product: renameKey(
    renameKey(
      renameKey($.product, "product_id", "productId"),
      "product_name", "productName"),
    "unit_price", "unitPrice"
  )
}
```

### Clean and Rename
```javascript
{
  data: removeKey(
    renameKey($.data, "old_name", "newName"),
    "deprecated_field"
  )
}
```

## Benefits

1. **Data Privacy**: Easily remove sensitive fields
2. **API Compatibility**: Transform between different naming conventions
3. **Code Modernization**: Update legacy field names
4. **Immutability**: Safe transformations without side effects
5. **Composability**: Chain with other functions for complex transformations

---

**Status**: ✅ Implemented and tested
**Version**: 1.0.0-SNAPSHOT
**Date**: 2025-11-16
