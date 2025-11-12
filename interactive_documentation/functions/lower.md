# lower() - Convert to Lowercase

## Syntax
```javascript
lower(string)
```

## Description
Converts a string to lowercase letters.

## Parameters
- `string` - The string to convert

## Returns
String in lowercase.

## Examples

### Basic Lowercase
```javascript
// Input
{
  "text": "HELLO WORLD"
}

// Morph
{
  lowercase: lower($.text)
}

// Output
{
  "lowercase": "hello world"
}
```

### Normalize Email
```javascript
// Input
{
  "email": "Alice@EXAMPLE.COM"
}

// Morph
{
  normalized: lower($.email)
}

// Output
{
  "normalized": "alice@example.com"
}
```

### Convert Array of Strings
```javascript
// Input
{
  "tags": ["JAVASCRIPT", "PYTHON", "JAVA"]
}

// Morph
{
  lowerTags: map($.tags, "t", lower(t))
}

// Output
{
  "lowerTags": ["javascript", "python", "java"]
}
```

### Case-Insensitive Search Prep
```javascript
// Input
{
  "users": [
    {"name": "Alice", "email": "ALICE@EXAMPLE.COM"},
    {"name": "Bob", "email": "BOB@EXAMPLE.COM"}
  ],
  "search": "alice"
}

// Morph
{
  found: filter($.users, "u", lower(u.email) == lower($.search + "@example.com"))
}

// Output
{
  "found": [
    {"name": "Alice", "email": "ALICE@EXAMPLE.COM"}
  ]
}
```

### URL Normalization
```javascript
// Input
{
  "url": "HTTPS://EXAMPLE.COM/API/V1/USERS"
}

// Morph
{
  normalized: lower($.url)
}

// Output
{
  "normalized": "https://example.com/api/v1/users"
}
```

### Mixed Case Input
```javascript
// Input
{
  "text": "JavaScript Is AWESOME!"
}

// Morph
{
  result: lower($.text)
}

// Output
{
  "result": "javascript is awesome!"
}
```

## Common Use Cases

1. **Normalization**: Normalize strings for comparison
2. **Email Processing**: Normalize email addresses
3. **URL Processing**: Normalize URLs
4. **Case-Insensitive Search**: Prepare strings for searching

## Tips and Best Practices

- Works with all Unicode characters
- Essential for case-insensitive comparisons
- Always normalize both sides of comparison
- Combine with trim() for complete normalization

## Related Functions

- [upper()](upper.md) - Convert to uppercase
- [trim()](trim.md) - Remove whitespace
- [replace()](replace.md) - Replace substrings

## Performance Notes

- O(n) where n is string length
- Fast operation
- Creates new string

---

[← Back to Functions](../README.md#string-functions)
