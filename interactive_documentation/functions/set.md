# set() - Set Nested Value

## Syntax
```javascript
set(object, path, value)
```

## Description
Sets a value at a nested path in an object. Creates intermediate objects as needed. Returns a new object with the value set.

## Parameters
- `object` - The source object
- `path` - String path using dot notation (e.g., "user.address.city")
- `value` - The value to set

## Returns
New object with the value set at the specified path.

## Examples

### Basic Nested Set
```javascript
// Input
{
  "user": {
    "name": "Alice"
  }
}

// Morph
{
  updated: set($.user, "age", 25)
}

// Output
{
  "updated": {
    "name": "Alice",
    "age": 25
  }
}
```

### Create Nested Structure
```javascript
// Input
{
  "user": {}
}

// Morph
{
  updated: set(set(set(
    $.user,
    "address.city", "New York"),
    "address.state", "NY"),
    "address.zip", "10001"
  )
}

// Output
{
  "updated": {
    "address": {
      "city": "New York",
      "state": "NY",
      "zip": "10001"
    }
  }
}
```

### Update Configuration
```javascript
// Input
{
  "config": {
    "database": {
      "host": "localhost"
    }
  }
}

// Morph
{
  config: set(set(
    $.config,
    "database.port", 5432),
    "database.user", "admin"
  )
}

// Output
{
  "config": {
    "database": {
      "host": "localhost",
      "port": 5432,
      "user": "admin"
    }
  }
}
```

### Set Array Element
```javascript
// Input
{
  "data": {
    "items": [10, 20, 30]
  }
}

// Morph
{
  updated: set($.data, "items.1", 25)
}

// Output
{
  "updated": {
    "items": [10, 25, 30]
  }
}
```

### Deep Nested Update
```javascript
// Input
{
  "company": {
    "departments": {
      "engineering": {
        "teams": {}
      }
    }
  }
}

// Morph
{
  updated: set($.company, "departments.engineering.teams.backend.lead", "Alice")
}

// Output
{
  "updated": {
    "company": {
      "departments": {
        "engineering": {
          "teams": {
            "backend": {
              "lead": "Alice"
            }
          }
        }
      }
    }
  }
}
```

### Multiple Updates
```javascript
// Input
{
  "user": {
    "name": "Alice",
    "age": 25
  }
}

// Morph
{
  updated: set(
    set(
      set($.user, "age", 26),
      "city", "NYC"),
    "active", true
  )
}

// Output
{
  "updated": {
    "name": "Alice",
    "age": 26,
    "city": "NYC",
    "active": true
  }
}
```

## Common Use Cases

1. **Data Updates**: Update nested properties safely
2. **Object Building**: Construct complex objects incrementally
3. **Configuration**: Set configuration values dynamically
4. **Data Enrichment**: Add computed fields to objects

## Tips and Best Practices

- Creates intermediate objects automatically
- Returns new object (immutable operation)
- Chain multiple set() calls for multiple updates
- Use merge() for updating multiple fields at once

## Related Functions

- [get()](get.md) - Get nested values safely
- [merge()](merge.md) - Merge multiple objects
- [keys()](keys.md) - Get object keys
- [exists()](exists.md) - Check if path exists

## Performance Notes

- O(d) where d is depth of path
- Creates new object (immutable)
- Efficient for small to medium depth

---

[← Back to Functions](../README.md#object-functions)
