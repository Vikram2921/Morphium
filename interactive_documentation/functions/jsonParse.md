# jsonParse() - Parse JSON String

## Syntax
```javascript
jsonParse(jsonString)
```

## Description
Parses a JSON string and returns the corresponding value (object, array, string, number, boolean, or null).

## Parameters
- `jsonString` - String containing valid JSON

## Returns
Parsed JSON value.

## Examples

### Parse JSON Object
```javascript
// Input
{
  "jsonStr": "{\"name\":\"Alice\",\"age\":25}"
}

// Morph
{
  parsed: jsonParse($.jsonStr)
}

// Output
{
  "parsed": {
    "name": "Alice",
    "age": 25
  }
}
```

### Parse JSON Array
```javascript
// Input
{
  "jsonStr": "[1,2,3,4,5]"
}

// Morph
{
  numbers: jsonParse($.jsonStr),
  sum: sum(jsonParse($.jsonStr))
}

// Output
{
  "numbers": [1, 2, 3, 4, 5],
  "sum": 15
}
```

### Parse Nested JSON
```javascript
// Input
{
  "config": "{\"database\":{\"host\":\"localhost\",\"port\":5432}}"
}

// Morph
{
  settings: jsonParse($.config),
  dbHost: jsonParse($.config).database.host
}

// Output
{
  "settings": {
    "database": {
      "host": "localhost",
      "port": 5432
    }
  },
  "dbHost": "localhost"
}
```

### Parse API Response String
```javascript
// Input
{
  "response": "{\"status\":\"success\",\"data\":[{\"id\":1},{\"id\":2}]}"
}

// Morph
{
  result: jsonParse($.response),
  ids: map(jsonParse($.response).data, "d", d.id)
}

// Output
{
  "result": {
    "status": "success",
    "data": [{"id": 1}, {"id": 2}]
  },
  "ids": [1, 2]
}
```

### Parse Array of JSON Strings
```javascript
// Input
{
  "items": [
    "{\"name\":\"Alice\"}",
    "{\"name\":\"Bob\"}",
    "{\"name\":\"Charlie\"}"
  ]
}

// Morph
{
  users: map($.items, "i", jsonParse(i)),
  names: map($.items, "i", jsonParse(i).name)
}

// Output
{
  "users": [
    {"name": "Alice"},
    {"name": "Bob"},
    {"name": "Charlie"}
  ],
  "names": ["Alice", "Bob", "Charlie"]
}
```

### Parse Primitive Values
```javascript
// Input
{
  "str": "\"hello\"",
  "num": "42",
  "bool": "true",
  "nullVal": "null"
}

// Morph
{
  string: jsonParse($.str),
  number: jsonParse($.num),
  boolean: jsonParse($.bool),
  nullValue: jsonParse($.nullVal)
}

// Output
{
  "string": "hello",
  "number": 42,
  "boolean": true,
  "nullValue": null
}
```

## Common Use Cases

1. **API Integration**: Parse JSON response strings
2. **Data Import**: Parse stored JSON data
3. **Configuration**: Load JSON config from strings
4. **Message Processing**: Parse JSON messages

## Tips and Best Practices

- Input must be valid JSON
- Throws error on invalid JSON
- Use for deserializing data
- Combine with error handling

## Related Functions

- [jsonStringify()](jsonStringify.md) - Convert to JSON string
- [toNumber()](toNumber.md) - Convert to number
- [toString()](toString.md) - Convert to string

## Performance Notes

- O(n) where n is string length
- Parser validates and builds structure
- Fast for well-formed JSON

---

[← Back to Functions](../README.md#type-conversion)
