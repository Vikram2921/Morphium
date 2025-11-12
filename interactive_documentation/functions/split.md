# split() - Split String into Array

## Syntax
```javascript
split(string, delimiter)
```

## Description
Splits a string into an array of substrings based on a delimiter.

## Parameters
- `string` - The string to split
- `delimiter` - The delimiter string to split on

## Returns
Array of strings.

## Examples

### Basic Split
```javascript
// Input
{
  "text": "apple,banana,orange"
}

// Morph
{
  fruits: split($.text, ",")
}

// Output
{
  "fruits": ["apple", "banana", "orange"]
}
```

### Split by Space
```javascript
// Input
{
  "sentence": "Hello World from Morphium"
}

// Morph
{
  words: split($.sentence, " "),
  wordCount: len(split($.sentence, " "))
}

// Output
{
  "words": ["Hello", "World", "from", "Morphium"],
  "wordCount": 4
}
```

### Split Email
```javascript
// Input
{
  "email": "user@example.com"
}

// Morph
{
  parts: split($.email, "@"),
  username: split($.email, "@")[0],
  domain: split($.email, "@")[1]
}

// Output
{
  "parts": ["user", "example.com"],
  "username": "user",
  "domain": "example.com"
}
```

### Split CSV Line
```javascript
// Input
{
  "csv": "Alice,25,Engineer,New York"
}

// Morph
{
  fields: split($.csv, ","),
  user: {
    name: split($.csv, ",")[0],
    age: toNumber(split($.csv, ",")[1]),
    role: split($.csv, ",")[2],
    city: split($.csv, ",")[3]
  }
}

// Output
{
  "fields": ["Alice", "25", "Engineer", "New York"],
  "user": {
    "name": "Alice",
    "age": 25,
    "role": "Engineer",
    "city": "New York"
  }
}
```

### Split Path
```javascript
// Input
{
  "path": "/home/user/documents/file.txt"
}

// Morph
{
  segments: split($.path, "/"),
  filename: split(split($.path, "/"), ".")
}

// Output
{
  "segments": ["", "home", "user", "documents", "file.txt"],
  "filename": ["file", "txt"]
}
```

### Process Multiple Lines
```javascript
// Input
{
  "text": "line1\nline2\nline3"
}

// Morph
{
  lines: split($.text, "\n"),
  lineCount: len(split($.text, "\n")),
  firstLine: split($.text, "\n")[0]
}

// Output
{
  "lines": ["line1", "line2", "line3"],
  "lineCount": 3,
  "firstLine": "line1"
}
```

### Split and Map
```javascript
// Input
{
  "tags": "javascript,python,java,golang"
}

// Morph
{
  tagList: map(split($.tags, ","), "t", {
    name: t,
    uppercase: upper(t)
  })
}

// Output
{
  "tagList": [
    {"name": "javascript", "uppercase": "JAVASCRIPT"},
    {"name": "python", "uppercase": "PYTHON"},
    {"name": "java", "uppercase": "JAVA"},
    {"name": "golang", "uppercase": "GOLANG"}
  ]
}
```

## Common Use Cases

1. **CSV Parsing**: Parse comma-separated values
2. **Text Processing**: Split sentences into words
3. **Path Parsing**: Split file paths or URLs
4. **Tag Processing**: Parse comma-separated tags

## Tips and Best Practices

- Returns array with at least one element
- Empty delimiter splits into individual characters
- Combine with trim() to handle extra whitespace
- Use with map() for further processing

## Related Functions

- [join()](join.md) - Join array into string
- [trim()](trim.md) - Remove whitespace
- [replace()](replace.md) - Replace substrings
- [map()](map.md) - Transform split results

## Performance Notes

- O(n) where n is string length
- Fast for most use cases
- Memory proportional to number of splits

---

[← Back to Functions](../README.md#string-functions)
