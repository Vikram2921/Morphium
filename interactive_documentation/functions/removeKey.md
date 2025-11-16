# removeKey() - Remove Object Key

## Syntax
```javascript
removeKey(object, key)
```

## Description
Removes a key from an object. Returns a new object without the specified key. The original object is not modified.

## Parameters
- `object` - The source object
- `key` - String name of the key to remove

## Returns
New object without the specified key.

## Examples

### Basic Key Removal
```javascript
// Input
{
  "user": {
    "name": "Alice",
    "age": 25,
    "temp": "delete me"
  }
}

// Morph
{
  user: removeKey($.user, "temp")
}

// Output
{
  "user": {
    "name": "Alice",
    "age": 25
  }
}
```

### Remove Sensitive Data
```javascript
// Input
{
  "user": {
    "id": 123,
    "name": "Alice",
    "password": "secret123",
    "email": "alice@example.com"
  }
}

// Morph
{
  user: removeKey($.user, "password")
}

// Output
{
  "user": {
    "id": 123,
    "name": "Alice",
    "email": "alice@example.com"
  }
}
```

### Chain Multiple Removals
```javascript
// Input
{
  "product": {
    "id": 1,
    "name": "Widget",
    "price": 29.99,
    "internalCode": "INT-001",
    "costPrice": 15.00
  }
}

// Morph
{
  product: removeKey(
    removeKey($.product, "internalCode"),
    "costPrice"
  )
}

// Output
{
  "product": {
    "id": 1,
    "name": "Widget",
    "price": 29.99
  }
}
```

### Remove Debug Fields
```javascript
// Input
{
  "order": {
    "id": 456,
    "total": 100.00,
    "debug_timestamp": "2024-01-01",
    "debug_source": "test"
  }
}

// Morph
{
  order: removeKey(
    removeKey($.order, "debug_timestamp"),
    "debug_source"
  )
}

// Output
{
  "order": {
    "id": 456,
    "total": 100.00
  }
}
```

### Clean API Response
```javascript
// Input
{
  "data": {
    "userId": 789,
    "userName": "Bob",
    "userEmail": "bob@example.com",
    "_metadata": {
      "version": 1
    },
    "_internal": true
  }
}

// Morph
{
  data: removeKey(
    removeKey($.data, "_metadata"),
    "_internal"
  )
}

// Output
{
  "data": {
    "userId": 789,
    "userName": "Bob",
    "userEmail": "bob@example.com"
  }
}
```

### Remove Array from Object
```javascript
// Input
{
  "config": {
    "name": "Production",
    "enabled": true,
    "temp_array": [1, 2, 3]
  }
}

// Morph
{
  config: removeKey($.config, "temp_array")
}

// Output
{
  "config": {
    "name": "Production",
    "enabled": true
  }
}
```

## Common Use Cases

1. **Data Sanitization**: Remove sensitive fields before sending data
2. **API Filtering**: Clean responses for external APIs
3. **Debugging**: Remove debug/internal fields from production data
4. **Privacy**: Strip personal information from logs
5. **Schema Cleanup**: Remove deprecated or unused fields

## Tips and Best Practices

- Returns new object (immutable operation)
- Original object is never modified
- Chain multiple removeKey() calls to remove multiple keys
- If key doesn't exist, returns object unchanged
- Works only with objects (returns input unchanged if not an object)

## Related Functions

- [renameKey()](renameKey.md) - Rename object keys
- [set()](set.md) - Set nested values
- [keys()](keys.md) - Get all object keys
- [values()](values.md) - Get all object values

## Performance Notes

- O(n) where n is number of keys (creates deep copy)
- Efficient for removing single keys
- Consider restructuring for bulk removals

---

[← Back to Functions](../README.md#object-functions)
