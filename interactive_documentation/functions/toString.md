# toString() - Convert to String

## Syntax
```javascript
toString(value)
```

## Description
Converts any value to its string representation.

## Parameters
- `value` - The value to convert to a string

## Returns
String representation of the value.

## Examples

### Convert Number to String
```javascript
// Input
{
  "num": 42,
  "price": 99.99
}

// Morph
{
  numStr: toString($.num),
  priceStr: toString($.price),
  formatted: "$" + toString($.price)
}

// Output
{
  "numStr": "42",
  "priceStr": "99.99",
  "formatted": "$99.99"
}
```

### Convert Boolean
```javascript
// Input
{
  "active": true,
  "verified": false
}

// Morph
{
  activeStr: toString($.active),
  verifiedStr: toString($.verified)
}

// Output
{
  "activeStr": "true",
  "verifiedStr": "false"
}
```

### Build Display Text
```javascript
// Input
{
  "user": {
    "name": "Alice",
    "age": 25,
    "score": 95.5
  }
}

// Morph
{
  summary: $.user.name + " is " + toString($.user.age) + " years old with score " + toString($.user.score)
}

// Output
{
  "summary": "Alice is 25 years old with score 95.5"
}
```

### Convert Array Elements
```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5]
}

// Morph
{
  strings: map($.numbers, "n", toString(n)),
  joined: join(map($.numbers, "n", toString(n)), ", ")
}

// Output
{
  "strings": ["1", "2", "3", "4", "5"],
  "joined": "1, 2, 3, 4, 5"
}
```

### Format IDs
```javascript
// Input
{
  "users": [
    {"id": 1, "name": "Alice"},
    {"id": 2, "name": "Bob"},
    {"id": 3, "name": "Charlie"}
  ]
}

// Morph
{
  formatted: map($.users, "u", "USER-" + toString(u.id) + "-" + u.name)
}

// Output
{
  "formatted": ["USER-1-Alice", "USER-2-Bob", "USER-3-Charlie"]
}
```

### Null Handling
```javascript
// Input
{
  "value1": 42,
  "value2": null,
  "value3": 0
}

// Morph
{
  str1: toString($.value1),
  str2: toString($.value2),
  str3: toString($.value3)
}

// Output
{
  "str1": "42",
  "str2": "null",
  "str3": "0"
}
```

### Create Log Messages
```javascript
// Input
{
  "event": {
    "type": "login",
    "userId": 12345,
    "timestamp": 1699876543210
  }
}

// Morph
{
  logMessage: $.event.type + " by user " + toString($.event.userId) + " at " + toString($.event.timestamp)
}

// Output
{
  "logMessage": "login by user 12345 at 1699876543210"
}
```

## Common Use Cases

1. **String Concatenation**: Combine values with strings
2. **Display Formatting**: Format values for display
3. **Logging**: Create log messages
4. **ID Generation**: Build composite IDs

## Tips and Best Practices

- Automatically handles all types
- Null becomes "null" string
- Numbers maintain precision
- Use for string concatenation safety

## Related Functions

- [toNumber()](toNumber.md) - Convert to number
- [toBool()](toBool.md) - Convert to boolean
- [jsonStringify()](jsonStringify.md) - Convert to JSON string

## Performance Notes

- O(1) for primitives
- O(n) for complex objects
- Fast conversion

---

[← Back to Functions](../README.md#type-conversion)
