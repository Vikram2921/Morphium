# exists() - Check if Value Exists

## Syntax
```javascript
exists(value)
```

## Description
Checks if a value exists (is not null and not undefined).

## Parameters
- `value` - The value to check

## Returns
Boolean: true if value exists, false if null or undefined.

## Examples

### Basic Existence Check
```javascript
// Input
{
  "user": {
    "name": "Alice",
    "age": 25,
    "email": null
  }
}

// Morph
{
  hasName: exists($.user.name),
  hasAge: exists($.user.age),
  hasEmail: exists($.user.email),
  hasPhone: exists($.user.phone)
}

// Output
{
  "hasName": true,
  "hasAge": true,
  "hasEmail": false,
  "hasPhone": false
}
```

### Filter by Existence
```javascript
// Input
{
  "items": [
    {"name": "Item1", "price": 10},
    {"name": "Item2", "price": null},
    {"name": "Item3", "price": 30}
  ]
}

// Morph
{
  withPrice: filter($.items, "i", exists(i.price))
}

// Output
{
  "withPrice": [
    {"name": "Item1", "price": 10},
    {"name": "Item3", "price": 30}
  ]
}
```

### Conditional Assignment
```javascript
// Input
{
  "config": {
    "host": "localhost",
    "port": null
  }
}

// Morph
{
  host: if (exists($.config.host)) { $.config.host } else { "127.0.0.1" },
  port: if (exists($.config.port)) { $.config.port } else { 8080 }
}

// Output
{
  "host": "localhost",
  "port": 8080
}
```

### Count Existing Values
```javascript
// Input
{
  "data": {
    "field1": "value1",
    "field2": null,
    "field3": "value3",
    "field4": null,
    "field5": "value5"
  }
}

// Morph
{
  existingCount: count(filter(values($.data), "v", exists(v)))
}

// Output
{
  "existingCount": 3
}
```

### Validate Required Fields
```javascript
// Input
{
  "user": {
    "firstName": "Alice",
    "lastName": "Smith",
    "email": "alice@example.com",
    "phone": null
  }
}

// Morph
{
  valid: exists($.user.firstName) && exists($.user.lastName) && exists($.user.email),
  missingFields: filter(
    ["firstName", "lastName", "email", "phone"],
    "f",
    !exists(get($.user, f))
  )
}

// Output
{
  "valid": true,
  "missingFields": ["phone"]
}
```

### Safe Navigation
```javascript
// Input
{
  "data": {
    "user": {
      "profile": {
        "name": "Alice"
      }
    }
  }
}

// Morph
{
  userName: if (exists($.data.user.profile.name)) {
    $.data.user.profile.name
  } else {
    "Unknown"
  }
}

// Output
{
  "userName": "Alice"
}
```

### Check Zero vs Null
```javascript
// Input
{
  "values": {
    "a": 0,
    "b": null,
    "c": "",
    "d": false
  }
}

// Morph
{
  existsA: exists($.values.a),
  existsB: exists($.values.b),
  existsC: exists($.values.c),
  existsD: exists($.values.d)
}

// Output (zero, empty string, and false exist, but null doesn't)
{
  "existsA": true,
  "existsB": false,
  "existsC": true,
  "existsD": true
}
```

## Common Use Cases

1. **Validation**: Check required fields
2. **Safe Access**: Validate before using values
3. **Filtering**: Remove items with missing data
4. **Conditional Logic**: Branch based on existence

## Tips and Best Practices

- Returns false for null and undefined only
- Zero, false, empty string are considered existing
- Use for validation before access
- Combine with get() for safe nested access

## Related Functions

- [get()](get.md) - Get value with default
- [filter()](filter.md) - Filter by existence
- [toBool()](toBool.md) - Convert to boolean

## Performance Notes

- O(1) operation
- Very fast check
- No memory allocation

---

[← Back to Functions](../README.md#utility-functions)
