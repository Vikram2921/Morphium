# upper() - Convert to Uppercase

## Syntax
```javascript
upper(string)
```

## Description
Converts a string to uppercase letters.

## Parameters
- `string` - The string to convert

## Returns
String in uppercase.

## Examples

### Basic Uppercase
```javascript
// Input
{
  "text": "hello world"
}

// Morph
{
  uppercase: upper($.text)
}

// Output
{
  "uppercase": "HELLO WORLD"
}
```

### Convert Names
```javascript
// Input
{
  "users": [
    {"name": "alice"},
    {"name": "bob"},
    {"name": "charlie"}
  ]
}

// Morph
{
  upperNames: map($.users, "u", upper(u.name))
}

// Output
{
  "upperNames": ["ALICE", "BOB", "CHARLIE"]
}
```

### Format Headers
```javascript
// Input
{
  "headers": {
    "content-type": "application/json",
    "authorization": "Bearer token123"
  }
}

// Morph
{
  formatted: {
    contentType: upper(get($.headers, "content-type")),
    auth: upper(get($.headers, "authorization"))
  }
}

// Output
{
  "formatted": {
    "contentType": "APPLICATION/JSON",
    "auth": "BEARER TOKEN123"
  }
}
```

### Code Generation
```javascript
// Input
{
  "constant": "max_retry_count"
}

// Morph
{
  constantName: upper(replace($.constant, "_", "_"))
}

// Output
{
  "constantName": "MAX_RETRY_COUNT"
}
```

### Mixed Case Input
```javascript
// Input
{
  "text": "JavaScript is Awesome!"
}

// Morph
{
  result: upper($.text)
}

// Output
{
  "result": "JAVASCRIPT IS AWESOME!"
}
```

## Common Use Cases

1. **Normalization**: Normalize strings for comparison
2. **Display Formatting**: Format headers or titles
3. **Code Generation**: Generate constant names
4. **Data Cleaning**: Standardize text data

## Tips and Best Practices

- Works with all Unicode characters
- Useful for case-insensitive comparisons
- Combine with trim() for complete normalization
- Use before string comparisons

## Related Functions

- [lower()](lower.md) - Convert to lowercase
- [trim()](trim.md) - Remove whitespace
- [replace()](replace.md) - Replace substrings

## Performance Notes

- O(n) where n is string length
- Fast operation
- Creates new string

---

[← Back to Functions](../README.md#string-functions)
