# merge() - Merge Objects

## Syntax
```javascript
merge(object1, object2, ...)
```

## Description
Merges multiple objects into a single object. Properties from later objects override properties from earlier objects when there are conflicts.

## Parameters
- `object1, object2, ...` - Objects to merge (2 or more objects)

## Returns
A new object containing all properties from all input objects. Later objects take precedence.

## Examples

### Basic Object Merge
```javascript
// Input
{
  "user": {"name": "Alice", "age": 25},
  "updates": {"age": 26, "city": "NYC"}
}

// Morph
{
  result: merge($.user, $.updates)
}

// Output
{
  "result": {
    "name": "Alice",
    "age": 26,
    "city": "NYC"
  }
}
```

### Merging Multiple Objects
```javascript
// Input
{
  "base": {"a": 1},
  "layer1": {"b": 2},
  "layer2": {"c": 3}
}

// Morph
{
  combined: merge($.base, $.layer1, $.layer2)
}

// Output
{
  "combined": {"a": 1, "b": 2, "c": 3}
}
```

### Override Properties
```javascript
// Input
{
  "defaults": {"theme": "light", "lang": "en", "notifications": true},
  "userPrefs": {"theme": "dark", "lang": "es"}
}

// Morph
{
  settings: merge($.defaults, $.userPrefs)
}

// Output
{
  "settings": {
    "theme": "dark",
    "lang": "es",
    "notifications": true
  }
}
```

### Building Complex Objects
```javascript
// Input
{
  "personal": {"name": "John", "age": 30},
  "contact": {"email": "john@example.com", "phone": "123-456"},
  "address": {"city": "Boston", "state": "MA"}
}

// Morph
{
  profile: merge(
    $.personal,
    $.contact,
    $.address,
    {"status": "active", "role": "user"}
  )
}

// Output
{
  "profile": {
    "name": "John",
    "age": 30,
    "email": "john@example.com",
    "phone": "123-456",
    "city": "Boston",
    "state": "MA",
    "status": "active",
    "role": "user"
  }
}
```

### Nested Object Merge
```javascript
// Input
{
  "obj1": {"user": {"name": "Alice"}, "meta": {"version": 1}},
  "obj2": {"user": {"age": 25}, "meta": {"version": 2}}
}

// Morph
{
  result: merge($.obj1, $.obj2)
}

// Output (shallow merge - nested objects are replaced, not merged)
{
  "result": {
    "user": {"age": 25},
    "meta": {"version": 2}
  }
}
```

## Common Use Cases

1. **Configuration Management**: Merge default settings with user preferences
2. **Object Composition**: Build complex objects from smaller pieces
3. **API Response**: Combine data from multiple sources
4. **Defaults Pattern**: Apply default values with overrides

## Tips and Best Practices

- Objects are merged shallowly (nested objects are replaced, not deep merged)
- Order matters: later objects override earlier ones
- Useful for applying configuration layers
- Can merge literal objects with data objects

## Related Functions

- [set()](set.md) - Set nested values in objects
- [get()](get.md) - Get nested values from objects
- [keys()](keys.md) - Get object keys
- [values()](values.md) - Get object values

## Performance Notes

- O(n) where n is total number of properties across all objects
- Efficient for combining small to medium objects
- Consider breaking down very large merges

---

[← Back to Functions](../README.md#object-functions)
