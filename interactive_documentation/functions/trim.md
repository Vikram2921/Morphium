# trim() - Remove Whitespace

## Syntax
```javascript
trim(string)
```

## Description
Removes leading and trailing whitespace from a string.

## Parameters
- `string` - The string to trim

## Returns
String with whitespace removed from both ends.

## Examples

### Basic Trim
```javascript
// Input
{
  "text": "  hello world  "
}

// Morph
{
  trimmed: trim($.text)
}

// Output
{
  "trimmed": "hello world"
}
```

### Clean User Input
```javascript
// Input
{
  "userInput": {
    "name": "  Alice  ",
    "email": "  alice@example.com  "
  }
}

// Morph
{
  cleaned: {
    name: trim($.userInput.name),
    email: trim($.userInput.email)
  }
}

// Output
{
  "cleaned": {
    "name": "Alice",
    "email": "alice@example.com"
  }
}
```

### Process Array of Strings
```javascript
// Input
{
  "items": ["  apple  ", "  banana  ", "  orange  "]
}

// Morph
{
  cleaned: map($.items, "item", trim(item))
}

// Output
{
  "cleaned": ["apple", "banana", "orange"]
}
```

### Clean CSV Fields
```javascript
// Input
{
  "csv": "Alice  , 25 ,  Engineer  "
}

// Morph
{
  fields: map(split($.csv, ","), "f", trim(f))
}

// Output
{
  "fields": ["Alice", "25", "Engineer"]
}
```

### Tabs and Newlines
```javascript
// Input
{
  "text": "\t\n  content  \n\t"
}

// Morph
{
  trimmed: trim($.text)
}

// Output
{
  "trimmed": "content"
}
```

### Normalize Before Comparison
```javascript
// Input
{
  "input": "  hello  ",
  "expected": "hello"
}

// Morph
{
  matches: trim($.input) == $.expected
}

// Output
{
  "matches": true
}
```

### Clean Multi-line Text
```javascript
// Input
{
  "lines": [
    "  line 1  ",
    "  line 2  ",
    "  line 3  "
  ]
}

// Morph
{
  cleaned: join(map($.lines, "l", trim(l)), "\n")
}

// Output
{
  "cleaned": "line 1\nline 2\nline 3"
}
```

## Common Use Cases

1. **User Input**: Clean form input data
2. **CSV Processing**: Clean CSV field values
3. **String Comparison**: Normalize before comparing
4. **Data Cleaning**: Remove unwanted whitespace

## Tips and Best Practices

- Always trim user input
- Use before string comparisons
- Doesn't affect internal whitespace
- Removes spaces, tabs, newlines

## Related Functions

- [upper()](upper.md) - Convert to uppercase
- [lower()](lower.md) - Convert to lowercase
- [replace()](replace.md) - Replace substrings
- [split()](split.md) - Split strings

## Performance Notes

- O(n) where n is string length
- Very fast operation
- Creates new string if trimming occurs

---

[← Back to Functions](../README.md#string-functions)
