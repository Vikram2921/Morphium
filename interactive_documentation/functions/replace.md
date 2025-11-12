# replace() - Replace Substring

## Syntax
```javascript
replace(string, searchString, replaceString)
```

## Description
Replaces all occurrences of a substring with another string.

## Parameters
- `string` - The source string
- `searchString` - The substring to find
- `replaceString` - The string to replace with

## Returns
New string with replacements made.

## Examples

### Basic Replace
```javascript
// Input
{
  "text": "Hello World"
}

// Morph
{
  result: replace($.text, "World", "Morphium")
}

// Output
{
  "result": "Hello Morphium"
}
```

### Replace Multiple Occurrences
```javascript
// Input
{
  "text": "foo bar foo baz foo"
}

// Morph
{
  result: replace($.text, "foo", "qux")
}

// Output
{
  "result": "qux bar qux baz qux"
}
```

### Remove Characters
```javascript
// Input
{
  "phone": "(555) 123-4567"
}

// Morph
{
  cleaned: replace(replace(replace(
    $.phone, "(", ""), ")", ""), " ", "")
}

// Output
{
  "cleaned": "555123-4567"
}
```

### Normalize Path
```javascript
// Input
{
  "windowsPath": "C:\\Users\\Alice\\Documents"
}

// Morph
{
  unixPath: replace($.windowsPath, "\\", "/")
}

// Output
{
  "unixPath": "C:/Users/Alice/Documents"
}
```

### Template String Replacement
```javascript
// Input
{
  "template": "Hello {{name}}, welcome to {{company}}!",
  "data": {
    "name": "Alice",
    "company": "Morphium"
  }
}

// Morph
{
  message: replace(
    replace($.template, "{{name}}", $.data.name),
    "{{company}}", $.data.company
  )
}

// Output
{
  "message": "Hello Alice, welcome to Morphium!"
}
```

### Clean Data
```javascript
// Input
{
  "items": [
    "item_one",
    "item_two",
    "item_three"
  ]
}

// Morph
{
  formatted: map($.items, "i", replace(i, "_", " "))
}

// Output
{
  "formatted": ["item one", "item two", "item three"]
}
```

### URL Encoding Simulation
```javascript
// Input
{
  "text": "hello world"
}

// Morph
{
  encoded: replace($.text, " ", "%20")
}

// Output
{
  "encoded": "hello%20world"
}
```

### Chain Replacements
```javascript
// Input
{
  "text": "The quick brown fox"
}

// Morph
{
  result: replace(
    replace(
      replace($.text, "quick", "slow"),
      "brown", "red"),
    "fox", "dog"
  )
}

// Output
{
  "result": "The slow red dog"
}
```

## Common Use Cases

1. **Text Transformation**: Replace placeholders or tokens
2. **Data Cleaning**: Remove or replace unwanted characters
3. **Path Normalization**: Convert path separators
4. **Template Processing**: Fill in template values

## Tips and Best Practices

- Replaces ALL occurrences, not just first
- Case-sensitive by default
- Use lower() for case-insensitive replacement
- Chain multiple replace() calls for complex transformations

## Related Functions

- [split()](split.md) - Split string by delimiter
- [join()](join.md) - Join array into string
- [trim()](trim.md) - Remove whitespace
- [upper()](upper.md), [lower()](lower.md) - Change case

## Performance Notes

- O(n) where n is string length
- Fast for most use cases
- Creates new string

---

[← Back to Functions](../README.md#string-functions)
