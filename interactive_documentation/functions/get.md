# get() - Get Nested Value Safely

## Syntax
```javascript
get(object, path, [defaultValue])
```

## Description
Safely retrieves a nested value from an object using dot notation or array indices. Returns a default value if the path doesn't exist.

## Parameters
- `object` - The source object
- `path` - String path using dot notation (e.g., "user.address.city")
- `defaultValue` - Optional value to return if path doesn't exist (defaults to null)

## Returns
The value at the specified path, or the default value if path doesn't exist.

## Examples

### Basic Nested Access
```javascript
// Input
{
  "user": {
    "name": "Alice",
    "address": {
      "city": "New York",
      "zip": "10001"
    }
  }
}

// Morph
{
  city: get($.user, "address.city"),
  zip: get($.user, "address.zip")
}

// Output
{
  "city": "New York",
  "zip": "10001"
}
```

### With Default Value
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
  name: get($.user, "name", "Unknown"),
  email: get($.user, "email", "no-email@example.com"),
  phone: get($.user, "phone", "N/A")
}

// Output
{
  "name": "Alice",
  "email": "no-email@example.com",
  "phone": "N/A"
}
```

### Array Access
```javascript
// Input
{
  "users": [
    {"name": "Alice", "age": 25},
    {"name": "Bob", "age": 30}
  ]
}

// Morph
{
  firstUser: get($.users, "0.name"),
  secondUser: get($.users, "1.name"),
  thirdUser: get($.users, "2.name", "N/A")
}

// Output
{
  "firstUser": "Alice",
  "secondUser": "Bob",
  "thirdUser": "N/A"
}
```

### Deep Nested Access
```javascript
// Input
{
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

// Morph
{
  backendLead: get($.company, "departments.engineering.teams.backend.lead"),
  frontendLead: get($.company, "departments.engineering.teams.frontend.lead", "TBD")
}

// Output
{
  "backendLead": "Alice",
  "frontendLead": "TBD"
}
```

### Safe Configuration Access
```javascript
// Input
{
  "config": {
    "database": {
      "host": "localhost",
      "port": 5432
    }
  }
}

// Morph
{
  dbHost: get($.config, "database.host", "127.0.0.1"),
  dbPort: get($.config, "database.port", 5432),
  dbUser: get($.config, "database.user", "admin"),
  dbPass: get($.config, "database.password", ""),
  cacheEnabled: get($.config, "cache.enabled", false)
}

// Output
{
  "dbHost": "localhost",
  "dbPort": 5432,
  "dbUser": "admin",
  "dbPass": "",
  "cacheEnabled": false
}
```

### Dynamic Path Access
```javascript
// Input
{
  "data": {
    "users": {
      "alice": {"score": 95},
      "bob": {"score": 88}
    }
  },
  "username": "alice"
}

// Morph
{
  userScore: get($.data, "users." + $.username + ".score", 0)
}

// Output
{
  "userScore": 95
}
```

## Common Use Cases

1. **Safe Access**: Avoid errors when accessing potentially missing data
2. **Configuration**: Read config values with defaults
3. **Optional Fields**: Handle optional nested properties
4. **API Response**: Safely extract data from API responses

## Tips and Best Practices

- Use when data structure may be incomplete
- Provide sensible default values
- More robust than direct property access
- Works with arrays using numeric indices

## Related Functions

- [set()](set.md) - Set nested values
- [exists()](exists.md) - Check if value exists
- [keys()](keys.md) - Get object keys
- [values()](values.md) - Get object values

## Performance Notes

- O(d) where d is depth of path
- Fast for shallow paths
- Use for safe access patterns

---

[← Back to Functions](../README.md#object-functions)
