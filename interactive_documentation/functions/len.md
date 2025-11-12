# len() - Get Length

## Syntax
```javascript
len(value)
```

## Description
Returns the length of an array or string.

## Parameters
- `value` - Array or string to measure

## Returns
Number representing the length.

## Examples

### Array Length
```javascript
// Input
{
  "items": ["apple", "banana", "orange"]
}

// Morph
{
  itemCount: len($.items)
}

// Output
{
  "itemCount": 3
}
```

### String Length
```javascript
// Input
{
  "text": "Hello World"
}

// Morph
{
  length: len($.text)
}

// Output
{
  "length": 11
}
```

### Empty Collections
```javascript
// Input
{
  "emptyArray": [],
  "emptyString": ""
}

// Morph
{
  arrayLen: len($.emptyArray),
  stringLen: len($.emptyString),
  arrayIsEmpty: len($.emptyArray) == 0,
  stringIsEmpty: len($.emptyString) == 0
}

// Output
{
  "arrayLen": 0,
  "stringLen": 0,
  "arrayIsEmpty": true,
  "stringIsEmpty": true
}
```

### Conditional Logic Based on Length
```javascript
// Input
{
  "users": [
    {"name": "Alice"},
    {"name": "Bob"}
  ]
}

// Morph
{
  userCount: len($.users),
  hasUsers: len($.users) > 0,
  message: if (len($.users) > 1) {
    "Multiple users found"
  } else if (len($.users) == 1) {
    "One user found"
  } else {
    "No users found"
  }
}

// Output
{
  "userCount": 2,
  "hasUsers": true,
  "message": "Multiple users found"
}
```

### Validate Input Length
```javascript
// Input
{
  "password": "mypassword",
  "username": "alice"
}

// Morph
{
  passwordValid: len($.password) >= 8,
  usernameValid: len($.username) >= 3 && len($.username) <= 20
}

// Output
{
  "passwordValid": true,
  "usernameValid": true
}
```

### Count Characters
```javascript
// Input
{
  "comment": "This is a test comment"
}

// Morph
{
  charCount: len($.comment),
  remaining: 100 - len($.comment),
  isValid: len($.comment) <= 100
}

// Output
{
  "charCount": 23,
  "remaining": 77,
  "isValid": true
}
```

### Process Based on Length
```javascript
// Input
{
  "items": ["a", "bb", "ccc", "dddd"]
}

// Morph
{
  longItems: filter($.items, "i", len(i) > 2),
  lengths: map($.items, "i", len(i)),
  totalChars: sum(map($.items, "i", len(i)))
}

// Output
{
  "longItems": ["ccc", "dddd"],
  "lengths": [1, 2, 3, 4],
  "totalChars": 10
}
```

## Common Use Cases

1. **Validation**: Check string/array length constraints
2. **Empty Checks**: Determine if collections are empty
3. **Statistics**: Count elements or characters
4. **Conditional Logic**: Branch based on size

## Tips and Best Practices

- Works with both strings and arrays
- Returns 0 for empty collections
- Use for validation before processing
- Efficient O(1) operation for arrays

## Related Functions

- [count()](count.md) - Count elements in stream
- [exists()](exists.md) - Check if value exists
- [filter()](filter.md) - Filter by length

## Performance Notes

- O(1) for arrays (stored length)
- O(1) or O(n) for strings (depends on implementation)
- Very fast operation

---

[← Back to Functions](../README.md#utility-functions)
