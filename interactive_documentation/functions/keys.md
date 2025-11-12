# keys() - Get Object Keys

## Syntax
```javascript
keys(object)
```

## Description
Returns an array containing all the keys (property names) of an object.

## Parameters
- `object` - The object to get keys from

## Returns
Array of strings containing all keys from the object.

## Examples

### Basic Object Keys
```javascript
// Input
{
  "user": {
    "name": "Alice",
    "age": 25,
    "email": "alice@example.com"
  }
}

// Morph
{
  userFields: keys($.user)
}

// Output
{
  "userFields": ["name", "age", "email"]
}
```

### Count Properties
```javascript
// Input
{
  "config": {
    "host": "localhost",
    "port": 8080,
    "debug": true,
    "timeout": 30
  }
}

// Morph
{
  configKeys: keys($.config),
  keyCount: len(keys($.config))
}

// Output
{
  "configKeys": ["host", "port", "debug", "timeout"],
  "keyCount": 4
}
```

### Check Required Fields
```javascript
// Input
{
  "data": {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com"
  },
  "required": ["firstName", "lastName", "email", "phone"]
}

// Morph
{
  presentFields: keys($.data),
  missingFields: filter($.required, "r", !exists(get($.data, r)))
}

// Output
{
  "presentFields": ["firstName", "lastName", "email"],
  "missingFields": ["phone"]
}
```

### Dynamic Object Construction
```javascript
// Input
{
  "metadata": {
    "title": "Document",
    "author": "Alice",
    "date": "2025-01-12"
  }
}

// Morph
{
  fields: keys($.metadata),
  fieldCount: len(keys($.metadata)),
  summary: join(map(keys($.metadata), "k", k + ": " + get($.metadata, k)), ", ")
}

// Output
{
  "fields": ["title", "author", "date"],
  "fieldCount": 3,
  "summary": "title: Document, author: Alice, date: 2025-01-12"
}
```

### Empty Object
```javascript
// Input
{
  "empty": {}
}

// Morph
{
  emptyKeys: keys($.empty),
  isEmpty: len(keys($.empty)) == 0
}

// Output
{
  "emptyKeys": [],
  "isEmpty": true
}
```

### Nested Object Keys
```javascript
// Input
{
  "user": {
    "profile": {
      "name": "Alice",
      "age": 25
    },
    "settings": {
      "theme": "dark",
      "lang": "en"
    }
  }
}

// Morph
{
  topLevelKeys: keys($.user),
  profileKeys: keys($.user.profile),
  settingsKeys: keys($.user.settings)
}

// Output
{
  "topLevelKeys": ["profile", "settings"],
  "profileKeys": ["name", "age"],
  "settingsKeys": ["theme", "lang"]
}
```

## Common Use Cases

1. **Schema Validation**: Check which fields are present
2. **Dynamic Processing**: Iterate over unknown object properties
3. **Debugging**: Inspect object structure
4. **Field Counting**: Count number of properties

## Tips and Best Practices

- Returns empty array for empty objects
- Order of keys may not be guaranteed in some implementations
- Useful for dynamic object processing
- Combine with map() to transform keys

## Related Functions

- [values()](values.md) - Get object values
- [entries()](entries.md) - Get key-value pairs
- [get()](get.md) - Get value by key
- [exists()](exists.md) - Check if key exists

## Performance Notes

- O(n) where n is number of keys
- Fast for small to medium objects
- Use for object introspection

---

[← Back to Functions](../README.md#object-functions)
