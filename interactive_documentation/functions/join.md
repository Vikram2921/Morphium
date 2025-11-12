# join() - Join Array into String

## Syntax
```javascript
join(array, separator)
```

## Description
Joins elements of an array into a single string, separated by a specified separator.

## Parameters
- `array` - Array of elements to join
- `separator` - String to use as separator between elements

## Returns
String with all array elements joined.

## Examples

### Basic Join
```javascript
// Input
{
  "fruits": ["apple", "banana", "orange"]
}

// Morph
{
  list: join($.fruits, ", ")
}

// Output
{
  "list": "apple, banana, orange"
}
```

### Join with Different Separators
```javascript
// Input
{
  "words": ["Hello", "World", "Morphium"]
}

// Morph
{
  space: join($.words, " "),
  dash: join($.words, "-"),
  underscore: join($.words, "_")
}

// Output
{
  "space": "Hello World Morphium",
  "dash": "Hello-World-Morphium",
  "underscore": "Hello_World_Morphium"
}
```

### Create CSV Line
```javascript
// Input
{
  "user": {
    "name": "Alice",
    "age": 25,
    "role": "Engineer"
  }
}

// Morph
{
  csv: join([$.user.name, $.user.age, $.user.role], ",")
}

// Output
{
  "csv": "Alice,25,Engineer"
}
```

### Build Sentence
```javascript
// Input
{
  "items": ["eggs", "milk", "bread"]
}

// Morph
{
  sentence: "Shopping list: " + join($.items, ", ")
}

// Output
{
  "sentence": "Shopping list: eggs, milk, bread"
}
```

### Join Numbers
```javascript
// Input
{
  "numbers": [1, 2, 3, 4, 5]
}

// Morph
{
  asString: join($.numbers, "-"),
  sumText: join($.numbers, " + ") + " = " + sum($.numbers)
}

// Output
{
  "asString": "1-2-3-4-5",
  "sumText": "1 + 2 + 3 + 4 + 5 = 15"
}
```

### Email List
```javascript
// Input
{
  "users": [
    {"name": "Alice", "email": "alice@example.com"},
    {"name": "Bob", "email": "bob@example.com"},
    {"name": "Charlie", "email": "charlie@example.com"}
  ]
}

// Morph
{
  emailList: join(map($.users, "u", u.email), "; "),
  nameList: join(map($.users, "u", u.name), ", ")
}

// Output
{
  "emailList": "alice@example.com; bob@example.com; charlie@example.com",
  "nameList": "Alice, Bob, Charlie"
}
```

### Build Path
```javascript
// Input
{
  "segments": ["home", "user", "documents", "file.txt"]
}

// Morph
{
  unixPath: "/" + join($.segments, "/"),
  windowsPath: join($.segments, "\\")
}

// Output
{
  "unixPath": "/home/user/documents/file.txt",
  "windowsPath": "home\\user\\documents\\file.txt"
}
```

### Join with Empty Separator
```javascript
// Input
{
  "chars": ["H", "e", "l", "l", "o"]
}

// Morph
{
  word: join($.chars, "")
}

// Output
{
  "word": "Hello"
}
```

## Common Use Cases

1. **List Formatting**: Create readable lists
2. **CSV Generation**: Build CSV lines
3. **Path Construction**: Build file paths or URLs
4. **String Building**: Concatenate multiple parts

## Tips and Best Practices

- Empty separator joins without spaces
- Works with any array element types
- Combine with map() to transform before joining
- Useful for creating formatted output

## Related Functions

- [split()](split.md) - Split string into array
- [map()](map.md) - Transform array before joining
- [filter()](filter.md) - Filter array before joining
- [concat()](concat.md) - Concatenate arrays

## Performance Notes

- O(n) where n is total string length
- Fast for most use cases
- Memory proportional to result string size

---

[← Back to Functions](../README.md#string-functions)
